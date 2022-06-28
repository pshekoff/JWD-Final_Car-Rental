<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
<title>Car rental - Вход выполнен</title>
</head>
<meta charset="UTF-8">
<body>
	<h1>Вход выполнен успешно. Добро пожаловать!</h1>
	
	<div>
		<p>ID: ${userid}</p>
		<p>Имя: ${username}</p>
		<p>Роль: ${userrole}</p>
	</div>
	
	<div>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="get_car_body_list" />
			<input type="submit" value="Аренда авто" />
		</form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="get_orders" />
			<input type="submit" value="Мои бронирования" />
		</form>
	</div>	
	
	<h1></h1>
	
	<div>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="edit_profile" />
			<input type="submit" value="Редактировать профиль" />
		</form>
	</div>
	
	<h1></h1>
	
	<div>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="sign_out" />
			<input type="submit" value="Выход" />
		</form>
	</div>
	
</body>
</html>