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
	  <fmt:message key="notification.title" />
	</title>
  </head>
  
  <body>
  	<h2>
  	  <%= request.getParameter("notification_message") %>
  	</h2>
  	
  	<div>
	  <form action="controller" method="post">
	  	<input type="hidden" name="command" value="homepage" />
		<button type="submit">
		  <fmt:message key="notification.button.ok" />
		</button>
	  </form>
  	</div>
  </body>
</html>