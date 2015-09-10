<%@ include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<link href="${root}common/css/v2/reviewFormIndex.css" rel="stylesheet"
	type="text/css" /><link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${root}common/js/v2/reviewSummary.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery.blockUI.js"></script>
<script type="text/javascript">
function saveSummary(){
	saveReviewSummary(${root}, ${reviewFormInfoVO.reviewHeaderId});
	$('#isEdited').val('no');
}

$(document).ready(function(){
	 jQuery.ajaxSetup({ cache:true});
		redirectToIndex('${reviewFormInfoVO.actualReviewStatus}','${reviewFormInfoVO.reviewFormRole}','${reviewFormInfoVO.reviewPhase.description}');
		$("#reviewStatusId").html("Status : "+'${reviewFormInfoVO.reviewStatus}');
		/* $("form :input").on('change', function(){
			$('#isEdited').val('yes');
		}); */
		$('#helpDocuments').on('click',function(){
			displayManagementToolKit(${root}, this);
			
		});
		
		$("#otherAppraisalLink").click(function() {
			  if ($(".otherAppraisalsSelect").val() == -1) {
				  setDialog("Please select a Review Cycle","alert");
	 			  $( "#alert" ).dialog( "open" );
				  return false;
			  }
			  $("#otherAppraisalLink").attr("href", ${root}+"appraisal/summary.html?reviewHeaderId="+$(".otherAppraisalsSelect").val());
			  return true;
		});
		
	});
	
</script>
<style type="text/css">
</style>

</head>
<body>
<div id="summaryPage">
	<input type="hidden" id="isEdited" name="isEdited" value="no" />
	<c:set var="displayInstructions" value="false" />
	<c:set var="displayInternalComments" value="false" />
	<c:if test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISE'}">

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NOT_STARTED' or reviewFormInfoVO.actualReviewStatus=='GOALS_SETTING_IN_PROGRESS'}">
			<c:set var="displayInstructions" value="true" />
		</c:if>

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_SUBMITTED'}">
<!-- 			Changed as per the changes suggested in UAT phase -->
			<c:set var="displayInstructions" value="true" />
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_FINALIZED'}">
 			<c:set var="displayInstructions" value="true" /> 
			<c:set var="displayInternalComments" value="true" /> 
		</c:if>

		<c:if test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_GOALS'}">
			<c:set var="displayInstructions" value="true" />
			<c:set var="displayInternalComments" value="true" />
		</c:if>

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_ACCEPTED' or reviewFormInfoVO.actualReviewStatus== 'APPRAISAL_IN_PROGRESS'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='APPRAISAL_SUBMITTED' or reviewFormInfoVO.actualReviewStatus=='ASSESSMENT_IN_PROGRESS'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>



	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISER' or reviewFormInfoVO.reviewFormRole.description=='SUPERUSER' or reviewFormInfoVO.reviewFormRole.description=='SHARED_APPRAISER'}">
		<!--  added this code in phase 2 */ -->
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_GOALS'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		
		
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_SUBMITTED'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_FINALIZED' or reviewFormInfoVO.actualReviewStatus=='GOALS_ACCEPTED' or reviewFormInfoVO.actualReviewStatus=='APPRAISAL_IN_PROGRESS'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='APPRAISAL_SUBMITTED' or reviewFormInfoVO.actualReviewStatus=='ASSESSMENT_IN_PROGRESS'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL'}">
			<c:set var="displayInternalComments" value="true" />
		</c:if>

	</c:if>

	<c:if test="${reviewFormInfoVO.actualReviewStatus=='COMPLETED'||reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'}">
		<c:set var="displayInternalComments" value="true" />
	</c:if>


	<c:if test="${displayInstructions}">
		<div id="instructions">
			<h3>Instructions</h3>
			<hr />
			<br /> <label class="tabName">Objectives:</label> <br /> <label
				class="tabDescription"><p>
					Select one or more objectives for next six months. At the end of
					the review period you will be asked to provide your achievement
					comments around these objectives.<br /> Objective should be
					Specific, Measurable, Achievable, Results-based and Time-specific.
				</p></label> <br /> <label class="tabName">Competencies:</label> <br /> <label
				class="tabDescription"><p>
					There are standard set of competencies. Please refer the <a
						id="helpDocuments">Performance Management Tool Kit</a> for
					more details on each competency. At the end of review period you
					will be asked to scale your self to the best of your ability.
				</p></label> <br /> <label class="tabName">Development Plan:</label> <br /> <label
				class="tabDescription"><p>Based on your commitments in
					Objectives section propose 1-3 development activities help you to
					enhance your performance. Your development plan needs to be based
					on all these aspects
				<ul>
					<li>Project/Business (Understanding of how the
						project/business works and why)</li>
					<li>Technical Skills</li>
					<li>Soft Skills
						<ul>
							<li>Communication</li>
							<li>Interpersonal Skills</li>
						</ul>
					</li>
				</ul>
				</p></label>
		</div>
	</c:if>
	<div id="appraisalSummary">
	<jsp:include page="appraisalSummary.jsp"></jsp:include>
	</div>

		<c:if test="${displayInternalComments}">
			<h3>
				Internal Comments History
			</h3>
			<hr />
			<br />
			<div class="internalCommentsConversation">
				<pre><c:forEach items="${reviewSummaryForm.reviewActionLogVOs}" 
					var="reviewActionLogVO"><b>${reviewActionLogVO.createdByName}  (<fmt:formatDate value="${reviewActionLogVO.createdDate}" pattern="yyyy-MM-dd hh:mm" />):</b><br/><c:out value="${reviewActionLogVO.userCommentText}"/>
					<br/></c:forEach></pre>
			</div>
		</c:if>
		<c:if test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISE' or reviewFormInfoVO.reviewFormRole.description=='APPRAISER' or reviewFormInfoVO.reviewFormRole.description=='SUPERUSER'}">
		<div id="previousReviewCycles">
			<h3><br/>
				Other Appraisals
			</h3>
			<hr />
			<br />
		<div id="submitrow" style="float: left; margin-right: 200px;">
			<select class="otherAppraisalsSelect">
				<option value="-1">--Select--</option>
				<c:forEach items="${otherAppraisals}" var="otherAppraisal">
					<option value="${otherAppraisal.reviewHeaderId}">${otherAppraisal.reviewCycleName}</option>
				</c:forEach>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;<a id="otherAppraisalLink" style="float: right;" class="tooltip" href="" target="_blank"><span>Go</span></a><br>
		</div>	
		</div>
		</c:if>
</div>
</body>