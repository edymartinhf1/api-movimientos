package com.bootcamp.bank.movimientos.model;

import lombok.Data;


@Data
public class CreditoProducto {
    private String  id;
    private String  idCliente;
    private String  tipoCredito; // PER = personal , EMP = empresarial , TJC = tarjeta de credito
    private String  numeroCredito;
    private String  numeroTarjetaCredito;
    private String  fechaCreacion;
    private Double  lineaCredito;



}