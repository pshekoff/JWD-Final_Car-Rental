<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
	  <fmt:message key="error_page.title" />
	</title>
  </head>

  <body>
	<h4>
	  <fmt:message key="error_page.header" />
	</h4>

	<p>
	  <b style="color:red">${error}</b>
	</p>
	
	<c:set var = "err" value = '<%=request.getParameter("error")%>'/>
	<c:if test = "${err != null}">
	  <p>
	  	<b style="color:red">
	  	  <fmt:message key="${err}" />
	  	</b>
	  </p>
	</c:if>

  	<div>
	  <form action="controller" method="post">
	  	<input type="hidden" name="command" value="homepage" />
		<button type="submit">
		  <fmt:message key="button.homepage" />
		</button>
	  </form>
  	</div>
  	
	<h1></h1>
	
	<div>
	  <form action="controller" method="post">
		<input type="hidden" name="command" value="sign_out" />
		<input type="submit" value=<fmt:message key="button.exit" /> />
	  </form>
	</div>
  </body>
</html>