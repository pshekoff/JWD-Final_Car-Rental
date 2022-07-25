package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.CommandProvider;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarBlockingUnblockingCommand implements Command {
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int carId = Integer.parseInt(request.getParameter(RequestParameterName.CAR_ID));
		
		try {
			carService.blockUnblockCar(carId);
			
			String page = CommandProvider.getInstance()
	   				 					 .getCommand(CommandName.GET_CARS.name())
	   				 					 .execute(request, response);

			return page;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								, manager.getValue("car.block_unblock.error"));
			return JSPPageName.ERROR_PAGE;
		}
	}

}
