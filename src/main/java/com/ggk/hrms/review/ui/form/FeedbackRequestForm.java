package com.ggk.hrms.review.ui.form;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ggk.hrms.review.ui.vo.EmployeeVO;
import com.ggk.hrms.review.ui.vo.FeedbackQuestionAnswerVO;

public class FeedbackRequestForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private int feedbackRequestId;

	private int reviewHeaderId;
	
	private int reviewCycleId;

	private Date requestedDate;

	private Date requestDueDate;

	private String action;
	
	private String type;

	private EmployeeVO requestedByEmployee;

	private EmployeeVO feedbackOnEmployee;
	
	private String feedbackOnEmployeeIds;
	
	private String reviewCycleName;

	private EmployeeVO requestedToEmployee;
	
	private EmployeeVO targetToEmployee;

 private List<FeedbackQuestionAnswerVO> feedbackQuestionAnswerVOs;
 
 private List<Integer> selectedFeedbackQuestionnaire;

	private int overallFeedbackCommentId;

	private String overallFeedbackComment;

	public FeedbackRequestForm() {
	}
	
	

	public List<Integer> getSelectedFeedbackQuestionnaire() {
		return selectedFeedbackQuestionnaire;
	}



	public void setSelectedFeedbackQuestionnaire(
			List<Integer> selectedFeedbackQuestionnaire) {
		this.selectedFeedbackQuestionnaire = selectedFeedbackQuestionnaire;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public int getReviewCycleId() {
		return reviewCycleId;
	}



	public String getFeedbackOnEmployeeIds() {
		return feedbackOnEmployeeIds;
	}



	public void setFeedbackOnEmployeeIds(String feedbackOnEmployeeIds) {
		this.feedbackOnEmployeeIds = feedbackOnEmployeeIds;
	}



	public void setReviewCycleId(int reviewCycleId) {
		this.reviewCycleId = reviewCycleId;
	}



	public int getFeedbackRequestId() {
		return feedbackRequestId;
	}

	public void setFeedbackRequestId(int feedbackRequestId) {
		this.feedbackRequestId = feedbackRequestId;
	}

	public int getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
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

	public EmployeeVO getRequestedByEmployee() {
		return requestedByEmployee;
	}

	public void setRequestedByEmployee(EmployeeVO requestedByEmployee) {
		this.requestedByEmployee = requestedByEmployee;
	}

	public EmployeeVO getFeedbackOnEmployee() {
		return feedbackOnEmployee;
	}

	public void setFeedbackOnEmployee(EmployeeVO feedbackOnEmployee) {
		this.feedbackOnEmployee = feedbackOnEmployee;
	}

	public EmployeeVO getRequestedToEmployee() {
		return requestedToEmployee;
	}

	public void setRequestedToEmployee(EmployeeVO requestedToEmployee) {
		this.requestedToEmployee = requestedToEmployee;
	}

	
	
	
	public EmployeeVO getTargetToEmployee() {
		return targetToEmployee;
	}



	public void setTargetToEmployee(EmployeeVO targetToEmployee) {
		this.targetToEmployee = targetToEmployee;
	}



	public List<FeedbackQuestionAnswerVO> getFeedbackQuestionAnswerVOs() {
		return feedbackQuestionAnswerVOs;
	}

	public void setFeedbackQuestionAnswerVOs(
			List<FeedbackQuestionAnswerVO> feedbackQuestionAnswerVOs) {
		this.feedbackQuestionAnswerVOs = feedbackQuestionAnswerVOs;
	}

	
	
	public int getOverallFeedbackCommentId() {
		return overallFeedbackCommentId;
	}

	public void setOverallFeedbackCommentId(int overallFeedbackCommentId) {
		this.overallFeedbackCommentId = overallFeedbackCommentId;
	}

	public String getOverallFeedbackComment() {
		return overallFeedbackComment;
	}

	public void setOverallFeedbackComment(String overallFeedbackComment) {
		this.overallFeedbackComment = overallFeedbackComment;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}



	public String getReviewCycleName() {
		return reviewCycleName;
	}



	public void setReviewCycleName(String reviewCycleName) {
		this.reviewCycleName = reviewCycleName;
	}
	

}