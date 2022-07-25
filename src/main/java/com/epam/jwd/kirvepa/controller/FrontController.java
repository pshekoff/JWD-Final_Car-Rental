package com.epam.jwd.kirvepa.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.jwd.kirvepa.controller.command.Command;

/**
 * Servlet implementation class FrontController
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final CommandProvider provider = CommandProvider.getInstance();
	private static final Logger logger = LogManager.getLogger(FrontController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isAdmin = (boolean) request.getSession().getAttribute(RequestAttributeName.USR_ROLE);
		String page;
		if (isAdmin) {
			page = JSPPageName.ADMIN_HOMEPAGE;
		} else {
			page = JSPPageName.USER_HOMEPAGE;
		}
		request.getRequestDispatcher(page).forward(request, response);
		logger.info("Forward to \"" + page + "\".");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String commandName = request.getParameter(RequestParameterName.COMMAND);
		logger.info("Command \"" + commandName + "\" was requested.");

		Command command = provider.getCommand(commandName); 

		String page = command.execute(request, response);
		logger.info("Command \"" + commandName + "\" was executed.");
		logger.info("Forwarding to \"" + page + "\".");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);
		

	}

}
