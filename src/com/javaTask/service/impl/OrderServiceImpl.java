package com.javaTask.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.OrderDAO;
import com.javaTask.model.Order;
import com.javaTask.service.OrderService;

public class OrderServiceImpl implements OrderService {
	
	private static final Logger LOG = Logger.getLogger(OrderServiceImpl.class.getName());

	@Override
	public void insert(Order order) {
		try {
			OrderDAO.insert(order);
		} catch (SQLException e) {
			LOG.info("Exception occured in the insert() of OrderServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getAll() {
		try {
			return OrderDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception occured in the getAll() of OrderServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Order getOneById(int id) {
		try {
			return OrderDAO.getOneById(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getOneById() of OrderServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Order order) {
		try {
			OrderDAO.updateById(order);
		} catch (SQLException e) {
			LOG.info("Exception occured in the update() of OrderServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		try {
			OrderDAO.delete(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the delete() of OrderServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getOrdersByCartId(int id) {
		try {
			return OrderDAO.getOrdersByCartId(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getOrdersByCartId() of OrderServiceImpl.class");
			e.printStackTrace();
		}
		
		return null;
	}

	
}
