<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ошибка входа</title>
</head>
<body>
    <div>
        <h2>Вход не выполнен. {message}</h2>
    </div>
	<form class="login-form" action="controller" method="post">
		<div>
			<input type="hidden" name="command" value="authorization" />
		</div>
		<div>
			<h4>Имя пользователя</h4>
			<input type="text" name="login" placeholder="Имя пользователя" />
		</div>
		<div>
			<h4>Пароль</h4>
			<input type="password" name="password" placeholder="Пароль" />
		</div>
		<div>
			<h2></h2>
			<input type="submit" value="Sign in" />
			<a href="jsp/pass_reset.jsp">Я забыл свой логин или пароль</a>
		</div>
	</form>
	<h2></h2>
	<a href="/CarRental/index.jsp">На главную</a>

</body>
</html>