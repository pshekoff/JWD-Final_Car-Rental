<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="UTF-8">
<title>Car rental - Администратор</title>
</head>
<meta charset="UTF-8">
<body>
	<h1>Вход выполнен успешно. Страница администратора!</h1>
	
	<div>
		<p>ID: ${userid}</p>
		<p>Имя: ${username}</p>
		<p>Роль: ${userrole}</p>
	</div>
	
	<div>
		<form action="controller" method="post">
			<input type="hidden" name="command" value="add_employee" />
			<input type="submit" value="Добавить администратора" />
		</form>
	</div>
	
	<div>
		<form action="controller" method="get">
			<input type="hidden" name="command" value="edit_profile" />
			<input type="submit" value="Редактировать профиль" />
		</form>
	</div>

</body>
</html>