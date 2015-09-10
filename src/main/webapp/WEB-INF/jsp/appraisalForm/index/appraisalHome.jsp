<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<link href="${root}common/css/v2/reviewFormIndex.css" rel="stylesheet"
	type="text/css" /><link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
#appraisalIndexHome {
	margin: 20px;
}
</style>
<script type="text/javascript" src="${root}common/js/v2/indexForm.js"></script>
<script type="text/javascript">
 $(document).ready(function(){
	 jQuery.ajaxSetup({ cache:true});
/*  var selfOrPeers='${selfOrPeers}';
 if(selfOrPeers=='self'){
   getAppraisalIndex(${root},0, "pending", "self");}
 else if(selfOrPeers=='peers'){
  getAppraisalIndex(${root},0, "all","peers");
 }else if(selfOrPeers=='all'){
  getAppraisalIndex(${root},0, "all","all");
 }
 else if(selfOrPeers=='shared'){
  getAppraisalIndex(${root},0, "all","shared")
 } */
}); 
 $("#downloadExcelByManagers").click(function(){
  var reviewCycleId = $("#reviewCycleDropDown option:selected").val();
  if (reviewCycleId == -1){
	  
	  setDialog("Please select a Review Cycle.","alert");
	    $( "#alert" ).dialog( "open" ); 
	    return;
  }
  var url = ${root}+"appraisal/generateReviewCycleExcelSheet.html?reviewCycleId="+reviewCycleId;
  window.open(url, "");
 });
 
$("#downloadExcelByProjects").click(function(){
	  var reviewCycleId = $("#reviewCycleDropDown option:selected").val();
	  if (reviewCycleId == -1){
		  
		  setDialog("Please select a Review Cycle.","alert");
		    $( "#alert" ).dialog( "open" ); 
		    return;
	  }
	  var url = ${root}+"appraisal/generateReviewCycleExcelSheetByProjects.html?reviewCycleId="+reviewCycleId;
	  window.open(url, "");
	 });
	 
$("#downloadSingleExcelByManagers").click(function(){
	  var reviewCycleId = $("#reviewCycleDropDown option:selected").val();
	  if (reviewCycleId == -1){
		  
		  setDialog("Please select a Review Cycle.","alert");
		    $( "#alert" ).dialog( "open" ); 
		    return;
	  }
	  var url = ${root}+"appraisal/generateReviewCycleSingleExcelSheet.html?reviewCycleId="+reviewCycleId;
	  window.open(url, "");
	 });

 
 function openReview(reviewHeaderId, isReady, loginRole){
  if(isReady == "true"){
   getReviewForm(${root}, reviewHeaderId)
  } else {
   if(loginRole == 'APPRAISE') {
    setDialog("You can not open this appraisal until your manager reassigns to you","alert");
    $( "#alert" ).dialog( "open" ); 
   }
   if(loginRole == 'APPRAISER' || loginRole == 'SHARED_APPRAISER' || loginRole == 'SUPERUSER') {
    setDialog("You can not open this appraisal until the appraise submits","alert");
    $( "#alert" ).dialog( "open" ); 
   } else if(loginRole == 'SUPERUSER'){
    setDialog("You can not open this appraisal","alert");
    $( "#alert" ).dialog( "open" ); 
   }
  }
 }
//Added this to facilitate superuser to download appraisal
 $("#downloadPdfs").click(function(){
	  var reviewCycleId = $("#reviewCycleDropDown option:selected").val();
	  if (reviewCycleId == -1){
		  
		  setDialog("Please select a Review Cycle.","alert");
		    $( "#alert" ).dialog( "open" ); 
		    return;
	  }
	  

			$("input:checkbox.appraisal:hidden").prop("checked", false);
			//var n = $( "input:checkbox.appraisal:checked:visible" ).length;
			var anSelected = fnGetSelected(oTable);
			if(anSelected.length < 1) {
				setDialog("Select the appraisal(s) for PDF download.","alert");
				$( "#alert" ).dialog( "open" );
				return false;
			}
			var reviewHeaderList = [];
			for(var i=0; i<anSelected.length; i++) {
				var aData = oTable.fnGetData( anSelected[i] );
				var headerId = aData["reviewHeaderId"];
				reviewHeaderList.push(headerId);
			}
	  var url = ${root}+"appraisal/downloadPDFs.html?reviewHeaderIds="+reviewHeaderList;
	  window.open(url, "");
	 });
 
</script>
<div id="appraisalIndexHome">
	<div id="pageLocation"></div>

	<!--  <center> -->
	<div id="content" align="center">
		<c:choose>
			<c:when
				test="${selfOrPeers=='peers' or selfOrPeers=='all' or selfOrPeers=='shared'}">
				<div id="reviewCycleDropDownDiv">
					<b>Appraisals for review cycle:</b> <select
						id="reviewCycleDropDown">
						<option value="-1" selected="selected">Select</option>
						<c:forEach items="${reviewCycleDropDownItems}"
							var="reviewCycleTemp">
								<option id="${reviewCycleTemp.id}" value="${reviewCycleTemp.id}">${reviewCycleTemp.value}</option>
						</c:forEach>
					</select>
				</div>
			</c:when>
			<c:when test="${selfOrPeers=='self'}">
				<div id="pendingDropDownDiv">
					<b>Select the option:</b> <select id="pendingDropDown">
					    <option value="-1" selected="selected">Select</option>
						<option value="pending">Pending</option>
						<option value="completed">All</option>
					</select>
				</div>
			</c:when>
			<c:otherwise>
     Not authorized user
    </c:otherwise>
		</c:choose>
		<br/>
		<div class="dataTables_paginate paging_full_numbers" id="submitrow1" style="float: right;">
			<input id="tableSearch" placeholder="Search" type="text" /> <a class="paginate_button" id="searchBtn">Search</a>
		</div>
		<c:if test="${isSuperUser}">
		<!--  Added this to facilitate superuser to download appraisal -->
		<div id="submitrow"  style="float: right; margin-right: 50px;">
			<a class="tooltip" id="downloadPdfs"><span>Download PDFs</span></a><br>
		</div>
		<!--  Added this to facilitate superuser to download appraisal -->
		<div id="submitrow"  style="float: right; margin-right: 50px;">
			<a class="tooltip" id="downloadExcelByManagers"><span>Download Excel By Mgr</span></a><br>
		</div>
		<div id="submitrow"  style="float: right; margin-right: 50px;">
			<a class="tooltip" id="downloadExcelByProjects"><span>Download Excel By Project</span></a><br>
		</div>
		<div id="submitrow"  style="float: right; margin-right: 50px;">
			<a class="tooltip" id="downloadSingleExcelByManagers"><span>Download Excel</span></a><br>
		</div>
		
		</c:if>
		<br/>
		<div id="appraisalIndex">
			<jsp:include page="./appraisalIndex.jsp" />
		</div>
	</div>
	<!-- </center> -->
</div>