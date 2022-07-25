package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface CarDAO {
	List<String> getCarBodyList(String filter) throws DAOException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies) throws DAOException;
	void handoverReturnCar(int orderId) throws DAOException, DAOUserException;
	boolean insertCar(Car car) throws DAOException;
	List<Car> getCars() throws DAOException;
	void blockUnblockCar(int carId) throws DAOException;
}
