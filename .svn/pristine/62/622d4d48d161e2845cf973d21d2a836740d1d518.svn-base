package com.ggk.hrms.review.beans.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;


@Entity
@Table(schema = "Review")
public class ReminderMessages {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MessageId")
	private int messageId;

	@Column(name = "DaysRemaining")
	private int daysRemaining;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "Message")
	private String message;

	@Column(name = "IsActive")
	private boolean isActive;
	
	@Column(name = "Subject")
	private String subject;
	
	@Column(name="LastUpdatedDate")
	private Date lastUpdatedDate;
	
	@ManyToOne
	@JoinColumn(name = "AppraisalPeriodTypeId")
	@ForeignKey(name = "FK_ReviewCycle_AppraisalPeriodTypeId")
	private AppraisalPeriodType appraisalPeriodType;

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getDaysRemaining() {
		return daysRemaining;
	}

	public void setDaysRemaining(int daysRemaining) {
		this.daysRemaining = daysRemaining;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public AppraisalPeriodType getAppraisalPeriodType() {
		return appraisalPeriodType;
	}

	public void setAppraisalPeriodType(AppraisalPeriodType appraisalPeriodType) {
		this.appraisalPeriodType = appraisalPeriodType;
	}
}
