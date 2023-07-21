package com.bootcamp.bank.movimientos.model;

import lombok.Data;

import java.util.List;
@Data
public class ReporteUltimoMovimiento {
    private Cliente cliente;
    private List<OperacionCta> operacionCtaList;
    private List<Pago> pagos;
    private List<CargoConsumo> consumos;
}
