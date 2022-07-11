package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderUpdationCommand implements Command{
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	private static final Logger logger = LogManager.getLogger(OrderUpdationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		// user personal data
		String firstName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_F_NAME);
		String lastName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_L_NAME);
		Date dayOfBirth = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_BIRTHDAY));
		String docNum = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_NUM);
		Date docIssueDate = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_ISSUE_DATE));
		Date docExpDate = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_EXP_DATE));
		String IdNum = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_ID_NUM);
		String address = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_ADDRESS);
		String phone = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PHONE);

		int userId = (int) request.getSession().getAttribute("user_id");
		int orderId = (int) request.getSession().getAttribute("order_id");
		
		PersonalData personalData = new PersonalData(firstName, lastName, dayOfBirth, docNum, docIssueDate, docExpDate, IdNum, address, phone);
		
		try {
			Order order = orderService.updateOrder(userId, orderId, personalData);
			request.setAttribute("amount", order.getAmount());
			return JSPPageName.ORDER_CREATED;
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Failed to create order.");
			return JSPPageName.ERROR_PAGE;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute("message", "Failed to create order. " + e.getMessage());
			return JSPPageName.ERROR_PAGE;
		}

	}

}
