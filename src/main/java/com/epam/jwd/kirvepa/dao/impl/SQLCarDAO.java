package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.CarDAO;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public class SQLCarDAO implements CarDAO {

	@Override
	public List<String> getCarBodyList() throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLQuery.GET_BODY_TYPE_LIST_RU);
	        
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
			
			preparedStatement = connection.prepareStatement(SQLQuery.GET_CAR_LIST);
			
			StringBuffer bodiesList = new StringBuffer(bodies[0]);
			for (int i = 1; i < bodies.length; i++) {
				bodiesList.append(",").append(bodies[i]);
			}
			
			preparedStatement.setDate(1, to);
			preparedStatement.setDate(2, from);
			preparedStatement.setString(3, bodiesList.toString());
			preparedStatement.setDate(4, from);
			preparedStatement.setDate(5, to);
			preparedStatement.setDate(6, from);
			preparedStatement.setDate(7, to);
			
			System.out.println(preparedStatement.toString()); //temporary
			
	        resultSet = preparedStatement.executeQuery();

	        Map<Car, Double> carsPrice = new HashMap<>();
	        
	        while(resultSet.next()) {
	        	String manufacturer = resultSet.getString(1);
	        	String model = resultSet.getString(2);
	        	String engine = resultSet.getString(3);
	        	String transmission = resultSet.getString(4);
	        	String driveType = resultSet.getString(5);
	        	String bodyType = resultSet.getString(6);
	        	Double price = resultSet.getDouble(7);
	        	
	        	carsPrice.put(new Car(manufacturer, model, bodyType, engine, transmission, driveType), price);
            }
	        
	        return carsPrice;
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
	
	public static int getCarId(Car car, Date from, Date to, Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.FIND_CAR_ID);
			
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
