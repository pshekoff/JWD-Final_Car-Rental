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
      <fmt:message key="personal_data.title" />
    </title>
  </head>

  <body>
  	<% if (request.getParameter("pers_data_message") != null) {
		out.println("<p style=\"color:green\">" + request.getParameter("pers_data_message") + "</p>");
	} %>
  	
	<h3>
      <fmt:message key="personal_data.header" />
    </h3>

  	<form class="pers_data-form" action="controller" method="post">
	  <input type="hidden" name="command" value="add_personal_data" />
	  <table style="with: 50%">
		<tr>
		  <td><fmt:message key="label.first_name" /></td>
		  <td><input type="text" name="first_name" required/></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.last_name" /></td>
		  <td><input type="text" name="last_name" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.birthday" /></td>
		  <td><input type="date" name="birthday" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.passport" /></td>
		  <td><input type="text" name="passport" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.issue_date" /></td>
		  <td><input type="date" name="issue_date" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.expiration_date" /></td>
		  <td><input type="date" name="expiration_date" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.identification_number" /></td>
		  <td><input type="text" name="identification_number" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.home_address" /></td>
		  <td><input type="text" name="address" required /></td>
		</tr>
		<tr>
		  <td><fmt:message key="label.phone" /></td>
		  <td><input type="text" name="phone" required /></td>
		</tr>
	  </table>
	  <input type="submit" value=<fmt:message key="personal_data.submit" /> />
	  <% if (request.getParameter("pers_data_error") != null) {
		out.println("<b style=\"color:green\">" + request.getParameter("pers_data_error") + "</b>");
	  } %>
  	</form>
	
	<p>
	  <a href="user_home.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	</p> 
  </body>
</html>