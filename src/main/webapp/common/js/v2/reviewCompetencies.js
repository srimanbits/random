function showCompetency(navigationButton) {

		var navigationButtonid = navigationButton.id;
		reviewCompetencyIdToShow = navigationButtonid.substring(1);
		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;

		for ( var i = 0, ii = nagivationButtons.length; i < ii; i++) {
			if (nagivationButtons[i].nodeName == 'DIV') {
				if (nagivationButtons[i].id.substring(1) == reviewCompetencyIdToShow) {

					var buttonToShow = nagivationButtons[i].id;
					reviewCompetencyToShow = document.getElementById("rc"
							+ buttonToShow.substring(1));
					reviewCompetencyToShow.style.display = 'inline';
					var navigationButtonToHighLight = document
							.getElementById(buttonToShow);
					navigationButtonToHighLight.style.backgroundColor = 'green';
				} else {

					var buttonToHide = nagivationButtons[i].id;
					reviewCompetencyToHide = document.getElementById("rc"
							+ buttonToHide.substring(1));
					reviewCompetencyToHide.style.display = 'none';
					var navigationButtonToNormal = document
							.getElementById(buttonToHide);
					navigationButtonToNormal.style.backgroundColor = '#BDBDBD';

				}

			}

		}
	}

	function initialShow() {
		var navigationDiv = document.getElementById("navigation");
		var nagivationButtons = navigationDiv.childNodes;

		for ( var i = 0, ii = nagivationButtons.length; i < ii; i++) {

			if (nagivationButtons[i].id == "n1") {

				var buttonToShow = nagivationButtons[i].id;
				reviewCompetencyToShow = document.getElementById("rc"
						+ buttonToShow.substring(1));
				reviewCompetencyToShow.style.display = 'inline';
				var navigationButtonToHighLight = document
						.getElementById(buttonToShow);
				navigationButtonToHighLight.style.backgroundColor = 'green';
			}
		}
	}
	
	function colorIt(element) {

		var buttonToColor = document.getElementById(element.id);
		if (buttonToColor.style.backgroundColor != 'green') {
			buttonToColor.style.backgroundColor = "orange";
		}
	}
	
	function fadeIt(element) {

		var buttonToColor = document.getElementById(element.id);
		if (buttonToColor.style.backgroundColor != 'green') {
			buttonToColor.style.backgroundColor = "#BDBDBD";//#BDBDBD
		}
	}
	
	function saveCompetencies(root) {
		$('#main-out').block({ message: 'Saving Please Wait....'});
			$.ajax({
				url : root+"appraisal/saveCompetencies.html",
				async : false,
				cache : false,
				type : "POST",
				data : $("#competencyForm").serialize()+"&opsType=save",
				success : function(result) {
					$('#main-out').unblock({ fadeOut: 0 });
					notification("Saved Successfully..");
					$('#isEdited').val('no');
					$('#body-content').html(result);
				},
				error: function(xmlHttpRequest, textStatus, errorThrown) { 
					$('#main-out').unblock({ fadeOut: 0 });
					setDialog("Something went wrong..Could not update your Competencies","alert");
					$( "#alert" ).dialog( "open" );
				}
			});
	}