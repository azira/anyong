<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String drama = request.getParameter("drama");
	session.setAttribute( "drama", drama );

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Drama Info and Downloads for <%=drama %></title>
</head>
<body>

<% if (drama == "") { // check if parameter is not passed 
	String redirectURL = "index.jsp";
	response.sendRedirect(redirectURL);
} else {

%>
<jsp:include page="dramaInfo.jsp">
 <jsp:param name="drama" value="<%=drama %>"/>
  <jsp:param name="type" value="downloads" />
</jsp:include>
<center>
 <IFRAME frameborder="0"  
            style="width: 993px; height: 500px; border: margin: 0px;"  
            id="downloadsGCS" name="downloadsGCS" scrolling="yes"  
            src="downloadsSearch.jsp?q=<%=drama %>"></center>  
        </IFRAME>  

<% } %>
</body>
</html>