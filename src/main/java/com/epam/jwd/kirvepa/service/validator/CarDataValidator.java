package com.epam.jwd.kirvepa.service.validator;

import java.sql.Date;
import java.util.Calendar;

public class CarDataValidator {
	private static final CarDataValidator instance = new CarDataValidator();
	
	private CarDataValidator() {}
	
	public static CarDataValidator getInstance() {
		return instance;
	}
	
	public int DateValidation(Date from, Date to) {
		if(from == null || to == null) {
			return 0;
		}
		else if (from.compareTo(to) > 0) {
			return -1;
		}
		else if (from.compareTo(Calendar.getInstance().getTime()) < 0) {
			return -2;
		}
		else {
			return 1;
		}
	}
	
	public boolean bodyValidation(String[] bodies) {
		if (bodies == null) {
			return false;
		}
		else {
			return true;
		}
	}

}
