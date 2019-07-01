package com.javaTask.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.ProductDAO;
import com.javaTask.model.Product;
import com.javaTask.service.ProductService;
import com.javaTask.utilities.ProductTO;

public class ProductServiceImpl implements ProductService {
	
	private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class.getName());

	@Override
	public void insert(Product product) {
		try {
			ProductDAO.insert(product);
		} catch (SQLException e) {
			LOG.info("Exception occured in the insert() of ProductServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<Product> getAll() {
		try {
			return ProductDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception occured in the getAll() of ProductServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ProductTO> getAllProductsByCartId(int cartId) {
		try {
			return ProductDAO.getAllProductsByCartId(cartId);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getAllProductsByCartId() of ProductServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ProductTO> getProductsHistoryByTimeAndUserId(int userId, long from, long till) {
		try {
			return ProductDAO.getProductsHistoryByTimeAndUserId(userId, from, till);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getProductsHistoryByTimeAndUserId() of ProductServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Product getOneById(int id) {
		try {
			return ProductDAO.getOneById(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getOneById() of ProductServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(Product product) {
		try {
			ProductDAO.updateById(product);
		} catch (SQLException e) {
			LOG.info("Exception occured in the update() of ProductServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Product product) {
		try {
			ProductDAO.delete(product);
		} catch (SQLException e) {
			LOG.info("Exception occured in the delete() of ProductServiceImpl.class");
			e.printStackTrace();
		}
	}

}
