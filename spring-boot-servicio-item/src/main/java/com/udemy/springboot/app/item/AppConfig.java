package com.udemy.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	@LoadBalanced //Balaceo de carga. Utiliza Ribbon para obtener la mejor instancia disponible
	@Bean("clienteRest")//Se puede registrar el metodo con un nombre
	public RestTemplate registrarRestTemplate() {
		return new RestTemplate();
	}
}
