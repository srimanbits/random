<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript"> 
$(document).ready(function(){
	
	$("#feedbackTemplate").dialog({
		autoOpen: false,
		modal : true,
		width: 800,
		draggable : false,
		resizable : false 
	});
	
});

</script>
<style>
#feedbackTable {
	border: 1px solid #BEBEBE;
	text-align: center;
	width: 100%;
/* 	margin:20px; */
}
.butttonsDiv a {
	background: transparent url('${root}common/css/images/leftcurve.png')
		no-repeat top left;
	display: block;
	float: left;
	font: 11px Verdana;
	line-height: 15px;
	height: 22px;
	padding-left: 8px;
	text-decoration: none;
}

.butttonsDiv a span {
	background: transparent url('${root}common/css/images/rightcurve.png')
		no-repeat top right;
	display: block;
	padding: 3px 20px 4px 10px;
	color: white;
	cursor: pointer;
}

 .ui-dialog-title{
font-size: 14px;

}


</style>
<div id="feedbackTemplate"
		style="display: none; overflow: auto; min-height: 200px; max-height: 600px;"
		title="Feedback"></div>