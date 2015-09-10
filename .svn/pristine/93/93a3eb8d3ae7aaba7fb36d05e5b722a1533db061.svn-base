<%@ include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript">

	$(document).ready(function(){
		$('.objectiveName').keyup(function() {
			  changeBanner($(this).attr("id"));
			});
		
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
	});
</script>

<c:set var="enableGoals" value="" />
<c:set var="renderAppraiseFields" value="true" />
<c:set var="enableAppraiseFields" value="disabled" />
<c:set var="renderAppraiserFields" value="true" />
<c:set var="enableAppraiserFields" value="disabled" />
<c:set var="renderAddObjButton" value="true" />
<c:set var="renderDeleteObjButton" value="true" />
<c:set var="renderSaveObjsButton" value="true" />
<c:set var="renderEvaluatingPerformance" value="true" />



<c:if
	test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISE'}">
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE1' or reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'}">
		<c:set var="renderEvaluatingPerformance" value="false" />
	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1' or reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
		<c:set var="enableGoals" value="disabled" />
		<c:set var="renderAddObjButton" value="false" />
		<c:set var="renderDeleteObjButton" value="false" />
		<c:set var="renderSaveObjsButton" value="false" />
	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISE_IN_PHASE2'||reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE1_COMPLETED'}">
		<c:set var="enableAppraiseFields" value="true" />
		<c:set var="renderAppraiserFields" value="false" />
	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
		<c:set var="renderAppraiserFields" value="false" />
	</c:if>
</c:if>
<!-- For appraiser -->

<c:if
	test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER'}">
	<c:if
		test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'}">
		<c:set var="renderEvaluatingPerformance" value="false" />
	</c:if>
</c:if>

