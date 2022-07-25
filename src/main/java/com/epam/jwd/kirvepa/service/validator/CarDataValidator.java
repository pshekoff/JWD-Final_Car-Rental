package com.epam.jwd.kirvepa.service.validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CarDataValidator {
	private static final Logger logger = LogManager.getLogger(CarDataValidator.class);
	private static final CarDataValidator instance = new CarDataValidator();
	
	private CarDataValidator() {}
	
	public static CarDataValidator getInstance() {
		return instance;
	}
	
	public int DateValidation(Date from, Date to) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = Date.valueOf(df.format(Calendar.getInstance().getTime()));
		
		if(from == null || to == null) {
			logger.warn("Some date is empty.");
			return 0;
		}
		else if (from.compareTo(to) > 0) {
			logger.warn("Date from is greater than date to.");
			return -1;
		}
		else if ((from.compareTo(curDate) < 0)) {
			logger.warn("Selected date is in the past.");
			return -2;
		}
		else {
			logger.info("Successfull date validation.");
			return 1;
		}
		
	} 
	
	public boolean bodyValidation(String[] bodies) {
		if (bodies == null) {
			logger.warn("Car body type is empty.");
			return false;
		}
		else {
			logger.info("Successfull car body type validation.");
			return true;
		}
	}
	
	public static Date getDateTruncated() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(Calendar.getInstance().getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

		return (Date) cal.getTime();
	}

}
