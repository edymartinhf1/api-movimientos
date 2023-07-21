package com.bootcamp.bank.movimientos.service.impl;

import com.bootcamp.bank.movimientos.clients.ClientApiClientes;
import com.bootcamp.bank.movimientos.clients.ClientApiConsumos;
import com.bootcamp.bank.movimientos.clients.ClientApiOperaciones;
import com.bootcamp.bank.movimientos.clients.ClientApiPagos;
import com.bootcamp.bank.movimientos.exception.BusinessException;
import com.bootcamp.bank.movimientos.model.Movimiento;
import com.bootcamp.bank.movimientos.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovimientoServiceImpl implements MovimientoService {

    private final ClientApiConsumos clientApiConsumos;

    private final ClientApiPagos clientApiPagos;

    private final ClientApiOperaciones clientApiOperaciones;

    private final ClientApiClientes clientApiClientes;

    @Override
    public Mono<Movimiento> getMovimientos(String id) {
        return clientApiClientes.getCliente(id)
                .switchIfEmpty(Mono.error(()->new BusinessException("No existe cliente con el id "+id))).
                flatMapMany(cliente-> {
                    log.info("cliente = "+cliente.toString());
                    return clientApiPagos.getPagos(id);
                })
                .collectList()
                .map(listaPagos-> {
                    Movimiento mov=new Movimiento();
                    mov.setPagosTarjetaCredito(listaPagos);
                    return mov;
                }).flatMap(movs -> {
                    return clientApiConsumos.getConsumos(id)
                            .collectList()
                            .map(listaconsumos->{
                                Movimiento mov=new Movimiento();
                                mov.setPagosTarjetaCredito(movs.getPagosTarjetaCredito());
                                mov.setCargosConsumoTarjetaCredito(listaconsumos);
                                return mov;
                            });
                }).flatMap(movimiento ->{
                    return clientApiOperaciones.getOperaciones(id)
                            .collectList()
                            .map(operaciones->{
                                Movimiento mov=new Movimiento();
                                mov.setIdCliente(id);
                                mov.setPagosTarjetaCredito(movimiento.getPagosTarjetaCredito());
                                mov.setCargosConsumoTarjetaCredito(movimiento.getCargosConsumoTarjetaCredito());
                                mov.setOperacionesCuentasCorriente(operaciones);
                                return mov;
                            });
                });

    }
}
