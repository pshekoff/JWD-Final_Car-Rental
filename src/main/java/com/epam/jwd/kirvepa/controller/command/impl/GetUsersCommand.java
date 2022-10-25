package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class GetUsersCommand implements Command {
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> parameters = new HashMap<>();
		
		try {
			List<User> users = userService.getUsers();
			
			request.setAttribute(RequestAttributeName.USR_LIST, users);
			
			return forward(JSPPageName.USER_LIST);
			
		} catch (ServiceException e) {
			logger.error(e);
			parameters.put(RequestAttributeName.ERR
			 	   	  	  , manager.getValue("user_list.error"));
	
			return redirect(JSPPageName.ERROR_PAGE, parameters);

		}
		
	}

}
