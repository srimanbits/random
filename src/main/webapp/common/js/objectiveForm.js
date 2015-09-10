
function autoSize(selector){
	$(selector).autosize({append : "\n"});
};

function objectivePaging(noOfObjOnPage,startPage){	
	if(noOfObjOnPage==0){
		$('#objectives_navigation').hide();
	}
	else{
		$('#objectives_navigation').show();
	}
	$('#objectives_container').pajinate({
		items_per_page : 1,
        start_page: startPage-1,
		item_container_id : '#objectives',
        show_first_last: false,
        nav_panel_id: '#objectives_navigation'
	});
	
};

function saveObjectiveForm(root){
$('#main-out').block({ message: 'Saving..'});
	$.ajax({
		  url: root+"appraisal/saveAllObjectives.html",
		  type: $('#objectiveForm').attr("method"),
		  data: $('#objectiveForm').serialize()+"&opsType=save",
		  cache: false,
		  async : false,
		  success: function(result){  
			  $('#body-content').html(result);
		  		//changeStatus('saveButton');
		  		$('#isEdited').val('no');
		  		manageObjectiveOptions();
		  		$('#main-out').unblock({ fadeOut: 0 });
		  		notification("Saved successfully..");
		  		
	      },
	      error: function(xmlHttpRequest, textStatus, errorThrown) { 
	    	  $('#main-out').unblock({ fadeOut: 0 });
	    	  setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
	      }
	});	
};
function applyBanners(){
	
	$("[id^=ObjectiveName]").each(function(){
		changeBanner($(this).attr("id"));
	});
	
	$(".rating select").each(function(){
		var id = $(this).attr("id");
   changeRating(id);			
	});
	
	
	
};

function toggleObjective(h2){	

if(!$(h2).hasClass("active")){
$(h2).next("div").slideDown("slow");
//.siblings("div:visible").slideUp("slow")
//$(this).siblings("h2").removeClass("active");	
		$(h2).toggleClass("active");
		
}

else {
	$(h2).removeClass("active");
		$(h2).next("div").slideUp("slow");			
	
	
	
}
        
};
	

function manageObjectiveOptions(){	
	if($("#objectives_container .objective").length>0){
		$("#saveObjButtonDiv").show();
		$("#objectiveFormButtons .collapseAll").show();
		$("#objectiveFormButtons .expandAll").show();
	}
	
	if($("#objectives_container .objective").length==0){
		$("#saveObjButtonDiv").hide();
		$("#objectiveFormButtons .collapseAll").hide();
		$("#objectiveFormButtons .expandAll").hide();
		$('#isEdited').val("no");
	}
	
};

