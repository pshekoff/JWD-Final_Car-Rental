package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order.OrderStatus;
import com.epam.jwd.kirvepa.dao.CarDAO;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public class SQLCarDAO implements CarDAO {
	private static final Logger logger = LogManager.getLogger(SQLCarDAO.class);
	private static final String FILTER_ALL = "all";
	private static final String FILTER_EXIST = "exist";
	
	@Override
	public List<String> getCarBodyList(String filter) throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			if (filter.equals(FILTER_ALL)) {
				preparedStatement = connection.prepareStatement(SQLCarQuery.GET_BODYTYPES_ALL);
			}
			else if (filter.equals(FILTER_EXIST)) {
				preparedStatement = connection.prepareStatement(SQLCarQuery.GET_BODYTYPES_EXIST);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
	        resultSet = preparedStatement.executeQuery();
	        
	        List<String> bodyTypes = new ArrayList<>();
            
	        while(resultSet.next()) {
                bodyTypes.add(resultSet.getString(1));
            }
	        
	        return bodyTypes;
	        
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}

	}

	@Override
	public Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws DAOException {
        
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();

			StringBuffer bodiesList = new StringBuffer(bodies[0]);
			for (int i = 1; i < bodies.length; i++) {
				bodiesList.append(",").append(bodies[i]);
			}
			
			preparedStatement = connection.prepareStatement(SQLCarQuery.GET_CAR_LIST);
			preparedStatement.setDate(1, to);
			preparedStatement.setDate(2, from);
			preparedStatement.setString(3, bodiesList.toString());
			preparedStatement.setDate(4, from);
			preparedStatement.setDate(5, to);
			preparedStatement.setDate(6, from);
			preparedStatement.setDate(7, to);
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
	        resultSet = preparedStatement.executeQuery();

	        Map<Car, Double> cars = new HashMap<>();
	        
	        while(resultSet.next()) {
	        	String manufacturer = resultSet.getString(1);
	        	String model = resultSet.getString(2);
	        	String engine = resultSet.getString(3);
	        	String transmission = resultSet.getString(4);
	        	String driveType = resultSet.getString(5);
	        	String bodyType = resultSet.getString(6);
	        	Double price = resultSet.getDouble(7);
	        	
	        	cars.put(new Car(manufacturer, model, bodyType, engine, transmission, driveType), price);
            }

	        return cars;
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
			
		} catch (SQLException e) {
			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}
        
	}
	
	@Override
	public void handoverReturnCar(int orderId) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLOrderQuery.UPDATE_ORDER_STATUS);
			
			String orderStatus = SQLOrderDAO.getOrderStatus(orderId, connection);
			
			if (orderStatus.equals(OrderStatus.APPROVED.name())) {
				preparedStatement.setString(1, OrderStatus.IN_PROGRESS.name());
			}
			else if (orderStatus.equals(OrderStatus.IN_PROGRESS.name())) {
				preparedStatement.setString(1, OrderStatus.COMPLETED.name());
			}
			else {
        		throw new DAOUserException(DAOUserException.MSG_ORDER_HANDOVER_RETURN_FAIL + orderStatus);
			}
			preparedStatement.setInt(2, orderId);
			
			SQLOrderDAO.autoUpdateOrderHistory(orderId, connection);

			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
			preparedStatement.executeUpdate();

			connection.commit();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
			
		} catch (SQLException e) {

			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new DAOException(e1);
				}
			}

			throw new DAOException(e);
			
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}
	}
	

	@Override
	public boolean insertCar(Car car) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        		
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLCarQuery.INSERT_CAR
															, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, car.getManufacturer());
			preparedStatement.setString(2, car.getModel());
			preparedStatement.setString(3, car.getLicensePlate());
			preparedStatement.setString(4, car.getBodyType());
			preparedStatement.setInt(5, car.getIssueYear());
			preparedStatement.setString(6, car.getEngine());
			preparedStatement.setString(7, car.getTransmission());
			preparedStatement.setString(8, car.getDriveType());
			preparedStatement.setString(9, car.getColor());
			preparedStatement.setInt(10, car.getWeight());
			preparedStatement.setString(11, car.getVin());
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
			preparedStatement.executeUpdate();
            
            resultSet = preparedStatement.getGeneratedKeys();
            
            if (resultSet.next()) {
            	return true;
            } else {
                return false;
            }
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}
        
	}
	
	@Override
	public List<Car> getCars() throws DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLCarQuery.GET_ALL_CARS);
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
			resultSet = preparedStatement.executeQuery();
			
			List<Car> cars = new ArrayList<>();
			
			while (resultSet.next()) {
				int carId = resultSet.getInt(1);
				String manufacturer = resultSet.getString(2);
				String model = resultSet.getString(3);
				String licensePlate = resultSet.getString(4);
				String bodyType = resultSet.getString(5);
				int issueYear = resultSet.getInt(6);
				String engine = resultSet.getString(7);
				String transmission = resultSet.getString(8);
				String driveType = resultSet.getString(9);
				String color = resultSet.getString(10);
				int weight = resultSet.getInt(11);
				String vin = resultSet.getString(12);
				boolean available = resultSet.getBoolean(13);
				
				cars.add(new Car(carId
								, manufacturer
								, model
								, licensePlate
								, vin
								, bodyType
								, issueYear
								, engine
								, transmission
								, driveType
								, color
								, weight
								, available) );
			}
			
			return cars;
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement, resultSet);
		}
	}

	@Override
	public void blockUnblockCar(int carId) throws DAOException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLCarQuery.BLOCK_UNBLOCK_CAR);
			preparedStatement.setInt(1, carId);
			
			preparedStatement.executeUpdate();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public static int getCarId(Car car, Date from, Date to, Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(SQLCarQuery.FIND_CAR_ID);
			
		preparedStatement.setString(1, car.getManufacturer());
		preparedStatement.setString(2, car.getModel());
		preparedStatement.setString(3, car.getBodyType());
		preparedStatement.setString(4, car.getEngine());
		preparedStatement.setString(5, car.getTransmission());
		preparedStatement.setString(6, car.getDriveType());
		preparedStatement.setDate(7, from);
		preparedStatement.setDate(8, to);
		preparedStatement.setDate(9, from);
		preparedStatement.setDate(10, to);
		
		if (logger.isDebugEnabled()) {
			logger.debug(preparedStatement.toString());
		}
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		int result;
		if (resultSet.next()) {
			result = resultSet.getInt(1);
		} else {
			result =  0;
		}
		
		resultSet.close();
		preparedStatement.close();
		
		return result;

	}

}
