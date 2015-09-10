package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.constants.ReviewFormRole;

public interface ReviewDevelopmentPlanDAO {
	
	ReviewDevelopmentPlanActivity getReviewDevelopmentPlanActivityById(int intValue);

	void remove(int reviewDevelopmentPlanActivityId);public int getNoOfDevPlans(int reviewHeaderId, ReviewFormRole owner);
	
	public int getNoOfUnApprovedDevPlans(int reviewHeaderId, ReviewFormRole owner);
	
	public List<ReviewDevelopmentPlanActivity> getReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner);
	
	public void copyReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner, String actionType);
}
