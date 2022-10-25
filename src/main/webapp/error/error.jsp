<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${param.lang}">
  <head>
	<meta charset="UTF-8">
	<title>
	  <fmt:message key="error_page.title" />
	</title>
  </head>

  <body>
	<h4>
	  <fmt:message key="error_page.header" />
	</h4>

	<p>
	  <b style="color:#ff0000">${error}</b>
	</p>
	
	<% if (request.getParameter("error") != null) {
		out.println("<b style=\"color:#ff0000\">" + request.getParameter("error") + "</b>");
	} %>

  	<div>
	  <form action="controller" method="post">
	  	<input type="hidden" name="command" value="homepage" />
		<button type="submit">
		  <fmt:message key="button.homepage" />
		</button>
	  </form>
  	</div>
  	
	<h1></h1>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="sign_out" />
		<input type="submit" value=<fmt:message key="button.exit" /> />
	  </form>
	</div>
  </body>
</html>