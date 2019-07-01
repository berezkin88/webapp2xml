package com.javaTask.DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javaTask.DAO.connection.ConnectionAndStatementFactory;
import com.javaTask.model.Order;
import com.javaTask.model.Product;
import com.javaTask.utilities.ProductTO;

class ProductDAOTest {
	
	private static final Logger LOG = Logger.getLogger(ProductDAOTest.class.getName());
	
	@BeforeEach
	public void dropAndRecreate() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		String drop = "drop table PRODUCT";
		String create = "create table PRODUCT (\r\n" + 
				"	id smallserial not null primary key,\r\n" + 
				"	title varchar(255) not null,\r\n" + 
				"	price real not null,\r\n" + 
				"	description varchar\r\n" + 
				");";

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("dropping PRODUCT table...");
			statement.execute(drop);
			LOG.info("recreating PRODUCT table...");
			statement.execute(create);
		} catch (SQLException e) {
			LOG.info("Exception thrown in dropAndRecreate() method in ProductDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	void testInsertProduct() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		Product product = new Product();
		Product checkProduct = new Product();
		
		product.setTitle("product1");
		product.setPrice(10.1);
		product.setDescription("about product1");
		
		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test product into PRODUCT table...");
			ProductDAO.insert(product);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test product in testInsertProduct() method in ProductDAOTest.class");
			e.printStackTrace();
		}
		
		String selectAll = "select * from PRODUCT";
 
		try {
			LOG.info("reading from PRODUCT table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkProduct.setTitle(rs.getString("title"));
				checkProduct.setPrice(rs.getDouble("price"));
				checkProduct.setDescription(rs.getString("description"));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testInsertUser() method in ProductDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		assertEquals(product.getTitle(), checkProduct.getTitle());
		assertEquals(product.getPrice(), checkProduct.getPrice(), 0.1);
		assertEquals(product.getDescription(), checkProduct.getDescription());
	}
	
	@Test
	void testGetAll() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		String query1 = "insert into PRODUCT(title, price, description) values ('product1', 10.1, 'about product1')";
		String query2 = "insert into PRODUCT(title, price, description) values ('product2', 20.2, 'about product2');";
		String query3 = "insert into PRODUCT(title, price, description) values ('product3', 30.3, 'about product3');";

		List<Product> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to CART table...");
			statement.executeBatch();

			testPool = ProductDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetAll() method in ProductDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		assertTrue(!testPool.isEmpty());
		assertEquals(3, testPool.size());
	}
	
	@Test
	void testUpdateById() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		Product product = new Product();
		Product checkProduct = new Product();
		
		product.setTitle("product1");
		product.setPrice(10.1);
		product.setDescription("about product1");
		
		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test product into PRODUCT table...");
			ProductDAO.insert(product);
			
			product.setId(1);
			product.setPrice(20.2);
			
			LOG.info("updating test product in PRODUCT table...");
			ProductDAO.updateById(product);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test product in testUpdateById() method in ProductDAOTest.class");
			e.printStackTrace();
		}
		
		String selectAll = "select * from PRODUCT";

		try {
			LOG.info("reading from PRODUCT table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkProduct.setPrice(rs.getDouble("price"));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testUpdateById() method in ProductDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		assertEquals(20.2, checkProduct.getPrice(), 0.1);
	}
	
	@Test
	void testDelete() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		Product product = new Product();
		
		product.setId(1);
		product.setTitle("product1");
		product.setPrice(10.1);
		product.setDescription("about product1");
		
		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test product into PRODUCT table...");
			ProductDAO.insert(product);
			
			LOG.info("deleting test product from PRODUCT table...");
			ProductDAO.delete(product);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test product in testDelete() method in ProductDAOTest.class");
			e.printStackTrace();
		}
		
		String selectAll = "select * from PRODUCT";

		try {
			LOG.info("reading from PRODUCT table...");
			ResultSet rs = statement.executeQuery(selectAll);

			assertFalse(rs.next());

		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in ProductDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	@Test
	void testGetOneById() {
		Product product = new Product();
		Product checkProduct = new Product();
		
		product.setTitle("product1");
		product.setPrice(10.1);
		product.setDescription("about product1");
		
		try {
			LOG.info("inserting test product into PRODUCT table...");
			ProductDAO.insert(product);
			
			LOG.info("getting test product from PRODUCT table...");
			checkProduct = ProductDAO.getOneById(1);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test product in testInsertProduct() method in ProductDAOTest.class");
			e.printStackTrace();
		}
		
		assertEquals(product.getTitle(), checkProduct.getTitle());
		assertEquals(product.getPrice(), checkProduct.getPrice(), 0.1);
		assertEquals(product.getDescription(), checkProduct.getDescription());
	}
	
	@Test
	void testGetAllProductsByCartId() {
		Product product = new Product();
		Order order = new Order();
		List<ProductTO> checkPool = null;
		
		product.setTitle("product1");
		product.setPrice(10.1);
		product.setDescription("about product1");
		
		order.setCartId(111);
		order.setProductId(1);
		order.setQuantity(1111);
		
		try {
			LOG.info("inserting test product into PRODUCT and ORDERENTITY tables...");
			ProductDAO.insert(product);
			OrderDAO.insert(order);
			
			LOG.info("getting test product from PRODUCT and ORDERENTITY table...");
			checkPool = ProductDAO.getAllProductsByCartId(111);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test product in testGetAllProductsByCartId() method in ProductDAOTest.class");
			e.printStackTrace();
		}
		
		assertEquals(1, checkPool.size());
		assertEquals(product.getTitle(), checkPool.get(0).getTitle());
		assertEquals(product.getPrice(), checkPool.get(0).getPrice(), 0.1);
		assertEquals(order.getQuantity(), checkPool.get(0).getQuantity());
	}
}
