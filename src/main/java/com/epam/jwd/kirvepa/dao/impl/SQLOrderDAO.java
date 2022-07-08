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
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;

public class SQLOrderDAO implements OrderDAO {
	private static final Logger logger = LogManager.getLogger(SQLOrderDAO.class);
	
	@Override
	public Order placeOrder(int userId, Car car, Date from, Date to, double price) throws DAOException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(SQLQuery.CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);

			String status = checkPersonalData(userId, connection);

			int carId = SQLCarDAO.getCarId(car, from, to, connection);

			if (carId == 0) {
				logger.error("Car ID is missing");
				throw new DAOException("Failed to book selected car.");
			}

			preparedStatement.setInt(1, carId);
			preparedStatement.setInt(2, userId);
			preparedStatement.setDate(3, from);
			preparedStatement.setDate(4, to);
			preparedStatement.setDouble(5, price);
			preparedStatement.setString(6, status);

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();

			if (!resultSet.next()) {
				connection.rollback();
				logger.error("Order ID is missing.");
				throw new DAOException("Failed to book selected car.");
			}

			Order order = getOrder(resultSet.getInt(1), connection);

			if (order == null) {
				connection.rollback();
				logger.error("Order ID is missing.");
				throw new DAOException("Failed to book selected car.");
			}
			
			connection.commit();

			return order;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}
	
	@Override
	public Order updateOrder(int userId, int orderId, PersonalData personalData) throws DAOException {

		boolean success = DAOFactory.getInstance().getUserDAO().insertUserPersonalData(userId, personalData);

		if (!success) {
			logger.error("Failed to save client data.");
			throw new DAOException("Failed to save client data.");
		}

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();

			preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_ORDER, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, orderId);
	        preparedStatement.executeUpdate();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			
			if (!resultSet.next()) {
				connection.rollback();
				logger.error("Order ID is missing.");
				throw new DAOException("Failed to book selected car.");
			}
			
			Order order = getOrder(resultSet.getInt(1), connection);
			if (order == null) {
				connection.rollback();
				logger.error("Order ID is missing.");
				throw new DAOException("Failed to book selected car.");
			}
			
			connection.commit();
			
			return order;
	        
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}

	@Override
	public boolean cancelOrder(int orderId) throws DAOException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLQuery.CANCEL_ORDER);
			
        	boolean paymentExist = checkPayment(orderId, connection);
        	if (paymentExist) {
        		refundOrder(orderId, connection);
        	}
			
			preparedStatement.setInt(1, orderId);
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
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public boolean payOrder(int orderId) throws DAOException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLQuery.ORDER_PAYMENT);
			preparedStatement.setInt(1, orderId);
			preparedStatement.setInt(2, orderId);
			
			preparedStatement.executeUpdate();
			
			return true;
			
		} catch (ConnectionPoolException e) {
			logger.error(e);
			throw new DAOException(e);
		} catch (SQLException e) {
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
			
			preparedStatement = connection.prepareStatement(SQLQuery.GET_USER_ORDERS);
			preparedStatement.setInt(1, userId);
			
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
	        	String status = resultSet.getString(11);
	        	
				orders.add(new Order(orderId, new Car(manufacturer, model, body, engine, transmission, drive), dateTo, dateFrom, amount, status));
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

		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ORDER);
		preparedStatement.setInt(1, orderId);

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
	    	String status = resultSet.getString(10);
	    	
			return new Order(orderId, new Car(manufacturer, model, body, engine, transmission, drive), dateTo, dateFrom, amount, status);
		}

	}
	
	private String checkPersonalData(int userId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CHECK_PERSONAL_DATA);
		preparedStatement.setInt(1, userId);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		String status;
		if (resultSet.next()) {
			status = "CREATED";
		} else {
			status = "PREPARED";
		}
		
		resultSet.close();
		preparedStatement.close();
		
		return status;
	}
	
	private boolean checkPayment(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CHECK_PAYMENT);
		preparedStatement.setInt(1, orderId);
		
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

	private void refundOrder(int orderId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.ORDER_REFUND);
		preparedStatement.setInt(1, orderId);
		preparedStatement.setInt(2, orderId);
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
}
