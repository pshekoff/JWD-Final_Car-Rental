<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
<title>Car rental - поиск и аренда</title>
</head>
<meta charset="UTF-8">
<body>
	<h1>Доступные автомобили</h1>
	
	<div>
		<p>Дата начала аренды: ${datefrom}</p>
   		<p>Дата окончания аренды: ${dateto}</p>
	</div>
	
	<form action="controller" method="post">

		<c:forEach var="car" items="${cars}">
     		<input type="radio" name="car"/>
      		<label for="${car}">${car.toString()}</label>
      		<h3></h3>
		</c:forEach>
    	
    	<input type="hidden" name="command" value="order_car" />
   		<input type="submit" value="Забронировать" />
  	</form>

</body>
</html>