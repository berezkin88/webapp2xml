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

import com.javaTask.DAO.connection.ConnectionAndStatementFactory;
import com.javaTask.model.Order;

class OrderDAOTest {

	private static final Logger LOG = Logger.getLogger(OrderDAOTest.class.getName());

	@BeforeEach
	public void dropAndRecreate() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		String drop = "drop table ORDERENTITY";
		String create = "create table ORDERENTITY (\r\n" + "	id smallserial not null primary key,\r\n"
				+ "	productId int not null,\r\n" + "	quantity int not null,\r\n" + "	cartId int not null\r\n" + ")";

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			LOG.info("dropping ORDERENTITY table...");
			statement.execute(drop);
			LOG.info("recreating ORDERENTITY table...");
			statement.execute(create);
		} catch (SQLException e) {
			LOG.info("Exception thrown in dropAndRecreate() method in OrderDAOTest.class");
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
	void testInsertOrder() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		Order order = new Order();
		Order checkOrder = new Order();

		order.setProductId(11);
		order.setQuantity(1);
		order.setCartId(111);

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			LOG.info("inserting test order into ORDERENTITY table...");
			OrderDAO.insert(order);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test order in testInsertOrder() method in OrderDAOTest.class");
			e.printStackTrace();
		}

		String selectAll = "select * from ORDERENTITY";

		try {
			LOG.info("reading from ORDERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkOrder.setProductId(rs.getInt("productid"));
				checkOrder.setQuantity(rs.getInt("quantity"));
				checkOrder.setCartId(rs.getInt("cartid"));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testInsertOrder() method in OrderDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		assertEquals(order.getProductId(), checkOrder.getProductId());
		assertEquals(order.getQuantity(), checkOrder.getQuantity());
		assertEquals(order.getCartId(), checkOrder.getCartId());
	}

	@Test
	void testGetAll() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		String query1 = "insert into ORDERENTITY(productid, quantity, cartid) values (11, 1, 111);";
		String query2 = "insert into ORDERENTITY(productid, quantity, cartid) values (22, 2, 222);";
		String query3 = "insert into ORDERENTITY(productid, quantity, cartid) values (33, 3, 333);";

		List<Order> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to ORDERENTITY table...");
			statement.executeBatch();

			testPool = OrderDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetAll() method in OrderDAOTest.class");
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
	void testGetOrdersByCartId() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		String query1 = "insert into ORDERENTITY(productid, quantity, cartid) values (11, 1, 111);";
		String query2 = "insert into ORDERENTITY(productid, quantity, cartid) values (22, 2, 222);";
		String query3 = "insert into ORDERENTITY(productid, quantity, cartid) values (33, 3, 111);";

		List<Order> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to ORDERENTITY table...");
			statement.executeBatch();

			testPool = OrderDAO.getOrdersByCartId(111);
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetOrdersByCartId() method in OrderDAOTest.class");
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
	void testUpdateById() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		Order order = new Order();
		Order checkOrder = new Order();

		order.setProductId(11);
		order.setQuantity(1);
		order.setCartId(111);

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			LOG.info("inserting test order into ORDERENTITY table...");
			OrderDAO.insert(order);

			order.setId(1);
			order.setQuantity(3);

			LOG.info("updating test order in ORDERENTITY table...");
			OrderDAO.updateById(order);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test order in testUpdateById() method in OrderDAOTest.class");
			e.printStackTrace();
		}

		String selectAll = "select * from ORDERENTITY";

		try {
			LOG.info("reading from ORDERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next()) {
				checkOrder.setQuantity(rs.getInt("quantity"));
			}

		} catch (SQLException e) {
			LOG.info("Exception thrown in testUpdateById() method in OrderDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		assertEquals(3, checkOrder.getQuantity());
	}

	@Test
	void testDelete() throws SQLException {
		Connection connection = null;
		Statement statement = null;

		Order order = new Order();

		order.setId(1);
		order.setProductId(11);
		order.setQuantity(1);
		order.setCartId(111);

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);

			LOG.info("inserting test order into ORDERENTITY table...");
			OrderDAO.insert(order);

			LOG.info("deleting test order in ORDERENTITY table...");
			OrderDAO.delete(order);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test order in testDelete() method in OrderDAOTest.class");
			e.printStackTrace();
		}

		String selectAll = "select * from ORDERENTITY";

		try {
			LOG.info("reading from ORDERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			assertFalse(rs.next());

		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in OrderDAOTest.class");
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
		Order order = new Order();
		Order checkOrder = null;

		order.setId(1);
		order.setProductId(11);
		order.setQuantity(1);
		order.setCartId(111);

		try {
			LOG.info("inserting test order into ORDERENTITY table...");
			OrderDAO.insert(order);

			LOG.info("getting test order in ORDERENTITY table...");
			checkOrder = OrderDAO.getOneById(1);
		} catch (SQLException e) {
			LOG.info("Exception thrown while inserting test order in testGetOneById() method in OrderDAOTest.class");
			e.printStackTrace();
		} 

		assertEquals(order.getProductId(), checkOrder.getProductId());
		assertEquals(order.getQuantity(), checkOrder.getQuantity());
		assertEquals(order.getCartId(), checkOrder.getCartId());
	}
}
