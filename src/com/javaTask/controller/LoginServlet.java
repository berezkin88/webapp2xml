package com.javaTask.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaTask.service.impl.UserServiceImpl;
import com.javaTask.model.User;
import com.javaTask.model.Product;
import com.javaTask.service.ProductService;
import com.javaTask.service.impl.ProductServiceImpl;
import com.javaTask.service.UserService;

/**
 * 
 * @author Aleksandr Beryozkin
 *
 * This servlet is creating new user, pulling up all products and sending shop.jsp in response
 */

@WebServlet(name = "loginservlet", urlPatterns = "/loginservlet")
public class LoginServlet extends HttpServlet{
	
	private static UserService us = new UserServiceImpl();
	private static ProductService ps = new ProductServiceImpl();
	private static List<Product> products;

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		
		user.setUsername(req.getParameter("username"));
		user.setPassword(req.getParameter("password"));
		
		us.insert(user);
		
		products = ps.getAll();
		
		req.setAttribute("products", products);
		
		RequestDispatcher view = req.getRequestDispatcher("jsp/shop.jsp");
		view.forward(req, resp);
	}
}
