package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;

public class EditProfileCommand implements Command {
	private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();

		if (session != null) {
			return JSPPageName.EDIT_PROFILE;
		}
		else {
			logger.warn(manager.getValue("edit_profile.warn"));
			return JSPPageName.HOMEPAGE;
		}

	}

}
