package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.command.Command;

public class EditProfileCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");

		if (session != null) {
			return "/jsp/edit_profile.jsp";
		}
		else {
			return "index.jsp";
		}

	}

}
