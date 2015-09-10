package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewActionLog;

public interface ReviewActionLogDAO {
	
	
	void saveReviewActionLogs(List<ReviewActionLog> reviewActionLogs);
	void saveReviewActionLog(ReviewActionLog reviewActionLog);
	
	List getReviewLog(int reviewHeaderId,String reviewFormRole, int startIndex, int pageDisplayLength, String searchValue, String sortDirection, String colIndex);
	Long getTotalActivityCount(int reviewHeaderId, String reviewFormRole);

}
