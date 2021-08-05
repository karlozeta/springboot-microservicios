package com.udemy.springboot.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.udemy.springboot.app.commons.usuarios.models.entity"})
public class SpringBootServicioUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootServicioUsuariosApplication.class, args);
	}

}
