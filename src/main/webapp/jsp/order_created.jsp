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
	
	<p>${amount} <fmt:message key="label.currency" /><fmt:message key="order_created.label.to_pay" /></p>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="order_payment" />
		<input type="submit" value=<fmt:message key="order_created.submit.order_pay" /> />
	  </form>
	</div>
	
	<p></p>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="cancel_order" />
			<input type="submit" value=<fmt:message key="order_created.submit.order_cancel" /> />
		</form>
	</div>	

</body>
</html>