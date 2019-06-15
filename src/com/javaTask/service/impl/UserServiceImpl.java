package com.javaTask.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.UserDAO;
import com.javaTask.model.User;
import com.javaTask.service.UserService;

public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class.getName());

	@Override
	public void insert(User user) {
		try {
			UserDAO.insert(user);
		} catch (SQLException e) {
			LOG.info("Exception occured in the insert() of UserServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public List<User> getAll() {
		try {
			return UserDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception occured in the getAll() of UserServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getOneById(int id) {
		try {
			return UserDAO.getOneById(id);
		} catch (SQLException e) {
			LOG.info("Exception occured in the getOneById() of UserServiceImpl.class");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(User user) {
		try {
			UserDAO.updateById(user);
		} catch (SQLException e) {
			LOG.info("Exception occured in the update() of UserServiceImpl.class");
			e.printStackTrace();
		}
	}

	@Override
	public void delete(User user) {
		try {
			UserDAO.delete(user);
		} catch (SQLException e) {
			LOG.info("Exception occured in the delete() of UserServiceImpl.class");
			e.printStackTrace();
		}
	}

}
