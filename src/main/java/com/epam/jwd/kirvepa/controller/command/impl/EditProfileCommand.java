package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.User;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class EditProfileCommand implements Command {
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		int userId = (int) session.getAttribute("user_id");
		
		

		if (session != null) {
	//		User user = userService.getUserData(userId);
			return "/jsp/edit_profile.jsp";
		}
		else {
			return "index.jsp";
		}

	}

}
