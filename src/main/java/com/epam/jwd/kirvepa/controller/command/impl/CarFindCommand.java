package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarFindCommand implements Command {
	private static final Logger logger = LogManager.getLogger(CarFindCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Date dateFrom = Date.valueOf(request.getParameter(RequestParameterName.DATE_FROM));
		Date dateTo = Date.valueOf(request.getParameter(RequestParameterName.DATE_TO));
		
		String[] bodies = request.getParameterValues(RequestParameterName.CAR_BODY);
		
		try {
			Map<Car, Double> cars = carService.getCarList(dateFrom, dateTo, bodies);
				
			request.setAttribute("cars", cars);
			request.setAttribute("date_from", dateFrom);
			request.setAttribute("date_to", dateTo);
			
			return JSPPageName.CAR_LIST;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR, manager.getValue("car_finder.carlist.error"));
			return JSPPageName.ERROR_PAGE;
		}
	}

}
