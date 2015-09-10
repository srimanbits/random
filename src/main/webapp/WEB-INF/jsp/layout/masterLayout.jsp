<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:url var="root" value="/"></c:url>
<html>
<head>
<title>
<c:choose>
<c:when test="${not empty appraisalName}">
${appraisalName}
</c:when>
<c:otherwise>
PMS Home
</c:otherwise>
</c:choose>
</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="text/html; charset=UTF-8" http-equiv="content-type">

<link href="${root}common/css/v2/common.css" rel="stylesheet" type="text/css" />
<link rel="Shortcut Icon" type="image/png" href="${root}common/images/favicon.ico">


<script type="text/javascript" src="${root}common/js/v2/jquery.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery.blockUI.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery.autosize.js"></script>
<script type="text/javascript" src="${root}common/js/v2/common.js"></script>
<%-- <script type="text/javascript" src="${root}common/js/jquery.fileDownload.js"></script> --%>
<!--  Added this to reduce traffic -->

 <script type="text/javascript" src="${root}common/js/v2/jquery.dataTables_1.9.0.js"></script>
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery.dataTables.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery-ui-1.10.0.custom.css" /> 
	<script type="text/javascript" src="${root}common/js/v2/headerMenu.js"></script>
	<script type="text/javascript" src="${root}common/js/v2/feedback.js"></script>
	<script type="text/javascript" src="${root}common/js/v2/indexForm.js"></script>
</head>

