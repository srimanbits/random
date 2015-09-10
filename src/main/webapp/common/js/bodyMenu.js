function getReviewSummary(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		$("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		          	$.ajax({
		          		url : root + "appraisal/summary.html",
		          		type : "POST",
		          		data : {
		          			"reviewHeaderId" : reviewHeaderId,
		          			"isInternal" : "yes"
		          		},
		          		cache : false,
		          		//async : false,
		          		success : function(result) {
		          			$('#body-content').html(result);
		          			$("#body-menu ul li").removeClass('selected');
		          			$("#reviewSummary").parent().addClass('selected');
		          			$('#main-out').unblock({ fadeOut: 0 });
		          		},
		          		error : function(xmlHttpRequest, textStatus, errorThrown) {
		          			$('#main-out').unblock({ fadeOut: 0 });
		          			setDialog(errorThrown,"alert");
		          			$( "#alert" ).dialog( "open" );
		          		}
		          	});
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});
	} else {
		$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "appraisal/summary.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId,
				"isInternal" : "yes"
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewSummary").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		});
	}
}

function getDevelopmentPlan(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		 $("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");		            
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		              $.ajax({
		      			url : root + "appraisal/getDevelopmentPlans.html",
		      			type : "POST",
		      			data : {
		      				"reviewHeaderId" : reviewHeaderId
		      			},
		      			cache : false,
		      			//async : false,
		      			success : function(result) {
		      				$('#body-content').html(result);
		      				$("#body-menu ul li").removeClass('selected');
		      				$("#reviewDevelopmentPlan").parent().addClass('selected');
		      				$('#main-out').unblock({ fadeOut: 0 });
		      			},
		      			error : function(xmlHttpRequest, textStatus, errorThrown) {
		      			$('#main-out').unblock({ fadeOut: 0 });
		      				setDialog(errorThrown,"alert");
		      				$( "#alert" ).dialog( "open" );
		      			}
		      		});
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});
		}
	else{
		$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "appraisal/getDevelopmentPlans.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewDevelopmentPlan").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
				$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		});
	}
	
};

function getCompetencies(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		 $("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		              $.ajax({
		      			url : root +"appraisal/getCompetencies.html",
		      			type : "POST",
		      			data : {
		      				"reviewHeaderId" : reviewHeaderId
		      			},
		      			cache : false,
		      			//async : false,
		      			success : function(result) {
		      				$('#body-content').html(result);
		      				$("#body-menu ul li").removeClass('selected');
		      				$("#reviewCompetencies").parent().addClass('selected');
		      				$('#main-out').unblock({ fadeOut: 0 });
		      			},
		      			error : function(xmlHttpRequest, textStatus, errorThrown) {
		      			$('#main-out').unblock({ fadeOut: 0 });
		      				setDialog(errorThrown,"alert");
		      				$( "#alert" ).dialog( "open" );
		      			}
		      		});	
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});
	}
	else{
	$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "appraisal/getCompetencies.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewCompetencies").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		});	
	}
	
};

function getObjectives(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		 $("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		              $.ajax({
		      			url : root + "appraisal/getReviewObjectives.html",
		      			type : "POST",
		      			data : {
		      				"reviewHeaderId" : reviewHeaderId
		      			},
		      			cache : false,
		      			//async : false,
		      			success : function(result) {
		      				$('#body-content').html(result);
		      				$("#body-menu ul li").removeClass('selected');
		      				$("#reviewObjectives").parent().addClass('selected');
		      				$('#main-out').unblock({ fadeOut: 0 });
		      			},
		      			error : function(xmlHttpRequest, textStatus, errorThrown) {
		      			$('#main-out').unblock({ fadeOut: 0 });
		      				setDialog(errorThrown,"alert");
		      				$( "#alert" ).dialog( "open" );
		      			}
		      		});
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});
		}
	else{
	$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "appraisal/getReviewObjectives.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewObjectives").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		});
	}
};

