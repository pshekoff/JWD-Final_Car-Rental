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
			logger.error(manager.getValue("reg.error.pass.match"));
			request.setAttribute(RequestAttributeName.REG_ERR
								 , manager.getValue("reg.error.pass.match"));
			
			return JSPPageName.REGISTRATION;
		}

		boolean success;
		try {
			logger.info("Registration attempt with username (" + login + "), email(" + email + ")");
			success = userService.registration(new User(login, email, admin), password.hashCode());
			
			if (!success) {
				logger.error(manager.getValue("reg.error") + manager.getValue("error.unexpected"));
				request.setAttribute(RequestAttributeName.REG_ERR
									 , manager.getValue("reg.error"));
				
				return JSPPageName.REGISTRATION;
			}
			else {
				logger.info("User " + login + " has been registered.");
				request.setAttribute(RequestAttributeName.AUTH_MSG
								 	 , manager.getValue("reg.success.message"));
				
				return JSPPageName.AUTHORIZATION;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.REG_ERR
								 , manager.getValue("reg.error"));
			
			return JSPPageName.REGISTRATION;
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.REG_ERR
								 , manager.getValue("reg.error")
								 + e.getMessage());
			
			return JSPPageName.REGISTRATION;
		}

	}

}
