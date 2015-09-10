<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<script type="text/javascript" src="${root}common/js/v2/headerMenu.js"></script>
<script type="text/javascript">
$(document).ready(function () { 
    $('#selfIndex').click(function() {
    	getAppraisalHome(${root},"self", this);

    });
    $('#sharedAppraisals').click(function() {
    	getAppraisalHome(${root},"shared", this);

    });
    $('#feedbackRequestsIn').click(function() {
    	getFeedbackRequestsIn(${root}, this);

    });
    
//     $('#orgChart').click(function() {
//     	alert("This will be implented later");

//     });
    $('#feedback').click(function() {
    	getSelfFeedbackTemplate(${root}, this);

    });
     
    $('#mangementToolKit').click(function() {
    	displayManagementToolKit(${root}, this);
    });
    
   /*  $('#helpDocs').click(function() {
    	displayHelpDocuments(${root}, this);
    }); */
    
});
</script>
<div id="header-menu">
	<ul id="header-menu-list">
		<li class="outerListElements"><a href="${root}welcome.html"> Home</a></li>
		<li class="outerListElements"><a href="#">Appraisal</a>
			<ul class="header-submenu-list">
				<li class="innerListElements"><a id="selfIndex">Self Appraisal</a></li>
				<li class="innerListElements"><a id="sharedAppraisals">Shared Appraisals</a></li>
				<li class="innerListElements"><a id="feedbackRequestsIn">Feedback Solicited</a></li>
				<li class="innerListElements"><a id="feedback">Give Feedback</a></li>
			</ul>
		</li>
		<!-- <li class="outerListElements"><a id="orgChart"> Org Chart</a></li> -->
		<li class="outerListElements"><a id="mangementToolKit">Performance Mgt. Tool Kit</a></li>
		<!-- <li class="outerListElements"><a id="helpDocs">Help Documents</a></li> -->
		<li class="outerListElements"><a href="http://sangam.ggktech.com">Sangam Home</a></li>
	</ul>
	<div id="logout" align="right" style="padding-right: 10px; color: #85EB85">
		<sec:authentication property="principal.fullName" />&nbsp;<sec:authentication property="principal.grade" />
		<a href="${root}authentication/mail.html" style="color: #ccc;"><font size="2px;">SignOut</font></a>
	</div>
</div>