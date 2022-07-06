package com.epam.jwd.kirvepa.service.validator;

import java.sql.Date;

public class PersonalDataValidator {
	private static final PersonalDataValidator instance = new PersonalDataValidator();
	
	private PersonalDataValidator() {}
	
	public static PersonalDataValidator getInstance() {
		return instance;
	}
	
	public boolean nameValidation(String name) {
		return false;
	}
	
	public boolean dateIssueValidation(Date issueDate) {
		return false;
	}
	
	public boolean dateExpireValidation(Date dateExpire) {
		return false;
	}
	
	public boolean dateOfBirthValidation(Date dateOfBirth) {
		return false;
	}
	
	public boolean passportNumberValidation(String passportNumber) {
		return false;
	}
}
