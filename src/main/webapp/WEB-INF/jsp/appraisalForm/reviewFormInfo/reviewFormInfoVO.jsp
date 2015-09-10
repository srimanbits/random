<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<%-- <script type="text/javascript" src="${root}common/js/jquery.dataTables_1.9.0.js"></script>
	<script type="text/javascript" src="${root}common/js/TableTools.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${root}common/css/jquery.dataTables.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/jquery-ui-1.10.0.custom.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/TableTools.css" /> --%>
<style type="text/css">
#reviewFormInfo {
	width: 1200px;
	height: 60px;
	background-color: rgba(11, 159, 245, 0.21);
	line-height: 20px;
	font-weight: bold;
}

#reviewFormInformation {
	width: 1200px;
	height: 30px;
	display: block;
}

.spancontent {
	padding-left: 10px;
	padding-right: 10px;
	padding-bottom: 10px;
	float: left;
}

#reviewFormInfoButtons {
	width: 1200px;
	height: 30px;
	display: block;
	/* 	width: 35%; margin: 0 auto; text-align: left" */
}

#reviewFormInfoButtons a {
	background: transparent url('${root}common/css/images/leftcurve.png')
		no-repeat top left;
	display: block;
	float: left;
	font: 11px Verdana;
	line-height: 15px;
	height: 22px;
	padding-left: 8px;
	text-decoration: none;
}

#reviewFormInfoButtons span {
	float: right;
}

#reviewFormInfoButtons a span {
	background: transparent url('${root}common/css/images/rightcurve.png')
		no-repeat top right;
	display: block;
	padding: 3px 20px 4px 10px;
	color: white;
	cursor: pointer;
}

.spancontentButton {
	padding-left: 10px;
	padding-right: 10px;
	padding-bottom: 10px;
	/* 	float: right; */
}

#selectTargetEmployeeDiv {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	margin: 10px;
}

#selectTargetEmployeeDiv #selectEmployeeLabel {
	width: 40%;
	margin: 10px;
}

