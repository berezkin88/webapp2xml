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

import main.java.com.javaTask.service.impl.UserServiceImpl;
import main.java.com.javaTask.model.User;
import main.java.com.javaTask.model.enums.Status;
import main.java.com.javaTask.model.Cart;
import main.java.com.javaTask.model.Product;
import main.java.com.javaTask.service.CartService;
import main.java.com.javaTask.service.ProductService;
import main.java.com.javaTask.service.impl.CartServiceImpl;
import main.java.com.javaTask.service.impl.ProductServiceImpl;
import main.java.com.javaTask.service.UserService;

/**
 * 
 * @author Aleksandr Beryozkin
 *
 *         This servlet is creating a new user and a new cart, pulling up all
 *         products and sending shop.jsp in response
 */

@WebServlet(name = "loginservlet", urlPatterns = "/loginservlet")
public class LoginServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

	private static UserService userService = new UserServiceImpl();
	private static ProductService productService = new ProductServiceImpl();
	private static CartService cartService = new CartServiceImpl();
	private static List<Product> products;
	private static Cart cart = null;
	private static User user = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		createUserAndCart(req.getParameter("username"), req.getParameter("password"));

		products = productService.getAll();

		req.setAttribute("products", products);
		req.setAttribute("userId", user.getId());
		req.setAttribute("cartId", cart.getId());

		RequestDispatcher view = req.getRequestDispatcher("jsp/shop.jsp");
		view.forward(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userid");
		products = productService.getAll();

		if (userId.isEmpty()) {
			RequestDispatcher view = req.getRequestDispatcher("jsp/index.jsp");
			view.forward(req, resp);
		}
		
		cart = createNewCart(Integer.valueOf(userId));

		req.setAttribute("products", products);
		req.setAttribute("userId", userId);
		req.setAttribute("cartId", cart.getId());

		RequestDispatcher view = req.getRequestDispatcher("jsp/shop.jsp");
		view.forward(req, resp);
	}

	private void createUserAndCart(String username, String password) {
		LOG.info("Creating user... ");

		if (user == null)
			user = new User();

		user.setUsername(username);
		user.setPassword(password);

		userService.insert(user);

		user = userService.getOneByUsernamed(user.getUsername());
		LOG.info(user.toString());

		if (cart == null)
			cart = new Cart();

		LOG.info("initializing cart...");
		cart.setUserId(user.getId());
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());

		cartService.createCart(cart);

		cart = cartService.getCartsByUserIdAndOpen(user.getId());

		LOG.info(cart.toString());
	}
	
	private Cart createNewCart(int userId) {
		Cart newCart = new Cart();
		
		LOG.info("initializing cart...");
		newCart.setUserId(user.getId());
		newCart.setStatus(Status.OPEN);
		newCart.setTime(System.currentTimeMillis());

		cartService.createCart(newCart);

		newCart = cartService.getCartsByUserIdAndOpen(userId);

		LOG.info("new cart created" + newCart.toString());
		
		return newCart;
	}

	public static Cart getCart() {
		return cart;
	}
}
