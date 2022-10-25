package com.epam.jwd.kirvepa.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPool;
import com.epam.jwd.kirvepa.dao.connection.ConnectionPoolException;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public class SQLUserDAO implements UserDAO {
	private static final Logger logger = LogManager.getLogger(SQLUserDAO.class);
			
	@Override
	public User authorization(String login, int passwordHash) throws DAOException, DAOUserException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

		try {
			connection = ConnectionPool.getInstance().takeConnection();

			preparedStatement = connection.prepareStatement(SQLUserQuery.CHECK_ACCESS);
            preparedStatement.setInt(1, passwordHash);
            preparedStatement.setString(2, login);
            
            if (logger.isDebugEnabled()) {
            	logger.debug(preparedStatement.toString());
            }
            
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
            	throw new DAOUserException(DAOUserException.MSG_USR_ABSENT);
            }
            else if (!resultSet.getBoolean(2)) {
            	logger.error(DAOUserException.MSG_USR_BLOCKED);
            	throw new DAOUserException(DAOUserException.MSG_USR_BLOCKED);
            }
            else if (resultSet.getString(5) == null) {
            	throw new DAOUserException(DAOUserException.MSG_PWD_INVALID);
            }
            else {
            	int userId = resultSet.getInt(1);
            	boolean active = resultSet.getBoolean(2);
            	boolean admin = resultSet.getBoolean(3);
            	String email = resultSet.getString(4);
            	
                return new User(userId, login, email, admin, active);
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
	public synchronized int insertUser(User user, int passwordHash) throws DAOException, DAOUserException {
		
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
			
        	preparedStatement = connection.prepareStatement(SQLUserQuery.INSERT_USER
        												   	, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setInt(2, passwordHash);
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.isAdmin());
            
        	if (findUser(user.getLogin(), connection) != 0) {
        		throw new DAOUserException(DAOUserException.MSG_USR_EXIST);
            }

            if (checkEmailExist(user.getEmail(), connection)) {
            	throw new DAOUserException(DAOUserException.MSG_EMAIL_EXIST);
            }

            if (logger.isDebugEnabled()) {
            	logger.debug(preparedStatement.toString());
            }
            
            preparedStatement.executeUpdate();
            
            resultSet = preparedStatement.getGeneratedKeys();
            
            int userId;
            if (resultSet.next()) {
            	userId = resultSet.getInt(1);
            	return userId;
            } else {
                 return 0;
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
	public synchronized boolean insertEmployee(Employee employee, int passwordHash) throws DAOException, DAOUserException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
        	connection = ConnectionPool.getInstance().takeConnection();
        	connection.setAutoCommit(false);
        	
            preparedStatement = connection.prepareStatement(SQLUserQuery.INSERT_EMPLOYEE
															, Statement.RETURN_GENERATED_KEYS);

			int userId = insertUser(employee, passwordHash);
			
            if (userId == 0) {
            	throw new DAOUserException(DAOUserException.MSG_USR_INS_FAIL);
            }
            
            insertPersonalData(userId, employee.getPersonalData());
            
            preparedStatement.setString(1, employee.getDepartment());
            preparedStatement.setString(2, employee.getPosition());
            preparedStatement.setDouble(3, employee.getSalary());
            preparedStatement.setInt(4, userId);
               
            if (logger.isDebugEnabled()) {
            	logger.debug(preparedStatement.toString());
            }
            
            preparedStatement.executeUpdate();
                
            resultSet = preparedStatement.getGeneratedKeys();
                
            if (resultSet.next()) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
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
	public void insertPersonalData(int userId, PersonalData personalData) throws DAOException {

		Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
			connection = ConnectionPool.getInstance().takeConnection();
			connection.setAutoCommit(false);
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.INSERT_PERSONAL_DATA);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, personalData.getFirstName());
			preparedStatement.setString(3, personalData.getLastName());
			preparedStatement.setDate(4, personalData.getDayOfBirth());
			preparedStatement.setString(5, personalData.getPassportNumber());
			preparedStatement.setDate(6, personalData.getIssueDate());
			preparedStatement.setDate(7, personalData.getExpireDate());
			preparedStatement.setString(8, personalData.getIdentificationNumber());
			preparedStatement.setString(9, personalData.getHomeAddress());
			preparedStatement.setString(10, personalData.getPhone());
			
			updatePersonalData(userId, connection);

			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
			preparedStatement.executeUpdate();
			
			SQLOrderDAO.updatePreparedOrders(userId, connection);
			
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
	public boolean updateLogin(int userId, String login) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.UPDATE_LOGIN);
			preparedStatement.setString(1, login);
			preparedStatement.setInt(2, userId);
			
        	if (findUser(login, connection) != 0) {
        		throw new DAOUserException(DAOUserException.MSG_USR_EXIST);
            }
        	
        	if (logger.isDebugEnabled()) {
        		logger.debug(preparedStatement.toString());
        	}
        	
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
	public boolean updatePassword(int userId, int passwordHash) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.UPDATE_PASSWORD);
			preparedStatement.setInt(1, passwordHash);
			preparedStatement.setInt(2, userId);
        	
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
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
	public boolean updateEmail(int userId, String email) throws DAOException, DAOUserException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
		try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.UPDATE_EMAIL);
			preparedStatement.setString(1, email);
			preparedStatement.setInt(2, userId);
            
			if (checkEmailExist(email, connection)) {
            	throw new DAOUserException(DAOUserException.MSG_EMAIL_EXIST);
            }
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
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
	public List<User> getUsers() throws DAOException {
        
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.GET_USER_LIST);
			
			if (logger.isDebugEnabled()) {
				logger.debug(preparedStatement.toString());
			}
			
			resultSet = preparedStatement.executeQuery();
			
			List<User> users = new ArrayList<>();
			while (resultSet.next()) {
				int userId = resultSet.getInt(1);
				String login = resultSet.getString(2);
				String email = resultSet.getString(3);
				boolean admin = resultSet.getBoolean(4);
				boolean active = resultSet.getBoolean(5);
				
				users.add(new User(userId, login, email, admin, active));
			}
			
			return users;
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		} 

	}
	
	@Override
	public void changeUserAccess(int userId) throws DAOException {
		
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        try {
			connection = ConnectionPool.getInstance().takeConnection();
			
			preparedStatement = connection.prepareStatement(SQLUserQuery.CHANGE_USER_ACCESS);
			preparedStatement.setInt(1, userId);
			
			preparedStatement.executeUpdate();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException(e);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			ConnectionPool.getInstance().closeConnectionQueue(connection, preparedStatement);
		} 
	}
	
	public static void updatePersonalData(int userId, Connection connection) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(SQLUserQuery.UPDATE_PERSONAL_DATA);
		preparedStatement.setInt(1, userId);
		
		if (logger.isDebugEnabled()) {
			logger.debug(preparedStatement.toString());
		}
		
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	public static int findUser(String login, Connection connection) throws SQLException {
		
		PreparedStatement preparedStatement = connection.prepareStatement(SQLUserQuery.FIND_USER);
		preparedStatement.setString(1, login);
		
		if (logger.isDebugEnabled()) {
			logger.debug(preparedStatement.toString());
		}
		
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
	
	public static boolean checkEmailExist(String email, Connection connection) throws SQLException {
        
		PreparedStatement preparedStatement = connection.prepareStatement(SQLUserQuery.FIND_EMAIL);
		preparedStatement.setString(1, email);
		
		if (logger.isDebugEnabled()) {
			logger.debug(preparedStatement.toString());
		}
		
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
