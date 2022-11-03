package com.epam.jwd.kirvepa.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;

public interface CarService {
	List<String> getCarBodyList(String language) throws ServiceException;
	List<List<String>> GetCarsAddingInfo(String language) throws ServiceException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies, String language) throws ServiceException, ServiceUserException;
	void handoverReturnCar(int orderId) throws ServiceException, ServiceUserException;
	boolean addCar(Car car, String language) throws ServiceException;
	List<Car> getCars(String language) throws ServiceException;
	void blockUnblockCar(int carId) throws ServiceException;
}
