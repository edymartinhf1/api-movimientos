package com.bootcamp.bank.movimientos.controller;

import com.bootcamp.bank.movimientos.model.Movimiento;
import com.bootcamp.bank.movimientos.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Clase Consulta Movimientos
 */
@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientosController {

    private final MovimientoService movimientoService;

    /**
     * Permite obtener los movimientos por id cliente
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<Movimiento> getMovimientos(@PathVariable String id){
        return movimientoService.getMovimientos(id);
    }
}
