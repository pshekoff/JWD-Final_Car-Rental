package com.epam.jwd.kirvepa.dao.exception;

public class DAOUserException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public static final String MSG_USR_ABSENT = "Specified user doesn't exist. ";
	public static final String MSG_USR_EXIST = "User with specified login is already exist. ";
	public static final String MSG_USR_BLOCKED = "Specified user is blocked. ";
	public static final String MSG_USR_INS_FAIL = "Employee user wasn't created. ";
	public static final String MSG_PWD_INVALID = "Wrong password. ";
	public static final String MSG_EMAIL_EXIST = "Specified email is already used. ";
	public static final String MSG_USR_DATA_FAIL = "Failed to get user data. ";
	public static final String MSG_CAR_ABSENT = "Selected car is missing. ";
	public static final String MSG_ORDER_ABSENT = "Created order is missing. ";
	public static final String MSG_ORDER_PERS_DATA = "Failed to save personal data. ";
	public static final String MSG_ORDER_CANCEL_FAIL = " order may not be CANCELLED. ";
	public static final String MSG_ORDER_PAID_FAIL = " order may not be PAID. ";
	public static final String MSG_ORDER_APPROVE_FAIL = " order may not be APPROVED. ";
	public static final String MSG_ORDER_REJECT_FAIL = " order may not be REJECTED. ";
	public static final String MSG_ORDER_HANDOVER_RETURN_FAIL = "Car may not be HANDOVER or RETURN for the order ";
	public static final String MSG_ORDER_UNKNOWN = "Failed to get order status. ";
	public static final String MSG_CHG_ROOT = "Root user may not be changed. ";
	
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
