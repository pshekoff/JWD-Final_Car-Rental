package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Order;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.OrderService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class OrderCreationCommand implements Command{
	private static final Logger logger = LogManager.getLogger(OrderCreationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final OrderService orderService = ServiceFactory.getInstance().getOrderService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//user personal data
		String firstName = request.getParameter(RequestParameterName.USR_F_NAME);
		String lastName = request.getParameter(RequestParameterName.USR_L_NAME);
		Date dayOfBirth = Date.valueOf(request.getParameter(RequestParameterName.USR_BIRTHDAY));
		String docNum = request.getParameter(RequestParameterName.USR_DOC_NUM);
		Date docIssueDate = Date.valueOf(request.getParameter(RequestParameterName.USR_DOC_ISSUE_DATE));
		Date docExpDate = Date.valueOf(request.getParameter(RequestParameterName.USR_DOC_EXP_DATE));
		String IdNum = request.getParameter(RequestParameterName.USR_ID_NUM);
		String address = request.getParameter(RequestParameterName.USR_ADDRESS);
		String phone = request.getParameter(RequestParameterName.USR_PHONE);

		int userId = (int) request.getSession().getAttribute(RequestAttributeName.USR_HEAD);
		int orderId = Integer.parseInt(request.getParameter(RequestParameterName.ORDER_ID));
		
		PersonalData personalData = new PersonalData(firstName, lastName, dayOfBirth, docNum
													, docIssueDate, docExpDate, IdNum, address, phone);
		
		try {
			Order order = orderService.createOrder(userId, orderId, personalData);
			
			request.setAttribute(RequestAttributeName.ORDER_ID, order.getId());
			request.setAttribute(RequestAttributeName.ORDER_AMOUNT, order.getAmount());
			
			return JSPPageName.ORDER_CREATED;
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.order.creation"));
			
			return JSPPageName.ERROR_PAGE;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("error.car.booking")
								 + e.getMessage());
			
			return JSPPageName.ERROR_PAGE;
		}

	}

}
