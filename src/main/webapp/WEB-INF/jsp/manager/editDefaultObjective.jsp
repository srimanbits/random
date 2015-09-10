<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>
<script type="text/javascript">
var gradeElementsForSpring=0;

var appearingGradeElements=0;//appearingGradeElements

var	allActiveGradesonDB=0;


	$(document).ready(function() {
		
		gradeElementsForSpring=$(".NavigationButtons").length;
		appearingGradeElements=$(".NavigationButtons").length;
$.ajax({
			
			url:"getNoOfActiveGrades.html",
			async:false,
			cache:false,
			success:function(data){
				
				allActiveGradesonDB=data;
			}
			
			
		});
		initialShow();
		
		$("#BtnSaveObjective").click(function(){
			var value=true;
			$(".TextAreaContent").each(
					function() {						
						if ($.trim($(this).val()) == "") {
							value = false;
						}		
					})
					if (value == false) {

						alert("Some fields are left Empty..Please Fill and Submit");
					}
			
			if(value==true){
				
				$.ajax({
					url:"saveDefaultObjective.html",
					type:"POST",
					data:$("#defaultObjectiveVO").serialize(),
					async:false,
				    cache:false,
				    success:function(){
				    	
				      alert("returned from server :)");
				    }
					
					
					
				});
				
			}
		
			
		});
		
		$("#addGrade").click(function(){
			
			var existingGrades="";					
			$(".NavigationButtons").each(function(){				
				existingGrades=existingGrades+$(this).attr("id")+",";				
			});
			
$.ajax({
				
				url:"getRemainingGrades.html?existingGrades="+existingGrades,
			    async:false,
			    cache:false,
			    success:function(data){	

if(data.length>=2){
			    	var content=data.split(",");
			    	var htmlCode="";
			    	for(var i=0;i<content.length;i++){
			    		
			    		if(i==0){
			    		
			    		htmlCode=htmlCode+"<input type=\"radio\" checked=\"checked\" name="+"radioGroup"+ " value="+content[i]+">"+content[i]+ "<br>";	
			    		}
			    		
			    		else {
			    			
			    			htmlCode=htmlCode+"<input type=\"radio\" name="+"radioGroup"+ " value="+content[i]+">"+content[i]+ "<br>";	
			    		}
			    	}
			    	
			    	var okButton="<br><br><input type=\"button\" value=\"ok\" onclick=\"okButtonPress()\"/>";
			    	htmlCode=htmlCode+okButton;			    	
			    	$("#GradesCheckBox").html(htmlCode);
			    	$("#GradesCheckBox").dialog({
						width : 200,
						height : 300,
						modal : true,
						draggable : false,
						resizable : false
					});
			    	}
			    	
			    	if(data.length==2 || allActiveGradesonDB==appearingGradeElements){
			    		
			    		//$("#addGrade").hide();
			    		
			    	}
}
				
			});

			
		});

	});

	function initialShow() {

		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;
		for ( var i = 0, ii = nagivationButtons.length; i < ii; i++) {
			if (nagivationButtons[i].id == "G1") {
				var buttonToShow = nagivationButtons[i].id;
				reviewCompetencyToShow = document.getElementById("OG"
						+ buttonToShow);
				reviewCompetencyToShow.style.display = 'inline';
				var navigationButtonToHighLight = document
						.getElementById(buttonToShow);
				navigationButtonToHighLight.style.backgroundColor = '#58D3F7';

			}

		}

	}

	function colorIt(element) {
		var buttonToColor = document.getElementById(element);
		//alert(buttonToColor.style.backgroundColor);
		if (buttonToColor.style.backgroundColor != '#58d3f7') {
			buttonToColor.style.backgroundColor = "orange";
		}

	}
	function fadeIt(element) {		
		var buttonToColor = document.getElementById(element);
		//alert(buttonToColor.style.backgroundColor);
		if (buttonToColor.style.backgroundColor != '#58d3f7') {
			buttonToColor.style.backgroundColor = "#BDBDBD";//#BDBDBD
		}

	}

	function showCompetency(navigationButtonid) {
		var reviewCompetencyIdToShow = navigationButtonid;
		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;
		var test =document.getElementById(navigationButtonid);
		
		$(".NavigationButtons").each(function(){
			
			if(navigationButtonid==$(this).attr("id")){
			$("#"+$(this).attr("id")).css("background-color","#58D3F7");
			$("#OG"+$(this).attr("id")).css("display","inline");
			}
			
			else {
				$("#"+$(this).attr("id")).css("background-color","#BDBDBD");
				$("#OG"+$(this).attr("id")).css("display","none");
				
			}
	})
	}
	
	function okButtonPress(){
		
		$("#GradesCheckBox").dialog("close");
		var selectedGrade=$('input:radio[name=radioGroup]:checked').val();		
		var htmlCode="<div class=\"NavigationButtons\" onclick=\"showCompetency('"+selectedGrade+"')\" id=\""+selectedGrade+"\">"+selectedGrade+"*</div>";
$.ajax({
			
			url:"addNewGrade.html?gradeName="+selectedGrade+"&noOfExistingElements="+gradeElementsForSpring,
			async:false,
		    cache:false,
		    type:"GET",
		    success:function(data){		
		    	$("#ObjectiveGrades").append(data);
		    	$("#navigation").append(htmlCode);
		    	gradeElementsForSpring=gradeElementsForSpring+1;
		    	showCompetency(selectedGrade);
		    	appearingGradeElements=appearingGradeElements+1;
		    	if(appearingGradeElements==1){
		    		$("#BtnSaveObjective").show();
		    	}
		    	
		    	
		    }
					
			
			
		});
		
	}
	
	function deleteGrade(gradeId){		
		$("#"+gradeId.substring(3)).remove();
		$("#OG"+gradeId.substring(3)).remove();
		showCompetency($(".NavigationButtons").first().attr("id"));
		appearingGradeElements=appearingGradeElements-1;
		if(appearingGradeElements==0){
    		
    		$("#BtnSaveObjective").hide();
    	}
		
$.ajax({
			
			url:"getNoOfActiveGrades.html",
			async:false,
			cache:false,
			success:function(data){
				
				allActiveGradesonDB=data;
			}
			
			
		});
		if(appearingGradeElements<allActiveGradesonDB){
			$("#addGrade").show();
		}
		
	}
	
	$("#BtnSaveObjective").click(function(){
		
		alert("save objective btn clicked");
		
	});
	
