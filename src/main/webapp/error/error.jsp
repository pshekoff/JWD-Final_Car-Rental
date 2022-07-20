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
	  <fmt:message key="error_page.title" />
	</title>
  </head>

  <body>
	<h4>
	  <fmt:message key="error_page.header" />
	</h4>

	<p>
	  <b style="color:#ff0000">${error}</b>
	</p>
	
	<p>  
	  <a href="/CarRental/index.jsp">
		<fmt:message key="href.homepage" />
	  </a>	
	</p>
  </body>
</html>