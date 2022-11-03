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
	  <fmt:message key="registration.title" />
	</title>
  </head>
  
  <body>
	<h3><fmt:message key="reg_success.label" /></h3>
	
	<form action="/CarRental/jsp/authorization.jsp">
      <button type="submit" id="btnAuth" name="btnAuth">
      	<fmt:message key="reg_success.submit" />
      </button>
	</form>	
  </body>
</html>