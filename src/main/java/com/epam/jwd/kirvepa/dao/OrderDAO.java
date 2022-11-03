package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface OrderDAO {
	Order registerOrder(int userId, Car car, Date from, Date to, double price, String language) throws DAOException, DAOUserException;
	void cancelOrder(int orderId) throws DAOException, DAOUserException;
	boolean payOrder(int orderId, int userId) throws DAOException, DAOUserException;
	List<Order> getOrders(String filter, int userId, String language) throws DAOException;
	void approveOrder(int orderId) throws DAOException, DAOUserException;
	void rejectOrder(int orderId) throws DAOException;
}
