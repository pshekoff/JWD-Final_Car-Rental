package com.epam.jwd.kirvepa.service.impl;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
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
	
	private static final UserDataValidator validator = UserDataValidator.getInstance();
	private static final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	@Override
	public AuthorizedUser singIn(String login, int passwordHash) throws ServiceException, ServiceUserException {

		if(!validator.loginValidation(login)){
			throw new ServiceException("Incorrect login.");
		}
		
		try {
			return userDAO.authorization(login, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public User singOut(String login) throws ServiceException {
		// TODO Auto-generated method stub
		User user = new User("login", "email", false); //stub
		return user;
		
	}

	@Override
	public boolean registration(User user, int passwordHash) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(user.getLogin())) {
			throw new ServiceUserException("Incorrect login.");
		}
		if (!validator.emailValidation(user.getEmail())) {
			throw new ServiceUserException("Incorrect email.");
		}
		
		try {
			return userDAO.insertUser(user, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}

	}

	@Override
	public boolean addEmployee(Employee employee, int passwordHash) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(employee.getLogin())) {
			throw new ServiceException("Incorrect login");
		}
		if (!validator.emailValidation(employee.getEmail())) {
			throw new ServiceException("Incorrect email");
		}
		
		try {
			return userDAO.insertEmployee(employee, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public boolean changeLogin(int userId, String login) throws ServiceException, ServiceUserException {
		
		if(!validator.loginValidation(login)){
			throw new ServiceException("Incorrect login.");
		}
		
		try {
			return userDAO.updateLogin(userId, login);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public boolean changePassword(int userId, int passwordHash) throws ServiceException, ServiceUserException {
		try {
			return userDAO.updatePassword(userId, passwordHash);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public boolean changeEmail(int userId, String email) throws ServiceException, ServiceUserException {
		
		if (!validator.emailValidation(email)) {
			throw new ServiceUserException("Incorrect email.");
		}
		
		try {
			return userDAO.updateEmail(userId, email);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
	}

}
