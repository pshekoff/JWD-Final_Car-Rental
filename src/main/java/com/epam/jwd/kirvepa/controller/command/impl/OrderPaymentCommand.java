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
	private static final Logger logger = LogManager.getLogger(OrderPaymentCommand.class);
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
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
			int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
			
			boolean success = orderService.payOrder(orderId, userId);
			
			if (!success) {
				parameters.put(RequestAttributeName.PERS_DATA_MSG
							   , manager.getValue("personal_data.add.message"));
				
				return redirect(JSPPageName.PERSONAL_DATA, parameters);
			}
			else {
				parameters.put(RequestAttributeName.NOTIFICATION_MSG
						 	   , manager.getValue("notification.order.paid"));
				
				return redirect(JSPPageName.NOTIFICATION, parameters);
			}
			
		} catch (NumberFormatException e) {
			logger.error(e);
			
			request.setAttribute(RequestAttributeName.USR_ORDERS_ERR
								, manager.getValue("user_orders.payment.error")
								+ manager.getValue("user_orders.order.absent"));
			
			return forward(JSPPageName.USER_ORDERS);
			
		} catch (ServiceException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.ERR
					 	   , manager.getValue("error.order.payment"));
			
			return redirect(JSPPageName.ERROR_PAGE, parameters);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.NOTIFICATION_MSG
					 	   , manager.getValue("notification.error")
					 	   + e.getMessage());
	
			return redirect(JSPPageName.NOTIFICATION, parameters);
		}

	}

}
