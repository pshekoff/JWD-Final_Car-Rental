package com.epam.jwd.kirvepa.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

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
	
	public PersonalData() {}
	
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
		setDayOfBirth(dayOfBirth);
		setPassportNumber(passportNumber);
		setIssueDate(issueDate);
		setExpireDate(expireDate);
		setIdentificationNumber(identificationNumber);
		setHomeAddress(homeAddress);
		setPhone(phone);
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDayOfBirth() {
		return dayOfBirth;
	}
	public void setDayOfBirth(Date dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dayOfBirth == null) ? 0 : dayOfBirth.hashCode());
		result = prime * result + ((expireDate == null) ? 0 : expireDate.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result + ((identificationNumber == null) ? 0 : identificationNumber.hashCode());
		result = prime * result + ((issueDate == null) ? 0 : issueDate.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((passportNumber == null) ? 0 : passportNumber.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		PersonalData other = (PersonalData) obj;
		return Objects.equals(this.dayOfBirth, other.dayOfBirth)
				&& Objects.equals(this.expireDate, other.expireDate)
				&& this.firstName.equals(other.firstName)
				&& this.homeAddress.equals(other.homeAddress)
				&& this.identificationNumber.equals(other.identificationNumber)
				&& Objects.equals(this.issueDate, other.issueDate)
				&& this.lastName.equals(other.lastName)
				&& this.passportNumber.equals(other.passportNumber)
				&& this.phone.equals(other.phone);
	}
	
	@Override
	public String toString() {
		return this.getClass().getName()
				+ "{"
				+ "firstName=" + this.firstName
				+ ", lastName=" + this.lastName
				+ ", dayOfBirth=" + this.dayOfBirth
				+ ", passportNumber=" + this.passportNumber
				+ ", issueDate=" + this.issueDate
				+ ", expireDate=" + this.expireDate
				+ ", identificationNumber=" + this.identificationNumber
				+ ", homeAddress=" + this.homeAddress
				+ ", phone=" + this.phone
				+ "}";
	}
	
}
