package com.bootcamp.bank.movimientos.clients;

import com.bootcamp.bank.movimientos.model.OperacionCta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Clase cliente permite obtener operaciones por id cliente
 */
@Component
public class ClientApiOperaciones {
    @Autowired
    @Qualifier("clientOperaciones")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-cuentas-operaciones
     * @param idCliente
     * @return
     */
    public Flux<OperacionCta> getOperaciones(String idCliente) {
        return webClient.get()
                .uri("/operaciones/cuentas/cliente/" + idCliente)
                .retrieve()
                .bodyToFlux(OperacionCta.class);
    }

    public Flux<OperacionCta> getMovsOperacionesPorIdClienteAndTarjetaDebito(String idCliente,String numeroTarjetaDebito) {
        return webClient.get()
                .uri("/operaciones/cuentas/movimiento/cliente/" + idCliente+"/debito/"+numeroTarjetaDebito)
                .retrieve()
                .bodyToFlux(OperacionCta.class);
    }



}