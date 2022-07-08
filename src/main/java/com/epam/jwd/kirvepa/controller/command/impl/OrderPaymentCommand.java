package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderPaymentCommand implements Command {
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	private static final Logger logger = LogManager.getLogger(OrderUpdationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int orderId = (int) request.getSession().getAttribute("order_id");
		
		try {
			boolean success = orderService.payOrder(orderId);
			
			if (!success) {
				logger.info("Command " + CommandName.ORDER_PAYMENT + " finished unsuccessfully.");
				request.setAttribute("message", "Payment failed.");
				return JSPPageName.ERROR_PAGE;
			}
			else {
				logger.info("Command " + CommandName.ORDER_PAYMENT + " finished successfully.");
				request.setAttribute("user_header", "Order has been payed.");
				return JSPPageName.USER_HOMEPAGE;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Payment failed.");
			return JSPPageName.ERROR_PAGE;
		}

	}

}
