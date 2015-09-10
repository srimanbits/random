<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Competency</title>
<style type="text/css">
.competency {
	width: 750px;
	height: 500px;
	border-top: 2px solid #336699;
	border-bottom: 2px solid #336699;
	overflow: auto;
}

textarea {
	width: 97%;
	height: 130px;
	overflow: auto;
}

.NavigationButtons {
	float: left;
	margin-left: 5px;
	margin-right: 5px;
	width: 20px;
	height: 20px;
	background-color: #BDBDBD;
	text-align: center;
	border-radius: 10px;
}

.navigation {
	float: left;
	margin-left: 400px;
	margin-top: 10px;
	font-size: small;
}
.show{
display: block;
color: red;

}
.hide{

display: none; 

}
.TextField
{

font-size: small;

}
</style>
<script type="text/javascript">
	function initialShow() {
		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;

		for ( var i = 0, ii = nagivationButtons.length; i < ii; i++) {

			if (nagivationButtons[i].id == "n1") {

				var buttonToShow = nagivationButtons[i].id;
				reviewCompetencyToShow = document.getElementById("PL"
						+ buttonToShow.substring(1));
				reviewCompetencyToShow.style.display = 'inline';
				var navigationButtonToHighLight = document
						.getElementById(buttonToShow);
				navigationButtonToHighLight.style.backgroundColor = 'green';

			}

		}

	}
	function showCompetency(navigationButton) {

		var navigationButtonid = navigationButton.id;
		reviewCompetencyIdToShow = navigationButtonid.substring(1);
		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;

		for ( var i = 0, ii = nagivationButtons.length; i < ii; i++) {
			if (nagivationButtons[i].nodeName == 'DIV') {
				if (nagivationButtons[i].id.substring(1) == reviewCompetencyIdToShow) {

					var buttonToShow = nagivationButtons[i].id;
					reviewCompetencyToShow = document.getElementById("PL"
							+ buttonToShow.substring(1));
					reviewCompetencyToShow.style.display = 'inline';
					var navigationButtonToHighLight = document
							.getElementById(buttonToShow);
					navigationButtonToHighLight.style.backgroundColor = 'green';
				}

				else {

					var buttonToHide = nagivationButtons[i].id;
					reviewCompetencyToHide = document.getElementById("PL"
							+ buttonToHide.substring(1));
					reviewCompetencyToHide.style.display = 'none';
					var navigationButtonToNormal = document
							.getElementById(buttonToHide);
					navigationButtonToNormal.style.backgroundColor = '#BDBDBD';

				}

			}

		}
	}
	function colorIt(element) {

		var buttonToColor = document.getElementById(element.id)
		if (buttonToColor.style.backgroundColor != 'green') {
			buttonToColor.style.backgroundColor = "orange";
		}

	}
	function fadeIt(element) {

		var buttonToColor = document.getElementById(element.id)
		if (buttonToColor.style.backgroundColor != 'green') {
			buttonToColor.style.backgroundColor = "#BDBDBD";//#BDBDBD
		}

	}
	function changeCompetencyName(textbox) {

		Textbox = document.getElementById(textbox.id);
		head = document.getElementById("h");
		head.innerHTML = Textbox.value;

	}

	$(document).ready(function() {
		initialShow();
		$("#saveButton").click(function() {

			var value = true;
			var initialCompetencyId=$("#initialCompetencyId").val();
			$(".TextFieldContent").each(

			function() {
				if ($.trim($(this).val()) == "") {
					value = false;
				}
			})
			if (value == false) {

				alert("Some fields are left Empty..Please Fill and Submit");
			}
			if (value == true && ($.trim($("#initialCompetencyName").val())!=$.trim($("#currnetCompetencyname").val()))) {	
				$.ajax ( {
				      url : "checkForDupes.html?competencyName="+$("#currnetCompetencyname").val(),
				    		  async:false,
				    		  cache:false,
				        success:function(data){
				       if(data=="yes"){
				    	   $("#errorMessage").removeClass("hide").addClass("show");
				    	  value= false;			    	  				    	   
				    	   }
				       if(data=="no"){
				    	   
				    	   value=false;
				    	   // start			    	   
				    	   $.ajax ( {
				                url : "saveCompetency.html",
				                type:"POST",
				                data:$('#competencyVO').serialize(), 
				    		    async:false,
				    		    cache:false,
				                success:function(data){
				        	$("#competencyDialog").dialog("close");
				        	$("#MasterCompetency"+initialCompetencyId).hide();
				        	$(".CompetencyForm").append(data);
				        				       
				        }				  
				     });
				    	   
				    	   
				    	   // end 
				    	   
				       }
				       
				        }				  
				     });

			}
			return value;
		})

	});
