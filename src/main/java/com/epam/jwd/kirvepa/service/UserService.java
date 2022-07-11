package com.epam.jwd.kirvepa.service;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface UserService {
	AuthorizedUser singIn(String login, int passwordHash) throws ServiceException, ServiceUserException;
	User singOut(String login) throws ServiceException;
	boolean registration(User user, int passwordHash) throws ServiceException, ServiceUserException;
	boolean addEmployee(Employee employee, int passwordHash) throws ServiceException, ServiceUserException;
	boolean changeLogin(int userId, String login) throws ServiceException, ServiceUserException;
	boolean changePassword(int userId, int passwordHash) throws ServiceException, ServiceUserException;
	boolean changeEmail(int userId, String email) throws ServiceException, ServiceUserException;
}
