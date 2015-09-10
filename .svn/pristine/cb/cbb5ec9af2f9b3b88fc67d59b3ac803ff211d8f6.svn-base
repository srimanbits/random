<%@include file="../common/taglibs.jsp"%>

<fmt:formatDate value="<%=new java.util.Date()%>" pattern="MMMMMMMMMMM"
	dateStyle="full" var="runningMonth" />
<c:forEach items="${ONOMinutes}" var="item" varStatus="status">
	<tr class="border_bottom" style="width: 95%; padding-bottom: 50px;">
		<td style="width: 10%;"></td>
		<td style="width: 20%;"><h3>
				Month:
				<fmt:formatDate value="${item.createdDate}" pattern="MMMMMMMMMMM"
					dateStyle="full" var="month" />${month }</h3></td>

		<c:choose>
			<c:when test="${runningMonth == month}">
				<td id="lastTd" style="width: 60%;padding-top: 20px;">

					<div id="commentsHistory" class="scrollbottom"
						style="white-space: pre-wrap; height: 200px; overflow: auto; background-color: #EBEBE4;padding-left: 10px;">${item.meetingMinutes}</div>

				</td>
			</c:when>
			<c:otherwise>
				<td id="lastTd" style="width: 60%;padding-top: 20px;">

					<div id="commentsHistory"
						style="white-space: pre-wrap; height: 200px; overflow: auto; background-color: #EBEBE4;">${item.meetingMinutes}</div>

				</td>
			</c:otherwise>
		</c:choose>
	</tr>

	<c:if test="${ status.last and edit == true}">
		<tr>
		<td style="width: 10%;"></td>
		<td style="width: 20%;"><h3>
				Comments : </h3></td>
			<td style="text-align: center;"><textarea
					id="comments" style="height: 100px;"></textarea></td>

		</tr>
		<tr>
			<td align="center" colspan="3">
				<div class="butttonsDiv">
					<a id="save${item.minutesId}" class="92" type="H2-2014"
						style="margin-left: 300px;" title="savecomment"
						onClick="saveMinutes('${reviewHeaderId}','${item.minutesId}','minutesText')"><span>Save
							Comment</span></a>
				</div>
			</td>
		</tr>
	</c:if>
	<c:if test="${ status.last and approve == true}">

		<tr style="margin-top: 50px;">
		<td style="width: 10%;"></td>
		<td style="width: 20%;"><h3>
				Comments : </h3></td>
			<td style="text-align: center; padding-top: 10px"><textarea
					id="comments" style="height: 100px;"></textarea></td>
		</tr>
		<tr>
			<td colspan="3">
				<div class="butttonsDiv">
					<a id="approve${item.minutesId}" class="92" type="H2-2014"
						style="margin-left: 300px;" title="Approve"
						onClick="approve('${reviewHeaderId}','${item.minutesId}')"><span>Approve</span></a>
					<a id="reject${item.minutesId}" class="92" type="H2-2014"
						style="margin-left: 20px;" title="Reject"
						onClick="reject('${reviewHeaderId}','${item.minutesId}')"><span>Reject</span></a>
				</div>
			</td>

		</tr>
	</c:if>
</c:forEach>
