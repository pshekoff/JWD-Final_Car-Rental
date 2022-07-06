package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class AuthorizationCommand implements Command {
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(AuthorizationCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_LOGIN);
		int passwordHash = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS).hashCode();

		AuthorizedUser authUser;
		
		try {
			authUser = userService.singIn(login, passwordHash);
			
			if (authUser != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("user_id", authUser.getUserId());
				session.setAttribute("login", authUser.getLogin());
				session.setAttribute("email", authUser.getEmail());
				session.setAttribute("admin", authUser.isAdmin());

				if (authUser.isAdmin()) {
					logger.info("Command " + CommandName.AUTHORIZATION + " with administrator role finished successfully.");
					return JSPPageName.ADMIN_AUTH_PAGE;
				}
				else {
					logger.info("Command " + CommandName.AUTHORIZATION + " with user role finished successfully.");
					return JSPPageName.USER_AUTH_PAGE;
				}

			}
			else {
				logger.warn("Command " + CommandName.AUTHORIZATION + " finished unsuccessfully.");
				request.setAttribute("message", "Authorization failed.");
				return JSPPageName.AUTH_PAGE_FAIL;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", e.getMessage());
			return JSPPageName.AUTH_PAGE_FAIL;
		}

	}

}
