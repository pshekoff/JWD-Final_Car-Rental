package com.epam.jwd.kirvepa.bean;

public class AuthorizedUser extends User {
	private static final long serialVersionUID = 1L;
	
	private int userId;
	
	public AuthorizedUser(int userId, String login, String email, boolean admin) {
		super(login, email, admin);
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}

}
