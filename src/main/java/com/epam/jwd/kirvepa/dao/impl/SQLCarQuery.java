package com.epam.jwd.kirvepa.dao.impl;

//import java.util.Locale;

public class SQLCarQuery {
	
	protected static final String GET_BODYTYPES_HEADER = "SELECT DISTINCT description_";
	protected static final String GET_BODYTYPES_EXIST = " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " ORDER BY bt.id;"; 
	
	protected static final String GET_CAR_BODY_TYPES_LIST_0 = "SELECT description_";
	protected static final String GET_CAR_BODY_TYPES_LIST_1 = " FROM body_types;";
	
	protected static final String GET_CAR_TRANSMISSION_TYPES_LIST_0 = "SELECT description_";
	protected static final String GET_CAR_TRANSMISSION_TYPES_LIST_1 = " FROM transmission_types;";
	
	protected static final String GET_CAR_DRIVE_TYPES_LIST_0 = "SELECT description_";
	protected static final String GET_CAR_DRIVE_TYPES_LIST_1 = " FROM drive_types;";
	
	protected static final String GET_CAR_COLORS_LIST_0 = "SELECT description_";
	protected static final String GET_CAR_COLORS_LIST_1 = " FROM colors;";
	
	protected static final String GET_CAR_LIST_0 = "SELECT DISTINCT"
			+ " c.manufacturer, c.model, c.engine, tt.description_";
	protected static final String GET_CAR_LIST_1 = ", dt.description_";
	protected static final String GET_CAR_LIST_2 = ", bt.description_";
	protected static final String GET_CAR_LIST_3 = ","
			+ " pl.tariff*if(dc.days_count=0, 1, dc.days_count) \"price\""
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN transmission_types tt ON tt.id = c.transmission_type_id"
			+ " LEFT JOIN drive_types dt ON dt.id = c.drive_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id = c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom AND pl.dateto"
			+ "		OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " LEFT JOIN (SELECT (STR_TO_DATE(?,'%Y-%m-%d')-STR_TO_DATE(?,'%Y-%m-%d')) \"days_count\") dc ON 1=1"
			+ " WHERE FIND_IN_SET (bt.description_";
	protected static final String GET_CAR_LIST_4 = ", ?) <> 0"
			+ " AND c.available = TRUE"
			+ " AND c.id NOT IN"
			+ "		(SELECT car_id FROM orders o"
			+ "		 LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ "		 WHERE o.date_return >= DATE(SYSDATE())"
			+ " 	 AND (o.date_handover BETWEEN ? AND ?"
			+ "		 	OR o.date_return+1 BETWEEN ? AND ?)"
			+ "		 AND os.description NOT IN ('CANCELLED','REJECTED','COMPLETED'));";
	
	protected static final String FIND_CAR_ID_0 = "SELECT c.id"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN transmission_types tt ON tt.id = c.transmission_type_id"
			+ " LEFT JOIN drive_types dt ON dt.id = c.drive_type_id"
			+ " LEFT JOIN price_list pl ON pl.car_id = c.id"
			+ " AND (DATE(SYSDATE()) BETWEEN pl.datefrom AND pl.dateto"
			+ " 	OR (DATE(SYSDATE()) >= pl.datefrom AND pl.dateto IS NULL))"
			+ " WHERE c.available = TRUE"
			+ " AND c.manufacturer = ?"
			+ " AND c.model = ?"
			+ " AND bt.description_";
	protected static final String FIND_CAR_ID_1 = " = ?"
			+ " AND c.engine = ?"
			+ " AND tt.description_";
	protected static final String FIND_CAR_ID_2 = " = ?"
			+ " AND dt.description_";
	protected static final String FIND_CAR_ID_3 = " = ?"
			+ " AND c.id NOT IN"
			+ "		(SELECT car_id FROM orders o"
			+ "		 LEFT JOIN order_statuses os ON os.id = o.order_status_id"
			+ "		 WHERE o.date_return >= DATE(SYSDATE())"
			+ " 	 AND (o.date_handover BETWEEN ? AND ?"
			+ "		 	OR o.date_return+1 BETWEEN ? AND ?)"
			+ "		 AND os.description NOT IN ('CANCELLED','REJECTED','COMPLETED'))"
			+ " LIMIT 1;";
	
	protected static final String INSERT_CAR_0 = "UPDATE cars"
			+ " (manufacturer, model, registration_number, body_type_id, issue_year,"
			+ " engine, transmission_type_id, drive_type_id, color_id, weight, VIN, available)"
			+ " VALUES (?, ?, ?, (SELECT id FROM body_types WHERE description_";
	protected static final String INSERT_CAR_1 = " = ?), ?, ?,"
			+ " (SELECT id FROM transmission_types WHERE description_";
	protected static final String INSERT_CAR_2 = " = ?), (SELECT id FROM drive_types WHERE description_";
	protected static final String INSERT_CAR_3 = " = ?), (SELECT id FROM colors WHERE description_";
	protected static final String INSERT_CAR_4 = " = ?), ?, ?, TRUE)";
	
	protected static final String GET_ALL_CARS_0 = "SELECT"
			+ " c.id, c.manufacturer, c.model, c.registration_number, bt.description_";
	protected static final String GET_ALL_CARS_1 = ","
			+ " c.issue_year, c.engine, tt.description_";
	protected static final String GET_ALL_CARS_2 = ", dt.description_";
	protected static final String GET_ALL_CARS_3 = ", co.description_";
	protected static final String GET_ALL_CARS_4 = ", c.weight, c.VIN, c.available"
			+ " FROM cars c"
			+ " LEFT JOIN body_types bt ON bt.id = c.body_type_id"
			+ " LEFT JOIN transmission_types tt ON tt.id = c.transmission_type_id"
			+ " LEFT JOIN drive_types dt ON dt.id = c.drive_type_id"
			+ " LEFT JOIN colors co ON co.id = c.color_id"
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
