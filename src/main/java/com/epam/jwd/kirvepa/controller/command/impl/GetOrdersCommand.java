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
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetOrdersCommand implements Command{
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int) request.getSession().getAttribute(RequestAttributeName.USR_ID);
		String filter = request.getParameter(RequestParameterName.ORDER_FILTER);
		
		try {
			List<Order> orders = orderService.getOrders(filter, userId);
			request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
			
			if (filter.equals("user")) {
				return JSPPageName.USER_ORDERS;
			}
			else if (filter.equals("all")) {
				return JSPPageName.ALL_ORDERS;
			}
			else if (filter.equals("new")) {
				return JSPPageName.NEW_ORDERS;
			}
			else if (filter.equals("handover_return")) {
				return JSPPageName.CAR_HANDOVER_RETURN;
			}
			else {
				logger.error(RequestAttributeName.ERR, manager.getValue("new_orders.error"));
				request.setAttribute(RequestAttributeName.ERR, manager.getValue("new_orders.error"));
				return JSPPageName.ERROR_PAGE;
			}

			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR, manager.getValue("get_orders.error"));
			return JSPPageName.ERROR_PAGE;
		}
	}

}
