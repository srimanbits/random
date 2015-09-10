<%@include file="../../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>

<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_page.css" />
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_table.css" />

<script type="text/javascript" src="${root}common/js/v2/jquery.dataTables_1.9.0.js"></script>
<style type="text/css">

.close {

cursor:pointer;
text-decoration: underline;

}

.open {

cursor:pointer;

}

</style>

<script>
	$(document).ready(function() {
		 jQuery.ajaxSetup({ cache:true});
		redirectToIndex('${reviewFormInfoVO.actualReviewStatus}','${reviewFormInfoVO.reviewFormRole}','${reviewFormInfoVO.reviewPhase.description}');
		var root='${root}';
		
		var reviewHeaderId=${reviewFormInfoVO.reviewHeaderId};
		var roleDescription='${reviewFormInfoVO.reviewFormRole.description}';
		$('#reviewFormLog').dataTable({
			"aaSorting" : [ [ 2, "desc" ] ],
			"aoColumnDefs": [
			                  { "bSortable": false, "aTargets": [ 0,1,3 ] }
			                ],
			"sPaginationType": "full_numbers",
			"bProcessing": true,
	        "bServerSide": true,
	        "bFilter": false,
	        "sort": "position",
	        //bStateSave variable you can use to save state on client cookies: set value "true" 
	        "bStateSave": false,
	        "bDestroy": true,
	        //Default: Page display length
	        "iDisplayLength": 10,
	        //We will use below variable to track page number on server side
	        "iDisplayStart": 0,
	        "sAjaxSource": root+"appraisal/actionLogJsonData.html",
	        "fnServerParams": function ( aoData ) {
	            aoData.push( { "name":"reviewHeaderId" ,"value": reviewHeaderId },{ "name":"roleDescription","value":roleDescription} );
	        },
	        "aoColumns": [
	            { "mDataProp": "createdByName" },         
	            { "mDataProp": "createdByRole" },
	            { "mDataProp": "createdDate" },
	            { "mDataProp": "notesInfo" }
	             ],
			"bLengthChange": true,
			"fnDrawCallback": function (oSettings) {
			},
			"oLanguage": {
		        "sEmptyTable": "There are no appraisals for this selection"
		    }
		}

		);

	$(".close").live("click", function() {			
			// $(this).find("span").text($(this).find("input").val());
			if($(this).prop("title")=="")
				$(this).prop("title",$(this).find("span").text());
			var comment = $(this).find("span").text($(this).find("input").val()).html();
			$(this).find("span").html('<pre style="font-family:Trebuchet MS, Gill Sans MT, Verdana; white-space: pre-line;">' + comment + '</pre>');
			$(this).removeClass("close").addClass("open");
		});
		
		$(".open").live("click", function() {
			
			$(this).find("span").text($(this).prop("title"));
			$(this).removeClass("open").addClass("close");
			
		});


	});
	
</script>
<style>
.display td {
	text-transform: capitalize;
	font-family: Trebuchet MS, Gill Sans MT, Verdana;
	font-size: 13px;
}

.display {
	white-space: normal !important;

	table-layout: fixed;
}
</style>
<div  style="width: 780px;">
	<table cellpadding="0" cellspacing="0" border="0" class="display"
		id="reviewFormLog">
		<thead>
			<tr>
				<th style="width: 20%">Updated By</th>
				<th style="width: 20%">Role</th>
				<th style="width: 20%">Updated On</th>
				<th style="width: 40%">Notes</th>
			</tr>
		</thead>
	</table>
</div>
