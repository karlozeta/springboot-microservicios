package com.udemy.springboot.app.productos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.udemy.springboot.app.commons.models.entity"})
public class SpringBootServicioProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioProductosApplication.class, args);
	}

}
