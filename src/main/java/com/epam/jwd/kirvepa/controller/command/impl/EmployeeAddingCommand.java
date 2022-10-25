package com.epam.jwd.kirvepa.controller.command.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

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
			logger.error(manager.getValue("add_employee.pass.match.error"));
			request.setAttribute(RequestAttributeName.ADD_EMP_ERR
								 , manager.getValue("add_employee.pass.match.error"));
			
			return forward(JSPPageName.ADD_EMPLOYEE);
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
		
		Map<String, String> parameters = new HashMap<>();
		
		boolean success;
		try {
			success = userService.addEmployee(employee, password.hashCode());
			
			if (!success) {
				logger.error(manager.getValue("error.empoyee.adding")
							+ manager.getValue("error.unexpected"));
				
				parameters.put(RequestAttributeName.ERR
				 	   	  	  , manager.getValue("error.empoyee.adding")
							  + manager.getValue("error.unexpected"));
				
				return redirect(JSPPageName.ERROR_PAGE, parameters);
			}
			else {
				logger.info("Employee " + employee.toString() + " has been added.");
				
				parameters.put(RequestAttributeName.NOTIFICATION_MSG
					 	   	  , manager.getValue("add_employee.success.message"));
			
				return redirect(JSPPageName.NOTIFICATION, parameters);
			}
			
		} catch (ServiceException e) {
			logger.error(e);
			parameters.put(RequestAttributeName.ERR
				 	   	  , manager.getValue("error.empoyee.adding"));
		
			return redirect(JSPPageName.ERROR_PAGE, parameters);

		} catch (ServiceUserException e) {
			logger.error(e);
			parameters.put(RequestAttributeName.NOTIFICATION_MSG
				 	   	  , manager.getValue("notification.error")
				 	   	  + e.getMessage());

			return redirect(JSPPageName.NOTIFICATION, parameters);

		}

	}

}
