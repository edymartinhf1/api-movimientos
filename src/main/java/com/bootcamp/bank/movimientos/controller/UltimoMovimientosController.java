package com.bootcamp.bank.movimientos.controller;

import com.bootcamp.bank.movimientos.model.ReporteUltimoMovimiento;
import com.bootcamp.bank.movimientos.service.UltimoMovimientosService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Permite obtener los ultimos movimientos
 */
@RestController
@RequestMapping("/ultimos/movimientos")
@RequiredArgsConstructor
public class UltimoMovimientosController {

    private final UltimoMovimientosService ultimoMovimientosService;

    /**
     * Permite obtener los ultimos movimientos de tarjetas de debito y de credito
     * @param idCliente
     * @return
     */
    @GetMapping("/{idCliente}")
    public Mono<ReporteUltimoMovimiento> geLastMovements(@PathVariable String idCliente){
        return ultimoMovimientosService.getUlitmosMovimientos(idCliente);
    }
}
