package com.epam.jwd.kirvepa.controller.command;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response);
	
	default String redirect(String page, Map<String, String> parameters) {
		
		if (parameters == null) {
			return String.format("redirect:%s", page);
		}
		
		StringBuffer url = new StringBuffer(page).append("?");
		System.out.println("URL before parameters: " + url); /////////////////////
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			System.out.println("URL + parameters: " + url); /////////////////////
		}
		System.out.println("URL after parameters: " + url); /////////////////////
		url.setLength(url.length() - 1);
		System.out.println("URL after cutting: " + url); /////////////////////
		return String.format("redirect:%s", url.toString());
	}

	default String forward(String url) {
		return String.format("forward:%s", url);
	}
	
	default String goGet(String commandName) {
		return String.format("doGet:%s", commandName);
	}
	
	default String getLanguage(HttpSession session) {
		String language;
		if (session.getAttribute("language") != null) {
			language = session.getAttribute("language").toString();
		} else {
			language = Locale.getDefault().toString().substring(0, 2);
		}
		return language;
	}

}
