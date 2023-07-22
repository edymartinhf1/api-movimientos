package com.bootcamp.bank.movimientos.service;

import com.bootcamp.bank.movimientos.model.reports.ReportePrevioUltimoMovimiento;
import com.bootcamp.bank.movimientos.model.reports.ReporteUltimoMovimiento;
import reactor.core.publisher.Mono;

public interface UltimoMovimientosService {
    Mono<ReporteUltimoMovimiento> getUlitmosMovimientos(String idCliente);
}
