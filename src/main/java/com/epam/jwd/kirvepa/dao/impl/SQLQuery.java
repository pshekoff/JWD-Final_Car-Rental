package com.epam.jwd.kirvepa.dao.impl;

public final class SQLQuery {
	
	protected static final String CHECK_ACCESS = "SELECT"
			+ " u1.id, u1.active, u1.admin, u1. email, u2.password_hash"
			+ " FROM users u1"
			+ " LEFT JOIN users u2 ON u2.id = u1.id AND u2.password_hash=?"
			+ " WHERE u1.login=?";
	
	protected static final String FIND_USER = "SELECT id FROM users WHERE login = ?";
	
	protected static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";
	
	protected static final String INSERT_USER = "INSERT INTO users"
			+ "(login, password_hash, email, admin, active, datetime_created, datetime_updated)"
			+ " values (?, ?, ?, ?, ?, SYSDATE(), SYSDATE());";
	
	protected static final String INSERT_EMPLOYEE = "INSERT INTO employees"
			+ " (department, position, salary, datetime_created, datetime_updated, user_id)"
			+ " VALUES (?, ?, ?, SYSDATE(), SYSDATE(), ?);";
	
	protected static final String GET_BODY_TYPE_LIST_RU = "SELECT DISTINCT"
			+ " description_ru"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id;"; 
	
	protected static final String GET_CAR_LIST = "SELECT DISTINCT"
			+ " c.manufacturer, c.model, c.engine, c.transmission_type, c.drive_type,"
			+ " bt.description_ru, pl.tariff*if(dc.days_count=0, 1, dc.days_count) \"price\""
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id=c.body_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id=c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom"
			+ " AND pl.dateto OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " LEFT JOIN (SELECT (STR_TO_DATE(?,'%Y-%m-%d')-STR_TO_DATE(?,'%Y-%m-%d')) \"days_count\") dc ON 1=1"
			+ " WHERE FIND_IN_SET (bt.description_ru, ?) <> 0"
			+ " AND c.available=TRUE"
			+ " AND c.id NOT IN"
			+ "		(SELECT car_id FROM orders"
			+ "		 WHERE date_return >= DATE(SYSDATE())"
			+ " 	 AND (date_handover BETWEEN ? AND ?"
			+ "		 OR date_return+1 BETWEEN ? AND ?));";

	protected static final String UPDATE_PERSONAL_DATA = "UPDATE personal_data"
			+ " SET valid=FALSE WHERE user_id = ?;";
	
	protected static final String INSERT_PERSONAL_DATA = "INSERT INTO personal_data"
			+ " (user_id, first_name, last_name, day_of_birth, passport_number, passport_issue_date,"
			+ " passport_expire_date, identification_number, home_address, phone, datetime_created, valid)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE(), TRUE);";
	
	protected static final String PREPARE_ORDER = "INSERT INTO orders"
			+ " (car_id, user_id, date_handover, date_return, total_price, order_status_id, datetime_created, datetime_updated)"
			+ " VALUES (?, ?, ?, ?, ?, (SELECT id FROM order_statuses WHERE description='PREPARED'), SYSDATE(), SYSDATE());";
	
	protected static final String FIND_ORDER = "SELECT id"
			+ " FROM orders"
			+ " WHERE car_id = ?"
			+ " AND user_id = ?"
			+ " AND order_status_id ="
			+ "		 (SELECT id FROM order_statuses WHERE description = 'PREPARED');";
	
	protected static final String PLACE_ORDER = "UPDATE orders"
			+ " SET order_status_id = (SELECT id FROM order_statuses WHERE description='CREATED')"
			+ ", datetime_updated = SYSDATE()"
			+ " WHERE id = ?;";
	
	protected static final String FIND_CAR_ID = "SELECT c.id"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id=c.body_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id=c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom"
			+ " AND pl.dateto OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " WHERE c.available=TRUE"
			+ " AND c.manufacturer = ?"
			+ " AND c.model = ?"
			+ " AND bt.description_ru = ?"
			+ " AND c.engine = ?"
			+ " AND c.transmission_type = ?"
			+ " AND c.drive_type = ?"
			+ " AND c.id NOT IN (SELECT car_id FROM orders WHERE date_return >= DATE(SYSDATE())"
			+ " AND (date_handover BETWEEN ? AND ? OR date_return+1 BETWEEN ? AND ?))"
			+ " LIMIT 1;";
	
	protected static final String HOLD_CAR = "UPDATE cars"
			+ " SET available = FALSE"
			+ " WHERE id = ?;";
	
	protected static final String UNHOLD_CAR = "UPDATE cars"
			+ " SET available = TRUE"
			+ " WHERE id = ?;";
	
	private SQLQuery() {}

}
