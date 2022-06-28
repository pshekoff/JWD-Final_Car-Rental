package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.tribes.util.Arrays;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarFindCommand implements Command {
	
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Date dateFrom = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_DATEFROM));
		Date dateTo = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_DATETO));
		
		String[] bodies = request.getParameterValues(RequestParameterName.REQ_PARAM_NAME_BODY);
		
		System.out.println(Arrays.toString(bodies)); //temp
		
		try {
			List<Car> cars = carService.getCarList(dateFrom, dateTo, bodies);
			
			HttpSession session = request.getSession();

			session.setAttribute("cars", cars);
			session.setAttribute("datefrom", dateFrom);
			session.setAttribute("dateto", dateTo);
			
			return JSPPageName.CAR_LIST;
			
		} catch (ServiceException e) {
			request.setAttribute("message", e.getMessage());
			e.printStackTrace(); //temporary
			return JSPPageName.ERROR_PAGE;
		}
	}

}
