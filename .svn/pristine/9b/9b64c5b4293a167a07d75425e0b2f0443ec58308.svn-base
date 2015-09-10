<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript"> 
 $(document).ready(function(){
	applyDatePicker(".date",'${root}');
   	$("#requestDueDate").datepicker( "option", "minDate", +0);
 });
 
	function clearSelection(){
		 $("#feedbackOnEmployee").val('');
	}
 
	function employeeSearch(sectionId, empIdSectionId, empDetailsSection, reviewCycleId){
		$( '#'+sectionId ).autocomplete({
			  source: function( request, response ) {
				  $('#'+empIdSectionId).val('');
				     $.ajax({					    
						    url: '${root}'+'feedback/employees.html',
						    dataType: "json",
						    data: {term: request.term, reviewCycleId:reviewCycleId},
						    success: function(data){                   				    	
						     response($.map(data, function(item) {
                                return{
                                	label: item.displayName,
                                	id: item.empId,
                                	email: item.email
                                };
							}));
						}});
		            },
		      minLength: 1,
		      select: function(event, ui) {
	    	 	 $('#'+empIdSectionId).val(ui.item.id);
		      }     	     
		 });
	}
	
	employeeSearch("requestedToEmployee", "requestedToEmployeeId", "requestedToEmployeeDetails");
	employeeSearch("selfFeedbackOnEmployee", "selfFeedbackOnEmployeeId", "selffeedbackOnEmployeeDetails", $('#reviewCycleId').val());
	employeeSearch("requestedByEmployee", "requestedByEmployeeId", "requestedByEmployeeDetails");
	
	$('#feedbackOnEmployee').autocomplete({
			  source: function( request, response ) { 
				  if(${role=='manager'}){
					 $.ajax({					    
					    url: '${root}'+'feedback/subOrdinates.html',
					    dataType: "json",
	                    data: {term: request.term},
					    success: function(data){		                             				    	
						    response($.map(data, function(item) {
		                       	return {
		                        		label: item.displayName,
		                       		id: item.empId,
		                        		email: item.email
		                        
		                       	};
		                   	}));
						}
	                });
				} else {
				     $.ajax({					    
						    url: '${root}'+'feedback/employees.html',
						    dataType: "json",
		                    data: {term: request.term, reviewCycleId:$('#reviewCycleId').val()},
						    success: function(data){		                             				    	
						     response($.map(data, function(item) {
                                   return{
	                                   label: item.displayName,
	                                   id: item.empId,
	                                   email: item.email
	                                };
                                }));
	                        }});
				}
			},
		      minLength: 1,
		      select: function(event, ui) {
		    	  event.preventDefault() ;
		    	  var employeeIdsSelected=$('#feedbackOnEmployeeId').val();
		    	  var  employeesSelected= $("#feedbackOnEmployeeDetails").val();
		    	  var updatedEmployeesSelected;
		    	  var updatedEmployeeIdsSelected;	    	  
		    	  if(employeeIdsSelected==' ' ||employeeIdsSelected==''){
			    	 updatedEmployeeIdsSelected=$.trim(ui.item.id);
	    	 		updatedEmployeesSelected=$.trim(ui.item.label);
		    		} else{
			    		  updatedEmployeeIdsSelected=employeeIdsSelected;
			    	 		updatedEmployeesSelected=employeesSelected;
			    		  var list = employeeIdsSelected.split(",");
			    		  var selectedEmployeeId = $.trim(ui.item.id);
			    		  if ($.inArray(selectedEmployeeId, list) != -1) {
			    			  setDialog("Duplicate selection","alert");
								$( "#alert" ).dialog( "open" );
				            	}else{
				          			 updatedEmployeeIdsSelected=employeeIdsSelected+','+$.trim(ui.item.id);
				          			 updatedEmployeesSelected=employeesSelected+','+$.trim(ui.item.label);
				          		} 		 
			    	  }
		    		$('#feedbackOnEmployeeId').val(updatedEmployeeIdsSelected);
		    	  $('#feedbackOnEmployeeDetails').val(updatedEmployeesSelected);
		    	 
		    	  $("#feedbackOnEmployee").val('');
		    	 	    	 
		      } 		      
		 });

	function clearAll(fieldToBeCleared){
		$('#'+fieldToBeCleared).val(' ');
		$("#feedbackOnEmployeeId").val('');
	}
  function clearLastSelection(fieldToBeClearedLastSelection) {
	  var list = $("#"+fieldToBeClearedLastSelection).val().split(",");
	  var filtered = jQuery.grep(list, function(value) {
		  return value != list[list.length-1];
		});
	  var joined = filtered.join(",");
	  $('#'+fieldToBeClearedLastSelection).val(joined);
	  //clear the ids
	  var listIds = $("#feedbackOnEmployeeId").val().split(",");
	  var filteredIds = jQuery.grep(listIds, function(value) {
		  return value != listIds[listIds.length-1];
		});
	  var joinedIds = filteredIds.join(",");
	  	  $('#feedbackOnEmployeeId').val(joinedIds);
  }
  
  $("#selectAll").on("change", function () {
		var selectedValue=$("#selectAll").is(":checked");
		$("input:checkbox.question").prop("checked", selectedValue);
	});
  
  function deselectSelectAllCheckbox(currentCheckBox) {
	  if(!$(currentCheckBox).is(':checked')) {
		  $("#selectAll").prop('checked', false);
	  }
  }
