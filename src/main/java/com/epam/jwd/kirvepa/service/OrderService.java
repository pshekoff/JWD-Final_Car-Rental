package com.epam.jwd.kirvepa.service;

import java.sql.Date;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface OrderService {
	Order placeOrder(int userId, Car car, Date from, Date to, double price) throws ServiceException, ServiceUserException;
	Order updateOrder(int UserId, int orderId, PersonalData personalData) throws ServiceException, ServiceUserException;
	boolean cancelOrder(int orderId) throws ServiceException;
	boolean payOrder(int orderId) throws ServiceException;
}
