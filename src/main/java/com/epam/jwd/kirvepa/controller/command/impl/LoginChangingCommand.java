package com.epam.jwd.kirvepa.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class LoginChangingCommand implements Command {
	private static final String MESSAGE = "edit_profile.login.message";
	private static final String ERROR = "edit_profile.login.error";
	private static final String SAME_LOGIN = "edit_profile.login.same";
	private static final String LOGIN_ROOT = "edit_profile.login.root";
	private static final String ROOT = "root";
	private static final String SESSION = "session.expired";

	private static final Logger logger = LogManager.getLogger(LoginChangingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.AUTH_ERR
					 , manager.getValue(SESSION, request));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		String currentLogin = (String) session.getAttribute(RequestAttributeName.USR_LOGIN);
		String newLogin =  request.getParameter(RequestParameterName.USR_LOGIN).trim();

		String error = manager.getValue(ERROR, request);
		String message = manager.getValue(MESSAGE, request);
		String sameLogins = manager.getValue(SAME_LOGIN, request);
		String root = manager.getValue(LOGIN_ROOT, request);
		
		if (currentLogin.equals(ROOT)) {
			logger.error(error + root);
			request.setAttribute(RequestAttributeName.ERR, error + root);
			return forward(JSPPageName.EDIT_PROFILE);
		}
		if (newLogin.equals(currentLogin)) {
			logger.error(error + sameLogins);
			request.setAttribute(RequestAttributeName.ERR, error + sameLogins);
			return forward(JSPPageName.EDIT_PROFILE);
		}
		
		Map<String, String> parameters = new HashMap<>();
		
		boolean success;
		try {
			success = userService.changeLogin(userId, newLogin);
			
			if (!success) {
				logger.error(error);
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(error, request));

				return forward(JSPPageName.ERROR_PAGE);
				
			} else {
				session.setAttribute(RequestAttributeName.USR_LOGIN, newLogin);
				logger.info(message + newLogin);
				parameters.put(RequestParameterName.MSG, message);

				return redirect(JSPPageName.EDIT_PROFILE, parameters);
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(error, request));

			return forward(JSPPageName.ERROR_PAGE);
			
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(error, request) + e.getMessage());
			
			return forward(JSPPageName.EDIT_PROFILE);
		}
	}

}
