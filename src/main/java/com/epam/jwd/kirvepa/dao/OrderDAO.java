package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface OrderDAO {
	Order placeOrder(int userId, Car car, Date from, Date to, double price) throws DAOException, DAOUserException;
	Order createOrder(int userId, int orderId, PersonalData personalData) throws DAOException, DAOUserException;
	boolean cancelOrder(int orderId) throws DAOException, DAOUserException;
	boolean payOrder(int orderId) throws DAOException, DAOUserException;
	List<Order> getUserOrders(int userId) throws DAOException;
}
