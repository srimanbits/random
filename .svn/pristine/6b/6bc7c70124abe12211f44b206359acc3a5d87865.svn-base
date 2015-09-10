<%@ include file="../../common/taglibs.jsp"%>


<script type="text/javascript">
$(document).ready(function(){
	$("form :input").on('change', function(){
		$('#isEdited').val('yes');
	});
	
	$("#confirmDialog").dialog({
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
							saveSummary();
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
	
});

function saveSummaryV2(){
	if('${reviewFormInfoVO.actualReviewStatus}'== 'COMPLETED'){
		
		if($('#isEdited').val() == 'yes'){
		$("#confirmDialog").dialog("open");
		
		}else{
			setDialog("Please change rating before saving.","alert");
			$("#alert").dialog("open");
		}
		return;
	}else{
		saveSummary();	
	}
	
	
}
</script>
<c:set var="readAppraiseFields" value="false" />
<c:set var="editAppraiseFields" value="false" />
<c:set var="readAppraiserFields" value="false" />
<c:set var="editAppraiserFields" value="false" />
<c:set var="displayAppraisalSummary" value="false" />
<c:set var="disableChangeOfCommentsOnlyForSuperUser" value="false" />

<c:if test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISE'}">

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='GOALS_ACCEPTED' or reviewFormInfoVO.actualReviewStatus== 'APPRAISAL_IN_PROGRESS'}">
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="editAppraiseFields" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='APPRAISAL_SUBMITTED' or reviewFormInfoVO.actualReviewStatus=='ASSESSMENT_IN_PROGRESS'}">
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="readAppraiseFields" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL'}">
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="editAppraiseFields" value="true" />
		</c:if>
   



	</c:if>
	<c:if
		test="${reviewFormInfoVO.reviewFormRole.description=='APPRAISER' or reviewFormInfoVO.reviewFormRole.description=='SUPERUSER' or reviewFormInfoVO.reviewFormRole.description=='SHARED_APPRAISER'}">
		<!--  added this code in phase 2 */ -->
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='APPRAISAL_SUBMITTED' or reviewFormInfoVO.actualReviewStatus=='ASSESSMENT_IN_PROGRESS'}">
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="editAppraiserFields" value="true" />
		</c:if>

		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL'}">
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="readAppraiseFields" value="true" />
		</c:if>
		<c:if
			test="${reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'}">
<!-- 			Changed as per the changes suggested in UAT phase -->
			<c:set var="displayAppraisalSummary" value="true" />
			<c:set var="readAppraiseFields" value="true" />
			<c:set var="readAppraiserFields" value="true" />
		</c:if>
		

	</c:if>
	<c:if test="${reviewFormInfoVO.actualReviewStatus=='COMPLETED' or reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'}">
		<c:set var="displayAppraisalSummary" value="true" />
		<c:set var="readAppraiseFields" value="true" />
		<c:set var="readAppraiserFields" value="true" />
	</c:if>
	<!-- Super user should be able to change rating after completed state as well. -->
	<c:if test="${(reviewFormInfoVO.actualReviewStatus=='COMPLETED') and  reviewFormInfoVO.reviewFormRole.description=='SUPERUSER'}">
		<c:set var="displayAppraisalSummary" value="true" />
		<c:set var="readAppraiseFields" value="true" />
		<c:set var="editAppraiserFields" value="true" />
		<c:set var="readAppraiserFields" value="false" />
		<c:set var="disableChangeOfCommentsOnlyForSuperUser" value="true" />
	</c:if>
	<form:form method="post" commandName="reviewSummaryForm">
	<br />
		<c:if test="${displayAppraisalSummary}">
				<div id="appraisalSummaryHeading">
					<h3>Appraisal Summary</h3>
					<c:if test="${editAppraiseFields or editAppraiserFields}">
						<a id="saveButtton" class="tooltip" title="Save appraisal summary" onclick="saveSummaryV2()"><span>Save
								Summary</span></a>
					</c:if>
				</div>
				<hr />
				<br />
				<c:if test="${readAppraiseFields or editAppraiseFields}">
					<div class="appraiseSection">
						<div>
							<div class="CommentsHeader">
								<form:label path="reviewSummaryVO.appraiseComment">Appraise Comments<font color="#FF0000" face="Verdana"
										style="float: right; font-weight: bold;">*</font></form:label>
							</div>
							<!--  ratings section begin -->

							<div class="RatingDropDown">
								<form:select disabled="${readAppraiseFields}"
									path="reviewSummaryVO.appraiseRatingId">
									<form:option value="-1">Select</form:option>
									<form:options items="${ratingDropDownItems}" />
								</form:select>
							</div>
							<div class="RatingHeader">Appraise Rating<font color="#FF0000" face="Verdana"
										style="float: right;">*</font></div>
							<!--  ratings section end -->
						</div>
						<div>
							<c:choose>
								<c:when test="${readAppraiseFields}">
									<form:textarea readonly="true" onkeydown="preventBackspace()"
										class="readOnlyTextareaClass"
										path="reviewSummaryVO.appraiseComment" cols="40" rows="6" />
								</c:when>
								<c:otherwise>
									<form:textarea path="reviewSummaryVO.appraiseComment" cols="40"
										rows="6" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>
				<c:if test="${readAppraiserFields or editAppraiserFields}">

					<div class="appraiserSection">
						<div>
							<div class="CommentsHeader">
								<form:label path="reviewSummaryVO.appraiserComment">Appraiser Comments<font color="#FF0000" face="Verdana"
										style="float: right;">*</font></form:label>
							</div>
							<!--  ratings section begin -->

							<div class="RatingDropDown">
								<form:select disabled="${readAppraiserFields}"
									path="reviewSummaryVO.appraiserRatingId">
									<form:option value="-1">Select</form:option>
									<form:options items="${ratingDropDownItems}" />
								</form:select>
							</div>
							<div class="RatingHeader">Appraiser Rating<font color="#FF0000" face="Verdana"
										style="float: right;">*</font></div>
							<!--  ratings section end -->
						</div>
						<div>
							<c:choose>
								<c:when test="${readAppraiserFields or disableChangeOfCommentsOnlyForSuperUser}">
									<form:textarea readonly="true" onkeydown="preventBackspace()"
										class="readOnlyTextareaClass"
										path="reviewSummaryVO.appraiserComment" cols="40" rows="6" />
								</c:when>
								<c:otherwise>
									<form:textarea path="reviewSummaryVO.appraiserComment" cols="40"
										rows="6" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</c:if>

			<br />
			<br />
		</c:if>
	</form:form>
	
	<div id="confirmDialog" style="display: none;"
	title="Confirm rating change">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span> Appraiser rating
		will be changed. Are you sure?
	</p>
</div>