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
	  <fmt:message key="user_orders.title" />
	</title>
  </head>
  
  <body>
  	<h2>
	  <fmt:message key="user_orders.header" />
	</h2>
	
  	<p style="color:green">
  	  ${message}
  	</p>
  	
	<p style="color:red">
  	  ${error}
  	</p>

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
		  	  	${order.getCar().toShortString(sessionScope.language)}
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
		  </tr>
		</c:forEach>
	  </table> 
  	  <p>
	  	<button type="submit" name="command" value="pay_order">
		  <fmt:message key="user_orders.button.pay" />
	  	</button>
	  	
  	  	<button type="submit" name="command" value="cancel_order">
		  <fmt:message key="user_orders.button.cancel" />
	  	</button>
	  </p>
	</form>
	
	<p>  
	  <a href="user_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>	
	</p>
  </body>
</html>