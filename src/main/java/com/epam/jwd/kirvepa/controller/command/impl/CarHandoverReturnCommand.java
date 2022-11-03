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
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarHandoverReturnCommand implements Command {
	private static final String MESSAGE = "car_handover_return.complete";
	private static final String ERROR = "error.car.handover_return";
	private static final String FILTER_HANDOVER_RETURN = "handover_return";
	
	private static final Logger logger = LogManager.getLogger(CarHandoverReturnCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		
		try {
			carService.handoverReturnCar(orderId);
			
			Map<String, String> parameters = new HashMap<>();
			
			parameters.put(RequestParameterName.MSG, MESSAGE);
			parameters.put(RequestParameterName.COMMAND, CommandName.GET_ORDERS.name());
			parameters.put(RequestParameterName.ORDER_FILTER, FILTER_HANDOVER_RETURN);
		
			return redirect(JSPPageName.RESULT, parameters);

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request) + e.getMessage());
			
			return forward(JSPPageName.ERROR_PAGE);
		}
		

	}

}
