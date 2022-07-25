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
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderApprovingCommand implements Command {
	private static final Logger logger = LogManager.getLogger(OrderCreationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		
		try {
			orderService.approveOrder(orderId);
			List<Order> orders = orderService.getOrders("new");
			
			request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
			request.setAttribute(RequestAttributeName.NEW_ORDERS
		 			 , orderId + manager.getValue("new_orders.order.approved"));

			return JSPPageName.NEW_ORDERS;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.approving"));
			
			return JSPPageName.ERROR_PAGE;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.approving")
								 + e.getMessage());
			
			return JSPPageName.ERROR_PAGE;
		}
	}

}
