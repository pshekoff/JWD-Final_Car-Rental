package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
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
		int passwordHash = request.getParameter(RequestParameterName.USR_PASS)
								  .hashCode();

		logger.info("User \"" + login + "\" requested to login");
		
		AuthorizedUser authUser;
		
		try {
			authUser = userService.singIn(login, passwordHash);
			
			if (authUser != null) {
				
				HttpSession session = request.getSession(true);
				session.setAttribute(RequestAttributeName.USR_ID , authUser.getUserId());
				session.setAttribute(RequestAttributeName.USR_LOGIN, authUser.getLogin());
				session.setAttribute(RequestAttributeName.USR_EMAIL, authUser.getEmail());
				session.setAttribute(RequestAttributeName.USR_ROLE, authUser.isAdmin());
				
				logger.info(authUser.toString() + " authorized.");
				
				if (authUser.isAdmin()) {
					request.setAttribute(RequestAttributeName.ADM_HEAD
										 , manager.getValue("label.welcome")
										 + authUser.getLogin());
					
					return JSPPageName.ADMIN_HOMEPAGE;
				}
				else {
					request.setAttribute(RequestAttributeName.USR_HEAD
										 , manager.getValue("label.welcome")
										 + authUser.getLogin());
					
					return JSPPageName.USER_HOMEPAGE;
				}

			}
			else {
				logger.error(manager.getValue("error.unexpected")
							+ manager.getValue("auth.error.null"));
				
				request.setAttribute(RequestAttributeName.AUTH_ERR
									 , manager.getValue("auth.error"));
				
				return JSPPageName.AUTHORIZATION;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.AUTH_ERR
								 , manager.getValue("auth.error"));
			
			return JSPPageName.AUTHORIZATION;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.AUTH_ERR
								 , manager.getValue("auth.error") + e.getMessage());
			
			return JSPPageName.AUTHORIZATION;
		}

	}

}
