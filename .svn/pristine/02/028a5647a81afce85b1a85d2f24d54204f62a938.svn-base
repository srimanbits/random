<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="root" value="/"></c:url>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CompetencyForm</title>
<style type="text/css">
.MasterCompetency{

background-color: rgb(229, 229, 229);
margin-top: 20px; 
width: 700px; 
height: 20px;

}
</style>
<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("a.CompetencyLinks").click(function(event) {
			event.preventDefault();
			editCompetency($(this).attr("id"));

		});

		$("#createCompetencyButton").click(function() {
			$.ajax({
				url : "createNewCompetency.html",
				async : false,
				cache : false,
				success : function(data) {					
					$("#competencyDialog").html(data);
					$("#competencyDialog").dialog({
						width : 800,
						height : 650,
						modal : true,
						draggable : false,
						resizable : false
					}

					);

				}
			});

		});

		$(".activatingButtons").click(function(event) {
			var buttonIdToMakeInactive = $(this).attr("id");
			makeInActive(buttonIdToMakeInactive);

		});

	})

	function editCompetency(competencyId) {

		$.ajax({
			url : "editCompetency.html?competencyId=" + competencyId,
			async : false,
			cache : false,
			success : function(data) {
				$("#competencyDialog").html(data);
				$("#competencyDialog").dialog({
					width : 800,
					height : 650,
					modal : true,
					draggable : false,
					resizable : false
				}

				);

			}
		});
		return false;

	}

	function makeInActive(buttonIdToMakeInactive) {
		$.ajax({
			url : "InactiveCompetency.html?competencyId="
					+ buttonIdToMakeInactive,
			async : false,
			cache : false,
			success : function(data) {
				if (data == "yes") {
					$("#MasterCompetency" + buttonIdToMakeInactive).hide();
				}
			}
		});
		return true;

	}
</script>

</head>
<body>
<center>
	<form:form commandName="competencyForm" id="competencyForm">
		<div class="CompetencyForm">

			<c:forEach items="${competencyForm.competencyVOs}" var="competencyVO"
				varStatus="status">

				<div class="competencyFormHeader">
					<c:if test="${status.count eq 1}">
						<div class="CompetencyName" >Competencies</div>
					</c:if>
				</div>
				<div class="MasterCompetency"
					style="margin-top: 20px; width: 700px; height: 25px;"
					id="MasterCompetency${competencyVO.competencyId}">
					<div style="float: left;">
						<a href="" class="CompetencyLinks"
							id="C${competencyVO.competencyId}">${competencyVO.competencyName}</a>
					</div>
					<div style="margin-left: 500px;">
						<input type="button" value="MakeInActive"
							style="margin-left: 50px;" id="${competencyVO.competencyId}"
							class="activatingButtons" />
					</div>
				</div>
			</c:forEach>

		</div>

		<input type="button" value="Add Competency"
			id="createCompetencyButton" style="margin-top: 20px;" />
	</form:form>

	<div class="dialogClass" id="competencyDialog" style="display: none;"
		title="Add/Edit Competency"></div>

</center>
</body>
</html>