<%@include file="../common/taglibs.jsp"%>
<%@include file="feedbackUtils.jsp"%>
<c:url var="root" value="/"></c:url>
<%-- <script type="text/javascript" src="${root}common/js/feedback.js"></script>
<script type="text/javascript" src="${root}common/js/headerMenu.js"></script> --%>

<script type="text/javascript">
$(document).ready(function(){
//  Caching Js Files
jQuery.ajaxSetup({ cache:true});
	if(location=='in'){
		$("#reviewStatusId").html("Status : "+'${reviewFormInfoVO.reviewStatus}');
		$("#feedbackRequestIndex").css('width','780px');
		$("#feedbackHome").css('width','780px');
		//$("#feedbackHome").css('height','200px');
	} else {
		$("#feedbackHome").css('margin','20px');
		$("#feedbackHome").css('height','500px');
	}
});

$("#reviewCycleDropDown").on("change", function () {
	 var reviewCycleId = $('option:selected', this).attr('value');
	 if (reviewCycleId == -1){
		 $('#feedbackRequestIndex').html("Please Select Review Cycle.");
		 return;
	 }
	    var user='${user}';
	    var location='${location}';
	    var type='${type}';
	    var reviewHeaderId=0;
	    
	   	getFeedbackRequestIndex(${root}, reviewCycleId, user, location, reviewHeaderId,type);
	   	});

</script>
<!-- <style>
#feedbackTable {
	border: 1px solid #BEBEBE;
	text-align: center;
	width: 100%;
/* 	margin:20px; */
}
.butttonsDiv a {
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

.butttonsDiv a span {
	background: transparent url('${root}common/css/images/rightcurve.png')
		no-repeat top right;
	display: block;
	padding: 3px 20px 4px 10px;
	color: white;
	cursor: pointer;
}

 .ui-dialog-title{
font-size: 14px;

}


</style> -->
<div id="feedbackHome">
	<c:if test="${location!='in'}">
		<div id="pageLocation" style="height: 25px;"></div>
		<div id="pageDescription" style="margin-bottom: 20px;">
		<c:choose>
			<c:when test="${type=='self' and user=='sender'}">
				<span style="margin-bottom: 10px;">
				This page will be used for you to provide feed back on your peers. We recommend you to provide feedback on all your team members to the responsible Lead or Manager.
				</span>
			</c:when>
			<c:when test="${type=='request' and user=='sender'}">
			</c:when>
			<c:when test="${type=='request' and user=='receiver'}">
				<span style="margin-bottom: 20px;">
				This page displays any formal feedback solicited by managers. You will provide feedback in response to the questionnaire sent by Manager.
				</span>
			</c:when>
		</c:choose>
		
		</div>
		<div style="display: block;"><c:choose>
			<c:when test="${type=='self' and user=='sender'}">
				 <b>Review Cycle:</b>
			</c:when>
			<c:when test="${type=='request' and user=='sender'}">
				<b>Feedback Requests made for Review Cycle :</b>
			</c:when>
			<c:when test="${type=='request' and user=='receiver'}">
				<b>Feedback solicited by manager for Review Cycle:</b>
			</c:when>
		</c:choose></b>
		<select id="reviewCycleDropDown">
                  <option value="-1" selected="selected">Select</option>
				<c:forEach items="${reviewCycleDropDownItems}" var="reviewCycleTemp">
					<c:if test="${reviewCycleVO.reviewCycleId==reviewCycleTemp.id }">
						<option value="${reviewCycleTemp.id}" selected="selected">${reviewCycleTemp.value}</option>
					</c:if>
					<c:if test="${reviewCycleVO.reviewCycleId!=reviewCycleTemp.id }">
						<option value="${reviewCycleTemp.id}">${reviewCycleTemp.value}</option>
					</c:if>
				</c:forEach>
			</select>
		</div>
	</c:if>
	<div class="butttonsDiv">
		<c:if test="${user=='sender'}">
			<c:choose>
				<c:when test="${location=='in'}">
					<a style="margin-left: 626px;"
						onclick=" newFeedbackTemplate('${reviewFormInfoVO.reviewHeaderId}','${type}', '${location}','${root}') "
						href="javascript:void(0)" class="tooltip"
						title="Create new feedback Request"><span> New Request</span>
					</a>
				</c:when>
				<c:when test="${location=='out'and type=='request'}">
					<a style="margin-left: 840px;"
						onclick=" newFeedbackTemplate('0',  '${type}', '${location}','${root}') "
						href="javascript:void(0)" class="tooltip"
						title="Create new feedback Request"> <span>New Request</span>
					</a>
				</c:when>
				<c:otherwise>
					<a style="margin-left: 840px;"
						onclick=" newFeedbackTemplate('0',  '${type}', '${location}','${root}') "
						href="javascript:void(0)" class="tooltip" title="Give feedback">
						<span>Give Feedback</span> </a>
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
	<br /> <br /> <br />
	<div id="feedbackRequestIndex" style="height: 400px;overflow: auto;">
		<jsp:include page="./feedbackRequestIndex.jsp" />
	</div>
	<br /> <br /> <br />
<!-- 	<div id="feedbackTemplate"
		style="display: none; overflow: auto; min-height: 200px; max-height: 600px;"
		title="Feedback"></div> -->
</div>
<c:if test="${type !='self' or user!='sender'}">
			<div style="margin-left: 20px;padding-left:20px;padding-top:20px;padding-bottom:20px; width: 500px; " title="Feedback Types">
<h4>Feedback Types*</h4><br>
<b>Change Of Mgr.</b> - Feedback request generated by System on change of Manager. <br>
<b>Request</b> - Feedback requested by you.
</div>
</c:if>

