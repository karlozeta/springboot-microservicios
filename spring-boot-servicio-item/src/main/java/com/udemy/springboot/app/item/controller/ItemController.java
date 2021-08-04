package com.udemy.springboot.app.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.udemy.springboot.app.item.models.Item;
import com.udemy.springboot.app.commons.models.entity.Producto;
import com.udemy.springboot.app.item.service.ItemService;

@RefreshScope
@RestController
public class ItemController {
	
	//Utilizando el @LoadBalanced se debe cambiar por el nombre asignado en la implementacion del servicio
	//@Qualifier("serviceFeign")
	
	//Nombre del servicio de la implementacion usando @LoadBalanced
	//@Qualifier("serviceRestTemplate") 
	
	public static Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Value("${configuracion.texto}")
	private String texto;
	
	@Autowired
	private Environment environment; 
	
	//Para usar el feign @Qualifier("serviceFeign")
	//Para usar el RestTemplate @Qualifier("serviceRestTemplate")
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(){
		return itemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod="metodoAlternativo")
	@GetMapping("/buscar/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable int cantidad) {
		return itemService.findById(id, cantidad);
	}
	
	//Se crea el metodo altenativo segun como esta referencia en el Hystrix
	public Item metodoAlternativo(Long id, int cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Camara SONY");
		producto.setPrecio(500.00);
		item.setProducto(producto);
		return item;
	}
	
	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto){
		logger.info(texto);
		Map<String, String> json = new HashMap<>();
		json.put("texto", texto);
		json.put("puerto", puerto);
		
		//Validar si se esta ingresando al ambiente Dev o Prod
		if(environment.getActiveProfiles().length > 0 && environment.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", environment.getProperty("configuracion.autor.email"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemService.save(producto);
		
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		return itemService.update(producto, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.eliminar(id);
	}
	
}
