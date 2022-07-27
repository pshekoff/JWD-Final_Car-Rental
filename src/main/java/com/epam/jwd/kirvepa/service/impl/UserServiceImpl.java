package com.epam.jwd.kirvepa.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.validator.UserDataValidator;

public class UserServiceImpl implements UserService {
	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
	private static final UserDataValidator validator = UserDataValidator.getInstance();
	private static final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	@Override
	public User authorization(String login, int passwordHash) throws ServiceException, ServiceUserException {

		if(!validator.loginValidation(login)){
			logger.error(ServiceUserException.MSG_USR_LOGIN_VAL_FAIL);
			throw new ServiceUserException(ServiceUserException.MSG_USR_LOGIN_WRONG);
		}
		
		try {
			return userDAO.authorization(login, passwordHash);
			
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public int registration(User user, int passwordHash) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(user.getLogin())) {
			logger.error(ServiceUserException.MSG_USR_LOGIN_VAL_FAIL);
			throw new ServiceUserException(ServiceUserException.MSG_USR_LOGIN_WRONG);
		}
		if (!validator.emailValidation(user.getEmail())) {
			logger.error(ServiceUserException.MSG_USR_EMAIL_VAL_FAIL);
			throw new ServiceUserException(ServiceUserException.MSG_USR_EMAIL_WRONG);
		}
		
		try {
			return userDAO.insertUser(user, passwordHash);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}

	}

	@Override
	public boolean addEmployee(Employee employee, int passwordHash) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(employee.getLogin())) {
			logger.error(ServiceUserException.MSG_USR_LOGIN_VAL_FAIL);
			throw new ServiceException(ServiceUserException.MSG_USR_LOGIN_WRONG);
		}
		if (!validator.emailValidation(employee.getEmail())) {
			logger.error(ServiceUserException.MSG_USR_EMAIL_VAL_FAIL);
			throw new ServiceException(ServiceUserException.MSG_USR_EMAIL_WRONG);
		}
		
		try {
			return userDAO.insertEmployee(employee, passwordHash);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public boolean changeLogin(int userId, String login) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(login)){
			logger.error(ServiceUserException.MSG_USR_LOGIN_VAL_FAIL);
			throw new ServiceException(ServiceUserException.MSG_USR_LOGIN_WRONG);
		}
		
		try {
			return userDAO.updateLogin(userId, login);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public boolean changePassword(int userId, int passwordHash) throws ServiceException, ServiceUserException {
		try {
			return userDAO.updatePassword(userId, passwordHash);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public boolean changeEmail(int userId, String email) throws ServiceException, ServiceUserException {
		
		if (!validator.emailValidation(email)) {
			logger.error(ServiceUserException.MSG_USR_EMAIL_VAL_FAIL);
			throw new ServiceUserException(ServiceUserException.MSG_USR_EMAIL_WRONG);
		}
		
		try {
			return userDAO.updateEmail(userId, email);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public List<User> getUsers() throws ServiceException {
		try {
			return userDAO.getUsers();
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void changeUserAccess(int userId) throws ServiceException {
		try {
			userDAO.changeUserAccess(userId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void addPersonalData(int userId, PersonalData personalData) throws ServiceException, ServiceUserException {
		try {
			userDAO.insertPersonalData(userId, personalData);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
		
	}

}
