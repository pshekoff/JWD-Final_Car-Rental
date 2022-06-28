package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	public List<Car> getCarList(Date from, Date to, String[] bodies) throws DAOException {
        
		System.out.println("from " + from + ", to " + to); //temp
		
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
			
			System.out.println(bodiesList.toString()); //temp
			
			preparedStatement.setString(1, bodiesList.toString());
			preparedStatement.setDate(2, from);
			preparedStatement.setDate(3, to);
			preparedStatement.setDate(4, from);
			preparedStatement.setDate(5, to);
			
			System.out.println(preparedStatement.toString()); //temp
			
	        resultSet = preparedStatement.executeQuery();
	        
	        List<Car> cars = new ArrayList<>();
	        
	        while(resultSet.next()) {
	        	int id = resultSet.getInt(1);
	        	String manufacturer = resultSet.getString(2);
	        	String model = resultSet.getString(3);
	        	String licencePlate = resultSet.getString(4);
	        	String vin = resultSet.getString(5);
	        	String bodyType = resultSet.getString(6);
	        	int issueYear = resultSet.getInt(7);
	        	//String engine = resultSet.getString(8);
	        	String transmission = resultSet.getString(9);
	        	String driveType = resultSet.getString(10);
	        	String color = resultSet.getString(11);
	        	int weight = resultSet.getInt(12);
	        	
                cars.add(new Car(id, manufacturer, model, licencePlate, vin, bodyType, issueYear, "2,0", transmission, driveType, color, weight));
            }
	        
	        return cars;
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
