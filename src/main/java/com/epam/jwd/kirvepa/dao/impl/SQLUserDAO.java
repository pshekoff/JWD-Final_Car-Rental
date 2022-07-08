package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public class SQLUserDAO implements UserDAO {
	private static final Logger logger = LogManager.getLogger(SQLUserDAO.class);
			
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
            	throw new DAOException("Specified user doesn't exist");
            }
            else if (!resultSet.getBoolean(2)) {
            	throw new DAOException("Specified user is blocked");
            }
            else if (resultSet.getString(5) == null) {
            	throw new DAOException("Wrong password.");
            }
            else {
            	int userId = resultSet.getInt(1);
            	boolean admin = resultSet.getBoolean(3);
            	String email = resultSet.getString(4);
                return new AuthorizedUser(userId, login, email, admin);
            }

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
	public synchronized int insertUser(User user, int passwordHash) throws DAOException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
			
        	preparedStatement = connection.prepareStatement(SQLQuery.INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setInt(2, passwordHash);
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.setBoolean(5, true);
            
        	if (findUser(user.getLogin(), connection) != 0) {
        		throw new DAOException("User with specified login is already exist");
            }

            if (checkEmailExist(user.getEmail(), connection)) {
            	throw new DAOException("Specified email is already used.");
            }

            preparedStatement.executeUpdate();

            return findUser(user.getLogin(), connection);

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
			preparedStatement.close();
			
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
	
	public int findUser(String login, Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.FIND_USER);
		preparedStatement.setString(1, login);
		 
		ResultSet resultSet = preparedStatement.executeQuery();
		
		int result;
        if (resultSet.next()) {
        	result = resultSet.getInt(1);
        } else {
        	result = 0;
        }
        
        resultSet.close();
        preparedStatement.close();
        
        return result;

	}
	
	public boolean checkEmailExist(String email, Connection connection) throws SQLException {
        
		PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.FIND_EMAIL);
		preparedStatement.setString(1, email);
		
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

}
