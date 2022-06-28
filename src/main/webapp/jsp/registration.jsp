<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>
	<form class="tab-form" action="controller" method="post">
		<div>
			<input type="hidden" name="command" value="registration" />
		</div>
		<div>
			<h4>Имя пользователя</h4>
			<input type="text" name="login" placeholder="Имя пользователя" />
		</div>
		<div class="td">
			<h4>Пароль</h4>
			<input type="password" id="txtNewPassword" name="password" placeholder="Пароль" />
		</div>
		<div class="td">
			<h4>Подтвердите пароль</h4>
			<input type="password" id="txtConfirmPassword" name="repeatpassword" placeholder="Повторите пароль" />
		</div>
		<div>
			<h4>Адрес электронной почты</h4>
			<input type="email" name="email" placeholder="Электронная почта" />
		</div>
		<div>
			<h2></h2>
			<input type="submit" value="Зарегистрироваться" />
		</div>
			<div class="registrationFormAlert" id="divCheckPasswordMatch">
		</div>
	</form>
	<div>
		<h2></h2>
		<a href="/CarRental/index.jsp">На главную</a>
	</div>

</body>
</html>