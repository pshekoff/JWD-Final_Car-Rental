package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.sql.Date;

public class PersonalData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	private String lastName;
	private Date dayOfBirth;
	private String passportNumber;
	private Date issueDate;
	private Date expireDate;
	private String identificationNumber;
	private String homeAddress;
	private String phone;
	
	public PersonalData(String firstName
			, String lastName
			, Date dayOfBirth
			, String passportNumber
			, Date issueDate
			, Date expireDate
			, String identificationNumber
			, String homeAddress
			, String phone) {

		setFirstName(firstName);
		setLastName(lastName);
		setBirthday(dayOfBirth);
		setPassportNumber(passportNumber);
		setIssueDate(issueDate);
		setExpireDate(expireDate);
		setIdentificationNumber(identificationNumber);
		setAddress(homeAddress);
		setPhone(phone);
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setBirthday(Date dayOfBirth) {
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
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public void setAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public Date getBirthday() {
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
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public String getAddress() {
		return homeAddress;
	}
	public String getPhone() {
		return phone;
	}

}
