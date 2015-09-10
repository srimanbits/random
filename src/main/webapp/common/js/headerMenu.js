function getAppraisalHome(root,selfOrPeers, element){
	$('#main-out').block({ message: 'Loading Please Wait....'});
	$.ajax({
		url: root+"appraisal/Home.html",
		type: "POST",
		data: {"selfOrPeers":selfOrPeers},
		cache:false,
		success: function(result){
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	if(typeof(element)==='undefined') {
	    		if(selfOrPeers=='self') {
	    			$("#pageLocation").html('Home > Appraisal > Self Appraisal');
	    		} else if(selfOrPeers=='peers') {
	    			$("#pageLocation").html('Home > Manager > Team Appraisal');
	    		} else if(selfOrPeers=='all') {
	    			$("#pageLocation").html('Home > Super User > All appraisals');
	    		} else if(selfOrPeers=='shared') {
	    			$("#pageLocation").html('Home > Super User > Shared appraisals');
	    		}
	    	} else {
	    		$("#pageLocation").html('Home > '+ pageLocation);
	    	}
	    	$('#main-out').unblock({ fadeOut: 0 });
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
};

function getFeedbackRequestsIn(root, element){
	$('#main-out').block({ message: 'Loading Please Wait....'});
	$.ajax({
		url: root+"feedback/feedbackRequests.html",
		type: "POST",
		data: {"user":"receiver", "location":"out", "type":"request", "reviewHeaderId":"0"},
		cache:false,
		success: function(result){
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
	    	$('#main-out').unblock({ fadeOut: 0 });
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
};


function getFeedbackRequestsOut(root, element){
	$('#main-out').block({ message: 'Loading Please Wait....'});
	$.ajax({
		url: root+"feedback/feedbackRequests.html",
		type: "POST",
		cache:false,
		data: {"user":"sender", "type":"request", "location":"out", "reviewHeaderId":"0"},
		success: function(result){ 
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
	    	$('#main-out').unblock({ fadeOut: 0 });
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
};

function getSelfFeedbackTemplate(root, element){
	$('#main-out').block({ message: 'Loading Please Wait....'});
	$.ajax({
		url: root+"feedback/feedbackRequests.html",
		type: "POST",
		cache:false,
		data: {"user":"sender", "type":"self", "location":"out", "reviewHeaderId":"0"},
		success: function(result){ 
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
	    	$('#main-out').unblock({ fadeOut: 0 });
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
};


function displayManagementToolKit(root, element) {
	$.ajax({
		url: root+"documents/showManagementToolKitDocs.html",
		type: "GET",
		cache:false,
		success: function(result){
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
}

/*function displayHelpDocuments(root, element) {
	$.ajax({
		url: root+"documents/showHelpDocs.html",
		type: "GET",
		cache:false,
		success: function(result){
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
}*/

function getServerProperties(root,element)
{
	$.ajax({
		url: root+"serverDetails/editServerDetails.html",
		type: "GET",
		cache:false,
		success: function(result){
			$('#body').html(result);
			var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
	
	
}

function getH1ReminderStages(root,element)
{
	  $.ajax({
	   url: root+"reminderMessages/getStages.html",
	   type: "GET",
	   cache:false,
	   data:{reviewPeriod:element.id},
	   success: function(result){
	    $('#body').html(result);
	    var pageLocation=getPageLocation(element);
	       $("#pageLocation").html('Home > '+ pageLocation);
	   },
	   error: function(xmlHttpRequest, textStatus, errorThrown) { 
	    setDialog(errorThrown,"alert");
	    $( "#alert" ).dialog( "open" );
	   }
	  });

}

function getReviewCycleDetails(root,element)
{
	 $.ajax({
	  url: root+"reviewCycle/reviewCycleDisplay.html",
	  type: "GET",
	  cache:false,
	  success: function(result){
	   $('#body').html(result);
	   var pageLocation=getPageLocation(element);
	      $("#pageLocation").html('Home > '+ pageLocation);
	  },
	  error: function(xmlHttpRequest, textStatus, errorThrown) { 
	   setDialog(errorThrown,"alert");
	   $( "#alert" ).dialog( "open" );
	  }
	 });
}

function getEmployeeHistory(root,empId,element,fromMenu)
{
	 $.ajax({
	  url: root+"employeeDetails/getGradeOrDesgHistoryByEmpId.html",
	  data: "empId=" +empId+"&fromMenu="+fromMenu,
	  type: "GET",
	  cache:false,
	  success: function(result){
	   $('#body').html(result);
	   var pageLocation=getPageLocation(element);
	      $("#pageLocation").html('Home > '+ pageLocation);
	  },
	  error: function(xmlHttpRequest, textStatus, errorThrown) { 
	   setDialog(errorThrown,"alert");
	   $( "#alert" ).dialog( "open" );
	  }
	 });
}


