<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
	
	<title>
	  <fmt:message key="order_created.title" />
	</title>
  </head>
  
  <body>
  	<h2>
  	  <%= request.getParameter("order_header") %>
  	</h2>
	
	<p><%= request.getParameter("amount") %> <fmt:message key="label.currency" /><fmt:message key="order_created.label.to_pay" /></p>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="pay_order" />
		<input type="hidden" name="order_id" value=<%= request.getParameter("order_id") %> />
		<input type="submit" value=<fmt:message key="order_created.submit.order_pay" /> />
	  </form>
	</div>
	
	<p></p>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="cancel_order" />
		<input type="hidden" name="order_id" value=<%= request.getParameter("order_id") %> />
		<input type="submit" value=<fmt:message key="order_created.submit.order_cancel" /> />
	  </form>
	</div>	
	<p>
	
	<a href="user_home.jsp">
	  <fmt:message key="href.homepage" />
	</a>
  </p> 
</body>
</html>