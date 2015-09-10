<%@ include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<script type="text/javascript">
	$(document).ready(function(){
		$('.goalName').keyup(function() {
			  changeBanner($(this).attr("id"));
			});
	});
</script>

<c:set var="editActivities" value="false" />
<c:set var="editAppraiseFields" value="false" />
<c:set var="editAppraiserFields" value="false" />
<c:if
	test="${(reviewFormInfoVO.reviewFormRole.description=='APPRAISE' && reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE1')
	or ((reviewFormInfoVO.reviewFormRole.description=='APPRAISER' || reviewFormInfoVO.reviewFormRole.description=='SUPERUSER' || reviewFormInfoVO.reviewFormRole.description=='SHARED_APPRAISER') && reviewFormInfoVO.reviewPhase.description=='APPRAISER_IN_PHASE1')}">
	<c:set var="editActivities" value="true" />
</c:if>

<c:if
	test="${(reviewFormInfoVO.reviewFormRole.description=='APPRAISE' &&(reviewFormInfoVO.reviewPhase.description=='SYSTEM_PHASE1_COMPLETED'||reviewFormInfoVO.reviewPhase.description=='APPRAISE_IN_PHASE2'))}">
	<c:set var="editActivities" value="true" />
    <c:set var="editAppraiseFields" value="true" />	
</c:if>

<script type="text/javascript">
</script>
<c:if test="${editActivities}">
	<div id="devPlan${index}">
		<input type="hidden"
			name="reviewDevelopmentPlanActivities[${index}].reviewDevelopmentPlanActivityId"
			class="hidden" />
		<h2 onclick="toggleObjective(this)"  id="h2Banner${index}" class="active" style="text-decoration: underline;padding-left: 4px;">Unnamed</h2>
		<div class="devActivity" style="background-color: rgba(11, 159, 245, 0.207843);">
			<div class="row">
				<img id="deleteDevPlan${index}"
					src="${root}common/images/delete.jpg" alt="Delete this Goal" onClick="deleteActivity(this.id)"
					style="margin-left: 736px;" >
			</div>

			<div class="row">
				<div class="subTitleSmall">
					<span>Development Goal<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
				</div>
				<div class="smallContent">
					<textarea
						name="reviewDevelopmentPlanActivities[${index}].goalComment"
						class="goalName" onblur="changeBanner(this.id)" id="goalName${index}"></textarea>
				</div>
			</div>

			<div class="row">
				<div class="subTitleSmall">
					<span>Measurement<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
				</div>
				<div class="mediumContent">
					<textarea rows="2" cols="10"
						name="reviewDevelopmentPlanActivities[${index}].measurementComment"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="subTitleSmall">
					<span>Action steps<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
				</div>
				<div class="mediumContent">
					<textarea
						name="reviewDevelopmentPlanActivities[${index}].actionStepsComment"></textarea>
				</div>
			</div>
			<c:if test="${editAppraiseFields or readAppraiseFields}">
				<div id="appraiseSection" class="row">
					<div class="subTitleSmall">
						<span>Appraise </span>
						<span>Comments<font color="#FF0000" face="Verdana" style="float: right;font-weight: bold;"> *</font></span>
					</div>
					<div class="mediumContent">
						<textarea
							name="reviewDevelopmentPlanActivities[${index}].appraiseComment"></textarea>
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
						<textarea
							name="reviewDevelopmentPlanActivities[${index}].appraiserComment"></textarea>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</c:if>

