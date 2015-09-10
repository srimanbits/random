<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<c:url var="root" value="/"></c:url>
<link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${root}common/js/v2/reviewSummary.js"></script>
<script type="text/javascript" src="${root}common/js/v2/bodyMenu.js"></script>
<style>
.mandatory {
	color: red;
}

table input{
width: 171px !important;
}
</style>

<script type="text/Javascript">
$(document).ready(function(){
	if(${displayReviewCycle}){
		$('#appraisalDueDate').datepicker("destroy"); $('#goalsApprovalDueDate').datepicker("destroy");
		 $('#goalsSettingDueDate').datepicker("destroy");$('#goalsAcceptanceDueDate').datepicker("destroy");
		 $('#goalsAcceptanceDueDate').datepicker("destroy");$('#selfAppraisalDueDate').datepicker("destroy");
		 $('#appraisalDiscussionDueDate').datepicker("destroy");$('#reviewCycleStartDate').datepicker("destroy");
		 $('#reviewCycleEndDate').datepicker("destroy");
		
	}
	else{
		 $('#appraisalDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' }); 
		 $('#goalsApprovalDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy'});
		 $('#goalsSettingDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy'});
		 $('#goalsAcceptanceDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
		 $('#goalsAcceptanceDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
		 $('#selfAppraisalDueDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
		 $('#appraisalDiscussionDueDate').datepicker({changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
		 $('#reviewCycleStartDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
		 $('#reviewCycleEndDate').datepicker({ changeMonth: true, changeYear: true,dateFormat: 'dd-mm-yy' });
	 
	}
	
	function notification(message){
		$.blockUI({ 
	        message: message, 
	        fadeIn: 700, 
	        fadeOut: 700, 
	        timeout: 2000, 
	        showOverlay: false, 
	        centerY: false, 
	        css: { 
	            width: '300px', 
	            top: '10px', 
	            left: '', 
	            right: '10px', 
	            border: 'none', 
	            padding: '5px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .6, 
	            color: '#fff' 
	        } 
	    }); 	
	};


$("#addCycle").on('click', function() {
	
	var appraisalDiscussionDueDateString = $("#appraisalDiscussionDueDate").val().split("-");
	  var appraisalDiscussionDueDate = new Date(appraisalDiscussionDueDateString[2], appraisalDiscussionDueDateString[1] - 1, appraisalDiscussionDueDateString[0]);
	  
	  var appraisalDueDateString = $("#appraisalDueDate").val().split("-");
	  var appraisalDueDate = new Date(appraisalDueDateString[2], appraisalDueDateString[1] - 1, appraisalDueDateString[0]);
	  
	  var goalsSettingDueDateString = $("#goalsSettingDueDate").val().split("-");
	  var goalsSettingDueDate = new Date(goalsSettingDueDateString[2], goalsSettingDueDateString[1] - 1, goalsSettingDueDateString[0]);
	  
	  var reviewCycleStartDateString = $("#reviewCycleStartDate").val().split("-");
	  var reviewCycleStartDate = new Date(reviewCycleStartDateString[2], reviewCycleStartDateString[1] - 1, reviewCycleStartDateString[0]);
	  
	  var goalsApprovalDueDateString = $("#goalsApprovalDueDate").val().split("-");
	  var goalsApprovalDueDate = new Date(goalsApprovalDueDateString[2], goalsApprovalDueDateString[1] - 1, goalsApprovalDueDateString[0]);
	  
	  var goalsAcceptanceDueDateString = $("#goalsAcceptanceDueDate").val().split("-");
	  var goalsAcceptanceDueDate = new Date(goalsAcceptanceDueDateString[2], goalsAcceptanceDueDateString[1] - 1, goalsAcceptanceDueDateString[0]);
	  
	  var selfAppraisalDueDateString = $("#selfAppraisalDueDate").val().split("-");
	  var selfAppraisalDueDate = new Date(selfAppraisalDueDateString[2], selfAppraisalDueDateString[1] - 1, selfAppraisalDueDateString[0]);
	  
	  var reviewCycleEndDateString = $("#reviewCycleEndDate").val().split("-");
	  var reviewCycleEndDate = new Date(reviewCycleEndDateString[2], reviewCycleEndDateString[1] - 1, reviewCycleEndDateString[0]);
	  
	  var currentDateString = $("#currentDate").val().split("-");
	  var currentDate = new Date(currentDateString[2], currentDateString[1] - 1, currentDateString[0]);
	  
	  if($("#reviewCycleName").val().trim().length==0 || $("#reviewCycleName").val()==null){
			
			setDialog("Review Cycle Name cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
		
	if($("#goalsSettingDueDate").val().trim().length==0 || $("#goalsSettingDueDate").val()==null){
			
			setDialog("Goals Setting Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}

	else if (goalsSettingDueDate < reviewCycleStartDate)
		
		{
			setDialog("GoalsSettingDueDate cannot be less than ReviewCycleStartDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
	else if ($("#goalsSettingDueDate").val() == $("#reviewCycleStartDate").val())
		
	{
		setDialog("GoalsSettingDueDate cannot be equal to ReviewCycleStartDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;

		}
	else if(goalsSettingDueDate < currentDate){
		setDialog("GoalsSettingDueDate cannot be less than CurrentDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;
	}
		
		if($("#goalsApprovalDueDate").val().trim().length==0 || $("#goalsApprovalDueDate").val()==null){
			
			setDialog("Goals Approval Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
		else if(goalsApprovalDueDate < goalsSettingDueDate)
		{

					setDialog("GoalsApprovalDueDate cannot be less than GoalsSettingDueDate" , "alert");
					$( "#alert" ).dialog( "open" ); 
					return false;

			}
		else if($("#goalsApprovalDueDate").val() == $("#goalsSettingDueDate").val())
		{

					setDialog("GoalsApprovalDueDate cannot be equal to GoalsSettingDueDate" , "alert");
					$( "#alert" ).dialog( "open" ); 
					return false;

			}
		else if(goalsApprovalDueDate < currentDate){
			setDialog("GoalsApprovalDueDate cannot be less than CurrentDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
		
		if($("#goalsAcceptanceDueDate").val().trim().length==0 || $("#goalsAcceptanceDueDate").val()==null){
			
			setDialog("Goals Acceptance Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}

	else if(goalsAcceptanceDueDate < goalsApprovalDueDate)
		
		{
			setDialog("GoalsAcceptanceDueDate cannot be less than GoalsApprovalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
	else if($("#goalsAcceptanceDueDate").val() == $("#goalsApprovalDueDate").val())
		
	{
		setDialog("GoalsAcceptanceDueDate cannot be equal to GoalsApprovalDueDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;

		}
	else if(goalsAcceptanceDueDate < currentDate){
		setDialog("GoalsAcceptanceDueDate cannot be less than CurrentDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;
	}
		if($("#selfAppraisalDueDate").val().trim().length==0 || $("#selfAppraisalDueDate").val()==null){
			
			setDialog("Self Approval Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}

	else if(selfAppraisalDueDate < goalsAcceptanceDueDate)
		
		{
			setDialog("SelfAppraisalDueDate cannot be less than GoalsAcceptanceDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
	else if($("#selfAppraisalDueDate").val() == $("#goalsAcceptanceDueDate").val())
		
	{
		setDialog("SelfAppraisalDueDate cannot be equal to GoalsAcceptanceDueDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;

		}
	else if(selfAppraisalDueDate < currentDate){
		setDialog("SelfAppraisalDueDate cannot be less than CurrentDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;
	}
		if($("#appraisalDiscussionDueDate").val().trim().length==0 || $("#appraisalDiscussionDueDate").val()==null){
			
			setDialog("Appraisal Discussion Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
	else if(appraisalDiscussionDueDate < selfAppraisalDueDate)
		
		{
			setDialog("AppraisalDiscussionDueDate cannot be less than SelfAppraisalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
	else if($("#appraisalDiscussionDueDate").val() == $("#selfAppraisalDueDate").val())
		
	{
		setDialog("AppraisalDiscussionDueDate cannot be equal to SelfAppraisalDueDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;

		}
	else if(appraisalDiscussionDueDate < currentDate){
		setDialog("AppraisalDiscussionDueDate cannot be less than CurrentDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;
	}
		
	if($("#appraisalDueDate").val().trim().length==0 || $("#appraisalDueDate").val()==null){
			
			setDialog("Appraisal Due Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
		return false;
		}
		else if(appraisalDueDate < appraisalDiscussionDueDate)
		{
			setDialog("AppraisalDueDate cannot be less than AppraisalDiscussionDueDate" , "alert");
					$( "#alert" ).dialog( "open" ); 
					return false;
						}
		else if($("#appraisalDueDate").val() == $("#appraisalDiscussionDueDate").val())
		{
			setDialog("AppraisalDueDate cannot be equal to AppraisalDiscussionDueDate" , "alert");
					$( "#alert" ).dialog( "open" ); 
					return false;
						}
		else if(appraisalDueDate < currentDate){
			setDialog("AppraisalDueDate cannot be less than CurrentDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
		
		if($("#reviewCycleEndDate").val().trim().length==0 || $("#reviewCycleEndDate").val()==null){

			setDialog("Review Cycle End Date cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
			
		}

	else if(reviewCycleEndDate < appraisalDueDate)
		
		{
			setDialog("ReviewCycleEndDate cannot be less than AppraisalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
	else if($("#reviewCycleEndDate").val() == $("#appraisalDueDate").val())
		
	{
		setDialog("ReviewCycleEndDate cannot be equal to AppraisalDueDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;

		}

	else if(reviewCycleEndDate < currentDate){
		setDialog("ReviewCycleEndDate cannot be less than CurrentDate" , "alert");
		$( "#alert" ).dialog( "open" ); 
		return false;
	}
		if($("#reviewCycleStartDate").val().trim().length==0 || $("#reviewCycleStartDate").val()==null){
			
			setDialog("Review Cycle Start Date cannot be empty" , "alert");
			$("#alert").dialog( "open" ); 
			return false;
		}
		else if(reviewCycleStartDate < currentDate){
			setDialog("ReviewCycleStartDate cannot be less than CurrentDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;
		}
	
	var formData = $("#reviewCycleForm").serialize();
	$('#main-out').block({ message: 'Loading Please Wait....'});
	 $.ajax({
	    url: ${root}+"reviewCycle/save.html",
	    type: "GET",
	    data: formData,
		cache:false,
	    success: function(result){
	    	$('#main-out').unblock({ fadeOut: 0 });				  
	    	notification("Review Cycle is successfully added.");
	     	$('#body').html(result);
	    },
	    error: function(xmlHttpRequest, textStatus, errorThrown) { 
	    	alert(errorThrown);
	    }
	   });
	 });
	 

	  $("#backToReviewCycleDisplay").on('click', function() {
			$.ajax({
			    url: ${root}+"reviewCycle/reviewCycleDisplay.html",
			    type: "GET",
				cache:false,
			    success: function(result){
			     	$('#body').html(result);
			    },
			    	error: function(xmlHttpRequest, textStatus, errorThrown) { 
			    	alert(errorThrown);
			    }
			   });
		});
	  
	  $("#updateReviewCycle").on('click', function() {

		  var appraisalDiscussionDueDateString = $("#appraisalDiscussionDueDate").val().split("-");
		  var appraisalDiscussionDueDate = new Date(appraisalDiscussionDueDateString[2], appraisalDiscussionDueDateString[1] - 1, appraisalDiscussionDueDateString[0]);
		  
		  var appraisalDueDateString = $("#appraisalDueDate").val().split("-");
		  var appraisalDueDate = new Date(appraisalDueDateString[2], appraisalDueDateString[1] - 1, appraisalDueDateString[0]);
		  
		  var goalsSettingDueDateString = $("#goalsSettingDueDate").val().split("-");
		  var goalsSettingDueDate = new Date(goalsSettingDueDateString[2], goalsSettingDueDateString[1] - 1, goalsSettingDueDateString[0]);
		  
		  var reviewCycleStartDateString = $("#reviewCycleStartDate").val().split("-");
		  var reviewCycleStartDate = new Date(reviewCycleStartDateString[2], reviewCycleStartDateString[1] - 1, reviewCycleStartDateString[0]);
		  
		  var goalsApprovalDueDateString = $("#goalsApprovalDueDate").val().split("-");
		  var goalsApprovalDueDate = new Date(goalsApprovalDueDateString[2], goalsApprovalDueDateString[1] - 1, goalsApprovalDueDateString[0]);
		  
		  var goalsAcceptanceDueDateString = $("#goalsAcceptanceDueDate").val().split("-");
		  var goalsAcceptanceDueDate = new Date(goalsAcceptanceDueDateString[2], goalsAcceptanceDueDateString[1] - 1, goalsAcceptanceDueDateString[0]);
		  
		  var selfAppraisalDueDateString = $("#selfAppraisalDueDate").val().split("-");
		  var selfAppraisalDueDate = new Date(selfAppraisalDueDateString[2], selfAppraisalDueDateString[1] - 1, selfAppraisalDueDateString[0]);
		  
		  var reviewCycleEndDateString = $("#reviewCycleEndDate").val().split("-");
		  var reviewCycleEndDate = new Date(reviewCycleEndDateString[2], reviewCycleEndDateString[1] - 1, reviewCycleEndDateString[0]);
		  
		  var currentDateString = $("#currentDate").val().split("-");
		  var currentDate = new Date(currentDateString[2], currentDateString[1] - 1, currentDateString[0]);
		  
		  if($("#reviewCycleName").val().trim().length==0 || $("#reviewCycleName").val()==null){
				
				setDialog("Review Cycle Name cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}
			
		if($("#goalsSettingDueDate").val().trim().length==0 || $("#goalsSettingDueDate").val()==null){
				
				setDialog("Goals Setting Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}

		else if (goalsSettingDueDate < reviewCycleStartDate)
			
			{
				setDialog("GoalsSettingDueDate cannot be less than ReviewCycleStartDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;

				}
		else if ($("#goalsSettingDueDate").val() == $("#reviewCycleStartDate").val())
			
		{
			setDialog("GoalsSettingDueDate cannot be equal to ReviewCycleStartDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
			
			if($("#goalsApprovalDueDate").val().trim().length==0 || $("#goalsApprovalDueDate").val()==null){
				
				setDialog("Goals Approval Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}
			else if(goalsApprovalDueDate < goalsSettingDueDate)
			{

						setDialog("GoalsApprovalDueDate cannot be less than GoalsSettingDueDate" , "alert");
						$( "#alert" ).dialog( "open" ); 
						return false;

				}
			else if($("#goalsApprovalDueDate").val() == $("#goalsSettingDueDate").val())
			{

						setDialog("GoalsApprovalDueDate cannot be equal to GoalsSettingDueDate" , "alert");
						$( "#alert" ).dialog( "open" ); 
						return false;

				}
			
			if($("#goalsAcceptanceDueDate").val().trim().length==0 || $("#goalsAcceptanceDueDate").val()==null){
				
				setDialog("Goals Acceptance Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}

		else if(goalsAcceptanceDueDate < goalsApprovalDueDate)
			
			{
				setDialog("GoalsAcceptanceDueDate cannot be less than GoalsApprovalDueDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;

				}
		else if($("#goalsAcceptanceDueDate").val() == $("#goalsApprovalDueDate").val())
			
		{
			setDialog("GoalsAcceptanceDueDate cannot be equal to GoalsApprovalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
			if($("#selfAppraisalDueDate").val().trim().length==0 || $("#selfAppraisalDueDate").val()==null){
				
				setDialog("Self Approval Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}

		else if(selfAppraisalDueDate < goalsAcceptanceDueDate)
			
			{
				setDialog("SelfAppraisalDueDate cannot be less than GoalsAcceptanceDueDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;

				}
		else if($("#selfAppraisalDueDate").val() == $("#goalsAcceptanceDueDate").val())
			
		{
			setDialog("SelfAppraisalDueDate cannot be equal to GoalsAcceptanceDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
			if($("#appraisalDiscussionDueDate").val().trim().length==0 || $("#appraisalDiscussionDueDate").val()==null){
				
				setDialog("Appraisal Discussion Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			}
		else if(appraisalDiscussionDueDate < selfAppraisalDueDate)
			
			{
				setDialog("AppraisalDiscussionDueDate cannot be less than SelfAppraisalDueDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;

				}
		else if($("#appraisalDiscussionDueDate").val() == $("#selfAppraisalDueDate").val())
			
		{
			setDialog("AppraisalDiscussionDueDate cannot be equal to SelfAppraisalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}
			
		if($("#appraisalDueDate").val().trim().length==0 || $("#appraisalDueDate").val()==null){
				
				setDialog("Appraisal Due Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
			return false;
			}
			else if(appraisalDueDate < appraisalDiscussionDueDate)
			{
				setDialog("AppraisalDueDate cannot be less than AppraisalDiscussionDueDate" , "alert");
						$( "#alert" ).dialog( "open" ); 
						return false;
							}
			else if($("#appraisalDueDate").val() == $("#appraisalDiscussionDueDate").val())
			{
				setDialog("AppraisalDueDate cannot be equal to AppraisalDiscussionDueDate" , "alert");
						$( "#alert" ).dialog( "open" ); 
						return false;
							}
			
			if($("#reviewCycleEndDate").val().trim().length==0 || $("#reviewCycleEndDate").val()==null){

				setDialog("Review Cycle End Date cannot be empty" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
				
			}

		else if(reviewCycleEndDate < appraisalDueDate)
			
			{
				setDialog("ReviewCycleEndDate cannot be less than AppraisalDueDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;

				}
		else if($("#reviewCycleEndDate").val() == $("#appraisalDueDate").val())
			
		{
			setDialog("ReviewCycleEndDate cannot be equal to AppraisalDueDate" , "alert");
			$( "#alert" ).dialog( "open" ); 
			return false;

			}

			if($("#reviewCycleStartDate").val().trim().length==0 || $("#reviewCycleStartDate").val()==null){
				
				setDialog("Review Cycle Start Date cannot be empty" , "alert");
				$("#alert").dialog( "open" ); 
				return false;
			}
			/* else if(reviewCycleStartDate < currentDate){
				setDialog("ReviewCycleStartDate cannot be less than CurrentDate" , "alert");
				$( "#alert" ).dialog( "open" ); 
				return false;
			} */
			
			var formData = $("#reviewCycleForm").serialize();
			$('#main-out').block({ message: 'Loading Please Wait....'});
			 $.ajax({
			    url: ${root}+"reviewCycle/update.html",
			    type: "GET",
			    data: formData,
			    success: function(result){
			    	$('#main-out').unblock({ fadeOut: 0 });	
			    	notification("Review Cycle is successfully updated.");
			     	$('#body').html(result);
			    },
			    error: function(xmlHttpRequest, textStatus, errorThrown) { 
			    	alert(errorThrown);
			    }
			   });
			 });
	  
});

</script>



<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<div style="margin-top: 5%;">
		<c:if test="${displayReviewCycle}">
			<center>
				<h3 style="margin-bottom: 2%;">Display Review Cycle Details</h3>
			</center>
		</c:if>
		<c:if test="${update}">
			<center>
				<h3 style="margin-bottom: 2%;">Update Review Cycle Details</h3>
			</center>
		</c:if>
		<c:if test="${add}">
			<center>
				<h3 style="margin-bottom: 2%;">Add Review Cycle Details</h3>
			</center>
		</c:if>
		<form:form id="reviewCycleForm" commandName="reviewcycle">
			<table align="center" class="box">

				<c:if test="${update}">
					<tr>
						<td><input type="hidden" id="reviewCycleId"
							name="reviewCycleId" value="${reviewcycle.reviewCycleId}" /></td>
					</tr>
					<tr>
						<td><input type="hidden" id="isActive" name="isActive"
							value="${reviewcycle.isActive}" /></td>
					</tr>
				</c:if>

				<tr>
					<td>Review Cycle Name: <span class="mandatory">*</span>
					</td>
					<c:if test="${not displayReviewCycle && !update}">
						<td><input type="text" id="reviewCycleName"
							value="${reviewcycle.reviewCycleName}" name="reviewCycleName" /></td>
					</c:if>
					<c:if test="${displayReviewCycle || update}">
						<td><input type="text" id="reviewCycleName"
							value="${reviewcycle.reviewCycleName}" name="reviewCycleName"
							readonly="readonly" /></td>
					</c:if>

				</tr>
				<tr>
					<td>Appraisal Period Type: <span class="mandatory">*</span>
					</td>
					<c:if test="${not displayReviewCycle && !update}">
						<td><select id="appraisalPeriod" style="width: 173px;"
							name="appraisalPeriodTypeId">
								<c:choose>
									<c:when test="${reviewcycle.appraisalPeriodTypeId == '2'}">
										<option value="2" selected="selected">Half - Yearly</option>
									</c:when>
									<c:otherwise>
										<option value="2">Half - Yearly</option>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${reviewcycle.appraisalPeriodTypeId == '1'}">
										<option value="1" selected="selected">Annual</option>
									</c:when>
									<c:otherwise>
										<option value="1">Annual</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</c:if>
					<c:if test="${displayReviewCycle || update}">
						<td><select id="appraisalPeriod" disabled="disabled"
							style="width: 173px;" name="appraisalPeriodTypeId">
								<c:choose>
									<c:when test="${reviewcycle.appraisalPeriodTypeId == '2'}">
										<option value="2" selected="selected">Half - Yearly</option>
									</c:when>
									<c:otherwise>
										<option value="2">Half - Yearly</option>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${reviewcycle.appraisalPeriodTypeId == '1'}">
										<option value="1" selected="selected">Annual</option>
									</c:when>
									<c:otherwise>
										<option value="1">Annual</option>
									</c:otherwise>
								</c:choose>
						</select></td>
					</c:if>
				</tr>
				<tr>
					<td>Goals Setting Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.goalsSettingDueDate}"
						pattern="dd-MM-yyyy" var="goalsSettingDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="goalsSettingDueDate"
							value="${goalsSettingDueDate}" name="goalsSettingDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="goalsSettingDueDate"
							value="${goalsSettingDueDate}" disabled="disabled"
							name="goalsSettingDueDate" /></td>
					</c:if>

				</tr>
				<tr>
					<td>Goals Approval Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.goalsApprovalDueDate}"
						pattern="dd-MM-yyyy" var="goalsApprovalDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="goalsApprovalDueDate"
							value="${goalsApprovalDueDate}" name="goalsApprovalDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="goalsApprovalDueDate"
							value="${goalsApprovalDueDate}" disabled="disabled"
							name="goalsApprovalDueDate" /></td>
					</c:if>

				</tr>
				<tr>
					<td>Goals Acceptance Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.goalsAcceptanceDueDate}"
						pattern="dd-MM-yyyy" var="goalsAcceptanceDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="goalsAcceptanceDueDate"
							value="${goalsAcceptanceDueDate}" name="goalsAcceptanceDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="goalsAcceptanceDueDate"
							value="${goalsAcceptanceDueDate}" disabled="disabled"
							name="goalsAcceptanceDueDate" /></td>
					</c:if>
				</tr>
				<tr>
					<td>Self Appraisal Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.selfAppraisalDueDate}"
						pattern="dd-MM-yyyy" var="selfAppraisalDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="selfAppraisalDueDate"
							value="${selfAppraisalDueDate}" name="selfAppraisalDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="selfAppraisalDueDate"
							value="${selfAppraisalDueDate}" disabled="disabled"
							name="selfAppraisalDueDate" /></td>
					</c:if>
				</tr>
				<tr>
					<td>Appraisal Discussion Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.appraisalDiscussionDueDate}"
						pattern="dd-MM-yyyy" var="appraisalDiscussionDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="appraisalDiscussionDueDate"
							value="${appraisalDiscussionDueDate}"
							name="appraisalDiscussionDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="appraisalDiscussionDueDate"
							value="${appraisalDiscussionDueDate}" disabled="disabled"
							name="appraisalDiscussionDueDate" /></td>
					</c:if>
				</tr>
				<tr>
					<td>Appraisal Due Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.appraisalDueDate}"
						pattern="dd-MM-yyyy" var="appraisalDueDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="appraisalDueDate"
							value="${appraisalDueDate}" name="appraisalDueDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="appraisalDueDate"
							value="${appraisalDueDate}" disabled="disabled"
							name="appraisalDueDate" /></td>
					</c:if>
				</tr>
				<tr>
					<td>Review Cycle Start Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.reviewCycleStartDate}"
						pattern="dd-MM-yyyy" var="reviewCycleStartDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="reviewCycleStartDate"
							value="${reviewCycleStartDate}" name="reviewCycleStartDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="reviewCycleStartDate"
							value="${reviewCycleStartDate}" name="reviewCycleStartDate"
							disabled="disabled" /></td>
					</c:if>
				</tr>

				<tr>
					<td>Review Cycle End Date: <span class="mandatory">*</span>
					</td>
					<fmt:formatDate value="${reviewcycle.reviewCycleEndDate}"
						pattern="dd-MM-yyyy" var="reviewCycleEndDate" />
					<c:if test="${not displayReviewCycle}">
						<td><input type="text" id="reviewCycleEndDate"
							value="${reviewCycleEndDate}" name="reviewCycleEndDate" /></td>
					</c:if>
					<c:if test="${displayReviewCycle}">
						<td><input type="text" id="reviewCycleEndDate"
							value="${reviewCycleEndDate}" name="reviewCycleEndDate"
							disabled="disabled" /></td>
					</c:if>
				</tr>
				<tr>
					<td />
					<td>
						<div id="submitrow" style="float: left; margin-right: 10px;">
							<c:if test="${add}">
								<a id="addCycle" class="tooltip"><span>Add</span></a>
							</c:if>
						</div>
						<div id="submitrow" style="float: left;">
							<c:if test="${update}">
								<a id="updateReviewCycle" class="tooltip"><span>Update</span></a>
							</c:if>
							<a id="backToReviewCycleDisplay" class="tooltip"><span>Cancel</span></a>
						</div>
					</td>
				</tr>
			</table>
		</form:form>
		<fmt:formatDate value="<%=new java.util.Date()%>" pattern="dd-MM-yyyy"
			var="now" />
		<input type="hidden" id="currentDate" value="${now}">
	</div>

</body>
</html>

