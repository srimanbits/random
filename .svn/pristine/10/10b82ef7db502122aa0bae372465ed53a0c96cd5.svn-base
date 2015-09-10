<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script>
$(document).ready(function () {
	$("#index tr:even").css('background-color','rgb(250, 250, 250)');
	$("#index th").css('background-color','#eaf4fd');
	$("#index").css('width','95%' );
});
</script>
<style type="text/css">
table  { 
	border-collapse:collapse; 
	 }
 td, th { 
   padding-top: .4em; 
   padding-bottom: .4em; 
   text-align:left;
 } 
th {
text-align:left;
}

#documentSection{
margin:20px;
}
</style>
<div id="documentSection">
<c:if test="${fn:length(documentNamePath) > 0}" >
<div id="pageLocation"/>
<center>
<table id="index">
	<tr>
		<th>Sno.</th>
		<th>Document Name</th>
		<th>Download</th>
	</tr>
	<c:forEach items="${documentNamePath}" var="namePath" varStatus="status">
	<tr>
		<td>${status.count }</td>
		<td>${namePath.key}</td>
		<td><a href="${root}documents/downloadDocument.html?docURL=${namePath.value}">Click here</a></td>
	</tr>
	</c:forEach>
</table>
</center>
</c:if>
<br>
</div>