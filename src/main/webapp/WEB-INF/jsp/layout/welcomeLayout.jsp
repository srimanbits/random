<%@include file="../common/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<c:url var="root" value="/"></c:url>
<html>
<head>
<title>PMS Home</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="text/html; charset=UTF-8" http-equiv="content-type">

<link href="${root}common/css/v2/jquery-ui-1.10.0.custom.css" rel="stylesheet" type="text/css" />
<link href="${root}common/css/v2/common.css" rel="stylesheet" type="text/css" />
<link rel="Shortcut Icon" type="image/png" href="${root}common/images/favicon.ico">


<script type="text/javascript" src="${root}common/js/v2/jquery.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery.blockUI.js"></script>
<script type="text/javascript" src="${root}common/js/v2/jquery.autosize.js"></script>
<script type="text/javascript" src="${root}common/js/v2/common.js"></script>
<%-- <script type="text/javascript" src="${root}common/js/jquery.fileDownload.js"></script> --%>

<!--  Added this to reduce traffic -->

<script type="text/javascript" src="${root}common/js/v2/jquery.dataTables_1.9.0.js"></script>
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery.dataTables.css" />
	<link rel="stylesheet" type="text/css" href="${root}common/css/v2/jquery-ui-1.10.0.custom.css" />
	<script type="text/javascript" src="${root}common/js/v2/headerMenu.js"></script>
	<script type="text/javascript" src="${root}common/js/v2/feedback.js"></script>
	<script type="text/javascript" src="${root}common/js/v2/indexForm.js"></script>

<!--  Added this to reduce traffic Removed from appraisalIndex-->
</head>

<body>
	<div id="main-out">
		<div id="main">
			<div id="header">
				<tiles:insertAttribute name="header" />
				<sec:authorize access="hasRole('ROLE_USER')">
					 <tiles:insertAttribute name="user-header-menu" /> 
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_MANAGER')">
					 <tiles:insertAttribute name="manager-header-menu" /> 
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_SUPERUSER')">
					 <tiles:insertAttribute name="superuser-header-menu" /> 
				</sec:authorize>
			</div>
			<c:if test="${not empty scrollingNotificationMsg}">
			<div id="feedBackMarquee" style="background: rgb(11, 159, 245);color: rgb(133, 235, 133);z-index: -1000 !important">
				<marquee  style="cursor: pointer;" onmouseover="this.stop()" 
                    onmouseout="this.start()">${scrollingNotificationMsg}</marquee>
			</div>
			<div id="feedBackMarqueeFF" style="background: rgb(11, 159, 245);color: rgb(133, 235, 133);z-index: -1000 !important">
				<marquee  style="cursor: pointer;" onmouseover="javascript:this.setAttribute('scrollamount','0');"
				onmouseout="javascript:this.setAttribute('scrollamount','5');">${scrollingNotificationMsg}</marquee>
			</div>
			<script type="text/javascript">
			if(typeof InstallTrigger !== 'undefined') {
				   $('#feedBackMarquee').remove();
				} else {
					$('#feedBackMarqueeFF').remove();
				} 
			</script>
			</c:if>
			 <div id="body">
				<tiles:insertAttribute name="welcome" />
			</div>
			<div id="footer">
				<tiles:insertAttribute name="footer" />
			</div>
			<!-- <div id="alert" title="Message">
				<p></p>
			</div>
			<div id="confirm" title="Message">
				<p></p>
			</div>
			<div id="reloadConfirmation" title="Reload">
				<p></p>
			</div> -->
		</div>
	</div>
</body>
    <%@include file="commonUtils.jsp"%>
	<%@include file="../common/selfNotes.jsp"%>
	<%@include file="../feedback/feedbackUtils.jsp"%>

</html>
