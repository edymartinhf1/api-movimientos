package com.bootcamp.bank.movimientos.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Pago {

    private String id;
    private String idCliente;
    private String numeroCredito;
    private String numeroTarjetaCredito;
    private LocalDateTime fechaPago;
    private String fechaPagoT;
    private Double importe;






}