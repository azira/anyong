<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@page import="LuceneSearch.Lucene"%>
<%@page import="LuceneSearch.spellCheck"%>
<%@page import="LuceneSearch.Searcher"%>
<%@page import="LuceneSearch.indexDrama"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String userQuery = request.getParameter("query");
	//session.setAttribute( "query", query );
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form method="GET" action='search.jsp' id="searchForm">
		<p>
			anyoung<INPUT TYPE=TEXT NAME="query" SIZE=20 value="<%=userQuery%>">
		</p>
	</form>
		
		<INPUT TYPE=SUBMIT VALUE="search">
		<P>
			The query was
			<%=userQuery %></P>
		<%
		    //Index Files if havent
		    Lucene luceneSearch = new Lucene();
			luceneSearch.indexList();
			spellCheck checker = new spellCheck();
				
				List spellCheck = checker.correctWords(userQuery);
				if (spellCheck != null) {
			%>

		<p>
			Do you mean:

			<%
			Iterator spellCheckr = spellCheck.iterator();
				while (spellCheckr.hasNext()) {
					String queryCheck = spellCheckr.next().toString();
		%>

			<a href="search.jsp?query=<%=queryCheck%>"><%=queryCheck + ""%></a>

			<%
				}
			%>
		</p>
		<%
			} else { %>

		<%
		
		
		Searcher searcher = new Searcher();
		List<List<String>> dramaList = searcher.findByTitle(userQuery);
		
		%>
		<p>
			Total results:
			<%= dramaList.size() %></p>
		<% 
		if (dramaList != null) {
			for (int i=0; i < dramaList.size(); i++) {
				List drama = dramaList.get(i);
				
				
		
		%>


		<p>
			<a href=<%= drama.get(1)%>><%= drama.get(0) %></a>
		</p>


		<%
			
			
				 }
		}
		
				
			}
				
				
			%>
	
</body>
</html>