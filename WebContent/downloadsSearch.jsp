<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String drama = request.getParameter("drama");

%>
<head>
 <title>Drama info and downloads for <%= drama %></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Put the following javascript before the closing </head> tag. -->
<script>
  (function() {
    var cx = '014584964389786169008:ufenrpmviko';
    var gcse = document.createElement('script'); gcse.type = 'text/javascript'; gcse.async = true;
    gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') +
        '//www.google.com/cse/cse.js?cx=' + cx;
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(gcse, s);
  })();
</script>
</head>
<body>

<% if (drama == "") { // check if parameter is not passed 
	String redirectURL = "index.jsp";
	response.sendRedirect(redirectURL);
} else {
%>
<!-- Place this tag where you want the search results to render -->
<gcse:searchresults-only></gcse:searchresults-only> 

<% } %>
</body>
</html>