<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@page import="LuceneSearch.Lucene"%>
<%@page import="LuceneSearch.spellCheck"%>
<%@page import="LuceneSearch.Searcher"%>
<%@page import="LuceneSearch.indexDrama"%>
<%@page import="anyong.anyongStyle"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<%
	String userQuery = request.getParameter("query");
	response.setCharacterEncoding("UTF-8");
%>
<head>
    
    <title>anyong - your korean web search for downloads and streaming</title>
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <meta name="author" content="Hazirah Hamdani, n6945953, INN344-2">
    <meta name="description" content="Web search for korean drama that contains asianwiki,
    d-addicts and wikipedia web source. It also includes links to downloads and streaming">
    <meta name="keywords" content="Korea, drama, korean drama">
   <link rel="stylesheet" href="style/style.css" type="text/css" charset="utf-8" />
     <script type="text/javascript" src="resultPagination.js"></script>



</head>
<body> 
<% if (userQuery == null) { 
	// check if parameter is not passed - redirect to index page
			String redirectURL = "index.jsp";
			response.sendRedirect(redirectURL);
		
	} else { %> 
    <div class="wrapper">
            <!-- #Header -->
            <div class="header">
            <a href="index.jsp" target="_self"><img src="images/logo.png" alt="anyoung" id="logo"></a>
           <div class="search">
             <p style="float: left">Search results for:</p>
           <form>
             
            <input type="text" name="query" id="queryTextBox" id="search" size="70" class="searchbox"
            autofocus="autofocus" value="<%=userQuery%>">
          <input type="submit" value="SEARCH AGAIN">
           </form>
           
           
	<% if (userQuery == "") { // Tell user textfield is empty %>
		<p class="warning">Search textfield is empty!</p>
		
		   <%  } else { //Index Files if havent
		    Lucene luceneSearch = new Lucene();
			//luceneSearch.indexList();
			
			spellCheck checker = new spellCheck();

			List spellCheck = checker.correctWords(userQuery);
			
			// Check if there's a spell check
			if (spellCheck != null) {
			%>
            <p>Do you mean:
            <% // Display spell check suggestions 

			for (int i=0; i < spellCheck.size(); i++) {
					String queryCheck = spellCheck.get(i).toString();
					
					if (i == spellCheck.size()-1) { %> 
				
					<a href="search.jsp?query=<%=queryCheck%>"><%=queryCheck %></a>
			        <% } else { %>
			        <a href="search.jsp?query=<%=queryCheck%>"><%=queryCheck + ""%></a>,
			        
			        <% } } %>
			 ?</p>
			<% } else {
				Searcher searcher = new Searcher();
				// Search user query
				List<List<String>> dramaList = searcher.findByTitle(userQuery);
				
				// Display results into table
				if (dramaList.isEmpty()) {  // display msg if no result
					
				%>
				
				<p>  Cannot found results for <%= userQuery %></p>
			  </div>
          
          
        <!-- #End header -->
             <% } else { %>
  		</div>
          
          </div>  
        <!-- #End header -->
             <!-- #Divider -->
             <div class="divide">
             </div>
             <!-- #End divider -->
             
             <!-- #Search results -->
             <div class="search-results">
                <p class="resultText"> Total results found: <%= dramaList.size() %> 
                <span id="timeTaken"></span></p>
                   <script language="JavaScript">
					PLT_DisplayFormat = "(%%S%% seconds)";
					PLT_DisplayElementID = "timeTaken";
					PLT_BackColor = "#FBF7F6";
				</script>
				<script language="JavaScript" src="http://www.hashemian.com/js/PageLoadTime.js"></script>
                 <form action="" method="get" enctype="application/x-www-form-urlencoded">
                <table id="resultsTable" cellspacing="0" border="0" cellpadding="0" align="center">
                
                
		<% for (int i=0; i < dramaList.size(); i++) { 
				List drama = dramaList.get(i); 
				String title = drama.get(0).toString();
				String weburl = drama.get(1).toString();
				String imageSrc = drama.get(2).toString();
				String webSummary = Lucene.getDramaText(drama.get(1).toString());
				//Resize image to fit screen
				anyongStyle anyongS = new anyongStyle();
				List newSize = anyongS.resizeThumb(imageSrc);
				String width = null;
				String height = null;
				if (!newSize.isEmpty()) {
					height = newSize.get(0).toString();
					width = newSize.get(1).toString();
				} else {
					width = "80px";
					height = "100px";
				}
		%>	
		
                    <tr>
                    <td id="imgResult">
                        <img src="<%= imageSrc %>" onerror="this.onerror=null;this.src='images/nopic.png';"; alt="<%= title %>" 
		height="<%=height %>" width="<%= width %>">
                    </td>
                  <td id="textResult">
                     <p class="dramaText"><a href="<%= weburl %>" target="_blank"><%=title %></a><br>&nbsp;<span class="externalLink"><%= weburl %></span><br>
	<%= webSummary %><br><a href="downloads.jsp?drama=<%= title %>" target="_blank">Downloads</a>&nbsp;-&nbsp;
		<a href="streaming.jsp?drama=<%= title %>" target="_blank">Streaming</a></p>
                </td>
                    </tr>
                    
                    <tr>
                        <td id="space" height="20"></td>
                    </tr>
                      
                   <% } %>
                </table>     
             
			<!-- Pagination -->
	<% if (dramaList.size() > 10) { // display pagination if result more than 10 %>
	<div id="pageNavPosition"></div>
	</form>

<script type="text/javascript">
        var pager = new Pager('resultsTable', 8); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
        
        var thumb = document.getElementById('thumb');
        var width = thumb.clientWidth;
        var height = thumb.clientHeight;
        if (width > height) {
        	 thumb.style.width = '150px';
             thumb.style.height = '80px';
        } else {
        	 thumb.style.height = '150px';
             thumb.style.width = '80px';
        }
        
 </script>  
           
             <% } } } } %>
               </div>
             <!-- #End Search results -->
              <!-- #Divider -->
             <div class="divide">
             </div>
             <!-- #End divider -->
             
             <!-- #Footer -->
             <div class="footer">
                <div class="aboutAnyoung">
                    <b>About Anyong</b>
                   <p>anyoung is your Korean Drama-specific search engine implemented with <a href="http://lucene.apache.org/core/" target="_blank">
                   Apache Lucene</a>. It searches webpages from <a href="http://wikipedia.org">Wikipedia</a> ,<a href="http://wiki.d-addicts.com"
								target="_blank">D-Addicts Wiki</a> and <a
								href="http://asianwiki.com" target="_blank">Asian Wiki</a> covering Korean Dramas from the year 2003 to the latest.</p>
								<p>Downloads and Streaming will include a full description of the drama as well as links for downloads/streaming.</p>
                 
                </div>
                  <div class="footerText"> Copyright &#169; 2012 anyong, inc. All rights reserved.<br>
                    Designed & Developed by <a href="http://www.zee-note.net">Hazirah Hamdani</a>.&nbsp;&nbsp;
                    <a href="http://jigsaw.w3.org/css-validator/validator$link" target="_blank">Valid CSS</a><br>
                    
                     <!-- Stock photos are taken from http://www.fuzzimo.com -->
                        Stock photos from <a href="http://www.fuzzimo.com">fuzzimo</a> & <a href="http://zezu.org">zezu</a>. Fonts from <a href="http://www.fontsquirrel.com">Font Squirrel</a>.
                   </div>
             </div>
             <!-- #End Footer -->
    </div>
 <% } %>
</body>
</html>