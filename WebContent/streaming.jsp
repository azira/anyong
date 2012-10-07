<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String drama = request.getParameter("drama");

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Drama Info and Streaming for <%=drama %></title>
</head>
<body>
<% if (drama == "") { // check if parameter is not passed 
	String redirectURL = "index.jsp";
	response.sendRedirect(redirectURL);
} else {

%>

<jsp:include page="dramaInfo.jsp">
 <jsp:param name="drama" value="<%=drama %>"/>
  <jsp:param name="type" value="streaming" />
</jsp:include>
<center>
 <IFRAME frameborder="0"  
            style="width: 993px; height: 500px; border: margin: 0px;"  
            id="streamingGCS" name="streamingGCS" scrolling="yes"  
            src="streamingSearch.jsp?q=<%=drama %>">  </center>
        </IFRAME>  

<% } %>
</body>
</html>