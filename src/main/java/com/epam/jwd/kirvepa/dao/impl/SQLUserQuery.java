package com.epam.jwd.kirvepa.dao.impl;

public final class SQLUserQuery {
	
	protected static final String CHECK_ACCESS = "SELECT"
			+ " u1.id, u1.active, u1.admin, u1.email, u2.password_hash"
			+ " FROM users u1"
			+ " LEFT JOIN users u2 ON u2.id = u1.id AND u2.password_hash = ?"
			+ " WHERE u1.login = ?";
	
	protected static final String FIND_USER = "SELECT id FROM users WHERE login = ?";
	
	protected static final String FIND_EMAIL = "SELECT email FROM users WHERE email = ?";
	
	protected static final String CHECK_PERSONAL_DATA = "SELECT 1"
			+ " FROM personal_data"
			+ " WHERE user_id = ?"
			+ " AND valid = TRUE;";
	
	protected static final String INSERT_USER = "INSERT INTO users"
			+ "(login, password_hash, email, admin, active, datetime_created, datetime_updated)"
			+ " values (?, ?, ?, ?, TRUE, SYSDATE(), SYSDATE());";
	
	protected static final String UPDATE_LOGIN = "UPDATE users"
			+ " SET login = ?"
			+ " WHERE id = ?;";
	
	protected static final String UPDATE_PASSWORD = "UPDATE users"
			+ " SET password_hash = ?"
			+ " WHERE id = ?;";
	
	protected static final String UPDATE_EMAIL = "UPDATE users"
			+ " SET email = ?"
			+ " WHERE id = ?;";
	
	protected static final String INSERT_EMPLOYEE = "INSERT INTO employees"
			+ " (department, position, salary, datetime_created, datetime_updated, user_id)"
			+ " VALUES (?, ?, ?, SYSDATE(), SYSDATE(), ?);";
	
	protected static final String UPDATE_PERSONAL_DATA = "UPDATE personal_data"
			+ " SET valid=FALSE WHERE user_id = ?;";
	
	protected static final String INSERT_PERSONAL_DATA = "INSERT INTO personal_data"
			+ " (user_id, first_name, last_name, day_of_birth, passport_number, passport_issue_date,"
			+ " passport_expire_date, identification_number, home_address, phone, datetime_created, valid)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE(), TRUE);";

	private SQLUserQuery() {}

}
