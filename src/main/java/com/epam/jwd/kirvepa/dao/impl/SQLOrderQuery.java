package com.epam.jwd.kirvepa.dao.impl;

public class SQLOrderQuery {

	protected static final String PREPARE_ORDER = "INSERT INTO orders"
			+ " (car_id, user_id, date_handover, date_return, total_price, order_status_id, datetime_created, datetime_updated)"
			+ " VALUES (?, ?, ?, ?, ?, (SELECT id FROM order_statuses WHERE description = ?), SYSDATE(), SYSDATE());";
	
	protected static final String FIND_ORDER = "SELECT id"
			+ " FROM orders"
			+ " WHERE car_id = ?"
			+ " AND user_id = ?"
			+ " AND order_status_id ="
			+ "		 (SELECT id FROM order_statuses WHERE description = 'PREPARED');";
	
	protected static final String CHECK_STATUS = "SELECT description"
			+ " FROM orders o"
			+ " LEFT JOIN order_statuses os"
			+ " ON o.order_status_id = os.id"
			+ " WHERE o.id = ?;";
	
	protected static final String UPDATE_ORDER_STATUS = "UPDATE orders"
			+ " SET order_status_id = (SELECT id FROM order_statuses WHERE description = ?)"
			+ ", datetime_updated = SYSDATE()"
			+ " WHERE id = ?;";
	
	protected static final String GET_ORDER = "SELECT"
			+ " u.id, u.login, u.email, u.admin, u.active,"
			+ " c.manufacturer, c.model, bt.description_ru, c.engine, c.transmission_type, c.drive_type,"
			+ " o.date_handover, o.date_return, o.total_price, os.description"
			+ " FROM orders o"
			+ " LEFT JOIN users u ON u.id = o.user_id"
			+ " LEFT JOIN cars c ON c.id = o.car_id"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ " WHERE o.id = ?";	

	protected static final String GET_USER_ORDERS_ID = "SELECT o.id"
			+ " FROM orders o"
			+ " LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ " WHERE o.user_id = ?"
			+ " ORDER BY o.date_handover;";
	
	protected static final String GET_ORDERS_ID_ALL = "SELECT o.id"
			+ " FROM orders o"
			+ " ORDER BY o.date_handover";
	
	protected static final String GET_ORDERS_ID_FILTERED = "SELECT o.id"
			+ " FROM orders o"
			+ " LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ " WHERE FIND_IN_SET (os.description, ?) <> 0"
			+ " ORDER BY o.date_handover";
	
	protected static final String CHECK_PAYMENT = "SELECT"
			+ " payment_method_id, amount"
			+ " FROM payments"
			+ " WHERE order_id = ?"
			+ " AND payment_type_id = (SELECT id FROM payment_types WHERE description = 'PAYMENT');";
	
	protected static final String AUTO_UPDATE_ORDER_HISTORY = "INSERT INTO orders_history"
			+ " (datetime_created, order_id, order_status_id, comment)"
			+ " VALUES (SYSDATE(), ?, (SELECT order_status_id FROM orders WHERE id = ?), 'AUTO');";
	
	protected static final String ORDER_PAYMENT = "INSERT INTO payments"
			+ " (order_id, payment_type_id, payment_method_id, amount, datetime_created, datetime_updated)"
			+ " SELECT o.id, pt.id, pm.id, o.total_price, SYSDATE(), SYSDATE()"
			+ " FROM payment_types pt, payment_methods pm, orders o"
			+ " WHERE pt.description = 'PAYMENT'"
			+ " AND pm.description = 'Card'"
			+ " AND o.id = ?;";
	
	protected static final String ORDER_REFUND = "INSERT INTO payments"
			+ " (order_id, payment_type_id, payment_method_id, amount, datetime_created, datetime_updated)"
			+ " SELECT o.id, pt.id, pm.id, o.total_price, SYSDATE(), SYSDATE()"
			+ " FROM payment_types pt, payment_methods pm, orders o"
			+ " WHERE pt.description = 'REFUND'"
			+ " AND pm.description = 'Card'"
			+ " AND o.id = ?;";
	
	private SQLOrderQuery() {}
}
