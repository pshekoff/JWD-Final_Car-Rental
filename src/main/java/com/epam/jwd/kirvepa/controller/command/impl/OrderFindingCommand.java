package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.ArrayList;
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
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderFindingCommand implements Command {
	private static final String MESSAGE = "car_handover_return.search.null";
	private static final String ERROR = "error.order.finding";
	
	private static final Logger logger = LogManager.getLogger(CarFindingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		String language = getLanguage(request.getSession(false));
		
		try {
			Order order = orderService.findOrder(orderId, language);
			
			if (order == null) {
				request.setAttribute(RequestAttributeName.MSG, manager.getValue(MESSAGE, request));
				return forward(JSPPageName.CAR_HANDOVER_RETURN);
				
			} else {
				List<Order> orders = new ArrayList<>();
				orders.add(order);
				
				request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
				
				return forward(JSPPageName.CAR_HANDOVER_RETURN);
			}
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);
		}

	}

}
