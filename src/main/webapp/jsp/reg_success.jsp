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
      <fmt:message key="reg_success.title" />
    </title>
  </head>
  
  <body>
	<h2>
	  <fmt:message key="reg_success.label.complete" />
	</h2>
	
	<form class="login-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="authorization" />
	  </div>
	  
	  <div>
		<h4>
		  <fmt:message key="label.username" />
		</h4>
		<input type="text" name="login" />
	  </div>
	  <div>
		<h4>
		  <fmt:message key="label.password" />
		</h4>
		<input type="password" name="password" />
	  </div>
	  
	  <div>
		<h2></h2>
		<input type="submit" value="Вход" />
		<a href="jsp/pass_reset.jsp">
		  <fmt:message key="href.forgot_credentials" />
		</a>
	  </div>
	</form>
	
	<h2></h2>
	<a href="/CarRental/index.jsp">
	  <fmt:message key="href.homepage" />
	</a>
  </body>
</html>