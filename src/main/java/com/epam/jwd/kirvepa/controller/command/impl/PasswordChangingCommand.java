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

public class PasswordChangingCommand implements Command {
	private static final String MESSAGE = "edit_profile.pass.message";
	private static final String ERROR = "edit_profile.pass.error";
	private static final String PASS_MISMATCH = "edit_profile.pass.match.error";
	private static final String SESSION = "session.expired";
	
	private static final Logger logger = LogManager.getLogger(EmailChangingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			request.setAttribute(RequestAttributeName.ERR
					, manager.getValue(SESSION, request));
			return forward(JSPPageName.AUTHORIZATION);
		}
		
		int userId = (int) session.getAttribute(RequestAttributeName.USR_ID);
		String password = request.getParameter(RequestParameterName.USR_PASS);
		String passwordRepeat = request.getParameter(RequestParameterName.USR_PASS_REPEAT);
		
		String error = manager.getValue(ERROR, request);
		
		if (!password.equals(passwordRepeat)) {
			logger.error(error + manager.getValue(PASS_MISMATCH, request));
			request.setAttribute(RequestAttributeName.ERR
					, error + manager.getValue(PASS_MISMATCH, request));
			
			return forward(JSPPageName.EDIT_PROFILE);
		}

		Map<String, String> parameters = new HashMap<>();
		
		boolean success;
		try {
			success = userService.changePassword(userId, password.hashCode());
			
			if (!success) {
				logger.error(error);
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(error, request));

				return forward(JSPPageName.ERROR_PAGE);
				
			} else {
				logger.info(manager.getValue(MESSAGE, request));
				parameters.put(RequestParameterName.MSG, MESSAGE);
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
