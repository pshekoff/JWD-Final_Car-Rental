package com.epam.jwd.kirvepa.service.validator;

public class UserDataValidator {
	private static final UserDataValidator instance = new UserDataValidator();
	private static final int LOGIN_MAX_LENGTH = 16;
	
	private UserDataValidator() {}
	
	public static UserDataValidator getInstance() {
		return instance;
	}
	
	public boolean loginValidation(String login) {
		if(login == null || login.isEmpty() || login.length() > LOGIN_MAX_LENGTH){
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean emailValidation(String email) {
		if(email == null || email.isEmpty()){
			return false;
		}
		else {
			return true;
		}
	}
	
}
