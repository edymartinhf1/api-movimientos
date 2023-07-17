package com.bootcamp.bank.movimientos.service;

import com.bootcamp.bank.movimientos.model.Movimiento;
import reactor.core.publisher.Mono;

public interface MovimientoService {
    Mono<Movimiento> getMovimientos(String id);
}
