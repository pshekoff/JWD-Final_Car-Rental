package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jwd.kirvepa.controller.command.Command;

public class UnknownCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return request.getContextPath();
	}

}
