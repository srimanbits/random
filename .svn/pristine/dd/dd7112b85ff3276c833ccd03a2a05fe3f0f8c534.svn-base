
  function disable(field){
	 
	  $(field).each(function(){
   	  $(this).attr('readonly','readonly');
      });
	  
  }
  
  function show(requestId, reviewHeaderId, type,root){
	  $('#main-out').block({ message: 'Opening Please Wait....'});  
	    var url = root+'feedback/show.html';
	    var param = {requestId: requestId, reviewHeaderId: reviewHeaderId, "type":type,selectedReviewCycleId:$('#reviewCycleDropDown').val()};
	        
	    $.ajax({
		    type: 'POST',
		    url: url,
		    data: param,
		    success: function(data) {
		    	$('#main-out').unblock({ fadeOut: 0 });
		    	$('#feedbackTemplate').html("");
				$('#feedbackTemplate').html(data);
				disable('#feedbackTemplate textarea');
				$("#feedbackTemplate").dialog('open');
		    },
		    error:function(jqXHR, textStatus, errorThrown) {
		    	$('#main-out').unblock({ fadeOut: 0 });
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
	        }
	    });
 }

 function pendingByRequestId(requestId, type,root){
	 $('#main-out').block({ message: 'Opening Please Wait....'});  
	    var url = root+'feedback/pendingByRequestId.html';
	    var param = {requestId: requestId, type:type,selectedReviewCycleId:$('#reviewCycleDropDown').val()};
	    $.ajax({
		    type: 'POST',
		    url: url,
		    data: param,
		    success: function(data){
		    	$('#main-out').unblock({ fadeOut: 0 });
		    	$('#feedbackTemplate').html("");
			    $('#feedbackTemplate').html(data); 
				$("#feedbackTemplate").dialog('open');
		    },
		    error:function(jqXHR, textStatus, errorThrown){
		    	$('#main-out').unblock({ fadeOut: 0 });
		    	setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
	        }  
	    }); 
   }
 
 
 function newFeedbackTemplate(reviewHeaderId, type, location,root) {
	 $('#main-out').block({ message: 'Generating Please Wait....'}); 
	   var url = root+'feedback/newFeedbackrequest.html';
	   var param = {reviewHeaderId: reviewHeaderId, "type":type, "location":location,"selectedReviewCycleId":$('#reviewCycleDropDown').val()};
	  
	   $.ajax({
		    type: 'POST',
		    url: url,
		    data: param,
		    success: function(data) {
		    	
		    	$('#main-out').unblock({ fadeOut: 0 });
		    	$('#feedbackTemplate').html("");
	    	    $('#feedbackTemplate').html(data);
	    	    $("#feedbackTemplate").dialog('open');
	    	},
		    error:function(jqXHR, textStatus, errorThrown){
		    	$('#main-out').unblock({ fadeOut: 0 });
		    	setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
		    }
      });
 }
 
 function saveFeedback(root){
   		$('#main-out').block({ message: 'Saving please Wait....'});
   		var url = root+'feedback/save.html';
   		var params = $('#feedbackRequestForm').serialize();
   		
		 	$.ajax({
			    type: 'POST',
			    url: url,
			    data: params,
			    success: function(data) {
		    	    if(data=='success'){	
		    	    	$('#main-out').unblock({ fadeOut: 0 });
		    	    	notification(" Feedback Saved  Successfully");
		    	    	var reviewCycleId = $('option:selected', $('#reviewCycleDropDown')).attr('value');
		    	    	getFeedbackRequestIndex(root, reviewCycleId, 'receiver', 'out', '0', 'request');
		    	    }
		    	    if(data=='notAuthorized'){
		    	    	 $('#main-out').unblock({ fadeOut: 0 });
		    	    	 setDialog("You are not authorized to view or modify this content.","alert");
		    				$( "#alert" ).dialog( "open" );
		    	   }
		    	    else if(data=='error'){	
		    	    	 $('#main-out').unblock({ fadeOut: 0 });
		    	    	 setDialog("Feedback Request Not Submitted.","alert");
		    				$( "#alert" ).dialog( "open" );  	   
		    	    }			    	    
	             },
		         error:function(jqXHR, textStatus, errorThrown) {
	            	$('#main-out').unblock({ fadeOut: 0 });
	            	setDialog(errorThrown,"alert");
	    			$( "#alert" ).dialog( "open" );  
		        }  
	        });                                                            
}
 
 
 function submitFeedback(root){
	 
	 if($.trim($("#overallFeedbackComment").val())=='') {
		 setDialog("Please enter Overall Feedback Comments.","alert");
			$( "#alert" ).dialog( "open" );
		return false;
	 }
	 
	 
	 setDialog("Are you sure, you want to submit feedback","confirm");
	 
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
                $('#feedbackTemplate').dialog("close");
          		$('#main-out').block({ message: 'Submitting please Wait....'});
          		var url = root+'feedback/submit.html';
          		var params = $('#feedbackRequestForm').serialize();
          		
      		 	$.ajax({
      			    type: 'POST',
      			    url: url,
      			    data: params,
      			    success: function(data) {
			    	    if(data=='success'){	
			    	    	$('#main-out').unblock({ fadeOut: 0 });
			    	    	notification(" Feedback Submitted  Successfully");
			    	    	var reviewCycleId = $('option:selected', $('#reviewCycleDropDown')).attr('value');
			    	    	getFeedbackRequestIndex(root, reviewCycleId, 'receiver', 'out', '0', 'request');
			    	    }
			    	    if(data=='notAuthorized'){
			    	    	 $('#main-out').unblock({ fadeOut: 0 });
			    	    	 setDialog("You are not authorized to view or modify this content.","alert");
			    				$( "#alert" ).dialog( "open" );
			    	   }
			    	    else if(data=='error'){	
			    	    	 $('#main-out').unblock({ fadeOut: 0 });
			    	    	 setDialog("Feedback Request Not Submitted.","alert");
			    				$( "#alert" ).dialog( "open" );  	   
			    	    }			    	    
		             },
      		         error:function(jqXHR, textStatus, errorThrown) {
  		            	$('#main-out').unblock({ fadeOut: 0 });
  		            	setDialog(errorThrown,"alert");
  		    			$( "#alert" ).dialog( "open" );  
      		        }  
      	        });                                                            
            },
            "No":function() {
                    $(this).dialog("close");
//                    $(this).dialog("destroy");
             }
            }
	});
};

