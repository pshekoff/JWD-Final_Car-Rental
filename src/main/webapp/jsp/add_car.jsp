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
      <fmt:message key="add_car.title" />
    </title>
  </head>

  <body>
	<h2>
	  <fmt:message key="add_car.header" />
	</h2>
	
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="add_car" />
	  <table style="with: 50%">
		<tr>
		  <td><fmt:message key="label.manufacturer" /></td>
		  <td><input type="text" name="manufacturer" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.model" /></td>
		  <td><input type="text" name="model" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.licensePlate" /></td>
		  <td><input type="text" name="licensePlate" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.vin" /></td>
		  <td><input type="text" name="vin" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.bodyType" /></td>
		  <td>
			<select name="body" title="aaa">
  			  <c:forEach items="${bodylist}" var="body">
    			<option value="${body}">${body}</option>
  			  </c:forEach>
			</select>
	  	  </td>
		</tr>
		<tr>
		  <td><fmt:message key="label.issueYear" /></td>
		  <td><input type="number" name="issueYear" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.engine" /></td>
		  <td><input type="text" name="engine" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.transmission" /></td>
		  <td><input type="text" name="transmission" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.driveType" /></td>
		  <td><input type="text" name="drive" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.color" /></td>
		  <td><input type="text" name="color" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.weight" /></td>
		  <td><input type="number" name="weight" required /></td>
		</tr>
	  </table>
	  <input type="submit" value=<fmt:message key="add_car.submit" /> />
	  <b style="color:#ff0000">&nbsp;${add_car_error}</b>
	</form>
	
	<p>
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.back" />
	  </a>
	</p>
  </body>
</html>