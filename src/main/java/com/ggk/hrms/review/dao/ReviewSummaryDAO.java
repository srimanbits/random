package com.ggk.hrms.review.dao;

import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;

public interface ReviewSummaryDAO {
	
	ReviewSummary getReviewSummary(int reviewHeaderId,ReviewFormRole reviewFormRole);
	public ReviewSummary saveReviewSummary(ReviewSummary reviewSummary);
	void copyReviewSummary(int reviewHeaderId,ReviewFormRole owner,String actionType);
	ReviewSummary saveSuperUserReviewSummary(int reviewHeaderId, Rating appraiserRating);

}
