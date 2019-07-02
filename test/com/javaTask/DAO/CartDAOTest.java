package com.javaTask.DAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javaTask.DAO.ConnectionAndStatementFactory;
import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;

class CartDAOTest {
	private static final Logger LOG = Logger.getLogger(CartDAOTest.class.getName());
	
	@BeforeEach
	public void dropAndRecreate() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		String drop = "drop table CART";
		String create = "create table CART (\r\n" + "	id smallserial not null primary key,\r\n"
				+ "	userId int not null,\r\n" + "	status varchar(255),\r\n" + "	timestamp bigint\r\n" + ")";

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("dropping CART table...");
			statement.execute(drop);
			LOG.info("recreating CART table...");
			statement.execute(create);
		} catch (SQLException e) {
			LOG.info("Exception thrown in dropAndRecreate() method in CartDAOTest.class");
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
	void testInsertCart() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		Cart cart = new Cart();
		Cart checkCart = new Cart();

		cart.setUserId(111);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test cart into CART table...");
			CartDAO.insert(cart);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test cart in testInsertCart() method in CartDAOTest.class");
			e.printStackTrace();
		}
		
		String selectAll = "select * from CART";

		try {
			LOG.info("reading from CART table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkCart.setUserId(rs.getInt("userid"));
				checkCart.setStatus(Status.valueOf(rs.getString("status")));
				checkCart.setTime(rs.getLong("timestamp"));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testInsertUser() method in CartDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		assertEquals(cart.getUserId(), checkCart.getUserId());
		assertEquals(cart.getStatus(), checkCart.getStatus());
		assertEquals(cart.getTime(), checkCart.getTime());
	}
	
	@Test 
	void testGetAll() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		
		String query1 = "insert into cart(userid, status, timestamp) values (111, 'OPEN', 1560290002235);";
		String query2 = "insert into cart(userid, status, timestamp) values (222, 'OPEN', 1560290002245);";
		String query3 = "insert into cart(userid, status, timestamp) values (333, 'OPEN', 1560290002255);";

		List<Cart> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to CART table...");
			statement.executeBatch();

			testPool = CartDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetAll() method in CartDAOTest.class");
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
	void testUpdateById()  throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		Cart cart = new Cart();
		Cart checkCart = new Cart();

		cart.setUserId(111);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());
		
		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test cart into CART table...");
			CartDAO.insert(cart);
			
			cart.setId(1);
			cart.setStatus(Status.CLOSED);
			
			LOG.info("updating test cart in CART table...");
			CartDAO.updateById(cart);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test cart in testUpdateById() method in CartDAOTest.class");
			e.printStackTrace();
		}
		
		String selectAll = "select * from CART";

		try {
			LOG.info("reading from CART table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkCart.setStatus(Status.valueOf(rs.getString("status")));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testUpdateById() method in CartDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
		
		assertEquals(Status.CLOSED, checkCart.getStatus());
	}
	
	@Test
	void testDelete() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		
		Cart cart = new Cart();

		cart.setId(1);
		cart.setUserId(111);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test cart into CART table...");
			CartDAO.insert(cart);
			
			LOG.info("deleting test cart from CART table...");
			CartDAO.delete(cart);
		} catch (Exception e) {
			LOG.info("Exception thrown while inserting test cart in testDelete() method in CartDAOTest.class");
			e.printStackTrace();
		}

		String selectAll = "select * from CART";

		try {
			LOG.info("reading from CART table...");
			ResultSet rs = statement.executeQuery(selectAll);

			assertFalse(rs.next());

		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in CartDAOTest.class");
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
		Cart cart = new Cart();
		Cart checkCart = null;

		cart.setUserId(111);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());
		
		try {
			LOG.info("inserting test cart into CART table...");
			CartDAO.insert(cart);
			
			LOG.info("deleting test cart from CART table...");
			checkCart = CartDAO.getOneById(1);
		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in CartDAOTest.class");
			e.printStackTrace();
		} 
		
		assertTrue(checkCart != null);
		assertEquals(111, checkCart.getUserId());
		assertEquals(Status.OPEN, checkCart.getStatus());
	}
	
	@Test
	void testGetCartByUserIdAndOpen() {
		Cart cart = new Cart();
		Cart checkCart = null;

		cart.setUserId(111);
		cart.setStatus(Status.OPEN);
		cart.setTime(System.currentTimeMillis());
		
		try {
			LOG.info("inserting test cart into CART table...");
			CartDAO.insert(cart);
			
			LOG.info("deleting test cart from CART table...");
			checkCart = CartDAO.getCartByUserIdAndOpen(111);
		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in CartDAOTest.class");
			e.printStackTrace();
		} 
		
		assertTrue(checkCart != null);
		assertEquals(111, checkCart.getUserId());
		assertEquals(Status.OPEN, checkCart.getStatus());
	}
	
	@Test
	void testGetCartsByTime() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		
		String query1 = "insert into cart(userid, status, timestamp) values (111, 'OPEN', 1550290002235);";
		String query2 = "insert into cart(userid, status, timestamp) values (222, 'OPEN', 1560290002245);";
		String query3 = "insert into cart(userid, status, timestamp) values (333, 'OPEN', 1570290002255);";

		List<Cart> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to CART table...");
			statement.executeBatch();

			testPool = CartDAO.getCartsByTime(1560000000000l, 1580000000000l);
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetCartsByTime() method in CartDAOTest.class");
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
		assertEquals(2, testPool.size());
	}
	
	@Test
	void testGetCartsByUserId() throws SQLException{
		Connection connection = null;
		Statement statement = null;
		
		String query1 = "insert into cart(userid, status, timestamp) values (111, 'OPEN', 1550290002235);";
		String query2 = "insert into cart(userid, status, timestamp) values (222, 'OPEN', 1560290002245);";
		String query3 = "insert into cart(userid, status, timestamp) values (111, 'OPEN', 1570290002255);";

		List<Cart> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to CART table...");
			statement.executeBatch();

			testPool = CartDAO.getCartsByUserId(111);
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetCartsByUserId() method in CartDAOTest.class");
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
		assertEquals(2, testPool.size());
	}
}
