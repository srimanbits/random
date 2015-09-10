package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;

public class FeedbackQuestionAnswerVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private int feedbackQuestionId;

	private String feedbackQuestion;

	private int feedbackAnswerId;

	private String feedbackAnswer;

	public FeedbackQuestionAnswerVO() {
	}

	public int getFeedbackQuestionId() {
		return feedbackQuestionId;
	}

	public void setFeedbackQuestionId(int feedbackQuestionId) {
		this.feedbackQuestionId = feedbackQuestionId;
	}

	public String getFeedbackQuestion() {
		return feedbackQuestion;
	}

	public void setFeedbackQuestion(String feedbackQuestion) {
		this.feedbackQuestion = feedbackQuestion;
	}

	public int getFeedbackAnswerId() {
		return feedbackAnswerId;
	}

	public void setFeedbackAnswerId(int feedbackAnswerId) {
		this.feedbackAnswerId = feedbackAnswerId;
	}

	public String getFeedbackAnswer() {
		return feedbackAnswer;
	}

	public void setFeedbackAnswer(String feedbackAnswer) {
		this.feedbackAnswer = feedbackAnswer;
	}

}
