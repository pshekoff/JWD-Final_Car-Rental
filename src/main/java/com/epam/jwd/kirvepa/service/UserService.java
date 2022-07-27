package com.epam.jwd.kirvepa.service;

import java.util.List;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface UserService {
	User authorization(String login, int passwordHash) throws ServiceException, ServiceUserException;
	int registration(User user, int passwordHash) throws ServiceException, ServiceUserException;
	boolean addEmployee(Employee employee, int passwordHash) throws ServiceException, ServiceUserException;
	void addPersonalData(int userId, PersonalData personalData) throws ServiceException, ServiceUserException;
	boolean changeLogin(int userId, String login) throws ServiceException, ServiceUserException;
	boolean changePassword(int userId, int passwordHash) throws ServiceException, ServiceUserException;
	boolean changeEmail(int userId, String email) throws ServiceException, ServiceUserException;
	List<User> getUsers() throws ServiceException;
	void changeUserAccess(int userId) throws ServiceException;
}
