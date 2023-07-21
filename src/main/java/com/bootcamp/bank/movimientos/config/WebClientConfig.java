package com.bootcamp.bank.movimientos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Clase configuracion clientes
 */
@Configuration
public class WebClientConfig {

    @Bean(name = "clientConsumos")
    public WebClient webClientConsumos() {
        return WebClient.create("http://localhost:8085");
    }

    @Bean(name = "clientOperaciones")
    public WebClient webClientOperaciones() {
        return WebClient.create("http://localhost:8084");
    }

    @Bean(name = "clientPagos")
    public WebClient webClientPagos() {
        return WebClient.create("http://localhost:8086");
    }

    @Bean(name = "clientClientes")
    public WebClient webClientClientes() {
        return WebClient.create("http://localhost:8081");
    }

    @Bean(name = "clientCuentas")
    public WebClient webClientCuentas() {
        return WebClient.create("http://localhost:8083");
    }

    @Bean(name = "clientCreditos")
    public WebClient webClientCreditos() {
        return WebClient.create("http://localhost:8082");
    }
}