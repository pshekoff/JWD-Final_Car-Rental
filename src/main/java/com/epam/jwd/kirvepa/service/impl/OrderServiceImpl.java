package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.validator.PersonalDataValidator;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	private static final PersonalDataValidator validator = PersonalDataValidator.getInstance();
	private static final OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
	
	@Override
	public Order placeOrder(int userId, Car car, Date from, Date to, double price) throws ServiceException, ServiceUserException {
		
		try {
			return orderDAO.placeOrder(userId, car, from, to, price);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
	}
	
	@Override
	public Order createOrder(int userId, int orderId, PersonalData personalData) throws ServiceException, ServiceUserException {
		
		try {
			return orderDAO.createOrder(userId, orderId, personalData);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
	}

	@Override
	public boolean cancelOrder(int orderId) throws ServiceException, ServiceUserException {
		
		try {
			boolean success = orderDAO.cancelOrder(orderId);
			
			if (success) {
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
	}

	@Override
	public boolean payOrder(int orderId) throws ServiceException, ServiceUserException {
		
		try {
			boolean success = orderDAO.payOrder(orderId);
			
			if (success) {
				return true;
			} else {
				return false;
			}
			
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
		
	}

	@Override
	public List<Order> getUserOrders(int UserId) throws ServiceException {
		try {
			return orderDAO.getUserOrders(UserId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Order> getOrders(String filter) throws ServiceException {
		try {
			return orderDAO.getOrders(filter);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void approveOrder(int orderId) throws ServiceException, ServiceUserException {
		try {
			orderDAO.approveOrder(orderId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
		
	}

	@Override
	public void rejectOrder(int orderId) throws ServiceException {
		try {
			orderDAO.rejectOrder(orderId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
		
	}




	
}
