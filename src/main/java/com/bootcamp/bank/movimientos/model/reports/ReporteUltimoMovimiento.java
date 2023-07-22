package com.bootcamp.bank.movimientos.model.reports;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReporteUltimoMovimiento {
    private List<CreditoGrupo> creditos;
    private List<OperacionesCuentaGrupo> operacionesCuentas;
}
