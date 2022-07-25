package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.Order.OrderStatus;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;

public class SQLOrderDAO implements OrderDAO {
	private static final Logger logger = LogManager.getLogger(SQLOrderDAO.class);
	
	@Override
	public Order placeOrder(int userId, Car car, Date from, Date to, double price) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(SQLOrderQuery.PREPARE_ORDER, Statement.RETURN_GENERATED_KEYS);

			String status = checkPersonalData(userId, connection);

			int carId = SQLCarDAO.getCarId(car, from, to, connection);

			if (carId == 0) {
				logger.error(DAOUserException.MSG_CAR_ABSENT);
				throw new DAOUserException(DAOUserException.MSG_CAR_ABSENT);
			}

			preparedStatement.setInt(1, carId);
			preparedStatement.setInt(2, userId);
			preparedStatement.setDate(3, from);
			preparedStatement.setDate(4, to);
			preparedStatement.setDouble(5, price);
			preparedStatement.setString(6, status);

			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				connection.rollback();
				logger.error(DAOUserException.MSG_ORDER_ABSENT);
				throw new DAOUserException(DAOUserException.MSG_ORDER_ABSENT);
			}

			Order order = getOrder(resultSet.getInt(1), connection);

			if (order == null) {
				connection.rollback();
				logger.error(DAOUserException.MSG_ORDER_ABSENT);
				throw new DAOUserException(DAOUserException.MSG_ORDER_ABSENT);
			}
			
			connection.commit();

			return order;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}

	}
	
	@Override
	public Order createOrder(int userId, int orderId, PersonalData personalData) throws DAOException, DAOUserException {

		boolean success = DAOFactory.getInstance().getUserDAO().insertUserPersonalData(userId, personalData);

		if (!success) {
			logger.error(DAOUserException.MSG_ORDER_PERS_DATA);
			throw new DAOUserException(DAOUserException.MSG_ORDER_PERS_DATA);
		}

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
			autoUpdateOrderHistory(orderId, connection);
			
			preparedStatement.setString(1, OrderStatus.CREATED.name());
			preparedStatement.setInt(2, orderId);
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
	        preparedStatement.executeUpdate();
	        
	        Order order = getOrder(orderId, connection);
			
			if (order == null) {
				connection.rollback();
				logger.error(DAOUserException.MSG_ORDER_ABSENT);
				throw new DAOUserException(DAOUserException.MSG_ORDER_ABSENT);
			}
			
			connection.commit();
			
			return order;
	        
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}

	@Override
	public boolean cancelOrder(int orderId) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
			String orderStatus = checkStatus(orderId, connection);
        	if (!orderStatus.equals(OrderStatus.PREPARED)
        			|| !orderStatus.equals(OrderStatus.CREATED)
        			|| !orderStatus.equals(OrderStatus.PAID)
        			|| !orderStatus.equals(OrderStatus.APPROVED)) {
        		
        		logger.error(DAOUserException.MSG_ORDER_CANCEL_FAIL);
        		throw new DAOUserException(DAOUserException.MSG_ORDER_CANCEL_FAIL);
        	}
        	
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		refundPayment(orderId, connection);
        	}
			
        	autoUpdateOrderHistory(orderId, connection);
        	
        	preparedStatement.setString(1, OrderStatus.CANCELLED.name());
			preparedStatement.setInt(2, orderId);
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
			return true;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {

			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}

	@Override
	public boolean payOrder(int orderId) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
			String orderStatus = checkStatus(orderId, connection);
		
			if (orderStatus.equals(OrderStatus.PREPARED)) {
				return false;
			}
			
			else if (orderStatus.equals(OrderStatus.CREATED)) {
				createPayment(orderId, connection);
				autoUpdateOrderHistory(orderId, connection);
				
				preparedStatement.setString(1, OrderStatus.PAID.name());
				preparedStatement.setInt(2, orderId);
				
				logger.debug("SQL query to execute: " + preparedStatement.toString());
				
				preparedStatement.executeUpdate();
				
				connection.commit();
				
				return true;
				
			} else {
           		logger.error(orderStatus + DAOUserException.MSG_ORDER_PAID_FAIL);
        		throw new DAOUserException(orderStatus + DAOUserException.MSG_ORDER_PAID_FAIL);
			}

		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}

	@Override
	public List<Order> getUserOrders(int userId) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_USER_ORDERS_ID);
			preparedStatement.setInt(1, userId);
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			List<Order> orders = new ArrayList<>();
			
			int orderId;
			while (resultSet.next()) {
				orderId = resultSet.getInt(1);
				orders.add(getOrder(orderId, connection));
			}
			
			return orders;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}

	}
	
	@Override
	public List<Order> getOrders(String filter) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			if (filter.equals("all")) {
				preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_ORDERS_ID_ALL);
			}
			else if (filter.equals("new")) {
				preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_ORDERS_ID_FILTERED);
				preparedStatement.setString(1, OrderStatus.PREPARED.name()
											+ "," + OrderStatus.CREATED.name()
											+ "," + OrderStatus.PAID.name());
			}
			else if (filter.equals("handover_return")) {
				preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_ORDERS_ID_FILTERED);
				preparedStatement.setString(1, OrderStatus.APPROVED.name()
											+ "," + OrderStatus.IN_PROGRESS.name());
			}
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			List<Order> orders = new ArrayList<>();
			
			int orderId;
			while (resultSet.next()) {
				orderId = resultSet.getInt(1);
				orders.add(getOrder(orderId, connection));
			}
			
			return orders;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}

	}
	
	@Override
	public void approveOrder(int orderId) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
			String orderStatus = checkStatus(orderId, connection);
		
			if (!orderStatus.equals(OrderStatus.PAID.name())) {
           		logger.error(orderStatus + DAOUserException.MSG_ORDER_APPROVE_FAIL);
        		throw new DAOUserException(orderStatus + DAOUserException.MSG_ORDER_APPROVE_FAIL);
			}
			
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		refundPayment(orderId, connection);
        	}
        	
			autoUpdateOrderHistory(orderId, connection);
				
			preparedStatement.setString(1, OrderStatus.APPROVED.name());
			preparedStatement.setInt(2, orderId);
				
			logger.debug("SQL query to execute: " + preparedStatement.toString());
				
			preparedStatement.executeUpdate();
				
			connection.commit();

		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {
			
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}
		
	}

	@Override
	public void rejectOrder(int orderId) throws DAOException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		refundPayment(orderId, connection);
        	}
			
        	autoUpdateOrderHistory(orderId, connection);
        	
        	preparedStatement.setString(1, OrderStatus.REJECTED.name());
			preparedStatement.setInt(2, orderId);
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			preparedStatement.executeUpdate();

			connection.commit();
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
			
		} catch (SQLException e) {

			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					logger.error(e1);
					throw new DAOException(e1);
				}
			}
			
			logger.error(e);
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}
		
	}
	
	private static Order getOrder(int orderId, Connection connection) throws SQLException, DAOException {

		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_ORDER);
		preparedStatement.setInt(1, orderId);

		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.next()) {
        	return null;
        	
		} else {
			int userId = resultSet.getInt(1);
			String login = resultSet.getString(2);
			String email = resultSet.getString(3);
			boolean admin = resultSet.getBoolean(4);
			boolean active = resultSet.getBoolean(5);
	    	String manufacturer = resultSet.getString(6);
	    	String model = resultSet.getString(7);
	    	String body = resultSet.getString(8);
	    	String engine = resultSet.getString(9);
	    	String transmission = resultSet.getString(10);
	    	String drive = resultSet.getString(11);
	    	Date dateFrom = resultSet.getDate(12);
	    	Date dateTo = resultSet.getDate(13);
	    	Double amount = resultSet.getDouble(14);
	    	OrderStatus status = OrderStatus.valueOf(resultSet.getString(15));
	    	
			return new Order(orderId, new User(userId, login, email, admin, active) //+PersonalData
							, new Car(manufacturer, model, body, engine, transmission, drive)
							, dateFrom, dateTo, amount, status);
		}

	}
	
	protected static void autoUpdateOrderHistory(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.AUTO_UPDATE_ORDER_HISTORY);
		preparedStatement.setInt(1, orderId);
		preparedStatement.setInt(2, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
	}
	
	private static String checkPersonalData(int userId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLUserQuery.CHECK_PERSONAL_DATA);
		preparedStatement.setInt(1, userId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		String status;
		if (resultSet.next()) {
			status = OrderStatus.CREATED.name();
		} else {
			status = OrderStatus.PREPARED.name();
		}
		
		resultSet.close();
		preparedStatement.close();
		
		return status;
	}
	
	private static boolean checkPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.CHECK_PAYMENT);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		boolean result;
		if (resultSet.next()) {
			result = true;
		} else {
			result = false;
		}
		
		resultSet.close();
		preparedStatement.close();
		
		return result;
	}

	private static void createPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.ORDER_PAYMENT);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	private static void refundPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.ORDER_REFUND);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	protected static String checkStatus(int orderId, Connection connection) throws SQLException, DAOUserException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.CHECK_STATUS);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		//error
		ResultSet resultSet = preparedStatement.executeQuery();

		if (!resultSet.next()) {
    		logger.error(DAOUserException.MSG_ORDER_UNKNOWN);
    		throw new DAOUserException(DAOUserException.MSG_ORDER_UNKNOWN);
		}
		
		String status = resultSet.getString(1);

		resultSet.close();
		preparedStatement.close();

		return status;
	}

}
