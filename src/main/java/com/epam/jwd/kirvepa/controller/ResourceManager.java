package com.epam.jwd.kirvepa.controller;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

public final class ResourceManager {
	private final static ResourceManager instance = new ResourceManager();
	
	private ResourceBundle bundle;
	private ResourceManager() {}
	
	public static ResourceManager getInstance() {
		return instance;
	}
	
	public String getValue(String key, HttpServletRequest request) {
		bundle = ResourceBundle.getBundle("messages", FrontController.getLocale(request));
		return bundle.getString(key);
	}
	
	public String getValue(String key, String language) {
		bundle = ResourceBundle.getBundle("messages", new Locale(language));
		return bundle.getString(key);
	}
}
