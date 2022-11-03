package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderCancellationCommand implements Command {
	private static final String MESSAGE = "notification.order.cancelled";
	private static final String ERROR = "error.order.cancellation";
	private static final String ERROR_ORDER_CANCEL = "user_orders.cancel.error";
	private static final String ERROR_ORDER_ABSENT = "user_orders.order.absent";
	
	private static final Logger logger = LogManager.getLogger(OrderCancellationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
			orderService.cancelOrder(orderId);
			parameters.put(RequestParameterName.MSG, MESSAGE);		
			return redirect(JSPPageName.NOTIFICATION, parameters);
			
		} catch (NumberFormatException e) {
			logger.error(e);
			
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR_ORDER_CANCEL, request)
						+ manager.getValue(ERROR_ORDER_ABSENT, request));
			
			return forward(JSPPageName.USER_ORDERS);
			
		}  catch (ServiceException e) {
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
