package com.bootcamp.bank.movimientos.clients;

import com.bootcamp.bank.movimientos.model.CargoConsumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

/**
 * Client Api consumos
 */
@Component
public class ClientApiConsumos {
    @Autowired
    @Qualifier("clientConsumos")
    private WebClient webClient;

    /**
     * Permite obtener operaciones de cliente del api-creditos-consumos
     * @param idCliente
     * @return
     */
    public Flux<CargoConsumo> getConsumos(String idCliente) {
        return webClient.get()
                .uri("/creditos/tarjetas/cargos/" + idCliente)
                .retrieve()
                .bodyToFlux(CargoConsumo.class);
    }

    public Flux<CargoConsumo> getMovsConsumosPorIdClienteAndTarjetaCredito(String idCliente, String numeroTarjetaCredito) {
        return webClient.get()
                .uri("/creditos/tarjetas/cargos/movimiento/cliente/" + idCliente+"/credito/"+numeroTarjetaCredito)
                .retrieve()
                .bodyToFlux(CargoConsumo.class);
    }

}
