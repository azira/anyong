<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

</head>
<body>
<p>Hello!  The time is now <%= new java.util.Date() %></p>

<form method="GET" action='search.jsp' id ="searchForm">
<p>anyoung<INPUT TYPE=TEXT NAME="query" SIZE=20></p>
<P><INPUT TYPE=SUBMIT VALUE="search">
</body>
</html>

