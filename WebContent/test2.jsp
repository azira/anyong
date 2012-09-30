<html version="-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<title>Pagination Using JavaScript - www.javaworkspace.com</title>
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
</head>

<script type="text/javascript">

function Pager(tableName, itemsPerPage) {
    this.tableName = tableName;
    this.itemsPerPage = itemsPerPage;
    this.currentPage = 1;
    this.pages = 0;
    this.inited = false;
    
    this.showRecords = function(from, to) {        
        var rows = document.getElementById(tableName).rows;
        // i starts from 1 to skip table header row
        for (var i = 1; i < rows.length; i++) {
            if (i < from || i > to)  
                rows[i].style.display = 'none';
            else
                rows[i].style.display = '';
        }
    }
    
    this.showPage = function(pageNumber) {
     if (! this.inited) {
      alert("not inited");
      return;
     }

        var oldPageAnchor = document.getElementById('pg'+this.currentPage);
        oldPageAnchor.className = 'pg-normal';
        
        this.currentPage = pageNumber;
        var newPageAnchor = document.getElementById('pg'+this.currentPage);
        newPageAnchor.className = 'pg-selected';
        
        var from = (pageNumber - 1) * itemsPerPage + 1;
        var to = from + itemsPerPage - 1;
        this.showRecords(from, to);
    }   
    
    this.prev = function() {
        if (this.currentPage > 1)
            this.showPage(this.currentPage - 1);
    }
    
    this.next = function() {
        if (this.currentPage < this.pages) {
            this.showPage(this.currentPage + 1);
        }
    }                        
    
    this.init = function() {
        var rows = document.getElementById(tableName).rows;
        var records = (rows.length - 1); 
        this.pages = Math.ceil(records / itemsPerPage);
        this.inited = true;
    }

    this.showPageNav = function(pagerName, positionId) {
     if (! this.inited) {
      alert("not inited");
      return;
     }
     var element = document.getElementById(positionId);
     
     var pagerHtml = '<span onclick="' + pagerName + '.prev();" class="pg-normal"> « Prev </span> | ';
        for (var page = 1; page <= this.pages; page++) 
            pagerHtml += '<span id="pg' + page + '" class="pg-normal" onclick="' + pagerName + '.showPage(' + page + ');">' + page + '</span> | ';
        pagerHtml += '<span onclick="'+pagerName+'.next();" class="pg-normal"> Next »</span>';            
        
        element.innerHTML = pagerHtml;
    }
}

</script>
<body>
<form action="" method="get" enctype="application/x-www-form-urlencoded">
<h1>Pagination Using JavaScript - <a
	href="http://www.javaworkspace.com" target="_blank">www.javaworkspace.com</a></h1>
<table id="results">
	<tr>
		<th>#</th>
		<th>field</th>
	</tr>
	<tr>
		<td>1</td>
		<td>rajeev</td>
	</tr>
	<tr>
		<td>2</td>
		<td>ramesh</td>
	</tr>
	<tr>
		<td>3</td>
		<td>Rahul</td>
	</tr>
	<tr>
		<td>4</td>
		<td>Bala</td>
	</tr>
	<tr>
		<td>5</td>
		<td>Nathan</td>
	</tr>
	<tr>
		<td>6</td>
		<td>Robin</td>
	</tr>
	<tr>
		<td>7</td>
		<td>Sambha</td>
	</tr>
	<tr>
		<td>8</td>
		<td>Arjun</td>
	</tr>
	<tr>
		<td>9</td>
		<td>Satyan</td>
	</tr>
	<tr>
		<td>10</td>
		<td>Singapore</td>
	</tr>
</table>
<br />
<div id="pageNavPosition"></div>
<br />
<div><input type="submit"
	onclick="alert('Hey, this is just a sample!'); return false;" /> <input
	type="reset" /></div>
</form>

<script type="text/javascript"><!--
        var pager = new Pager('results', 3); 
        pager.init(); 
        pager.showPageNav('pager', 'pageNavPosition'); 
        pager.showPage(1);
    //--></script>

</body>
</html>
