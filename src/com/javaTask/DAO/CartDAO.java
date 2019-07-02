package com.javaTask.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.ConnectionAndStatementFactory;
import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;

public class CartDAO {

	private final static Logger LOG = Logger.getLogger(CartDAO.class.getName());

	public static void insert(Cart cart) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("inserting into CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String sql = "INSERT INTO CART (userid, status, timestamp) VALUES ( " + cart.getUserId() + " , '"
					+ cart.getStatus() + "', " + cart.getTime() + ")";

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

	public static List<Cart> getAll() throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<Cart> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM CART";

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createCart(resultSet));
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

	public static void updateById(Cart cart) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("updating CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "UPDATE CART SET userid = " + cart.getUserId() + ", status = '" + cart.getStatus()
					+ "', timestamp = " + cart.getTime() + " WHERE id = " + cart.getId();

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

	public static void delete(Cart cart) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("deleting from CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "DELETE FROM CART WHERE id = " + cart.getId();

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

	public static Cart getOneById(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		Cart cart = new Cart();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("seaching by id through CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM CART WHERE id = " + id + "LIMIT 1";

			ResultSet resultSet = statement.executeQuery(SQL);

			if (resultSet.next())
				cart = createCart(resultSet);
			LOG.info("seaching complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return cart;
	}

	public static List<Cart> getCartsByTime(long from, long till) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<Cart> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM CART WHERE timestamp BETWEEN " + from + " AND " + till;

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading complete");

			while (resultSet.next()) {
				try {
					result.add(createCart(resultSet));
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

	public static List<Cart> getCartsByUserId(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<Cart> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM CART WHERE userId = " + id;

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading complete");

			while (resultSet.next()) {
				try {
					result.add(createCart(resultSet));
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
	
	public static Cart getCartByUserIdAndOpen(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		Cart cart = new Cart();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("seaching by id through CART table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM CART WHERE userid = " + id + "AND status LIKE 'OPEN' LIMIT 1";

			ResultSet resultSet = statement.executeQuery(SQL);

			if (resultSet.next())
				cart = createCart(resultSet);
			LOG.info("seaching complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return cart;

	}
	
	private static Cart createCart(ResultSet resultSet) throws SQLException {
		Cart cart = new Cart();

		cart.setId(resultSet.getInt("id"));
		cart.setUserId(resultSet.getInt("userid"));
		cart.setStatus(Status.valueOf(resultSet.getString("status")));
		cart.setTime(resultSet.getLong("timestamp"));

		return cart;
	}
}
