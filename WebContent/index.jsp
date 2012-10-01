<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	var getjs = function(value) {
		if (!value)
			return;
		url = 'http://en.wikipedia.org/w/api.php?action=opensearch&search='
				+ value + '&format=json&callback=spellcheck';
		document.getElementById('loadMsg').innerHTML = 'Loading ... Please wait';
		var elem = document.createElement('script');
		elem.setAttribute('src', url);
		elem.setAttribute('type', 'text/javascript');
		document.getElementsByTagName('head')[0].appendChild(elem);
	};
</script>
</head>
<body>
	<p>
		Hello! The time is now
		<%=new java.util.Date()%></p>

	<form method="GET" action='search.jsp' id="searchForm">
		<p>
			anyoung<INPUT TYPE=TEXT NAME="query" 
				SIZE=20><INPUT TYPE=SUBMIT VALUE="search" onclick="getjs (this.value);"> <span
				id="loadMsg"></span>
	</form>
</body>
</html>

