package com.epam.jwd.kirvepa.controller.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
	String execute(HttpServletRequest request, HttpServletResponse response);
	
	default String redirect(String page, Map<String, String> parameters) {
		
		if (parameters == null) {
			return String.format("redirect:%s", page);
		}
		
		StringBuffer url = new StringBuffer(page).append("?");
		
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			url.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		url.setLength(url.length() - 1);
		
		return String.format("redirect:%s", url.toString());
	}
	
	default String forward(String url) {
		return String.format("forward:%s", url);
	}

}
