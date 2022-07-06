package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderPreparationCommand implements Command {
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	private static final Logger logger = LogManager.getLogger(OrderPreparationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int userId = (int) request.getSession().getAttribute("user_id");

		//car data
		String manufacturer = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_MANUF);
		String model = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_MODEL);
		String bodyType = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_BODY);
		String engine = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_ENGINE);
		String transmission = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_TRANSM);
		String driveType = request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_DRIVE);
		
		double price = Double.parseDouble(request.getParameter(RequestParameterName.REQ_PARAM_NAME_CAR_PRICE));
		
		Date dateFrom = (Date) request.getSession().getAttribute(RequestParameterName.REQ_PARAM_NAME_DATEFROM);
		Date dateTo = (Date) request.getSession().getAttribute(RequestParameterName.REQ_PARAM_NAME_DATETO);

		Car car = new Car(manufacturer, model, bodyType, engine, transmission, driveType);
				
		try {
			int orderId = orderService.prepareOrder(userId, car, dateFrom, dateTo, price);
			
			HttpSession session = request.getSession();
			session.setAttribute("order_id", orderId);
			
			return JSPPageName.PERSONAL_DATA_PAGE;
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Failed to prepare order.");
			return JSPPageName.ERROR_PAGE;
		}
	}
	
}
