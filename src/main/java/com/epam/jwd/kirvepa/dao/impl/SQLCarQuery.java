package com.epam.jwd.kirvepa.dao.impl;

import java.util.Locale;

public class SQLCarQuery {
	
	protected static final String GET_BODYTYPES_EXIST = "SELECT DISTINCT"
			+ " description_" + Locale.getDefault().toString().substring(0, 2)
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " ORDER BY bt.id;"; 
	
	protected static final String  GET_BODYTYPES_ALL = "SELECT "
			+ " description_" + Locale.getDefault().toString().substring(0, 2)
			+ " FROM body_types"
			+ " ORDER BY id;";

	protected static final String GET_CAR_LIST = "SELECT DISTINCT"
			+ " c.manufacturer, c.model, c.engine, c.transmission_type, c.drive_type,"
			+ " bt.description_ru, pl.tariff*if(dc.days_count=0, 1, dc.days_count) \"price\""
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id = c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom AND pl.dateto"
			+ "		OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " LEFT JOIN (SELECT (STR_TO_DATE(?,'%Y-%m-%d')-STR_TO_DATE(?,'%Y-%m-%d')) \"days_count\") dc ON 1=1"
			+ " WHERE FIND_IN_SET (bt.description_ru, ?) <> 0"
			+ " AND c.available = TRUE"
			+ " AND c.id NOT IN"
			+ "		(SELECT car_id FROM orders o"
			+ "		 LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ "		 WHERE o.date_return >= DATE(SYSDATE())"
			+ " 	 AND (o.date_handover BETWEEN ? AND ?"
			+ "		 	OR o.date_return+1 BETWEEN ? AND ?)"
			+ "		 AND os.description NOT IN ('CANCELLED','REJECTED','COMPLETED'));";
	
	protected static final String FIND_CAR_ID = "SELECT c.id"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id = c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom AND pl.dateto"
			+ " 	OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " WHERE c.available = TRUE"
			+ " AND c.manufacturer = ?"
			+ " AND c.model = ?"
			+ " AND bt.description_ru = ?"
			+ " AND c.engine = ?"
			+ " AND c.transmission_type = ?"
			+ " AND c.drive_type = ?"
			+ " AND c.id NOT IN"
			+ "		(SELECT car_id FROM orders o"
			+ "		 LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ "		 WHERE o.date_return >= DATE(SYSDATE())"
			+ " 	 AND (o.date_handover BETWEEN ? AND ?"
			+ "		 	OR o.date_return+1 BETWEEN ? AND ?)"
			+ "		 AND os.description NOT IN ('CANCELLED','REJECTED','COMPLETED'))"
			+ " LIMIT 1;";
	
	protected static final String INSERT_CAR = "UPDATE cars"
			+ " (manufacturer, model, registration_number, body_type_id, issue_year,"
			+ " engine, transmission_type, drive_type, colorm weight, VIN, available)"
			+ " VALUES (?, ?, ?, (SELECT id FROM body_types WHERE description = ?),"
			+ " ?, ?, ?, ?, ?, ?, ?, TRUE)";
	
	protected static final String GET_ALL_CARS = "SELECT"
			+ " c.id, c.manufacturer, c.model, c.registration_number, bt.description_ru, c.issue_year,"
			+ " c.engine, c.transmission_type, c.drive_type, c.color, c.weight, c.VIN, c.available"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " ORDER BY c.id;";
	
	protected static final String BLOCK_UNBLOCK_CAR = "UPDATE cars"
			+ " SET available = NOT available"
			+ " WHERE id = ?;";
	
	protected static final String HOLD_CAR = "UPDATE cars"
			+ " SET available = FALSE"
			+ " WHERE id = ?;";
	
	protected static final String UNHOLD_CAR = "UPDATE cars"
			+ " SET available = TRUE"
			+ " WHERE id = ?;";
	
	private SQLCarQuery() {}
	
}
