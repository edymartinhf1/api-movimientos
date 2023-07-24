package com.bootcamp.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiMovimientosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiMovimientosApplication.class, args);
	}

}
