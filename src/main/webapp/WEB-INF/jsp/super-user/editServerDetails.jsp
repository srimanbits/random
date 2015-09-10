<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<%-- <script type="text/javascript" src="${root}common/js/jquery.js"></script>
<script type="text/javascript" src="${root}common/js/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="${root}common/js/common.js"></script> 
<link href="${root}common/css/common.css" rel="stylesheet" type="text/css" />--%>
<link href="${root}common/css/v2/developmentPlanForm.css" rel="stylesheet" type="text/css" />
<title>Edit Server Details</title>
<style>
.mandatory{
color: red;
}

</style>
<script type="text/javascript">
$(document).ready(function () { 
	jQuery.ajaxSetup({ cache:true});
$("#save").click(function() {
	saveServerProperties(${root},this);
}); 
});

function saveServerProperties(root,element)
{
	if($("#url").val().trim().length==0 || $("#url").val()==null){
		setDialog("Database url should not be empty" , "alert");
		$( "#alert" ).dialog( "open" ); 

		return false;
	}
	if($("#username").val().trim().length==0 || $("#username").val()==null){
		setDialog("Username should not be empty" , "alert");
		$( "#alert" ).dialog( "open" ); 

		return false;
	}
	if($("#password").val().trim().length==0 || $("#password").val()==null){
		setDialog("Password should not be empty" , "alert");
		$( "#alert" ).dialog( "open" ); 

		return false;
	}
	
	$.ajax({
		url: root+"serverDetails/save.html",
		type: "POST",
		cache:false,
		data:{url:$("#url").val(),username:$("#username").val(),password:$("#password").val()}, 
		success: function(result){
			notification("Saved successfully...");
			/*	$('#body').html(result);
			 var pageLocation=getPageLocation(element);
	    	$("#pageLocation").html('Home > '+ pageLocation); */
		},
		error: function(xmlHttpRequest, textStatus, errorThrown) { 
			setDialog(errorThrown,"alert");
			$( "#alert" ).dialog( "open" );
		}
	});
	
}

</script>
</head>
<center>
<br><br>
<h3>Update Database Details </h3>
<%-- <form:form method="post" action="serverDetails/save.html"> --%>
        <table>
        <tr><br><br></tr>
        <tr><td>Database url: <span class="mandatory">*</span> </td>
        <td><input type="text" name="url" id="url" value="${url}" style="width:300px" width="100%"></input></td></tr>
        <tr><td><br></td></tr>
        <tr><td>Database username: <span class="mandatory">*</span> </td>
        <td><input type="text" name="username" id="username" value="${username}" width="100%"></input></td></tr>
        <tr><td><br></td></tr>
        <tr><td>Database password: <span class="mandatory">*</span> </td>
        <td><input type="password" name="password" id="password" value="${password}" width="100%"></input></td>
        </tr>
        <tr><td><br></td></tr>
        <tr><td></td>
        <td>
        	<div id="submitrow">
				<a id="save" class="tooltip"><span>Save</span></a>
			</div>
		</td></tr>
        </table>
 <%-- </form:form> --%>
 </center>