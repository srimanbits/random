<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Default Objectives</title>

<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$(".DefaultObjectiv").click(function(event){		
		event.preventDefault();		
		var defaultObjectiveIdToEdit=$(this).attr("id");		
		$.ajax({
			
			url:"getObjective.html?defaultObjectiveIdToEdit="+defaultObjectiveIdToEdit,
			async : false,
			cache : false,
			success:function(data){
					$("#ObjectiveDialog").html(data);
					$("#ObjectiveDialog").dialog({
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
	
	
});
	
</script>
</head>
<body>
	<form:form commandName="defaultObjectivesForm">

		<c:forEach items="${defaultObjectivesForm.defaultObjectives}"
			var="defaultObjective">

			<div class="DefaultObjective"
				style="margin-top: 20px; width: 700px; height: 25px;"
				id="DefaultObjective${defaultObjective.defaultObjectiveId}">
				<div style="float: left;">
					<a href="getObjective.html?defaultObjectiveIdToEdit=DefaultObjective${defaultObjective.defaultObjectiveId}" class="DefaultObjectiveLinks"
						id="C${defaultObjective.defaultObjectiveId}">${defaultObjective.defaultObjectiveName}</a>
				</div>
			</div>
<!-- getObjective.html?defaultObjectiveIdToEdit=DefaultObjective${defaultObjective.defaultObjectiveId} -->

		</c:forEach>




	</form:form>
	
		<div class="dialogClass" id="ObjectiveDialog" style="display: none;"
		title="Add/Edit Objective"></div>
</body>
</html>