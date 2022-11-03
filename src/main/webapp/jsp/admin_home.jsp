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
	  <fmt:message key="admin_home.title" />
	</title>
  </head>

  <body>
  	<h2>
  	  <fmt:message key="label.welcome" />&nbsp;${sessionScope.login}
  	</h2>

	<div>
	  <form id="editProfile" action="edit_profile.jsp">
	  	<button type="submit" id="btnEditProfile" name="editProfile" >
		  <fmt:message key="admin_home.submit.edit_profile" />
		</button>
	  </form>

	  <form id="signOut" action="controller" method="post">
		<input type="hidden" name="command" value="sign_out" />
		<input type="submit" value=<fmt:message key="admin_home.submit.sign_out" /> />
	  </form>
	</div>
	
	<div>
	  <h4><fmt:message key="admin_home.label.user_management" /></h4>
	  <form id="addEmp" action="add_employee.jsp">
      	<button type="submit" id="btnAddEmp" name="btnAddEmp">
      	  <fmt:message key="admin_home.button.add_employee" />
      	</button>
	  </form>

	  <form id="blockUsr" action="controller" method="post">
	  	<input type="hidden" name="command" value="get_users" />
	  	<input type="submit" value=<fmt:message key="admin_home.submit.block_unblock_user" /> />
	  </form>
	</div>
	
	<div>
	  <h4 id="carHead"><fmt:message key="admin_home.label.car_management" /></h4>
	  <form id="addCar" action="controller" method="post">
	  	<input type="hidden" name="command" value="get_cars_adding_info" />
	  	<input type="submit" value=<fmt:message key="admin_home.submit.add_car" /> />
	  </form>

	  <form id="block_unblockCar" action="controller" method="post">
	  	<input type="hidden" name="command" value="get_cars" />
	  	<input type="submit" value=<fmt:message key="admin_home.submit.block_unblock_car" /> />
	  </form>
	  
	  <form id="hand_retnCar" action="controller" method="post">
		<input type="hidden" name="command" value="get_orders" />
		<input type="hidden" name="filter" value="handover_return" />
		<input type="submit" value=<fmt:message key="admin_home.submit.handover_return_car" /> />
	  </form>
	
	</div>
	
	<div>
	  <h4 id="ordHead"><fmt:message key="admin_home.label.order_management" /></h4>
	  <form id="newOrd" action="controller" method="post">
		<input type="hidden" name="command" value="get_orders" />
		<input type="hidden" name="filter" value="new" />
		<input type="submit" value=<fmt:message key="admin_home.submit.new_orders" /> />
	  </form>

	  <form id="allOrd" action="controller" method="post">
		<input type="hidden" name="command" value="get_orders" />
		<input type="hidden" name="filter" value="all" />
		<input type="submit" value=<fmt:message key="admin_home.submit.all_orders" /> />
	  </form>
	</div>
  </body>
</html>