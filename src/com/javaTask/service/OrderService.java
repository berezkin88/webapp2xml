package com.javaTask.service;

import java.util.List;

import com.javaTask.model.Order;

public interface OrderService {
	
	public void insert(Order order);
	public List<Order> getAll();
	public List<Order> getOrdersByCartId(int id);
	public Order getOneById(int id);
	public void update(Order order);
	public void delete(Order order);
}
