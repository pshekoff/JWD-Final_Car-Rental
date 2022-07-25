package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String login;
	private String email;
	private boolean admin;
	private PersonalData personalData;
	private boolean active;
	
	public User() {}
	
	public User(String login, String email, boolean admin) {
		setLogin(login);
		setEmail(email);
		setAdmin(admin);
	}
	
	public User(int userId, String login, String email, boolean admin, boolean active) {
		setUserId(userId);
		setLogin(login);
		setEmail(email);
		setAdmin(admin);
		setActive(active);
	}
	
	public User(int userId, String login, String email, boolean admin
				, boolean active, PersonalData personalData) {
		setUserId(userId);
		setLogin(login);
		setEmail(email);
		setAdmin(admin);
		setPersonalData(personalData);
		setActive(active);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getRole() {
		if (this.admin) {
			return "Administrator";
		} else {
			return "User";
		}
	}

	public String getStatus() {
		if (this.active) {
			return "Active";
		} else {
			return "Blocked";
		}
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((personalData == null) ? 0 : personalData.hashCode());
		result = prime * result + userId;
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
				&& this.userId == other.userId
				&& this.email.equals(other.email)
				&& this.login.equals(other.login)
				&& Objects.equals(this.personalData, other.personalData)
				&& this.active == other.active;
	}

	@Override
	public String toString() {
		String role;
		if (this.admin) {
			role = "Administrator";
		} else {
			role = "User";
		}
		
		String status;
		if (this.active) {
			status = "Active";
		} else {
			status = "Blocked";
		}
		
		return this.getClass().getName()
				+ "{"
				+ "id=" + userId
				+ ", login=" + this.login
				+ ", email=" + this.email
				+ ", role=" + role
				+ ", status=" + status
				+ "}";
	}

}
