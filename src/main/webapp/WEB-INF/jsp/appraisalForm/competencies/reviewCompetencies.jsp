<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/reviewCompetencies.css" />
<script type="text/javascript"
	src="${root}common/js/v2/reviewCompetencies.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		 jQuery.ajaxSetup({ cache:true});
		redirectToIndex('${reviewFormInfoVO.actualReviewStatus}','${reviewFormInfoVO.reviewFormRole}','${reviewFormInfoVO.reviewPhase.description}');
		$("#reviewStatusId").html("Status : "+'${reviewFormInfoVO.reviewStatus}');
		//$("h2:first").addClass("active").next("div").slideToggle("slow");
		applyBanners();
		$(".CompetenciesForm h2").on("click", function() {
			$(this).next("div").slideToggle("slow");
			//.siblings("div:visible").slideUp("slow")
			$(this).toggleClass("active");
			//$(this).siblings("h2").removeClass("active");
		});

		$("form :input").on('change', function() {
			$('#isEdited').val('yes');
		});

		$("#saveCompetencesButton").click(function() {
			$('#main-out').block({
				message : 'Saving Please Wait....'
			});
			saveCompetencies('${root}');
			$('#isEdited').val('no');

		});

		$("#competencyOptions .expandAll").live("click", function() {

			$(".CompetenciesForm h2").each(function() {
				if (!$(this).hasClass("active")) {
					$(this).next("div").slideDown("slow");
					$(this).toggleClass("active");
				}

			});

		});

		$("#competencyOptions .collapseAll").live("click", function() {
			$(".CompetenciesForm h2").each(function() {
				if ($(this).hasClass("active")) {
					$(this).next("div").slideUp("slow");
					$(this).removeClass("active");
				}

			});

		});

		$(".competencyRating").on("change", function() {

			changeCompetencyRating($(this).attr("id"));

		});
		
		$(".ExpctdProficiencyLvl").on("change", function() {

			changeExpectedProficiencyLevel($(this).attr("id"));

		});
		
		$(".ExpctdProficiencyLvl").on("change",function(){
			var id=$(this).attr("id");
			var competencyId=id.substr(3);
			if($("#" + id + " option:selected").text()!='Select'){
			var proficiencyLevelId=$("#" + id + " option:selected").text().substring(0,1);
			
			$.ajax({
				
				url:${root}+"appraisal/getBehavioralIndicator.html",
				type:"POST",
				cache: false,
				data: {competencyId :competencyId,proficiencyLevelId:proficiencyLevelId,reviewHeaderId:'${reviewFormInfoVO.reviewHeaderId}'},
				success:function(data){	
					$("#BI"+competencyId).text(data);
				},
				error: function(xmlHttpRequest, textStatus, errorThrown) { 
		          	  setDialog(errorThrown,"alert");
					$( "#alert" ).dialog( "open" );
		            }
				
				
				
			});
		 
			}
			
			else {
				
				$("#BI"+competencyId).text("No Proficiency level selected.");
			}
		});

	});

	function applyBanners() {
		$(".competencyRating").each(function() {
			changeCompetencyRating($(this).attr("id"));
			

		});
		
		$(".ExpctdProficiencyLvl").each(function() {
			changeExpectedProficiencyLevel($(this).attr("id"));
			

		});
		

	}
	
	function changeCompetencyRating(id){
		
			var value = $("#" + id + " option:selected").html();
				if (id.substr(0,8) == 'appraise') {

				if (value != "Select") {
					$("#h2Banner"+id.substr(14)+"> div:nth-child(4)").text("Appraise Rating : " + $.trim(value.split("-")[0]));
					$("#h2Banner"+id.substr(14)+"> div:nth-child(4)").show();
				}
				if (value == "Select") {
					$("#h2Banner"+id.substr(14)+"> div:nth-child(4)").hide();
				}

			}

				if (id.substr(0,8) == 'appraisr'){

				if (value != "Select") {
					$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").text("Appraiser Rating : " + $.trim(value.split("-")[0]));
					$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").show();
				}
				if (value == "Select") {
					$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").hide();
				}

			}
			
			
		
	};
	
	function changeExpectedProficiencyLevel(id){
		
		var value = $("#" + id + " option:selected").html();
		if (value != "Select") {
			$("#h2Banner"+id.substr(3)+"> div:nth-child(2)").text(value);
			$("#h2Banner"+id.substr(3)+"> div:nth-child(2)").show();
		}
		if (value == "Select") {
			$("#h2Banner"+id.substr(3)+"> div:nth-child(2)").hide();
		}
		
		
	};
