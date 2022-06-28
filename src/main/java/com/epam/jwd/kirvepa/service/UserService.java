package com.epam.jwd.kirvepa.service;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.service.exception.ServiceException;

public interface UserService {
	AuthorizedUser singIn(String login, int passwordHash) throws ServiceException;
	User singOut(String login) throws ServiceException;
	boolean registration(User user) throws ServiceException;
	boolean addEmployee(Employee employee) throws ServiceException;
}
