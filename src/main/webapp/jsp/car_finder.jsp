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
	<h1>Поиск и аренда автомобиля</h1>
	
	<form action="controller" method="get">
   		<p>Выберите дату начала аренды: <input type="date" name="datefrom">
   		<p>Выберите дату окончания аренды: <input type="date" name="dateto">
   		
   		<h3>Выберите тип кузова</h3>

		<c:forEach var="body" items="${bodylist}">
     		<input type="checkbox" name="body" value="${body}"/>
      		<label>${body}</label>
      		<p></p>
		</c:forEach>
    	
    	<input type="hidden" name="command" value="find_car" />
   		<input type="submit" value="Найти авто" />
  	</form>

</body>
</html>