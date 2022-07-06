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

			preparedStatement = connection.prepareStatement(SQLQuery.CHECK_ACCESS);
            preparedStatement.setInt(1, passwordHash);
            preparedStatement.setString(2, login);
            
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
            	throw new DAOException("Authorization failed. Specified user doesn't exist");
            }
            else if (!resultSet.getBoolean(2)) {
            	throw new DAOException("Authorization failed. Specified user is blocked");
            }
            else if (resultSet.getString(5) == null) {
            	throw new DAOException("Authorization failed. Wrong password.");
            }
            else {
            	int userId = resultSet.getInt(1);
            	boolean admin = resultSet.getBoolean(3);
            	String email = resultSet.getString(4);
                return new AuthorizedUser(userId, login, email, admin);
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
	public synchronized int insertUser(User user, int passwordHash) throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
        	
        	if (findUser(user.getLogin(), connection) != 0) {
            	throw new DAOException("User with specified login is already exist");
            }
            
            if (checkEmailExist(user.getEmail(), connection)) {
            	throw new DAOException("Specified email is already used.");
            }
            
			preparedStatement = connection.prepareStatement(SQLQuery.INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setInt(2, passwordHash);
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.setBoolean(5, true);

            preparedStatement.executeUpdate();
            
            return findUser(user.getLogin(), connection);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}
		
	}

	@Override
	public synchronized int insertEmployee(Employee employee, int passwordHash) throws DAOException {
        
		int userId = insertUser(employee, passwordHash);

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
 
    		preparedStatement = connection.prepareStatement(SQLQuery.INSERT_EMPLOYEE);
                
            preparedStatement.setString(1, employee.getDepartment());
            preparedStatement.setString(2, employee.getPosition());
            preparedStatement.setDouble(3, employee.getSalary());
            preparedStatement.setInt(4, userId);
                
            preparedStatement.executeUpdate();
            
            return findUser(employee.getLogin(), connection);

		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		}
        
	}
	
	public int findUser(String login, Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
		preparedStatement = connection.prepareStatement(SQLQuery.FIND_USER);
		preparedStatement.setString(1, login);
			
		resultSet = preparedStatement.executeQuery();
			
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            return 0;
        }

	}
	
	public boolean checkEmailExist(String email, Connection connection) throws DAOException {
		
		PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
		try {
			preparedStatement = connection.prepareStatement(SQLQuery.FIND_EMAIL);
			preparedStatement.setString(1, email);
			
			resultSet = preparedStatement.executeQuery();
			
            if (resultSet.next()) {
            	return true;
            } else {
            	return false;
            }

		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

}
