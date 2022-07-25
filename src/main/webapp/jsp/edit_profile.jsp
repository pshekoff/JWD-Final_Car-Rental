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

	<form action="controller" method="post">
	  <input type="hidden" name="command" value="change_login" />
	  <table style="with: 50%">
		<tr>
		  <td><fmt:message key="label.login" />&nbsp;<b>${sessionScope.login}</b></td>
		</tr>
		<tr>
		  <td><input type="text" name="login" required /></td>
		  <td><input type="submit" value=<fmt:message key="edit_profile.submit.login" /> /></td>
		</tr>
	  </table>
	</form>
	
	<h1></h1>
	
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="change_password" />
	  <table style="with: 50%">
		<tr>
		  <td><fmt:message key="label.new_password" /></td>
		  <td><input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.password_repeat" /></td>
		  <td><input type="password" name="password_repeat" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required /></td>
		</tr>
		<tr>
		  <td><input type="submit" value=<fmt:message key="edit_profile.submit.password" /> /></td>
		</tr>
	  </table>
	</form>
	
	<h1></h1>
	
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="change_email" />
	  <table style="with: 50%">
	  	<tr>
	  	  <td><fmt:message key="label.email" />&nbsp;<b>${sessionScope.email}</b></td>
	  	</tr>
	  	<tr>
	  	  <td><input type="email" name="email" required /></td>
	  	  <td><input type="submit" value=<fmt:message key="edit_profile.submit.email" /> /></td>
	  	</tr>
	  </table>
	</form>

    <p>
	  <a href="controller">
		<fmt:message key="href.back" />
	  </a>
	</p>
  </body>
</html>