function getFeedback(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		 $("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		              $.ajax({
		      			url : root + "/feedback/feedbackRequests.html",
		      			type : "POST",
		      			data : {
		      				"reviewHeaderId" : reviewHeaderId,
		      				"user" : "sender",
		      				"location" : "in",
		      				"type" : "request"
		      			},
		      			cache : false,
		      			//async : false,
		      			success : function(result) {
		      				$('#body-content').html(result);
		      				$("#body-menu ul li").removeClass('selected');
		      				$("#reviewFeedback").parent().addClass('selected');
		      				$('#main-out').unblock({ fadeOut: 0 });
		      			},
		      			error : function(xmlHttpRequest, textStatus, errorThrown) {
		      			$('#main-out').unblock({ fadeOut: 0 });
		      				setDialog(errorThrown,"alert");
		      				$( "#alert" ).dialog( "open" );
		      			}
		      		});
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});	
	}
	else{
	$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "/feedback/feedbackRequests.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId,
				"user" : "sender",
				"location" : "in",
				"type" : "request"
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewFeedback").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
			$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		});
	}
};

function getHistory(root, reviewHeaderId, isEdited, currentTab) {
	if (isEdited == 'yes') {
		$("#confirm > p").html("Do you want to continue without saving?");
		 $("#confirm").dialog({
		        autoOpen:true,
		        width: 300,
				draggable: true,
				resizable: true,
				maxHeight: 400,
				modal:true,
		        buttons:{
		            "Yes":function(){
		              $(this).dialog("close");
		              $('#main-out').block({ message: 'Loading Please Wait....'}); 
		      		$.ajax({
		      			url : root + "appraisal/actionLog.html",
		      			type : "POST",
		      			data : {
		      				"reviewHeaderId" : reviewHeaderId
		      			},
		      			cache : false,
		      			//async : false,
		      			success : function(result) {
		      				$('#body-content').html(result);
		      				$("#body-menu ul li").removeClass('selected');
		      				$("#reviewLog").parent().addClass('selected');
		      				$('#main-out').unblock({ fadeOut: 0 });
		      			},
		      			error : function(xmlHttpRequest, textStatus, errorThrown) {
		      				$('#main-out').unblock({ fadeOut: 0 });
		      				if(errorThrown != "") {
		    					setDialog(errorThrown,"alert");
		    					$( "#alert" ).dialog( "open" );
		    				}
		      			}
		      		});
		            },
		            "No":function() { 
		                    $(this).dialog("close");

		             }
		            }
			});	
	}
	else{
	$('#main-out').block({ message: 'Loading Please Wait....'}); 
		$.ajax({
			url : root + "appraisal/actionLog.html",
			type : "POST",
			data : {
				"reviewHeaderId" : reviewHeaderId
			},
			cache : false,
			//async : false,
			success : function(result) {
				$('#body-content').html(result);
				$("#body-menu ul li").removeClass('selected');
				$("#reviewLog").parent().addClass('selected');
				$('#main-out').unblock({ fadeOut: 0 });
			},
			error : function(xmlHttpRequest, textStatus, errorThrown) {
				$('#main-out').unblock({ fadeOut: 0 });
				if(errorThrown != "") {
					setDialog(errorThrown,"alert");
					$( "#alert" ).dialog( "open" );
				}
			}
		});
	}
};

function saveOrNoSave(currentTab){
	
    var go=true;
     if(currentTab=='reviewDevelopmentPlan'){
	$(".goalName").each(function(){	
		if($.trim($(this).val())==""){			
			go=false;
		}
		
	});
	}
    else{
		$(".objectiveName").each(function(){	
			if($.trim($(this).val())==""){			
				go=false;
			}
			
		});
		}
    
    
	
	if(go==false){	
		if(currentTab=='reviewDevelopmentPlan'){
			setDialog("Goal Names are mandatory.","alert");
			$( "#alert" ).dialog( "open" );
			}
		
		else{
			setDialog("Objective Names are mandatory.","alert");
			$( "#alert" ).dialog( "open" );
		}
	
	}
	
	return go;
	
};