</script>

<style type="text/css">
#feedbackTemplate,.feedbackQuestionnaireTable,.feedbackTemplateTable,.feedbackTemplateTable textarea,.feedbackQuestionnaireTable textarea
{
    
	width: 100%;
}



.shortColumn {
	width: 15%;
}

.bigColumn {
	width: 55%;
}
</style>



<div id="feedbackTemplate">
	<!-- 	<div class="butttonsDiv" -->
	<!-- 		style="float: right; display: block; margin-right: 10px;"> -->
	<!-- 		<a onclick="closeFeedbackSection()"><span>Close</span></a> -->
	<!-- 	</div> -->
	<form:form id="feedbackRequestForm" name="feedbackRequestForm"
		commandName="feedbackRequestForm">
		<form:hidden path="feedbackRequestId" />


		<%-- When user wants to give feedback and clicks on give feedback link in 'Feedback' tab  (SELF)--%>
		<c:choose>
			<c:when
				test="${feedbackRequestForm.action == 'request' and feedbackRequestForm.type == 'self' and location == 'out'}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="4" align="left">
							<h3>Feedback On</h3>
						</td>
					</tr>
					<tr>
						<td class="shortColumn">Search Employee</td>

						<td class="shortColumn"><input id="selfFeedbackOnEmployee"
							type="text" maxlength="100" tabIndex="1" /> <form:hidden
								id="selfFeedbackOnEmployeeId" path="feedbackOnEmployee.empId" /></td>
						<td class="shortColumn">For review cycle :</td>
						<td class="bigColumn"><c:out
								value="${feedbackRequestForm.reviewCycleName}" /> <form:hidden
								path="reviewCycleId"
								value="${feedbackRequestForm.reviewCycleId}" id="reviewCycleId" />
						<td>
					</tr>
					<tr>
						<td colspan="4" align="left">
							<h3>Send to</h3>
						</td>
					</tr>
					<tr>
						<td>Search Employee</td>
						<td><input id="requestedByEmployee" type="text"
							maxlength="100" tabIndex="2" /> <form:hidden
								id="requestedByEmployeeId" path="requestedByEmployee.empId" />
						</td>
					</tr>
					<tr>
						<td colspan="4"><h3>Feedback Comments</h3></td>
					</tr>
					<tr>
						<td colspan="4"><form:textarea rows="4"
								path="overallFeedbackComment" id="overallFeedbackComment"
								tabIndex="3" /></td>
					</tr>
				</table>
				<div
					style="display: block; text-align: center; padding-left: 300px; margin: 20px;"
					class="butttonsDiv">
					<a
						onclick="generateRequest(${root}, ${feedbackRequestForm.reviewHeaderId}, '${feedbackRequestForm.type}', '${location}')"
						class="tooltip" title="Send feedback" tabIndex="4"><span>Send
							Feedback</span></a>
				</div>
			</c:when>
			<%--User is opened the self given feedback in 'Feedback Tab'--%>
			<c:when
				test="${feedbackRequestForm.action=='show' and feedbackRequestForm.type=='self' and empty reviewFormInfoVO.reviewHeaderId}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="6" align="left">
							<h3>Feedback On</h3>
						</td>
					</tr>
					<tr>
						<td class="shortColumn">Employee Id :</td>
						<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.empId}
							<form:hidden path="feedbackOnEmployee.empId"
								id="feedbackOnEmployeeId" />
						</td>
						<td class="shortColumn">Employee Name :</td>
						<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
						<td width="shortColumn">For review Cycle:</td>
						<td width="25%">${feedbackRequestForm.reviewCycleName}</td>

					</tr>
					<tr>
						<td colspan="6" align="left">
							<h3>Sent to</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>

						<td>${feedbackRequestForm.requestedByEmployee.empId} <form:hidden
								path="requestedByEmployee.empId" />
						</td>
						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedByEmployee.displayName}</td>
					<tr>
					<tr>
						<td colspan="6"><h3>Feedback Comments</h3></td>
					</tr>
					<tr>
						<td colspan="6"><form:textarea rows="4"
								path="overallFeedbackComment" id="overallFeedbackComment" /></td>
					</tr>
				</table>
			</c:when>
			<%-- User is opened the self received feedbacks in 'Feedback' tab  --%>
			<c:when
				test="${feedbackRequestForm.action=='show' and feedbackRequestForm.type=='selfReceived'}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="6" align="left">
							<h3>Feedback On</h3>
						</td>
					</tr>
					<tr>
						<td width="15%">Employee Id :</td>
						<td width="15%">${feedbackRequestForm.feedbackOnEmployee.empId}
							<form:hidden path="feedbackOnEmployee.empId"
								id="feedbackOnEmployeeId" />
						</td>
						<td width="15%">Employee Name :</td>
						<td width="15%">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
						<td width="15%">For review Cycle:</td>
						<td width="25%">${feedbackRequestForm.reviewCycleName}</td>

					</tr>
					<tr>
						<td colspan="6" align="left">
							<h3>Given by</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>

						<td>${feedbackRequestForm.requestedToEmployee.empId}</td>
						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedToEmployee.displayName}</td>
					<tr>
					<tr>
						<td colspan="6"><h3>Overall Feedback</h3></td>
					</tr>
					<tr>
						<td colspan="6"><form:textarea rows="4"
								path="overallFeedbackComment" id="overallFeedbackComment" /></td>
					</tr>
				</table>
			</c:when>
			<%-- 			 User is opened the pending incoming feedback request in 'Feedback Requests - IN' tab  --%>
			<c:when
				test="${feedbackRequestForm.action=='pending' and (feedbackRequestForm.type=='request' or feedbackRequestForm.type=='projectchange')}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="6" align="left">
							<h3><table><tr><td>Appraise Details :</td></tr></table></h3>
						</td>
					</tr>
					<tr>
						<td class="shortColumn">Employee Id :</td>
						<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.empId}
							<form:hidden path="feedbackOnEmployee.empId"
								id="feedbackOnEmployeeId" />
						</td>
						<td class="shortColumn">Employee Name :</td>
						<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
						<td width="shortColumn">For review Cycle:</td>
						<td width="25%">${feedbackRequestForm.reviewCycleName} <form:hidden
								path="reviewCycleId"
								value="${feedbackRequestForm.reviewCycleId}" id="reviewCycleId" />
						</td>
					</tr>
					<tr>
						<td>Email Address :</td>
						<td>${feedbackRequestForm.feedbackOnEmployee.email}</td>
						<td><table><tr><td>Designation :</td></tr></table></td>
						<td>${feedbackRequestForm.feedbackOnEmployee.designation}</td>
					</tr>
					<tr>
						<td colspan="6">
							<table><tr><td><h3>Details : Appraiser</h3></td></tr></table>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedByEmployee.empId} <form:hidden
								path="requestedByEmployee.empId" />
						</td>


						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedByEmployee.displayName}</td>
					</tr>

					<tr>
						<td colspan="6">
							<table><tr><td><h3>Details : Other Participant</h3></td></tr></table>
						</td>
					</tr>

					<tr>
						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedToEmployee.empId} <form:hidden
								path="requestedToEmployee.empId" />
						</td>
						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedToEmployee.displayName}</td>
					<tr>
				</table>
				<table cellpadding="5" cellspacing="5"
					class="feedbackQuestionnaireTable">
					<c:if
						test="${not empty feedbackRequestForm.feedbackQuestionAnswerVOs}">
						<tr>
							<td width="70%">
								<table><tr><td><h2>Questionnaire :</h2></td></tr></table>
							</td>
						</tr>
					</c:if>
					<c:forEach items="${feedbackRequestForm.feedbackQuestionAnswerVOs}"
						var="feedbackQuestionAnswerVO" varStatus="status">
						<form:hidden
							path="feedbackQuestionAnswerVOs[${status.index}].feedbackQuestionId" />

						<tr>
							<td><table><tr><td>${status.count}.
								${feedbackQuestionAnswerVO.feedbackQuestion}</td></tr></table></td>
						</tr>
						<tr>
							<td><form:textarea rows="5" cols="90"  
									path="feedbackQuestionAnswerVOs[${status.index}].feedbackAnswer"
									tabindex="${status.index+1}" /></td>
						<tr>
					</c:forEach>
					<tr>
						<td><table><tr><td><h3>Overall Feedback.</h3></td></tr></table></td>
					</tr>
					<tr>
						<td><form:textarea rows="4" cols="90"
								path="overallFeedbackComment" id="overallFeedbackComment"
								tabindex="${fn:length(feedbackRequestForm.feedbackQuestionAnswerVOs)+1};" /></td>
					</tr>
				</table>
				<table>
				<tr>
				<td>
				<div
					style="display: block;  padding-left: 200px; margin: 20px;"
					class="butttonsDiv">
					<a onclick="saveFeedback(${root})"
						tabindex="${fn:length(feedbackRequestForm.feedbackQuestionAnswerVOs)+2}"><span>Save Feedback</span></a>
				</div>
				</td>
				<td>
				<div
					style="display: block; text-align: center; padding-left: 25px; margin: 20px;"
					class="butttonsDiv">
					
					<a onclick="submitFeedback(${root})"
						tabindex="${fn:length(feedbackRequestForm.feedbackQuestionAnswerVOs)+2}"><span>Submit Feedback</span></a>
				</div>
				</td>
				</tr>
				</table>

			</c:when>
			<%-- 			User is opened the submitted feedback in 'Feedback Requests - In' tab and manager opened the submitted feedback in 'Create feedback' tab --%>
			<c:when
				test="${feedbackRequestForm.action=='show' and (feedbackRequestForm.type=='request' or feedbackRequestForm.type == 'projectchange')}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="6" align="left">
							<h3>Appraise Details :</h3>
						</td>
					</tr>
					<c:if test="${empty reviewFormInfoVO.reviewHeaderId}">
						<tr>
							<td class="shortColumn">Employee Id :</td>
							<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.empId}
								<form:hidden path="feedbackOnEmployee.empId"
									id="feedbackOnEmployeeId" />
							</td>
							<td class="shortColumn">Employee Name :</td>
							<td class="shortColumn">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
							<td width="shortColumn">For review Cycle:</td>
							<td width="25%">${feedbackRequestForm.reviewCycleName} <form:hidden
									path="reviewCycleId"
									value="${feedbackRequestForm.reviewCycleId}" id="reviewCycleId" />
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty reviewFormInfoVO.reviewHeaderId}">
						<tr>
							<td width="25%">Employee Id :</td>
							<td width="25%">${feedbackRequestForm.feedbackOnEmployee.empId}
								<form:hidden path="feedbackOnEmployee.empId"
									id="feedbackOnEmployeeId" />
							</td>
							<td width="25%">Employee Name :</td>
							<td width="55%">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>

						</tr>
					</c:if>
					<tr>
						<td>Email Address :</td>
						<td>${feedbackRequestForm.feedbackOnEmployee.email}</td>
						<td>Designation :</td>
						<td>${feedbackRequestForm.feedbackOnEmployee.designation}</td>
					</tr>

					<tr>
						<td colspan="4" align="left">
							<h3>Details : Appraiser</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedByEmployee.empId} <form:hidden
								path="requestedByEmployee.empId" />
						</td>


						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedByEmployee.displayName}</td>
					</tr>

					<tr>
						<td colspan="4" align="left">
							<h3>Details : Other Participant</h3>
						</td>
					</tr>
					<tr>
						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedToEmployee.empId} <form:hidden
								path="requestedToEmployee.empId" />
						</td>
						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedToEmployee.displayName}</td>
					<tr>
				</table>
				<table cellpadding="5" cellspacing="5"
					class="feedbackQuestionnaireTable">
					<c:if
						test="${not empty feedbackRequestForm.feedbackQuestionAnswerVOs}">
						<tr>
							<td width="70%">
								<h2>Questionnaire :</h2>
							</td>
						</tr>
					</c:if>

					<c:forEach items="${feedbackRequestForm.feedbackQuestionAnswerVOs}"
						var="feedbackQuestionAnswerVO" varStatus="status">
						<form:hidden
							path="feedbackQuestionAnswerVOs[${status.index}].feedbackQuestionId" />

						<tr >
							<td style="text-align: left;">${status.count}.
								${feedbackQuestionAnswerVO.feedbackQuestion}</td>
						</tr>
						<tr>
							<td><form:textarea rows="5" cols="90"
									path="feedbackQuestionAnswerVOs[${status.index}].feedbackAnswer" />
							</td>
						<tr>
					</c:forEach>
					<tr>
						<td><h3>Overall Feedback11</h3></td>
					</tr>
					<tr>
						<td><form:textarea rows="4" cols="90"
								path="overallFeedbackComment" id="overallFeedbackComment" /></td>
					</tr>
				</table>
			</c:when>
			<%-- 			 User is creating new request in 'Create feedback request tab' and from create new feedback link  --%>
			<c:when
				test="${feedbackRequestForm.action=='request' and feedbackRequestForm.type=='request' and location=='out'}">
				<table cellpadding="5" cellspacing="5">
					<tr>
						<td colspan="5" align="left">
							<h3>Appraise Details :</h3>
						</td>
					</tr>

					<tr>
						<td width="10%">Search Employee:</td>

						<td width="15%"><input id="feedbackOnEmployee" type="text"
							onblur="clearSelection();" maxlength="100" tabindex="1" /> <form:hidden
								id="feedbackOnEmployeeId" path="feedbackOnEmployeeIds" /></td>
						<td width="20%">For review cycle :</td>
						<td width="55%" colspan="2">
							<c:out
								value="${feedbackRequestForm.reviewCycleName}" /> <form:hidden
								path="reviewCycleId"
								value="${feedbackRequestForm.reviewCycleId}" id="reviewCycleId" />
						</td>
					</tr>

					<tr>
						<td colspan="5"><h3>Selected Employee Details :</h3></td>
					</tr>

					<tr>
						<td colspan="3"><textarea rows="3" cols="70"
								id="feedbackOnEmployeeDetails" disabled="disabled"> </textarea>
						<td class="butttonsDiv" width="20%"><a
							onclick="clearAll('feedbackOnEmployeeDetails')"><span>Clear
									All</span></a></td>
						<td class="butttonsDiv" width="45%"><a
							onclick="clearLastSelection('feedbackOnEmployeeDetails')"><span>Clear
									the last value</span></a></td>

					</tr>


					<tr>
						<td colspan="5" align="left">
							<h3>Details : Appraiser</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedByEmployee.empId} <form:hidden
								path="requestedByEmployee.empId" />
						</td>


						<td>Employee Name :</td>
						<td colspan="2">${feedbackRequestForm.requestedByEmployee.displayName}</td>
					</tr>

					<tr>
						<td colspan="5" align="left">
							<h3>Details : Other Participant</h3>
						</td>
					</tr>

					<tr>
						<td>Search Employee:</td>

						<td><input id="requestedToEmployee" type="text"
							maxlength="100" tabindex="2" /> <form:hidden
								id="requestedToEmployeeId" path="requestedToEmployee.empId" /></td>

						<td>Due Date :</td>
						<td colspan="2"><form:input path="requestDueDate"
								class="date" readonly="true" id="requestDueDate" tabindex="3" />
						</td>
					</tr>
				</table>
				<table cellpadding="5" cellspacing="5"
					class="feedbackQuestionnaireTable">
					<tr>
						<td width="70%">
							<h2>Questionnaire :</h2>
						</td>
						<td><input type="checkbox" id="selectAll" tabindex="4"></td>
					</tr>

					<c:forEach items="${feedbackRequestForm.feedbackQuestionAnswerVOs}"
						var="feedbackQuestionAnswerVO" varStatus="status">
						<form:hidden
							path="feedbackQuestionAnswerVOs[${status.index}].feedbackQuestionId" />

						<tr>
							<td>${status.count}.
								${feedbackQuestionAnswerVO.feedbackQuestion}</td>
							<td><form:checkbox onclick="deselectSelectAllCheckbox(this)"
									path="selectedFeedbackQuestionnaire" class="question"
									value="${feedbackQuestionAnswerVO.feedbackQuestionId}"
									tabindex="${status.index+5}" /></td>
						</tr>

					</c:forEach>

					<tr>
						<td colspan="5">Overall Feedback (Default)</td>
					</tr>
				</table>
				<div
					style="display: block; text-align: center; padding-left: 300px; margin: 20px;"
					class="butttonsDiv">
					<a
						onclick="generateRequest('${root}', '${feedbackRequestForm.reviewHeaderId}', '${feedbackRequestForm.type}', '${location}')"
						class="tooltip" title="Send request"
						tabindex="${fn:length(feedbackRequestForm.feedbackQuestionAnswerVOs)+5}"><span>Send
							Request</span></a>
				</div>
			</c:when>

			<%--                        User creating new feedback inside the appraisal  --%>
			<c:when
				test="${feedbackRequestForm.action=='request' and feedbackRequestForm.type=='request' and location=='in'}">
				<form:hidden path="reviewHeaderId" />
				<table cellpadding="5" cellspacing="5">
					<tr>
						<td colspan="4" align="left">
							<h3>Appraise Details :</h3>
						</td>
					</tr>
					<tr>
						<td width="15%">Employee Id :</td>
						<td width="15%">${feedbackRequestForm.feedbackOnEmployee.empId}
							<form:hidden path="feedbackOnEmployee.empId"
								id="feedbackOnEmployeeId" />
						</td>
						<td width="15%">Employee Name :</td>
						<td width="55%">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
					</tr>
					<tr>
						<td>Email Address :</td>
						<td>${feedbackRequestForm.feedbackOnEmployee.email}</td>
						<td>Designation :</td>
						<td>${feedbackRequestForm.feedbackOnEmployee.designation}</td>
					</tr>


					<tr>
						<td colspan="4" align="left">
							<h3>Details : Appraiser</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>
						<td>${feedbackRequestForm.requestedByEmployee.empId} <form:hidden
								path="requestedByEmployee.empId" />
						</td>


						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedByEmployee.displayName}</td>
					</tr>

					<tr>
						<td colspan="4" align="left">
							<h3>Details : Other Participant</h3>
						</td>
					</tr>

					<tr>
						<td>Search Employee</td>

						<td><input id="requestedToEmployee" type="text"
							maxlength="100" /> <form:hidden id="requestedToEmployeeId"
								path="requestedToEmployee.empId" /></td>

						<td>Due Date :</td>
						<td><form:input path="requestDueDate" class="date"
								readonly="true" id="requestDueDate" />
						<td>
					</tr>
				</table>
				<br />
				<table cellpadding="5" cellspacing="5"
					class="feedbackQuestionnaireTable">
					<tr>
						<td width="70%">
							<h2>Questionnaire :</h2>
						</td>
						<td><input type="checkbox" id="selectAll"></td>
					</tr>

					<c:forEach items="${feedbackRequestForm.feedbackQuestionAnswerVOs}"
						var="feedbackQuestionAnswerVO" varStatus="status">
						<form:hidden
							path="feedbackQuestionAnswerVOs[${status.index}].feedbackQuestionId" />

						<tr>
							<td>${status.count}.
								${feedbackQuestionAnswerVO.feedbackQuestion}</td>
							<td><form:checkbox path="selectedFeedbackQuestionnaire"
									class="question" onclick="deselectSelectAllCheckbox(this)"
									value="${feedbackQuestionAnswerVO.feedbackQuestionId}" /></td>
						</tr>

					</c:forEach>

					<tr>
						<td>Overall Feedback (Default)</td>
					</tr>
				</table>
				<div
					style="display: block; padding-left: 300px; margin: 20px;"
					class="butttonsDiv">
					<a
						onclick="generateRequest('${root}', '${feedbackRequestForm.reviewHeaderId}', '${feedbackRequestForm.type}', '${location}')"
						class="tooltip" title="Send request"><span>Send Request</span></a>
				</div>

			</c:when>
			<%--User is opened the self given feedback inside the appraisal--%>
			<c:when
				test="${feedbackRequestForm.action=='show' and feedbackRequestForm.type=='self' and not empty reviewFormInfoVO.reviewHeaderId}">
				<table cellpadding="5" cellspacing="5" class="feedbackTemplateTable">
					<tr>
						<td colspan="4" align="left">
							<h3>Feedback On</h3>
						</td>
					</tr>
					<tr>
						<td width="20%">Employee Id :</td>
						<td width="20%">${feedbackRequestForm.feedbackOnEmployee.empId}
							<form:hidden path="feedbackOnEmployee.empId"
								id="feedbackOnEmployeeId" />
						</td>
						<td width="20%">Employee Name :</td>
						<td width="40%">${feedbackRequestForm.feedbackOnEmployee.displayName}</td>
					<tr>
					<tr>
						<td colspan="4" align="left">
							<h3>Given by</h3>
						</td>
					</tr>
					<tr>

						<td>Employee Id :</td>

						<td>${feedbackRequestForm.requestedToEmployee.empId}</td>
						<td>Employee Name :</td>
						<td>${feedbackRequestForm.requestedToEmployee.displayName}</td>
					<tr>
					<tr>
						<td colspan="4"><h3>Overall Feedback</h3></td>
					</tr>
					<tr>
						<td colspan="4"><form:textarea rows="4" cols="90"
								path="overallFeedbackComment" id="overallFeedbackComment" /></td>
					</tr>
				</table>
			</c:when>
		</c:choose>
	</form:form>
</div>
