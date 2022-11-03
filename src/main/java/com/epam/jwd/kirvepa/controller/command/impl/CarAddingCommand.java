package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarAddingCommand implements Command {
	private static final boolean available = true;
	private static final String MESSAGE = "add_car.success.message";
	private static final String ERROR = "error.car.adding";
	
	private static final Logger logger = LogManager.getLogger(EmployeeAddingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//car data
		String manufacturer = request.getParameter(RequestParameterName.CAR_MANUF);
		String model = request.getParameter(RequestParameterName.CAR_MODEL);
		String licensePlate = request.getParameter(RequestParameterName.CAR_LPN);
		String vin = request.getParameter(RequestParameterName.CAR_VIN);
		String bodyType = request.getParameter(RequestParameterName.CAR_BODY);
		int issueYear = Integer.parseInt(request.getParameter(RequestParameterName.CAR_ISSUE_YEAR));
		String engine = request.getParameter(RequestParameterName.CAR_ENGINE);
		String transmission = request.getParameter(RequestParameterName.CAR_TRANSM);
		String driveType = request.getParameter(RequestParameterName.CAR_DRIVE);
		String color = request.getParameter(RequestParameterName.CAR_COLOR);
		int weight = Integer.parseInt(request.getParameter(RequestParameterName.CAR_WEIGHT));
		
		Car car = new Car(manufacturer
						 , model
						 , licensePlate
						 , vin
						 , bodyType
						 , issueYear
						 , engine
						 , transmission
						 , driveType
						 , color
						 , weight
						 , available);
		
		String language = getLanguage(request.getSession(false));
		
		Map<String, String> parameters = new HashMap<>();
		
		boolean success;
		try {
			success = carService.addCar(car, language);
			
			if (!success) {
				logger.error(manager.getValue(ERROR, request));
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(ERROR, request));

				return forward(JSPPageName.ERROR_PAGE);	
				
			} else {
				logger.info("New car " + car.toString() + " has been added.");
				parameters.put(RequestParameterName.MSG, MESSAGE);
				return redirect(JSPPageName.NOTIFICATION, parameters);
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);	
		}
	}

}
