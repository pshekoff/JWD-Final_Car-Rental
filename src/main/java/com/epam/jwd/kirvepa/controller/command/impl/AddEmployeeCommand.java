package com.epam.jwd.kirvepa.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class AddEmployeeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_LOGIN);
		String password = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_PASSWORD);
		String email = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_EMAIL);
		String firstName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_FIRST_NAME);
		String lastName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_LAST_NAME);
		String homeAdress = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USER_ADRESS);
		String phone = request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPLOYEE_PHONE);
		String department = request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPLOYEE_DEPARTMENT);
		String position = request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPLOYEE_POSITION);
		Double salary = Double.parseDouble(request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPLOYEE_SALARY));
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		UserService userService = serviceFactory.getUserService();

		boolean success;
		try {
			success = userService.addEmployee(new Employee(login, password.hashCode(), email, firstName, lastName, homeAdress, phone, department, position, salary));
			
			if (!success) {
				return JSPPageName.ADD_EMPLOYEE_FAIL;
			}
			else {
				return JSPPageName.ADD_EMPLOYEE_SUCCESS;
			}
			
		} catch (ServiceException e) {
			e.printStackTrace(); //temporary
			return JSPPageName.ADD_EMPLOYEE_FAIL;
		}

	}

}
