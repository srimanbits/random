package com.ggk.hrms.review.service;

import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewStatus;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public interface ReviewFormService {
	
	ReviewFormRole getReviewFormRole(ReviewHeader reviewHeader);
	ReviewPhase getReviewPhase(ReviewStatus reviewStatus);
	void changeReviewStatus(ReviewHeader reviewHeader,ReviewFormRole reviewFormRole);
	public String validateAppraisal(int reviewHeaderId,ReviewFormInfoVO reviewFormInfoVO);
	public ReviewFormInfoVO getReviewFromInfoVO(int reviewHeaderId ,int empId, String opsType);
	public boolean isEmp1MGRofEmp2(int mgrId, int empId);
	boolean isLoggedInUserHasAccesstoIt(int reviewHeaderId);
	boolean isLoggedInUserHasAccesstoIt(ReviewHeader reviewHeader);
	boolean isGivenEmpIdHasAccesstoIt(Integer empId,ReviewHeader reviewHeader);
	boolean isGivenEmpIdHasAccesstoIt(Integer empId,Integer reviewHeaderId);


}
