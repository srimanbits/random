package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the FeedbackRequest database table.
 * 
 */
@Entity
public class FeedbackRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FeedbackRequestId")
	private int feedbackRequestId;

	@OneToOne
	@JoinColumn(name = "OnEmployeeId")
	@ForeignKey(name = "FK_FeedbackRequest_OnEmployee")
	private Employee feedbackOnEmployee;

	@Column(name = "IsPendingAssistance")
	private boolean isPendingAssistance;

	@Column(name = "RequestDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDueDate;

	@OneToOne
	@JoinColumn(name = "ToEmployeeId")
	@ForeignKey(name = "FK_FeedbackRequest_RequestedByEmp")
	private Employee requestedByEmployee;

	@Column(name = "RequestedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestedDate;

	@OneToOne
	@JoinColumn(name = "FromEmployeeId")
	@ForeignKey(name = "FK_FeedbackRequest_RequestedToEmp")
	private Employee requestedToEmployee;

	// bi-directional many-to-one association to FeedbackQuestionAnswer
	@OneToMany(mappedBy = "feedbackRequest" ,cascade = CascadeType.ALL)
	private List<FeedbackQuestionAnswer> feedbackQuestionAnswers;

	// bi-directional many-to-one association to ReviewHeader
	@ManyToOne
	@JoinColumn(name = "ReviewHeaderId")
	@ForeignKey(name = "FK_FeedbackRequest_ReviewHeader")
	private ReviewHeader reviewHeader;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "OverallFeedbackCommentId" )
	@ForeignKey(name = "FK_FeedbackRequest_OverallFeedbackComment")
	private Comment OverallFeedbackComment;

//	
//	@OneToOne
//	@JoinColumn(name = "TargetEmployeeId")
//	@ForeignKey(name = "FK_FeedbackRequest_TargetEmp")
//	private Employee targetEmployee;
	
	@Column(name="Type")
	private String type;
	
	public FeedbackRequest() {
	}

	public int getFeedbackRequestId() {
		return this.feedbackRequestId;
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

	public Date getRequestDueDate() {
		return this.requestDueDate;
	}

	public void setRequestDueDate(Date requestDueDate) {
		this.requestDueDate = requestDueDate;
	}

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public List<FeedbackQuestionAnswer> getFeedbackQuestionAnswers() {
		return this.feedbackQuestionAnswers;
	}

	public void setFeedbackQuestionAnswers(
			List<FeedbackQuestionAnswer> feedbackQuestionAnswers) {
		this.feedbackQuestionAnswers = feedbackQuestionAnswers;
	}

	public ReviewHeader getReviewHeader() {
		return this.reviewHeader;
	}

	public void setReviewHeader(ReviewHeader reviewHeader) {
		this.reviewHeader = reviewHeader;
	}

	public Employee getFeedbackOnEmployee() {
		return feedbackOnEmployee;
	}

	public void setFeedbackOnEmployee(Employee feedbackOnEmployee) {
		this.feedbackOnEmployee = feedbackOnEmployee;
	}

	public Employee getRequestedByEmployee() {
		return requestedByEmployee;
	}

	public void setRequestedByEmployee(Employee requestedByEmployee) {
		this.requestedByEmployee = requestedByEmployee;
	}

	public Employee getRequestedToEmployee() {
		return requestedToEmployee;
	}

	public void setRequestedToEmployee(Employee requestedToEmployee) {
		this.requestedToEmployee = requestedToEmployee;
	}

	public void setPendingAssistance(boolean isPendingAssistance) {
		this.isPendingAssistance = isPendingAssistance;
	}
	

	public Comment getOverallFeedbackComment() {
		return OverallFeedbackComment;
	}

	public void setOverallFeedbackComment(Comment overallFeedbackComment) {
		OverallFeedbackComment = overallFeedbackComment;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



}