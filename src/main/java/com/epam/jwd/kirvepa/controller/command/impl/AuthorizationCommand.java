package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.jwd.kirvepa.bean.AuthorizedUser;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class AuthorizationCommand implements Command {
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_LOGIN);
		int passwordHash = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_PASSWORD).hashCode();

		AuthorizedUser authUser;
		
		try {
			authUser = userService.singIn(login, passwordHash);
			
			if (authUser != null) {
				HttpSession session = request.getSession(true);
				session.setAttribute("userid", authUser.getId());
				session.setAttribute("username", authUser.getName());
				session.setAttribute("userrole", authUser.getRole());

				if (authUser.getRole().equals("Administrator")) {
					return JSPPageName.ADMIN_AUTH_PAGE;
				}
				else {
					return JSPPageName.USER_AUTH_PAGE;
				}

			}
			else {
				return JSPPageName.USER_AUTH_PAGE_FAIL;
			}
			
		} catch (ServiceException e) {
			request.setAttribute("message", e);
			e.printStackTrace(); //temporary
			return JSPPageName.USER_AUTH_PAGE_FAIL;
		}

	}

}
