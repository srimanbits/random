package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public interface ReviewDevelopmentPlanService {

	// List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivities(int reviewHeaderId);

	List<ReviewDevelopmentPlanActivityVO> saveReviewDevelopmentPlanActivities( List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities,ReviewFormInfoVO reviewFormInfoVO);

	ReviewDevelopmentPlanActivity getReviewDevelopmentPlanActivity(int reviewDevelopmentPlanActivityId);
	
	boolean remove(int reviewDevelopmentPlanActivityId);
	
	public List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivityVOByHeaderId(int reviewHeaderId, ReviewFormRole owner,String requestType);
	
	public int getNoOfDevPlans(int reviewHeaderId, ReviewFormRole owner);
	
	public int getNoOfUnApprovedDevPlans(int reviewHeaderId, ReviewFormRole owner);
	
	public List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner);
	
	public void copyReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner, String requestType);
}
