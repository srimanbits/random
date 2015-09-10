<%@include file="../common/taglibs.jsp"%>
<c:url var="root" value="/"></c:url>
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_page.css" />
<link rel="stylesheet" type="text/css"
	href="${root}common/css/v2/demo_table.css" />

<script type="text/javascript"
	src="${root}common/js/v2/jquery.dataTables_1.9.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 jQuery.ajaxSetup({ cache:true});
	var type='${type}';
	var user='${user}';
	var location='${location}';
	if(type=='self' && user=='sender' && location=='out'){
		$('#feedbackTable').dataTable({

			"aaSorting" : [ [ 2, "desc" ] ]

		}
		);
	} else if (type=='request' && user=='sender' && location=='in'){
		$('#feedbackTable').dataTable({

			"aaSorting" : [ [ 3, "asc" ], [ 1, "desc" ] ]

		}
		);
	} else if (type=='request' && user=='sender' && location=='out'){
		$('#feedbackTable').dataTable({

			"aaSorting" : [ [ 4, "asc" ], [ 2, "desc" ] ]

		}
		);
	}else if (type=='request' && user=='receiver' && location=='out'){
		$('#feedbackTable').dataTable({

			"aaSorting" : [ [ 4, "asc" ],  [ 3, "asc" ],  [ 2, "asc" ]]

		}
		);
	}

$("td").css('text-align','center');
$("#feedbackTable").css('width','100%');
$("#feedbackTable th").css('background-color','rgb(190, 190, 190)');
$("td").css('text-align','center');

if(type=='self' && user=='sender'){
	$("#feedbackTableReceived th").css('background-color','rgb(190, 190, 190)');
	$("#feedbackTableReceived").css('width','100%');
	$("td").css('text-align','center');
	$("#feedbackTableReceived").css('border', '1px solid #BEBEBE');
	$('#feedbackTableReceived').dataTable({
		"aaSorting" : [ [ 2, "desc" ] ]
	}
	);
}

	/* $("#feedbackTemplate").dialog({
		autoOpen: false,
		modal : true,
		width: 800,
		draggable : false,
		resizable : false
	}); */
});

