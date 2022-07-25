package com.epam.jwd.kirvepa.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface CarService {
	List<String> getCarBodyList(String filter) throws ServiceException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws ServiceException, ServiceUserException;
	void handoverReturnCar(int orderId) throws ServiceException, ServiceUserException;
	boolean addCar(Car car) throws ServiceException;
	List<Car> getCars() throws ServiceException;
	void blockUnblockCar(int carId) throws ServiceException;
}