</script>
<input type="hidden" id="isEdited" name="isEdited" value="no" />
<c:set var="enableAppraiseSection" value="false" />
<c:set var="showAppraiseSection" value="false" />
<c:set var="enableAppraiserSection" value="false" />
<c:set var="showAppraiserSection" value="false" />
<c:set var="renderSaveButton" value="false" />
<c:set var="enableExpectedProficiencyLevelSetting" value="false"/>
<c:if test="${reviewFormInfoVO.reviewFormRole eq 'APPRAISE'}">
<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISE_IN_PHASE1' and reviewFormInfoVO.actualReviewStatus!='GOALS_FINALIZED'}">
	<c:set var="renderSaveButton" value="true" />
	 <c:set var="enableExpectedProficiencyLevelSetting" value="true"/> 
</c:if>
<c:if
	test="${(reviewFormInfoVO.reviewPhase.description eq 'APPRAISE_IN_PHASE2' or reviewFormInfoVO.reviewPhase.description eq 'SYSTEM_PHASE1_COMPLETED') and reviewFormInfoVO.actualReviewStatus!='READY_FOR_MEETING'}">

	<c:set var="enableAppraiseSection" value="true" />
	<c:set var="showAppraiseSection" value="true" />
	<c:set var="renderSaveButton" value="true" />
</c:if>

<c:if
	test="${ reviewFormInfoVO.reviewPhase.description eq 'APPRAISE_IN_PHASE2' and reviewFormInfoVO.actualReviewStatus eq 'READY_FOR_MEETING'}">

	<c:set var="showAppraiseSection" value="true" />
	<c:set var="showAppraiserSection" value="true" />
</c:if>

<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISER_IN_PHASE2'}">

	<c:set var="showAppraiseSection" value="true" />
</c:if>
</c:if>
<c:if test="${(reviewFormInfoVO.reviewFormRole eq 'APPRAISER' or reviewFormInfoVO.reviewFormRole eq 'SHARED_APPRAISER' or reviewFormInfoVO.reviewFormRole eq 'SUPERUSER')}">
<c:if 
	test="${reviewFormInfoVO.reviewPhase.description eq 'SYSTEM_PHASE1_COMPLETED'}">

	<!-- Nothing to display.. Manager will be displayed with some message.. -->
	<%-- <c:set var="showAppraiseSection" value="false" /> --%>

</c:if>

<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISE_IN_PHASE2' and (reviewFormInfoVO.actualReviewStatus eq 'NEED_TO_EDIT_APPRAISAL' || reviewFormInfoVO.actualReviewStatus=='READY_FOR_MEETING') }">

	<!-- Nothing to display.. Manager will be displayed with some message.. -->
	<c:set var="showAppraiseSection" value="true" />
	<c:set var="showAppraiserSection" value="true" />
</c:if>

<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISER_IN_PHASE2'}">

	<c:set var="showAppraiseSection" value="true" />
	<c:set var="enableAppraiserSection" value="true" />
	<c:set var="showAppraiserSection" value="true" />
	<!-- 	<c:set var="renderSaveButton" value="true" /> -->

</c:if>

<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISER_IN_PHASE1'}">
	<c:set var="renderSaveButton" value="true" />
	<c:set var="enableExpectedProficiencyLevelSetting" value="true"/>
</c:if>
<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'APPRAISE_IN_PHASE1'}">
	<c:set var="enableExpectedProficiencyLevelSetting" value="false"/>
</c:if>
</c:if>
<c:if
	test="${reviewFormInfoVO.reviewPhase.description eq 'SYSTEM_PHASE2_COMPLETED'}">

	<c:set var="showAppraiseSection" value="true" />
	<c:set var="showAppraiserSection" value="true" />
