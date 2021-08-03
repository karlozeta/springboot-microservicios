package com.udemy.springboot.app.productos.services;

import java.util.List;

import com.udemy.springboot.app.productos.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	public Producto findById(Long id);
}
