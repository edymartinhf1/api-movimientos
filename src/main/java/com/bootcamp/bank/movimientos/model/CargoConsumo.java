package com.bootcamp.bank.movimientos.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CargoConsumo {
    private String id;
    private String idCliente;
    private String numeroCredito;
    private String fechaConsumo;
    private Double importe;
    private String fechaConsumoT;
    private String numeroTarjetaCredito;


}