package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.command.Command;

public class HomePageCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
		if (session == null) {
			return forward(JSPPageName.HOMEPAGE);
		}
		
		boolean isAdmin = (boolean) session.getAttribute(RequestAttributeName.USR_ROLE);
		
		if (isAdmin) {
			return forward(JSPPageName.ADMIN_HOMEPAGE);
		} else {
			return forward(JSPPageName.USER_HOMEPAGE);
		}
		
	}

}