#selectTargetEmployeeDiv #selectEmployee {
	width: 50%;
	margin: 10px;
}
</style>
<div id="reviewFormInfo">
	<div id="reviewFormInformation">
		<c:if
			test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER'}">
			<span class="spancontent"> Appraise :
				${reviewFormInfoVO.appraise} </span>
		</c:if>
		<c:if
			test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISE' or reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER' or reviewFormInfoVO.isManagersMgr == true}">
			<span class="spancontent"> Appraiser :
				${reviewFormInfoVO.mainAppraiser} </span>
		</c:if>
		<span class="spancontent"> Grade-Desg :
				${reviewFormInfoVO.grade}-${reviewFormInfoVO.designation} </span>
				<span class="spancontent"
			id="reviewCycleName"> Appraisal :
			${reviewFormInfoVO.reviewCycleName} </span>
		<span class="spancontent" id="reviewStatusId"> Status :
			${reviewFormInfoVO.reviewStatus} </span> 
	</div>
	<div id="reviewFormInfoButtons">
		<c:if
			test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISER' or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER'}">
			<c:if
				test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE2'}">
				<span class="spancontentButton"> <a id="approve_appraisal"
					 title="Approve Appraisal"
					onclick="changeStatus(this.id)"><span>Approve Appraisal</span></a>
					<!-- 										<img id="approve_appraisal" --> <%-- 										src="${root}common/images/Approve.png" --%>
					<!-- 										alt="Approve Appraisal" class="tooltip" title="Approve Appraisal" onclick="changeStatus(this.id)"> -->
				</span>
				<span class="spancontentButton"> <a id="resubmit_to_appraise"
					 title="Reassign to appraise for more information"
					onclick="changeStatus(this.id)"><span>Resubmit to
							Appraise</span></a> <!-- 									<img id="resubmit_to_appraise" --> <%-- 										src="${root}common/images/Reject.png" --%>
					<!-- 										alt="Reject" class="tooltip" title="Reassign to appraise for more information" onclick="changeStatus(this.id)"> -->
				</span>
			<%-- 	<span class="spancontentButton"> <a id="shareAppraisal" class="${reviewFormInfoVO.reviewHeaderId}" type="${reviewFormInfoVO.reviewCycleName}"
				 title="Share Appraisal"><span>Share Appraisal</span></a>
			</span> --%>
			</c:if>
			<c:if
				test="${reviewFormInfoVO.reviewPhase.description == 'APPRAISER_IN_PHASE1'}">
				<span class="spancontentButton"> <a id="approve_goals"
					 title="Approve goals"
					onclick="changeStatus(this.id)"><span>Approve Goals</span></a></span>
				<span class="spancontentButton"> <a id="resubmit_to_appraise"
					 title="Reassign to appraise for more information"
					onclick="changeStatus(this.id)"><span>Resubmit to
							Appraise</span></a> </span>
			</c:if>
		</c:if>
		<c:if
			test="${reviewFormInfoVO.reviewFormRole.description == 'SHARED_APPRAISER'}">
			<span class="spancontentButton"> <a id="assessment_Completed"
				 title="Assessment Completed"
				onclick="changeStatus(this.id)"><span>Unshare</span></a>
			</span>
		</c:if>
		<c:if
			test="${reviewFormInfoVO.reviewFormRole.description == 'APPRAISE'}">
			<c:if
				test="${reviewFormInfoVO.actualReviewStatus == 'NOT_STARTED' or
			  reviewFormInfoVO.actualReviewStatus == 'GOALS_SETTING_IN_PROGRESS'
			   or reviewFormInfoVO.actualReviewStatus == 'NEED_TO_EDIT_GOALS' 
			   or reviewFormInfoVO.actualReviewStatus=='GOALS_FINALIZED'
			    or reviewFormInfoVO.actualReviewStatus=='GOALS_ACCEPTED'
			    or reviewFormInfoVO.actualReviewStatus=='APPRAISAL_IN_PROGRESS'
			    or reviewFormInfoVO.actualReviewStatus=='NEED_TO_EDIT_APPRAISAL'
			    or reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'
			    }">
				<span class="spancontentButton"><a id="submit_to_appraiser"
					title="Submit your appraisal to your manager"
					onclick="changeStatus(this.id)"><span>Submit to appraiser</span></a></span>
			</c:if>
			<c:if test="${reviewFormInfoVO.actualReviewStatus=='GOALS_FINALIZED'}">
				<span class="spancontentButton"><a id="accept_goals"
					 title="Accept the goals"
					onclick="changeStatus(this.id)"><span>Accept goals</span></a></span>
			</c:if>
			<c:if test="${reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING'}">
				<span class="spancontentButton"><a id="accept_appraisal"
					 title="Accept the appraisal"
					onclick="changeStatus(this.id)"><span>Accept appraisal</span></a></span>
			</c:if>
		</c:if>
			    <c:if test="${reviewFormInfoVO.actualReviewStatus!='COMPLETED'}">
			<span class="spancontentButton"> <a id="addNotes"
				 title="Add Notes"
				onclick="addNotes('${reviewFormInfoVO.reviewHeaderId}', '${reviewFormInfoVO.reviewFormRole.description}')"><span>Add
						Notes</span></a>
			</span>
			<c:if
			test="${reviewFormInfoVO.mainAppraiserId == reviewFormInfoVO.currentManagerId or reviewFormInfoVO.reviewFormRole.description == 'SUPERUSER' or reviewFormInfoVO.reviewFormRole.description == 'APPRAISE'}"> 
			<span class="spancontentButton"> <a id="showONO"
				 title="One to One"
				onclick="showONO('${reviewFormInfoVO.reviewHeaderId}')"><span>One On One</span></a>
			</span>
			
			<span class="spancontentButton"> <a id="history"
				 title="History"
				onclick="showHistory('${reviewFormInfoVO.reviewHeaderId}')"><span>Employee History</span></a>
			</span>
			 </c:if>
			</c:if>
	</div>
</div>