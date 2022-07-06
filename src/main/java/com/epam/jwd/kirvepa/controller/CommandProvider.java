package com.epam.jwd.kirvepa.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.command.Command;
import com.epam.jwd.kirvepa.controller.command.impl.AddEmployeeCommand;
import com.epam.jwd.kirvepa.controller.command.impl.AuthorizationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CarFindCommand;
import com.epam.jwd.kirvepa.controller.command.impl.CommandName;
import com.epam.jwd.kirvepa.controller.command.impl.EditProfileCommand;
import com.epam.jwd.kirvepa.controller.command.impl.GetBodyListCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderCreationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.OrderPreparationCommand;
import com.epam.jwd.kirvepa.controller.command.impl.RegistrationCommand;

public class CommandProvider {
	private static final CommandProvider instance = new CommandProvider();
	private final Map<CommandName, Command> repository = new HashMap<>();
	private static final Logger logger = LogManager.getLogger(CommandProvider.class);
	
	public CommandProvider() {
		repository.put(CommandName.AUTHORIZATION, new AuthorizationCommand());
		repository.put(CommandName.REGISTRATION, new RegistrationCommand());
		repository.put(CommandName.ADD_EMPLOYEE, new AddEmployeeCommand());
		repository.put(CommandName.EDIT_PROFILE, new EditProfileCommand());
		repository.put(CommandName.GET_CAR_BODY_LIST, new GetBodyListCommand());
		repository.put(CommandName.FIND_CAR, new CarFindCommand());
		repository.put(CommandName.PLACE_ORDER, new OrderCreationCommand());
		repository.put(CommandName.PREPARE_ORDER, new OrderPreparationCommand());
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
