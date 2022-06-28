package com.epam.jwd.kirvepa.service;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.service.exception.ServiceException;

public interface CarService {
	List<String> getCarBodyList() throws ServiceException;
	List<Car> getCarList(Date from, Date to, String[] bodies) throws ServiceException;
}
