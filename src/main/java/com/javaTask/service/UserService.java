package main.java.com.javaTask.service;

import java.util.List;

import main.java.com.javaTask.model.User;

public interface UserService {

	public void insert(User user);
	public List<User> getAll();
	public User getOneById(int id);
	public User getOneByUsernamed(String username);
	public void update(User user);
	public void delete(User user);
}
