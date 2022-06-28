package com.epam.jwd.kirvepa.service.impl;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.CarDAO;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.factory.DAOFactory;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.validator.CarDataValidator;

public class CarServiceImpl implements CarService{
	
	private static final CarDataValidator validator = CarDataValidator.getInstance();
	private static final CarDAO carDAO = DAOFactory.getInstance().getCarDAO();
	
	@Override
	public List<String> getCarBodyList() throws ServiceException {
		try {
			return carDAO.getCarBodyList();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Car> getCarList(Date from, Date to, String[] bodies) throws ServiceException {
		
		if (!validator.bodyValidation(bodies)) {
			throw new ServiceException("No any car body type selected.");
		}
		
		switch (validator.DateValidation(from, to)) {
			case (1):
				try {
					return carDAO.getCarList(from , to, bodies);
				} catch (DAOException e) {
					throw new ServiceException(e);
				}
			case (0):
				throw new ServiceException("Selected date is epmty.");
			case (-1):
				throw new ServiceException("Date from is greater than date to.");
			case (-2):
				throw new ServiceException("Selected date is in the past.");
			default:
				return null;
		}

	}

}
