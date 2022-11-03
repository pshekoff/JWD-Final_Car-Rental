package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetCarsAddingInfoCommand implements Command {
	private static final String ERROR = "add_car.error";
	private static final String SESSION = "session.expired";
	
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	private static final Logger logger = LogManager.getLogger(GetCarBodyListCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(SESSION, request));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		String language = getLanguage(session);
		
		try {
			List<List<String>> carAddInfo = carService.GetCarsAddingInfo(language);
			
			session.setAttribute(RequestAttributeName.CAR_ADD_INFO, carAddInfo);
			return forward(JSPPageName.ADD_CAR);
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			return forward(JSPPageName.ERROR_PAGE);
		}
		
		

	}

}
