package main.java.com.javaTask.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.com.javaTask.model.Cart;
import main.java.com.javaTask.model.Order;
import main.java.com.javaTask.model.enums.Status;
import main.java.com.javaTask.service.CartService;
import main.java.com.javaTask.service.OrderService;
import main.java.com.javaTask.service.impl.CartServiceImpl;
import main.java.com.javaTask.service.impl.OrderServiceImpl;

/**
 * 
 * @author Aleksandr Beryozkin
 *
 *         This servlet purpose is adding new orders to open carts, accepting
 *         POST calls
 */

@WebServlet(name = "orderservlet", urlPatterns = "/orderservlet")
public class OrderServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(OrderServlet.class.getName());
	private static OrderService orderService = new OrderServiceImpl();
	private static CartService cartService = new CartServiceImpl();
	private static Cart cart = null;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userid");
		String cartId = req.getParameter("cartid");
		LOG.info("Cart ID is " + cartId);
		cart = validateCart(Integer.valueOf(cartId));
		Order order = new Order();

		order.setCartId(cart.getId());
		order.setProductId(Integer.valueOf(req.getParameter("productid")));
		order.setQuantity(Integer.valueOf(req.getParameter("quantity")));

		LOG.info("creating new order...");
		orderService.insert(order);

		resp.setStatus(201);

	}

	private static Cart validateCart(int id) {
		Cart cart = cartService.getOneById(id);

		if (cartService.validateCart(cart)) {
			return cart;
		} else {
			Cart newCart = new Cart();

			LOG.info("initializing new cart...");
			newCart.setUserId(cart.getUserId());
			newCart.setStatus(Status.OPEN);
			newCart.setTime(System.currentTimeMillis());

			cartService.createCart(newCart);
			newCart = cartService.getCartsByUserIdAndOpen(newCart.getUserId());

			LOG.info(newCart.toString());

			return newCart;
		}
	}

	public static Cart getCart() {
		return cart;
	}
}
