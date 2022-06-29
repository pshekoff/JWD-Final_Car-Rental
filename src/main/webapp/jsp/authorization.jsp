<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="en"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">

    <title>
      <fmt:message key="authorization.title" />
    </title>
  </head>

  <body>
	<form class="login-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="authorization" />
	  </div>
	  
	  <div>
		<h4>
		  <fmt:message key="authorization.label.username" />
		</h4>
		<input type="text" name="login" />
	  </div>
	  
	  <div>
		<h4>
		  <fmt:message key="authorization.label.password" />
		</h4>
		<input type="password" name="password" />
	  </div>
	  
	  <div>
		<h2></h2>
		<input type="submit" value=<fmt:message key="authorization.submit" /> />
		<a href="jsp/pass_reset.jsp"><fmt:message key="authorization.href.forgot_credentials" /></a>
	  </div>
	</form>
	
	<h2></h2>
	<a href="/CarRental/index.jsp"><fmt:message key="authorization.href.index" /></a>
  </body>
</html>