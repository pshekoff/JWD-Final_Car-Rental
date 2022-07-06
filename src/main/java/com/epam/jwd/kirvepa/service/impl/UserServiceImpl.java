package com.epam.jwd.kirvepa.service.impl;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.validator.UserDataValidator;

public class UserServiceImpl implements UserService {
	
	private static final UserDataValidator validator = UserDataValidator.getInstance();
	private static final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	@Override
	public AuthorizedUser singIn(String login, int passwordHash) throws ServiceException {

		if(!validator.loginValidation(login)){
			throw new ServiceException("Incorrect login");
		}
		
		try {
			AuthorizedUser authUser =  userDAO.authorization(login, passwordHash);
			return authUser;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
		
	}

	@Override
	public User singOut(String login) throws ServiceException {
		// TODO Auto-generated method stub
		User user = new User("login", "email", false); //stub
		return user;
		
	}

	@Override
	public boolean registration(User user, int passwordHash) throws ServiceException {
		
		if(!validator.loginValidation(user.getLogin())) {
			throw new ServiceException("Incorrect login");
		}
		if (!validator.emailValidation(user.getEmail())) {
			throw new ServiceException("Incorrect email");
		}
		
		try {
			int userId = userDAO.insertUser(user, passwordHash);
			
			if (userId != 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	@Override
	public boolean addEmployee(Employee employee, int passwordHash) throws ServiceException {
		
		if(!validator.loginValidation(employee.getLogin())) {
			throw new ServiceException("Incorrect login");
		}
		if (!validator.emailValidation(employee.getEmail())) {
			throw new ServiceException("Incorrect email");
		}
		
		try {
			int employeeId = userDAO.insertEmployee(employee, passwordHash);
			
			if (employeeId != 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
