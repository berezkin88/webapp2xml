package main.java.com.javaTask.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.javaTask.service.ProductService;
import main.java.com.javaTask.service.impl.ProductServiceImpl;
import main.java.com.javaTask.utilities.ProductTO;

/**
 * 
 * @author Aleksandr Beryozkin
 *
 * This servlet is summarizing the purchase, can remove items and send call to close the cart, accepting GET calls
 */

@WebServlet(name="cartservlet", urlPatterns="/cart")
public class CartServlet extends HttpServlet {
	
	private static final Logger LOG = Logger.getLogger(CartServlet.class.getName());
	private ProductService productService = new ProductServiceImpl(); 

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userid");
		String cartId = req.getParameter("cartid");
		List<ProductTO> results = productService.getAllProductsByCartId(Integer.valueOf(cartId));
		
		req.setAttribute("results", results);
		req.setAttribute("cartId", cartId);
		req.setAttribute("userId", userId);
		LOG.info("Cart ID is " + cartId);
		
		if (userId == null || cartId == null || results == null) {
			resp.setStatus(400);
		}
		
		RequestDispatcher view = req.getRequestDispatcher("jsp/cart.jsp");
		view.forward(req, resp);
	}
}
