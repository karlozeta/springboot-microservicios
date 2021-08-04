package com.udemy.springboot.app.item.models;

import com.udemy.springboot.app.commons.models.entity.Producto;

public class Item {

	private Producto producto;
	private int cantidad;

	public Item() {
	}

	public Item(Producto producto, int cantidad) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getTotal() {
		return producto.getPrecio() * this.cantidad;
	}

}
