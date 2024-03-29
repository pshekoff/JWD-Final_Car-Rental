<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<!DOCTYPE html>
<html>
  
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
			<select name="body">
  			  <c:forEach items="${car_add_info.get(0)}" var="body">
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
		  <td>
			<select name=transmission>
  			  <c:forEach items="${car_add_info.get(1)}" var="transmission">
    			<option value="${transmission}">${transmission}</option>
  			  </c:forEach>
			</select>
	  	  </td>
		</tr>	
		<tr>
		  <td><fmt:message key="label.driveType" /></td>
		  <td>
			<select name="drive">
  			  <c:forEach items="${car_add_info.get(2)}" var="drive">
    			<option value="${drive}">${drive}</option>
  			  </c:forEach>
			</select>
	  	  </td>
		</tr>		
		<tr>
		  <td><fmt:message key="label.color" /></td>
		  <td>
			<select name="color">
  			  <c:forEach items="${car_add_info.get(3)}" var="color">
    			<option value="${color}">${color}</option>
  			  </c:forEach>
			</select>
	  	  </td>
		</tr>
		<tr>
		  <td><fmt:message key="label.weight" /></td>
		  <td><input type="number" name="weight" required /></td>
		</tr>
	  </table>
	  <input type="submit" value=<fmt:message key="add_car.submit" /> />
	  <b style="color:red">&nbsp;${add_car_error}</b>
	</form>
	
	<p>
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p>
  </body>
</html>