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
			
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		refundPayment(orderId, connection);
        	}
        	
			String orderStatus = checkStatus(orderId, connection);
        	if (orderStatus.equals(OrderStatus.CANCELLED)) {
        		logger.error(DAOUserException.MSG_ORDER_CANCELLED);
        		throw new DAOUserException(DAOUserException.MSG_ORDER_CANCELLED);
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
			
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		logger.error(DAOUserException.MSG_ORDER_PAID);
        		throw new DAOUserException(DAOUserException.MSG_ORDER_PAID);
        	}
        	
        	createPayment(orderId, connection);
        	
        	autoUpdateOrderHistory(orderId, connection);
			
        	preparedStatement.setString(1, OrderStatus.PAID.name());
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
	public List<Order> getUserOrders(int userId) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_USER_ORDERS);
			preparedStatement.setInt(1, userId);
			
			logger.debug("SQL query to execute: " + preparedStatement.toString());
			
			resultSet = preparedStatement.executeQuery();
			
			List<Order> orders = new ArrayList<>();
			
			while (resultSet.next()) {
	        	String manufacturer = resultSet.getString(1);
	        	String model = resultSet.getString(2);
	        	String body = resultSet.getString(3);
	        	String engine = resultSet.getString(4);
	        	String transmission = resultSet.getString(5);
	        	String drive = resultSet.getString(6);
	        	int orderId = resultSet.getInt(7);
	        	Date dateFrom = resultSet.getDate(8);
	        	Date dateTo = resultSet.getDate(9);
	        	Double amount = resultSet.getDouble(10);
	        	OrderStatus status = OrderStatus.valueOf(resultSet.getString(11));
	        	
				orders.add(new Order(orderId, new Car(manufacturer, model, body, engine, transmission, drive)
									, dateTo, dateFrom, amount, status));
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
	
	private Order getOrder(int orderId, Connection connection) throws SQLException, DAOException {

		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.GET_ORDER);
		preparedStatement.setInt(1, orderId);

		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (!resultSet.next()) {
        	return null;
        	
		} else {
	    	String manufacturer = resultSet.getString(1);
	    	String model = resultSet.getString(2);
	    	String body = resultSet.getString(3);
	    	String engine = resultSet.getString(4);
	    	String transmission = resultSet.getString(5);
	    	String drive = resultSet.getString(6);
	    	Date dateFrom = resultSet.getDate(7);
	    	Date dateTo = resultSet.getDate(8);
	    	Double amount = resultSet.getDouble(9);
	    	OrderStatus status = OrderStatus.valueOf(resultSet.getString(10));
	    	
			return new Order(orderId, new Car(manufacturer, model, body, engine, transmission, drive)
							, dateTo, dateFrom, amount, status);
		}

	}
	
	private void autoUpdateOrderHistory(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.AUTO_UPDATE_ORDER_HISTORY);
		preparedStatement.setInt(1, orderId);
		preparedStatement.setInt(2, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
	}
	
	private String checkPersonalData(int userId, Connection connection) throws SQLException {
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
	
	private boolean checkPayment(int orderId, Connection connection) throws SQLException {
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

	private void createPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.ORDER_PAYMENT);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	private void refundPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.ORDER_REFUND);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	private String checkStatus(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLOrderQuery.CHECK_STATUS);
		preparedStatement.setInt(1, orderId);
		
		logger.debug("SQL query to execute: " + preparedStatement.toString());
		
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		
		String status = resultSet.getString(1);
		
		resultSet.close();
		preparedStatement.close();
		
		return status;
	}
}
