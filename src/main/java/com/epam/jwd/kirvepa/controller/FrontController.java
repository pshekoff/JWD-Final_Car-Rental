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
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("displayEmployee.jsp");
        view.forward(request, response); 
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

		String[] commandResult = command.execute(request, response).split(":");
		logger.info("Command \"" + commandName + "\" was executed.");
		
		if (commandResult[0].equals("forward")) {
			logger.info("Forwarding to \"" + commandResult[1] + "\".");
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(commandResult[1]);
			dispatcher.forward(request, response);
			
		} else if (commandResult[0].equals("redirect")) {
			logger.info("Redirecting to \"" + request.getContextPath() + commandResult[1] + "\".");
			response.sendRedirect(request.getContextPath() + commandResult[1]);
		} else {
			logger.info("Redirecting to \"" + request.getContextPath() + JSPPageName.ERROR_PAGE + "\".");
			response.sendRedirect(request.getContextPath() + JSPPageName.ERROR_PAGE);
		}

	}
	
	

}
