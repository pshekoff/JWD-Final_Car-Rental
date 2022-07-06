package com.epam.jwd.kirvepa.dao;

import java.sql.Date;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public interface OrderDAO {
	int prepareOrder(int userId, Car car, Date from, Date to, double price) throws DAOException;
	boolean createOrder(int userId, int orderId, PersonalData personalData) throws DAOException;
	boolean insertUserPersonalData(int userId, PersonalData personalData) throws DAOException;
}
