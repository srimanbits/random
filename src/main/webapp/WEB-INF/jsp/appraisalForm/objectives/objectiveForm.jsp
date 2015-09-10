
<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>



<link href="${root}common/css/v2/objectiveForm.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/jquery.multiselect.css" rel="stylesheet"
	type="text/css" />
 <script type="text/javascript" src="${root}common/js/v2/objectiveForm.js"></script>
<script type="text/javascript"
	src="${root}common/js/v2/jquery.multiselect.min.js"></script>
<script type="text/javascript">
var objectiveNumber = ${fn:length(objectiveForm.objectives)}-1;
var noOfObjOnPage = 0;
var ObjDeleteImgIdSuffixNo;
$(document).ready(function(){
	
	 //jQuery.ajaxSetup({ cache:true});
	 
	
	redirectToIndex('${reviewFormInfoVO.actualReviewStatus}','${reviewFormInfoVO.reviewFormRole}','${reviewFormInfoVO.reviewPhase.description}');
	 $("#reviewStatusId").html("Status : "+'${reviewFormInfoVO.reviewStatus}');
	autoSize(".objective textarea");
	applyDatePickerForObjectives(".date:input:enabled",'${root}','${reviewFormInfoVO.reviewCycleStartDate}','${reviewFormInfoVO.reviewCycleEndDate}');
	applyBanners();
	
	$(".projects").multiselect({
		   position: {
			   my: 'center',
			      at: 'center'
			   },
			   header: "Choose Project(s) Below",
			   noneSelectedText:"Select Projects",
	selectedText: function(numChecked, numTotal, checkedItems){
		
		var selectedProjects="";
	
		for(var i=0;i<checkedItems.length;i++){
			if(i<2){
				selectedProjects=selectedProjects+checkedItems[i].nextSibling.innerHTML+' ,';
			}
			else{
				selectedProjects=selectedProjects.substring(0,selectedProjects.length-1);
				selectedProjects=selectedProjects+' ...';
				break;
			}
		}
		if(checkedItems.length<=2){
			selectedProjects=selectedProjects.substring(0,selectedProjects.length-1);				
		}
		return selectedProjects;
	    
	   }
			});
	manageObjectiveOptions();
	
	//$("h2:first").addClass("active").next("div").slideToggle("slow");
	$("form :input, select").on('change', function(){
			$('#isEdited').val('yes');
	});
	
	$("#addObjective").click(function() {
		$('#main-out').block({ message: 'Adding New Objective..'});
		objectiveNumber++;
		
		$.ajax({
			  url: ${root}+"appraisal/addNewReviewObjective.html",
			  type: "POST",
			  cache: false,
			  data: {objectiveNumber : objectiveNumber,defaultObjId:-1,reviewHeaderId:'${reviewFormInfoVO.reviewHeaderId }'},//defaultObjId:$('#defaultObjDropDown option:selected').val()
			  success: function(data){  				  
					$("#objectives_container").append(data);
					autoSize(".objective textarea");
					applyDatePickerForObjectives(".editableDate",'${root}','${reviewFormInfoVO.reviewCycleStartDate}','${reviewFormInfoVO.reviewCycleEndDate}');
					$('#isEdited').val('yes');
					//notification('Objective is successfully added.');
					manageObjectiveOptions();
					 $('#main-out').unblock({ fadeOut: 0 });
	            },
	            error: function(xmlHttpRequest, textStatus, errorThrown) { 
	          	  objectiveNumber--;
	          	 $('#main-out').unblock({ fadeOut: 0 });
	          	  setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
	            }
			});
	}); 
	
	$("#saveObjButton").click(function(){
		if(saveOrNoSave('reviewObjectives')){
			saveObjectiveForm(${root});
		}
		
		
	});
	
	$("#objectiveFormButtons .expandAll").click(function() {
		$("#objectives_container h2").each(function() {
			if (!$(this).hasClass("active")){
			$(this).next("div").slideDown("slow");
			$(this).toggleClass("active");
			}

		});

	});

	$("#objectiveFormButtons .collapseAll").click(function() {
		$("#objectives_container h2").each(function() {
			//$(this).css("width",730);
			if ($(this).hasClass("active")){
			$(this).next("div").slideUp("slow");
			$(this).removeClass("active");
			}

		});

	});
	
	/* $( "input[type=checkbox]" ).click(function(){
		
		var id=$(this).attr("id");
		var chk_id=id.substr(3);
		if($(this).attr("checked")=='checked'){
		$("#h2Banner"+chk_id).css("color","green");				
		}
		
		else{
			$("#h2Banner"+chk_id).css("color","black");
		}
	}); */
	
		$('.objectiveName').keyup(function() {
			  changeBanner($(this).attr("id"));
			});
	
	});

