<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
session.setAttribute("language", request.getAttribute("lang"));
%>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">

	<title>
	  <fmt:message key="index.title" />
	</title>
  </head>
  
  <body>

	<h2><fmt:message  key="index.label.welcome" /></h2>
	
	<h3><fmt:message key="index.label.logination" /></h3>
	
	<form action="/CarRental/jsp/authorization.jsp">
      <button type="submit" id="btnAuth" name="btnAuth">
      	<fmt:message key="index.button.login" />
      </button>
	</form>	
	
	<p></p>	
	
	<form action="/CarRental/jsp/registration.jsp">
      <button type="submit" id="btnReg" name="btnReg">
      	<fmt:message key="index.button.registration" />
      </button>
	</form>

	<ul>
	  <li><a href="/CarRental/?lang=en"><fmt:message key="label.lang.en" /></a></li>
	  <li><a href="/CarRental/?lang=ru"><fmt:message key="label.lang.ru" /></a></li>
	</ul>
  </body>
</html>