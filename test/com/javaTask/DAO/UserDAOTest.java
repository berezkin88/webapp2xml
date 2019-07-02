package com.javaTask.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javaTask.DAO.ConnectionAndStatementFactory;
import com.javaTask.model.User;

class UserDAOTest {
	private static final Logger LOG = Logger.getLogger(UserDAOTest.class.getName());
	
	@BeforeEach
	public void dropAndRecreate() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		String drop = "drop table USERENTITY";
		String create = "create table USERENTITY (\r\n" + "	id smallserial not null primary key,\r\n"
				+ "	username varchar(255) not null,\r\n" + "	password varchar(255) not null\r\n" + ")";

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("dropping USERENTITY table...");
			statement.execute(drop);
			LOG.info("recreating USERENTITY table...");
			statement.execute(create);
		} catch (SQLException e) {
			LOG.info("Exception thrown in dropAndRecreate() method in UserDAOTest.class");
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
	void testInsertUser() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		User user = new User();
		User checkUser = new User();

		user.setUsername("user1");
		user.setPassword("pass11");

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			UserDAO.insert(user);
		} catch (Exception e) {
			LOG.info("Exception thrown while inserting test user in testInsertUser() method in UserDAOTest.class");
			e.printStackTrace();
		}

		String selectAll = "select * from USERENTITY";

		try {
			LOG.info("reading from USERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next())
				checkUser.setUsername(rs.getString("username"));

		} catch (SQLException e) {
			LOG.info("Exception thrown in testInsertUser() method in UserDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		assertEquals("user1", checkUser.getUsername());
	}

	@Test
	void testGetAll() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		String query1 = "insert into userentity(username, password) values ('user1', 'pass11');";
		String query2 = "insert into userentity(username, password) values ('user2', 'pass22');";
		String query3 = "insert into userentity(username, password) values ('user3', 'pass33');";

		List<User> testPool = null;

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			statement.addBatch(query1);
			statement.addBatch(query2);
			statement.addBatch(query3);

			LOG.info("adding 3 records to USERENTITY table...");
			statement.executeBatch();

			testPool = UserDAO.getAll();
		} catch (SQLException e) {
			LOG.info("Exception thrown in testGetAll() method in UserDAOTest.class");
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
		
		User user = new User();
		User checkUser = new User();

		user.setUsername("user1");
		user.setPassword("pass11");

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test user into USERENTITY table...");
			UserDAO.insert(user);

			user.setId(1);
			user.setPassword("pass22");

			LOG.info("updating test user in USERENTITY table...");
			UserDAO.updateById(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String selectAll = "select * from USERENTITY";

		try {
			LOG.info("reading from USERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			if (rs.next())
				checkUser.setPassword(rs.getString("password"));

		} catch (SQLException e) {
			LOG.info("Exception thrown in testUpdateById() method in UserDAOTest.class");
			e.printStackTrace();
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

		assertEquals("pass22", checkUser.getPassword());
	}

	@Test
	void testDelete() throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		User user = new User();

		user.setId(1);
		user.setUsername("user1");
		user.setPassword("pass11");

		try {
			connection = ConnectionAndStatementFactory.connecting();
			statement = ConnectionAndStatementFactory.createStatement(connection);
			
			LOG.info("inserting test user into USERENTITY table...");
			UserDAO.insert(user);

			LOG.info("deleting user from USERENTITY table...");
			UserDAO.delete(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String selectAll = "select * from USERENTITY";

		try {
			LOG.info("reading from USERENTITY table...");
			ResultSet rs = statement.executeQuery(selectAll);

			assertFalse(rs.next());

		} catch (SQLException e) {
			LOG.info("Exception thrown in testDelete() method in UserDAOTest.class");
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
		User user = new User();
		User checkUser = null;

		user.setUsername("user1");
		user.setPassword("pass11");

		try {
			LOG.info("inserting test user into USERENTITY table...");
			UserDAO.insert(user);

			LOG.info("getting test user by his id into USERENTITY table...");
			checkUser = UserDAO.getOneById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(checkUser != null);
		assertEquals("user1", checkUser.getUsername());
		assertEquals("pass11", checkUser.getPassword());
	}
	
	@Test
	void testGetOneByUsername() {
		User user = new User();
		User checkUser = null;

		user.setUsername("user1");
		user.setPassword("pass11");

		try {
			LOG.info("inserting test user into USERENTITY table...");
			UserDAO.insert(user);

			LOG.info("getting test user by his id into USERENTITY table...");
			checkUser = UserDAO.getOneByUsername(user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(checkUser != null);
		assertEquals("user1", checkUser.getUsername());
		assertEquals("pass11", checkUser.getPassword());
	}
}
