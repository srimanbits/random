<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<link href="${root}common/css/v2/reviewSummary.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet"
	type="text/css" />
<link href="${root}common/css/v2/developmentPlanForm.css"
	rel="stylesheet" type="text/css" />
	
<script type="text/javascript" src="${root}common/js/v2/configureCompetencies.js"></script>


<style type="text/css">
#competenciesDiv {
	margin: 20px;
}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		jQuery.ajaxSetup({
			cache : true
		});

	});


	$(".competencyDropdown").on("change", function() {
		
		var selectedValue = $('option:selected', this).attr('value');
		
		var deptId = $(".deptDropdown option:selected").val();
      if (selectedValue != -1 && deptId != -1 ){
			
			displayCompetencies('${root}')
		}
	});
	$(".deptDropdown").on("change", function() {
		var selectedValue = $('option:selected', this).attr('value');
		
		var competencyId = $(
		".competencyDropdown option:selected").val();
		if (selectedValue != -1 && competencyId != -1 ){
			
			displayCompetencies('${root}')
		}

	});

	$("#saveCompetencies")
			.click(
					function() {
						
						var competencyId = $(
						".competencyDropdown option:selected").val();
						var deptId = $(".deptDropdown option:selected").val();
						if (competencyId == -1 ){
							
							setDialog("Please select a competency.", "alert");
							$("#alert").dialog("open");
							return;
						}
						
						if(deptId == -1){
							setDialog("Please select a department.", "alert");
							$("#alert").dialog("open");
							return;
							
						}
							
							var competencyName = $(".competencyDropdown option:selected").text();
							var deptName = $(".deptDropdown option:selected").text();
							$("#confirm > p")
							.html(
									"Are you sure, want to save proficiencies for '"+deptName+"' against '"+competencyName+"'");
					 $("#confirm").dialog({
						autoOpen : true,
						width : 300,
						draggable : true,
						resizable : true,
						maxHeight : 400,
						modal : true,
						buttons : {
							"Yes" : function() {
								$(this).dialog("close");
								saveEditedProficiencies('${root}',$("#editCompetency"));
							},
							"Cancel" : function() {
								$(this).dialog("close");

							}
						}
					});
							
							
							//$('#isEdited').val('no');	
						

					});


	
</script>
<div id="competenciesDiv">
	<div id="pageLocation">Home > Configuration > Configure Competencies</div>

	<!--  <center> -->
	<div id="content" align="center">
		<form action="competencyForm" name="competencyForm"
			id="competencyForm">

			<b><label style="margin-right:90px;  margin-bottom: 5px; display: inline-block;">Competency</label><label style="margin-left: 80px">Department</label></b><br>
			<form:select class="competencyDropdown"
				path="competencyForm.competency" cssStyle="margin-left:25px;">
				<form:option value="-1">Select</form:option>
				<form:options items="${competencyForm.competencyList}"></form:options>
			</form:select>
			<form:hidden path="competencyForm.competency" name="previousCompetency" id="previouCompetency" />
			<form:select class="deptDropdown" path="competencyForm.dept" cssStyle="margin-left:30px;">
				<form:option value="-1">Select</form:option>
				<form:options items="${competencyForm.deptList}"></form:options>
			</form:select>
			<form:hidden path="competencyForm.dept" name="previousDept" id="previousDept" />
			 <div id="submitrow" style="float: right; margin-right: 50px;">
				<!-- <a class="tooltip" id="editCompetencies"><span>Edit
						Proficiencies</span></a><br> -->
			</div>



			<div id="competancyValues">
				<jsp:include page="./competencyValues.jsp" />
			</div>

			<div id="submitrow" style="float: right; margin-right: 50px;">
				<a class="tooltip" id="saveCompetencies"><span>Save
						Proficiencies</span></a><br>
			</div>
		</form>
	</div>
	<!-- </center> -->
</div>