package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarBlockingUnblockingCommand implements Command {
	private static final String MESSAGE = "car.block_unblock.success";
	private static final String ERROR = "car.block_unblock.error";
	
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int carId = Integer.parseInt(request.getParameter(RequestParameterName.CAR_ID));
		try {
			carService.blockUnblockCar(carId);
		
			Map<String, String> parameters = new HashMap<>();
		
			parameters.put(RequestParameterName.MSG, MESSAGE);
			parameters.put(RequestParameterName.COMMAND, CommandName.GET_CARS.name());
		
			return redirect(JSPPageName.RESULT, parameters);
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);

		}
	}

}
