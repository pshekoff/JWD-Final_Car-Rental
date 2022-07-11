package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class LoginChangingCommand implements Command {
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(LoginChangingCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int) request.getSession().getAttribute("user_id");
		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_LOGIN);

		boolean success;
		try {
			success = userService.changeLogin(userId, login);
			
			if (!success) {
				logger.error("Login changing failed.");
				request.setAttribute("error", "Login changing failed.");
				return JSPPageName.EDIT_PROFILE;
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("login", login);

				logger.error("Login successfully changed.");
				request.setAttribute("message", "Login successfully changed.");
				return JSPPageName.EDIT_PROFILE;
			}
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("error", "Login changing failed.");
			return JSPPageName.EDIT_PROFILE;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute("error", "Login changing failed. " + e.getMessage());
			return JSPPageName.EDIT_PROFILE;
		}
	}

}
