<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<style type="text/css">
#content {
	margin: 20px;
}

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

#searchDiv {
	margin-top: 30px;
}
</style>


<script>
var root="${root}";
var forGrade="${forGrade}";

function initializeTable(){
	/* $('#historyTable').dataTable({
		 "sDom": 't',
		 "aoColumnDefs": [
		                  { "bSortable": false, "aTargets": [ 0,3 ] }
		                ],
	}); */
}

function employeeSearch(sectionId){
	$( '#empSearchField' ).autocomplete({
		  source: function( request, response ) {
			  if(${role=='manager'}){
				  $("#hiddenTargetEmployeeId").val('');
					 $.ajax({					    
					    url: '${root}'+'feedback/subOrdinates.html',
					    dataType: "json",
	                    data: {term: request.term},
					    success: function(data){		                             				    	
						    response($.map(data, function(item) {
		                       	return {
		                        		label: item.displayName,
		                       		id: item.empId,
		                        		email: item.email
		                        
		                       	};
		                   	}));
						}
	                });
				} else if(${role=='superuser'}){
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
	      }
		  },
	      minLength: 1,
	      select: function(event, ui) {
	    	  $("#hiddenTargetEmployeeId").val(ui.item.id);
		  }     	     
	 });
}

	$(document).ready(function() {
		//initializeTable();
		employeeSearch();
		
	});
	
	
	function searchDH(){
		var empId=$("#hiddenTargetEmployeeId").val();
		if($( '#empSearchField' ).val()==''){
			setDialog("Employee Name is mandatory","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}else if(empId==''){
			setDialog("Please select valid Employee","alert");
			$( "#alert" ).dialog( "open" );
			return false;
		}
		
		$.ajax({
			<c:choose>
			<c:when test="${forGrade==true}">
			url: root+"employeeDetails/getGradeOrDesgHistoryByEmpId.html",
			</c:when>
			<c:otherwise>
			url: root+"employeeDetails/getGradeOrDesgHistoryByEmpId.html",
			</c:otherwise>
			</c:choose>
			type: "POST",
			data: {"empId":empId,"forGrade":forGrade,"fromMenu":false},
			cache:false,
			success: function(result){ 
				$('#searchDiv').html(result);
				//initializeTable();
			},
			error: function(xmlHttpRequest, textStatus, errorThrown) { 
				setDialog(errorThrown,"alert");
				$( "#alert" ).dialog( "open" );
			}
		    });		
	}

</script>
<div id="content" align="center">
<c:if test="${fromMenu==true }">
	<sec:authorize access="hasAnyRole('ROLE_SUPERUSER','ROLE_MANAGER')">
		
			
				Employee History:
			
							
		<input type="text" id="empSearchField">&nbsp; <input
			type="button" value="Search" onclick="searchDH();">
		<input type="hidden" id="hiddenTargetEmployeeId">
	</sec:authorize>
	</c:if>
	<br /> <br />
	<div id="searchDiv">
	<c:if test="${fromMenu==false }">
	 	<jsp:include page="historyDetails.jsp"></jsp:include> 
	 </c:if>
	</div>

</div>