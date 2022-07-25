package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetCarBodyListCommand implements Command {
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	private static final Logger logger = LogManager.getLogger(GetCarBodyListCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String filter = request.getParameter(RequestParameterName.CAR_BODY_FILTER);
		String nextPage = request.getParameter(RequestParameterName.NEXT_PAGE);
		
		try {
			List<String> bodyList = carService.getCarBodyList(filter);
			request.setAttribute(RequestAttributeName.CAR_BODY_LIST
								 , bodyList);
			
			return nextPage;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR, manager.getValue("car_finder.bodylist.error"));
			return JSPPageName.ERROR_PAGE;
		}

	}

}
