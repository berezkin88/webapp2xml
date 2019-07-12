package main.java.com.javaTask.DAO;

import java.io.File;
import java.util.logging.Logger;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
	private static SessionFactory sessionFactory = buildSessionFactory();
	private static Logger LOG = Logger.getLogger(HibernateFactory.class.getName());

	private static SessionFactory buildSessionFactory() {
		if (sessionFactory == null) {
				return new Configuration().configure().buildSessionFactory();
		}
		
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		getSessionFactory().close();
	}
}
