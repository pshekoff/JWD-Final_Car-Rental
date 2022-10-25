package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class PersonalDataAddingCommand implements Command{
	private static final Logger logger = LogManager.getLogger(PersonalDataAddingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
		
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.AUTH_ERR
					 , manager.getValue("session.expired"));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		
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
		
		PersonalData personalData = new PersonalData(firstName, lastName, dayOfBirth, docNum
													, docIssueDate, docExpDate, IdNum, address, phone);
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			userService.addPersonalData(userId, personalData);
			
			parameters.put(RequestAttributeName.NOTIFICATION_MSG
					 	   , manager.getValue("add_pers_data.success.message"));
			
			return redirect(JSPPageName.NOTIFICATION, parameters);
			
		} catch (ServiceException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.ERR
						   , manager.getValue("add_pers_data.error"));
			
			return redirect(JSPPageName.ERROR_PAGE, parameters);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			
			parameters.put(RequestAttributeName.PERS_DATA_ERR
					 	   , manager.getValue("add_pers_data.error")
					 	   + e.getMessage());
			
			return redirect(JSPPageName.PERSONAL_DATA, parameters);
		}
	}

}
