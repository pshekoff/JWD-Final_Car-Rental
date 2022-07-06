package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.validator.PersonalDataValidator;

public class OrderServiceImpl implements OrderService {

	private static final PersonalDataValidator validator = PersonalDataValidator.getInstance();
	private static final OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
	
	@Override
	public int prepareOrder(int userId, Car car, Date from, Date to, double price) throws ServiceException {
		
		try {
			return orderDAO.prepareOrder(userId, car, from, to, price);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}

	}
	
	@Override
	public boolean createOrder(int userId, int orderId, PersonalData client) throws ServiceException {
		
	/*	if(!validator.loginValidation(client.getFirstName()) {
			throw new ServiceException("Incorrect first name");
		}
*/
		
		try {
			boolean success = orderDAO.createOrder(userId, orderId, client);
			
			if (success) {
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}



}
