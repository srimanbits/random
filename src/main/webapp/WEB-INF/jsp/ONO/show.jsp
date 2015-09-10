<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<fmt:formatDate value="<%=new java.util.Date()%>" pattern="MMMMMMMMMMM"
	dateStyle="full" var="currentMonth" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>One To One</title>

<style>
/* #textDiv {
	-moz-appearance: textfield-multiline;
	-webkit-appearance: textarea;
	border: 1px solid gray;
	font: medium -moz-fixed;
	font: -webkit-small-control;
	height: 28px;
	overflow: auto;
	padding: 2px;
	resize: both;
	width: 400px;
	white-space: pre-wrap;
	background-color: white;
}
 */
.butttonsDiv a {
	background: transparent url('/pms/common/css/images/leftcurve.png')
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
	background: transparent url('/pms/common/css/images/rightcurve.png')
		no-repeat top right;
	display: block;
	padding: 3px 20px 4px 10px;
	color: white;
	cursor: pointer;
}

textarea {
	background-color: white;
}

#lastTd {
	/*border-bottom: 1pt solid black; */
}

#commentsHistory {
font-size: 12px;
}
</style>

<script type="text/javascript">

var ONOCurrent= ${ONOCurrent};
var interval = ${ONOCurrent};
var totalNoOfMinutes = ${totalNoOfMinutes};
 
showOrHideLoadPrevious();

function addMinutes(){                             
	
	if($('#newMinutes').length>0){

		setDialog("You can't add more than one at a time","alert");
		$( "#alert" ).dialog( "open" );
		return;
	}
	
	// row = $("<tr id='newMinutes'></tr>");
	 row = $("<textarea id='comments' style='margin-top:50px;'></textarea>");
	 $("#addTd").html(row);   
	    $("#saveMinutes").show();
	    $("#Clear").show();
	    $("#addMinutes").hide();
	    /*  $("#removeMinutes").show(); */
	
}

 function saveMinutes(reviewHeaderId,minutesId,textId){
// write logic to call server.
var meetingMinutes = $("#comments").val();
var commentsHistory=$("#commentsHistory").html();
if(commentsHistory==undefined)
	commentsHistory="";
if($("#comments").val().trim()==""){

	setDialog("Comments are mandatory.","alert");
	$( "#alert" ).dialog( "open" );
	return;
}
	$.ajax({
		
		url:${root}+"ONOMinutes/save.html",
		type:"POST",
		cache: false,
		data: {reviewHeaderId :reviewHeaderId,meetingMinutes:meetingMinutes,minutesId:minutesId,commentsHistory:commentsHistory},
		success:function(data){				
			ignoreTextArea(minutesId); 
			$("#ONODialog").dialog('close');
			if(data=='Error')
				notification("This ONO Minutes already Approved");
			else
				notification("ONO Minutes Saved Successfully...");
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
          	  setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
            }
		
		
		
	});
	
} 

/* function removeMinutes(){
	 $("#saveMinutes").hide();
	 $("#removeMinutes").hide();
	 $("#newMinutes").remove();
	
} */


/* function enableTextArea(id){
	
	$("#minutesText"+id).prop("disabled", false);
	$("#ignore"+id).prop("disabled", false);
	$("#edit"+id).prop("disabled", true);
	$("#save"+id).prop("disabled", false);
	
} */

 function ignoreTextArea(id){
	
	$("#minutesText"+id).prop("disabled", true);
	/* $("#ignore"+id).prop("disabled", true);
	$("#edit"+id).prop("disabled", false); */
	$("#save"+id).prop("disabled", true);
	
} 

function loadPrevious(reviewHeaderId){
	
$.ajax({
		
		url:${root}+"ONOMinutes/loadPrevious.html",
		type:"POST",
		cache: false,
		data: {reviewHeaderId :reviewHeaderId},
		success:function(data){	
			$("#ONOTable tr:first").before(data);   
			ONOCurrent = ONOCurrent+interval;
			showOrHideLoadPrevious();
			$("#ONODialog").animate({ scrollTop: 1000},200);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
          	  setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
            }
		
		
		
	});
	
}

function showOrHideLoadPrevious(){
	//alert(ONOCurrent +":"+totalNoOfMinutes);
	if(ONOCurrent < totalNoOfMinutes){
		 //alert("showing button");
		$("#btnLoadPrevious").show(); 
		return;
	 }
	//alert("hiding button");
	$("#btnLoadPrevious").hide(); 
}

