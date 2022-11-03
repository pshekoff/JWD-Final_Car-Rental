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
	private static final String ERROR = "get_orders.error";
	private static final String SESSION = "session.expired";
	private static final String FILTER_ALL = "all";
	private static final String FILTER_USER = "user";
	private static final String FILTER_NEW = "new";
	private static final String FILTER_HANDOVER_RETURN = "handover_return";
	
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
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
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		String filter = request.getParameter(RequestParameterName.ORDER_FILTER);
		String language = getLanguage(session);
		
		String error = manager.getValue(ERROR, request);
		
		try {
			List<Order> orders = orderService.getOrders(filter, userId, language);
			request.setAttribute(RequestAttributeName.ORDER_LIST, orders);
			
			if (filter.equals(FILTER_USER)) {
				return forward(JSPPageName.USER_ORDERS);
			}
			else if (filter.equals(FILTER_ALL)) {
				return forward(JSPPageName.ALL_ORDERS);
			}
			else if (filter.equals(FILTER_NEW)) {
				return forward(JSPPageName.NEW_ORDERS);
			}
			else if (filter.equals(FILTER_HANDOVER_RETURN)) {
				return forward(JSPPageName.CAR_HANDOVER_RETURN);
			}
			else {
				logger.error(RequestAttributeName.ERR, error);
				request.setAttribute(RequestAttributeName.ERR, error);
				return forward(JSPPageName.ERROR_PAGE);
			}

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR, error);
			return forward(JSPPageName.ERROR_PAGE);
		}
	}

}
