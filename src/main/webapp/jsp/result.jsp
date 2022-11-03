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
	  <fmt:message key="result.title" />
	</title>
  </head>
  
  <body>
  	<c:set var = "msg" value = '<%=request.getParameter("message")%>'/>
    <c:if test = "${msg != null}">
      <p>
      	<b style="color:green">
      	  <fmt:message key="${msg}" />
      	</b>
      </p>
    </c:if>
  	
  	<c:set var = "command" value = '<%=request.getParameter("command")%>'/>
  	<c:set var = "filter" value = '<%=request.getParameter("filter")%>'/>
  	<div>
	  <form action="controller" method="post">
	  	<input type="hidden" name="command" value="${command}" />
	  	<input type="hidden" name="filter" value="${filter}" />
		<button type="submit">
		  <fmt:message key="result.button.ok" />
		</button>
	  </form>
  	</div>
  </body>
</html>