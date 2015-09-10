package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the FeedbackQuestionAnswer database table.
 * 
 */
@Entity
public class FeedbackQuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FeedbackQuestionAnswerId")
	private int feedbackQuestionAnswerId;

	// bi-directional many-to-one association to FeedbackQuestion
	@ManyToOne
	@JoinColumn(name = "FeedbackQuestionId")
	@ForeignKey(name = "FK_FeedbackQuestionAnswer_Question")
	private FeedbackQuestion feedbackQuestion;

	// bi-directional many-to-one association to FeedbackRequest
	@ManyToOne
	@JoinColumn(name = "FeedbackRequestId")
	@ForeignKey(name = "FK_FeedbackQuestionAnswer_Request")
	private FeedbackRequest feedbackRequest;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FeedbackAnswerCommentId")
	@ForeignKey(name = "FK_FeedbackQuestionAnswer_Comment")
	private Comment feedbackAnswerComment;

	public FeedbackQuestionAnswer() {
	}

	public int getFeedbackQuestionAnswerId() {
		return this.feedbackQuestionAnswerId;
	}

	public void setFeedbackQuestionAnswerId(int feedbackQuestionAnswerId) {
		this.feedbackQuestionAnswerId = feedbackQuestionAnswerId;
	}

	public FeedbackQuestion getFeedbackQuestion() {
		return this.feedbackQuestion;
	}

	public void setFeedbackQuestion(FeedbackQuestion feedbackQuestion) {
		this.feedbackQuestion = feedbackQuestion;
	}

	public FeedbackRequest getFeedbackRequest() {
		return this.feedbackRequest;
	}

	public void setFeedbackRequest(FeedbackRequest feedbackRequest) {
		this.feedbackRequest = feedbackRequest;
	}

	public Comment getFeedbackAnswerComment() {
		return feedbackAnswerComment;
	}

	public void setFeedbackAnswerComment(Comment feedbackAnswerComment) {
		this.feedbackAnswerComment = feedbackAnswerComment;
	}

}