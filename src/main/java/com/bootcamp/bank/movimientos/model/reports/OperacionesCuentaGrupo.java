package com.bootcamp.bank.movimientos.model.reports;

import com.bootcamp.bank.movimientos.model.OperacionCta;
import lombok.Data;

import java.util.List;
@Data
public class OperacionesCuentaGrupo {
    private String numeroTarjetaDebito;
    private List<OperacionCta> operaciones;
}
