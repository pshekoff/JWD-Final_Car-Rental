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
	  <fmt:message key="new_orders.title" />
	</title>
	
	<style>
   	  .user_login {
    	display: inline-block; /* Строчно-блочный элемент */
    	position: relative; /* Относительное позиционирование */
     }
   	  .user_login:hover::after {
    	content: attr(data-title); /* Выводим текст */
    	position: absolute; /* Абсолютное позиционирование */
    	left: 20%; top: 30%; /* Положение подсказки */
    	z-index: 1; /* Отображаем подсказку поверх других элементов */
    	background: rgba(255,255,230,0.9); /* Полупрозрачный цвет фона */
    	font-family: Arial, sans-serif; /* Гарнитура шрифта */
    	font-size: 11px; /* Размер текста подсказки */
    	padding: 5px 10px; /* Поля */
    	border: 1px solid #333; /* Параметры рамки */
     }
	</style>
  </head>
  
  <body>
  	<h2>
	  <fmt:message key="new_orders.header" />
	</h2>
	
  	<p>
  	  ${orders_message}
  	</p>
  	
	<p style="color:#ff0000">
  	  ${orders_error}
  	</p>

	<form class="orders" action="controller" method="post">
		<table border="1">
	  	  <tr>
		  	<th></th>
		  	<th>
			  <label>
			  	<fmt:message key="label.order_id" />
			  </label>
		  	</th>
		  	<th>
			  <label>
			  	<fmt:message key="label.date_from" />
			  </label>
		  	</th>
		  	<th>
		  	  <label>
		  	  	<fmt:message key="label.date_to" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.car" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.amount" />
		  	  </label>
		  	</th>	
			<th>
		  	  <label>
		  	  	<fmt:message key="label.status" />
		  	  </label>
		  	</th>
			<th>
		  	  <label>
		  	  	<fmt:message key="label.user_login" />
		  	  </label>
		  	</th>	  		  	
	  	  </tr>
	  		  	
		<c:forEach var="order" items="${orders}">
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${order.getId()}" name="order_id" value="${order.getId()}" required> 
		  	</td>

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getId()}
			  </label>
		  	</td>
		  			  	
		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getDateFrom()}
			  </label>
		  	</td>
		  	
		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getDateTo()}
			  </label>
		  	</td>
		  	
		  	<td width="600" height="8">
		  	  <label for="${order}">
		  	  	${order.getCar().toString()}
		  	  </label>
		  	</td>

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getAmount()}
			  </label>
		  	</td>		  	

		  	<td width="100" height="8">
			  <label for="${order}">
		  	  	${order.getStatus()}
			  </label>
		  	</td>

		  	<td width="100" height="8">
			  <label class="user_login" data-title="${order.getUser().toString()}" for="${order}">
		  	  	${order.getUser().getLogin()}
			  </label>
		  	</td>	 
		  	
		  	<td>
		  	  <button type="submit" name="command" value="approve_order">
		  	  	<fmt:message key="new_orders.button.approve" />
			  </button>
		  	</td>
		  	
		  	<td>
		  	  <button type="submit" name="command" value="reject_order">
		  	  	<fmt:message key="new_orders.button.reject" />
			  </button>
		  	</td>
		  </tr>
		</c:forEach>
	  </table> 
	  <p></p>
	</form>
	
	<p>  
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.back" />
	  </a>	
	</p>
  </body>
</html>