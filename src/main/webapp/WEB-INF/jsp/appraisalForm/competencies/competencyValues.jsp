<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<style type="text/css">
.dataTable {
	white-space: normal !important;
	word-wrap: break-word;
	word-break: break-all;
	table-layout: auto;
}

.dataTable td {
	text-transform: capitalize;
	font-family: Trebuchet MS, Gill Sans MT, Verdana;
	font-size: 13px;
	text-align: left;
	height: 30px;
}

#competancyValues {
	margin: 20px;
}

#tableHeading {
	font-size: 17px;
}
</style>

<script type="text/javascript">
var   oTable ;
$(document).ready(function(){
	
	$('#competencyDataTable').dataTable(
			{
			"bPaginate": false,
			"bSort" : false,
			"bFilter":false,
			"bInfo":false
			}
	);
	$("#competencyDataTable").css('border', '1px solid #BEBEBE');
});
</script>

<table id="competencyDataTable" class="dataTable" style="width: 300px;">
	<thead>
		<tr>
			<th width=14%>Grade</th>
			<th width=15%>Proficiency Level</th>

		</tr>
	</thead>
	<tbody>
		<c:forEach items="${competencyForm.gradeProficiencyVOs}" var="values"
			varStatus="status">
			<tr>
				<td><form:label
						path="competencyForm.gradeProficiencyVOs[${ status.index}].gradeName">${competencyForm.gradeProficiencyVOs[status.index].gradeName }</form:label>
					<form:hidden
						path="competencyForm.gradeProficiencyVOs[${ status.index}].gradeId" />
				</td>
				<td><form:select class="proficiencyDropdown"
						path="competencyForm.gradeProficiencyVOs[${ status.index}].expectedProficiencyLevelAsPerGrade">
						<form:option value="-1">Select</form:option>
						<form:options items="${competencyForm.proficiencyLevels}"></form:options>
					</form:select> <form:hidden
						path="competencyForm.gradeProficiencyVOs[${ status.index}].gradeCompetencyExpectationId" />
				</td>
			</tr>

		</c:forEach>
	</tbody>
</table>

<br>
<br>
