package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.sql.Date;

public class Client extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String identificationNumber;
	private Date dayOfBirth;
	private String passportNumber;
	private Date issueDate;
	private Date expireDate;
	
	public Client(String login
			, int passwordHash
			, String email
			, String phone
			, String role
			, String identificationNumber
			, String firstName
			, String lastName
			, Date dayOfBirth
			, String passportNumber
			, Date issueDate
			, Date expireDate
			, String homeAdress) {
		super(login, passwordHash, firstName, lastName, homeAdress, email, phone, role);
		setIdentificationNumber(identificationNumber);
		setDayOfBirth(dayOfBirth);
		setPassportNumber(passportNumber);
		setIssueDate(issueDate);
		setExpireDate(expireDate);
	}
	
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public Date getDayOfBirth() {
		return dayOfBirth;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public Date getExpireDate() {
		return expireDate;
	}

}
