package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class RegistrationCommand implements Command {
	private static final boolean admin = false;
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_LOGIN);
		String password = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS);
		String passwordRepeat = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS_REPEAT);
		String email = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_EMAIL);
		
		if (!password.equals(passwordRepeat)) {
			request.setAttribute("message", "Entered passwords aren't match.");
			return JSPPageName.REGISTER_FAIL;
		}

		boolean success;
		try {
			success = userService.registration(new User(login, email, admin), password.hashCode());
			
			if (!success) {
				logger.info("Command " + CommandName.REGISTRATION + " finished unsuccessfully.");
				request.setAttribute("message", "Registration failed");
				return JSPPageName.REGISTER_FAIL;
			}
			else {
				logger.info("Command " + CommandName.REGISTRATION + " finished successfully.");
				return JSPPageName.REGISTER_SUCCESS;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Registration failed");
			return JSPPageName.REGISTER_FAIL;
		}

	}

}
