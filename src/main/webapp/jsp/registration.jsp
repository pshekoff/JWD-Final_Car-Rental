<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>

<fmt:setLocale value="${param.lang}" />
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${param.lang}">
  <head>
    <meta charset="UTF-8">
    <title>
      <fmt:message key="registration.title" />
    </title>
  </head>

  <style>
  
  /* Style all input fields */
input {
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    margin-top: 6px;
    margin-bottom: 16px;
}

/* Style the submit button */
input[type=submit] {
    background-color: #4CAF50;
    color: white;
}

/* Style the container for inputs */
.container {
    background-color: #f1f1f1;
    padding: 20px;
}

/* The message box is shown when the user clicks on the password field */
#message {
    display:none;
    background: #f1f1f1;
    color: #000;
    position: relative;
    padding: 20px;
    margin-top: 10px;
}

#message p {
    padding: 10px 35px;
    font-size: 18px;
}

/* Add a green text color and a checkmark when the requirements are right */
.valid {
    color: green;
}

.valid:before {
    position: relative;
    left: -35px;
    content: "&#10004;";
}

/* Add a red text color and an "x" icon when the requirements are wrong */
.invalid {
    color: red;
}

.invalid:before {
    position: relative;
    left: -35px;
    content: "&#10006;";
}


.registration {
  background-color: whitesmoke;
  list-style-type: none;
  padding: 0;
  border-radius: 3px;
}
.form-row {
  display: flex;
  justify-content: flex-end;
  padding: .5em;
}
.form-row > label {
  padding: .5em 1em .5em 0;
  flex: 1;
}
.form-row > input {
  flex: 2;
}
.form-row > input,
.form-row > button {
  padding: .5em;
}
.form-row > button {
  background: gray;
  color: white;
  border: 0;
}
</style>

  <body>
  	<h2>
  	  <fmt:message key="registration.header" />
  	</h2>

	<p style="color:#ff0000">
  	  ${reg_error}
  	</p>
  	
  <form action="controller" method="post">
	<input type="hidden" name="command" value="registration" />
	<ul class="registration">
      <li class="form-row">
      	<label for="username">
      	  <fmt:message key="label.username" />
      	</label>
      	<input type="text" id="username" name="login" required />
      </li>
    
      <li class="form-row">
		<label>
		  <fmt:message key="label.password" />
		</label>
		<input type="password" id="password" name="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required>
      </li>
      
	  <li class="form-row">
		<label>
		  <fmt:message key="label.password_repeat" />
		</label>
		<input type="password" id="repeat_password" name="repeat_password" required>
      </li>
      
	  <li class="form-row">
		<label for="email">
		  <fmt:message key="label.email" />
		</label>
  	  	<input type="email" id="emaul" name="email" required />
      </li>
    
	  <li class="form-row">
		<input type="submit" value=<fmt:message key="registration.submit" /> />
      </li>
	</ul>
  </form>
	
	<p>
	  <a href="/CarRental/index.jsp">
	  	<fmt:message key="href.homepage" />
	  </a>
	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <a href="authorization.jsp">
	  	<fmt:message key="href.authorization" />
	  </a>
	</p>
	
  <div id="notification">
	<h4>Password must contain:</h4>
  	<p id="letter" class="invalid">A <b>lowercase</b> letter</p>
  	<p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
  	<p id="number" class="invalid">A <b>number</b></p>
  	<p id="length" class="invalid">Minimum <b>8 characters</b></p>
  </div>  

<script>
var myInput = document.getElementById("password");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

// When the user clicks on the password field, show the message box
myInput.onfocus = function() {
  document.getElementById("notification").style.display = "block";
}

// When the user clicks outside of the password field, hide the message box
myInput.onblur = function() {
  document.getElementById("notification").style.display = "none";
}

// When the user starts to type something inside the password field
myInput.onkeyup = function() {
  // Validate lowercase letters
  var lowerCaseLetters = /[a-z]/g;
  if(myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
}

  // Validate capital letters
  var upperCaseLetters = /[A-Z]/g;
  if(myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }

  // Validate numbers
  var numbers = /[0-9]/g;
  if(myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }

  // Validate length
  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}
</script>

  </body>
</html>