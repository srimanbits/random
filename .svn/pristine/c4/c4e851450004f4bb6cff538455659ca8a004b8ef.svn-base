<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript" src="${root}common/js/v2/bodyMenu.js"></script>
<script type="text/javascript">
$(document).ready(function () { 
	//var currentTab='reviewSummary';
	$("#reviewSummary").parent().addClass('selected');
	 $('#reviewSummary').click(function() {
	    	getReviewSummary(${root}, ${reviewFormInfoVO.reviewHeaderId}, $('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));
	    	

	    });
	 $('#reviewDevelopmentPlan').click(function() {
	     	getDevelopmentPlan(${root}, ${reviewFormInfoVO.reviewHeaderId}, $('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));
	     	

	   });
	 $('#reviewCompetencies').click(function() {
	    	getCompetencies(${root}, ${reviewFormInfoVO.reviewHeaderId},  $('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));	    	
	    });
	 $('#reviewObjectives').click(function() {
	    	getObjectives(${root}, ${reviewFormInfoVO.reviewHeaderId}, $('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));	    	
	    });
	 
	 $('#reviewFeedback').click(function() {
	    	getFeedback(${root}, ${reviewFormInfoVO.reviewHeaderId},$('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));
	    	
	    });
    
    $('#reviewLog').click(function() {
    	getHistory(${root}, ${reviewFormInfoVO.reviewHeaderId},$('#isEdited').val(), $("#body-menu-list .selected").find("a").attr("id"));
    });
   
});

</script>
<div id="body-menu">
	<ul id="body-menu-list">
		<li class="outerListElements unSelected"><a id="reviewSummary"> Summary</a></li>
		<li class="outerListElements unSelected"><a id="reviewObjectives"> Objectives</a></li>
		<li class="outerListElements unSelected"><a id="reviewCompetencies"> Competencies</a></li>
		<li class="outerListElements unSelected"><a id="reviewDevelopmentPlan"> Development Plan</a></li>
		<c:if test="${reviewFormInfoVO.reviewFormRole =='SHARED_APPRAISER'}">
			<li class="outerListElements unSelected"><a id="reviewFeedback"> Feedback </a> </li> 
		</c:if>
		<li class="outerListElements unSelected"><a id="reviewLog"> Activity Log</a></li>
	</ul>
</div>
