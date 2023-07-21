package com.bootcamp.bank.movimientos.clients;

import com.bootcamp.bank.movimientos.model.TarjetaDebito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class ClientApiCuentas {
    @Autowired
    @Qualifier("clientCuentas")
    private WebClient webClient;

    public Flux<TarjetaDebito> getTarjetaDebitoPorCliente(String idCliente) {
        return webClient.get()
                .uri("/tarjeta/debito/" + idCliente)
                .retrieve()
                .bodyToFlux(TarjetaDebito.class);
    }

}