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
	private static final Logger logger = LogManager.getLogger(OrderRegistrationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.AUTH_ERR
					 , manager.getValue("session.expired"));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
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
			Order order = orderService.registerOrder(userId, car, dateFrom, dateTo, price);

			if (order.getStatus().equals(OrderStatus.PREPARED)) {
				parameters.put(RequestAttributeName.PERS_DATA_MSG
							   , manager.getValue("personal_data.new_order.message"));
	
				return redirect(JSPPageName.PERSONAL_DATA, parameters);
				
			} else if (order.getStatus().equals(OrderStatus.CREATED)) {
				
				parameters.put(RequestAttributeName.ORDER_ID, Integer.toString(order.getId()));
				parameters.put(RequestAttributeName.ORDER_AMOUNT, Double.toString(order.getAmount()));
				parameters.put(RequestAttributeName.ORDER_HEAD
						 	   , manager.getValue("order_created.header"));
				
				return redirect(JSPPageName.ORDER_CREATED, parameters);
				
			} else {
				logger.error(manager.getValue("error.unexpected")
							+ manager.getValue("error.order.creation"));
				
				parameters.put(RequestAttributeName.ERR
			 			 	   , manager.getValue("error.order.creation"));

				return redirect(JSPPageName.ERROR_PAGE, parameters);
			}

		} catch (ServiceException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.ERR
	 			 	   , manager.getValue("error.order.creation"));
			
			return redirect(JSPPageName.ERROR_PAGE, parameters);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.ERR
	 			 	   	   , manager.getValue("error.order.creation")
	 			 	   	   + e.getMessage());
			
			return redirect(JSPPageName.ERROR_PAGE, parameters);
		}
	}
	
}
