<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
	
	 <script type="text/javascript" src="${root}common/js/v2/fnFilterClear.js"></script> 
	 <script type="text/javascript" src="${root}common/js/v2/fnReloadAjax.js"></script> 
	 <script type="text/javascript" src="${root}common/js/v2/indexForm.js"></script>

<style type="text/css">

.dataTable {
	white-space: normal !important;
	word-wrap: break-word;
	word-break: break-all;
	table-layout: auto;
}

.dataTable td {
	text-transform: capitalize;
	font-family: Trebuchet MS, Gill Sans MT, Verdana;
	font-size: 13px;
	text-align: left;
	height: 30px;
}

.emailClass {
	text-transform: none !important;
}
#shareActionDiv,#emailActionDiv,#delegateActionDiv,#unShareActionDiv {
	float: left;
	margin-left: 5px;
	margin-right: 5px;
}

#index {
	margin: 5px;
}

#tableHeading {
	font-size:17px;
}

#selectTargetEmployeeDiv {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	margin: 10px;
}

#selectTargetEmployeeDiv #selectEmployeeLabel {
	width: 40%;
	margin: 10px;
}

#selectTargetEmployeeDiv #selectEmployee {
	width: 50%;
	margin: 10px;
}
</style>

<script type="text/javascript">
var   oTable ;
$(document).ready(function(){
	
	/* var noOfAppraisals = $( "input:checkbox.appraisal" ).length;
	 if(noOfAppraisals <= 0) {
		$(".checkBoxTd").hide();
		$("#checkBoxTh").hide();
		$("#shareLink").hide();
	}  */	
	jQuery.ajaxSetup({ cache:true});
	var selfOrPeers='${selfOrPeers}';
	 var root='${root}';
	$("#index tr:even").css('background-color','rgb(250, 250, 250)');
	$("#index th").css('background-color','#eaf4fd');
	
	var c = $( "input:checkbox.appraisal" ).length;
	if(c>=1){
	//	$("#selectAll").show();
		$("#shareLink").show();
	}
	if(selfOrPeers=='self')
		$("#index").css('width','65%');
	else {
		$("#index").css('width','100%');
		$("#index th").css('text-align','left');
		$("#index td").css('text-align','left');	
	}
	
	$("#index").css('height','100%');
	$("#index").css('text-align','center');
	$("#index").css('border', '1px solid #BEBEBE');
	$("#selectTargetEmployeeDiv").dialog({
		width : 400,
		height : 150,
		modal : true,
		draggable : false,
		resizable : false,
		autoOpen: false,
		close: function( event, ui ) {
			$( this ).dialog( "close" );
			$("#shareToEmployee").val('');
			$("#selectedEmpoyeeDetails").val('');
			$("#hiddenTargetEmployeeId").val('');
		},
		buttons: [
			{
				text: "Share",
				click: function() {
					if($("#hiddenTargetEmployeeId").val()!='' && $("#shareToEmployee").val()!='') {
						shareSelectedAppraisals($(this).data("reviewHeaderList"));
						//shareSelectedAppraisals();
						$( this ).dialog( "close" );
					} else if($("#shareToEmployee").val()!='') {
						 var root='${root}';
						 $.ajax({
							url: root+'feedback/getEmployeeByDisplayname.html',
						  	dataType: "json",
						  	cache:false,
						  	context: $(this),
		                  	data: {employeeDisplayName:$("#shareToEmployee").val()},
						  	success: function(data){
							  	if(data != null) {
							  		$("#hiddenTargetEmployeeId").val(data.empId);
								  	shareSelectedAppraisals($(this).data("reviewHeaderList"));
								  	//shareSelectedAppraisals();
									$( this ).dialog( "close" );
							  	} else {
							   		setDialog("Please select valid employee","alert");
									$( "#alert" ).dialog( "open" ); 
							  	}
	                 		}
		            	});
					} else {
						setDialog("Employee name is mandatory","alert");
						$( "#alert" ).dialog( "open" );
					}
					$("#shareToEmployee").val('');
					$("#selectedEmpoyeeDetails").val('');
					$("#hiddenTargetEmployeeId").val('');
				}
			},
			
			{
				text: "Cancel",
				click: function() {
					$( this ).dialog( "close" );
					$("#shareToEmployee").val('');
					$("#selectedEmpoyeeDetails").val('');
					$("#hiddenTargetEmployeeId").val('');
				}
			}
		]
	});
	
	// Unshare dialog 
	
	$("#unShareDialog").dialog({
		width : 400,
		height : 150,
		modal : true,
		draggable : false,
		resizable : false,
		autoOpen: false,
		close: function( event, ui ) {
			$( this ).dialog( "close" );
		},
		buttons: [
			{
				text: "Yes",
				click: function() {
					var data = $(this).data("reviewHeaderList");
					$( this ).dialog( "close" );
					$('#main-out').block({ message: 'Unsharing Please Wait....'}); 
					$.ajax({
						url: root+"appraisal/unShare.html",
						type: "POST",
						data: "reviewHeaderIds=" + data + "&reviewCycleId=" + $('#reviewCycleDropDown').val(),
						cache:false,
						success: function(result) {
							$('#main-out').unblock({ fadeOut: 0 });
							if(result=='success') {
							    oTable.fnReloadAjax(); 
								notification("Unshared successfully.");
								//getAppraisalHome(${root},"all", $("allIndex").get(0));
							} else {
								setDialog("Somethig went wrong. You may not an authorised user for one or more appraisals that you have selected .","alert");
								$( "#alert" ).dialog( "open" );
							}
						},
						error: function(xmlHttpRequest, textStatus, errorThrown) { 
							$('#main-out').unblock({ fadeOut: 0 });
							alert(errorThrown);
						}
					});
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
	
	$("#composeEmailDiv").dialog({
		width : 500,
		height : 310,
		modal : true,
		draggable : false,
		resizable : false,
		autoOpen: false,
		close: function( event, ui ) {
			$( this ).dialog( "close" );
			$("#emailToEmployee").val('');
			$("#selectedEmpoyeeDetails").val('');
			$("#emailSubject").val('');
			$("#emailText").val('');
		},
		buttons: [
			{
				text: "send",
				click: function() {
					if($("#emailSubject").val().trim()!='') {
						var root='${root}';
						$('#main-out').block({ message: 'Sending Please Wait....'}); 
						 $.ajax({
							url: root+'appraisal/sendCustomizedEmail.html',
							type: "POST",
						  	cache:false,
						  	context: $(this),
						  	data: "reviewHeaderIds="+$(this).data("reviewHeaderList") + "&emailToEmployee=" + $("#emailToEmployee").val() + "&emailSubject=" + $('#emailSubject').val() + "&emailText=" + $('#emailText').val(),
						  	success: function(data) {
						  		$('#main-out').unblock({ fadeOut: 0 });
							  	if(data == 'success') {
									$("input:checkbox.appraisal").prop('checked', false);
									notification("Notification sent successfully");
									getAppraisalHome(${root},"all", $("allIndex").get(0));
									//getAppraisalHome(${root},"all", document.getElementById("allIndex"));
							  	} else {
							   		setDialog("Please enter valid employee email address","alert");
									$( "#alert" ).dialog( "open" ); 
							  	}
	                 		},
	                 		error:function(jqXHR, textStatus, errorThrown) {
	         			    	$('#main-out').unblock({ fadeOut: 0 });
	         					setDialog(errorThrown,"alert");
	         					$( "#alert" ).dialog( "open" );
	         		        }
		            	});
						$( this ).dialog( "close" );
					} else {
						setDialog("Subject name is mandatory","alert");
						$( "#alert" ).dialog( "open" );
					}
					$("#emailToEmployee").val('');
					$("#selectedEmpoyeeDetails").val('');
					$("#emailSubject").val('');
					$("#emailText").val('');
				}
			},
			
			{
				text: "Cancel",
				click: function() {
					$( this ).dialog( "close" );
					$("#emailToEmployee").val('');
					$("#selectedEmpoyeeDetails").val('');
					$("#emailSubject").val('');
					$("#emailText").val('');
				}
			}
		]
	});
	
	if(selfOrPeers=="all"){
	 oTable = $("#indexDataTable").dataTable(
		{	
			"sDom": '<"top" lt><"bottom"ip> r',
			"bProcessing": true,
	        "bServerSide": true,
	        "sort": "position",
	        //bStateSave variable you can use to save state on client cookies: set value "true" 
	        "bStateSave": false,
	        "bDestroy": true,
	        //Default: Page display length
	        "iDisplayLength": 10,
	        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
	        "iDisplayStart": 0,
	        
	        "sAjaxSource": root+"appraisal/appraisalData.html",
	        "fnServerParams": function ( aoData ) {
	            aoData.push( { "name": "reviewCycleId", "value": $('#reviewCycleDropDown').val() },{ "name": "state", "value": "all" },{ "name": "selfOrPeers", "value": selfOrPeers } );
	        },
	        "aoColumns": [
	            { "mDataProp": "reviewHeaderCheckBox" },         
	            { "mDataProp": "reviewCycleNameField" },
	            { "mDataProp": "email" },
	            { "mDataProp": "gradeAndDesgField" }, 
	            { "mDataProp": "reviewFormStatus" },
	            { "mDataProp": "mainAppraiserDisplayName" }, 
	            { "mDataProp": "project" },
	            { "mDataProp": "mainAppraiserDisplayNameField" }
	             
	        ],
	        "aLengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
			"bLengthChange": true,
			"aaSorting" : [ [ 1, "asc" ] ],
			"aoColumnDefs": [
			                  { "bSortable": false, "aTargets": [ 0,2,7 ] }
			                ],
			"sPaginationType": "full_numbers",
			"fnDrawCallback": function (oSettings) {
				 //Get page numer on client. Please note: number start from 0 So
	            //for the first page you will see 0 second page 1 third page 2...
	            //Un-comment below alert to see page number
	        	//alert("Current page number: "+this.fnPagingInfo().iPage);    
	        },
			"oLanguage": {
		        "sEmptyTable": "No appraisals for this selection"
		    }
	});
	}else if(selfOrPeers=="peers"){
		 oTable = $("#indexDataTable").dataTable(
				{	
					"sDom": '<"top" lt><"bottom"ip> r',
					"bProcessing": true,
			        "bServerSide": true,
			        "sort": "position",
			        //bStateSave variable you can use to save state on client cookies: set value "true" 
			        "bStateSave": false,
			        "bDestroy": true,
			        //Default: Page display length
			        "iDisplayLength": 10,
			        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			        "iDisplayStart": 0,
			        "sAjaxSource": root+"appraisal/appraisalData.html",
			        "fnServerParams": function ( aoData ) {
			            aoData.push( { "name": "reviewCycleId", "value": $('#reviewCycleDropDown').val() },{ "name": "state", "value": "all" },{ "name": "selfOrPeers", "value": selfOrPeers } );
			        },
			        "aoColumns": [
			            { "mDataProp": "reviewHeaderCheckBox" },         
			            { "mDataProp": "reviewCycleNameField" },
			            { "mDataProp": "email" },
			            { "mDataProp": "gradeAndDesgField" }, 
			            { "mDataProp": "reviewFormStatus" },
			            { "mDataProp": "mainAppraiserDisplayName" }, 
			            { "mDataProp": "project" },
			            { "mDataProp": "mainAppraiserDisplayNameField" }
			             
			        ],
			        "aLengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
					"bLengthChange": true,
					"aaSorting" : [ [ 1, "asc" ] ],
					"aoColumnDefs": [
					                  { "bSortable": false, "aTargets": [ 0,2,7 ] }
					                ],
					"sPaginationType": "full_numbers",
					"fnDrawCallback": function (oSettings) {
					},
					"oLanguage": {
				        "sEmptyTable": "No appraisals for this selection"
				    }
			});
	}else if(selfOrPeers=="shared"){
		 oTable = $("#indexDataTable").dataTable(
				{	
					"sDom": '<"top" lt><"bottom"ip> r',
					"bProcessing": true,
			        "bServerSide": true,
			        "sort": "position",
			        //bStateSave variable you can use to save state on client cookies: set value "true" 
			        "bStateSave": false,
			        "bDestroy": true,
			        //Default: Page display length
			        "iDisplayLength": 10,
			        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			        "iDisplayStart": 0,
			        "sAjaxSource": root+"appraisal/appraisalData.html",
			        "fnServerParams": function ( aoData ) {
			            aoData.push( { "name": "reviewCycleId", "value": $('#reviewCycleDropDown').val() },{ "name": "state", "value": "all" },{ "name": "selfOrPeers", "value": selfOrPeers } );
			        },
			        "aoColumns": [  
			            { "mDataProp": "reviewCycleNameField" },
			            { "mDataProp": "reviewFormStatus" },
			            { "mDataProp": "mainAppraiserDisplayName" }, 
			            { "mDataProp": "mainAppraiserDisplayNameField" }
			             
			        ],
			        "aLengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
					"bLengthChange": true,
					"aaSorting" : [ [ 0, "asc" ] ],
					"aoColumnDefs": [
					                  { "bSortable": false, "aTargets": [ 3 ] }
					                ],
					"sPaginationType": "full_numbers",
					"fnDrawCallback": function (oSettings) {
			        },
					"oLanguage": {
				        "sEmptyTable": "No appraisals for this selection"
				    }
			});
	} else{
		 oTable = $("#indexDataTable").dataTable(
				{	
					"sDom": '<"top" lt><"bottom"ip> r',
					"bProcessing": true,
			        "bServerSide": true,
			        "sort": "position",
			        //bStateSave variable you can use to save state on client cookies: set value "true" 
			        "bStateSave": false,
			        "bDestroy": true,
			        //Default: Page display length
			        "iDisplayLength": 10,
			        //We will use below variable to track page number on server side(For more information visit: http://legacy.datatables.net/usage/options#iDisplayStart)
			        "iDisplayStart": 0,
			        "sAjaxSource": root+"appraisal/appraisalData.html",
			        "fnServerParams": function ( aoData ) {
			            aoData.push( { "name": "reviewCycleId", "value": 0 },{ "name": "state", "value": $('#pendingDropDown').val() },{ "name": "selfOrPeers", "value": selfOrPeers } );
			        },
			        "aoColumns": [  
			            { "mDataProp": "reviewCycleNameField" },
			            { "mDataProp": "reviewFormStatus" },
			            { "mDataProp": "mainAppraiserDisplayName" }, 
			            { "mDataProp": "mainAppraiserDisplayNameField" }
			             
			        ],
			        "aLengthMenu": [ [10, 25, 50, -1], [10, 25, 50, "All"] ],
					"bLengthChange": true,
					"aaSorting" : [ [ 0, "desc" ] ],
					"aoColumnDefs": [
					                  { "bSortable": false, "aTargets": [ 1,2,3 ] }
					                ],
					"sPaginationType": "full_numbers",
					"fnDrawCallback": function (oSettings) {
			        },
					"oLanguage": {
				        "sEmptyTable": "No appraisals for this selection"
				    }
			});
	}
	$("#indexDataTable").css('border', '1px solid #BEBEBE');
	//oTable.fnSetColumnVis(0, false);
	
	$("#pendingDropDown").on("change", function () {
		  var selectedValue = $('option:selected', this).attr('value');
		  oTable.fnFilterClear();
		  $('#tableSearch').val('');
		  //oTable.fnReloadAjax(); 
		  $("#indexDataTable_wrapper").show();
		});
	
	 $("#reviewCycleDropDown").on("change", function () {
	  var selfOrPeers='${selfOrPeers}';
	  var reviewCycleId = $('option:selected', this).attr('value');
	  oTable.fnFilterClear();
	  $('#tableSearch').val('');
	  //oTable.fnReloadAjax(); 
	  $("#indexDataTable_wrapper").show();
	});
	 
	$('#searchBtn').on('click',function(e){
			oTable.fnFilter($.trim($('#tableSearch').val()));
	})
	
	$('#tableSearch').on('keyup',function(e){
		if($.trim($(this).val())=='') {
	    	  oTable.fnFilter("");
	      }
	})
	
	$('#indexDataTable tbody tr').live('click', function (event) {
		if(event.target.type == 'checkbox') {
			if(event.target.checked) {
				$(event.target.parentNode.parentNode).addClass('row_selected');
			} else {
				$(event.target.parentNode.parentNode).removeClass('row_selected');
			}
		}
	});
	

	$("#shareLink").on("click", function () {
		$("input:checkbox.appraisal:hidden").prop("checked", false);
		//var n = $( "input:checkbox.appraisal:checked:visible" ).length;
		var anSelected = fnGetSelected(oTable);
		if(anSelected.length < 1) {
			setDialog("Select the appraisal(s) to be shared.","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}
		var reviewHeaderList = [];
		for(var i=0; i<anSelected.length; i++) {
			var aData = oTable.fnGetData( anSelected[i] );
			var headerId = aData["reviewHeaderId"];
			reviewHeaderList.push(headerId);
		}
		$("#selectTargetEmployeeDiv").data("reviewHeaderList", reviewHeaderList);
		$("#selectTargetEmployeeDiv").dialog( "open" );
	});
	
	$("#unShareLink").on("click", function () {
		$("input:checkbox.appraisal:hidden").prop("checked", false);
		//var n = $( "input:checkbox.appraisal:checked:visible" ).length;
		var anSelected = fnGetSelected(oTable);
		if(anSelected.length < 1) {
			setDialog("Select the appraisal(s) to be Unshared.","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}
		var reviewHeaderList = [];
		for(var i=0; i<anSelected.length; i++) {
			var aData = oTable.fnGetData( anSelected[i] );
			var headerId = aData["reviewHeaderId"];
			reviewHeaderList.push(headerId);
		}
		// Code to be written here...
		$("#unShareDialog").data("reviewHeaderList", reviewHeaderList);
		$("#unShareDialog").dialog( "open" );
	});


	$("#emailLink").on("click", function () {
		$("input:checkbox.appraisal:hidden").prop("checked", false);
		/* var n = $( "input:checkbox.appraisal:checked:visible" ).length;
		if(n<1){
			$("#emailToTR").show();
		} else {
			$("#emailToTR").hide();
		}
		$("#composeEmailDiv").dialog( "open" ); */
		
		var anSelected = fnGetSelected(oTable);
		var reviewHeaderList = [];
		if(anSelected.length == 0) {
			$("#emailToTR").show();
		} else {
			$("#emailToTR").hide();
		}
		for(var i=0; i<anSelected.length; i++) {
			var aData = oTable.fnGetData( anSelected[i] );
			var headerId = aData["reviewHeaderId"];
			reviewHeaderList.push(headerId);
		}
		$("#composeEmailDiv").data("reviewHeaderList", reviewHeaderList);
		$("#composeEmailDiv").dialog( "open" );
	});
});

function fnGetSelected( oTableLocal ) {
	var aReturn = new Array();
	var aTrs = oTableLocal.fnGetNodes();
	
	for ( var i=0 ; i<aTrs.length ; i++ ) {
		//var childCount = $(aTrs[i])[0].childNodes.length;
		if ($(aTrs[i]).hasClass('row_selected')) {
			aReturn.push( aTrs[i] );
		}
	}
	return aReturn;
}

function employeeSearch(sectionId){
	$( '#'+sectionId ).autocomplete({
		  source: function( request, response ) {
			  $("#hiddenTargetEmployeeId").val('');
			  var root='${root}';
			  $.ajax({					    
				url: root+'feedback/employees.html',
				dataType: "json",
	            data: {term: request.term},
					success: function(data) {
						response($.map(data, function(item) {
                        	return {
                               	label: item.displayName,
                               	id: item.empId,
                               	email: item.email
                            };
						}));
          			}
	           }); // ajax
	      },
	      minLength: 1,
	      select: function(event, ui) {
	    	  $("#hiddenTargetEmployeeId").val(ui.item.id);
		  }     	     
	 });
}
employeeSearch("shareToEmployee");


function shareSelectedAppraisals(reviewHeaderList){
	$('#main-out').block({ message: 'Sharing Please Wait....'});
	var root='${root}';
	var selfOrPeers='${selfOrPeers}';
	$.ajax({
		url: root+"appraisal/share.html",
		type: "POST",
		data: "reviewHeaderIds=" + reviewHeaderList + "&shareOrDelegate=share" + "&reviewCycleId=" + $('#reviewCycleDropDown').val() + "&targetEmployeeId=" + $("#hiddenTargetEmployeeId").val(),
		cache:false,
		success: function(result) {
			$('#main-out').unblock({ fadeOut: 0 });
			if(result=='selfAssign') { 
				setDialog("You can not share with yourself.","alert");
				$( "#alert" ).dialog( "open" );
			} else if(result=='superUserAssign'||result=='assignToMainAppraiser'||result=='assignToActiveSharedAppraiser') {
				setDialog("The employee with whom you are sharing already access on the appraisal(s).","alert");
				$( "#alert" ).dialog( "open" );
			} else if(result=='assignToAppraise') {
				setDialog("You can not share with appraise.","alert");
				$( "#alert" ).dialog( "open" );
			} else if(result=='success') {
				$("input:checkbox.appraisal").prop('checked', false);
				//var reviewCycleId = $('option:selected', $('#reviewCycleDropDown')).attr('value');
				//getAppraisalIndex(${root},reviewCycleId, "all",selfOrPeers);
				  oTable.fnReloadAjax(); 
				notification("shared successfully.");
				//getAppraisalHome(${root},"all", $("allIndex").get(0));
			} else {
				setDialog("Somethig went wrong. You may not an authorised user for one or more appraisals you have selected .","alert");
				$( "#alert" ).dialog( "open" );
			}
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			$('#main-out').unblock({ fadeOut: 0 });
			alert(errorThrown);
		}
	});
}
</script>

		<%--If the user is viewing self appraisal --%>
		<c:if test="${selfOrPeers=='self'}">
			<table id="indexDataTable">
				<caption>
					<span><b id="tableHeading">Appraisals</b></span>
				</caption>
			<thead>
				<tr>
					<th width=15%>Appraisal</th>
					<th width=30%>Status</th>
					<th width=20%>Appraiser</th>
					
					<th width=35%>Details</th>
				
				</tr>
			</thead>
			</table>
		</c:if>
		
		<%--If the user is viewing shared appraisals --%>
		<c:if test="${selfOrPeers=='shared'}">
		<table id="indexDataTable">
				<caption>
					<span><b id="tableHeading">Appraisals</b></span>
				</caption>
			<thead>
				<tr>
					<th width=30%>Appraisal</th>
					<th width=25%>Status</th>
					<th width=15%>Main Appraiser</th>
			
					<th width=20%>Details</th>
				
				</tr>
			</thead>
		</table>
		</c:if>
		<%--If the user is viewing his team appraisals --%>
		<c:if test="${selfOrPeers=='peers'}">
			<form name="shareAppraisalForm">
			<table id="indexDataTable" class="dataTable" cellpadding="0">
				<caption>
					<span id="shareActionDiv"> <a href="javascript:void(0)" id="shareLink" class="tooltip" title="Share the appraisals">Share</a></span>
					<span id="unShareActionDiv"> <a href="javascript:void(0)" id="unShareLink" class="tooltip" title="Unshare the appraisals">Unshare</a></span>
					<span><b id="tableHeading">Appraisals</b></span>
				</caption>
				<thead>
					<tr>
						<th width=3% id="checkBoxTh"></th>
						<th width=14%>Appraisal</th>
						<th width=15%>Email Address</th>
						<th width=12%>Grade-Desg</th>
						<th width=15%>Status</th>
						<th width=15%>Main Appraiser</th>
						<th width=10%>Project</th>
						<th width=20%>Details</th>
					
						
					</tr>
				</thead>
			</table>
			<input type="hidden" name="targetEmployeeId" id="hiddenTargetEmployeeId" />
			<div id="selectTargetEmployeeDiv" title="Search" style="display: none;">
				<div>
					<span id="selectEmployeeLabel">Select the employee:</span><span
						id="selectEmployee"><input id="shareToEmployee" type="text" maxlength="100"/>
					</span>
				</div>
			</div>
			<div id="unShareDialog" title="Unshare" style="display: none;">
				<p>Are you sure, want to unshare appraisal(s). </p>
			</div>
		</form> 
		</c:if>
		
		<%--If the user is viewing all appraisals --%>
		<c:if test="${selfOrPeers=='all'}">
		<form name="shareAppraisalForm">
	
			<table id="indexDataTable" class="dataTable">
				<caption>
					<span id="shareActionDiv"> <a href="javascript:void(0)" id="shareLink" class="tooltip" title="Share the appraisals">Share</a></span>
					<span id="unShareActionDiv"> <a href="javascript:void(0)" id="unShareLink" class="tooltip" title="Unshare the appraisals">Unshare</a></span>
					<span id="emailActionDiv"> <a href="javascript:void(0)" id="emailLink" class="tooltip" title="Compose Email">Send Email</a></span>
					<span><b id="tableHeading">Appraisals</b></span>
				</caption>
				<thead>
					<tr>
						<th width=3% id="checkBoxTh"></th>
						<th width=14%>Appraisal</th>
						<th width=15%>Email Address</th>
						<th width=12%>Grade-Desg</th>
						<th width=15%>Status</th>
						<th width=15%>Main Appraiser</th>
						<th width=10%>Project</th>
						<th width=20%>Details</th>
						
					
					</tr>
				</thead>
			</table>
			<input type="hidden" name="targetEmployeeId" id="hiddenTargetEmployeeId" />
			<div id="selectTargetEmployeeDiv" title="Search" style="display: none;">
				<div>
					<span id="selectEmployeeLabel">Select the employee:</span><span
						id="selectEmployee"> <input id="shareToEmployee"
						type="text" maxlength="100"/>
					</span>
				</div>
			</div>
			<div id="composeEmailDiv" title="Compose Email" style="display: none;">
				<div>
					<table>
						<tr style="display: none;" id="emailToTR">
							<td>To</td>
							<td><input id="emailToEmployee" type="text" size="50"/></td>
						</tr>
						<tr>
							<td>Subject</td>
							<td><textarea id="emailSubject" rows="2" cols="50"></textarea></td>
						</tr>
						<tr>
							<td>Text</td>
							<td><textarea id="emailText" rows="7" cols="50"></textarea></td>
						</tr>
					</table>
				</div>
			</div>
			<div id="unShareDialog" title="Unshare" style="display: none;">
				<p>Are you sure, want to unshare appraisal(s). </p>
			</div>
		</form>
		</c:if>
		<br><br>