function deleteObj(deleteImgId,root){
	var deleteImgIdSuffixNo=deleteImgId.substring("deleteObjective".length,deleteImgId.length);


	$( "#objDeleteConfirmation").dialog({
		width: 300,
		draggable: true,
		resizable: true,
		maxHeight: 400,
		modal:true,
		buttons: [
			{
				text: "Delete",
				click: function() {
					$( this ).dialog( "close" );
					$('#main-out').block({ message: 'Deleting Please Wait..'});
					deleteObjective(deleteImgIdSuffixNo,root);
				}
			},
			{
				text: "Cancel",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});

};

function deleteObjective(deleteImgIdSuffixNo,root){
	var reviewObjectiveId=$('#objective'+deleteImgIdSuffixNo+'> input:hidden').val();
	var reviewHeaderId= $(".ObjectiveForm > input:hidden").val();
	
	if(reviewObjectiveId==0){
		  $('#objective'+deleteImgIdSuffixNo).remove();
		  manageObjectiveOptions();
		  $('#main-out').unblock({ fadeOut: 0 });
		  notification('Objective successfully deleted.');
	} else {
		$.ajax({
			  url: root+"appraisal/removeObjective.html",
			  type: "POST",
			  cache: false,
			  async:false,
			  data: {reviewObjectiveId:reviewObjectiveId,reviewHeaderId:reviewHeaderId},
			  success: function(data){ 
				  if(data){
					  $('#objective'+deleteImgIdSuffixNo).remove();
					  manageObjectiveOptions();
					  $('#main-out').unblock({ fadeOut: 0 });				  
					  notification('Objective successfully deleted.');									 
				  }
				  else {
					  $('#main-out').unblock({ fadeOut: 0 });
					  setDialog(" You can not delete this Objective.","alert");
						$( "#alert" ).dialog( "open" ); 
				  }
	          },
	          error: function(xmlHttpRequest, textStatus, errorThrown) { 
	        	 $('#main-out').unblock({ fadeOut: 0 });
	        	  alert(errorThrown);
	          }
			});	
	}
	
};

function changeRating(id){
	var value = $("#" + id + " option:selected").html();
		if (id.substr(0,8) == 'appraise') {

		if (value != "Select") {
			$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").text("Appraise Rating : " + $.trim(value.split("-")[0]));
			$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").show();
		}
		if (value == "Select") {
			$("#h2Banner"+id.substr(14)+"> div:nth-child(3)").hide();
		}

	}

		if (id.substr(0,8) == 'appraisr'){

		if (value != "Select") {
			$("#h2Banner"+id.substr(14)+"> div:nth-child(2)").text("Appraiser Rating : " + $.trim(value.split("-")[0]));
			$("#h2Banner"+id.substr(14)+"> div:nth-child(2)").show();
		}
		if (value == "Select") {
			$("#h2Banner"+id.substr(14)+"> div:nth-child(2)").hide();
		}

	}
	
	
};

function validateDate(id){
	if(id.substr(0,4)=='achm'){
		var targetCompletionString=$.trim($("#tgtc"+id.substr(4)).val());
		
		if(targetCompletionString==''){
			
			
		}
		
		else {
			
			var acheivementDate=new Date($("#"+id).val().substr(6,4),$("#"+id).val().substr(3,2)-1,$("#"+id).val().substr(0,2));
			var targetCompletionDate=new Date(targetCompletionString.substr(6,4),targetCompletionString.substr(3,2)-1,targetCompletionString.substr(0,2));
			if(acheivementDate < targetCompletionDate){
				$("#"+id).val("");
				setDialog("Acheivement Date should be greater than or equal to Target Completion Date("+targetCompletionString+")" ,"alert");
				$( "#alert" ).dialog( "open" );
				
			}
		}
	}
	
	if(id.substr(0,4)=='tgtc'){
		var acheivementString=$.trim($("#achm"+id.substr(4)).val());
		
		if(acheivementString==''){
			
			
		}
		
		else {
			
			var targetCompletionDate=new Date($("#"+id).val().substr(6,4),$("#"+id).val().substr(3,2)-1,$("#"+id).val().substr(0,2));
			var acheivementDate=new Date(acheivementString.substr(6,4),acheivementString.substr(3,2)-1,acheivementString.substr(0,2));
			if(acheivementDate < targetCompletionDate){
				$("#"+id).val("");
				 setDialog("Target Completion Date  should be less than or equal Acheivement Date("+acheivementString+")" ,"alert");
					$( "#alert" ).dialog( "open" );
				
			}
		}
	}
	
	
	
};

function applyDatePickerForObjectives(selector,root,reviewCycleStartDate,reviewCycleEndDate){
	var reviewCycleStartDateParts = reviewCycleStartDate.substr(0,10).split('-');
	var reviewCycleEndDateParts = reviewCycleEndDate.substr(0,10).split('-');
	$(selector).datepicker({
		showOn: "button",
		buttonImage: root+"common/images/calendar.png",
		buttonImageOnly: true,
		duration: 'fast',
		changeMonth: true,
        changeYear: true,
        dateFormat: 'dd-mm-yy',
        minDate: new Date(reviewCycleStartDateParts[0],reviewCycleStartDateParts[1]-1,reviewCycleStartDateParts[2]),
        //maxDate: new Date(reviewCycleEndDateParts[0],reviewCycleEndDateParts[1]-1,reviewCycleEndDateParts[2])
	});
};

function changeBanner(id){
	var content=$.trim($("#"+id).val());
	var modifiedContent=$.trim($("#"+id).val());
	if(content.length==0){		
		$("#h2Banner"+id.substring(13)+"> div:nth-child(1)").html("Unnamed");	
	}
	else{
		if(content.length>30){
						
			var modifiedContent=content.substring(0,30)+"....";	
			if(content.length>200){					
				$("#"+id).val($("#"+id).val().substring(0,200));
			}
		}
	$("#h2Banner"+id.substring(13)+"> div:nth-child(1)").text(modifiedContent);
	}
}
