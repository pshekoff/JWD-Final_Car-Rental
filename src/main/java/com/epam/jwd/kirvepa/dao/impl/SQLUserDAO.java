package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {


	@Override
	public AuthorizedUser authorization(String login, int passwordHash) throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLQuery.CHECK_CREDENTIALS);
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, passwordHash);
            
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
            	return null;
            }
            else {
            	int id = resultSet.getInt(1);
            	String role = resultSet.getString(2);
                return new AuthorizedUser(id, login, role);
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
	public synchronized boolean insertUser(User user) throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
        	connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        	
			preparedStatement = connection.prepareStatement(SQLQuery.FIND_USER);
            preparedStatement.setString(1, user.getLogin());
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	throw new DAOException("User with specified login is already exist");
            }
            
			preparedStatement = connection.prepareStatement(SQLQuery.FIND_EMAIL);
            preparedStatement.setString(1, user.getEmail());
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	throw new DAOException("Specified email is used");
            }
            
			preparedStatement = connection.prepareStatement(SQLQuery.INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setInt(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getFirstName());
            preparedStatement.setString(6, user.getLastName());
            preparedStatement.setString(7, user.getAdress());
            preparedStatement.setString(8, user.getPhone());
            preparedStatement.executeUpdate();
            
            return true;
            
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
	}

	@Override
	public boolean insertEmployee(Employee employee) throws DAOException {
        
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
        	connection.setAutoCommit(false);
        	
			preparedStatement = connection.prepareStatement(SQLQuery.FIND_USER);
            preparedStatement.setString(1, employee.getLogin());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
            	throw new DAOException("Employee with specified login is already exist");
            }
            
			preparedStatement = connection.prepareStatement(SQLQuery.FIND_EMAIL);
            preparedStatement.setString(1, employee.getEmail());

            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
            	throw new DAOException("Specified email is used");
            }
            
            try {
    			preparedStatement = connection.prepareStatement(SQLQuery.INSERT_USER);
                preparedStatement.setString(1, employee.getLogin());
                preparedStatement.setInt(2, employee.getPasswordHash());
                preparedStatement.setString(3, employee.getEmail());
                preparedStatement.setString(4, employee.getRole());
                preparedStatement.setString(5, employee.getFirstName());
                preparedStatement.setString(6, employee.getLastName());
                preparedStatement.setString(7, employee.getAdress());
                preparedStatement.setString(8, employee.getPhone());
                
                preparedStatement.executeUpdate();
                
                preparedStatement = connection.prepareStatement(SQLQuery.FIND_USER);
                preparedStatement.setString(1, employee.getLogin());
                resultSet = preparedStatement.executeQuery();
                
                if (!resultSet.next()) {
                	throw new SQLException();
                } else {
                	int id = resultSet.getInt(1);
                	
                	preparedStatement = connection.prepareStatement(SQLQuery.INSERT_EMPLOYEE);
                	preparedStatement.setInt(1, id);
                	preparedStatement.setString(2, employee.getDepartment());
                	preparedStatement.setDouble(3, employee.getSalary());
                	preparedStatement.executeUpdate();
                }
                
                connection.commit();
                
            } catch (SQLException e) {
            	connection.rollback();
            }
            
            return true;

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

}
