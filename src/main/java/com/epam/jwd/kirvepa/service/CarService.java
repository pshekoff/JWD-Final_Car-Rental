package com.epam.jwd.kirvepa.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.service.exception.ServiceException;

public interface CarService {
	List<String> getCarBodyList() throws ServiceException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws ServiceException;
}
