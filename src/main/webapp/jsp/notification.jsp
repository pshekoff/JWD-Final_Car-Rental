<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	  <fmt:message key="notification.title" />
	</title>
  </head>
  
  <body>
   	<p><b style="color:green">${message}</b></p>
	<p><b style="color:red">${error}</b></p>
	
  	<c:set var = "msg" value = '<%=request.getParameter("message")%>'/>
    <c:if test = "${msg != null}">
      <p>
      	<b style="color:green">
      	  <fmt:message key="${msg}" />
      	</b>
      </p>
    </c:if>
  	
  	<div>
	  <form action="controller" method="post">
	  	<input type="hidden" name="command" value="homepage" />
		<button type="submit">
		  <fmt:message key="notification.button.ok" />
		</button>
	  </form>
  	</div>
  </body>
</html>