package com.javaTask.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.CartDAO;
import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;
import com.javaTask.service.CartService;

public class CartServiceImpl implements CartService{
	
	private static final Logger LOG = Logger.getLogger(CartServiceImpl.class.getName());
	
	@Override
	public void createCart(Cart cart) {
		try {
			CartDAO.insert(cart);
		} catch (SQLException e) {
			LOG.info("Exception occured in the insert() of CartServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<Cart> getAll() {
		try {
			return CartDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception occured in the getAll() of CartServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Cart getOneById(int id) {
		try {
			return CartDAO.getOneById(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getOneById() of CartServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Cart cart) {
		try {
			CartDAO.updateById(cart);
		} catch (SQLException e) {
			LOG.info("Exception occured in the update() of CartServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Cart cart) {
		try {
			CartDAO.delete(cart);
		} catch (SQLException e) {
			LOG.info("Exception occured in the delete() of CartServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<Cart> getCartsByTime(long from, long till) {
		try {
			return CartDAO.getCartsByTime(from, till);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getCartsByTime() of CartServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Cart> getCartsByUserId(int id) {
		try {
			return CartDAO.getCartsByUserId(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getCartsByTime() of CartServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void checkout(Cart cart) {
		try {
			Cart cartToClose = CartDAO.getOneById(cart.getId());
			cartToClose.setStatus(Status.CLOSED);
			CartDAO.updateById(cartToClose);
		} catch (SQLException e) {
			LOG.info("Exception occured in the checkout() of CartServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public boolean validateCart(Cart cart) {
		Cart cartToCheck = null;
		
		try {
			cartToCheck = CartDAO.getOneById(cart.getId());
		} catch (SQLException e) {
			LOG.info("Exception occured in the validateCart() of CartServiceImpl.class");
			e.printStackTrace();
		}
		
		if (cartToCheck.getStatus().equals("Open") && cartToCheck != null) 
			return true;
		
		return false;
	}
}
