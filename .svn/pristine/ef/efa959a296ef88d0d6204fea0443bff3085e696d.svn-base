<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript" src="${root}common/js/v2/headerMenu.js"></script>
<script type="text/javascript">
$(document).ready(function () { 	
	
	$('#selfIndex').click(function() {
		getAppraisalHome(${root},"self", this);
    });
    $('#peersIndex').click(function() {
    	getAppraisalHome(${root},"peers", this);
    });
    
    $('#allIndex').click(function() {
   		getAppraisalHome(${root},"all", this);
    });

    $('#defaultObjectives').click(function() {
    	alert("This will be implented later");

    });
    $('#createReviewCycle').click(function() {
    	alert("This will be implented later");

    });
//     $('#orgChart').click(function() {
//     	alert("This will be implented later");

//     });
    $('#feedbackQuestionnaire').click(function() {
    	alert("This will be implented later");

    });
    $('#reviewCycleStatus').click(function() {
    	alert("This will be implented later");

    });
    $('#feedbackRequestsIn').click(function() {
    	getFeedbackRequestsIn(${root}, this);

    });
    
    $('#createFeedBackRequest').click(function() {
    	getFeedbackRequestsOut(${root}, this);

    });
    
    $('#feedback').click(function() {
    	getSelfFeedbackTemplate(${root}, this);
    });
    
    $('#mangementToolKit').click(function() {
    	displayManagementToolKit(${root}, this);
    });
    
     $("#serverDetails").click(function() {
    	getServerProperties(${root},this);
    }); 
     
     $("#Half-Yearly").click(function() {
    	 getH1ReminderStages(${root},this);
     }); 
     
     $("#Annual").click(function() {
    	 getH1ReminderStages(${root},this);
     });
     
     $("#addReviewCycle").click(function() {
         getReviewCycleDetails(${root},this);
     });
     
     $("#getEmployeeHistory").click(function() {
    	 getEmployeeHistory(${root},<sec:authentication property="principal.empId" />,this,true);
     });
     
     /* $("#getDesgnHistory").click(function() {
    	 getEmployeeHistory(${root},<sec:authentication property="principal.empId" />,this,true);
     }); */
    
    /*$('#helpDocs').click(function() {
    	displayHelpDocuments(${root}, this);
    }); */
    
     $("ul.dropdown li").hover(function(){
    	    
         $(this).addClass("hover");
         $('ul:first',this).css('visibility', 'visible');
     
     }, function(){
     
         $(this).removeClass("hover");
         $('ul:first',this).css('visibility', 'hidden');
     
     });
     
    $("#reminder_messages").hover(function(){
    	//$(this).addClass("hover");
    	$("#halfyearly").css('background', '#555');
    	 $("#annually").css('background', '#555');
        $("#halfyearly").css('visibility', 'visible');
        $("#annually").css('visibility', 'visible');
    
    }, function(){
    
       // $(this).removeClass("hover");
        $("#halfyearly").css('visibility', 'hidden');
        $("#annually").css('visibility', 'hidden');
    
    });
    
    $('#editCompetency').click(function() {
    	displayEditCompetencies(${root}, this);
    });
     
});
</script>

<style type="text/css">

</style>

<sec:authentication property="principal.managerEmployee" var="isManagerExist"  scope="page"  />
<!-- <div id="header-menu-submenu" style="height:40px;"> -->
<div id="header-menu">
	<ul id="header-menu-list">
		<li class="outerListElements"><a href="${root}welcome.html">Home</a></li>
		<li class="outerListElements"><a href="#">Appraisal</a>
			<ul class="header-submenu-list">
				<c:if test="${not empty isManagerExist}">
					<li class="innerListElements"><a id="selfIndex">Self Appraisal</a></li>
            	</c:if>
				<li class="innerListElements"><a id="feedbackRequestsIn">Feedback Solicited</a></li>
				<li class="innerListElements"><a id="feedback">Give Feedback</a></li>
			</ul>
		</li>
		<li class="outerListElements"><a> Manager</a>
			<ul class="header-submenu-list">
				<li class="innerListElements"><a id="peersIndex">Team Appraisals</a></li>
				<!-- <li class="innerListElements"><a href="/hrms/review/manager/defaultObjectives.html">Default Objectives</a></li> -->
			</ul>
		</li>
		<li class="outerListElements"><a> Super user</a>
			<ul class="header-submenu-list">
				<li class="innerListElements"><a id="allIndex">All Appraisals</a></li>
				<li class="innerListElements"><a id="createFeedBackRequest">Solicit Feedback</a></li>
				<li class="innerListElements"><a id="getEmployeeHistory"  >Employee History</a></li>
				<!-- <li class="innerListElements"><a id="createReviewCycle">Create ReviewCycle</a></li> -->
				<!-- <li class="innerListElements"><a href="/hrms/review/admin/activeCompetencies.html">Competencies</a></li> -->
				<!-- <li class="innerListElements"><a id="feedbackQuestionnaire">Feedback Questionnaire </a></li> -->
				<!-- <li class="innerListElements"><a id="reviewCycleStatus">Review Cycle Status</a></li> -->
			</ul>
		</li>
		<li class="outerListElements"><a href="#">Configuration</a>
			<ul class="header-submenu-list">
				<li class="innerListElements" id="reminder_messages"><a href="#">Reminder Messages</a>
					<ul>
						<li id="halfyearly"><a id="Half-Yearly">Halfyearly-appraisal</a></li>
	    				<li id="annually"><a id="Annual">Annual-appraisal</a></li>
					</ul></li>
				<li class="innerListElements"><a id="addReviewCycle">Add Review Cycle</a>
				<li class="innerListElements"><a id="editCompetency">Configure Competencies</a></li>
				<!-- <li class="innerListElements"><a id="serverDetails">Edit Server Details</a></li> -->
			</ul>
		</li>
		<!-- <li class="outerListElements"><a id="orgChart"> Org Chart</a></li> -->
		<li class="outerListElements"><a id="mangementToolKit">Performance Mgt. Tool Kit</a></li>
		<li class="outerListElements"><a href="http://sangam.ggktech.com">Sangam Home</a></li>
		
		
				
				
		<!-- <li class="outerListElements"><a href="#">Reminder Messages</a>
			<ul class="header-submenu-list">
				<li class="innerListElements"><a id="half-yearly">Halfyearly-appraisal</a></li>
    			<li class="innerListElements"><a id="annual">Annual-appraisal</a></li>
			</ul>
		</li>
		<li class="outerListElements"><a id="addReviewCycle">Add Review Cycle</a>
   <ul class="header-submenu-list">
    <li class="innerListElements"><a id="H1ReviewCycle">H1-appraisal</a></li>
    <li class="innerListElements"><a id="H2ReviewCycle">H2-appraisal</a></li>
    <li class="innerListElements"><a id="AnnualReviewCycle">Annual-appraisal</a></li>
   </ul>
  </li>
		<li class="outerListElements"><a id="serverDetails">Edit Server Details</a></li>
		<li class="outerListElements"><a id="helpDocs">Help Documents</a></li> -->
	</ul>
	<div id="logout" align="right" style="padding-right: 10px; color:#85EB85;">
		<sec:authentication property="principal.fullName" />&nbsp;<sec:authentication property="principal.grade" />
		<a href="${root}authentication/mail.html" style="color: #ccc;"><font size="2px;">SignOut</font></a>
	</div>
</div>