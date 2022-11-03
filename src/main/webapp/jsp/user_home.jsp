<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<!DOCTYPE html>
<html>
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
		<button type="submit" id="btnAddPersData" name="pers_data_message">
		  <fmt:message key="user_home.button.add_personal_data" />
		</button>
	  </form>
	</div>
	
	<h1></h1>
		
	<div>
	  <form id="editProfile" action="edit_profile.jsp">
	  	<button type="submit" id="btnEditProfile" name="editProfile" >
		  <fmt:message key="user_home.button.edit_profile" />
		</button>
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