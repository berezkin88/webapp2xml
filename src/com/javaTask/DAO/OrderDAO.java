package com.javaTask.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.connection.ConnectionAndStatementFactory;
import com.javaTask.model.Order;

public class OrderDAO {

	private final static Logger LOG = Logger.getLogger(OrderDAO.class.getName());

	public static void insert(Order order) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("inserting into ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String sql = "INSERT INTO ORDERENTITY (productid, quantity, cartid) VALUES ( " + order.getProductId()
					+ " , " + order.getQuantity() + ", " + order.getCartId() + ")";

			statement.execute(sql);
			LOG.info("insertion complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static List<Order> getAll() throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<Order> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM ORDERENTITY";

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createOrder(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return result;
	}

	public static List<Order> getOrdersByCartId(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<Order> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM ORDERENTITY WHERE cartid = " + id;

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createOrder(resultSet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return result;
	}

	public static void updateById(Order order) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("updating ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "UPDATE ORDERENTITY SET productid = " + order.getProductId() + ", quantity = "
					+ order.getQuantity() + ", cartid = " + order.getCartId() + " WHERE id = " + order.getId();

			statement.execute(SQL);
			LOG.info("update complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static void delete(Order order) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("deleting from ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "DELETE FROM ORDERENTITY WHERE id = " + order.getId();

			statement.execute(SQL);
			LOG.info("deleting complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static Order getOneById(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		Order order = new Order();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("seaching by id through ORDERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM ORDERENTITY WHERE id = " + id + "LIMIT 1";

			ResultSet resultSet = statement.executeQuery(SQL);

			if (resultSet.next())
				order = createOrder(resultSet);
			LOG.info("seaching complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return order;
	}

	private static Order createOrder(ResultSet resultSet) throws SQLException {
		Order order = new Order();

		order.setId(resultSet.getInt("id"));
		order.setProductId(resultSet.getInt("productid"));
		order.setQuantity(resultSet.getInt("quantity"));
		order.setCartId(resultSet.getInt("cartid"));

		return order;
	}
}
