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
      <fmt:message key="registration.title" />
    </title>
  </head>
  
  <body>
	<form class="tab-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="registration" />
	  </div>
	  
	  <div>
		<h4>
		  <fmt:message key="label.username" />
		</h4>
		<input type="text" name="login" />
	  </div>
	  
	  <div class="td">
		<h4>
		  <fmt:message key="label.password" />
		</h4>
		<input type="password" id="txtNewPassword" name="password" />
	  </div>
	  
	  <div class="td">
		<h4>
		  <fmt:message key="label.password_repeat" />
		</h4>
		<input type="password" id="txtConfirmPassword" name="repeat_password" />
	  </div>
	  
	  <div>
		<h4>
		  <fmt:message key="label.email" />
		</h4>
		<input type="email" name="email" />
	  </div>
	  
	  <div>
		<h2></h2>
		<input type="submit" value=<fmt:message key="registration.submit" /> />
	  </div>
	  
	  <div class="registrationFormAlert" id="divCheckPasswordMatch"></div>
	</form>
	
	<div>
	  <h2></h2>
	  <a href="/CarRental/index.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</div>
  </body>
</html>