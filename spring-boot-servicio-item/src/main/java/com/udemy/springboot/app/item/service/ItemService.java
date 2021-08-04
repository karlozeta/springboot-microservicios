package com.udemy.springboot.app.item.service;

import java.util.List;

import com.udemy.springboot.app.item.models.Item;
import com.udemy.springboot.app.item.models.Producto;

public interface ItemService {
	
	public List<Item> findAll();
	
	public Item findById(Long id, int cantidad);
	
	public Producto save(Producto producto);
	
	public Producto update(Producto producto, Long id);
	
	public void eliminar(Long id);

}
