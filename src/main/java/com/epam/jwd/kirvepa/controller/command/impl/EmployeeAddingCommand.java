package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.bean.Employee;
import com.epam.jwd.kirvepa.bean.PersonalData;
import com.epam.jwd.kirvepa.controller.JSPPageName;
import com.epam.jwd.kirvepa.controller.RequestAttributeName;
import com.epam.jwd.kirvepa.controller.RequestParameterName;
import com.epam.jwd.kirvepa.controller.ResourceManager;
import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.service.UserService;
import com.epam.jwd.kirvepa.service.exception.ServiceException;
import com.epam.jwd.kirvepa.service.exception.ServiceUserException;
import com.epam.jwd.kirvepa.service.factory.ServiceFactory;

public class EmployeeAddingCommand implements Command {
	private static final boolean admin = true;
	private static final Logger logger = LogManager.getLogger(EmployeeAddingCommand.class);
	private static final ResourceManager manager = ResourceManager.getInstance();
	private static final UserService userService = ServiceFactory.getInstance().getUserService();
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		//employee data
		String login = request.getParameter(RequestParameterName.USR_LOGIN);
		String password = request.getParameter(RequestParameterName.USR_PASS);
		String passwordRepeat = request.getParameter(RequestParameterName.USR_PASS_REPEAT);
		String email = request.getParameter(RequestParameterName.USR_EMAIL);
		String firstName = request.getParameter(RequestParameterName.USR_F_NAME);
		String lastName = request.getParameter(RequestParameterName.USR_L_NAME);
		Date dayOfBirth = Date.valueOf(request.getParameter(RequestParameterName.USR_BIRTHDAY));
		String passportNumber = request.getParameter(RequestParameterName.USR_DOC_NUM);
		Date issueDate = Date.valueOf(request.getParameter(RequestParameterName.USR_DOC_ISSUE_DATE));
		Date expireDate = Date.valueOf(request.getParameter(RequestParameterName.USR_DOC_EXP_DATE));
		String identificationNumber = request.getParameter(RequestParameterName.USR_ID_NUM);
		String homeAddress = request.getParameter(RequestParameterName.USR_ADDRESS);
		String phone = request.getParameter(RequestParameterName.USR_PHONE);
		String department = request.getParameter(RequestParameterName.EMPL_DEPT);
		String position = request.getParameter(RequestParameterName.EMPL_POS);
		Double salary = Double.parseDouble(request.getParameter(RequestParameterName.EMPL_SAL));
		
		if (!password.equals(passwordRepeat)) {
			logger.error(manager.getValue("edit_profile.pass.match.error"));
			request.setAttribute(RequestAttributeName.ADD_EMP_ERR
								 , manager.getValue("edit_profile.pass.match.error"));
			
			return JSPPageName.ADD_EMPLOYEE;
		}
		
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
				logger.error(manager.getValue("add_employee.error")
							+ manager.getValue("error.unexpected"));
				request.setAttribute(RequestAttributeName.ADD_EMP_ERR
									 , manager.getValue("add_employee.error"));
				
				return JSPPageName.ADD_EMPLOYEE;
			}
			else {
				logger.info("Employee " + employee.toString() + " has been added.");
				request.setAttribute(RequestAttributeName.ADM_HEAD
								 	 , manager.getValue("add_employee.success.message"));
				
				return JSPPageName.ADMIN_HOMEPAGE;
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ERR
								 , manager.getValue("add_employee.error"));
			
			return JSPPageName.ERROR_PAGE;

		} catch (ServiceUserException e) {
			logger.error(e);
			request.setAttribute(RequestAttributeName.ADD_EMP_ERR
								 , manager.getValue("add_employee.error")
								 + e.getMessage());
			
			return JSPPageName.ADD_EMPLOYEE;
		}

	}

}
