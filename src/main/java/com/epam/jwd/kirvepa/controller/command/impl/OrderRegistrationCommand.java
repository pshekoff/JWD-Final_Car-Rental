package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Car;
import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.Order.OrderStatus;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderRegistrationCommand implements Command {
	private static final String MESSAGE = "personal_data.new_order.message";
	private static final String ERROR = "error.order.creation";
	private static final String ORDER_HEADER = "order_created.header";
	private static final String SESSION = "session.expired";
	
	private static final Logger logger = LogManager.getLogger(OrderRegistrationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.AUTH_ERR
					, manager.getValue(SESSION, request));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		String language = getLanguage(session);

		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		
		String[] carParams = request.getParameter(RequestParameterName.CAR).split(";");
		double price = Double.parseDouble(carParams[6]);
		Date dateFrom = Date.valueOf(request.getParameter(RequestParameterName.DATE_FROM));
		Date dateTo = Date.valueOf(request.getParameter(RequestParameterName.DATE_TO));
		
		String manufacturer = carParams[0];
		String model = carParams[1];
		String bodyType = carParams[2];
		String engine = carParams[3];
		String transmission = carParams[4];
		String driveType = carParams[5];
		
		Car car = new Car(manufacturer, model, bodyType, engine, transmission, driveType);
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			Order order = orderService.registerOrder(userId, car, dateFrom, dateTo, price, language);

			if (order.getStatus().equals(OrderStatus.PREPARED)) {
				parameters.put(RequestParameterName.MSG, MESSAGE);
				return redirect(JSPPageName.PERSONAL_DATA, parameters);
				
			} else if (order.getStatus().equals(OrderStatus.CREATED)) {
				parameters.put(RequestParameterName.ORDER_ID, Integer.toString(order.getId()));
				parameters.put(RequestParameterName.ORDER_AMOUNT, Double.toString(order.getAmount()));
				parameters.put(RequestParameterName.HEAD, ORDER_HEADER);
				
				return redirect(JSPPageName.ORDER_CREATED, parameters);
				
			} else {
				logger.error(manager.getValue(ERROR, request));
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(ERROR, request));
				
				return forward(JSPPageName.ERROR_PAGE);
			}

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request) + e.getMessage());
			
			return forward(JSPPageName.NOTIFICATION);
		}
	}
	
}
