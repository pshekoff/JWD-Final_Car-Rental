<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	  <fmt:message key="car_list_all.title" />
	</title>
  </head>
  
  <body>
	<h2>
	  <fmt:message key="car_list_all.header" />
	</h2>
	
	<form action="controller" method="post">
	  <table border="1">
	  	<tr>
		  <th></th>
		  <th>
			<label>
			  <fmt:message key="car_list_all.label.car_id" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="car_list_all.label.vin" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="car_list_all.label.lpn" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="car_list_all.label.car_info" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="car_list_all.label.status" />
			</label>
		  </th>
	  	</tr>
	  		  	
		<c:forEach var="car" items="${cars}">
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${car.getId()}" name="car_id" value="${car.getId()}"> 
		  	</td>
		  	<td width="50" height="8">
		  	  <label for="${car}">
		  	  	${car.getId()}
		  	  </label>
		  	</td>
		  	<td width="150" height="8">
		  	  <label for="${car}">
		  	  	${car.getVin()}
		  	  </label>
		  	</td>
		  	<td width="100" height="8">
		  	  <label for="${car}">
		  	  	${car.getLicensePlate()}
		  	  </label>
		  	</td>
		  	<td width="800" height="8">
		  	  <label for="${cars}">
		  	  	${car.toLongString(sessionScope.language)}
		  	  </label>
		  	</td>
		  	<td width="100" height="8">
		  	  <label for="${cars}">
		  	  	${car.getStatus()}
		  	  </label>
		  	</td>
		  </tr>
		</c:forEach>
	  </table>
	  <p></p>
	  <input type="hidden" name="command" value="block_unblock_car" />
	  <input type="submit" value=<fmt:message key="car_list_all.submit" /> />
	  
	  <b style="color:#ff0000">&nbsp;${car_list_all_error}</b>
	</form>
	
	<p>
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p>
 </body>
</html>