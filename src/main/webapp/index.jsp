<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Прокат автомобилей</title>
</head>
<body>
    <div>
    	<h2>
        	<fmt:message key="label.welcome" />
    	</h2>
    </div>
    <div>
        <h3>Зарегистрируйтесь или войдите в систему</h3>
    </div>
    <button onclick="location='jsp/registration.jsp'">Регистрация</button>
    <button onclick="location='jsp/authorization.jsp'">Вход</button>
</body>
</html>