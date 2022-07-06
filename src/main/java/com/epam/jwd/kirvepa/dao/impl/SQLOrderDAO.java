package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.FrontController;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public class SQLOrderDAO implements OrderDAO {
	private static final Logger logger = LogManager.getLogger(SQLOrderDAO.class);

	@Override
	public int prepareOrder(int userId, Car car, Date from, Date to, double price) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();

			int carId = SQLCarDAO.getCarId(car, from, to, connection, preparedStatement);
			
			if (carId == 0) {
				throw new DAOException("Failed to book selected car.");
			}
			
			preparedStatement = connection.prepareStatement(SQLQuery.PREPARE_ORDER);
			preparedStatement.setInt(1, carId);
			preparedStatement.setInt(2, userId);
			preparedStatement.setDate(3, from);
			preparedStatement.setDate(4, to);
			preparedStatement.setDouble(5, price);
	        preparedStatement.executeUpdate();
	        
	        int orderId = findOrder(carId, userId, connection, preparedStatement);

	        if (orderId == 0) {
	        	throw new DAOException("Failed to book selected car.");
	        }
			
	        return orderId;

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}

	}
	
	@Override
	public boolean createOrder(int userId, int orderId, PersonalData personalData) throws DAOException {

		boolean success = insertUserPersonalData(userId, personalData);

		if (!success) {
			throw new DAOException("Failed to save client data.");
		}

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        

        try {
			connection = ConnectionPool.getInstance().takeConnection();

			preparedStatement = connection.prepareStatement(SQLQuery.PLACE_ORDER);

			preparedStatement.setInt(1, orderId);

	        preparedStatement.executeUpdate();

	        return true;
	        
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {

			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);

		}

	}

	@Override
	public boolean insertUserPersonalData(int userId, PersonalData personalData) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.setAutoCommit(false);

			preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_PERSONAL_DATA);
			preparedStatement.setInt(1, userId);
			preparedStatement.executeUpdate();

			preparedStatement = connection.prepareStatement(SQLQuery.INSERT_PERSONAL_DATA);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, personalData.getFirstName());
			preparedStatement.setString(3, personalData.getLastName());
			preparedStatement.setDate(4, personalData.getBirthday());
			preparedStatement.setString(5, personalData.getPassportNumber());
			preparedStatement.setDate(6, personalData.getIssueDate());
			preparedStatement.setDate(7, personalData.getExpireDate());
			preparedStatement.setString(8, personalData.getIdentificationNumber());
			preparedStatement.setString(9, personalData.getAddress());
			preparedStatement.setString(10, personalData.getPhone());

			try {
				preparedStatement.executeUpdate();
				connection.commit();
				return true;
				
			} catch (SQLException e) {
				connection.rollback();
				return false;
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		} 

	}
	
	public int findOrder(int carId, int userId, Connection c, PreparedStatement ps) throws SQLException {

		ps = c.prepareStatement(SQLQuery.FIND_ORDER);
		ps.setInt(1, carId);
		ps.setInt(2, userId);
		 
		ResultSet rs = ps.executeQuery();
		
		int result; 
		if (rs.next()) {
			result = rs.getInt(1);
		} else {
			result = 0;
		}
		rs.close();
		return result;
	}


}
