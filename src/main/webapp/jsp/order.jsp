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
	  <fmt:message key="order_created.title" />
	</title>
  </head>
  
  <body>
	<h1>
	  <fmt:message key="order_created.welcome" />
	</h1>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="order_payment" />
		<input type="submit" value=<fmt:message key="order_created.submit.order_pay" /> />
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