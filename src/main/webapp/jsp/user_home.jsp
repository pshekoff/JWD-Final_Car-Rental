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
	  <fmt:message key="user_home.title" />
	</title>
  </head>
  
  <body>
  	<h2>
  	  <fmt:message key="label.welcome" />&nbsp;${sessionScope.login}
  	</h2>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="get_car_body_list" />
		<input type="hidden" name="filter" value="exist" />
		<input type="hidden" name="next_page" value="/jsp/car_finder.jsp" />
		<input type="submit" value=<fmt:message key="user_home.submit.rent_car" /> />
	  </form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="get_user_orders" />
			<input type="submit" value=<fmt:message key="user_home.submit.orders" /> />
		</form>
	</div>	
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="edit_profile" />
			<input type="submit" value=<fmt:message key="user_home.submit.edit_profile" /> />
		</form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="sign_out" />
			<input type="submit" value=<fmt:message key="user_home.submit.sign_out" /> />
		</form>
	</div>
	
</body>
</html>