package com.bootcamp.bank.movimientos.service.impl;

import com.bootcamp.bank.movimientos.clients.*;
import com.bootcamp.bank.movimientos.exception.BusinessException;
import com.bootcamp.bank.movimientos.model.reports.*;
import com.bootcamp.bank.movimientos.model.OperacionCta;
import com.bootcamp.bank.movimientos.service.UltimoMovimientosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
@RequiredArgsConstructor
public class UltimoMovimientoServiceImpl implements UltimoMovimientosService {

    private final ClientApiOperaciones clientApiOperaciones;

    private final ClientApiCuentas clientApiCuentas;

    private final ClientApiCreditos clientApiCreditos;

    private final ClientApiConsumos clientApiConsumos;

    private final ClientApiPagos clientApiPagos;

    private final ClientApiClientes clientApiClientes;

    /**
     * Permite obtener 10 ultimos movimientos de tarjetas credito y tarjetas debito
     * @param idCliente
     * @return
     */
    @Override
    public Mono<ReporteUltimoMovimiento> getUlitmosMovimientos(String idCliente) {

        return clientApiClientes.getCliente(idCliente)
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con el id "+idCliente)))
                .flatMap(cliente->{
                    log.info("cliente="+cliente.toString());
                    return clientApiCuentas.getTarjetaDebitoPorCliente(idCliente)
                            .flatMap(tarjetaDebito -> {
                                log.info(" tarjeta debito " + tarjetaDebito.toString());
                                return clientApiOperaciones.getMovsOperacionesPorIdClienteAndTarjetaDebito(idCliente, tarjetaDebito.getNumeroTarjetaDebito());
                            })
                            .collectList()
                            .flatMap(listOperaciones->{
                                ReportePrevioUltimoMovimiento rep=new ReportePrevioUltimoMovimiento();
                                rep.setCliente(cliente);
                                rep.setOperacionCtaList(listOperaciones);
                                return Mono.just(rep);
                            });
                })
                .flatMap(reporteconsumo->{
                    return this.getCreditoConsumos(reporteconsumo,idCliente)
                            .flatMap(reportepago->{
                                return this.getCreditoPagos(reportepago,idCliente);
                            });
                }).flatMap(rep->{
                    ReporteUltimoMovimiento reporte=generateReport(rep);
                    return Mono.just(reporte);
                });


    }


    private Mono<ReportePrevioUltimoMovimiento> getCreditoConsumos(ReportePrevioUltimoMovimiento reporte, String idCliente){

        return clientApiCreditos.getCreditos(idCliente)
                .filter(credito->credito.getNumeroTarjetaCredito()!=null)
                .flatMap(credito->{
                    log.info("producto credito = "+credito.toString());
                    return clientApiConsumos.getMovsConsumosPorIdClienteAndTarjetaCredito(idCliente,credito.getNumeroTarjetaCredito());
                })
                .collectList()
                .flatMap(listaconsumos->{
                    ReportePrevioUltimoMovimiento reps=new ReportePrevioUltimoMovimiento();
                    reps.setCliente(reporte.getCliente());
                    reps.setOperacionCtaList(reporte.getOperacionCtaList());
                    reps.setConsumos(listaconsumos);
                    return Mono.just(reps);
                });
    }

    private Mono<ReportePrevioUltimoMovimiento> getCreditoPagos(ReportePrevioUltimoMovimiento reporte, String idCliente){

        return clientApiCreditos.getCreditos(idCliente)
                .filter(credito->credito.getNumeroTarjetaCredito()!=null)
                .flatMap(credito->{
                    log.info("producto credito = "+credito.toString());
                    return clientApiPagos.getMovsPagosPorIdClienteAndTarjetaCredito(idCliente,credito.getNumeroTarjetaCredito());
                })
                .collectList()
                .flatMap(listapagos->{
                    ReportePrevioUltimoMovimiento reps=new ReportePrevioUltimoMovimiento();
                    reps.setCliente(reporte.getCliente());
                    reps.setOperacionCtaList(reporte.getOperacionCtaList());
                    reps.setConsumos(reporte.getConsumos());
                    reps.setPagos(listapagos);
                    return Mono.just(reps);
                });

    }

