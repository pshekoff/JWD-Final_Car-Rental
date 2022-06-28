package com.epam.jwd.kirvepa.service.factory;

import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.impl.CarServiceImpl;
import com.epam.jwd.kirvepa.service.impl.UserServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	
	private final UserService userService = new UserServiceImpl();
	private final CarService carService = new CarServiceImpl();
	
	private ServiceFactory(){
	}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public UserService getUserService(){
		return userService;
	}
	public CarService getCarService(){
		return carService;
	}
	
}
