package com.epam.jwd.kirvepa.service;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.service.exception.ServiceException;

public interface EmployeeService {
	void singIn(Employee employee) throws ServiceException;
	void singOut(Employee employee) throws ServiceException;
	void registration(Employee employee) throws ServiceException;
}
