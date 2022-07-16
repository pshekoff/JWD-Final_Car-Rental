package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetOrdersCommand implements Command {
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int) request.getSession().getAttribute("user_id");
		
		try {
			List<Order> orders =  orderService.getUserOrders(userId);
			request.getSession().setAttribute("orders", orders);
			
			return JSPPageName.USER_ORDERS;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.getSession().setAttribute("user_error", "Failed to get orders.");
			return JSPPageName.USER_HOMEPAGE;
		}

	}

}
