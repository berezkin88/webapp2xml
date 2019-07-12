package com.javaTask.DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.java.com.javaTask.DAO.CartDAO;
import main.java.com.javaTask.DAO.HibernateFactory;
import main.java.com.javaTask.model.Cart;
import main.java.com.javaTask.model.enums.Status;

class CartDAOTest {
	private static final Logger LOG = Logger.getLogger(CartDAOTest.class.getName());
	private static final SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
	private static Cart cart1 = null;
	private static Cart cart2 = null;
	private static Cart cart3 = null;

	@BeforeAll
	public static void dropAndRecreate() {
		cart1 = new Cart(111, Status.OPEN, 1550290002235l);
		cart2 = new Cart(222, Status.OPEN, 1560290002245l);
		cart3 = new Cart(111, Status.CLOSED, 1570290002235l);

		LOG.info("inserting test carts into CART table...");
		CartDAO.insertCart(cart1);
		CartDAO.insertCart(cart2);
		CartDAO.insertCart(cart3);
	}

	@Test
	void testInsertAndDeleteCart() {
		Session session = sessionFactory.openSession();

		Cart cart = new Cart();
		List<Cart> checkCart = new ArrayList();

		cart.setUserId(444);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());

		LOG.info("inserting test cart into CART table...");
		CartDAO.insertCart(cart);

		LOG.info("reading from CART table...");
		session.getTransaction().begin();
		checkCart = session.createQuery("from Cart c").getResultList();

		assertEquals(4, checkCart.size());
		assertEquals(cart.getUserId(), checkCart.get(3).getUserId());
		assertEquals(cart.getStatus(), checkCart.get(3).getStatus());
		assertEquals(cart.getTime(), checkCart.get(3).getTime());

		CartDAO.deleteCart(cart);

		LOG.info("reading from CART table...");
		checkCart = session.createQuery("from Cart c").getResultList();
		session.close();

		assertEquals(3, checkCart.size());
	}

	@Test
	void testGetAll() {
		LOG.info("getting all test carts from CART table...");
		List<Cart> testPool = null;

		testPool = CartDAO.getAll();

		assertTrue(!testPool.isEmpty());
		assertEquals(3, testPool.size());
	}

	@Test
	void testUpdateCart() {
		Session session = sessionFactory.openSession();

		Cart cart = new Cart();
		Cart checkCart = new Cart();

		cart.setUserId(444);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());

		LOG.info("inserting test cart into CART table...");
		CartDAO.insertCart(cart);

		cart.setStatus(Status.CLOSED);

		LOG.info("updating test cart in CART table...");
		CartDAO.updateCart(cart);

		LOG.info("reading from CART table...");
		session.getTransaction().begin();
		checkCart = (Cart) session.createQuery("from Cart c").getResultList().get(3);
		session.close();

		assertEquals(Status.CLOSED, checkCart.getStatus());

		CartDAO.deleteCart(cart);
	}

	@Test
	void testGetOneById() {
		Cart checkCart = null;

		checkCart = CartDAO.getOneById(1);

		assertTrue(checkCart != null);
		assertEquals(111, checkCart.getUserId());
		assertEquals(Status.OPEN, checkCart.getStatus());
	}

	@Test
	void testGetCartByUserIdAndOpen() {
		Cart checkCart = null;

		checkCart = CartDAO.getCartByUserIdAndOpen(111);

		assertTrue(checkCart != null);
		assertEquals(111, checkCart.getUserId());
		assertEquals(Status.OPEN, checkCart.getStatus());
	}

	@Test
	void testGetCartsByTime() {
		List<Cart> testPool = null;

		testPool = CartDAO.getCartsByTime(1560000000000l, 1580000000000l);

		assertTrue(!testPool.isEmpty());
		assertEquals(2, testPool.size());
	}

	@Test
	void testGetCartsByUserId() {
		List<Cart> testPool = null;

		testPool = CartDAO.getCartsByUserId(111);

		assertTrue(!testPool.isEmpty());
		assertEquals(2, testPool.size());
	}
}