<div id="objective${objectiveNumber}" style="width: 764px;">
	<input id="objectives${objectiveNumber}.reviewObjectiveId"
		name="objectives[${objectiveNumber}].reviewObjectiveId" type="hidden"
		value="${reveiwObj.reviewObjectiveId}" /> <input
		id="objectives${objectiveNumber}.objectiveIndex"
		name="objectives[${objectiveNumber}].objectiveIndex" type="hidden"
		value="${objectiveNumber}" />
	<h2 onclick="toggleObjective(this)" id="h2Banner${objectiveNumber}"
		class="active">
		<div style="text-decoration: underline; width: 454px !important; float: left;padding-left: 4px;">Unnamed</div>
		<div style="margin-right: 20px;float: left;width:125px;"></div> <div style="margin-right: 30px;float: right;width:125px;"></div>
	</h2>
	<div class="objective" style="margin-right: 25px;">
		<div class="objectiveDetails" id="objectiveDetails${objectiveNumber}">
			<c:if test="${renderDeleteObjButton }">
				<div class="row">
					<img id="deleteObjective${objectiveNumber}"
						src="${root}common/images/delete.jpg" alt="Delete this objective"
						onClick="deleteObj(this.id)" title="Delete this objective"
						style="margin-right: 24px; float: right;">
				</div>
			</c:if>
			<div class="row">
				<div class="subTitleSmall">
					<span>Objective<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span> <span style="font-size: smaller;">(Max
						200 Chars.)</span>
				</div>
				<div class="smallContent">
					<textarea name="objectives[${objectiveNumber}].objectiveName"
						rows="2" cols="50" ${enableGoals} class="objectiveName"
						onkeydown="changeBanner(this.id)"
						onblur="changeBanner(this.id)"
						id="ObjectiveName${objectiveNumber}">${reveiwObj.objectiveName}</textarea>
				</div>
				<br>
			</div>
			<div class="row">
				<div class="subTitleSmall">
					<span>Projects</span>
				</div>
				<div class="smallContent">
					<select id="objectives${objectiveNumber}.projectId"
						name="objectives[${objectiveNumber}].projectIds" ${enableGoals} multiple="true" class="projects" style="width: 100px;">
						<c:forEach items="${projectDropDown}" var="project">
							<option value="${project.key}">${project.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="subTitleSmall">
					<span>Details<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
				</div>
				<div class="mediumContent">
					<textarea id="objectives${objectiveNumber}.detailsComment"
						name="objectives[${objectiveNumber}].detailsComment" rows="2"
						cols="50" ${enableGoals}>${reveiwObj.detailsComment}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="subTitleSmall">
					<span>SuccessCriteria<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
				</div>
				<div class="mediumContent">
					<textarea id="objectives${objectiveNumber}.successCriteriaComment"
						name="objectives[${objectiveNumber}].successCriteriaComment"
						rows="2" cols="50" ${enableGoals}>${reveiwObj.successCriteriaComment}</textarea>
				</div>
			</div>
			<div class="row">
				<div class="targetCompletionDate">
					<div class="subTitleMedium">
						<span>Target Completion Date (dd-mm-yyyy)</span>
					</div>
					<div class="verySmallContent">
						<input name="objectives[${objectiveNumber}].targetCompletionDate"
							class="date editableDate" type="text"
							value="${reveiwObj.targetCompletionDate}" readonly="readonly"
							${enableGoals} id="tgtcDate${objectiveNumber}"
							 />
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
								<input name="objectives[${objectiveNumber}].achievmentDate"
									class="date editableDate" type="text" value="${reveiwObj.achievmentDate}"
									readonly="readonly" ${enableAppraiseFields }
									id="achmDate${objectiveNumber}" />
							</div>
						</div>
					</div>
				</c:if>
				<c:if test="${renderAppraiseFields }">
					<div class="row">
						<div class="howDidYouAchieve">
							<div class="title" style="margin-bottom: 10px;">
								<span>How did you achieve the Objective? What did you do
									in the Project?</span>
							</div>
							<div class="bigContent">
								<textarea
									id="objectives${objectiveNumber}.howYouAchievedComment"
									name="objectives[${objectiveNumber}].howYouAchievedComment"
									rows="2" cols="50" ${enableAppraiseFields }>${reveiwObj.howYouAchievedComment}</textarea>
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
								<span>Appraise </span>
								<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
							</div>
							<div class="mediumContent">
								<textarea
									id="objectives${objectiveNumber}.appraisePositiveComment"
									name="objectives[${objectiveNumber}].appraisePositiveComment"
									rows="2" cols="50" ${enableAppraiseFields }>${reveiwObj.appraisePositiveComment}</textarea>
							</div>
						</div>
					</c:if>
					<c:if test="${renderAppraiserFields }">
						<div class="row">
							<div class="subTitleSmall">
								<span>Appraiser </span>
								<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
							</div>
							<div class="mediumContent">
								<textarea
									id="objectives${objectiveNumber}.appraiserPositiveComment"
									name="objectives[${objectiveNumber}].appraiserPositiveComment"
									rows="2" cols="50" ${enableAppraiserFields }>${reveiwObj.appraiserPositiveComment}</textarea>
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
								<span>Appraise </span>
								<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
							</div>
							<div class="mediumContent">
								<textarea
									id="objectives${objectiveNumber}.appraiseNegativeComment"
									name="objectives[${objectiveNumber}].appraiseNegativeComment"
									rows="2" cols="50" ${enableAppraiseFields }>${reveiwObj.appraiseNegativeComment}</textarea>
							</div>
						</div>
					</c:if>
					<c:if test="${renderAppraiserFields }">
						<div class="row">
							<div class="subTitleSmall">
								<span>Appraiser </span>
								<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
							</div>
							<div class="mediumContent">
								<textarea
									id="objectives${objectiveNumber}.appraiserNegativeComment"
									name="objectives[${objectiveNumber}].appraiserNegativeComment"
									rows="2" cols="50" ${enableAppraiserFields }>${reveiwObj.appraiserNegativeComment}</textarea>
							</div>
						</div>
					</c:if>
				</div>
				<div class="rating">
					<div class="row">
						<div class="bigContent">
							<c:if test="${renderAppraiseFields }">
								<span style="margin-right: 10px; padding-top: 3px;">Appraise Rating<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
								<select id="appraiseRating${objectiveNumber}"
									name="objectives[${objectiveNumber}].appraiseRatingId"
									${enableAppraiseFields } onchange="changeRating(this.id);">
									<option value="-1">Select</option>
									<c:forEach items="${ratingDropDown}" var="rating">
										<option value="${rating.key}">${rating.value}</option>
									</c:forEach>
								</select>
							</c:if>
							<c:if test="${renderAppraiserFields }">
								<span style="margin-left: 20px; margin-right: 10px; padding-top: 3px;">Appraiser Rating<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
								<select id="appraisrRating${objectiveNumber}"
									name="objectives[${objectiveNumber}].appraiserRatingId"
									${enableAppraiserFields } onchange="changeRating(this.id);">
									<option value="-1">Select</option>
									<c:forEach items="${ratingDropDown}" var="rating">
										<option value="${rating.key}">${rating.value}</option>
									</c:forEach>
								</select>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>