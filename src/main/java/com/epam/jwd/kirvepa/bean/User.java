package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String email;
	private boolean admin;
	private PersonalData personalData;
	
	public User() {}
	
	public User(String login, String email, boolean admin) {
		setLogin(login);
		setEmail(email);
		setAdmin(admin);
	}
	
	public User(String login, String email, boolean admin, PersonalData personalData) {
		setLogin(login);
		setEmail(email);
		setAdmin(admin);
		setPersonalData(personalData);
	}

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public PersonalData getPersonalData() {
		return personalData;
	}
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((personalData == null) ? 0 : personalData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return this.admin == other.admin
				&& this.email.equals(other.email)
				&& this.login.equals(other.login)
				&& Objects.equals(this.personalData, other.personalData);
	}

	@Override
	public String toString() {
		String role;
		if (this.admin) {
			role = "Administrator";
		} else {
			role = "User";
		}
		return this.getClass()
				+ "{"
				+ "login=" + this.login
				+ ", email=" + this.email
				+ ", role=" + role
				+ "}";
	}

}
