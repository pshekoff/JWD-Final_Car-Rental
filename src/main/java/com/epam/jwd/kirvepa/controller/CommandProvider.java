package com.epam.jwd.kirvepa.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.controller.command.impl.EmployeeAddingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.AuthorizationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CarAddingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CarBlockingUnblockingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CarFindingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CarHandoverReturnCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CommandName;
import com.epam.jwd.kirvepa.controller.command.impl.EditProfileCommand;
import com.epam.jwd.kirvepa.controller.command.impl.EmailChangingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetCarBodyListCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetCarsCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetOrdersCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetUserOrdersCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetUsersCommand;
import com.epam.jwd.kirvepa.controller.command.impl.LoginChangingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderApprovingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderCancellationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderCreationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.PasswordChangingCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderPaymentCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderRegistrationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderRejectionCommand;
import com.epam.jwd.kirvepa.controller.command.impl.RegistrationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.SignOutCommand;
import com.epam.jwd.kirvepa.controller.command.impl.UnknownCommand;
import com.epam.jwd.kirvepa.controller.command.impl.UserAccessChangingCommand;

public class CommandProvider {
	private static final CommandProvider instance = new CommandProvider();
	private final Map<CommandName, Command> repository = new HashMap<>();
	private static final Logger logger = LogManager.getLogger(CommandProvider.class);
	
	public CommandProvider() {
		repository.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
		repository.put(CommandName.REGISTRATION, new RegistrationCommand());
		repository.put(CommandName.ADD_EMPLOYEE, new EmployeeAddingCommand());
		repository.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
		repository.put(CommandName.GET_CAR_BODY_LIST, new GetCarBodyListCommand());
		repository.put(CommandName.FIND_CAR, new CarFindingCommand());
		repository.put(CommandName.CREATE_ORDER, new OrderCreationCommand());
		repository.put(CommandName.REGISTER_ORDER, new OrderRegistrationCommand());
		repository.put(CommandName.CANCEL_ORDER, new OrderCancellationCommand());
		repository.put(CommandName.PAY_ORDER, new OrderPaymentCommand());
		repository.put(CommandName.CHANGE_LOGIN, new LoginChangingCommand());
		repository.put(CommandName.CHNGE_PASSWORD, new PasswordChangingCommand());
		repository.put(CommandName.CHANGE_EMAIL, new EmailChangingCommand());
		repository.put(CommandName.GET_USER_ORDERS, new GetUserOrdersCommand());
		repository.put(CommandName.SIGN_OUT, new SignOutCommand());
		repository.put(CommandName.UNKNOWN_COMMAND, new UnknownCommand());
		repository.put(CommandName.GET_USERS, new GetUsersCommand());
		repository.put(CommandName.USER_ACCESS_CHANGE, new UserAccessChangingCommand());
		repository.put(CommandName.GET_ORDERS, new GetOrdersCommand());
		repository.put(CommandName.APPROVE_ORDER, new OrderApprovingCommand());
		repository.put(CommandName.REJECT_ORDER, new OrderRejectionCommand());
		repository.put(CommandName.CAR_HANDOVER_RETURN, new CarHandoverReturnCommand());
		repository.put(CommandName.ADD_CAR, new CarAddingCommand());
		repository.put(CommandName.GET_CARS, new GetCarsCommand());
		repository.put(CommandName.BLOCK_UNBLOCK_CAR, new CarBlockingUnblockingCommand());
	}
	
	public static CommandProvider getInstance() {
		return instance;
	}
	
	public Command getCommand(String name){
		CommandName commandName = null;
		Command command = null;
		
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch(IllegalArgumentException | NullPointerException e){
			logger.error(e);
			command = repository.get(CommandName.UNKNOWN_COMMAND);
		}
		
		return command;
	}
	
}
