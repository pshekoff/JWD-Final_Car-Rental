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
	  <fmt:message key="car_finder.title" />
	</title>
  </head>

  <body>
	<h1>
	  <fmt:message key="car_finder.label.search" />
	</h1>
	
	<form action="controller" method="post">
	  <p>
	  	<fmt:message key="car_finder.label.datefrom" />
	  	<input type="date" name="date_from" required />
	  <p>
	  	<fmt:message key="car_finder.label.dateto" />
	  	<input type="date" name="date_to" required />
   		
	  <h3>
	    <fmt:message key="car_finder.label.body" />
	  </h3>

	  <c:forEach var="body" items="${bodylist}">
		<input type="checkbox" name="body" value="${body}" />
		<label>${body}</label>
		<p></p>
	  </c:forEach>
	  
	  <input type="hidden" name="command" value="find_car" />
	  <input type="submit" value=<fmt:message key="car_finder.submit.search" /> />
	  <b style="color:red">&nbsp;${car_finder_error}</b>
  	</form>
	
	<p>
	  <a href="user_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p>
  </body>
</html>