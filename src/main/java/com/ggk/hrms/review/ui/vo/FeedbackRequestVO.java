package com.ggk.hrms.review.ui.vo;

import java.math.BigInteger;
import java.util.Date;

public class FeedbackRequestVO {

	private int feedbackRequestId;

	private boolean isPendingAssistance;

	private Date requestedDate;

	private Date requestDueDate;

	private String requestedByEmployee;

	private String requestedToEmployee;
	
	private String requestedToEmployeeEmail;

	private String requestedOnEmployee;
	
	//	private String reviewCycleName;
	
	private Integer reviewCycleId;
	
	private String type;
	
	private Integer requestedByEmployeeId;

	private Integer requestedToEmployeeId;

	private Integer requestedOnEmployeeId;
	
	private String actionField;

	private String requestedByEmployeeEmail;

	public FeedbackRequestVO() {

	}

	// Feedback Request
	public FeedbackRequestVO(int feedbackRequestId, boolean isPendingAssistance,
			Date requestedDate, Date requestDueDate,
			String requestedOnEmployee,String requestedByEmployee, String requestedToEmployee, String type) {

		this.feedbackRequestId = feedbackRequestId;
		this.isPendingAssistance = isPendingAssistance;
		this.requestedDate = requestedDate;
		this.requestDueDate = requestDueDate;
		this.requestedOnEmployee = requestedOnEmployee;
		this.requestedToEmployee = requestedToEmployee;
this.type=type;
this.requestedByEmployee=requestedByEmployee;
	}

	// Feedback Response
	public FeedbackRequestVO(int feedbackRequestId, Date requestedDate,
			Date requestDueDate, String requestedByEmployee,
			String requestedOnEmployee, boolean isPendingAssistance, String type) {

		this.feedbackRequestId = feedbackRequestId;
		this.isPendingAssistance = isPendingAssistance;
		this.requestedDate = requestedDate;
		this.requestDueDate = requestDueDate;
		this.requestedByEmployee = requestedByEmployee;
		this.requestedOnEmployee = requestedOnEmployee;
		this.type=type;

	}
	
	public FeedbackRequestVO(int feedbackRequestId, boolean isPendingAssistance,
			Date requestedDate, Date requestDueDate,
			String requestedOnEmployee,String requestedByEmployee, String requestedToEmployee, String type,Integer requestedOnEmployeeId,Integer requestedByEmployeeId, Integer requestedToEmployeeId) {

		this.feedbackRequestId = feedbackRequestId;
		this.isPendingAssistance = isPendingAssistance;
		this.requestedDate = requestedDate;
		this.requestDueDate = requestDueDate;
		this.requestedOnEmployee = requestedOnEmployee;
		this.requestedToEmployee = requestedToEmployee;
this.type=type;
this.requestedByEmployee=requestedByEmployee;
this.requestedOnEmployeeId= requestedOnEmployeeId;
this.requestedByEmployeeId = requestedByEmployeeId;
this.requestedToEmployeeId = requestedToEmployeeId;
	}
	
	public FeedbackRequestVO(int feedbackRequestId,Date requestedDate, Date requestDueDate,
			String requestedOnEmployee,String requestedByEmployeeEmail,String requestedByEmployee,String requestedToEmployeeEmail, String requestedToEmployee) {

		this.feedbackRequestId = feedbackRequestId;
		this.requestedDate = requestedDate;
		this.requestDueDate = requestDueDate;
		this.requestedOnEmployee = requestedOnEmployee;
		this.setRequestedByEmployeeEmail(requestedByEmployeeEmail);
		this.requestedByEmployee=requestedByEmployee;
		this.requestedToEmployeeEmail=requestedToEmployeeEmail;
		this.requestedToEmployee = requestedToEmployee;
		
		

	}
	

	public String getRequestedOnEmployee() {
		return requestedOnEmployee;
	}

	public void setRequestedOnEmployee(String requestedOnEmployee) {
		this.requestedOnEmployee = requestedOnEmployee;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setPendingAssistance(boolean isPendingAssistance) {
		this.isPendingAssistance = isPendingAssistance;
	}

	public int getFeedbackRequestId() {
		return feedbackRequestId;
	}

	public Integer getReviewCycleId() {
		return reviewCycleId;
	}

	public void setReviewCycleId(Integer reviewCycleId) {
		this.reviewCycleId = reviewCycleId;
	}

	public void setFeedbackRequestId(int feedbackRequestId) {
		this.feedbackRequestId = feedbackRequestId;
	}

	public boolean getIsPendingAssistance() {
		return this.isPendingAssistance;
	}

	public void setIsPendingAssistance(boolean isPendingAssistance) {
		this.isPendingAssistance = isPendingAssistance;
	}

	public Date getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Date getRequestDueDate() {
		return requestDueDate;
	}

	public void setRequestDueDate(Date requestDueDate) {
		this.requestDueDate = requestDueDate;
	}

	public String getRequestedByEmployee() {
		return requestedByEmployee;
	}

	public void setRequestedByEmployee(String requestedByEmployee) {
		this.requestedByEmployee = requestedByEmployee;
	}

	public String getRequestedToEmployee() {
		return requestedToEmployee;
	}

	public void setRequestedToEmployee(String requestedToEmployee) {
		this.requestedToEmployee = requestedToEmployee;
	}

	public Integer getRequestedByEmployeeId() {
		return requestedByEmployeeId;
	}

	public void setRequestedByEmployeeId(Integer requestedByEmployeeId) {
		this.requestedByEmployeeId = requestedByEmployeeId;
	}

	public Integer getRequestedToEmployeeId() {
		return requestedToEmployeeId;
	}

	public void setRequestedToEmployeeId(Integer requestedToEmployeeId) {
		this.requestedToEmployeeId = requestedToEmployeeId;
	}

	public Integer getRequestedOnEmployeeId() {
		return requestedOnEmployeeId;
	}

	public void setRequestedOnEmployeeId(Integer requestedOnEmployeeId) {
		this.requestedOnEmployeeId = requestedOnEmployeeId;
	}

	public String getActionField() {
		return actionField;
	}

	public void setActionField(String actionField) {
		this.actionField = actionField;
	}

	public String getRequestedToEmployeeEmail() {
		return requestedToEmployeeEmail;
	}

	public void setRequestedToEmployeeEmail(String requestedToEmployeeEmail) {
		this.requestedToEmployeeEmail = requestedToEmployeeEmail;
	}
	
	public String getRequestedByEmployeeEmail() {
		return requestedByEmployeeEmail;
	}

	public void setRequestedByEmployeeEmail(String requestedByEmployeeEmail) {
		this.requestedByEmployeeEmail = requestedByEmployeeEmail;
	}

	public void setRowNum(BigInteger rowNum){
	
	}


}