</script>
<c:set var="enableGoals" value="false" />
<c:set var="renderAppraiseFields" value="false" />
<c:set var="enableAppraiseFields" value="false" />
<c:set var="renderAppraiserFields" value="false" />
<c:set var="enableAppraiserFields" value="false" />
<c:set var="renderAddObjButton" value="false" />
<c:set var="renderSaveObjsButton" value="false" />
<c:set var="renderEvaluatingPerformance" value="false" />
<c:set var="showReviewObjective" value="true" />
<c:set var="canshow" value="true" />
<c:set var="enableProjects" value="false" />
<c:if
	test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISE'}">
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1'}">
		<c:set var="renderAddObjButton" value="true" />
		<c:set var="renderSaveObjsButton" value="true" />
		<c:if test="${reviewFormInfoVO.actualReviewStatus!='GOALS_FINALIZED'}">
		<c:set var="enableProjects" value="true" />
		</c:if>
	</c:if>

	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'}">
		<%-- <c:set var="enableGoals" value="true" /> --%>
	</c:if>

	<c:if
		test="${(reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2' && reviewFormInfoVO.actualReviewStatus!='READY_FOR_MEETING') || reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE1_COMPLETED' }">
		<c:set var="renderAddObjButton" value="true" />
		<c:set var="renderAppraiseFields" value="true" />
		<c:set var="enableAppraiseFields" value="true" />
		<c:set var="renderSaveObjsButton" value="true" />
		<c:set var="renderEvaluatingPerformance" value="true" />
		<c:set var="enableProjects" value="true" />
		<%-- <c:set var="enableGoals" value="true" />
 --%>
	</c:if>

	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
		<c:set var="renderAppraiseFields" value="true" />
		<c:set var="renderEvaluatingPerformance" value="true" />
		<%-- <c:set var="renderAppraiserFields" value="true" /> --%>
		<%-- <c:set var="enableGoals" value="true" /> --%>

	</c:if>
</c:if>
<c:if
	test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER' or reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER'}">
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'}">
		<c:set var="renderSaveObjsButton" value="true" />
		<c:set var="renderAddObjButton" value="true" />
		<c:set var="enableProjects" value="true" />
	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
		<c:set var="renderSaveObjsButton" value="true" />
		<%-- <c:set var="enableGoals" value="true" /> --%>
		<c:set var="renderAppraiseFields" value="true" />
		<c:set var="renderAppraiserFields" value="true" />
		<c:set var="enableAppraiserFields" value="true" />
		<c:set var="renderEvaluatingPerformance" value="true" />
		<c:set var="enableProjects" value="false" />
	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2' && (reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL' || reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING')}">
		<c:set var="renderAppraiseFields" value="true" />
		<c:set var="renderAppraiserFields" value="true" />
		<c:set var="renderEvaluatingPerformance" value="true" />
		<c:set var="enableAppraiserFields" value="false" />
	</c:if>
</c:if>
<%-- <c:if
	test="${ reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER'}">
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
		<c:set var="renderSaveObjsButton" value="true" />
		<c:set var="enableGoals" value="true" />
		<c:set var="renderAppraiseFields" value="true" />
		<c:set var="renderAppraiserFields" value="true" />
		<c:set var="enableAppraiserFields" value="true" />
		<c:set var="renderEvaluatingPerformance" value="true" />
	</c:if>
