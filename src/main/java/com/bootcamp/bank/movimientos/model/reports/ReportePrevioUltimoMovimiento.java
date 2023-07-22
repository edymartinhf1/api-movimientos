package com.bootcamp.bank.movimientos.model.reports;

import com.bootcamp.bank.movimientos.model.CargoConsumo;
import com.bootcamp.bank.movimientos.model.Cliente;
import com.bootcamp.bank.movimientos.model.OperacionCta;
import com.bootcamp.bank.movimientos.model.Pago;
import lombok.Data;

import java.util.List;
@Data
public class ReportePrevioUltimoMovimiento {
    private Cliente cliente;
    private List<OperacionCta> operacionCtaList;
    private List<Pago> pagos;
    private List<CargoConsumo> consumos;
}