    /**
     *
     * @param reporte
     * @return
     */
    private Map<String,List<CreditoMovimiento>> mergeAndGroupMovimientosTarjetaCredito(ReportePrevioUltimoMovimiento reporte){

        Stream<CreditoMovimiento> reporteMovimientoConsumo=reporte.getConsumos().stream().map(consumo->{
           CreditoMovimiento operacionCredito=new CreditoMovimiento();
           operacionCredito.setNumeroCredito(consumo.getNumeroCredito());
           operacionCredito.setId(consumo.getId());
           operacionCredito.setIdCliente(consumo.getIdCliente());
           operacionCredito.setNumeroTarjetaCredito(consumo.getNumeroTarjetaCredito());
           operacionCredito.setFecha(consumo.getFechaConsumo());
           operacionCredito.setImporte(consumo.getImporte());
           return operacionCredito;
        });

        Stream<CreditoMovimiento> reporteMovimientoPago=reporte.getPagos().stream().map(pago->{
            CreditoMovimiento operacionCredito=new CreditoMovimiento();
            operacionCredito.setNumeroCredito(pago.getNumeroCredito());
            operacionCredito.setId(pago.getId());
            operacionCredito.setIdCliente(pago.getIdCliente());
            operacionCredito.setNumeroTarjetaCredito(pago.getNumeroTarjetaCredito());
            operacionCredito.setFecha(pago.getFechaPago());
            operacionCredito.setImporte(pago.getImporte());
            return operacionCredito;
        });

        Map<String,List<CreditoMovimiento>> creditos = Stream.concat(reporteMovimientoConsumo,reporteMovimientoPago)
                .sorted(Comparator.comparing(CreditoMovimiento::getFecha))
                .limit(10)
                .collect(Collectors.groupingBy(CreditoMovimiento::getNumeroTarjetaCredito));

        return creditos;

    }
    private Map<String,List<OperacionCta>> groupOperacionTarjetaDebito(ReportePrevioUltimoMovimiento reporte){
        Map<String,List<OperacionCta>> operaciones = reporte.getOperacionCtaList().stream()
                .sorted(Comparator.comparing(OperacionCta::getFechaOperacion))
                .limit(10)
                .collect(Collectors.groupingBy(OperacionCta::getNumeroTarjetaDebito));
        return operaciones;
    }


    private ReporteUltimoMovimiento generateReport(ReportePrevioUltimoMovimiento reporte){
        Map<String,List<CreditoMovimiento>> creditos = mergeAndGroupMovimientosTarjetaCredito(reporte);
        Map<String,List<OperacionCta>> operaciones = groupOperacionTarjetaDebito(reporte);

        List<CreditoGrupo> creditosGrupoList=new ArrayList<>();
        creditos.forEach((a,b)->{
           CreditoGrupo creditoGrupo=new CreditoGrupo();
           creditoGrupo.setNumeroTarjetaCredito(a);
           creditoGrupo.setCreditos(b);
           creditosGrupoList.add(creditoGrupo);
        });

        List<OperacionesCuentaGrupo> operacionesCuentaGrupoList=new ArrayList<>();
        operaciones.forEach((numeroTarjetaDebito,operacionesList)->{
            OperacionesCuentaGrupo operacionesCuentaGrupo=new OperacionesCuentaGrupo();
            operacionesCuentaGrupo.setNumeroTarjetaDebito(numeroTarjetaDebito);
            operacionesCuentaGrupo.setOperaciones(operacionesList);
            operacionesCuentaGrupoList.add(operacionesCuentaGrupo);
        });

        ReporteUltimoMovimiento repUltimoMovimiento=new ReporteUltimoMovimiento();
        repUltimoMovimiento.setCreditos(creditosGrupoList);
        repUltimoMovimiento.setOperacionesCuentas(operacionesCuentaGrupoList);

        return repUltimoMovimiento;
    }



}
