<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<style type="text/css">
.visibleDiv
{
    position: fixed;
     bottom: 50px;
    right: 50px;
}

</style>
<script type="text/javascript">
$(document).ready(function(){
	
	$("#selfNotes").dialog({
		width: 600,
		draggable: true,
		resizable: true,
		height: 500,
		autoOpen: false,
		position: "right-10 bottom-10",
		modal:true,
		buttons: [
					{
						text: "Save",
						click: function() {
							//alert($(this).data("reviewHeaderId"));
							//alert($(this).data("roleDescription"));
							$.ajax({
								url : ${root}+"saveSelfNotes.html",
								async : false,
								cache : false,
								type:"POST",
								data:{notesText:$("#selfNotesTextArea").val()}
							});
							$(this).dialog("close");
						}
					},
					{
						text: "Close",
						click: function() {
							$( this ).dialog( "close" );
						}
					}
				]
	});
	
	
});

function getEmployeeNotes(){
	
	

		$.ajax({
			url : ${root}+"getSelfNotes.html",
			async : false,
			cache : false,
			type:"GET",
			success : function(data) {
				$("#selfNotesTextArea").html("");
				$("#selfNotesTextArea").val(data);
				$("#selfNotes").dialog('open');
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
	            window.location=${root}+"authentication/login.html";
	        }
		});
}

</script>
<div class="visibleDiv">
	<!-- <a href="javascript:void(0)" onclick="getEmployeeNotes()"> Self Notes</a> -->
	<img alt="Self Notes" src="${root}common/images/selfNotes.png" onclick="getEmployeeNotes()" height="30px;" width="30px;">
</div>
<div id="selfNotes" style="display:none;" title="Self Notes">
 <textarea rows="10" cols="80"
				style="overflow-y: auto; width: 98%; height: 208%;" id="selfNotesTextArea"></textarea>
		</div>