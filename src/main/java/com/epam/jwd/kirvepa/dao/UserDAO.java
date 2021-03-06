package com.epam.jwd.kirvepa.dao;

import java.util.List;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface UserDAO {
	User authorization(String login, int passwordHash) throws DAOException, DAOUserException;
	int insertUser(User user, int passwordHash) throws DAOException, DAOUserException;
	boolean insertEmployee(Employee employee, int passwordHash) throws DAOException, DAOUserException;
	void insertPersonalData(int userId, PersonalData personalData) throws DAOException, DAOUserException;
	boolean updateLogin(int userId, String login) throws DAOException, DAOUserException;
	boolean updatePassword(int userId, int passwordHash) throws DAOException, DAOUserException;
	boolean updateEmail(int userId, String email) throws DAOException, DAOUserException;
	List<User> getUsers() throws DAOException;
	void changeUserAccess(int userId) throws DAOException;
}
