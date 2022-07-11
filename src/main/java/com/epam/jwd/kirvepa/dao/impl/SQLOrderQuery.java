package com.epam.jwd.kirvepa.dao.impl;

public class SQLOrderQuery {

	protected static final String CREATE_ORDER = "INSERT INTO orders"
			+ " (car_id, user_id, date_handover, date_return, total_price, order_status_id, datetime_created, datetime_updated)"
			+ " VALUES (?, ?, ?, ?, ?, (SELECT id FROM order_statuses WHERE description=?), SYSDATE(), SYSDATE());";
	
	protected static final String FIND_ORDER = "SELECT id"
			+ " FROM orders"
			+ " WHERE car_id = ?"
			+ " AND user_id = ?"
			+ " AND order_status_id ="
			+ "		 (SELECT id FROM order_statuses WHERE description = 'PREPARED');";
	
	protected static final String UPDATE_ORDER = "UPDATE orders"
			+ " SET order_status_id = (SELECT id FROM order_statuses WHERE description='CREATED')"
			+ ", datetime_updated = SYSDATE()"
			+ " WHERE id = ?;";
	
	protected static final String CANCEL_ORDER = "UPDATE orders"
			+ " SET order_status_id = (SELECT id FROM order_statuses WHERE description='CANCELLED')"
			+ ", datetime_updated = SYSDATE()"
			+ " WHERE id = ?;";
	
	protected static final String GET_ORDER = "SELECT"
			+ " c.manufacturer, c.model, bt.description_ru, c.engine, c.transmission_type, c.drive_type,"
			+ " o.date_handover, o.date_return, o.total_price, os.description"
			+ " FROM orders o"
			+ " LEFT JOIN cars c ON c.id = o.car_id"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ " WHERE o.id = ?";	

	protected static final String GET_USER_ORDERS = "SELECT"
			+ " c.manufacturer, c.model, bt.description_ru, c.engine, c.transmission_type, c.drive_type,"
			+ " o.id, o.date_handover, o.date_return, o.total_price, os.description"
			+ " FROM orders o"
			+ " LEFT JOIN cars c ON c.id = o.car_id"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ " WHERE o.user_id = ?"
			+ " ORDER BY o.date_handoved;";
	
	protected static final String CHECK_PAYMENT = "SELECT"
			+ " payment_method_id, amount"
			+ " FROM payments WHERE order_id = ?;";
	
	protected static final String UPDATE_ORDER_HISTORY = "INSERT INTO orders_history"
			+ " (datetime_created, order_id, order_status_id, comment, employee_id)"
			+ " VALUES (SYSDATE(), ?, ?, ?, ?);";
	
	protected static final String ORDER_PAYMENT = "INSERT INTO payments"
			+ " (order_id, payment_type_id, payment_method_id, amount, datetime_created, datetime_updated)"
			+ " SELECT ?, pt.id, pm.id, o.total_price, SYSDATE(), SYSDATE()"
			+ " FROM payment_types pt, payment_method pm, orders o"
			+ " WHERE pt.description = 'PAYMENT'"
			+ " AND pm.description = 'Card'"
			+ " AND o.id = ?;";
	
	protected static final String ORDER_REFUND = "INSERT INTO payments"
			+ " (order_id, payment_type_id, payment_method_id, amount, datetime_created, datetime_updated)"
			+ " SELECT ?, pt.id, pm.id, o.total_price, SYSDATE(), SYSDATE()"
			+ " FROM payment_types pt, payment_method pm, orders o"
			+ " WHERE pt.description = 'REFUND'"
			+ " AND pm.description = 'Card'"
			+ " AND o.id = ?;";
	
	private SQLOrderQuery() {}
}
