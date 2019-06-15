package com.javaTask.service;

import java.util.List;

import com.javaTask.model.Product;

public interface ProductService {

	public void insert(Product product);
	public List<Product> getAll();
	public Product getOneById(int id);
	public void update(Product product);
	public void delete(Product product);
}
