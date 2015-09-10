function displayCompetencies(root,element) {
	$('#main-out').block({
		message : 'Fetching Please Wait....'
	});
	$.ajax({
		url : root + "appraisal/editProficiencies.html",
		type : "GET",
		cache : false,
		data : {
			"competency" : $('.competencyDropdown').val(),
			"dept" : $('.deptDropdown').val()
		},
		success : function(result) {
			$('#body').html(result);
			$('#main-out').unblock({
				fadeOut : 0
			});
			/* var pageLocation=getPageLocation(element);
			$("#pageLocation").html('Home > '+ pageLocation); */
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({
				fadeOut : 0
			});
			setDialog(errorThrown, "alert");
			$("#alert").dialog("open");
		}
	});
}

function saveEditedProficiencies(root,element) {
	$('#main-out').block({
		message : 'Saving Please Wait....'
	});
	$.ajax({
		url : root + "appraisal/saveEditedProficiencies.html",
		async : false,
		cache : false,
		type : "POST",
		data : $("#competencyForm").serialize(),
		success : function(result) {
			$('#main-out').unblock({
				fadeOut : 0
			});
			notification("Saved Successfully..");
			$('#body').html(result);
			/*var pageLocation=getPageLocation(element);
			$("#pageLocation").html('Home > '+ pageLocation); */
		},
		error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({
				fadeOut : 0
			});
			setDialog(
					"Something went wrong..Could not update Competencies",
					"alert");
			$("#alert").dialog("open");
		}
	});
}