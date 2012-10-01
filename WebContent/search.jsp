<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
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

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>anyoung - your korean web search engine</title>
<style type="text/css">
.pg-normal a, .pg-normal a:link, .pg-normal a:active,.pg-normal a:visited {
	color: #0000FF;
	font-weight: normal;
	text-decoration: none;
	cursor: pointer;
}

.pg-selected a, .pg-selected a:link, .pg-selected a:active,.pg-selected a:visited{
	color: #800080;
	font-weight: bold;
	text-decoration: underline;
	cursor: pointer;
}
</style>
<script type="text/javascript" src="resultPagination.js"></script>

</head>

<body>
	<% if (userQuery == null) { userQuery = "";} %>
	<form method="GET" action='search.jsp' id="searchForm">
		<p>anyoung
<INPUT TYPE=TEXT NAME="query" id="queryTextBox" SIZE=20 value="<%=userQuery%>"><INPUT TYPE=SUBMIT VALUE="search">
		</p>
	</form>


	<% if (userQuery == "") { // Tell user textfield is empty %>
		<span class="warning">Search textfield is empty</span>
		
		   <%  } else { //Index Files if havent
		    Lucene luceneSearch = new Lucene();
			//luceneSearch.indexList();
			spellCheck checker = new spellCheck();

			List spellCheck = checker.correctWords(userQuery);
			
			// Check if there's a spell check
			if (spellCheck != null) {
			%>

				Do you mean:

			<% // Display spell check suggestions 

			for (int i=0; i < spellCheck.size(); i++) {
					String queryCheck = spellCheck.get(i).toString();%>

			<a href="search.jsp?query=<%=queryCheck%>"><%=queryCheck + ""%></a>

			<% } } else { %>

		<% Searcher searcher = new Searcher();
		// Search user query
		List<List<String>> dramaList = searcher.findByTitle(userQuery);
		
		// Display results into table
		if (!dramaList.isEmpty()) { 
			%>
		<p>
			Total results:
			<%= dramaList.size() %></p>
			 <form action="" method="get" enctype="application/x-www-form-urlencoded">
			 <table id="results">
		
		<% for (int i=0; i < dramaList.size(); i++) { 
				List drama = dramaList.get(i); 
				String title = drama.get(0).toString();
				String weburl = drama.get(1).toString();
				String webSummary = Lucene.getDramaText(drama.get(1).toString());
				String imageSrc = Lucene.getImageLink(weburl);
		%>	

		<tr>
		<td align="center"><img src="<%=imageSrc %>" weight="50px" height="141px"></td>
		<td> <a href=<%= weburl%>><%= title %></a><br>
		<%= webSummary %><br><a href="downloads.jsp?drama=<%= title %>">Downloads</a>&nbsp;&nbsp;<a href="streaming.jsp?drama=<%= title %>"">Streaming</a></td></tr>

		<% } } else { // display msg if no result %> 
		
		<p>Cannot found results for <%= userQuery %></p>
			
		
		<% } } %>
		

</table>
		<br>
	<!-- Pagination -->
	<% Searcher searcher = new Searcher(); 
	List<List<String>> dramaList = searcher.findByTitle(userQuery);
	if (dramaList.size() > 10) { // display pagination if result more than 10 %>
	<div id="pageNavPosition"></div>
	</form>

<script type="text/javascript">
        var pager = new Pager('results', 10); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
 </script>
<% } } %>

</body>
</html>