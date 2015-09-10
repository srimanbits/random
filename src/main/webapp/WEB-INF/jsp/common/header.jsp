<%@include file="taglibs.jsp"%>

<c:url var="root" value="/"></c:url>
<div id="header-top" style="background-color: #0B9FF5; height: 100px; /* width: 1024px; */">
	<div id="header-img" style="text-align: left; padding: 20px 0 15px 30px;">
		<img src="${root}common/images/GGK_Tech2_New.png" alt="Delivering Commitments" onclick="window.location.href='${root}'">
	</div>
<%-- 	<sec:authorize access="isAuthenticated()">
		<div id="logout" >
			Welcome, <sec:authentication property="principal.fullName" />&nbsp;
			<a href="${root}logout.html">Logout</a>
		</div>
	</sec:authorize> --%>
</div>

 <div id="header-base" style="background-color: #FFA400; height: 5px;float: left;width: 1200px;"></div>