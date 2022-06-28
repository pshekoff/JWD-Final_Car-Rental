package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetBodyListCommand implements Command {
	
	private static final CarService carService = ServiceFactory.getInstance().getCarService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		try {
			List<String> bodyList = carService.getCarBodyList();
			request.getSession().setAttribute("bodylist", bodyList);
			
			return JSPPageName.CAR_FINDER;
			
		} catch (ServiceException e) {
			request.setAttribute("message", e);
			e.printStackTrace(); //temporary
			return JSPPageName.ERROR_PAGE;
		}

	}

}
