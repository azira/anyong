<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@page import="LuceneSearch.Lucene"%>
<%@page import="LuceneSearch.spellCheck"%>
<%@page import="LuceneSearch.Searcher"%>
<%@page import="LuceneSearch.indexDrama"%>
<%@page import="anyong.anyongInfo"%>
<%@page import="anyong.anyongStyle"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String drama = request.getParameter("drama");
	String type = request.getParameter("type");
	response.setCharacterEncoding("UTF-8");

%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Drama Info for <%= drama %>
</title>
<link rel="stylesheet" href="style/styleInfo.css" type="text/css"
	charset="utf-8" />
</head>
<body>

	<% if (drama == "") { // check if parameter is not passed 
	String redirectURL = "index.jsp";
	response.sendRedirect(redirectURL);
	}
	
	Searcher searcher = new Searcher();
	anyongInfo data = new anyongInfo();
	// Search user query
	List<List<String>> dramaList = searcher.findByTitle(drama);
	List dramaInfo = new ArrayList();
	String imageSrc = ""; 
	String summary = "No information available";
	String genre = "No information available";
	String episodes = "No information available";
	String network = "No information available";
	String casts = "No information available";
	String ratings = "No information available";

	
	// Display results into table
	if (!dramaList.isEmpty()) {  // display msg if no result
	
		 for (int i=0; i < dramaList.size(); i++) { 
				List kdrama = dramaList.get(i); 
				String title = kdrama.get(0).toString();
				String weburl = kdrama.get(1).toString();
				imageSrc = kdrama.get(2).toString();
				if (weburl.contains("d-addicts")) {
					// Get from d-addicts - Summary, genre, episodes, broadcastnetwork & casts
					dramaInfo = data.getDramaInfo(weburl);
					summary = dramaInfo.get(0).toString();
					genre = dramaInfo.get(1).toString();
					episodes = dramaInfo.get(2).toString();
					network = dramaInfo.get(3).toString();
					casts = dramaInfo.get(4).toString();
				}
				
				if (weburl.contains("asianwiki")) {
					// get users ratings from asianwiki
					ratings = data.getRatings(weburl);
				}
				
				
			 } 
		 }%>
	<div class="wrapper">

		<!-- #Divider -->
		<div class="divide">
		
			<p class="head">
			
			<%= drama %></p>
		</div>
		<!-- #End divider -->
		<!-- #Drama Info -->
	

			<table id="dramaInfo" align="center" border=0 cellspacing=0
				cellpadding=0>
				<tr>
					<td><b>Summary</b>: <%= summary %><br> <b>Genre</b>: <%= genre %>
						<br> <b>Episodes</b>: <%= episodes %> <br> <b>Broadcast
							Network</b>: <%= network %><br> <b>Users Rating</b>: <%=ratings %><br>
						<b>Casts</b>:<br> <% 
String[] theCasts = casts.split(","); 
for (int i=0; i < theCasts.length; i++) { 
	
%> <%= theCasts[i] %> <br> <% } %></td>
					<td id="dramaPic"><img src="<%=imageSrc %>"
						onerror="this.onerror=null;this.src='images/nopic.png';" border=0
						alt="<%= drama %>" align="center"></td>
				</tr>
				<tr>
					<td colspan="2"><p>
							Information retrieved from <a href="http://wiki.d-addicts.com"
								target="_blank">D-Addicts Wiki</a> and <a
								href="http://asianwiki.com" target="_blank">Asian Wiki</a>
						</p></td>
				</tr>
			</table>


		<!-- #End Drama Info -->
			<!-- #Divider -->
	<div class="divide">
	
			<% if (type.equals("downloads")) { %>
			<p class="head">Downloads Links</p>
			<% } else { %>
			<p class="head">Streaming Online Links</p>
			<% } %>
		
	</div>
	<!-- #End divider -->
	</div>





</body>
</html>