/* review From Info VO button functions */

function approve_appraisal(reviewHeaderId,reviewPhase,reviewFormRole,root){
	
if(reviewFormRole=='APPRAISE'||reviewFormRole=='SHARED_APPRAISER'){
		
		return;
	}
	
	
	 
		 var isEdited=$('#isEdited').val();
			if(isEdited=='yes'){
				setDialog("Save the content before approve.","alert");
				$( "#alert" ).dialog( "open" );
				return false;
			}
			
			var status=true;
			
			if(reviewPhase == 'APPRAISER_IN_PHASE2'){
					
					$.ajax({

						url : root+"appraisal/getNoOfNotApplicableObjectives.html",
						async : false,
						cache : false,
						type:"GET",
						data:{reviewHeaderId:reviewHeaderId},
						success : function(data) {				
							if(data>0){
							
								setDialog("One or More Objectives are not approved.","alert");
								status=false;
								$( "#alert" ).dialog( "open" );
								
							}
							
							else{
								$("#checkinDialog > p:first").html("Are you sure, you want to Approve Appraisal ?");
								isValid=true;
								
							}
						}

					});
				}
			if(status==false){
				return;
			}
			
			$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
			 $("#checkinDialog").data("opsType", "approve_appraisal");
			 $("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
			 $("#checkinDialog").data("reviewFormRole",  reviewFormRole);
			 $.ajax({
					url : root+"appraisal/getInternalComments.html",
					async : false,
					cache : false,
					type:"POST",
					data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
					success : function(data) {
						$("#checkinDialog > textarea").val(data);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						window.location=root+"authentication/login.html";
		            }
				});
				$("#checkinDialog").dialog('open');
		
}

 
 function accept_goals(reviewHeaderId,reviewPhase,reviewFormRole,root){
	//alert("clicked on accept goals");
	var isEdited=$('#isEdited').val();
	if(isEdited=='yes'){
		setDialog("Save the content before resubmit.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	
	var status=true;
		
	if(reviewPhase == 'APPRAISE_IN_PHASE1') {
				
		$.ajax({
			url : root+"appraisal/areGoalsAcceptable.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId},
			success : function(data) {	
				if(data){
					setDialog("One or More Objectives/Development plans are not approved.Please submit to appraiser for approval.","alert");
					status=false;
					$( "#alert" ).dialog( "open" );
				} else {
					$("#checkinDialog > p:first").html("Are you sure, you want to Accept Goals ?");
					isValid=true;
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
	            window.location=root+"authentication/login.html";
	        }
			
		});
	}
	if(status==false){
		return;
	}
		
	$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	$("#checkinDialog").data("opsType", "accept_goals");
	$("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
	$("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	$.ajax({
		url : root+"appraisal/getInternalComments.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
		success : function(data) {
			$("#checkinDialog > textarea").val(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            window.location=root+"authentication/login.html";
        }
	});
	$("#checkinDialog").dialog('open');
}

 function approve_goals(reviewHeaderId,reviewPhase,reviewFormRole,root){
	 
	if(reviewFormRole=='APPRAISE'||reviewFormRole=='SHARED_APPRAISER'){
		return;
	}
	
	var isEdited=$('#isEdited').val();
	if(isEdited=='yes'){
		setDialog("Save the content before submit.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	var status=true;
	
	if(reviewPhase == 'APPRAISER_IN_PHASE1') {
		
		$.ajax({
			url : root+"appraisal/isSubmittable.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId},
			success : function(data) {
				if(data){
					isValid=true;
				} else {
					status=false;
					setDialog("You should set atleast one Objective and one Development Plan Activity.","alert");
					$( "#alert" ).dialog( "open" );
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
	        }
		});
	}
	if(status==false){
		return;
	}
	
	$.ajax({
		url : root+"appraisal/validateAppraisal.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId},
		success : function(data) {
			if($.trim(data).length>0){
				status=false;
				setDialog(data,"alert");
				$( "#alert" ).dialog( "open" );
			} 	else {
				status=true;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
            window.location=root+"authentication/login.html";
        }
	});
	
	var activeSharedAppraiser=getActiveSharedAppraiser(root, reviewHeaderId);
	if(activeSharedAppraiser!=""){
		setDialog("Appraisal is shared with "+activeSharedAppraiser+". Please get feedback from the shared appraiser.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	$("#checkinDialog > p:first").html("Are you sure, you want to Approve Goals ?");	
	
	if(status==false){
		return;
	}
	 $("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	 $("#checkinDialog").data("opsType", "approve_goals");
	 $("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
	 $("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	 $.ajax({
			url : root+"appraisal/getInternalComments.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
			success : function(data) {
				$("#checkinDialog > textarea").val(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
	        }
		});
		$("#checkinDialog").dialog('open');
	
	
}
 
 function resubmit_to_appraise(reviewHeaderId,reviewPhase,reviewFormRole,root){
	 //alert("resubmit_to_appraise");	
	 if(reviewFormRole=='APPRAISE'){
			return;
		}
	 var isEdited=$('#isEdited').val();
		if(isEdited=='yes'){
			setDialog("Save the content before resubmit.","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}
		var activeSharedAppraiser=getActiveSharedAppraiser(root, reviewHeaderId);
		if(activeSharedAppraiser!=""){
			setDialog("Appraisal is shared with "+activeSharedAppraiser+". Please get feedback from the shared appraiser.","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}
		
		
		$("#checkinDialog > p:first").html("Are you sure, you want to re-submit the appraisal To Appraise ?");
		$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
		 $("#checkinDialog").data("opsType", "resubmit_to_appraise");
		 $("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
		 $("#checkinDialog").data("reviewFormRole",  reviewFormRole);
		 $.ajax({
				url : root+"appraisal/getInternalComments.html",
				async : false,
				cache : false,
				type:"POST",
				data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
				success : function(data) {
					$("#checkinDialog > textarea").val(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					window.location=root+"authentication/login.html";
		        }
			});
			$("#checkinDialog").dialog('open');
	
	
}


/* function submit_to_appraiser*/

function submit_to_Appraiser(reviewHeaderId,reviewPhase,reviewFormRole,actualReviewStatus,root){
	
	var isEdited=$('#isEdited').val();
	
	if(isEdited=='yes'){
		setDialog("Save the content before submit.","alert");
		$( "#alert" ).dialog( "open" );
		return false;
	}
	
	var status=true;
	
	 if(reviewPhase== 'APPRAISE_IN_PHASE1'){
		$.ajax({
			url : root+"appraisal/isSubmittable.html",
			cache : false,
			async : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId},
			success : function(data) {
				if(data){			
					$("#checkinDialog > p:first").html("Are you sure, you want to submit the appraisal to Appraiser?");
				} else {
					status=false;
					setDialog("You should set atleast one Objective and one Development Plan Activity.","alert");
					$( "#alert" ).dialog( "open" );
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
	        }
		});
	}
	
	if(status==false) {
		return;
	}
	
	$.ajax({

		url : root+"appraisal/validateAppraisal.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId},
		success : function(data) {
			if($.trim(data).length>0){
				status=false;
				setDialog(data,"alert");
				$( "#alert" ).dialog( "open" );
			} else{
				status=true;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			window.location=root+"authentication/login.html";
        }
	});
	
	if(status==false){
		return;
	}
	
	if(reviewPhase == 'APPRAISE_IN_PHASE2'||reviewPhase=='SYSTEM_PHASE1_COMPLETED'){
		
		/*setDialog("You cannot submit appraisal right now.","alert");
		$( "#alert" ).dialog( "open" );
		return;*/
		$.ajax({

			url : root+"appraisal/validateAppraisal.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId},
			success : function(data) {
				if($.trim(data).length>0){
					status=false;
					setDialog(data,"alert");
					$( "#alert" ).dialog( "open" );
				} else{
					status=true;
					$("#checkinDialog > p:first").html("Are you sure, you want to submit the appraisal to Appraiser ?");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
	        }
		});
		if(status==false){
			return;
		}
		
	}
	
	$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	$("#checkinDialog").data("opsType", "submit_to_appraiser");
	$("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
	$("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	$("#checkinDialog").data("actualReviewStatus",  actualReviewStatus);
	$.ajax({
		url : root+"appraisal/getInternalComments.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
		success : function(data) {
			$("#checkinDialog > textarea").val(data);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			window.location=root+"authentication/login.html";
        }
	});
	
	$("#checkinDialog").dialog('open');
}
	 
/* assesment completed */


function assessment_Completed(reviewHeaderId,reviewPhase,reviewFormRole,root) {
	
	if(reviewFormRole=='APPRAISE'){
			
			return;
		}
	$("#checkinDialog > p:first").html("Are you sure, you want to submit to main appraiser ?");
	$("#checkinDialog").data("url",  root+"appraisal/assessmentCompleted.html");
	$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	$("#checkinDialog").data("opsType", "assessment_Completed");
	$("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	$.ajax({
			url : root+"appraisal/getInternalComments.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
			success : function(data) {
				$("#checkinDialog > textarea").val(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
            }
		});
		$("#checkinDialog").dialog('open');
	}


function getActiveSharedAppraiser(root, reviewHeaderId){
	var activeSharedAppraiser="";
$.ajax({
	url : root+"appraisal/checkForActiveSharedAppraiser.html",
	async : false,
	cache : false,
	type:"POST",
	data:{reviewHeaderId:reviewHeaderId},
	success : function(data) {
		if(data!='noActiveSharedAppraiser'){
			activeSharedAppraiser=data;		
		}
	},
	error: function(XMLHttpRequest, textStatus, errorThrown) {
		window.location=root+"authentication/login.html";
    }
});
return activeSharedAppraiser;
}
/* added in phase 2*/
function approve_appraisal(reviewHeaderId,reviewPhase,reviewFormRole,root) {
	
	if(reviewFormRole=='APPRAISE'||reviewFormRole=='SHARED_APPRAISER'){
			
			return;
		}
	
	var status=true;
	
	$.ajax({

		url : root+"appraisal/validateAppraisal.html",
		async : false,
		cache : false,
		type:"POST",
		data:{reviewHeaderId:reviewHeaderId},
		success : function(data) {
			if($.trim(data).length>0){
				status=false;
				setDialog(data,"alert");
				$( "#alert" ).dialog( "open" );
			} else{
				status=true;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			window.location=root+"authentication/login.html";
        }
	});
	
	if(status==false){
		
		return;
	}
	
	$("#checkinDialog > p:first").html("Are you sure, you want to approve appraisal ?");
	$("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
	$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	$("#checkinDialog").data("opsType", "approve_appraisal");
	$("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	$.ajax({
			url : root+"appraisal/getInternalComments.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
			success : function(data) {
				$("#checkinDialog > textarea").val(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
            }
		});
		$("#checkinDialog").dialog('open');
	
}
function accept_appraisal(reviewHeaderId,reviewPhase,reviewFormRole,root) {
	
	if(reviewFormRole!='APPRAISE'){
			
			return;
		}
	
	$("#checkinDialog > p:first").html("Are you sure, you want accept the appraisal ?");
	$("#checkinDialog").data("url",  root+"appraisal/updateReviewStatus.html");
	$("#checkinDialog").data("reviewHeaderId", reviewHeaderId);
	$("#checkinDialog").data("opsType", "accept_appraisal");
	$("#checkinDialog").data("reviewFormRole",  reviewFormRole);
	$.ajax({
			url : root+"appraisal/getInternalComments.html",
			async : false,
			cache : false,
			type:"POST",
			data:{reviewHeaderId:reviewHeaderId, roleDescription:reviewFormRole},
			success : function(data) {
				$("#checkinDialog > textarea").val(data);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				window.location=root+"authentication/login.html";
            }
		});
		$("#checkinDialog").dialog('open');
	
}