package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the FeedbackQuestion database table.
 * 
 */
@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.FeedbackQuestion",usage=CacheConcurrencyStrategy.READ_ONLY)
public class FeedbackQuestion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FeedbackQuestionId")
	private int feedbackQuestionId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Column(name = "Question")
	private String question;

	// bi-directional many-to-one association to FeedbackQuestionAnswer
	@OneToMany(mappedBy = "feedbackQuestion")
	private List<FeedbackQuestionAnswer> feedbackQuestionAnswers;

	public FeedbackQuestion() {
	}

	public int getFeedbackQuestionId() {
		return this.feedbackQuestionId;
	}

	public void setFeedbackQuestionId(int feedbackQuestionId) {
		this.feedbackQuestionId = feedbackQuestionId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<FeedbackQuestionAnswer> getFeedbackQuestionAnswers() {
		return this.feedbackQuestionAnswers;
	}

	public void setFeedbackQuestionAnswers(
			List<FeedbackQuestionAnswer> feedbackQuestionAnswers) {
		this.feedbackQuestionAnswers = feedbackQuestionAnswers;
	}

}