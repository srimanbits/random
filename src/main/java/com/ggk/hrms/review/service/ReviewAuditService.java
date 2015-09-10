package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public interface ReviewAuditService {
	
	public void saveReviewActionLog(ReviewActionLog reviewActionLog);
	public void saveReviewActionLog(List<ReviewActionLog> reviewActionLogs);
	public  List<ReviewActionLog> getReviewActionLog(Class clazz, List initialList, List updatedList,ReviewFormInfoVO reviewFormInfoVO);
	
	public List<ReviewActionLogVO> getReviewActionLog(int reviewHeaderId,String reviewFormRole, int startIndex, int pageDisplayLength, String searchValue, String sortDirection, String colIndex, DataTablesJSONWrapper reviewFormLinkVOWrapper);
	public void substituteRatingValuesWithRatingIds(
			List reviewEntities,
			Map<Integer, String>  dropdown,Class clazz);
	public void substituteRatingIdsWithRatingValues(
			List reviewEntities,
			Map<Integer, String> dropdown ,Class clazz);
	public ReviewActionLog createReviewActionLog(
			ReviewFormInfoVO reviewFormInfoVO, String actionType,
			ReviewFeildGroupType reviewFeildGroupType,
			String reviewFieldGroupName, String fieldName, String initialValue,
			String updatedValue, String userCommentText);
	public List<ReviewActionLog> getReviewActionLog(Class clazz,
			Object initialObject, Object updatedObject,
			ReviewFormInfoVO reviewFormInfoVO);

}