</c:if>
<form:form method="post" commandName="competencyForm">
	<input type="hidden" name="reviewHeaderId"
		value="${reviewFormInfoVO.reviewHeaderId}" />

	<div style="width: 764px; height: 10px; margin-bottom: 30px;"
		id="competencyOptions">
		<div style="float: left; margin-right: 10px;">
			<c:if test="${renderSaveButton}">
				<a id="saveCompetencesButton" class="tooltip"
					title="Save competencies" onclick="saveOrNoSave(this.id)"><span>Save
						All</span></a>
			</c:if>
		</div>
		<div style="float: right; margin-left: 10px;">
			<img alt="Collapse All" src="${root}common/css/images/uparrow_1.png"
				class="collapseAll" title="Collapse All">
		</div>
		<div style="float: right; margin-left: 10px;">
			<img alt="esss" src="${root}common/css/images/downarrow_1.png"
				class="expandAll" title="Expand All">
		</div>
	</div>
	<c:if test="${competencyForm.reviewCompetencyVOs!=null}">
		<div id="competencies_container">
			<div class="CompetenciesForm">
				<c:forEach items="${competencyForm.reviewCompetencyVOs}"
					var="reviewCompetencyVO" varStatus="status">
					<form:hidden
						path="reviewCompetencyVOs[${status.index}].reviewCompetencyId" />
					<form:hidden
						path="reviewCompetencyVOs[${status.index}].competencyName" />
					<h2 id="h2Banner${reviewCompetencyVO.competencyId}">
						<div
							style="text-decoration: underline; width: 300px !important; float: left;">${reviewCompetencyVO.competencyName}</div>
						<div style="margin-right: 20px; float: left; width: 125px;"></div>
						<div style="margin-right: 20px; float: left; width: 125px;"></div>
						<div style="margin-right: 25px; float: right; width: 125px;"></div>
					</h2>
					<div
						style="display: none; background-color: rgba(11, 159, 245, 0.21); width: 764px;">
						<div class="CompetencyDefinition"
							id="reviewCompetencyVO.competencyVO.definition">${reviewCompetencyVO.competencyDefinition}</div>
						<div class="ExpectedBehavior" style="margin-top: 10px;">
							<div>
								<div class="CommentsHeader" style="margin-right: 10px;">Expected
									Proficiency Level:
								</div>
								<div style="margin-right: 20px; float: left; width: 125px;">
									<form:select class="ExpctdProficiencyLvlAsPerGrade"
										path="reviewCompetencyVOs[${status.index}].expectedProficiencyLevelIdAsPerGrade"
										disabled="true">
										<form:option value="-1">Select</form:option>
										<form:options items="${competencyForm.proficiencyLevels}"></form:options>
									</form:select>
								</div>
								<div class="CommentsHeader" style="margin-right: 10px;margin-left: 100px;">Selected
									Proficiency Level:
								</div>
								<div>
									<form:select class="ExpctdProficiencyLvl"
										path="reviewCompetencyVOs[${status.index}].expectedProficiencyLevelId"
										id="EPL${reviewCompetencyVO.competencyId}" disabled="${!enableExpectedProficiencyLevelSetting}">
										<form:option value="-1">Select</form:option>
										<form:options items="${competencyForm.proficiencyLevels}"></form:options>
									</form:select>
								</div>
							</div>
							<div style="margin-top: 5px;">
								<div class="CommentsHeader" style="margin-bottom: 10px;">Behavioral Indicator:</div>
								<div>
									<form:textarea
										id="BI${reviewCompetencyVO.competencyId}"
										path="reviewCompetencyVOs[${status.index}].expectedBehavioralIndicator"
										class="readOnlyTextareaClass" readonly="true" onkeydown="preventBackspace()" style="height: 60px;overflow: auto;"/>
								</div>
							</div>
						</div>

						<c:if test="${showAppraiseSection}">
							<div class="appraiseSection">
								<div>
									<div class="CommentsHeader">Appraise Comments</div>
									<!--  ratings section begin -->
									<div class="RatingHeader">Performance Rating:</div>
									<div class="RatingDropDown">
										<form:select class="competencyRating"
											path="reviewCompetencyVOs[${status.index}].appraiseRatingId"
											disabled="${!enableAppraiseSection}"
											id="appraiseRating${reviewCompetencyVO.competencyId}">
											<form:option value="-1">Select</form:option>
											<form:options items="${competencyForm.ratings}"></form:options>
										</form:select>
									</div>

									<!--  ratings section end -->
								</div>
								<div>
									<c:choose>
										<c:when test="${!enableAppraiseSection}">
											<form:textarea
												id="reviewCompetencyVOs[${status.index}].appraiseCommentText"
												path="reviewCompetencyVOs[${status.index}].appraiseCommentText"
												readonly="true" onkeydown="preventBackspace()"
												class="readOnlyTextareaClass" />
										</c:when>
										<c:otherwise>
											<form:textarea
												id="reviewCompetencyVOs[${status.index}].appraiseCommentText"
												path="reviewCompetencyVOs[${status.index}].appraiseCommentText" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</c:if>
						<c:if test="${showAppraiserSection}">
							<div class="appraiserSection">
								<div>
									<div class="CommentsHeader">Appraiser Comments</div>
									<!--  ratings section begin -->
									<div class="RatingHeader">Performance Rating:</div>
									<div class="RatingDropDown">
										<form:select class="competencyRating"
											path="reviewCompetencyVOs[${status.index}].appraiserRatingId"
											disabled="${!enableAppraiserSection}"
											id="appraisrRating${reviewCompetencyVO.competencyId}">
											<form:option value="-1">Select</form:option>
											<form:options items="${competencyForm.ratings}"></form:options>
										</form:select>
									</div>
									<!--  ratings section end -->
								</div>
								<div>
									<c:choose>
										<c:when test="${!enableAppraiserSection}">
											<form:textarea
												id="reviewCompetencyVOs[${status.index}].appraiserCommentText"
												path="reviewCompetencyVOs[${status.index}].appraiserCommentText"
												readonly="true" onkeydown="preventBackspace()"
												class="readOnlyTextareaClass" />
										</c:when>
										<c:otherwise>
											<form:textarea
												id="reviewCompetencyVOs[${status.index}].appraiserCommentText"
												path="reviewCompetencyVOs[${status.index}].appraiserCommentText" />
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
		</div>
	</c:if>
</form:form>
