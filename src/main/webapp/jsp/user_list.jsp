<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language==null}">
  <c:set scope="session" var="language" value="${param.lang}"/>
</c:if>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html>
  <head>
	<meta charset="UTF-8">
	<title>
	  <fmt:message key="user_list.title" />
	</title>
  </head>
  
  <body>
	
	<h2>
	  <fmt:message key="user_list.header" />
	</h2>
	
	<form action="controller" method="post">
	  <input type="hidden" name="command" value="user_access_change" />
	  <table border="1">
	  	<tr>
		  <th></th>
		  <th>
			<label>
			  <fmt:message key="user_list.label.userid" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="user_list.label.login" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="user_list.label.email" />
			</label>
		  <th>
			<label>
			  <fmt:message key="user_list.label.role" />
			</label>
		  </th>
		  <th>
			<label>
			  <fmt:message key="user_list.label.status" />
			</label>
		  </th>
	  	</tr>
	  		  	
		<c:forEach var="user" items="${users}">
		  <tr>
		  	<td width="32" height="8">
			  <input type="radio" id="${user.getUserId()}" name="user_id" value="${user.getUserId()}"> 
		  	</td>
		  	<td width="32" height="8">
		  	  <label for="${user}">
		  	  	${user.getUserId()}
		  	  </label>
		  	</td>
		  	<td width="128" height="8">
		  	  <label for="${user}">
		  	  	${user.getLogin()}
		  	  </label>
		  	</td>
		  	<td width="256" height="8">
		  	  <label for="${user}">
		  	  	${user.getEmail()}
		  	  </label>
		  	</td>
		  	<td width="64" height="8">
		  	  <label for="${user}">
		  	  	${user.getRole()}
		  	  </label>
		  	</td>
		  	<td width="64" height="8">
		  	  <label for="${user}">
		  	  	${user.getStatus()}
		  	  </label>
		  	</td>
		  </tr>
		</c:forEach>
	  </table>
	  <p></p>
	  <input type="submit" value=<fmt:message key="user_list.submit" /> />
	  <b style="color:#ff0000">&nbsp;${user_list_error}</b>
	</form>
	
	<p>
	  <a href="admin_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p>
 </body>
</html>