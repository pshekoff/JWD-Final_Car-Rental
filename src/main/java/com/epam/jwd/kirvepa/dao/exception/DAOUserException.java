package com.epam.jwd.kirvepa.dao.exception;

public class DAOUserException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public static final String MSG_USR_ABSENT = "Specified user doesn't exist";
	public static final String MSG_USR_EXIST = "User with specified login is already exist";
	public static final String MSG_USR_BLOCKED = "Specified user is blocked";
	public static final String MSG_USR_INS_FAIL = "Employee user wasn't created.";
	public static final String MSG_PWD_INVALID = "Wrong password.";
	public static final String MSG_EMAIL_EXIST = "Specified email is already used.";
	public static final String MSG_USR_DATA_FAIL = "Failed to get user data.";

	
	public DAOUserException() {
		super();
	}
	
	public DAOUserException(String message) {
		super(message);
	}
	
	public DAOUserException(Exception e) {
		super(e);
	}
	
	public DAOUserException(String message, Exception e) {
		super(message, e);
	}
}