</script>
<c:choose>
	<%--User is viewing self given feedbacks and received feedbacks --%>
	<c:when test="${type=='self' and user=='sender' and location=='out'}">
		<b>Feedbacks you have sent: </b>
		<br />
		<br />
		<c:choose>
		<c:when test="${selectReviewCycle}">
				Please Select Review Cycle.
				</c:when>
			<c:when test="${empty feedbackRequests}">
				You did not send any feedback in the selected review cycle.
				</c:when>
			<c:otherwise>
				<div class="table">
					<table id="feedbackTable">
						<thead>
							<tr>
								<th width="25%">On</th>
								<th width="25%">Sent to</th>
								<th width="25%">Given Date(YYYY-MM-DD)</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${feedbackRequests}" var="feedbackRequest"
								varStatus="status">
								<tr>
									<td><a
										onclick=" show('${feedbackRequest.feedbackRequestId}', '0', '${type}',${root})"
										href="javascript:void(0)">
											${feedbackRequest.requestedOnEmployee} </a></td>
									<td>${feedbackRequest.requestedByEmployee}</td>
									<td><fmt:formatDate
											value="${feedbackRequest.requestedDate}" pattern="yyyy-MM-dd" /></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<br />
		<br />
		<b>Feedbacks you have received: </b>
		<br />
		<br />
		<c:choose>
		<c:when test="${selectReviewCycle}">
				Please Select Review Cycle.
				</c:when>
			<c:when test="${empty feedbacksReceived}">
				You did not receive feedback in the selected review cycle.
				</c:when>
			<c:otherwise>
				<div class="table">
					<table id="feedbackTableReceived">
						<thead>
							<tr>
								<th width="25%">On</th>
								<th width="25%">Given By</th>
								<th width="25%">Given Date(YYYY-MM-DD)</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${feedbacksReceived}" var="feedbackRequest"
								varStatus="status">
								<tr>
									<td><a
										onclick=" show('${feedbackRequest.feedbackRequestId}', '0', 'selfReceived',${root})"
										href="javascript:void(0)">
											${feedbackRequest.requestedOnEmployee} </a></td>
									<td>${feedbackRequest.requestedToEmployee}</td>
									<td><fmt:formatDate
											value="${feedbackRequest.requestedDate}" pattern="yyyy-MM-dd" /></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<br />
		<br />
	</c:when>

	<%--User is viewing the feedbacks inside the appraisal--%>
	<c:when test="${type=='request' and user=='sender' and location=='in'}">
		<c:choose>
		<c:when test="${selectReviewCycle}">
				Please Select Review Cycle.
				</c:when>
			<c:when test="${empty feedbackRequests}">
				No feedbacks available.
				</c:when>
			<c:otherwise>
				<table id="feedbackTable">
					<thead>
						<tr>
							<th width="20%">Feedback From</th>
							<th width="20%">Requested By </th>
							<th width="15%">Type</th>
							<th width="15%">Requested Date</th>
							<th width="15%">Due Date</th>
							<th width="15%">Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${feedbackRequests}" var="feedbackRequest"
							varStatus="status">
							<tr>
								<td>${feedbackRequest.requestedToEmployee}</td>
								<td>${feedbackRequest.requestedByEmployee}</td>
								<td><c:choose>
										<c:when test="${feedbackRequest.type == 'request'}">			                                  
						                                                   Request			      
						                                  </c:when>
										<c:otherwise>
											Change Of MGR.
										</c:otherwise>
									</c:choose></td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestedDate}" pattern="dd-MM-yy" /></td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestDueDate}" pattern="dd-MM-yy" /></td>
								<td><c:choose>
										<c:when test="${feedbackRequest.isPendingAssistance == true}">			                                  
						                                                   Pending			      
						                                  </c:when>
										<c:otherwise>
											<a
												onclick=" show('${feedbackRequest.feedbackRequestId}', ${reviewFormInfoVO.reviewHeaderId}, '${feedbackRequest.type}',${root})"
												href="javascript:void(0)"> Submitted </a>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</c:when>
	<%--(Create Feedback requests) User is viewing the feedbacks out side of the appraisal--%>
	<c:when
		test="${type=='request' and user=='sender' and location=='out'}">
		<c:choose>
		<c:when test="${selectReviewCycle}">
				Please Select Review Cycle.
				</c:when>
			<c:when test="${empty feedbackRequests}">
				You did not send any feedback requests in selected review cycle.
				</c:when>
			<c:otherwise>
				<table id="feedbackTable">
					<thead>
						<tr>
							<th width="20%">Requested to</th>
							<th width="20%">On</th>
							<th width="15%">Type*</th>
							<th width="15%">Requested Date</th>
							<th width="15%">Due Date</th>
							<th width="15%">Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${feedbackRequests}" var="feedbackRequest"
							varStatus="status">
							<tr>
								<td>${feedbackRequest.requestedToEmployee}</td>
								<td>${feedbackRequest.requestedOnEmployee}</td>
								<td><c:choose>
										<c:when test="${feedbackRequest.type == 'request'}">			                                  
						                                                   Request			      
						                                  </c:when>
										<c:otherwise>
											Change Of Mgr.
										</c:otherwise>
									</c:choose></td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestedDate}" pattern="dd-MM-yy" /></td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestDueDate}" pattern="dd-MM-yy" /></td>
								<td><c:choose>
										<c:when test="${feedbackRequest.isPendingAssistance == true}">			                                  
						                                                   Pending			      
						                                  </c:when>
										<c:otherwise>
											<a
												onclick=" show('${feedbackRequest.feedbackRequestId}','0', '${feedbackRequest.type}',${root})"
												href="javascript:void(0)"> Submitted </a>
										</c:otherwise>
									</c:choose></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</c:when>


	<c:when
		test="${type=='request' and user=='receiver' and location=='out'}">
		<c:choose>
		<c:when test="${selectReviewCycle}">
				Please Select Review Cycle.
				</c:when>
			<c:when test="${empty feedbackRequests}">
				You did not receive any feedback requests in the selected review cycle.
				</c:when>
			<c:otherwise>

				<table id="feedbackTable">
					<thead>
						<tr>
							<th width="20%">From</th>
							<th width="20%">On</th>
							<th width="15%">Type*</th>
							<th width="15%">Requested Date</th>
							<th width="15%">Due Date</th>
							<th width="15%">Status</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${feedbackRequests}" var="feedbackRequest"
							varStatus="status">

							<tr>

								<td>${feedbackRequest.requestedByEmployee}</td>
								<td>${feedbackRequest.requestedOnEmployee}</td>
								<td><c:choose>
										<c:when test="${feedbackRequest.type == 'request'}">			                                  
						                                                   Request			      
						                                  </c:when>
										<c:otherwise>
											Change Of Mgr.
										</c:otherwise>
									</c:choose></td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestedDate}" pattern="yyyy-MM-dd" />
								</td>
								<td><fmt:formatDate
										value="${feedbackRequest.requestDueDate}" pattern="yyyy-MM-dd" />
								</td>

								<td><c:choose>

										<c:when test="${feedbackRequest.isPendingAssistance == true}">
										<a
												onclick=" pendingByRequestId('${feedbackRequest.feedbackRequestId}', '${feedbackRequest.type}',${root})"
												href="javascript:void(0)"> Pending </a>			 
										</c:when>
										<c:otherwise>
											<a
												onclick=" show('${feedbackRequest.feedbackRequestId}','0','${feedbackRequest.type}',${root})"
												href="javascript:void(0)"> Submitted </a>
										</c:otherwise>

									</c:choose></td>

							</tr>

						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</c:when>
</c:choose>

<br />
<br />
