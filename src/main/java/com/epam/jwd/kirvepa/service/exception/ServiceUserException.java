package com.epam.jwd.kirvepa.service.exception;

public class ServiceUserException extends Exception {
	private static final long serialVersionUID = 1L;
	
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
