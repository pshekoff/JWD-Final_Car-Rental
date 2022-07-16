package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderRegistrationCommand implements Command {
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	private static final Logger logger = LogManager.getLogger(OrderRegistrationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int userId = (int) request.getSession().getAttribute("user_id");
		System.out.println("user ID = " + userId);
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
			Order order = orderService.placeOrder(userId, car, dateFrom, dateTo, price);
			
			request.setAttribute("order_id", order.getId());

			if (order.getStatus().equals("PREPARED")) {
				return JSPPageName.PERSONAL_DATA_PAGE;
			} else if (order.getStatus().equals("CREATED")) {
				request.setAttribute("user_header", "Order has been created.");
				request.setAttribute("amount", order.getAmount());
				return JSPPageName.ORDER_CREATED;
			} else {
				logger.error("Unexpected error.");
				request.setAttribute("user_header", "Unexpected error.");
				return JSPPageName.USER_HOMEPAGE;
			}

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("user_header", "Failed to book selected car");
			return JSPPageName.USER_HOMEPAGE;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute("user_header", "Failed to book selected car. " + e.getMessage());
			return JSPPageName.USER_HOMEPAGE;
		}
	}
	
}
