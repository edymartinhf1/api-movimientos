package com.bootcamp.bank.movimientos.model;

import lombok.Data;

@Data
public class Pago {
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaPago;
    private Double importe;
}