</script>
<style type="text/css">

.DeleteButton {
	width: 18px;
	height: 18px;
	background-color: #BDBDBD;
	float: left;
	margin-left: 940px;
	background-color: #FA8072;
	text-align: center;
}
.NavigationButtons {
	width: 50px;
	height: 20px;
	background-color: #BDBDBD;
	text-align: center;
	float: left;
}

.navigation {
	height: 20px;
	width: 1000px;
}

.ObjectiveGrades {
	background-color: skyblue;
	height: 380px;
	width: 1000px;
}

.showon {
	display: inline;
}

.showoff {
	display: none;
}

textarea {
	height: 140px;
	overflow: auto;
	width: 97%;
}

.ObjectiveDefinitions {
margin-top:15px;
	width: 700px;
	height: 450px;
}

.defaultObjectiveName {
	height: 35px;
	overflow: auto;
	width: 97%;
}
</style>
</head>
<body>
	<form:form commandName="defaultObjectiveVO">
		<div class="ObjectiveHeader">

			<div>
				<form:label path="">
					<h4>Objective Name :</h4>
				</form:label>
			</div>
			<div style="margin-top: 15px;">
				<form:textarea path="defaultObjectiveName"
					cssClass="defaultObjectiveName" />
			</div>
		</div>
		<div class="ObjectiveDefinitions">

			<div class="navigation" id="navigation">
				<c:forEach items="${defaultObjectiveVO.defaultObjectiveGrades}"
					var="defaultObjectiveGrade" varStatus="status">
					<div class="NavigationButtons" onclick="showCompetency(this.id)"
						id="${defaultObjectiveGrade.grade.grade}">
						<!-- onmouseover="colorIt(this.id)" onmouseout="fadeIt(this.id)" -->
						${defaultObjectiveGrade.grade.grade}</div>

				</c:forEach>
				<div style="float: right;">
					<input type="button" value="add grade" id="addGrade"/>
				</div>
			</div>


			<div class="ObjectiveGrades" id="ObjectiveGrades">
				<c:forEach items="${defaultObjectiveVO.defaultObjectiveGrades}"
					var="defaultObjectiveGrade" varStatus="stat">
					<form:hidden path="defaultObjectiveGrades[${stat.index }].grade.grade"/>
					<form:hidden path="defaultObjectiveGrades[${stat.index }].defaultObjectiveGradeId"/>
					<div class="ObjectiveGrade"
						id="OG${defaultObjectiveGrade.grade.grade}" style="display: none">

						<div style="padding: 10px;">
						<div style="float: left;">
							<form:label path="">
								<h4>Definition</h4>
							</form:label>
							</div>
						<div id="Btn${defaultObjectiveGrade.grade.grade}" value="X" width="50px;" onclick="deleteGrade(this.id)" class="DeleteButton">	
						X
						</div>
						</div>
						<div style="margin-left: 10px;">
							<form:textarea cssClass="TextAreaContent"
								path="defaultObjectiveGrades[${stat.index }].detailsComment.commentText" />
						</div>
						<div style="padding: 10px;">
							<form:label path="">
								<h4>Success Criteria</h4>
							</form:label>
						</div>
						<div style="margin-left: 10px;">
							<form:textarea cssClass="TextAreaContent"
								path="defaultObjectiveGrades[${stat.index }].successCriteria.commentText" />
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<input type="Button" value="Save" id="BtnSaveObjective"/>
	</form:form>
	<div class="GradesCheckBox" id="GradesCheckBox" title="Choose Grade" style="display: none;"></div>

</body>
</html>