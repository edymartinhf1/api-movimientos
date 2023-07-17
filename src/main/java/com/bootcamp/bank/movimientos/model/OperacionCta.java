package com.bootcamp.bank.movimientos.model;

import lombok.Data;

@Data
public class OperacionCta {
    private String id;
    private String idCliente;
    private String tipoOperacion;
    private String fechaOperacion;
    private String numeroCuenta;
    private Double importe;
}