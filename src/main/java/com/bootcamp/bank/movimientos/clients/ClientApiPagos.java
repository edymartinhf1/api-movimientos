package com.bootcamp.bank.movimientos.clients;

import com.bootcamp.bank.movimientos.model.Pago;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Client Api Pagos
 */
@Component
public class ClientApiPagos {

    @Autowired
    @Qualifier("clientPagos")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-creditos-pagos
     * @param idCliente
     * @return
     */
    public Flux<Pago> getPagos(String idCliente) {
        return webClient.get()
                .uri("/creditos/pago/" + idCliente)
                .retrieve()
                .bodyToFlux(Pago.class);
    }

    public Flux<Pago> getMovsPagosPorIdClienteAndTarjetaCredito(String idCliente, String numeroTarjetaCredito) {
        return webClient.get()
                .uri("/creditos/pago/movimiento/cliente/" + idCliente+"/credito/"+numeroTarjetaCredito)
                .retrieve()
                .bodyToFlux(Pago.class);
    }
}
