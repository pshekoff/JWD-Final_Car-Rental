package com.epam.jwd.kirvepa.dao.impl;

public final class SQLQuery {
	
	protected static final String CHECK_CREDENTIALS = "SELECT u.id, r.description"
			+ " FROM users u LEFT JOIN roles r ON u.role_id=r.id"
			+ " WHERE login=? and password_hash=?";
	
	protected static final String FIND_USER = "SELECT id FROM users WHERE login = ?";
	
	protected static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";
	
	protected static final String INSERT_USER = "INSERT INTO users"
			+ "(login, password_hash, email, role_id firstname, lastname,"
			+ " homeadress, phonenumber, status_id, datetime_created, datetime_updated)"
			+ " values (?, ?, ?, (select id from roles where description = ?), ?, ?, ?, ?"
			+ ", (select id from user_statuses where description = 'ACTIVE'), sysdate(), sysdate());";
	
	protected static final String INSERT_EMPLOYEE = "";
	
	protected static final String GET_BODY_TYPE_LIST_RU = "SELECT description_ru FROM body_types;"; 
	
	protected static final String GET_CAR_LIST = "SELECT"
			+ " c.id, c.manufacturer, c.model, c.registration_number, c.VIN, bt.description_ru,"
			+ " c.issue_year, c.engine, c.transmission_type, c.drive_type, c.color, c.weight"
			+ " FROM cars c LEFT JOIN body_types bt ON  bt.id=c.body_type_id"
			+ " WHERE FIND_IN_SET (bt.description_ru, ?) <> 0 AND c.available=TRUE"
			+ " AND c.id NOT IN (SELECT car_id FROM orders WHERE date_return >= DATE(SYSDATE())"
			+ " AND (date_handover NOT BETWEEN ? AND ? OR date_return+1 NOT BETWEEN ? AND ?));";
	
	protected static final String GET_CAR_LIST_TEST = "SELECT c.* FROM cars c LEFT JOIN body_types bt ON  bt.id=c.body_type_id"
			+ " WHERE FIND_IN_SET (bt.description_ru, ?) <> 0 AND c.available=TRUE AND c.id NOT IN"
			+ " (SELECT car_id FROM orders WHERE date_return >= DATE(SYSDATE()) AND"
			+ " (date_handover NOT BETWEEN ? AND ? OR date_return+1 NOT BETWEEN ? AND ?))";
	
	private SQLQuery() {}

}
