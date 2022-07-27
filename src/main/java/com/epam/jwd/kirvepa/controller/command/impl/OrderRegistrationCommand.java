package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		int userId = (int) request.getSession().getAttribute(RequestAttributeName.USR_ID);
		
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
		
		try {
			Order order = orderService.registerOrder(userId, car, dateFrom, dateTo, price);
			
			request.setAttribute(RequestAttributeName.ORDER_ID, order.getId());

			if (order.getStatus().equals(OrderStatus.PREPARED)) {
				request.setAttribute(RequestAttributeName.PERS_DATA_MSG
									, manager.getValue("user_home.order.created")
									+ manager.getValue("personal_data.add.message"));
	
				return JSPPageName.PERSONAL_DATA;
				
			} else if (order.getStatus().equals(OrderStatus.CREATED)) {
				
				request.setAttribute(RequestAttributeName.ORDER_HEAD
									 , manager.getValue("order_created.message"));
				
				request.setAttribute(RequestAttributeName.ORDER_AMOUNT, order.getAmount());
				
				return JSPPageName.ORDER_CREATED;
				
			} else {
				logger.error(manager.getValue("error.unexpected")
							+ manager.getValue("error.order.creation"));
				
				request.setAttribute(RequestAttributeName.ERR
						 			 , manager.getValue("error.order.creation"));

				return JSPPageName.ERROR_PAGE;
			}

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.creation"));
			
			return JSPPageName.ERROR_PAGE;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.creation")
								 + e.getMessage());
			
			return JSPPageName.ERROR_PAGE;
		}
	}
	
}
