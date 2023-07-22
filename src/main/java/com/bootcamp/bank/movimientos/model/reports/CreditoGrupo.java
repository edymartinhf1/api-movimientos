package com.bootcamp.bank.movimientos.model.reports;

import lombok.Data;

import java.util.List;

@Data
public class CreditoGrupo {
    private String numeroTarjetaCredito;
    private List<CreditoMovimiento> creditos;

}
