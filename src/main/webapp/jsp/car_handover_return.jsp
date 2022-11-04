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
	  <fmt:message key="car_handover_return.title" />
	</title>
	
	<style>
   	  .user_login {
    	display: inline-block;
    	position: relative;
     }
   	  .user_login:hover::after {
    	content: attr(data-title);
    	position: absolute;
    	left: 20%; top: 30%;
    	z-index: 1;
    	background: rgba(255,255,230,0.9);
    	font-family: Arial, sans-serif;
    	font-size: 11px;
    	padding: 5px 10px;
    	border: 1px solid #333;
     }
	</style>
  </head>
  
  <body>
  	<h2>
	  <fmt:message key="car_handover_return.header" />
	</h2>
  	
  	<form action="controller" method="post">
  	  <label>
  	  	<fmt:message key="car_handover_return.label.search" />
  	  </label>
  	  <input type="hidden" name="command" value="find_order" />
  	  <input type="text" name="order_id" placeholder=<fmt:message key="car_handover_return.placeholder.search" /> required />
  	  <input type="submit" value=<fmt:message key="car_handover_return.submit.order.search" /> />
  	</form>
  	
	<b style="color:red;">&nbsp;${error}</b>
	<p style="color:green;">${message}</p>
	
	<form id="hand_retnCar" action="controller" method="post">
	  <input type="hidden" name="command" value="get_orders" />
	  <input type="hidden" name="filter" value="handover_return" />
	  <input type="submit" value=<fmt:message key="admin_home.submit.all_orders" /> />
	</form>
  		
	<form class="orders" action="controller" method="post">
		<table border="1">
	  	  <tr>
		  	<th></th>
		  	<th>
			  <label>
			  	<fmt:message key="label.order_id" />
			  </label>
		  	</th>
		  	<th>
			  <label>
			  	<fmt:message key="label.date_from" />
			  </label>
		  	</th>
		  	<th>
		  	  <label>
		  	  	<fmt:message key="label.date_to" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.car" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.amount" />
		  	  </label>
		  	</th>	
			<th>
		  	  <label>
		  	  	<fmt:message key="label.status" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.user_login" />
		  	  </label>
		  	</th>	  		  	
	  	  </tr>
	  		  	
		<c:forEach var="order" items="${orders}">
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${order.getId()}" name="order_id" value="${order.getId()}" required> 
		  	</td>

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getId()}
			  </label>
		  	</td>
		  			  	
		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getDateFrom()}
			  </label>
		  	</td>
		  	
		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getDateTo()}
			  </label>
		  	</td>
		  	
		  	<td width="600" height="8">
		  	  <label for="${order}">
		  	  	${order.getCar().toLongString(sessionScope.language)}
		  	  </label>
		  	</td>

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getAmount()}
			  </label>
		  	</td>		  	

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getStatus()}
			  </label>
		  	</td>

		  	<td width="100" height="8">
			  <label class="user_login" data-title="${order.getUser().toString()}" for="${order}">
		  	  	${order.getUser().getLogin()}
			  </label>
		  	</td>
		  </tr>
		</c:forEach>
	  </table> 
	  <p>
	  	<input type="hidden" name="command" value="car_handover_return" />
	  	<input type="submit" value=<fmt:message key="car_handover_return.button.handover_return" /> />
	  </p>
	</form>
	
	<p>  
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>	
	</p>
  </body>
</html>