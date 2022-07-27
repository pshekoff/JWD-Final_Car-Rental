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

	<p><b style="color:green">${user_home_message}</b></p>
	<p><b style="color:red">${user_home_error}</b></p>
	
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
			<input type="hidden" name="command" value="get_orders" />
			<input type="hidden" name="filter" value="user" />
			<input type="submit" value=<fmt:message key="user_home.submit.orders" /> />
		</form>
	</div>	
	
	<h1></h1>
	
	<div>
		<form id="addPersData" action="personal_data.jsp">
			<button type="submit" id="btnAddPersData" name="btnAddPersData">
				<fmt:message key="user_home.button.add_personal_data" />
			</button>
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