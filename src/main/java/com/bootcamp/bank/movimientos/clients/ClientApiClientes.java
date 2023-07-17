package com.bootcamp.bank.movimientos.clients;

import com.bootcamp.bank.movimientos.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Client api clientes
 */
@Component
public class ClientApiClientes {
    @Autowired
    @Qualifier("clientClientes")
    private WebClient webClient;

    /**
     * Permite obtener informacion de cliente del api-clientes
     * @param idCliente
     * @return
     */

    public Mono<Cliente> getClientes(String idCliente) {
        return webClient.get()
                .uri("/clientes/" + idCliente)
                .retrieve()
                .bodyToMono(Cliente.class);
    }
}