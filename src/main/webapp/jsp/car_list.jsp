<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
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
	  <input type="hidden" name="command" value="register_order" />
	  <table border="1">
	  	<tr>
		  <th></th>
		  <th>
			<label>
			  <fmt:message key="car_list.label.car" />
			</label>
		  </th>
		  <th>
		  	<label>
		  	  <fmt:message key="car_list.label.price" />
		  	</label>
		  </th>
	  	</tr>
	  		  	
		<c:forEach var="car" items="${cars}">
		
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${car}" name="car" value="${car.key.getManufacturer()};${car.key.getModel()};${car.key.getBodyType()};${car.key.getEngine()};${car.key.getTransmission()};${car.key.getDriveType()};${car.value}" required> 
			  <input type="hidden" name="date_from" value="${date_from}"> 
			  <input type="hidden" name="date_to" value="${date_to}"> 
		  	</td>
		  	<td width="700" height="8">
		  	  <label for="${car}">
		  	  	${car.key.toShortString()}
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
	
	<p>
	  <a href="user_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p> 
 </body>
</html>