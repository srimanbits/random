
function saveReviewSummary(root, reviewHeaderId){
	$.ajax({
		url: root+"appraisal/saveSummary.html",
		type: "POST",
		data: $("#reviewSummaryForm").serialize()+ "&reviewHeaderId="+reviewHeaderId+"&opsType=save",
		cache:false,
		async : false,
		success: function(result) {
			
			$('#main-out').unblock({ fadeOut: 0 });
			notification("Saved successfully...");
			$('#appraisalSummary').html(result);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
});
}




