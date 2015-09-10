package com.ggk.hrms.review.ui.form;

import java.util.List;

import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public class ReviewDevelopmentPlanForm {

private List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities;

private ReviewFormInfoVO reviewFormInfoVO;

	
	public ReviewFormInfoVO getReviewFormInfoVO() {
	return reviewFormInfoVO;
}
public void setReviewFormInfoVO(ReviewFormInfoVO reviewFormInfoVO) {
	this.reviewFormInfoVO = reviewFormInfoVO;
}
	public List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivities() {
		return reviewDevelopmentPlanActivities;
	}
	public void setReviewDevelopmentPlanActivities(
			List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities) {
		this.reviewDevelopmentPlanActivities = reviewDevelopmentPlanActivities;
	}
	
}
