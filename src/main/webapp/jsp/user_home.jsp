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
	  <fmt:message key="auth_user.title" />
	</title>
  </head>
  
  <body>
  	<h2>
  	  ${user_header}
  	</h2>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="get_car_body_list" />
		<input type="submit" value=<fmt:message key="auth_user.submit.rent_car" /> />
	  </form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="get_orders" />
			<input type="submit" value=<fmt:message key="auth_user.submit.orders" /> />
		</form>
	</div>	
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="edit_profile" />
			<input type="submit" value=<fmt:message key="auth_user.submit.edit_profile" /> />
		</form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="sign_out" />
			<input type="submit" value=<fmt:message key="auth_user.submit.sign_out" /> />
		</form>
	</div>
	
</body>
</html>