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
.pg-normal {
	color: #0000FF;
	font-weight: normal;
	text-decoration: none;
	cursor: pointer;
}

.pg-selected {
	color: #800080;
	font-weight: bold;
	text-decoration: underline;
	cursor: pointer;
}
</style>
<script type="text/javascript" src="resultPagination.js"></script>

</head>

<body>

	<form method="GET" action='search.jsp' id="searchForm">
		<p>
			anyoung<INPUT TYPE=TEXT NAME="query" SIZE=20 value="<%=userQuery%>"><INPUT TYPE=SUBMIT VALUE="search">
		</p>
	</form>
		
		
		<P>
			The query was
			<%=userQuery %></P>
		<%
		    //Index Files if havent
		    Lucene luceneSearch = new Lucene();
			luceneSearch.indexList();
			spellCheck checker = new spellCheck();
				
			List spellCheck = checker.correctWords(userQuery);
			// Check if there's a spell check
			if (spellCheck != null) {
			%>

		<p>
			Do you mean:

			<%
			// Display spell check suggestions 
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
		// Search user query
		List<List<String>> dramaList = searcher.findByTitle(userQuery);
		
		%>
		<p>
			Total results:
			<%= dramaList.size() %></p>
			 <form action="" method="get" enctype="application/x-www-form-urlencoded">
			 <table id="results">
		<tr>
			<td>
		<% 
		// Display results into table
		if (dramaList != null) {
			for (int i=0; i < dramaList.size(); i++) {
				List drama = dramaList.get(i);

		%>
		
		<tr><td> <a href=<%= drama.get(1)%>><%= drama.get(0) %></a>
			</td>
		</tr>
		
		<%
				 }
			}

		}
			%>
			
</table>
		<br>
	<!-- Pagination -->
	<div id="pageNavPosition"></div>
	</form>
	
<script type="text/javascript">
        var pager = new Pager('results', 10); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
 </script>


</body>
</html>