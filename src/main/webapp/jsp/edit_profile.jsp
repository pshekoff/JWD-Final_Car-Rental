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
  	  ${profile_error}
  	</p>
  	
	<h3>
  	  ${profile_message}
  	</h3>  	

	<form class="login-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="change_login" />
		<fmt:message key="label.login" />&nbsp;${sessionScope.login}
		<p>
		  <input type="text" name="login" required />
		  <input type="submit" value=<fmt:message key="edit_profile.submit.login" /> />
		</p>
	  </div>
	</form>
	
	<form class="login-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="change_password" />
		<fmt:message key="label.password" />
		<input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required />
		<p>
		  <fmt:message key="label.password_repeat" />
		  <input type="password" name="password_repeat" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required />
		  <input type="submit" value=<fmt:message key="edit_profile.submit.password" /> />
		</p>
	  </div>
	</form>
	
	<form>
	  <div>
		<input type="hidden" name="command" value="change_email" />
		<fmt:message key="label.email" />&nbsp;${sessionScope.email}
		<p>
		  <input type="email" name="email" required />
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