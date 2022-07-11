package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class PasswordChangingCommand implements Command {
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(PasswordChangingCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		int userId = (int) request.getSession().getAttribute("user_id");
		String password = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS);
		String passwordRepeat = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS_REPEAT);
		
		if (!password.equals(passwordRepeat)) {
			request.setAttribute("error", "Entered passwords aren't match.");
			return JSPPageName.EDIT_PROFILE;
		}

		boolean success;
		try {
			success = userService.changePassword(userId, password.hashCode());
			
			if (!success) {
				logger.error("Password changing failed.");
				request.setAttribute("error", "Password changing failed.");
				return JSPPageName.EDIT_PROFILE;
			} else {
				logger.error("Password successfully changed.");
				request.setAttribute("message", "Password successfully changed.");
				return JSPPageName.EDIT_PROFILE;
			}
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("error", "Password changing failed.");
			return JSPPageName.EDIT_PROFILE;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute("error", "Password changing failed. " + e.getMessage());
			return JSPPageName.EDIT_PROFILE;
		}
	}

}
