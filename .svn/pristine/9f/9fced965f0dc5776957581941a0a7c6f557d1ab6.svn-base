<%@ include file="../common/taglibs.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:url var="root" value="/"></c:url>
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/jquery.dataTables.css">
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/jquery-ui-1.10.0.custom.css">
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/TableTools.css">
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/jquery.dataTables.css" />

<link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${root}common/js/v2/jquery.dataTables_1.9.0.js"></script>
<script type="text/javascript" src="${root}common/js/v2/reviewSummary.js"></script>
<script type="text/javascript" src="${root}common/js/v2/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	jQuery.ajaxSetup({ cache:true});
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
	
	$("#add").click(function(){
		
		if($("#daysRemaining").val().trim().length==0 || $("#daysRemaining").val()==null){
			setDialog("Days Remaining should not be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#selectStage").val()==""){
			setDialog("Stage cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#reminderMessage").val().trim().length==0 || $("#reminderMessage").val()==""){
			setDialog("Reminder Message cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#subject").val().trim().length==0 || $("#subject").val()==""){
			setDialog("Subject cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		
		 $.ajax({
			  url: ${root}+"reminderMessages/saveReminderMessage.html",
			  cache:false,
			  data:{reminderMessage:$("#reminderMessage").val(),daysRemaining:$("#daysRemaining").val(),reviewPeriod:$("#reviewPeriod").val(),status:$("#selectStage").val(),subject:$("#subject").val() },
			  success: function(result){
				  if (result != 'error') {
				  	$("#body").html(result);
				  }
				  else {
					  setDialog("Cannot add multiple Remainder Messages for same Days Remaining and same Review Cycle Stage","alert");
					   $( "#alert" ).dialog( "open" );
				  }
			  },
			  error: function(xmlHttpRequest, textStatus, errorThrown) { 
			   setDialog(errorThrown,"alert");
			   $( "#alert" ).dialog( "open" );
			  }
			 }); 
		
	});
	
	$("#update").on('click', function() {
		if($("#daysRemaining").val().trim().length==0 || $("#daysRemaining").val()==null){
			setDialog("Days Remaining should not be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#selectStage").val()==""){
			setDialog("Stage cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#reminderMessage").val().trim().length==0 || $("#reminderMessage").val()==""){
			setDialog("Reminder Message cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		if($("#subject").val().trim().length==0 || $("#subject").val()==""){
			setDialog("Subject cannot be empty" , "alert");
			$( "#alert" ).dialog( "open" ); 

			return false;
		}
		
		$.ajax({
			  url: ${root}+"reminderMessages/updateReminderMessage.html",
			  type: "POST",
			  data: {messageId:$("#messageId").val(), daysRemaining:$("#daysRemaining").val(), reminderMessage:$("#reminderMessage").val(), subject:$("#subject").val(),reviewPeriod:$("#reviewPeriod").val(),reviewCycleStage:$("#selectStage").val()},
			  cache:false,
			  success: function(result){
				  if (result != 'error') {
				  	$("#body").html(result);
				  }
				  else {
					  setDialog("Cannot update multiple Remainder Messages for same Days Remaining and same Review Cycle Stage","alert");
					   $( "#alert" ).dialog( "open" );
				  }
			  },
			  error: function(xmlHttpRequest, textStatus, errorThrown) { 
			   setDialog(errorThrown,"alert");
			   $( "#alert" ).dialog( "open" );
			  }
			 });
		});
	
	 $("#backToReviewCycleDisplay").on('click', function() {
			$.ajax({
			    url: ${root}+"reminderMessages/backToReviewCycleDisplay.html",
			    type: "GET",
			    data: {reviewCycleStage:$("#reviewCycleStage").val(),reviewPeriod:$("#reviewPeriod").val()},
				cache:false,
			    success: function(result){
			     $('#body').html(result);
			    },
			    error: function(xmlHttpRequest, textStatus, errorThrown) { 
			    alert(errorThrown);
			    }
			   });
		});
	 
});

function IsNumeric(key){
	//getting key code of pressed key
    var keycode = (key.which) ? key.which : key.keyCode;
    //comparing pressed keycodes

    if (keycode > 31 && (keycode < 48 || keycode > 57)) {
    	setDialog("You can enter only digits" , "alert");
		$( "#alert" ).dialog( "open" );
        return false;
    }
    else return true;


}

</script>
<style>
.mandatory {
	color: red;
}
</style>


</head>

<body>
	<center>
		<input type="hidden" name="reviewCycleId" id="reviewPeriod"
			value="${reviewCycleId}" />
		<c:if test="${addNew}">
			<input type="hidden" name="reviewCycleStage" id="reviewCycleStage"
				value="${reviewCycleStage}" />
		</c:if>
		<c:if test="${not addNew}">
			<input type="hidden" name="reviewCycleStage" id="reviewCycleStage"
				value="${reminderMessage.status}" />
		</c:if>
		<c:if test="${addNew}">
			<br>
			<br>
			<h3>Add new reminder message for ${reviewPeriod} review period</h3>
		</c:if>

		<c:if test="${not addNew}">
			<br>
			<br>
			<h3>Update reminder message for ${reminderMessage.reviewPeriod}
				review period</h3>
		</c:if>

		<table style="margin-top: 50px;">
			<tr>
				<td><input type="hidden" id="messageId"
					value="${reminderMessage.messageId}"></td>
			</tr>
			<tr>
				<td>Days Remaining: <span class="mandatory">*</span>
				</td>
				<c:if test="${addNew}">
				<td><input type="text" onkeypress="return IsNumeric(event);"
					id="daysRemaining" value="${reminderMessage.daysRemaining}"></td>
				</c:if>
				<c:if test="${not addNew}">
				<td><input type="text" onkeypress="return IsNumeric(event);"
					id="daysRemaining" value="${reminderMessage.daysRemaining}" disabled="${not addNew}"></td>
				</c:if>
			</tr>
			<tr>
				<td>Stage: <span class="mandatory">*</span>
				</td>
				<c:if test="${addNew}">
					<td><select id="selectStage">
							<option value="">Select a stage</option>
							<option value="NOT_STARTED">Goals Setting</option>
							<option value="GOALS_SUBMITTED">Goals Approval</option>
							<option value="GOALS_FINALIZED">Goals Acceptance</option>
							<option value="GOALS_ACCEPTED">Self Appraisal</option>
							<option value="APPRAISAL_SUBMITTED">Appraisal Discussion</option>
							<option value="READY_FOR_MEETING">Accept Appraisal</option>
					</select></td>
				</c:if>
				<c:if test="${not addNew}">
					<td><input type="text" id="selectStage"
						value="${reminderMessage.status}" disabled="${not addNew}"></td>
				</c:if>
			</tr>
			<tr>
				<td>Reminder Message: <span class="mandatory">*</span>
				</td>
				<td><textarea id="reminderMessage">${reminderMessage.message}</textarea></td>
			</tr>
			<tr>
				<td>Review Period:</td>
				<c:if test="${addNew}">
					<td><input type="text" id="reviewPeriod"
						value="${reviewPeriod}" disabled="${addNew}"></td>
				</c:if>
				<c:if test="${not addNew}">
					<td><input type="text" id="reviewPeriod"
						value="${reminderMessage.reviewPeriod}" disabled="${not addNew}"></td>
				</c:if>
			</tr>
			<tr>
				<td>Subject: <span class="mandatory">*</span></td>
				<td><textarea id="subject">${reminderMessage.subject}</textarea></td>
			</tr>
			<tr>
				<td></td>
						<td>
						<div id="submitrow"  style="float: left; margin-right: 10px;">
						<c:if test="${addNew}">
							<a class="tooltip" id="add"><span>Add</span></a>
							</c:if>
						</div>
					<div id="submitrow"  style="float: left;">
					<c:if test="${not addNew}">
						<a class="tooltip" id="update"><span>Update</span></a>	
				</c:if>
					<a class="tooltip" id="backToReviewCycleDisplay"><span>Cancel</span></a>
				</div>
				</td>
			</tr>
		</table>
	</center>
</body>
