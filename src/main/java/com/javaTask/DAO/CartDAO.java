package main.java.com.javaTask.DAO;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import main.java.com.javaTask.model.Cart;
import main.java.com.javaTask.model.enums.Status;

public class CartDAO {

	private final static Logger LOG = Logger.getLogger(CartDAO.class.getName());
	private static SessionFactory sessionFactory = HibernateFactory.getSessionFactory();

	public static void insertCart(Cart cart) {
		LOG.info("saving cart...");
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.save(cart);
		session.getTransaction().commit();
		session.close();
		LOG.info("saving cart complete");
	}

	public static List<Cart> getAll() {
		LOG.info("getting all carts...");
		Session session = sessionFactory.openSession();

		String sql = "from Cart c";

		List<Cart> results = session.createQuery(sql).getResultList();
		LOG.info("retrieving all carts complete");
		return results;
	}

	public static void updateCart(Cart cart) {
		LOG.info("updating cart info...");

		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.update(cart);
		session.getTransaction().commit();
		session.close();
		LOG.info("updating cart complete");
	}

	public static void deleteCart(Cart cart) {
		LOG.info("deleting cart...");

		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		session.delete(cart);
		session.getTransaction().commit();
		session.close();

		LOG.info("deleting cart complete");
	}

	public static Cart getOneById(int id) {
		LOG.info("detting cart by  id...");
		Session session = sessionFactory.openSession();
		session.getTransaction().begin();
		Cart cartFromDB = (Cart) session.find(Cart.class, id);
		session.getTransaction().commit();
		session.close();
		LOG.info("cart found");

		return cartFromDB;
	}

	public static List<Cart> getCartsByTime(long from, long till) {
		List<Cart> result = null;

		LOG.info("getting all carts by time range...");
		Session session = sessionFactory.openSession();

		result = session.createQuery("from Cart c where c.time between :from and :till").setParameter("from", from)
				.setParameter("till", till).getResultList();

		LOG.info("retrieving all carts complete");
		return result;
	}

	public static List<Cart> getCartsByUserId(int id) {
		List<Cart> result = null;

		LOG.info("getting all carts by userid...");
		Session session = sessionFactory.openSession();

		result = session.createQuery("from Cart c where c.userId = :id").setParameter("id", id).getResultList();

		LOG.info("retrieving all carts complete");
		return result;
	}
	
	public static Cart getCartByUserIdAndOpen(int id) {
		Cart resultCart = null;

		LOG.info("getting all carts by userid and open...");
		Session session = sessionFactory.openSession();

		resultCart = (Cart) session.createQuery("from Cart c where c.userId = :id and c.status = :status").setParameter("id", id).setParameter("status", Status.OPEN).uniqueResult();

		LOG.info("retrieving all carts complete");
		return resultCart;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		CartDAO.sessionFactory = sessionFactory;
	}
}
