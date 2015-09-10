<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<head>
<c:url var="root" value="/"></c:url>
<%-- <link rel="stylesheet" type="text/css"
	href="${root}common/css/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="${root}common/css/jquery-ui-1.10.0.custom.css">
<link rel="stylesheet" type="text/css"
	href="${root}common/css/TableTools.css"> --%>
<link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet"
	type="text/css" />
<%-- <script type="text/javascript"
	src="${root}common/js/jquery.dataTables_1.9.0.js"></script> --%>
<script type="text/javascript" src="${root}common/js/v2/reviewSummary.js"></script>
<script type="text/Javascript">
$(document).ready(function(){
	jQuery.ajaxSetup({ cache:true});
	 $("#reviewCycleTable").dataTable({
		  "bPaginate": false,
		  "aoColumns": [{"sName": "ReviewCycleName" }, {"sName": "ReviewCycleStartDate"},{"sName": "ReviewCycleEndDate"},
		                {"sName": "Action" }/* ,{"sName": "Action"} */
		              ],
		              "aoColumnDefs": [
		                               { "bSortable": false, "aTargets": [3] }
		                             ]
		 });
		 
	
	$("#addNewReviewCycle").on('click', function() {
			   $.ajax({
			    url: ${root}+"reviewCycle/add.html",
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
		 
	 $(".update").click(function(){
			var href = $(this).attr("id");
			$.ajax({
				  url: ${root}+"reviewCycle/edit.html",
				  type: "GET",
				  data: {id:href},
				  cache:false,
				  success: function(result){
				   $('#body').html(result);
				  },
				  error: function(xmlHttpRequest, textStatus, errorThrown) { 
				   alert(errorThrown);
				  }
				 });
			
		}); 
		
	 $(".delete").click(function(){
		 var href = $(this).attr("id");
		 $( "#deleteReviewCycleConfirmation").dialog({
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
							deleteReviewCycle(href);
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
		}); 
	 
	/*  $(".startReviewCycleClass").click(function() {
			startReviewCycleFunction($(this).attr("id"), $(this).attr("title"));
	 }); */
	 
	 $(".display").click(function(){
		   var href = $(this).attr("id");
		   $.ajax({
		      url: ${root}+"reviewCycle/displayReviewCycleDetails.html",
		      type: "GET",
		      data: {id:href},
		      cache:false,
		      success: function(result){
		       $('#body').html(result);
		      },
		      error: function(xmlHttpRequest, textStatus, errorThrown) { 
		       setDialog(errorThrown,"alert");
		       $( "#alert" ).dialog( "open" );
		      }
		     });
		   
		  });
	 
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
		
	});
	
	function startReviewCycleFunction(reviewCycleId, appraisalPeriodTypeId){
		$("#startReviewCycleConfirmation").dialog({
		width: 300,
		draggable: true,
		resizable: true,
		maxHeight: 400,
		modal:true,
		buttons: [
			{
				text: "Start",
				click: function() {
					$( this ).dialog( "close" );
					startReviewCycle(reviewCycleId, appraisalPeriodTypeId);
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
	
	function startReviewCycle(reviewCycleId, appraisalPeriodTypeId) {
		$.ajax({
			  url: ${root}+"reviewCycle/startReviewCycle.html",
			  type: "GET",
			  cache: false,
			  data: {reviewCycleId:reviewCycleId, appraisalPeriodTypeId:appraisalPeriodTypeId},
			  success: function(result){
			    	notification("Review Cycle is successfully started.");
			     	$('#body').html(result);
			    },
			      error: function(xmlHttpRequest, textStatus, errorThrown) { 
			       setDialog(errorThrown,"alert");
			       $( "#alert" ).dialog( "open" );
			      }
		});
	}
	
	function deleteReviewCycle(id){
		$.ajax({
			  url: ${root}+"reviewCycle/delete.html",
			  type: "GET",
			  data: {id:id},
			  cache:false,
			  success: function(result){
				  $('#reviewCycleRow'+id).remove();
				  notification("Review Cycle is successfully deleted.");
				  $('#body').html(result); 
			  },
			  error: function(xmlHttpRequest, textStatus, errorThrown) { 
			   setDialog(errorThrown,"alert");
			   $( "#alert" ).dialog( "open" );
			  }
			 });
	}
	
/* function getdeleted(id){
	if($("#isDeleted").val()==""){
		  	setDialog("Employees are already assigned to this review cycle and hence it cant be deleted" , "alert");
			$( "#alert" ).dialog( "open" );
	   		$('#body').html(result);
	  }
	  else{
		  $('#reviewCycleRow'+id).remove();
		  notification("Review Cycle is successfully deleted.");
		  $('#body').html(result);
	  }
} */

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Review Cycle Details</title>
</head>

	<div style="margin-top: 5%;">
		<center>
			<h3>Review Cycle Details</h3>
		</center>

		<%--  <center><span id="message" style="margin-top: 2%;">${isDeleted}</span></center> --%>
		<input type="hidden" id="isDeleted" value="${isDeleted}">


		<div id="submitrow"
			style="margin-bottom: 30px; height: 10px; width: 764px;">
			<a id="addNewReviewCycle" class="tooltip"
				style="margin-right: -43%; margin-bottom: 2%; margin-left: 120%;"><span>Add
					Review Cycle</span></a>
		</div>

		<div align="center" style="padding: 50px;">
		<table id="reviewCycleTable" align="center">

			<thead>
				<th>ReviewCycleName</th>
				<th>ReviewCycleStartDate</th>
				<th>ReviewCycleEndDate</th>
				<th>Action</th>
				<!-- <th>StartReviewCycle</th> -->
			</thead>

			<c:forEach items="${reviewcycleList}" var="reviewcycle">
				<tr id="reviewCycleRow${reviewcycle.reviewCycleId}">
					<td><a id="${reviewcycle.reviewCycleId}" class="display"
						href="#">${reviewcycle.reviewCycleName}</a></td>
					<fmt:formatDate value="${reviewcycle.reviewCycleStartDate}"
						pattern="yyyy-MM-dd" var="reviewCycleStartDate" />
					<td><c:out value="${reviewCycleStartDate}" /></td>
					<fmt:formatDate value="${reviewcycle.reviewCycleEndDate}"
						pattern="yyyy-MM-dd" var="reviewCycleEndDate" />
					<td><c:out value="${reviewCycleEndDate}" /></td>
					<td><div id="submitrow"><c:if
							test="${reviewcycle.reviewCycleStatus != 'Completed'}">
							<a id="${reviewcycle.reviewCycleId}" class="update" href="#"><span>Update</span></a>
						</c:if> <c:if test="${reviewcycle.reviewCycleStatus == 'Created'}">
							<a id="${reviewcycle.reviewCycleId}" class="delete" href="#"><span>
								Delete</span></a>
						</c:if>
						<c:if test="${reviewcycle.reviewCycleStatus == 'Created'}">
								<a id="${reviewcycle.reviewCycleId}"
									title="${reviewcycle.appraisalPeriodType.appraisalPeriodTypeId}"
									class="tooltip startReviewCycleClass" href="#" onclick="startReviewCycleFunction($(this).attr('id'), $(this).attr('title'))"><span>
									Start </span></a>
							</c:if></div></td>
					<%-- <td><div id="submitrow">
							<c:if test="${reviewcycle.reviewCycleStatus == 'Created'}">
								<a id="${reviewcycle.reviewCycleId}"
									title="${reviewcycle.appraisalPeriodType.appraisalPeriodTypeId}"
									class="tooltip startReviewCycleClass"><span>Start
										Review Cycle</span></a>
							</c:if>
						</div></td> --%>
				</tr>
			</c:forEach>
		</table></div>
	</div>

	<div id="startReviewCycleConfirmation" title="Confirmation"
		style="display: none;">
		<p>
			Are you sure, you want to start this Review Cycle?<br>
			<br>
			<b>Note: </b>Starting this Review Cycle will lead to disabling the
			current active Review Cycle.
		</p>
	</div>
	<div id="deleteReviewCycleConfirmation" title="Confirmation"
		style="display: none;">
		<p>Are you sure, you want to delete this Review Cycle?</p>
	</div>


