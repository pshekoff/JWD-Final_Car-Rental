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
	  <fmt:message key="index.title" />
	</title>
  </head>
  
  <body>
	<h2><fmt:message key="index.label.welcome" /></h2>
	
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
  </body>
</html>