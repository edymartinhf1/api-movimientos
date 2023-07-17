package com.bootcamp.bank;

import com.bootcamp.bank.movimientos.service.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApiMovimientosApplicationTests {
	@Autowired
	private MovimientoService movimientoService;
	@Test
	void contextLoads() {
		assertThat(movimientoService).isNotNull();
	}

}
