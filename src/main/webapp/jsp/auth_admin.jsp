<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
	
	<title>
	  <fmt:message key="auth_admin.title" />
	</title>
  </head>

  <body>
	<h1>
	  <fmt:message key="auth_admin.label.welcome" />
	</h1>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="add_employee" />
		<input type="submit" value=<fmt:message key="auth_admin.submit.add_employee" /> />
	  </form>
	</div>
	
	<div>
	  <form action="controller" method="get">
		<input type="hidden" name="command" value="edit_profile" />
		<input type="submit" value=<fmt:message key="auth_admin.submit.edit_profile" /> />
	  </form>
	</div>
  </body>
</html>