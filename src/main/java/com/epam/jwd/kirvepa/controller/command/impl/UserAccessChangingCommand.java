package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.CommandProvider;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class UserAccessChangingCommand implements Command {
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = Integer.parseInt(request.getParameter(RequestParameterName.USR_ID));
		
		try {
			userService.changeUserAccess(userId);
			
			String page = CommandProvider.getInstance()
						   				 .getCommand(CommandName.GET_USERS.name())
						   				 .execute(request, response);
			
			return page;

		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								, manager.getValue("user_list.access_change.error"));
			return JSPPageName.ERROR_PAGE;
		} 

	}

}
