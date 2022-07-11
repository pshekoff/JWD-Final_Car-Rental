package com.epam.jwd.kirvepa.dao;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface UserDAO {
	AuthorizedUser authorization(String login, int passwordHash) throws DAOException, DAOUserException;
	boolean insertUser(User user, int passwordHash) throws DAOException, DAOUserException;
	boolean insertEmployee(Employee employee, int passwordHash) throws DAOException, DAOUserException;
	boolean insertUserPersonalData(int userId, PersonalData personalData) throws DAOException, DAOUserException;
	boolean updateLogin(int userId, String login) throws DAOException, DAOUserException;
	boolean updatePassword(int userId, int passwordHash) throws DAOException, DAOUserException;
	boolean updateEmail(int userId, String email) throws DAOException, DAOUserException;

}
