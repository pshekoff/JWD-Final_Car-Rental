<%
   String message = pageContext.getException().getMessage();
   String exception = pageContext.getException().getClass().toString();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exception</title>
</head>
<body>
<h4>Exception occurred while processing the request</h4>
<p>Message: ${error}</p>
</body>
</html>