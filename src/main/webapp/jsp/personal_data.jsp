<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${param.lang}">
  <head>
	<meta charset="UTF-8">

    <title>
      <fmt:message key="new_order.title" />
    </title>
  </head>

  <body>
   
	<form class="order-form" action="controller" method="post">
	  <div>
		<input type="hidden" name="command" value="update_order" />
	  </div>
	  <div>
		<h4>
		  <fmt:message key="label.first_name" />
		</h4>
		<input type="text" name="first_name" />
	  </div>
	  <div>
		<h4>
		  <fmt:message key="label.last_name" />
		</h4>
		<input type="text" name="last_name" />
	  </div>
	  <div>
		<h4>
		  <fmt:message key="label.birthday" />
		</h4>
		<input type="date" name="birthday" />
	  </div>		  
	  <div>
		<h4>
		  <fmt:message key="label.passport" />
		</h4>
		<input type="text" name="passport" />
	  </div>	  
	  <div>
		<h4>
		  <fmt:message key="label.pass_issue_date" />
		</h4>
		<input type="date" name="issue_date" />
	  </div>		  
	  <div>
		<h4>
		  <fmt:message key="label.expiration_date" />
		</h4>
		<input type="date" name="expiration_date" />
	  </div>	
	  <div>
		<h4>
		  <fmt:message key="label.identification_number" />
		</h4>
		<input type="text" name="identification_number" />
	  </div>	  
	  <div>
		<h4>
		  <fmt:message key="label.home_address" />
		</h4>
		<input type="text" name="address" />
	  </div>
	  <div>
		<h4>
		  <fmt:message key="label.phone" />
		</h4>
		<input type="text" name="phone" />
	  </div>	  
	  
	  <div>
		<h2></h2>
		<input type="submit" value=<fmt:message key="new_order.submit" /> />
	  </div>
	</form>
  </body>
</html>