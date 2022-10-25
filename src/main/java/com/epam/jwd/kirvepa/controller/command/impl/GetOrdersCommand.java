package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.AUTH_ERR
					 , manager.getValue("session.expired"));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		String filter = request.getParameter(RequestParameterName.ORDER_FILTER);
		
		try {
			List<Order> orders = orderService.getOrders(filter, userId);
			request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
			
			if (filter.equals("user")) {
				return forward(JSPPageName.USER_ORDERS);
			}
			else if (filter.equals("all")) {
				return forward(JSPPageName.ALL_ORDERS);
			}
			else if (filter.equals("new")) {
				return forward(JSPPageName.NEW_ORDERS);
			}
			else if (filter.equals("handover_return")) {
				return forward(JSPPageName.CAR_HANDOVER_RETURN);
			}
			else {
				logger.error(RequestAttributeName.ERR, manager.getValue("new_orders.error"));
				request.setAttribute(RequestAttributeName.ERR, manager.getValue("new_orders.error"));
				return forward(JSPPageName.ERROR_PAGE);
			}

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR, manager.getValue("get_orders.error"));
			return forward(JSPPageName.ERROR_PAGE);
		}
	}

}
