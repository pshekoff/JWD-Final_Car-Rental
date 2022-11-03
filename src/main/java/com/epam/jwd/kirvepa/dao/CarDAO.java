package com.epam.jwd.kirvepa.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.dao.exception.DAOException;
import com.epam.jwd.kirvepa.dao.exception.DAOUserException;

public interface CarDAO {
	List<String> getCarBodyList(String language) throws DAOException;
	List<List<String>> GetCarsAddingInfo(String language) throws DAOException;
	Map<Car, Double> getCarList(Date from, Date to, String[] bodies, String language) throws DAOException;
	void handoverReturnCar(int orderId) throws DAOException, DAOUserException;
	boolean insertCar(Car car, String language) throws DAOException;
	List<Car> getCars(String language) throws DAOException;
	void blockUnblockCar(int carId) throws DAOException;
}
