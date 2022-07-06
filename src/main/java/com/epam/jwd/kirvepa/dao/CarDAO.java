package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public interface CarDAO {
	List<String> getCarBodyList() throws DAOException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws DAOException;
}
