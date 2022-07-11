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
      <fmt:message key="edit_profile.title" />
    </title>
  </head>
  
  <body>
  	<h2>
  	  <fmt:message key="edit_profile.header" />
  	</h2>

	<p style="color:#ff0000">
  	  ${error}
  	</p>
  	<p>
  	  ${message}
  	</p>
  	
	<form class="login-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="change_login" />
		<fmt:message key="label.username" />&nbsp;${sessionScope.login}
		<p>
		  <input type="text" name="login" />
		  <input type="submit" value=<fmt:message key="edit_profile.submit.login" /> />
		</p>
	  </div>

	  <div>
		<input type="hidden" name="command" value="change_password" />
		<fmt:message key="label.password" />
		<input type="password" name="login" />
		<p>
		  <fmt:message key="label.password_repeat" />
		  <input type="password" name="password_repeat" />
		  <input type="submit" value=<fmt:message key="edit_profile.submit.password" /> />
		</p>
	  </div>
	  
	  <div>
		<input type="hidden" name="command" value="change_email" />
		<fmt:message key="label.email" />&nbsp;${sessionScope.email}
		<p>
		  <input type="text" name="email" />
		  <input type="submit" value=<fmt:message key="edit_profile.submit.email" /> />
		</p>
	  </div>

	</form>
    
    <p>
	  <a href="user_home.jsp">
		<fmt:message key="href.back" />
	  </a>
	</p>
  </body>
</html>