function approve(reviewHeaderId , minutesId){
	var comments = $("#comments").val();
	var commentsHistory=$("commentsHistory").val();
	if ($("#comments").val().trim()=="") {
		setDialog("Comments are mandatory.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	
$.ajax({
		url:${root}+"ONOMinutes/approve.html",
		type:"POST",
		cache: false,
		data: {reviewHeaderId :reviewHeaderId,minutesId:minutesId,comments:comments,commentsHistory:commentsHistory},
		success:function(data){	
			$("#ONODialog").dialog('close');
			notification("ONO Minutes Approved Successfully...");
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
          	  setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
            }
	});
	
}

function reject(reviewHeaderId , minutesId){
	var comments = $("#comments").val();
	var commentsHistory=$("commentsHistory").val();
	if ($("#comments").val().trim()=="") {
		setDialog("Comments are mandatory.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	
$.ajax({
		url:${root}+"ONOMinutes/reject.html",
		type:"POST",
		cache: false,
		data: {reviewHeaderId :reviewHeaderId,minutesId:minutesId,comments:comments,commentsHistory:commentsHistory},
		success:function(data){	
			$("#ONODialog").dialog('close');
			notification("ONO Minutes Rejected Successfully...");
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
          	  setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
            }
	});
	
}

</script>
</head>
<body>
	<c:if test="${not empty appraisalMsg }">
		<div style="text-align: center">
			<b><c:out value="${appraisalMsg }"></b>
			</c:out>
		</div>
	</c:if>
	<table id="ONOTable" style="width: 950px;">
		<jsp:include page="rows.jsp"></jsp:include>

		<c:if test="${add == true}">
		<tr class="border_bottom" style="width: 95%; padding-bottom: 50px;">
			<td style="width: 10%;"></td>
			<td style="width: 20%;"><h3>Month: ${currentMonth }</h3></td>



			<td id="addTd" style="width: 60%">
				<textarea id='comments' style='height:100px;margin-top:50px;width:647px'></textarea>
			</td>



		</tr>
		
		<tr>
			<td align="center" colspan="3">
				<div class="butttonsDiv" id="AddCommentDiv" style="padding-top: 15px;">
			<a id="saveMinutes"
				class="92" type="H2-2014" style="margin-left: 300px; "
				title="saveMinutes"
				onClick="saveMinutes('${reviewHeaderId}','0','newMinutesText')"><span>Save</span></a>
			<a id="Clear" class="92" type="H2-2014"
				style="margin-left: 20px; " title="Clear"
				onClick="$('#comments').val('');"><span>Clear</span></a>
		</div>
			</td>
		</tr>
		</c:if>
	</table>
	<%-- <c:if test="${add == true}">
		<input type="button" onclick="addMinutes()" id="addMinutes"
			value="Add Minutes">
		<input type="button"
			onclick="saveMinutes('${reviewHeaderId}','0','newMinutesText')"
			id="saveMinutes" style='display: none' value="Save Minutes">
		<!-- <input type="button" onclick="removeMinutes()" id="removeMinutes" style='display:none' value="Remove Minutes"> -->
		<div class="butttonsDiv" id="AddCommentDiv" style="padding-top: 15px;">
			<a id="addMinutes" class="92" type="H2-2014"
				style="margin-left: 300px;" title="addMinutes"
				onClick="addMinutes()"><span>Add</span></a> <a id="saveMinutes"
				class="92" type="H2-2014" style="margin-left: 300px; display: none;"
				title="saveMinutes"
				onClick="saveMinutes('${reviewHeaderId}','0','newMinutesText')"><span>Save</span></a>
			<a id="Clear" class="92" type="H2-2014"
				style="margin-left: 20px; display: none;" title="Clear"
				onClick="$('#comments').val('');"><span>Clear</span></a>
		</div>
	</c:if> --%>
	<div class="butttonsDiv">
		<a onclick="loadPrevious('${reviewHeaderId}')" id="btnLoadPrevious"
			class="92" type="H2-2014" title="loadPrevious"><span>Load
				Previous</span></a>
	</div>
	<script type="text/javascript">
	$(document).ready(function () { 
	/* $(".scrollbottom").animate({ scrollTop: $(".scrollbottom")[0].scrollHeight}, 1000); */
		$(".scrollbottom").animate({ scrollTop: 1000},200);
});
</script>
</body>
</html>