package com.udemy.springboot.app.item.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.springboot.app.item.cliente.ProductoClienteRest;
import com.udemy.springboot.app.item.models.Item;

@Service("serviceFeign")
public class ItemServiceFeign implements ItemService{

	@Autowired
	private ProductoClienteRest clienteFeign;
	
	@Override
	public List<Item> findAll() {
		return clienteFeign.listar().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, int cantidad) {
		return new Item(clienteFeign.detalle(id), cantidad);
	}

}
