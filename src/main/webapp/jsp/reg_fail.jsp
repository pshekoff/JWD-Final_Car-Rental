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
      <fmt:message key="reg_fail.title" />
    </title>
  </head>
  
  <body>
    <div>
	  <h2>
		<fmt:message key="reg_fail.label.failed" />
	  </h2>
    </div>
	
	<div>
	  <h3>${message}</h3>
    </div>
    
	<a href="/CarRental/jsp/registration.jsp">
	 <fmt:message key="href.registeration" />
	</a>
	
	<fmt:message key="separator" />

	<a href="/CarRental/jsp/authorization.jsp">
	  <fmt:message key="href.authorization" />
	</a>
	
	<h2></h2>
	<a href="/CarRental/index.jsp">
	  <fmt:message key="href.homepage" />
	</a>
  </body>
</html>