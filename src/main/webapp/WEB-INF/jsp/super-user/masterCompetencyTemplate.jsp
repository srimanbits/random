<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Competency Template</title>
</head>
<body>

	<div class="MasterCompetency"
		style="margin-top: 20px; width: 700px; height: 25px;"
		id="MasterCompetency${competencyVO.competencyId}">
		<div style="float: left;">
			<a href="#" class="CompetencyLinks"
				id="C${competencyVO.competencyId}"
				onclick="editCompetency(this.id); return false;"
				>${competencyVO.competencyName}</a>
		</div>
		<div style="margin-left: 500px;">
			<input type="button" value="MakeInActive" style="margin-left: 50px;"
				id="${competencyVO.competencyId}" class="activatingButtons" onclick="makeInActive(this.id);"/>
		</div>
	</div>

</body>
</html>