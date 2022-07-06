package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetBodyListCommand implements Command {
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	private static final Logger logger = LogManager.getLogger(GetBodyListCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<String> bodyList = carService.getCarBodyList();
			request.getSession().setAttribute("bodylist", bodyList);
			
			return JSPPageName.CAR_FINDER;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Failed to get car body types.");
			return JSPPageName.ERROR_PAGE;
		}

	}

}
