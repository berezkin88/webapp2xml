package main.java.com.javaTask.service;

import java.util.List;

import main.java.com.javaTask.model.Cart;

public interface CartService {

	public void createCart(Cart cart);
	public List<Cart> getAll();
	public List<Cart> getCartsByTime(long from, long till);
	public List<Cart> getCartsByUserId(int id);
	public Cart getOneById(int id);
	public Cart getCartsByUserIdAndOpen(int id);
	public void update(Cart cart);
	public void delete(Cart cart);
	public void checkout(int id);
	public boolean validateCart(Cart cart);
}
