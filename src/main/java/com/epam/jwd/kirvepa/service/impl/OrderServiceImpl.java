package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	private static final OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
	
	@Override
	public Order registerOrder(int userId, Car car, Date from, Date to, double price) throws ServiceException, ServiceUserException {
		
		try {
			return orderDAO.registerOrder(userId, car, from, to, price);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public void cancelOrder(int orderId) throws ServiceException, ServiceUserException {
		
		try {
			orderDAO.cancelOrder(orderId);

		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e.getMessage());
		}
	}

	@Override
	public boolean payOrder(int orderId, int userId) throws ServiceException, ServiceUserException {
		
		try {
			boolean success = orderDAO.payOrder(orderId, userId);
			
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
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public List<Order> getOrders(String filter, int userId) throws ServiceException {
		try {
			return orderDAO.getOrders(filter, userId);
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
