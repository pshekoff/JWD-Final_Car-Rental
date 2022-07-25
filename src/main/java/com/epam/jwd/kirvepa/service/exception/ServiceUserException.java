package com.epam.jwd.kirvepa.service.exception;

public class ServiceUserException extends Exception {
	private static final long serialVersionUID = 1L;

	public static final String MSG_USR_LOGIN_WRONG = "Incorrect login.";
	public static final String MSG_USR_EMAIL_WRONG = "Incorrect email.";
	public static final String MSG_USR_LOGIN_VAL_FAIL = "Login validation failed. ";
	public static final String MSG_USR_EMAIL_VAL_FAIL = "Email validation failed. ";
	public static final String MSG_CAR_BODY_SELECT_NULL = "No any car body type selected. ";
	public static final String MSG_CAR_DATE_NULL = "Selected date is epmty.";
	public static final String MSG_CAR_DATES_DISCREPANCY = "Date from is greater than date to. ";
	public static final String MSG_CAR_DATE_PAST = "Selected date is in the past. ";
	
	public ServiceUserException() {
		super();
	}
	
	public ServiceUserException(String message) {
		super(message);
	}
	
	public ServiceUserException(Exception e) {
		super(e);
	}
	
	public ServiceUserException(String message, Exception e) {
		super(message, e);
	}
	
}
