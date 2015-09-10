<%@ include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<%-- <script type="text/javascript" src="${root}common/js/jquery.js"></script>
<script type="text/javascript" src="${root}common/js/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="${root}common/js/common.js"></script>
<script type="text/javascript" src="${root}common/js/jquery.dataTables_1.9.0.js"></script>
	<script type="text/javascript" src="${root}common/js/TableTools.min.js"></script> --%>
	
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery.dataTables.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery-ui-1.10.0.custom.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/TableTools.css" />
	<link href="${root}common/css/v2/common.css" rel="stylesheet" type="text/css" />
	<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready(function(){
	jQuery.ajaxSetup({ cache:true});
	 $('#ReminderMessagesTable').dataTable( {
        "scrollY":        200,
        "scrollCollapse": true,
        "jQueryUI":       true,
        "aoColumnDefs": [
                         { "bSortable": false, "aTargets": [1,2,3,4] }
                       ]
    } ); 
	
	
	  $("#ReminderMessagesTable").css('border', '1px solid #BEBEBE'); 
	 
		 /* $("#ReminderMessagesTable").dataTable(
				{	
					"sDom": 'T<"clear">lfrtip',
					"iDisplayLength": 10,
					"bLengthChange": true,
					"aoColumnDefs": [
					                  { "bSortable": false, "aTargets": [ 0 ] }
					                ],
					"sPaginationType": "full_numbers",
					"oTableTools": {
						"sSwfPath": "${root}common/js/swf/copy_csv_xls_pdf.swf",
						 "aButtons": [
						                "csv",
						                "pdf",
						                "xls"
						            ]
					}
			}); */
	 
	 
$('#selectStage').on('change', function() {
	var stage=this.value;
	$('#ReminderMessagesTable tbody').empty();
	$('#ReminderMessagesTable').dataTable().fnClearTable();
	 $.ajax({
		  url: ${root}+"reminderMessages/getReminderMessages.html",
		  type: "POST",
		  data: {reviewCycleStage:$("#selectStage").val(),reviewPeriod:$("#reviewPeriod").val()},
		  cache:false,
		  success: function(result){
		  
		           $.each(result, function (i, item) {
		        	
		        	  $('#ReminderMessagesTable').dataTable().fnAddData( [
                                                             item.daysRemaining,
                                                             item.message,
                                                             item.subject,
                                                             '<a id="'+item.messageId+'" class="anchorupdate" onclick="updateReminderMessage('+item.messageId+')" href="#"><span>Update</span></a>',
                                                             '<a id="'+item.messageId+'" class="anchordelete" onclick="deleteReminderMessage('+item.messageId+')" href="#"><span>Delete</span></a>']
                                                           );    
		        	  
		       
		        });  
		        
		  },
		  error: function(xmlHttpRequest, textStatus, errorThrown) { 
		   setDialog(errorThrown,"alert");
		   $( "#alert" ).dialog( "open" );
		  }
		 }); 
	});
			
	         
			 $('#addReminderMessage').click(function(){
				 $.ajax({
					  url: ${root}+"reminderMessages/addReminderMessage.html",
					  cache:false,
					  data:{reviewPeriod:$("#reviewPeriod").val(),reviewCycleStage:$("#selectStage").val()},
					  success: function(result){
						  $("#body").html(result);
					        
					  },
					  error: function(xmlHttpRequest, textStatus, errorThrown) { 
					   setDialog(errorThrown,"alert");
					   $( "#alert" ).dialog( "open" );
					  }
					 }); 
			}); 
			
});

function updateReminderMessage(href){
	
	//var href = $(this).attr("id");
	$.ajax({
		  url: ${root}+"reminderMessages/update.html",
		  type: "GET",
		  data: {id:href,reviewPeriod:$("#reviewPeriod").val()},
		  cache:false,
		  success: function(result){
		   $('#body').html(result);
		  },
		  error: function(xmlHttpRequest, textStatus, errorThrown) { 
		   setDialog(errorThrown,"alert");
		   $( "#alert" ).dialog( "open" );
		  }
		 });
}

function deleteReminderMessage(href){
	//var href = $(this).attr("id");
	$.ajax({
		  url: ${root}+"reminderMessages/deleteReminderMessage.html",
		  type: "GET",
		  data: {id:href,reviewPeriod:$("#reviewPeriod").val(),reviewCycleStage:$("#selectStage").val()},
		  cache:false,
		  success: function(result){
		   $('#body').html(result);
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
<div  style="margin-top: 5%;">
<center><h3>${reviewCycleId} Reminder Messages</h3></center>
<input type="hidden" name="reviewCycleId" id="reviewPeriod" value="${reviewCycleId}" />
<%-- <input type="hidden" name="reviewCycleStage" id="reviewCycleStage" value="${reviewCycleStage}" /> --%>
<select id="selectStage" style="margin-top: 5%;margin-left: 40%">

<option value="">Select a stage</option>
<option <c:if test="${reviewCycleStage=='NOT_STARTED'}">selected="selected"</c:if> value="NOT_STARTED">Goals Setting</option>
<option <c:if test="${reviewCycleStage=='GOALS_SUBMITTED'}">selected="selected"</c:if> value="GOALS_SUBMITTED">Goals Approval</option>
<option <c:if test="${reviewCycleStage=='GOALS_FINALIZED'}">selected="selected"</c:if> value="GOALS_FINALIZED">Goals Acceptance</option>
<option <c:if test="${reviewCycleStage=='GOALS_ACCEPTED'}">selected="selected"</c:if> value="GOALS_ACCEPTED">Self Appraisal</option>
<option <c:if test="${reviewCycleStage=='APPRAISAL_SUBMITTED'}">selected="selected"</c:if> value="APPRAISAL_SUBMITTED">Appraisal Discussion</option>
<option <c:if test="${reviewCycleStage=='READY_FOR_MEETING'}">selected="selected"</c:if> value="READY_FOR_MEETING">Accept Appraisal</option>

</select>
<div id="submitrow" style="margin-bottom: 30px; height: 10px; width: 764px;">
	<a id="addReminderMessage" class="tooltip" style="margin-right: -43%; margin-bottom: 2%; margin-left: 108%;"><span>Add New Reminder Messages</span></a>
</div>
<div align="center" style="padding: 50px;">
<table id="ReminderMessagesTable" class="dataTable" aria-describedby="indexDataTable_info" style="border: 1px solid rgb(190, 190, 190);">
<thead>
<tr>
<th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1" aria-label="Appraisal" style="width: 100px;">Pending Days</th>
<th style="width: 400px;">Message</th>
<th style="width: 150px;">Subject</th>
<th style="width: 40px;">Update</th><th style="width: 40px;">Delete</th>
</tr>
</thead>
<tbody>
<c:forEach items="${reminderMessages}" var="reminderMessage">
<tr>
<td>${reminderMessage.daysRemaining}</td>
<td>${reminderMessage.message}</td>
<td>${reminderMessage.subject}</td>
<td><a id="${reminderMessage.messageId}" class="anchorupdate" onclick="updateReminderMessage(${reminderMessage.messageId})" href="#"><span>Update</span></a></td>
<td><a id="${reminderMessage.messageId}" class="anchordelete" onclick="deleteReminderMessage(${reminderMessage.messageId})" href="#"><span>Delete</span></a></td>
</c:forEach>
</tbody>

</table></div>
</center>
</div>
</body>
</html>