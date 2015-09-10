package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;

public class ReminderMessagesVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int messageId;
	
	private boolean isActive;

	private int daysRemaining;
	
	private String message;
	
	private String subject;
	
	private String status;
	
	private String reviewPeriod;
	
	private int appraisalPeriodTypeId;

	public int getDaysRemaining() {
		return daysRemaining;
	}

	public void setDaysRemaining(int daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReviewPeriod() {
		return reviewPeriod;
	}

	public void setReviewPeriod(String reviewPeriod) {
		this.reviewPeriod = reviewPeriod;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getAppraisalPeriodTypeId() {
		return appraisalPeriodTypeId;
	}

	public void setAppraisalPeriodTypeId(int appraisalPeriodTypeId) {
		this.appraisalPeriodTypeId = appraisalPeriodTypeId;
	}
}
