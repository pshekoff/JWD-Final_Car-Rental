package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.command.Command;

public class UnknownCommand implements Command {
	private static final ResourceManager manager = ResourceManager.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//return request.getContextPath();
		request.setAttribute(RequestAttributeName.ERR, manager.getValue("command.unknown.error"));
		return forward(JSPPageName.ERROR_PAGE);
	}

}
