package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

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
	private static final CarDataValidator validator = CarDataValidator.getInstance();
	private static final CarDAO carDAO = DAOFactory.getInstance().getCarDAO();
	
	@Override
	public List<String> getCarBodyList(String language) throws ServiceException {
		try {
			return carDAO.getCarBodyList(language);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<List<String>> GetCarsAddingInfo(String language) throws ServiceException {
		try {
			return carDAO.GetCarsAddingInfo(language);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public Map<Car, Double> getCarList(Date from, Date to, String[] bodies, String language) throws ServiceException, ServiceUserException {
		
		if (!validator.bodyValidation(bodies)) {
			throw new ServiceUserException(ServiceUserException.MSG_CAR_BODY_SELECT_NULL);
		}
		
		switch (validator.DateValidation(from, to)) {
			case (1):
				try {
					return carDAO.getCarList(from, to, bodies, language);
				} catch (DAOException e) {
					throw new ServiceException(e);
				}
			case (0):
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATE_NULL);
			case (-1):
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATE_PAST);
			case (-2):
				throw new ServiceUserException(ServiceUserException.MSG_CAR_DATES_DISCREPANCY);
			default:
				throw new ServiceException(ServiceUserException.MSG_CAR_DATES_UNKNOWN);
		}

	}

	@Override
	public void handoverReturnCar(int orderId) throws ServiceException, ServiceUserException {
		
		try {
			carDAO.handoverReturnCar(orderId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (DAOUserException e) {
			throw new ServiceUserException(e.getMessage());
		}
		
	}

	@Override
	public boolean addCar(Car car, String language) throws ServiceException {
		try {
			return carDAO.insertCar(car, language);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Car> getCars(String language) throws ServiceException {
		try {
			return carDAO.getCars(language);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void blockUnblockCar(int carId) throws ServiceException {
		try {
			carDAO.blockUnblockCar(carId);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		
	}

}
