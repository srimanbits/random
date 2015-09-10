
function saveDevelopmentPlan(root){
	$('#main-out').block({ message: 'Saving Please Wait....'});
	$.ajax({
		url: root+"appraisal/saveReviewDevelopmentPlan.html",
		type: "POST",
		data: $("#reviewDevelopmentPlanForm").serialize()+"&opsType=save",
		cache:false,
		  async : false,
		success: function(result){ 
				$('#main-out').unblock({ fadeOut: 0 });
				$('#isEdited').val('no');
				manageDevPlanOptions();
				notification("Saved successfully..");
				$('#body-content').html(result);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			$('#main-out').unblock({ fadeOut: 0 });	
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
});
};

function addNewDevelopmentPlanActivity(root, noOfElementsOnPage, reviewHeaderId){
	 $.ajax({
			url: root+"appraisal/appendDevelopmentPlanActivity.html",
			type: "POST",
			data: {index: noOfElementsOnPage-1,reviewHeaderId:reviewHeaderId},
			cache:false,
			async:false,
			success: function(result){ 
				$('#isEdited').val("yes");
				//notification("Added developmentplan activity");
			$("#reviewDevlpmntPlanActivities").append(result);	
			manageDevPlanOptions();
			},
			error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
			}
	});
	
};

