package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class AuthorizationCommand implements Command {
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.USR_LOGIN);
		int passwordHash = request.getParameter(RequestParameterName.USR_PASS).hashCode();
		
		logger.info("User \"" + login + "\" requested to login");
		
		User user;
		try {
			user = userService.authorization(login, passwordHash);
			
			if (user != null) {
				
				HttpSession session = request.getSession(true);
				session.setAttribute(RequestAttributeName.USR_ID , user.getUserId());
				session.setAttribute(RequestAttributeName.USR_LOGIN, user.getLogin());
				session.setAttribute(RequestAttributeName.USR_EMAIL, user.getEmail());
				session.setAttribute(RequestAttributeName.USR_ROLE, user.isAdmin());
				
				logger.info(user.toString() + " has been authorized.");
				
				if (user.isAdmin()) {
					return forward(JSPPageName.ADMIN_HOMEPAGE);
				}
				else {
					return forward(JSPPageName.USER_HOMEPAGE);
				}

			}
			else {
				logger.error(manager.getValue("error.unexpected")
							+ manager.getValue("auth.error.null"));
				
				request.setAttribute(RequestAttributeName.ERR
									 , manager.getValue("auth.error"));
				
				return forward(JSPPageName.ERROR_PAGE);
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("auth.error"));
			
			return forward(JSPPageName.ERROR_PAGE);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.AUTH_ERR
								 , manager.getValue("auth.error") + e.getMessage());
			
			return forward(JSPPageName.AUTHORIZATION);
		}

	}

}
