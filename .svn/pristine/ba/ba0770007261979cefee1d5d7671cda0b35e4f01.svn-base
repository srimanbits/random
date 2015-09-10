<%-- <%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_page.css" />
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_table.css" />
	<sec:authorize access="hasAnyRole('ROLE_SUPERUSER','ROLE_MANAGER')" var="isAllowed"/>
<script type="text/javascript">



$(document).ready(function(){
	
	/* $("#appraiserChangeLog").dataTable({
		
		bFilter: false, bInfo: false, bSearchable:false,"bLengthChange": false,bSort:false
	}); */
	var root="${root}";
	if(${isAllowed==true}){
	$('#appraiserChangeLog').dataTable({
		bSearchable:false,
		"bLengthChange": false,
		bSort:false,
		"sPaginationType": "full_numbers",
		"bProcessing": true,
        "bServerSide": true,
        "bFilter": false,
        //bStateSave variable you can use to save state on client cookies: set value "true" 
        "bStateSave": false,
        "bDestroy": true,
        //Default: Page display length
        "iDisplayLength": 5,
        //We will use below variable to track page number on server side
        "iDisplayStart": 0,
        "sAjaxSource": root+"appraiserChangeLogsForManager.html",
        /* "fnServerParams": function ( aoData ) {
            aoData.push( { "name":"reviewHeaderId" ,"value": reviewHeaderId },{ "name":"roleDescription","value":roleDescription} );
        }, */
        "aoColumns": [
            { "mDataProp": "requestedOnEmployee" },         
            { "mDataProp": "requestedToEmployee" },
            { "mDataProp": "actionField" }
             ],
		"bLengthChange": false,
		"fnDrawCallback": function (oSettings) {
		},
		"oLanguage": {
	        "sEmptyTable": "There are no Employees moved from you"
	    }
	}

	);
	}
});

</script> --%>
<center><H3 style="line-height:100px;">Welcome to PMS application</H3>
</center>
<%-- <c:if test="${ isAllowed}">
<div style="float: left;margin-left: 30px;" >

<br><b>Following Employees moved from you. Please
Send Feedback </b><br><br><br>
<table id="appraiserChangeLog">
<thead>
<tr>
<th>Employee</th>
<th>Current Appraiser</th>
<th>Action</th>
</tr>
</thead>
</table>
</div></c:if> --%>