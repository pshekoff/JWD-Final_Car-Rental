package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.exception.DAOException;

public interface CarDAO {
	List<String> getCarBodyList() throws DAOException;
	List<Car> getCarList(Date from, Date to, String[] bodies) throws DAOException;
}
