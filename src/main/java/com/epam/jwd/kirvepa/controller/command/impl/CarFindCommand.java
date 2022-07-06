package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarFindCommand implements Command {
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	private static final Logger logger = LogManager.getLogger(CarFindCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Date dateFrom = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_DATEFROM));
		Date dateTo = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_DATETO));
		
		String[] bodies = request.getParameterValues(RequestParameterName.REQ_PARAM_NAME_CAR_BODY);
		
		try {
			Map<Car, Double> cars = carService.getCarList(dateFrom, dateTo, bodies);
				
			HttpSession session = request.getSession();
			session.setAttribute("cars", cars);
			session.setAttribute("date_from", dateFrom);
			session.setAttribute("date_to", dateTo);
			
			return JSPPageName.CAR_LIST;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Failed to get car list.");
			return JSPPageName.ERROR_PAGE;
		}
	}

}
