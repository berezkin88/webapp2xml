package com.javaTask.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.DAO.ConnectionAndStatementFactory;
import com.javaTask.model.User;

public class UserDAO {

	private final static Logger LOG = Logger.getLogger(UserDAO.class.getName());

	public static void insert(User user) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("inserting into USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String sql = "INSERT INTO userentity (username, password) VALUES ( '" + user.getUsername() + "' , '"
					+ user.getPassword() + "')";

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

	public static List<User> getAll() throws SQLException {
		Statement statement = null;
		Connection connection = null;
		List<User> result = new ArrayList<>();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("reading from USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM USERENTITY";

			ResultSet resultSet = statement.executeQuery(SQL);
			LOG.info("reading completed");

			while (resultSet.next()) {
				try {
					result.add(createUser(resultSet));
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

	public static void updateById(User user) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("updating USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "UPDATE USERENTITY SET username = '" + user.getUsername() + "', password = '"
					+ user.getPassword() + "' WHERE id = " + user.getId();

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

	public static void delete(User user) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		
		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("deleting from USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "DELETE FROM USERENTITY WHERE id = " + user.getId();

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

	public static User getOneById(int id) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		User user = new User();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("seaching by id through USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM USERENTITY WHERE id = " + id + "LIMIT 1";

			ResultSet resultSet = statement.executeQuery(SQL);

			if (resultSet.next())
				user = createUser(resultSet);

			LOG.info("seaching complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return user;
	}

	public static User getOneByUsername(String username) throws SQLException {
		Statement statement = null;
		Connection connection = null;
		User user = new User();

		try {
			connection = ConnectionAndStatementFactory.connecting();

			LOG.info("seaching by username through USERENTITY table...");
			statement = ConnectionAndStatementFactory.createStatement(connection);

			String SQL = "SELECT * FROM USERENTITY WHERE username LIKE '" + username + "' LIMIT 1";

			ResultSet resultSet = statement.executeQuery(SQL);

			if (resultSet.next())
				user = createUser(resultSet);

			LOG.info("seaching complete");
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		return user;
	}

	private static User createUser(ResultSet resultSet) throws SQLException {
		User user = new User();

		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));

		return user;
	}
}
