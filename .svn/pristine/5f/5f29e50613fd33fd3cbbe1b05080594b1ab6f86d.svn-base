<%@include file="../common/taglibs.jsp"%>

<div id="exp">
	<label><b>Employee:</b></label>
	<c:out value="${displayName}" />
	&nbsp&nbsp <label><b>DOJ:</b></label>
	<fmt:formatDate value="${doj}" pattern="dd-MM-yyyy" /></td>&nbsp&nbsp

	<br><br>
		<label><b>Experience in GGK:</b></label>
		<c:out value="${exp}"></c:out>
	
</div><br><br>
<table id="historyTable">
	<tr style="font-size: 1.17em;">
		<th><c:if test="${not empty gradeEmployeeVOs }">Grade History</c:if></th>
		<th><c:if test="${not empty desgnEmployeeVOs }">Designation History</c:if></th>
	</tr>
	<tr>
		<td style="padding-right: 20px;font-size: 1.17em;"><table id="gradeTable" class="dataTable"
				style="width: 200px;">
				<tbody>
					<c:forEach items="${gradeEmployeeVOs }" var="item">
						<tr>
							<td
								style="border: 1px solid rgb(190, 190, 190); width: 150px; text-align: center;"><h3>${item.grade}</h3>
								<br> <c:if test="${not empty item.lastUpdatedDate }"> Updated On : <fmt:formatDate
									value="${item.lastUpdatedDate }" pattern="dd-MM-yyy"
									dateStyle="full" /> </c:if><br>
							<br> <c:if test="${not empty item.lastModifiedByUser }"> Updated by : ${item.lastModifiedByUser}</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table></td>
		<td ><table id="designationTable" class="dataTable"
				style="width: 200px;">
				<tbody>
					<c:forEach items="${desgnEmployeeVOs }" var="item">
						<tr>
							<td
								style="border: 1px solid rgb(190, 190, 190); width: 150px; text-align: center;"><h3>${item.designation}</h3>
								<br> <c:if test="${not empty item.lastUpdatedDate }"> Updated On : <fmt:formatDate
									value="${item.lastUpdatedDate }" pattern="dd-MM-yyy"
									dateStyle="full" /> </c:if><br>
							<br> <c:if test="${not empty item.lastModifiedByUser }"> Updated by : ${item.lastModifiedByUser}</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table></td>
	</tr>
</table>


