package com.epam.jwd.kirvepa.service.validator;

public class UserDataValidator {
	private static final UserDataValidator instance = new UserDataValidator();
	
	private UserDataValidator() {}
	
	public static UserDataValidator getInstance() {
		return instance;
	}
	
	public boolean loginValidation(String login) {
		if(login == null || login.isEmpty() || login.isBlank()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean emailValidation(String email) {
		if(email == null || email.isEmpty() || email.isBlank()) {
			return false;
		}
		else {
			return true;
		}
	}
	
}
