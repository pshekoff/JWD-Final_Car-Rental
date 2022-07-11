package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class AddEmployeeCommand implements Command {
	private static final boolean admin = true;
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	private static final Logger logger = LogManager.getLogger(AddEmployeeCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		//employee data
		String login = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_LOGIN);
		String password = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PASS);
		String email = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_EMAIL);
		String firstName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_F_NAME);
		String lastName = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_L_NAME);
		Date dayOfBirth = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_BIRTHDAY));
		String passportNumber = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_NUM);
		Date issueDate = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_ISSUE_DATE));
		Date expireDate = Date.valueOf(request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_DOC_EXP_DATE));
		String identificationNumber = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_ID_NUM);
		String homeAddress = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_ADDRESS);
		String phone = request.getParameter(RequestParameterName.REQ_PARAM_NAME_USR_PHONE);
		String department = request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPL_DEPT);
		String position = request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPL_POS);
		Double salary = Double.parseDouble(request.getParameter(RequestParameterName.REQ_PARAM_NAME_EMPL_SAL));
		
		PersonalData personalData = new PersonalData(firstName
				, lastName
				, dayOfBirth
				, passportNumber
				, issueDate
				, expireDate
				, identificationNumber
				, homeAddress
				, phone);
		
		Employee employee = new Employee(login
				, email
				, admin
				, personalData
				, department
				, position
				, salary);

		boolean success;
		try {
			success = userService.addEmployee(employee, password.hashCode());
			
			if (!success) {
				logger.warn("Command " + CommandName.ADD_EMPLOYEE + " finished unsuccessfully.");
				return JSPPageName.ADD_EMPLOYEE_FAIL;
			}
			else {
				logger.info("Command " + CommandName.ADD_EMPLOYEE + " finished successfully.");
				return JSPPageName.ADD_EMPLOYEE_SUCCESS;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute("message", "Employee adding failed.");
			return JSPPageName.ADD_EMPLOYEE_FAIL;
		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute("message", "Employee adding failed. " + e.getMessage());
			return JSPPageName.ADD_EMPLOYEE_FAIL;
		}

	}

}
