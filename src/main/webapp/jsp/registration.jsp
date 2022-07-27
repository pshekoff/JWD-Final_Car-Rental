<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

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
  	<h2>
  	  <fmt:message key="registration.header" />
  	</h2>
  	
  	<form action="controller" method="post">
	  <input type="hidden" name="command" value="registration" />
	  <table style="with: 50%">
		<tr>
		  <td><fmt:message key="label.login" /></td>
		  <td><input type="text" name="login" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.password" /></td>
		  <td><input type="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.password_repeat" /></td>
		  <td><input type="password" name="password_repeat" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title=<fmt:message key="message.password.requirements" /> required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.email" /></td>
		  <td><input type="email" name="email" required /></td>
		</tr>		
	  </table>
	  <input type="submit" value=<fmt:message key="registration.submit" /> />
	  <b style="color:#ff0000">&nbsp;${reg_error}</b>
  	</form>
	
	<p>
	  <a href="/CarRental/index.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="authorization.jsp">
	  	<fmt:message key="href.authorization" />
	  </a>
	</p>
  </body>
</html>