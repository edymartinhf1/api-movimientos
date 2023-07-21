package com.bootcamp.bank.movimientos.model;

import lombok.Data;

@Data
public class TarjetaDebito {
    private String id;
    private String idCliente;
    private String fechaCreacionT;
    private String numeroCuentaPrincipal;
    private String numeroTarjetaDebito;
    private String numeroCuenta;
}
