package com.epam.jwd.kirvepa.service;

import java.sql.Date;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.service.exception.ServiceException;

public interface OrderService {
	int prepareOrder(int userId, Car car, Date from, Date to, double price) throws ServiceException;
	boolean createOrder(int userId, int orderId, PersonalData client) throws ServiceException;
}