<!--  Added this to reduce traffic -->
<!-- <script type="text/javascript">
function updateReviewStatus(opsType) {
	$("#checkinDialog > p:last").html("");
	$("#checkinDialog > textarea").val("");
	if(opsType=='submit_to_appraiser') {
		submit_to_Appraiser('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}','${reviewFormInfoVO.actualReviewStatus}',${root});
	}
	if(opsType=='resubmit_to_appraise') {
		resubmit_to_appraise('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
	if(opsType=='approve_goals') {
		approve_goals('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
	if(opsType=='accept_goals') {	
		accept_goals('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
	if(opsType=='assessment_Completed') {	
		assessment_Completed('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
	if(opsType=='approve_appraisal') {	
		approve_appraisal('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
	if(opsType=='accept_appraisal') {	
		accept_appraisal('${reviewFormInfoVO.reviewHeaderId}','${reviewFormInfoVO.reviewPhase.description}','${reviewFormInfoVO.reviewFormRole.description}',${root});
	}
}

	$(document).ready(function(){
		$("#checkinDialog").dialog({
			width: 400,
			draggable: true,
			resizable: true,
			Height: 250,
			autoOpen: false,
			modal:true,
			buttons: [
						{
							text: "Yes",
							click: function() {
								checkinComments=$.trim($("#checkinDialog > textarea").val());
								if(checkinComments.length>0){
	//								$(this).dialog("close");
	//									$('#main-out').block({ message: 'Please Wait....'});
									/* if($(this).data("actualReviewStatus") == 'GOALS_SETTING_IN_PROGRESS' || $(this).data("actualReviewStatus") == 'NEED_TO_EDIT_GOALS' || $(this).data("actualReviewStatus") == 'NOT_STARTED') {
										window.open('${root}documents/downloadAppraisalForm.html?reviewHeaderId='+$(this).data("reviewHeaderId"),'mywindow','width=400,height=200,toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes,resizable=yes');	 */				
									 	/*   Using File download plugin "jquery.fileDownload.js"
									 	$.fileDownload('${root}documents/downloadAppraisalForm.html?reviewHeaderId='+$(this).data("reviewHeaderId"), {
										    successCallback: function (url) {
										        alert('You just got a file download dialog or ribbon for this URL :' + url);
										    },
										    failCallback: function (html, url) {
										 
										        alert('Your file download just failed for this URL:' + url + '\r\n' +
										                'Here was the resulting error HTML: \r\n' + html
										                );
										    }
										}); */
									//}
									var role=$(this).data("reviewFormRole");
									$.ajax({
										url : $(this).data("url"),
										async : false,
										cache : false,
										type:"POST",
										data:{reviewHeaderId:$(this).data("reviewHeaderId"),opsType:$(this).data("opsType"),checkinComments:checkinComments},
										success : function(data) {
											if(data){
												if(role == 'APPRAISE'){
													getAppraisalHome(${root},"self");
												}
												
												else if(role == 'APPRAISER'){
													getAppraisalHome(${root},"peers");
													
												}
												else if(role == 'SHARED_APPRAISER'){
													getAppraisalHome(${root},"shared");
													
												}
												else if(role == 'SUPERUSER'){
													getAppraisalHome(${root},"all");
													
												}
											}
											else {
												 setDialog("Invalid operation.","alert");
												 $( "#alert" ).dialog( "open" );	
											}
										}
									});
									$(this).dialog("close");
								} else {
									$("#checkinDialog > p:last").html("* Comments are mandatory.");
								}
							}
						},
						{
							text: "Cancel",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
					]
		});
		
		$("#notesDialog").dialog({
			width: 400,
			draggable: true,
			resizable: true,
			Height: 250,
			autoOpen: false,
			modal:true,
			buttons: [
						{
							text: "save",
							click: function() {
								//alert($(this).data("reviewHeaderId"));
								//alert($(this).data("roleDescription"));
								$.ajax({
									url : ${root}+"appraisal/saveHelperNotes.html",
									async : false,
									cache : false,
									type:"POST",
									data:{reviewHeaderId:$(this).data("reviewHeaderId"), roleDescription:$(this).data("roleDescription"), notesCommentText:$("#notesCommentText").val()}
								});
								$(this).dialog("close");
							}
						},
						{
							text: "Cancel",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
					]
		});
		
		$("#ONODialog").dialog({
			autoOpen: false,
			modal : true,
			height : 750,
			width: 1000,
			draggable : false,
			resizable : false
		});
	});


function changeStatus(btnId){
	updateReviewStatus(btnId)
}



function redirectToIndex(currentReviewStatus,currentReviewFormRole,currentReviewPhase){
	
	if('${reviewFormInfoVO.reviewFormRole}'!=$.trim(currentReviewFormRole) 
			|| '${reviewFormInfoVO.reviewPhase.description}'!=$.trim(currentReviewPhase)){
		
		
if(currentReviewFormRole=='APPRAISE'){
	if('${reviewFormInfoVO.actualReviewStatus}'=='GOALS_ACCEPTED' &&$.trim(currentReviewStatus)=='APPRAISAL_IN_PROGRESS'){
		
		return;
	}
	else{
			
			getAppraisalHome(${root},'self');
			
	}
		}
		
		if(currentReviewFormRole=='APPRAISER'){
			
			getAppraisalHome(${root},'peers');
			
		}
		
		if(currentReviewFormRole=='SHARED_APPRAISER'){
			getAppraisalHome(${root},'shared');
		}
		
		if(currentReviewFormRole=='SUPERUSER'){
			
			getAppraisalHome(${root},'all');
		}
	}
	else {
	}
}


function addNotes(reviewHeaderId, roleDescription) {

	$.ajax({
		url : ${root}+"appraisal/showHelperNotes.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId, roleDescription:roleDescription},
		success : function(data) {
			$("#notesDialog").html("");
			$("#notesDialog").html(data);
			$("#notesDialog").data("reviewHeaderId", reviewHeaderId);
			$("#notesDialog").data("roleDescription", roleDescription);
			$("#notesDialog").dialog('open');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            window.location=${root}+"authentication/login.html";
        }
	});
}

function showONO(reviewHeaderId) {

	$.ajax({
		url : ${root}+"ONOMinutes/show.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId},
		success : function(data) {
			$("#ONODialog").html(data);
			$("#ONODialog").dialog('open');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            window.location=${root}+"authentication/login.html";
        }
	});
}

</script> -->

<body>
	<div id="main-out">
		<div id="main">
			<div id="header">
				 <tiles:insertAttribute name="header" />
				<%-- <sec:authorize access="hasRole('ROLE_USER')">
					 <tiles:insertAttribute name="user-header-menu" /> 
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_MANAGER')">
					 <tiles:insertAttribute name="manager-header-menu" /> 
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_SUPERUSER')">
					 <tiles:insertAttribute name="superuser-header-menu" /> 
				</sec:authorize> --%>
			</div>
			 <div id="body">
				<div id="baseLayout-body">
			<div id="reviewFormInfoDiv"> 
	           		 <tiles:insertAttribute name="reviewFormInfo" />
			</div> 
			<sec:authorize access="hasRole('ROLE_USER')">
				<tiles:insertAttribute name="user-body-menu" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_MANAGER')">
				<tiles:insertAttribute name="manager-body-menu" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_SUPERUSER')">
				<tiles:insertAttribute name="superuser-body-menu" />
			</sec:authorize>
	
			<div id="body-content-out">
				<div id="body-content">
					<tiles:insertAttribute name="body-content" />
				</div>
			</div>
		</div>
		<!-- <div id="reloadConfirmation" title="Reload">
			<p></p>
		</div> -->
		
		<!-- <div id="checkinDialog" style="display: none;" title="Confimation">
			<p></p>
			<p style="margin-top:2px;">Comments:</p>
			<textarea rows="10" cols="80"
				style="overflow-y: auto; width: 98%; height: 98%;"></textarea>
			<p style="color: red;"></p>
		</div> -->
	<!-- 	<div id="notesDialog" style="display:none;" title="Helper Notes">
		</div> -->
		<!-- <div id="ONODialog" style="display:none;background-color: #F4F7F5;" title="One On One" ></div> -->
			</div>
			<div id="footer">
				<tiles:insertAttribute name="footer" />
			</div>
			<!-- <div id="alert" title="Message">
				<p></p>
			</div>
			<div id="confirm" title="Message">
				<p></p>
			</div>
			<div id="reloadConfirmation" title="Reload">
				<p></p>
			</div> -->
		</div>
	</div>
</body>
<%@include file="commonUtils.jsp"%>
<%@include file="../common/selfNotes.jsp"%>
<%@include file="../feedback/feedbackUtils.jsp"%>


</html>