</script>
</head>
<body>
	<form:form modelAttribute="competencyVO">
		<h5 id="h">${competencyVO.competencyName}</h5>
		<input type="hidden" id="initialCompetencyName" value="${competencyVO.competencyName}"/>
		<div class="competency">
			<div class="competencyDetails" style="margin-top: 10px;">
				<div class="competencyName">
					<div>
						<form:label path="">
							<h6>Competency Name</h6>
						</form:label>
					</div>
					<div style="margin-top: 10px;" class="TextField">
						<form:input path="competencyName" disabled="false" size="40"
							cssClass="TextFieldContent" id="currnetCompetencyname" onkeyup="changeCompetencyName(this)"
							/>
							<div class="hide" id="errorMessage">Another Competency Exist With Same name..Choose Another Name</div>
					</div>
					<form:hidden path="competencyId" id="initialCompetencyId"/>
				</div>
				<div class="competencyDefinition" style="margin-top: 10px;">
					<div>
						<form:label path="">
							<h6>Competency Definition</h6>
						</form:label>
					</div>
					<div style="margin-top: 10px;" Class="TextField">
						<form:textarea path="definition" disabled="false"
							cssClass="TextFieldContent"/>
					</div>
				</div>
			</div>
			<h5 style="margin-top: 10px;">Proficiency Level</h5>
			<div class="P-Levels" style="margin-top: 20px;">
				<c:forEach items="${competencyVO.proficiencyLevelCompetencyVOs}"
					var="proficiencyLevelCompetencyVO" varStatus="status">
					<div class="P-Level" style="margin-top: 20px; display: none;"
						id="PL${status.count}">
						<div class="PL-Name" style="width: 350px;">
							<div style="float: left; width: 175px;">
								<form:label path=""><h6>Proficiency Level Name</h6></form:label>
							</div>
							<div style="margin-left: 50px;" Class="TextField">
								<form:label
									path="proficiencyLevelCompetencyVOs[${status.index}].proficiencyLevelVO.proficiencyLevelName">
									: ${proficiencyLevelCompetencyVO.proficiencyLevelVO.proficiencyLevelName}</form:label>
							</div>
							<form:hidden
								path="proficiencyLevelCompetencyVOs[${status.index}].proficiencyLevelVO.proficiencyLevelId" />
							<form:hidden
								path="proficiencyLevelCompetencyVOs[${status.index}].proficiencyLevelCompetencyId" />
						</div>

						<div class="PL-Number" style="width: 350px; margin-top: 10px;">
							<div style="float: left; width: 175px;">
								<form:label path=""><h6>Proficiency Level Number</h6></form:label>
							</div>
							<div style="margin-left: 150px;" Class="TextField">
								<form:label
									path="proficiencyLevelCompetencyVOs[${status.index}].proficiencyLevelVO.proficiencyLevelNumber">: ${proficiencyLevelCompetencyVO.proficiencyLevelVO.proficiencyLevelNumber}</form:label>
							</div>
						</div>
						<div style="margin-top: 10px;" Class="TextField">
							<form:label path=""><h6 class="TextField">Behavior Indicator :</h6></form:label>

							<form:textarea style="margin-top: 10px; height:75px;"
								path="proficiencyLevelCompetencyVOs[${status.index}].behaviorIndicator"
								disabled="false" cssClass="TextFieldContent"/>


						</div>

					</div>

				</c:forEach>
			</div>
		</div>

		<input type="button" value="Save" id="saveButton" style="width: 50px; height: 35px;"/>
		<div style="float: left" style="margin-left:170px;margin-top: 10px;"
			class="navigation" id="navigation">
			<c:forEach items="${competencyVO.proficiencyLevelCompetencyVOs}"
				var="proficiencyLevelCompetencyVO" varStatus="status">

				<div class="NavigationButtons" onclick="showCompetency(this)"
					id="n${status.count}" onmouseover="colorIt(this)"
					onmouseout="fadeIt(this)">${status.count}</div>

			</c:forEach>
		</div>
	</form:form>

</body>
</html>