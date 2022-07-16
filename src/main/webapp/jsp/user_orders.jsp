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
	  <fmt:message key="user_orders.title" />
	</title>
  </head>
  
  <body>
  	<h2>
	  <fmt:message key="user_orders.header" />
	</h2>
	
  	<p>
  	  ${orders_message}
  	</p>
  	
	<p style="color:#ff0000">
  	  ${orders_error}
  	</p>

	<form class="orders" action="controller" method="post">
		<table border="1">
	  	  <tr>
		  	<th></th>
		  	<th>
			  <label>DataFrom</label>
		  	</th>
		  	<th>
		  	  <label>DateTo</label>
		  	</th>
			<th>
		  	  <label>Car</label>
		  	</th>
			<th>
		  	  <label>Amount</label>
		  	</th>	
			<th>
		  	  <label>Status</label>
		  	</th>		  		  	
	  	  </tr>
	  		  	
		<c:forEach var="order" items="${orders}">
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${order.getId()}" name="order_id" value="${order.getId()}" required> 
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
		  	  	${order.getCar().toString()}
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
		  	
		  	<td>
		  	  <button type="submit" name="command" value="cancel_order">
		  	  	<fmt:message key="user_orders.button.cancel" />
			  </button>
		  	</td>
		  	
		  	<td>
		  	  <button type="submit" name="command" value="pay_order">
		  	  	<fmt:message key="user_orders.button.pay" />
			  </button>
		  	</td>
		  </tr>
		</c:forEach>
	  </table> 
	  <p></p>
	</form>
	
	<p>  
	  <a href="user_home.jsp">
	  	<fmt:message key="href.back" />
	  </a>	
	</p>
  </body>
</html>