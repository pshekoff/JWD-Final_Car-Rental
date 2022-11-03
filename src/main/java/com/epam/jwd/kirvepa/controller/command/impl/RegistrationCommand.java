package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class RegistrationCommand implements Command {
	private static final boolean admin = false;
	private static final String ERROR = "reg.error";
	private static final String PASS_MISMATCH = "reg.error.pass.match";
	
	private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.USR_LOGIN);
		String password = request.getParameter(RequestParameterName.USR_PASS);
		String passwordRepeat = request.getParameter(RequestParameterName.USR_PASS_REPEAT);
		String email = request.getParameter(RequestParameterName.USR_EMAIL);
		
		if (!password.equals(passwordRepeat)) {
			logger.error(manager.getValue(PASS_MISMATCH, request));
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(PASS_MISMATCH, request));
			
			return forward(JSPPageName.REGISTRATION);
		}

		int userId;
		try {
			logger.info("Registration attempt with username (" + login + "), email(" + email + ")");
			userId = userService.registration(new User(login, email, admin), password.hashCode());
			
			if (userId == 0) {
				logger.error(manager.getValue(ERROR, request));
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(ERROR, request));
				
				return forward(JSPPageName.ERROR_PAGE);
			}
			else {
				logger.info("User " + login + "ID=" + userId + " has been registered.");
				return redirect(JSPPageName.REG_SUCCESS, null);
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request));
			
			return forward(JSPPageName.ERROR_PAGE);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(ERROR, request) + e.getMessage());
			
			return forward(JSPPageName.REGISTRATION);
		}

	}

}
