package com.epam.jwd.kirvepa.service.validator;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CarDataValidator {
	private static final CarDataValidator instance = new CarDataValidator();
	
	private CarDataValidator() {}
	
	public static CarDataValidator getInstance() {
		return instance;
	}
	
	public int DateValidation(Date from, Date to) {
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = Date.valueOf(df.format(Calendar.getInstance().getTime()));
		
		if(from == null || to == null) {
			//null date
			return 0;
		}
		else if ((from.compareTo(curDate) < 0) || (to.compareTo(curDate) < 0)) {
			//date in the past
			return -1;
		}
		else if (from.compareTo(to) > 0) {
			//dateFrom is greater than dateTo
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
