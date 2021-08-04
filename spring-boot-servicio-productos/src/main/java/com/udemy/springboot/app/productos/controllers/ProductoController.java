package com.udemy.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.springboot.app.commons.models.entity.Producto;
import com.udemy.springboot.app.productos.services.IProductoService;

@RestController
public class ProductoController {
	
	@Autowired
	private Environment environment;
	
	@Value("${server.port}")
	private int port;

	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/listar")
	public List<Producto> list(){
		return productoService.findAll().stream().map(producto -> {
			//Se utiliza inyectando Environment para obtener valores del properties
			producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
			
			//Se utiliza usando la anotacion @Values( para obtener valores del properties)
			//producto.setPort(port);
			return producto;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/buscar/{id}")
	public Producto buscarXId(@PathVariable Long id) {
		Producto producto = productoService.findById(id); 
		
		//Se utiliza inyectando Environment para obtener valores del properties
		producto.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		
		//Se utiliza usando la anotacion @Values( para obtener valores del properties)
		//producto.setPort(port);
		
		/*
		 * Se usó para simular un error en tiempo de ejecucion para probar el Hystrix
		 * el cual se creo un metodo alternativo para ejecutar en caso de fallo
		boolean ok = false;
		if(!ok) {
			throw new RuntimeException("No se pudo cargar el producto");
		}
		*/
		
		/*
		 * Se usó para simular tiempo de espera y generar un timeout
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return producto;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);		
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoDB = productoService.findById(id);
		productoDB.setNombre(producto.getNombre());
		productoDB.setPrecio(producto.getPrecio());
		return productoService.save(productoDB);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {	
		productoService.deleteById(id);
	}
	
	 
}
