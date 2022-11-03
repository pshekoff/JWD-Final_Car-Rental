package com.epam.jwd.kirvepa.service;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface OrderService {
	Order registerOrder(int userId, Car car, Date from, Date to, double price, String language) throws ServiceException, ServiceUserException;
	void cancelOrder(int orderId) throws ServiceException, ServiceUserException;
	boolean payOrder(int orderId, int userId) throws ServiceException, ServiceUserException;
	List<Order> getOrders(String filter, int userId, String language) throws ServiceException;
	void approveOrder(int orderId) throws ServiceException, ServiceUserException;
	void rejectOrder(int orderId) throws ServiceException;
}
