package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.CarDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.validator.CarDataValidator;

public class CarServiceImpl implements CarService{
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	private static final CarDataValidator validator = CarDataValidator.getInstance();
	private static final CarDAO carDAO = DAOFactory.getInstance().getCarDAO();
	
	@Override
	public List<String> getCarBodyList(String filter) throws ServiceException {
		try {
			return carDAO.getCarBodyList(filter);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws ServiceException, ServiceUserException {
		
		if (!validator.bodyValidation(bodies)) {
			logger.error(ServiceUserException.MSG_CAR_BODY_SELECT_NULL);
			throw new ServiceUserException(ServiceUserException.MSG_CAR_BODY_SELECT_NULL);
		}
		
		switch (validator.DateValidation(from, to)) {
			case (1):
				try {
					return carDAO.getCarList(from , to, bodies);
				} catch (DAOException e) {
					logger.error(e);
					throw new ServiceException(e);
				}
			case (0):
				logger.error(ServiceUserException.MSG_CAR_DATE_NULL);
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATE_NULL);
			case (-1):
				logger.error(ServiceUserException.MSG_CAR_DATES_DISCREPANCY);
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATES_DISCREPANCY);
			case (-2):
				logger.error(ServiceUserException.MSG_CAR_DATE_PAST);
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATE_PAST);
			default:
				return null;
		}

	}

	@Override
	public void handoverReturnCar(int orderId) throws ServiceException, ServiceUserException {
		
		try {
			carDAO.handoverReturnCar(orderId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			logger.error(e);
			throw new ServiceUserException(e);
		}
		
	}

	@Override
	public boolean addCar(Car car) throws ServiceException {
		try {
			return carDAO.insertCar(car);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Car> getCars() throws ServiceException {
		try {
			return carDAO.getCars();
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void blockUnblockCar(int carId) throws ServiceException {
		try {
			carDAO.blockUnblockCar(carId);
		} catch (DAOException e) {
			logger.error(e);
			throw new ServiceException(e);
		}
		
	}

}
