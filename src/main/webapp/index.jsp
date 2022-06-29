<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
    	
	<title>
	  <fmt:message key="index.title.main" />
	</title>
  </head>
  
  <body>
	<div>
	  <h2>
		<fmt:message key="index.label.welcome" />
	  </h2>
	</div>
	
	<div>
	  <h3>
	    <fmt:message key="index.label.logination" />
	  </h3>
	</div>
	
	<button onclick="location='jsp/registration.jsp'">
	  <fmt:message key="index.button.registration" />
	</button>
	
    <button onclick="location='jsp/authorization.jsp'">
	  <fmt:message key="index.button.login" />
    </button>
  </body>
</html>