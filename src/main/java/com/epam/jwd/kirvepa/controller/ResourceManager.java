package com.epam.jwd.kirvepa.controller;

import java.util.ResourceBundle;

public final class ResourceManager {
	private final static ResourceManager instance = new ResourceManager();
	
	private ResourceBundle bundle;
	private ResourceManager() {}
	
	public static ResourceManager getInstance() {
		return instance;
	}
	
	public String getValue(String key) {
		bundle = ResourceBundle.getBundle("messages", FrontController.locale);
		return bundle.getString(key);
	}
}
