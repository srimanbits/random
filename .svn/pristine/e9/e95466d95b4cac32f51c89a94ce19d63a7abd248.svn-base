package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;

public class ReviewDevelopmentPlanActivityVO implements Serializable,
		Comparable<ReviewDevelopmentPlanActivityVO> {
	private static final long serialVersionUID = 1L;

	private Integer reviewHeaderId;

	private Integer reviewDevelopmentPlanActivityId;

	private String actionStepsComment;

	private String appraiseComment;

	private String appraiserComment;

	private String goalComment;

	private String measurementComment;
	
	private Boolean isApproved;

	public ReviewDevelopmentPlanActivityVO() {
	}
	
	

	public Boolean getIsApproved() {
		return isApproved;
	}



	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}



	public Integer getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(Integer reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

	public Integer getReviewDevelopmentPlanActivityId() {
		return reviewDevelopmentPlanActivityId;
	}

	public void setReviewDevelopmentPlanActivityId(
			Integer reviewDevelopmentPlanActivityId) {
		this.reviewDevelopmentPlanActivityId = reviewDevelopmentPlanActivityId;
	}

	public String getActionStepsComment() {
		return actionStepsComment;
	}

	public void setActionStepsComment(String actionStepsComment) {
		this.actionStepsComment = actionStepsComment;
	}

	public String getAppraiseComment() {
		return appraiseComment;
	}

	public void setAppraiseComment(String appraiseComment) {
		this.appraiseComment = appraiseComment;
	}

	public String getAppraiserComment() {
		return appraiserComment;
	}

	public void setAppraiserComment(String appraiserComment) {
		this.appraiserComment = appraiserComment;
	}

	public String getGoalComment() {
		return goalComment;
	}

	public void setGoalComment(String goalComment) {
		this.goalComment = goalComment;
	}

	public String getMeasurementComment() {
		return measurementComment;
	}

	public void setMeasurementComment(String measurementComment) {
		this.measurementComment = measurementComment;
	}

	@Override
	public int compareTo(
			ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO) {
		

		if (this.reviewDevelopmentPlanActivityId == reviewDevelopmentPlanActivityVO.reviewDevelopmentPlanActivityId) {

			return 0;
		}
		if (this.reviewDevelopmentPlanActivityId > reviewDevelopmentPlanActivityVO.reviewDevelopmentPlanActivityId) {

			return 1;
		}
		else

			return -1;
		
		
	}

}