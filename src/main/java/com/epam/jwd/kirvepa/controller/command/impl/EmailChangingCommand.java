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

public class EmailChangingCommand implements Command {
	private static final String MESSAGE = "edit_profile.email.message";
	private static final String ERROR = "edit_profile.email.error";
	private static final String SAME_EMAIL = "edit_profile.email.same";
	private static final String SESSION = "session.expired";
	
	private static final Logger logger = LogManager.getLogger(EmailChangingCommand.class);
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
		String currentEmail = (String) session.getAttribute(RequestAttributeName.USR_EMAIL);
		String newEmail = request.getParameter(RequestParameterName.USR_EMAIL).trim();

		String error = manager.getValue(ERROR, request);
		String message = manager.getValue(MESSAGE, request);
		String sameEmails = manager.getValue(SAME_EMAIL, request);

		if (newEmail.equals(currentEmail)) {
			logger.error(error + sameEmails);
			request.setAttribute(RequestAttributeName.ERR, error + sameEmails);
			return forward(JSPPageName.EDIT_PROFILE);
		}
		
		Map<String, String> parameters = new HashMap<>();
		
		boolean success;
		try {
			success = userService.changeEmail(userId, newEmail);
			
			if (!success) {
				logger.error(error);
				request.setAttribute(RequestAttributeName.ERR
						, manager.getValue(error, request));

				return forward(JSPPageName.ERROR_PAGE);
				
			} else {
				session.setAttribute(RequestAttributeName.USR_EMAIL, newEmail);
				logger.info(message + newEmail);
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
					, manager.getValue(error, request)
					+ e.getMessage());
			
			return forward(JSPPageName.EDIT_PROFILE);
		}
	}

}
