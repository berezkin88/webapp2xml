package main.java.com.javaTask.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.javaTask.service.CartService;
import main.java.com.javaTask.service.impl.CartServiceImpl;

/**
 * Servlet implementation class CheckoutServlet
 */
//@WebServlet(name="checkoutservlet", urlPatterns="/checkout")
public class CheckoutServlet extends HttpServlet {
	
	private static final Logger LOG = Logger.getLogger(CheckoutServlet.class.getName());
	private static CartService cartService = new CartServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userid");
		String cartId = req.getParameter("cartid");
		int id = Integer.valueOf(cartId);
		
		cartService.checkout(id);
		LOG.info("Cart #" + id + " is closed");
		
		resp.setStatus(201);
		
		req.setAttribute("cartId", cartId);
		req.setAttribute("userId", userId);
		
		if (userId == null || cartId == null) {
			resp.setStatus(400);
		}
		
		RequestDispatcher view = req.getRequestDispatcher("jsp/success.jsp");
		view.forward(req, resp);
	}
}
