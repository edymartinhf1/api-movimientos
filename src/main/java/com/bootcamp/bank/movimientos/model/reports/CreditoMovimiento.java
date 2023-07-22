package com.bootcamp.bank.movimientos.model.reports;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreditoMovimiento {
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String numeroTarjetaCredito;
    private LocalDateTime fecha;
    private String fechaT;
    private Double importe;
}
