package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetCarsCommand implements Command {
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Map<String, String> parameters = new HashMap<>();
		
		try {
			List<Car> cars = carService.getCars();
			
			request.setAttribute(RequestAttributeName.CAR_LIST, cars);
			
			return forward(JSPPageName.CAR_LIST_ALL);
			
		} catch (ServiceException e) {
			logger.error(e);
			parameters.put(RequestAttributeName.ERR
		 	   	  	  , manager.getValue("car_list.error"));
			
			return redirect(JSPPageName.ERROR_PAGE, parameters);
		}
	}

}
