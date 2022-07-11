package com.epam.jwd.kirvepa.dao.impl;

public class SQLCarQuery {
	
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
	
	private SQLCarQuery() {}
	
}
