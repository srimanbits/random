function getReviewForm(root, reviewHeaderId){
	$.ajax({
		url: root+"appraisal/summary.html",
		type: "POST",
		data: {"reviewHeaderId":reviewHeaderId},
		cache:false,
		success: function(result){ 
			$('#body').html(result);
			//$('#body').dialog();
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	    });
};

function getAppraisalIndex(root,reviewCycleId, state, selfOrPeers){
	$.ajax({
		url: root+"appraisal/Index.html",
		type: "POST",
		data: {"reviewCycleId":reviewCycleId,"state": state, "selfOrPeers":selfOrPeers},
		cache:false,
		success: function(result){ 
			$('#appraisalIndex').html(result);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	    });
	   };