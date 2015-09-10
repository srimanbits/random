package com.ggk.hrms.review.utils;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewStatus;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public class ReviewFormInfoUtil {
	
	public static ReviewFormInfoVO getReviewFormInfoVO(ReviewHeader reviewHeader, ReviewFormRole reviewFormRole, ReviewPhase reviewPhase,Employee loginEmployee){
		ReviewFormInfoVO reviewFormInfoVO = new ReviewFormInfoVO();
		reviewFormInfoVO.setReviewHeaderId(reviewHeader.getReviewHeaderId());
		reviewFormInfoVO.setReviewFormRole(reviewFormRole);
		reviewFormInfoVO.setReviewPhase(reviewPhase);
		reviewFormInfoVO.setAppraise(reviewHeader.getEmployee()
				.getDisplayName());
		reviewFormInfoVO.setMainAppraiser(reviewHeader.getMainAppraiserEmployee()
				.getDisplayName());
		// Swaroops PMS 2.O added for ONO display
		if(reviewHeader.getMainAppraiserEmployee() != null)
		reviewFormInfoVO.setMainAppraiserId(reviewHeader.getMainAppraiserEmployee().getEmpId());
		if (reviewHeader.getEmployee().getReportingToEmployee() != null)
		reviewFormInfoVO.setCurrentManagerId(reviewHeader.getEmployee().getReportingToEmployee().getEmpId());
		
		
		
		reviewFormInfoVO.setCurrentAppraiser(loginEmployee
				.getDisplayName());
		// Setting this variable, when manager's manager is viewing appraisal.
		if(SecurityDetailsAccessor.getEmpId() != reviewHeader.getMainAppraiserEmployee().getEmpId()){
			reviewFormInfoVO.setIsManagersMgr(true);
		}
		
		ReviewStatus reviewStatus=reviewHeader.getReviewStatus();
		reviewFormInfoVO.setActualReviewStatus(reviewStatus.getReviewStatusCode());
		//added in phase 2
		
		reviewFormInfoVO.setReviewCycleName(reviewHeader.getReviewCycle()
				.getReviewCycleName());
		reviewFormInfoVO.setReviewCycleStartDate(reviewHeader.getReviewCycle()
				.getReviewCycleStartDate());
		reviewFormInfoVO.setReviewCycleEndDate(reviewHeader.getReviewCycle()
				.getReviewCycleEndDate());
		if(reviewHeader.getCurrentGrade() != null){
			reviewFormInfoVO.setGrade(reviewHeader.getCurrentGrade().getGrade());
		}
		if(reviewHeader.getCurrentDesignation() != null){
			reviewFormInfoVO.setDesignation(reviewHeader.getCurrentDesignation().getShortName());
		}
		// added in phase 2
		if (reviewFormRole == ReviewFormRole.APPRAISE) {
			reviewFormInfoVO.setReviewStatus(ReviewStatus
					.getAppraiseReviewStatus(reviewStatus));
		} else if (reviewFormRole == ReviewFormRole.APPRAISER||reviewFormRole == ReviewFormRole.SHARED_APPRAISER||reviewFormRole == ReviewFormRole.SUPERUSER) {
			reviewFormInfoVO.setReviewStatus(ReviewStatus
					.getAppraiserReviewStatus(reviewStatus));
		}
		else {
			reviewFormInfoVO.setReviewStatus(ReviewStatus
					.getSuperUserReviewStatus(reviewStatus));
			
		}
		return reviewFormInfoVO;
		
		
	}

}
