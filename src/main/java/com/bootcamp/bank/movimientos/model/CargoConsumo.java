package com.bootcamp.bank.movimientos.model;

import lombok.Data;

@Data
public class CargoConsumo {
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaConsumo;
    private Double importe;

}