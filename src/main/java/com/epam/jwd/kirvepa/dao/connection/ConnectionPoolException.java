package com.epam.jwd.kirvepa.dao.connection;

public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public static final String MSG_INIT_FAIL = "Can't initialize connection pool. ";
	public static final String MSG_PROP_READ_FAIL = "Can't read data from properties file. Connections count set to ";
	public static final String MSG_SQL_EXC = "SQLException in connection pool. ";
	public static final String MSG_DB_DRIVER_FAIL = "Can't find database driver class. ";
	public static final String MSG_CONN_CLOSE_FAIL = "Error closing the connection. ";
	public static final String MSG_CONN_RETURN_FAIL = "Connection isn't return to the pool. ";
	public static final String MSG_RS_CLOSE_FAIL = "Result set isn't closed. ";
	public static final String MSG_ST_CLOSE_FAIL = "Statement isn't closed. ";
	public static final String MSG_CONN_TAKE_FAIL = "Error connecting of the data source. ";
	public static final String MSG_CONN_CLOSE_CLOSED = "Attemping to close closed connection. ";
	public static final String MSG_CONN_REMOVE_QUEUE = "Error deleting connection from the giwen away connection pool. ";
	public static final String MSG_CONN_ADD_QUEUE = "Error allocating connection in the connection pool. ";

	public ConnectionPoolException() {
		super();
	}
	
	public ConnectionPoolException(String message) {
		super(message);
	}
	
	public ConnectionPoolException(Exception e) {
		super(e);
	}
	
	public ConnectionPoolException(String message, Exception e) {
		super(message, e);
	}
}
