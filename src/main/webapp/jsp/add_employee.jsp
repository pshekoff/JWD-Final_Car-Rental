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
      <fmt:message key="add_employee.title" />
    </title>
  </head>

  <body>
  
	<h2>
	  <fmt:message key="add_employee.header" />
	</h2>
	
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="add_employee" />
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
		<tr>
		  <td><fmt:message key="label.first_name" /></td>
		  <td><input type="text" name="first_name" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.last_name" /></td>
		  <td><input type="text" name="last_name" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.birthday" /></td>
		  <td><input type="date" name="birthday" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.passport" /></td>
		  <td><input type="text" name="passport" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.issue_date" /></td>
		  <td><input type="date" name="issue_date" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.expiration_date" /></td>
		  <td><input type="date" name="expiration_date" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.identification_number" /></td>
		  <td><input type="text" name="identification_number" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.home_address" /></td>
		  <td><input type="text" name="address" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.phone" /></td>
		  <td><input type="text" name="phone" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.department" /></td>
		  <td><input type="text" name="department" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.position" /></td>
		  <td><input type="text" name="position" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.salary" /></td>
		  <td><input type="text" name="salary" required /></td>
		</tr>
	  </table>
	  <input type="submit" value=<fmt:message key="add_employee.submit" /> />
	  <b style="color:#ff0000">&nbsp;${add_emp_error}</b>
	</form>
  </body>
</html>