</c:if> --%>
<c:if
	test="${reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE2_COMPLETED' || reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'}">
	<c:set var="renderEvaluatingPerformance" value="true" />
	<c:set var="renderAppraiseFields" value="true" />
	<c:set var="renderAppraiserFields" value="true" />
</c:if>
<form:form method="post" cssClass="ObjectiveForm"
	action="javascript:void(0);" commandName="objectiveForm">
	<input type="hidden" value="${reviewFormInfoVO.reviewHeaderId}"
		name="reviewHeaderId"></input>
	<input type="hidden" id="isEdited" name="isEdited" value="no" />
	<%-- 	<c:if
		test="${(reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or  reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or  reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER') and renderSaveObjsButton and fn:length(objectiveForm.objectives)<1}">
		<c:set var="renderSaveObjsButton" value="false" />
	</c:if> --%>
	<div id="objectiveFormButtons"
		style="height: 20px; width: 764px; margin-bottom: 20px;">
		<c:if test="${renderAddObjButton || renderSaveObjsButton }">
			<c:if test="${renderAddObjButton }">
				<!-- 	<select id="defaultObjDropDown">
						<option value="-1"><- Select -></option>  	
						<c:forEach items="${objectiveForm.defaultObjDropDown}"
							varStatus="status" var="defaultObjective">
							<option value="${defaultObjective.id}">${defaultObjective.value}</option>
						</c:forEach>
					</select>  //default objectives will be on nxt phase-->
				<div style="float: left; margin-right: 10px;">
					<a id="addObjective" class="tooltip" title="Add new objective"><span>Add
							New</span></a>
				</div>
			</c:if>
			<c:if test="${renderSaveObjsButton }">
				<div style="float: left;" id="saveObjButtonDiv">
					<a id="saveObjButton" class="tooltip" title="Save your objectives"><span>Save
							All </span></a>
				</div>
			</c:if>
		</c:if>
		<div style="float: right; margin-left: 10px;">
			<img alt="Collapse All" src="${root}common/css/images/uparrow_1.png"
				class="collapseAll" title="Collapse All">
		</div>
		<div style="float: right; margin-left: 10px;">
			<img alt="esss" src="${root}common/css/images/downarrow_1.png"
				class="expandAll" title="Expand All">
		</div>
	</div>
	<div id="objectives_container">
		<c:forEach items="${objectiveForm.objectives}"
			varStatus="objectiveStatus" var="reviewObjective">
			<c:if test='${!empty reviewObjective}'>
				<div id="objective${objectiveStatus.index}" style="width: 764px;">
					<form:hidden
						path="objectives[${objectiveStatus.index}].reviewObjectiveId" />
					<input type="hidden"
						id="objectives${objectiveStatus.index}.objectiveIndex"
						name="objectives[${objectiveStatus.index}].objectiveIndex"
						value="${objectiveStatus.index}" />
					<!-- Code for enable goals -->
					<c:choose>
						<c:when test="${!reviewObjective.isApproved 
						&&(	(reviewFormInfoVO.reviewFormRole.description == 'APPRAISE' 
								&& (reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1'
									|| reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2'
									|| reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE1_COMPLETED'))
							|| (reviewFormInfoVO.reviewFormRole.description != 'APPRAISE' 
									&& reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'))}">
							<!-- (reviewObjective.reviewPhase=='APPRAISE_IN_PHASE1'&& reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' && reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1') -->
							<c:set var="enableGoals" value="false" />
						</c:when>
						<c:otherwise>
							<c:set var="enableGoals" value="true" />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when
							test="${reviewFormInfoVO.reviewPhase.description != 'SYSTEM_PHASE2_COMPLETED' && reviewObjective.isApproved}">
							<h2 id="h2Banner${objectiveStatus.index}"
								onclick="toggleObjective(this)" style="color: midnightblue;">
								<div style="text-decoration: underline; width: 454px !important; float: left; padding-left: 4px;"></div>
								<div style="margin-right: 20px; float: left; width: 125px;"></div>
								<div style="margin-right: 30px; float: right; width: 125px;"></div>
							</h2>
						</c:when>
						<c:otherwise>
							<h2 id="h2Banner${objectiveStatus.index}"
								onclick="toggleObjective(this)">
								<div style="text-decoration: underline; width: 454px !important; float: left; padding-left: 4px;"></div>
								<div style="margin-right: 20px; float: left; width: 125px;"></div>
								<div style="margin-right: 30px; float: right; width: 125px;"></div>
							</h2>
						</c:otherwise>
					</c:choose>
					<div class="objective" style="display: none;">
						<div class="objectiveDetails" id="objectiveDetails${objectiveStatus.index}" style="height: auto;">
							<div class="row">
								<c:if
									test="${!reviewObjective.isApproved && ((reviewFormInfoVO.reviewFormRole.description == 'APPRAISE' 
							&& (reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1'||reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2'|| reviewFormInfoVO.reviewPhase.description == 'SYSTEM_PHASE2_COMPLETED'))||
							(reviewFormInfoVO.reviewFormRole.description != 'APPRAISE' 
							&& reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'))}">
									<%-- <c:if
										test="${reviewObjective.isNotApplicable==1 and reviewObjective.isApproved==0 and (reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_GOALS' || reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL')}">
										<span style="float: left; margin-left: 10px; color: red;">Appraiser needs more information on this Objective.</span>
									</c:if> --%>
									<img id="deleteObjective${objectiveStatus.index}"
										src="${root}common/images/delete.jpg"
										alt="Delete this Objective"
										onClick="deleteObj(this.id,${root})"
										style="margin-right: 24px; float: right;">
								</c:if>
								<%-- <c:if
									test="${(reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1' && (reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or  reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER')&& reviewObjective.reviewPhase=='APPRAISE_IN_PHASE1')
									||(reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2' && (reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or  reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER') && reviewObjective.reviewPhase=='APPRAISE_IN_PHASE2')}">
									<span style="float: left; margin-left: 10px; color: Green;">
										<form:checkbox
											path="objectives[${objectiveStatus.index}].isApproved"
											value="1" id="app${objectiveStatus.index}" />&nbsp;&nbsp;Approve
									</span>
								</c:if> --%>
							</div>
							<!-- code changes @swaroops -->
							<div class="row">
								<div class="subTitleSmall">
									<span>Objective<font color="#FF0000" face="Verdana"
										style="float: right; font-weight: bold;"> *</font></span><br> <span
										style="font-size: smaller;">(Max 200 Chars.)</span>
								</div>
								<div class="smallContent">
									<c:choose>
										<c:when test="${enableGoals}">
											<form:textarea rows="2" cols="50"
												path="objectives[${objectiveStatus.index}].objectiveName"
												readonly="true" class="readOnlyTextareaClass"
												onkeydown="preventBackspace()"
												id="ObjectiveName${objectiveStatus.index}"></form:textarea>
										</c:when>
										<c:otherwise>
											<form:textarea rows="2" cols="50"
												path="objectives[${objectiveStatus.index}].objectiveName"
												id="ObjectiveName${objectiveStatus.index}"
												onblur="changeBanner(this.id)"
												cssClass="objectiveName validate"></form:textarea>
										</c:otherwise>
									</c:choose>
								</div>
								<br>
							</div>
							<div class="row">
								<div class="subTitleSmall">
									<span style="padding-top: 5px;">Projects</span>
								</div>
								<div class="mediumContent">
									<c:choose>
										<c:when test="${!enableProjects}">
											<select
												name="objectives[${objectiveStatus.index}].projectIds"
												Class="projects" multiple style="width: 100px;">
												<c:forEach items="${objectiveForm.projectDropDown}"
													var='project'>
													<c:forEach
														items="${reviewObjective.reviewObjectiveProjectVOs}"
														var='reviewObjectiveProject'>
														<%-- <c:set var="canshow" value="true" /> --%>
														<c:if
															test="${project['key']== reviewObjectiveProject.projectId}">

															<option value="${project['key']}" disabled="disabled"
																selected="selected">${project["value"]}</option>

														</c:if>
													</c:forEach>
												</c:forEach>
												<%-- <form:options items="${objectiveForm.projectDropDown}" /> --%>
											</select>

										</c:when>
										<c:otherwise>
											<select
												name="objectives[${objectiveStatus.index}].projectIds"
												Class="projects" multiple style="width: 100px;">
												<c:forEach items="${objectiveForm.projectDropDown}"
													var='project'>
													<c:set var="canshow" value="true" />
													<c:forEach
														items="${reviewObjective.reviewObjectiveProjectVOs}"
														var='reviewObjectiveProject'>
														<%-- <c:set var="canshow" value="true" /> --%>
														<c:if
															test="${project['key']== reviewObjectiveProject.projectId}">
															<c:set var="canshow" value="false" />
															<c:choose>
																<c:when test="${reviewObjectiveProject.approved}">
																	<option value="${project['key']}" disabled="disabled"
																		selected="selected">${project["value"]}</option>
																</c:when>
																<c:otherwise>
																	<option value="${project['key']}" selected="selected">${project["value"]}</option>
																</c:otherwise>
															</c:choose>
														</c:if>
													</c:forEach>
													<c:if test="${canshow}">
														<option value="${project['key']}">${project["value"]}</option>
													</c:if>
												</c:forEach>
												<%-- <form:options items="${objectiveForm.projectDropDown}" /> --%>
											</select>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="row">
								<div class="subTitleSmall">
									<span>Details<font color="#FF0000" face="Verdana"
										style="float: right; font-weight: bold;"> *</font></span>
								</div>
								<div class="mediumContent">
									<c:choose>
										<c:when test="${enableGoals}">
											<form:textarea rows="2" cols="50"
												class="readOnlyTextareaClass" onkeydown="preventBackspace()"
												path="objectives[${objectiveStatus.index}].detailsComment"
												readonly="true"></form:textarea>
										</c:when>
										<c:otherwise>
											<form:textarea rows="2" cols="50"
												path="objectives[${objectiveStatus.index}].detailsComment"></form:textarea>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="row">
								<div class="subTitleSmall">
									<span>SuccessCriteria<font color="#FF0000"
										face="Verdana" style="float: right; font-weight: bold;">
											*</font></span>
								</div>
								<div class="mediumContent">
									<c:choose>
										<c:when test="${enableGoals}">
											<form:textarea rows="2" cols="50"
												class="readOnlyTextareaClass" onkeydown="preventBackspace()"
												path="objectives[${objectiveStatus.index}].successCriteriaComment"
												readonly="true"></form:textarea>
										</c:when>
										<c:otherwise>
											<form:textarea rows="2" cols="50"
												path="objectives[${objectiveStatus.index}].successCriteriaComment"></form:textarea>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<div class="row">
								<div class="targetCompletionDate">
									<div class="subTitleMedium">
										<span>Target Completion Date (dd-mm-yyyy)</span>
									</div>
									<div class="verySmallContent">
										<c:choose>
											<c:when test="${enableGoals}">
												<form:input cssClass="date readOnlyTextareaClass"
													path="objectives[${objectiveStatus.index}].targetCompletionDate"
													readonly="true" disabled="${enableGoals}"
													id="tgtcDate${objectiveStatus.index}"
													style="margin-right:5px;"></form:input>
											</c:when>
											<c:otherwise>
												<form:input cssClass="date validate"
													path="objectives[${objectiveStatus.index}].targetCompletionDate"
													readonly="true" disabled="${enableGoals}"
													id="tgtcDate${objectiveStatus.index}"
													style="margin-right:5px;"></form:input>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</div>
						</div>
						<c:if test="${renderEvaluatingPerformance }">
							<div class="evaluatingPerformance" style="height: auto;">
								<div class="row" style="padding-left: 0px;">
									<div class="mainTitle">
										<span>Evaluating Performance (This has to be filled at the end of the review period)</span>
									</div>
								</div>
								<c:if test="${renderAppraiseFields }">
									<div class="row">
										<div class="acheivementDate">
											<div class="subTitleMedium">
												<span>Achievement Date (dd-mm-yyyy)</span>
											</div>
											<div class="verySmallContent">
												<c:choose>
													<c:when test="${!enableAppraiseFields}">
														<form:input cssClass="date readOnlyTextareaClass"
															path="objectives[${objectiveStatus.index}].achievmentDate"
															readonly="true" disabled="${!enableAppraiseFields }"
															id="achmDate${objectiveStatus.index}"
															style="margin-right:5px;"></form:input>
													</c:when>
													<c:otherwise>
														<form:input cssClass="date"
															path="objectives[${objectiveStatus.index}].achievmentDate"
															readonly="true" disabled="${!enableAppraiseFields }"
															id="achmDate${objectiveStatus.index}"
															style="margin-right:5px;"></form:input>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${renderAppraiseFields }">
									<div class="row">
										<div class="howDidYouAchieve">
											<div class="title" style="margin-bottom: 10px;">
												<span>How did you achieve the Objective? What did you
													do in the Project?<font color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font>
												</span>
											</div>
											<div class="bigContent">
												<c:choose>
													<c:when test="${!enableAppraiseFields }">
														<form:textarea rows="2" cols="50"
															class="readOnlyTextareaClass"
															onkeydown="preventBackspace()"
															path="objectives[${objectiveStatus.index}].howYouAchievedComment"
															readonly="true"></form:textarea>
													</c:when>
													<c:otherwise>
														<form:textarea rows="2" cols="50"
															path="objectives[${objectiveStatus.index}].howYouAchievedComment"></form:textarea>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</div>
								</c:if>
								<div class="comments">
									<div class="row">
										<div class="title">
											<span>What went well ?</span>
										</div>
									</div>
									<c:if test="${renderAppraiseFields }">
										<div class="row">
											<div class="subTitleSmall">
												<span>Appraise </span> <span>Comments<font
													color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font></span>
											</div>
											<div class="mediumContent">
												<c:choose>
													<c:when test="${!enableAppraiseFields }">
														<form:textarea rows="2" cols="50"
															class="readOnlyTextareaClass"
															onkeydown="preventBackspace()"
															path="objectives[${objectiveStatus.index}].appraisePositiveComment"
															readonly="true"></form:textarea>
													</c:when>
													<c:otherwise>
														<form:textarea rows="2" cols="50"
															path="objectives[${objectiveStatus.index}].appraisePositiveComment"></form:textarea>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:if>
									<c:if test="${renderAppraiserFields }">
										<div class="row">
											<div class="subTitleSmall">
												<span>Appraiser </span> <span>Comments<font
													color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font></span>
											</div>
											<div class="mediumContent">
												<c:choose>
													<c:when test="${!enableAppraiserFields }">
														<form:textarea rows="2" cols="50"
															class="readOnlyTextareaClass"
															onkeydown="preventBackspace()"
															path="objectives[${objectiveStatus.index}].appraiserPositiveComment"
															readonly="true"></form:textarea>
													</c:when>
													<c:otherwise>
														<form:textarea rows="2" cols="50"
															path="objectives[${objectiveStatus.index}].appraiserPositiveComment"></form:textarea>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:if>
								</div>
								<div class="comments">
									<div class="row">
										<div class="title">
											<span>What could have been gone better ?</span>
										</div>
									</div>
									<c:if test="${renderAppraiseFields }">
										<div class="row">
											<div class="subTitleSmall">
												<span>Appraise </span> <span>Comments<font
													color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font></span>
											</div>
											<div class="mediumContent">
												<c:choose>
													<c:when test="${!enableAppraiseFields }">
														<form:textarea rows="2" cols="50"
															class="readOnlyTextareaClass"
															onkeydown="preventBackspace()"
															path="objectives[${objectiveStatus.index}].appraiseNegativeComment"
															readonly="true"></form:textarea>
													</c:when>
													<c:otherwise>
														<form:textarea rows="2" cols="50"
															path="objectives[${objectiveStatus.index}].appraiseNegativeComment"></form:textarea>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:if>
									<c:if test="${renderAppraiserFields }">
										<div class="row">
											<div class="subTitleSmall">
												<span>Appraiser </span> <span>Comments<font
													color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font></span>
											</div>
											<div class="mediumContent">
												<c:choose>
													<c:when test="${!enableAppraiserFields }">
														<form:textarea rows="2" cols="50"
															class="readOnlyTextareaClass"
															onkeydown="preventBackspace()"
															path="objectives[${objectiveStatus.index}].appraiserNegativeComment"
															readonly="true"></form:textarea>
													</c:when>
													<c:otherwise>
														<form:textarea rows="2" cols="50"
															path="objectives[${objectiveStatus.index}].appraiserNegativeComment"></form:textarea>
													</c:otherwise>
												</c:choose>
											</div>
										</div>
									</c:if>
								</div>
								<div class="rating">
									<div class="row">
										<div class="bigContent">
											<c:if test="${renderAppraiseFields }">
												<span style="margin-right: 10px; padding-top: 3px;">Appraise
													Rating<font color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font>
												</span>
												<c:choose>
													<c:when test="${!enableAppraiseFields }">
														<form:select class="readOnlyTextareaClass"
															path="objectives[${objectiveStatus.index}].appraiseRatingId"
															disabled="${!enableAppraiseFields }"
															id="appraiseRating${objectiveStatus.index}"
															onchange="changeRating(this.id);">
															<form:option value="-1">Select</form:option>
															<form:options items="${ratingDropDown}" />
														</form:select>
													</c:when>
													<c:otherwise>
														<form:select
															path="objectives[${objectiveStatus.index}].appraiseRatingId"
															disabled="${!enableAppraiseFields }"
															id="appraiseRating${objectiveStatus.index}"
															onchange="changeRating(this.id);">
															<form:option value="-1">Select</form:option>
															<form:options items="${ratingDropDown}" />
														</form:select>
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${renderAppraiserFields}">
												<span
													style="margin-left: 20px; margin-right: 10px; padding-top: 3px;">Appraiser
													Rating<font color="#FF0000" face="Verdana"
													style="float: right; font-weight: bold;"> *</font>
												</span>

												<c:choose>
													<c:when test="${!enableAppraiserFields }">
														<form:select class="readOnlyTextareaClass"
															path="objectives[${objectiveStatus.index}].appraiserRatingId"
															disabled="${!enableAppraiserFields }"
															id="appraisrRating${objectiveStatus.index}"
															onchange="changeRating(this.id);">
															<form:option value="-1">Select</form:option>
															<form:options items="${ratingDropDown}" />
														</form:select>
													</c:when>
													<c:otherwise>
														<form:select
															path="objectives[${objectiveStatus.index}].appraiserRatingId"
															disabled="${!enableAppraiserFields }"
															id="appraisrRating${objectiveStatus.index}"
															onchange="changeRating(this.id);">
															<form:option value="-1">Select</form:option>
															<form:options items="${ratingDropDown}" />
														</form:select>
													</c:otherwise>
												</c:choose>
											</c:if>
										</div>
									</div>
								</div>
							</div>
						</c:if>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</form:form>
<div id="objDeleteConfirmation" title="Confirmation"
	style="display: none">
	<p>Are you sure, you want to delete this Objective?</p>
</div>
