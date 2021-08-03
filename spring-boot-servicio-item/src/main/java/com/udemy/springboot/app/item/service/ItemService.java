package com.udemy.springboot.app.item.service;

import java.util.List;

import com.udemy.springboot.app.item.models.Item;

public interface ItemService {
	
	public List<Item> findAll();
	
	public Item findById(Long id, int cantidad);

}