// used for self feedback, create feedback request sending
function generateRequest(root,reviewHeaderId, type, location) {
		var isError = false;
		//in feedback tab, checking the user entered some text in the text field of feedback on employee.
		//If he does not enter date in that text field, displaying alert to him 
		 if(type == 'self') {
			if($("#selfFeedbackOnEmployee").val()=='') {
				 setDialog("Please select the employee on whom you want to give feedback.","alert");
 				$( "#alert" ).dialog( "open" );  
				return false;
			} 
//			if the user entered some text in the text field and not selected through the auto complete box, search is there an employee with the entered name in the specified review cycle
			else if ($("#selfFeedbackOnEmployee").val() != '' && ($("#selfFeedbackOnEmployeeId").val() ==0 || $("#selfFeedbackOnEmployeeId").val() == '')) {
				$.ajax({
				  	url: root+'feedback/getEmployeeByDisplayname.html',
				  	dataType: "json",
				  	cache:false,
				  	async: false,
                  	data: {employeeDisplayName:$("#selfFeedbackOnEmployee").val(),reviewCycleId:$('#reviewCycleId').val()},
				  	success: function(data) {
				  		if(data != null) {
				  			//if the employee is found populating the id field which will be passed to the controller when submitted
					  		$("#selfFeedbackOnEmployeeId").val(data.empId);
					  	} else {
					  		//else the user will be displayed the text he entered in the field is not proper and setting isError value to true and the condition will be checked and control will be returned to the page
					  		setDialog("The employee name: '" + $("#selfFeedbackOnEmployee").val() + "' is not valid, please select valid employee","alert");
			 				$( "#alert" ).dialog( "open" ); 
					  		isError = true;
					  	}
             		}
            	});
			}
	
			if(isError) {
				return false;
			}
			
			//if the user did not enter the text in send to person in feedback tab, display alert to him
			if($("#requestedByEmployee").val()=='') {
				setDialog("Please select the employee to whom you want to send the feedback.","alert");
 				$( "#alert" ).dialog( "open" );
				return false;
//				if the user entered some text in the text field and not selected through the auto complete box, search is there an employee with the entered name	
			} else if(($("#requestedByEmployeeId").val()==0 || $("#requestedByEmployeeId").val()=='') && $("#requestedByEmployee").val() != '') {
				$.ajax({
					url: root+'feedback/getEmployeeByDisplayname.html',
				  	dataType: "json",
				  	async: false,
				  	cache:false,
                  	data: {employeeDisplayName:$("#requestedByEmployee").val()},
				  	success: function(data) {
				  		if(data != null) {
				  		//if the employee is found populating the id field which will be passed to the controller when submitted
					  		$("#requestedByEmployeeId").val(data.empId)
					  	} else {
					  	//else the user will be displayed the text he entered in the field is not proper and setting isError value to true and the condition will be checked and control will be returned to the page
					  		setDialog("The employee name: '"+$("#requestedByEmployee").val()+"' is not valid, please select valid employee.","alert");
			 				$( "#alert" ).dialog( "open" );
					  		isError = true;
					  	}
             		}
            	});
			}
			
			if(isError) {
				return false;
			}
			//validating if the user entered feedback comments or not
			
			if($.trim($("#overallFeedbackComment").val())=='') {
				setDialog("Please enter Feedback Comments","alert");
 				$( "#alert" ).dialog( "open" );
				return false;
			}
			
		} else 
			//When creating new feedback request, checking the user entered some text for the feedback on employee field
			if(type == 'request') {
			//{
				if(($("#feedbackOnEmployeeId").val()=='' || $("#feedbackOnEmployeeId").val()==0) && $("#feedbackOnEmployee").val()=='') { // && $("#feedbackOnEmployee").val() == ''
					setDialog("Please select the employee on whom you want to ask feedback.","alert");
	 				$( "#alert" ).dialog( "open" );
					return false;
				}	
//				if the user entered some text in the text field and not selected through the auto complete box, search is there an employee with the entered name with in specified reveiew cycle
				if ($("#feedbackOnEmployee").val() != '' && ($("#feedbackOnEmployeeId").val() ==0 || $("#feedbackOnEmployeeId").val() == '')) {
					$.ajax({
					  	url: root+'feedback/getEmployeeByDisplayname.html',
					  	dataType: "json",
					  	cache:false,
					  	async: false,
	                  	data: {employeeDisplayName:$("#feedbackOnEmployee").val(),reviewCycleId:$('#reviewCycleId').val()},
					  	success: function(data) {
					  	//if the employee is found populating the id field which will be passed to the controller when submitted
					  		if(data != null) {
						  		$("#feedbackOnEmployeeId").val(data.empId);
						  		if(location == 'out') {
						  			$("#feedbackOnEmployeeDetails").val(data.displayName);
						  		 $("#feedbackOnEmployee").val('');}
						  	} 
					  	//else the user will be displayed the text he entered in the field is not proper and setting isError value to true and the condition will be checked and control will be returned to the page
					  		else {
					  			setDialog("The employee name: '"+$("#feedbackOnEmployee").val()+"' is not valid, please select valid employee.","alert");
				 				$( "#alert" ).dialog( "open" ); 
						  		isError = true;
						  	}
	             		}
	            	});
				}
				
				//When creating new feedback request, checking the user entered some text for the target employee field
			if($("#requestedToEmployee").val()==''){
				setDialog("Please select the employee to whom you want to send the feedback request.","alert");
 				$( "#alert" ).dialog( "open" );
				 return false;
			 } 
//			if the user entered some text in the text field and not selected through the auto complete box, search is there an employee with the entered name with in specified reveiew cycle		
			else if($("#requestedToEmployee").val()!='' && ($("#requestedToEmployeeId").val()==0 || $("#requestedToEmployeeId").val() =='' )) {
				 $.ajax({
					 	url: root+'feedback/getEmployeeByDisplayname.html',
					  	dataType: "json",
					  	async: false,
					  	cache:false,
		              	data: {employeeDisplayName:$("#requestedToEmployee").val()},
					  	success: function(data){
					  	//if the employee is found populating the id field which will be passed to the controller when submitted
					  		if(data != null) {
						  		$("#requestedToEmployeeId").val(data.empId);
						  	} 
					  	//else the user will be displayed the text he entered in the field is not proper and setting isError value to true and the condition will be checked and control will be returned to the page
					  		else {
					  			setDialog("The employee name: '"+$("#requestedToEmployee").val()+"' is not valid, please select valid employee.","alert");
				 				$( "#alert" ).dialog( "open" );
						  		isError = true;
						  	}
		         		}
		        	});
			 }
			
			if(isError) {
				return false;
			}
			//due date validation
			if($("#requestDueDate").val()=='') {
				setDialog("Please select the due date","alert");
 				$( "#alert" ).dialog( "open" );
				 return false;
			 }
			
	}
		
		if(isError) {
			return false;
		}
		if(reviewHeaderId!=0 ||type=='request') {
			setDialog("Are you sure, you want to send the feedback request","confirm");
    	} else if(type=='self') {
    		setDialog("Are you sure, you want to send the feedback","confirm");
    	}
		
		 
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
	            	$('#feedbackTemplate').dialog("close");
	        		$('#main-out').block({ message: 'Please Wait....'});
	        		
	        		var url = root+'feedback/saveRequest.html';
	        		var params = $('#feedbackRequestForm').serialize()+ "&type="+type;
	        		
	        	    $.ajax({
	        		    type: 'POST',
	        		    url: url,
	        		    data: params,
	        		    success: function(data) {
	        		    	    if(data=='success') {
	        		    	    	
	        		    	    	$('#main-out').unblock({ fadeOut: 0 });
	        		    	    	
	        		    	    	var reviewCycleId = $('option:selected', $('#reviewCycleDropDown')).attr('value');
	        		    	    	if(reviewHeaderId!=0) {
	        		    	    		notification(" Feedback Request Generated Successfully.");
	        		    	    		getFeedback(root, reviewHeaderId);
	        		    	    	} else if(type=='request'){
	        		    	    		notification(" Feedback Request(s) Generated Successfully.");
	        		    	    		getFeedbackRequestIndex(root, reviewCycleId, 'sender', 'out', '0', 'request');
	        		    	    	} else if(type=='self') {
	        		    	    		notification(" Feedback Sent Successfully");
	        		    	    		getFeedbackRequestIndex(root, reviewCycleId, 'sender', 'out', '0', 'self');
	        		    	    	}
	        		    	    } else if(data=='error') {
	        			    	     $('#main-out').unblock({ fadeOut: 0 });
	        			    	     setDialog("Feedback Request Not Generated Fill the details Correctly.","alert");
	        			 				$( "#alert" ).dialog( "open" );
	        		    	    }
	        		    	    else{
	        		    	    	$('#main-out').unblock({ fadeOut: 0 });
	        		    	    	document.write(data);
	        		    	    }
	        	             },
	                   error:function(jqXHR, textStatus, errorThrown) {
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
	
};


function getFeedbackRequestIndex(root,reviewCycleId, user, location, reviewHeaderId, type){
	$('#main-out').block({ message: 'Please Wait....'});
	$.ajax({
		url: root+"feedback/getFeedbackRequestIndex.html",
		type: "POST",
		cache:false,
		data: {"user":user, "reviewCycleId":reviewCycleId,"location":location, "reviewHeaderId":reviewHeaderId, "type":type},
		success: function(result){ 
			$('#main-out').unblock({ fadeOut: 0 });
			$('#feedbackRequestIndex').html(result);
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			$('#main-out').unblock({ fadeOut: 0 });
			setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
		}
	});
	}
