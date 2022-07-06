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
	  <fmt:message key="car_list.title" />
	</title>
  </head>
  
  <body>
	<div>
	  <p>
	    <fmt:message key="car_list.datefrom" /> ${date_from}
	  </p>
	  <p>
	    <fmt:message key="car_list.dateto" /> ${date_to}
	  </p>
	</div>
	
	<h2>
	  <fmt:message key="car_list.available" />
	</h2>
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="prepare_order" />
	  <table border="1">
	  	<tr>
		  <th></th>
		  <th>
			<label>Car</label>
		  </th>
		  <th>
		  	<label>Total price</label>
		  </th>
	  	</tr>
	  		  	
		<c:forEach var="car" items="${cars}">
		
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${car}" name="car" value="${car}"> 
			  <input type="hidden" name="manufacturer" value="${car.key.getManufacturer()}">
			  <input type="hidden" name="model" value="${car.key.getModel()}"> 
			  <input type="hidden" name="body" value="${car.key.getBodyType()}"> 
			  <input type="hidden" name="engine" value="${car.key.getEngine()}"> 
			  <input type="hidden" name="transmission" value="${car.key.getTransmission()}"> 
			  <input type="hidden" name="drive" value="${car.key.getDriveType()}"> 
			  <input type="hidden" name="price" value="${car.value}"> 
		  	</td>
		  	<td width="700" height="8">
		  	  <label for="${car}">
		  	  	${car.key}
		  	  </label>
		  	</td>
		  	<td width="150" height="8">
			  <label for="${car}">
		  	  	${car.value}
			  </label>
		  	</td>
		  </tr>
		</c:forEach>
	  </table> 
	  <p></p>
	  <input type="submit" value=<fmt:message key="car_list.submit" /> />
	</form>  
 </body>
</html>