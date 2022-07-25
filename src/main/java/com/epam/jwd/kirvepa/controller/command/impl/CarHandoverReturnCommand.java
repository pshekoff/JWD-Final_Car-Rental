package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.CarService;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class CarHandoverReturnCommand implements Command {
	private static final Logger logger = LogManager.getLogger(OrderCreationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final CarService carService = ServiceFactory.getInstance().getCarService();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		
		try {
			carService.handoverReturnCar(orderId);
			List<Order> orders = orderService.getOrders("handover_return");
			
			request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
			request.setAttribute(RequestAttributeName.CAR_HANDOVER_RETURN
		 			 , manager.getValue("car_handover_return.complete"));

			return JSPPageName.CAR_HANDOVER_RETURN;
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.car.handover_return"));
			
			return JSPPageName.ERROR_PAGE;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.car.handover_return")
								 + e.getMessage());
			
			return JSPPageName.ERROR_PAGE;
		}
		

	}

}
