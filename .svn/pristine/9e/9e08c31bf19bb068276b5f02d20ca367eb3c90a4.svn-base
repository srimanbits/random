<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<form:hidden path="defaultObjectiveVO.defaultObjectiveGrades[${index}].grade.grade" value="${gradeName}"/>
<div id="OG${gradeName}" style="display: none;" name="${gradeName}*">

	<div style="padding: 10px;">
		<div style="float: left;">
			<h4>Definition</h4>
		</div>
		<div id="Btn${gradeName}" value="X"
			width="50px;" onclick="deleteGrade(this.id)" class="DeleteButton">
			X</div>
	</div>
	<div style="margin-left: 10px;">
		<form:textarea
			path="defaultObjectiveVO.defaultObjectiveGrades[${index}].detailsComment.commentText"
			cssClass="TextAreaContent" />
	</div>
	<div style="padding: 10px;">
		<h4>Success Criteria</h4>
	</div>
	<div style="margin-left: 10px;">
		<form:textarea
			path="defaultObjectiveVO.defaultObjectiveGrades[${index}].successCriteria.commentText"
			cssClass="TextAreaContent" />
	</div>
</div>

