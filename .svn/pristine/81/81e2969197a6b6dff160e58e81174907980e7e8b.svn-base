<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@include file="../../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<c:url var="root" value="/"></c:url>
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${root}common/js/v2/developmentPlanForm.js"></script>
<script type="text/javascript">
var noOfElementsOnPage = 0;
	function deleteActivity(deleteImgId,root){
		var deleteImgIdSuffixNo=deleteImgId.substring("deleteDevPlan".length,deleteImgId.length);
		$( "#devPlanDeleteConfirmation").dialog({
		width: 300,
		draggable: true,
		resizable: true,
		maxHeight: 400,
		modal:true,
		buttons: [
			{
				text: "Delete",
				click: function() {
					$( this ).dialog( "close" );
					deleteDevPlanActivity(deleteImgIdSuffixNo,root);
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
	
	}

	function deleteDevPlanActivity(deleteImgIdSuffixNo,root){
		$('#main-out').block({ message: 'Deleting Please Wait..'});
		var reviewDevelopmentPlanActivityId=$('#devPlan'+deleteImgIdSuffixNo+'> input:hidden').val();
		var reviewHeaderId= $(".devPlanForm > input:hidden").val();
		
		if(reviewDevelopmentPlanActivityId==0){
			$('#devPlan'+deleteImgIdSuffixNo).remove();
			manageDevPlanOptions();
			$('#main-out').unblock({ fadeOut: 0 });
			notification('Development plan activity is successfully deleted.');
		} else {
			$.ajax({
				  url: root+"appraisal/removeDevPlanActivity.html",
				  type: "POST",
				  cache: false,
				  data: {reviewDevelopmentPlanActivityId:reviewDevelopmentPlanActivityId,reviewHeaderId:reviewHeaderId},
				  success: function(data){ 
					  if(data){
						  $('#devPlan'+deleteImgIdSuffixNo).remove();
						  manageDevPlanOptions();
						  $('#main-out').unblock({ fadeOut: 0 });				  
						  notification('Development plan activity is successfully deleted.');							 
					  } else if(!data){
						  $('#main-out').unblock({ fadeOut: 0 });
						  setDialog("You can not delete this development plan activity","alert");
							$( "#alert" ).dialog( "open" );
					  }
					  else {
						  $('#main-out').unblock({ fadeOut: 0 });
						  setDialog("Network error.. Development plan activity is not deleted","alert");
							$( "#alert" ).dialog( "open" );	 
						  
					  }
		          },
		          error: function(xmlHttpRequest, textStatus, errorThrown) { 
		        	 $('#main-out').unblock({ fadeOut: 0 });
		        	 setDialog(errorThrown,"alert");
						$( "#alert" ).dialog( "open" );
		          }
			});	
		}
	}

	
$(document).ready(function(){
	// Load JS files and Cache them
	
	  jQuery.ajaxSetup({ cache:true});
	
	redirectToIndex('${reviewFormInfoVO.actualReviewStatus}','${reviewFormInfoVO.reviewFormRole}','${reviewFormInfoVO.reviewPhase.description}');
	$("#reviewStatusId").html("Status : "+'${reviewFormInfoVO.reviewStatus}');
	noOfElementsOnPage=${noOfElements};
// 	$("h2:first").addClass("active").next("div").slideToggle("slow");
    manageDevPlanOptions();
    applyBanners();
	$("#addDevelopmentPlanActivityButton").click(function() {
		
		noOfElementsOnPage++;
		addNewDevelopmentPlanActivity(${root}, noOfElementsOnPage, ${reviewFormInfoVO.reviewHeaderId});
		manageDevPlanOptions();
	});
		$("#saveDevButton").click(function(){
			
			if(saveOrNoSave('reviewDevelopmentPlan')){
				
				saveDevelopmentPlan('${root}');
			}
					
		});
	
	$("form :input").on('change', function(){
		$('#isEdited').val('yes');
	});
	$("#submitrow .expandAll").live("click", function() {

		$("#reviewDevlpmntPlanActivities h2").each(function() {
			if (!$(this).hasClass("active")){
			$(this).next("div").slideDown("slow");
			$(this).toggleClass("active");
			}

		});

	});

	$("#submitrow .collapseAll").live("click", function() {
		$("#reviewDevlpmntPlanActivities h2").each(function() {
			if ($(this).hasClass("active")){
			$(this).next("div").slideUp("slow");
			$(this).removeClass("active");
			}

		});

	});
	
	$('.goalName').keyup(function() {
		  changeBanner($(this).attr("id"));
		});
});

	
	function toggleObjective(h2){	
		if(!$(h2).hasClass("active")){
			$(h2).next("div").slideDown("slow");
					$(h2).toggleClass("active");					
			}
			
			else {
				$(h2).removeClass("active");				
					$(h2).next("div").slideUp("slow");							
			}
	}
	

		
		function changeBanner(element){	
			
			var content=$.trim($("#"+element).val());
			if(content.length==0){		
				$("#h2Banner"+element.substring(8)).text("Unnamed");	
			}
			else{
				if(content.length>50){
								
					content=content.substring(0,50)+"....";	
				}
			$("#h2Banner"+element.substring(8)).text(content);
			}
			}
		
		
		
	
function manageDevPlanOptions(){	
		if($("#reviewDevlpmntPlanActivities .devActivity").length>0){
			$("#saveDevButtonDiv").show();
			$("#submitrow .collapseAll").show();
			$("#submitrow .expandAll").show();
			
		}
		
		if($("#reviewDevlpmntPlanActivities .devActivity").length==0){
			$("#saveDevButtonDiv").hide();
			$("#submitrow .collapseAll").hide();
			$("#submitrow .expandAll").hide();
			$('#isEdited').val("no");
		}
		
	}
	
	function applyBanners(){
		
		$("[id^=goalName]").each(function(){
			changeBanner($(this).attr("id"));
		});
		
		
	}
	


</script>
<!-- <title>Review Development Plan</title> -->
</head>
<body>
	<input type="hidden" id="isEdited" name="isEdited" value="no" />

	<c:set var="editActivities" value="false" />
	<c:set var="readActivities" value="false" />

	<c:set var="readAppraiseFields" value="false" />
	<c:set var="editAppraiseFields" value="false" />

	<c:set var="readAppraiserFields" value="false" />
	<c:set var="editAppraiserFields" value="false" />

	<c:set var="displaySaveButton" value="false" />
	<c:set var="displayAddButton" value="false" />


	<c:if test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISE'}">
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE1'}">
			<c:set var="editActivities" value="true" />
			<c:set var="displayAddButton" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:choose>
				<c:when test="${reviewFormInfoVO.actualReviewStatus == 'READY_FOR_MEETING'}">
					<c:set var="readAppraiseFields" value="true" />
					<c:set var="readAppraiserFields" value="true" />
				</c:when>
				<c:otherwise>
					<c:set var="editAppraiseFields" value="true" />
					<c:set var="displayAddButton" value="true" />
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE1'}">
			<c:set var="readActivities" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE1_COMPLETED'}">
			<c:set var="readActivities" value="true" />
			<c:set var="displayAddButton" value="true" />
			<c:set var="editAppraiseFields" value="true" />
			<%-- <c:set var="displaySaveButton" value="true" /> --%>
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE2_COMPLETED'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="readAppraiserFields" value="true" />
		</c:if>
	</c:if>
	<c:if test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISER' or reviewFormInfoVO.reviewFormRole.description=='SUPERUSER' or reviewFormInfoVO.reviewFormRole.description=='SHARED_APPRAISER'}">

		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE1'}">
			<c:set var="readActivities" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE1'}">
			<c:set var="editActivities" value="true" />
			<c:set var="displayAddButton" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:if test="${reviewFormInfoVO.actualReviewStatus == 'NEED_TO_EDIT_APPRAISAL' or reviewFormInfoVO.actualReviewStatus == 'READY_FOR_MEETING'}">
				<c:set var="readAppraiseFields" value="true" />
				<c:set var="readAppraiserFields" value="true" />
			</c:if>
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="editAppraiserFields" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE1_COMPLETED'}">
			<c:set var="readActivities" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE2_COMPLETED'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="readAppraiserFields" value="true" />
		</c:if>
	</c:if>
	
	<%-- <c:if test="${reviewFormInfoVO.reviewFormRole.description=='SHARED_APPRAISER'}">

		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE1'}">
 			<c:set var="editActivities" value="true" /> 
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:if test="${reviewFormInfoVO.actualReviewStatus == 'NEED_TO_EDIT_APPRAISAL' or reviewFormInfoVO.actualReviewStatus == 'READY_FOR_MEETING'}">
				<c:set var="readAppraiseFields" value="true" />
				<c:set var="readAppraiserFields" value="true" />
			</c:if>
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE2'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="editAppraiserFields" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE1_COMPLETED'}">
			<c:set var="readActivities" value="true" />
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE2_COMPLETED'}">
			<c:set var="readActivities" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="readAppraiserFields" value="true" />
		</c:if>
	</c:if> --%>
	
	
	<form:form commandName="reviewDevelopmentPlanForm" class="devPlanForm">
		<c:if test="${editAppraiserFields and noOfElements >= 1}">
			<c:set var="displaySaveButton" value="true" />
		</c:if>
		<c:if test="${editAppraiseFields and noOfElements >= 1}">
			<c:set var="displaySaveButton" value="true" />
		</c:if>
		<input type="hidden" value="${reviewFormInfoVO.reviewHeaderId}"
			name="reviewHeaderId" />

		<div id="submitrow" style="margin-bottom: 30px; height: 10px; width: 764px;">
			<%-- <c:if test="${editActivities or displaySaveButton}">  and (editAppraiseFields or editAppraiseFields)--%>
			 <%--  and (editAppraiseFields or editAppraiseFields)--%>
			<c:if test="${displayAddButton}">
				<div id="addNew" style="float: left; margin-right: 10px;">
					<a id="addDevelopmentPlanActivityButton" class="tooltip" title="Add new developmentplan activity"><span>Add New</span></a>
				</div>
			</c:if>
			<c:if test="${editActivities or displaySaveButton}">
				<div style="float: left;" id="saveDevButtonDiv">
					<a id="saveDevButton" class="tooltip" title="Save your developmentplan"><span>Save All</span></a> 
				</div>
			</c:if>
			<div style="float: right; margin-left: 10px;">
				<img alt="Collapse All" src="${root}common/css/images/uparrow_1.png"
					class="collapseAll" title="Collapse All">
			</div>
			<div style="float: right; margin-left: 10px;">
				<img alt="Expand All" src="${root}common/css/images/downarrow_1.png"
					class="expandAll" title="Expland All">
			</div>
		</div>
		<%-- <c:if test="${(reviewFormInfoVO.actualReviewStatus == 'GOALS_SUBMITTED' 
					|| reviewFormInfoVO.actualReviewStatus == 'GOALS_ACCEPTED' 
					|| reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2' 
					|| reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'
					|| reviewFormInfoVO.actualReviewStatus == 'COMPLETED') 
					&& fn:length(reviewDevelopmentPlanForm.reviewDevelopmentPlanActivities) == 0}">
							<c:out value="No Development Plan activities set for this appraisal period" />
		</c:if> --%>
			<div id="reviewDevlpmntPlanActivities">
				<c:forEach items="${reviewDevelopmentPlanForm.reviewDevelopmentPlanActivities}"	var="reviewDevelopmentPlanActivity" varStatus="status">
					<c:if test='${!empty reviewDevelopmentPlanActivity}'>
					<c:choose>
						<c:when
							test="${!reviewDevelopmentPlanActivity.isApproved && ((reviewFormInfoVO.reviewFormRole.description == 'APPRAISE' 
							&& (reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1'||reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2'|| reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE2_COMPLETED'))||
							(reviewFormInfoVO.reviewFormRole.description != 'APPRAISE' 
							&& reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'))}">
							<!-- (reviewObjective.reviewPhase=='APPRAISE_IN_PHASE1'&& reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' && reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1') -->
							<c:set var="editActivities" value="true" />
						</c:when>
						<c:otherwise>
							<c:set var="editActivities" value="false" />
						</c:otherwise>
					</c:choose> 
						<div id="devPlan${status.index}">
							<form:hidden path="reviewDevelopmentPlanActivities[${status.index}].reviewDevelopmentPlanActivityId" class="hidden" />
							<c:choose>
								<c:when test="${reviewFormInfoVO.actualReviewStatus!='COMPLETED' and  reviewDevelopmentPlanActivity.isApproved}">
									<h2 onclick="toggleObjective(this)" id="h2Banner${status.index}" style="text-decoration: underline;padding-left: 4px; color:green"></h2>
								</c:when>
								<c:otherwise>
									<h2 onclick="toggleObjective(this)" id="h2Banner${status.index}" style="text-decoration: underline;padding-left: 4px"></h2>
								</c:otherwise>
							</c:choose>
							<div class="devActivity" style="display: none;background-color: rgba(11, 159, 245, 0.207843);">
								<div class="row">
									<c:if test="${!reviewDevelopmentPlanActivity.isApproved && ((reviewFormInfoVO.reviewFormRole.description == 'APPRAISE' 
							&& (reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1'||reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2'|| reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE2_COMPLETED'))||
							(reviewFormInfoVO.reviewFormRole.description != 'APPRAISE' 
							&& reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'))}">
										<img id="deleteDevPlan${status.index}"
											src="${root}common/images/delete.jpg" alt="Delete this Goal"
											onClick="deleteActivity(this.id,${root})" style="margin-left: 736px;">
									</c:if>
								</div>

								<div class="row">
									<div class="subTitleSmall">
										<span>Development Goal<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
									</div>
									<div class="smallContent">
										<c:choose>
											<c:when test="${!editActivities}">
												<form:textarea readonly="true"
													onkeydown="preventBackspace()"
													path="reviewDevelopmentPlanActivities[${status.index}].goalComment"
													id="goalName${status.index}" class="readOnlyTextareaClass" />
											</c:when>
											<c:otherwise>
												<form:textarea
													path="reviewDevelopmentPlanActivities[${status.index}].goalComment"
													onblur="changeBanner(this.id)"
													id="goalName${status.index}" cssClass="goalName"/>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row">
									<div class="subTitleSmall">
										<span>Measurement<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
									</div>
									<div class="mediumContent">
										<c:choose>
											<c:when test="${!editActivities}">
												<form:textarea readonly="true" rows="2" cols="10"
													onkeydown="preventBackspace()"
													class="readOnlyTextareaClass"
													path="reviewDevelopmentPlanActivities[${status.index}].measurementComment" />
											</c:when>
											<c:otherwise>
												<form:textarea rows="2" cols="10"
													path="reviewDevelopmentPlanActivities[${status.index}].measurementComment" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row">
									<div class="subTitleSmall">
										<span>Action steps<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
									</div>
									<div class="mediumContent">
										<c:choose>
											<c:when test="${!editActivities}">
												<form:textarea readonly="true"
													onkeydown="preventBackspace()"
													class="readOnlyTextareaClass"
													path="reviewDevelopmentPlanActivities[${status.index}].actionStepsComment" />
											</c:when>
											<c:otherwise>
												<form:textarea
													path="reviewDevelopmentPlanActivities[${status.index}].actionStepsComment" />
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<c:if test="${editAppraiseFields or readAppraiseFields}">
									<div id="appraiseSection" class="row">
										<div class="subTitleSmall">
											<span>Appraise </span>
											<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
										</div>
										<div class="mediumContent">
												<c:choose>
													<c:when test="${readAppraiseFields}">
														<form:textarea readonly="${readAppraiseFields}" class="readOnlyTextareaClass"
															path="reviewDevelopmentPlanActivities[${status.index}].appraiseComment" />
													</c:when>
													<c:otherwise>
														<form:textarea readonly="${readAppraiseFields}"
															path="reviewDevelopmentPlanActivities[${status.index}].appraiseComment" />
													</c:otherwise>
												</c:choose>
										</div>
									</div>
								</c:if>
								<c:if test="${editAppraiserFields or readAppraiserFields}">
									<div id="appraiserSection" class="row">
										<div class="subTitleSmall">
											<span>Appraiser </span>
											<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
										</div>
										<div class="mediumContent">
											<c:choose>
												<c:when test="${readAppraiserFields}">
													<form:textarea readonly="${readAppraiserFields}" class="readOnlyTextareaClass"
														path="reviewDevelopmentPlanActivities[${status.index}].appraiserComment" />
												</c:when>
												<c:otherwise>
													<form:textarea readonly="${readAppraiserFields}"
														path="reviewDevelopmentPlanActivities[${status.index}].appraiserComment" />
												</c:otherwise>
											</c:choose>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>
	</form:form>
	<div id="devPlanDeleteConfirmation" title="Confirmation"
		style="display: none;">
		<p>Are you sure, you want to delete this Development Plan?</p>
	</div>
</body>
</html>