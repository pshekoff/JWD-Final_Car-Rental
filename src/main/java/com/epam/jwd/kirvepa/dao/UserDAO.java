package com.epam.jwd.kirvepa.dao;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public interface UserDAO {
	AuthorizedUser authorization(String login, int passwordHash) throws DAOException;
	int insertUser(User user, int passwordHash) throws DAOException;
	int insertEmployee(Employee employee, int passwordHash) throws DAOException;

}
