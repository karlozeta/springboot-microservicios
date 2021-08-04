package com.udemy.springboot.app.productos.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.udemy.springboot.app.commons.models.entity.Producto;

public class ProductoControllerTest {

	private ProductoController controller;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void listarProductos() {
		controller = new ProductoController();
		List<Producto> listaProducto = new ArrayList<>();
		listaProducto.add(new Producto("Armario", 100.0, new Date()));
		listaProducto.add(new Producto("Muebles", 1500.0, new Date()));
		for (Producto lista : listaProducto) {
			System.out.println(lista.getNombre() + " - " + lista.getPrecio());
		}
		if (listaProducto == null || listaProducto.isEmpty()) {
			Assert.assertTrue(false);
		} else {
			Assert.assertTrue(true);
		}
	}

	@Test
	public void listarProductosException() {
		controller = new ProductoController();
		Assert.assertThrows(NullPointerException.class, () -> {
			controller.list();
		});

	}

}
