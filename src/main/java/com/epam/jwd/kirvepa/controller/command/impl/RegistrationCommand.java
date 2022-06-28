package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class RegistrationCommand implements Command {
	private static final String role = "User";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_LOGIN);
		String password = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_PASSWORD);
		String passwordRepeat = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_PASSWORD_REPEAT);
		String email = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_EMAIL);
		
		if (!password.equals(passwordRepeat)) {
			request.setAttribute("message", "Entered passwords aren't match.");
			return JSPPageName.REGISTER_FAIL;
		}
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		boolean success;
		try {
			success = userService.registration(new User(login, password.hashCode(), email, role));
			
			if (!success) {
				request.setAttribute("message", "Unknown error.");
				return JSPPageName.REGISTER_FAIL;
			}
			else {
				return JSPPageName.REGISTER_SUCCESS;
			}
			
		} catch (ServiceException e) {
			request.setAttribute("message", e);
			e.printStackTrace();  //temporary
			return JSPPageName.REGISTER_FAIL;
		}

	}

}
