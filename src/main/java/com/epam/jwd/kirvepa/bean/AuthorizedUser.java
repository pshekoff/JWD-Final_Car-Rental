package com.epam.jwd.kirvepa.bean;

import java.util.Objects;

public class AuthorizedUser extends User {
	private static final long serialVersionUID = 1L;
	
	private int userId;
	
	public AuthorizedUser() {}
	
	public AuthorizedUser(int userId, String login, String email, boolean admin) {
		super(login, email, admin);
		this.userId = userId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + this.userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AuthorizedUser other = (AuthorizedUser) obj;
		return this.userId == other.userId
				&& this.isAdmin() == other.isAdmin()
				&& this.getEmail().equals(other.getEmail())
				&& this.getLogin().equals(other.getLogin())
				&& Objects.equals(this.getPersonalData(), other.getPersonalData());
	}
	
	@Override
	public String toString() {
		String role;
		if (this.isAdmin()) {
			role = "Administrator";
		} else {
			role = "User";
		}
		return this.getClass()
				+ "{"
				+ "id=" + this.userId
				+ ", login=" + this.getLogin()
				+ ", email=" + this.getEmail()
				+ ", role=" + role
				+ "}";
	}

}
