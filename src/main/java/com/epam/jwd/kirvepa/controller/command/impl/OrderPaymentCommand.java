package com.epam.jwd.kirvepa.controller.command.impl;

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

public class OrderPaymentCommand implements Command {
	private static final Logger logger = LogManager.getLogger(OrderPaymentCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		int userId = (int) request.getSession(false).getAttribute(RequestAttributeName.USR_ID);
		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		
		try {
			boolean success = orderService.payOrder(orderId, userId);
			
			if (!success) {
				request.setAttribute(RequestAttributeName.PERS_DATA_MSG
									, manager.getValue("personal_data.add.message"));
				
				return JSPPageName.PERSONAL_DATA;
			}
			else {
				request.setAttribute(RequestAttributeName.USR_HOME_MSG
									 , manager.getValue("user_home.order.paid"));
				
				return JSPPageName.USER_HOMEPAGE;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.payment"));
			
			return JSPPageName.ERROR_PAGE;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.USR_HOME_ERR
								 , manager.getValue("user_orders.payment.error")
								 + e.getMessage());
			
			return JSPPageName.USER_HOMEPAGE;
		}

	}

}
