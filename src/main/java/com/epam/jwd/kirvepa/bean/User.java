package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String login;
	private int passwordHash;
	private String firstName;
	private String lastName;
	private String homeAdress;
	private String email;
	private String phone;
	private String role;
	
	public User() {}
	
	public User(String login
				, int passwordHash
				, String firstName
				, String lastName
				, String homeAdress
				, String email
				, String phone
				, String role) {
		this.login = login;
		setPassword(passwordHash);
		setFirstName(firstName);
		setLastName(lastName);
		setAdress(homeAdress);
		setPhone(phone);
		setEmail(email);
		setRole(role);
	}
	
	public User(String login, int passwordHash, String email, String role) {
		this.login = login;
		setPassword(passwordHash);
		setEmail(email);
		setRole(role);
	}
	
	public void setPassword(int passwordHash) {
		this.passwordHash = passwordHash;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setAdress(String homeAdress) {
		this.homeAdress = homeAdress;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}
	public int getPasswordHash() {
		return passwordHash;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getAdress() {
		return homeAdress;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public String getRole() {
		return role;
	}

	@Override
	public String toString() {
		return String.format("%s;%s;%s;%s;%s;%s;%s"
							, login, role, firstName, lastName, email, phone, homeAdress);
	}

}
