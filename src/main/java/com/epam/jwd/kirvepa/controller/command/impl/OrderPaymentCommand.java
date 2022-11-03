package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

public class OrderPaymentCommand implements Command {
	private static final String MESSAGE_PERS_DATA = "personal_data.add.message";
	private static final String MESSAGE_NOTE = "notification.order.paid";
	private static final String ERROR = "error.order.payment";
	private static final String ERROR_ORDER_PAYMENT = "user_orders.payment.error";
	private static final String ERROR_ORDER_ADSENT = "user_orders.order.absent";
	private static final String SESSION = "session.expired";
	
	private static final Logger logger = LogManager.getLogger(OrderPaymentCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(SESSION, request));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
			int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
			
			boolean success = orderService.payOrder(orderId, userId);
			
			if (!success) {
				parameters.put(RequestParameterName.MSG, MESSAGE_PERS_DATA);
				return redirect(JSPPageName.PERSONAL_DATA, parameters);
			}
			else {
				parameters.put(RequestParameterName.MSG, MESSAGE_NOTE);
				return redirect(JSPPageName.NOTIFICATION, parameters);
			}
			
		} catch (NumberFormatException e) {
			logger.error(e);
			
			request.setAttribute(RequestAttributeName.USR_ORDERS_ERR
					, manager.getValue(ERROR_ORDER_PAYMENT, request)
						+ manager.getValue(ERROR_ORDER_ADSENT, request));
			
			return forward(JSPPageName.USER_ORDERS);
			
		} catch (ServiceException e) {
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
