package com.bootcamp.bank.movimientos.service.impl;

import com.bootcamp.bank.movimientos.clients.*;
import com.bootcamp.bank.movimientos.exception.BusinessException;
import com.bootcamp.bank.movimientos.model.ReporteUltimoMovimiento;
import com.bootcamp.bank.movimientos.service.UltimoMovimientosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
                                ReporteUltimoMovimiento rep=new ReporteUltimoMovimiento();
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
                    return Mono.just(rep);
                });


    }


    public Mono<ReporteUltimoMovimiento> getCreditoConsumos(ReporteUltimoMovimiento reporte,String idCliente){

        return clientApiCreditos.getCreditos(idCliente)
                .filter(credito->credito.getNumeroTarjetaCredito()!=null)
                .flatMap(credito->{
                    log.info("producto credito = "+credito.toString());
                    return clientApiConsumos.getMovsConsumosPorIdClienteAndTarjetaCredito(idCliente,credito.getNumeroTarjetaCredito());
                })
                .collectList()
                .flatMap(listaconsumos->{
                    ReporteUltimoMovimiento reps=new ReporteUltimoMovimiento();
                    reps.setOperacionCtaList(reporte.getOperacionCtaList());
                    reps.setConsumos(listaconsumos);
                    return Mono.just(reps);
                });
    }

    public Mono<ReporteUltimoMovimiento> getCreditoPagos(ReporteUltimoMovimiento reporte,String idCliente){

        return clientApiCreditos.getCreditos(idCliente)
                .filter(credito->credito.getNumeroTarjetaCredito()!=null)
                .flatMap(credito->{
                    log.info("producto credito = "+credito.toString());
                    return clientApiPagos.getMovsPagosPorIdClienteAndTarjetaCredito(idCliente,credito.getNumeroTarjetaCredito());
                })
                .collectList()
                .flatMap(listapagos->{
                    ReporteUltimoMovimiento reps=new ReporteUltimoMovimiento();
                    reps.setOperacionCtaList(reporte.getOperacionCtaList());
                    reps.setConsumos(reporte.getConsumos());
                    reps.setPagos(listapagos);
                    return Mono.just(reps);
                });

    }



}
