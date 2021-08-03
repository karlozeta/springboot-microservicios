package com.udemy.springboot.app.productos.dao;

import org.springframework.data.repository.CrudRepository;

import com.udemy.springboot.app.productos.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long>{

}
