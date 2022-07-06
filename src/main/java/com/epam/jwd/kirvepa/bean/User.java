package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String email;
	private boolean admin;
	private PersonalData personalData;
	
	public User(String login, String email, boolean admin) {
		this.login = login;
		setEmail(email);
		this.admin = admin;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public String getLogin() {
		return login;
	}
	public String getEmail() {
		return email;
	}
	public boolean isAdmin() {
		return admin;
	}
	public PersonalData getPersonalData() {
		return personalData;
	}

}
