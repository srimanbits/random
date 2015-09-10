package com.ggk.hrms.review.service;
import java.util.List;

import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;


public interface ObjectiveService {
	
	List<ReviewObjectiveVO> getDefaultReviewObjectives(int empId,int designationId);
	
}
