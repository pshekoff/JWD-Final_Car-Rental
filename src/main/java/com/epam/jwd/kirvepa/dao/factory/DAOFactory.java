package com.epam.jwd.kirvepa.dao.factory;

import com.epam.jwd.kirvepa.dao.CarDAO;
import com.epam.jwd.kirvepa.dao.OrderDAO;
import com.epam.jwd.kirvepa.dao.UserDAO;
import com.epam.jwd.kirvepa.dao.impl.SQLCarDAO;
import com.epam.jwd.kirvepa.dao.impl.SQLOrderDAO;
import com.epam.jwd.kirvepa.dao.impl.SQLUserDAO;

public final class DAOFactory {
	private static final DAOFactory instance = new DAOFactory();
	
	private final UserDAO sqlUserImpl = new SQLUserDAO();
	private final CarDAO sqlCarImpl = new SQLCarDAO();
	private final OrderDAO sqlOrderImpl = new SQLOrderDAO();
	
	private DAOFactory() {
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
	public UserDAO getUserDAO(){
		return sqlUserImpl;
	}
	public CarDAO getCarDAO(){
		return sqlCarImpl;
	}
	public OrderDAO getOrderDAO() {
		return sqlOrderImpl;
	}

}
