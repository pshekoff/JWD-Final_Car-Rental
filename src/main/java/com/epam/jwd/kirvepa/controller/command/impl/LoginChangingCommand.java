package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class LoginChangingCommand implements Command {
	private static final Logger logger = LogManager.getLogger(LoginChangingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		String newLogin =  request.getParameter(RequestParameterName.USR_LOGIN);

		String error = manager.getValue("edit_profile.login.error");
		String message = manager.getValue("edit_profile.login.message");
		
		boolean success;
		try {
			success = userService.changeLogin(userId, newLogin);
			
			if (!success) {
				logger.error(error);
				request.setAttribute(RequestAttributeName.PROFILE_ERR, error);
				return JSPPageName.EDIT_PROFILE;
				
			} else {
				session.setAttribute(RequestAttributeName.USR_LOGIN, newLogin);
				logger.info(message + newLogin);
				request.setAttribute(RequestAttributeName.PROFILE_MSG, message + newLogin);
				return JSPPageName.EDIT_PROFILE;
			}
			
		} catch (ServiceException e) {
			logger.error(error + e);
			request.setAttribute(RequestAttributeName.PROFILE_ERR, error);
			return JSPPageName.EDIT_PROFILE;
			
		} catch (ServiceUserException e) {
			logger.error(error + e);
			request.setAttribute(RequestAttributeName.PROFILE_ERR, error + e.getMessage());
			return JSPPageName.EDIT_PROFILE;
		}
	}

}
