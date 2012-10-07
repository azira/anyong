<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String drama = request.getParameter("drama");

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Drama Info for <%= drama %> </title>
</head>
<body>

<% if (drama == "") { // check if parameter is not passed 
	String redirectURL = "index.jsp";
	response.sendRedirect(redirectURL);
} else {
	String summary = "Summary here";
	String network = "Network here";
	String genre = "Genre here" ;
	String episode = "Episode here";
	String ratings = "Ratings here";
	String casts = "Casts here";
%>
<h1><%= drama %></h1>
<p><b>Summary</b>:<br>
<b>Genre</b>:
<b>Episodes</b>:
<b>Broadcast Network</b>:
<b>Users Rating</b>:
<b>Casts</b>
</p>

<% } %>
</body>
</html>