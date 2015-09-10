package com.ggk.hrms.review.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewStatus;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.constants.Role;
import com.ggk.hrms.review.controller.ReviewCompetencyFormController;
import com.ggk.hrms.review.dao.ReviewFormDAO;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.SharedAppraiserService;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.PMSAppConstants;
import com.ggk.hrms.review.utils.ReviewFormInfoUtil;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Service("reviewFormService")
public class ReviewFormServiceImpl implements ReviewFormService {
	
	@Resource
	private SharedAppraiserService sharedAppraiserService;
	
	@Resource
	private ReviewFormDAO reviewFormDAO;
	
	@Resource
	private EmployeeService  employeeService;
	
	@Resource
	private ReviewHeaderService reviewHeaderService;
	
	private Logger logger = Logger.getLogger(ReviewFormServiceImpl.class);

	@Override
	public ReviewFormRole getReviewFormRole(ReviewHeader reviewHeader) {
		if (SecurityDetailsAccessor.getEmpId() == reviewHeader.getEmployee()
				.getEmpId()) {
			return ReviewFormRole.APPRAISE;
		}
		if (SecurityDetailsAccessor.getAuthorities().contains(
				Role.ROLE_SUPERUSER)) {
			return ReviewFormRole.SUPERUSER;
		}

		if (isLoggedInUserHasAccesstoIt(reviewHeader) == true) {
			return ReviewFormRole.APPRAISER;
		}
		
		if (sharedAppraiserService.getActiveSharedAppraiser(reviewHeader
				.getReviewHeaderId()) != null) {
			if (SecurityDetailsAccessor.getEmpId() == sharedAppraiserService
					.getActiveSharedAppraiser(reviewHeader.getReviewHeaderId())
					.getAssignedToEmployee().getEmpId()) {
				return ReviewFormRole.SHARED_APPRAISER;
			}
		}
		return null;
	}

	public ReviewPhase getReviewPhase(ReviewStatus reviewStatus) {
		if (ReviewStatus.APPRAISE_PHASE1_STATUS.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.APPRAISE_IN_PHASE1;
		} else if (ReviewStatus.APPRAISE_PHASE2_STATUS.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.APPRAISE_IN_PHASE2;
		} else if (ReviewStatus.APPRAISER_PHASE1_STATUS.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.APPRAISER_IN_PHASE1;
		} else if (ReviewStatus.APPRAISER_PHASE2_STATUS.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.APPRAISER_IN_PHASE2;
		} else if (ReviewStatus.SYSTEM_PHASE1_COMPLETED.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.SYSTEM_PHASE1_COMPLETED;
		} else if (ReviewStatus.SYSTEM_PHASE2_COMPLETED.contains(reviewStatus
				.getReviewStatusCode())) {
			return ReviewPhase.SYSTEM_PHASE2_COMPLETED;
		}
		return null;
	}

	@Override
	public void changeReviewStatus(ReviewHeader reviewHeader,ReviewFormRole reviewFormRole) {
		String currentReviewStatusCode = reviewHeader.getReviewStatus()
				.getReviewStatusCode();

		if (reviewFormRole.getDescription()
				.equals(PMSAppConstants.APPRAISE)) {
			
			if (currentReviewStatusCode.equals(PMSAppConstants.NOT_STARTED)){
				
				reviewHeader.setReviewStatus(ReviewStatus.GOALS_SETTING_IN_PROGRESS);
			}
			
			if (currentReviewStatusCode.equals(PMSAppConstants.GOALS_ACCEPTED)){
				
				reviewHeader.setReviewStatus(ReviewStatus.APPRAISAL_IN_PROGRESS);
			}

		}
		
		if (reviewFormRole.getDescription()
				.equals(PMSAppConstants.APPRAISER)||reviewFormRole.getDescription()
				.equals(PMSAppConstants.SUPERUSER)||reviewFormRole.getDescription()
				.equals(PMSAppConstants.SHARED_APPRAISER)) {
			
			if (currentReviewStatusCode.equals(PMSAppConstants.APPRAISAL_SUBMITTED)) {
				reviewHeader.setReviewStatus(ReviewStatus.ASSESSMENT_IN_PROGRESS);

			}
			
		}
		

	}

	@Override
	public String validateAppraisal(int reviewHeaderId,ReviewFormInfoVO reviewFormInfoVO) {
		return reviewFormDAO.validateAppraisal(reviewHeaderId,reviewFormInfoVO);
	}

	@Override
	public ReviewFormInfoVO getReviewFromInfoVO(int reviewHeaderId,int empId,
			String opsType) {
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);

		ReviewFormRole reviewFormRole = 
				getReviewFormRole(reviewHeader);
		if (reviewFormRole == null) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}

		if (opsType != null && opsType.equals("save")) {

			changeReviewStatus(reviewHeader, reviewFormRole);
		}

		ReviewPhase reviewPhase = 
			getReviewPhase(reviewHeader
				.getReviewStatus());
		Employee loginEmployee = employeeService
				.getEmployeeById(empId);
		ReviewFormInfoVO reviewFormInfoVO = ReviewFormInfoUtil
				.getReviewFormInfoVO(reviewHeader, reviewFormRole, reviewPhase,
						loginEmployee);
		return reviewFormInfoVO;
	}
	@Override
	public boolean isEmp1MGRofEmp2(int mgrId , int empId){
		if (mgrId == empId) return true;
		
		Employee manager = employeeService.getEmployeeById(empId);
		if( manager != null ){
			
			while (manager.getReportingToEmployee() != null) {

				if (manager.getReportingToEmployee().getEmpId() == mgrId) {

					return true;
				}
				manager = manager.getReportingToEmployee();
			}
			
		}
		
		return false;
	}

	@Override
	public boolean isLoggedInUserHasAccesstoIt(int reviewHeaderId) {
		ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(reviewHeaderId);
		return isLoggedInUserHasAccesstoIt(reviewHeader);
	}

	@Override
	public boolean isLoggedInUserHasAccesstoIt(ReviewHeader reviewHeader) {
	 return isGivenEmpIdHasAccesstoIt(SecurityDetailsAccessor.getEmpId(), reviewHeader);
	}

	@Override
	public boolean isGivenEmpIdHasAccesstoIt(Integer empId,
			ReviewHeader reviewHeader) {
		if (reviewHeader == null)
			return false;
		if (empId == reviewHeader
				.getMainAppraiserEmployee().getEmpId()) {
			return true;
		}

		ReviewHeader managerReviewHeader = reviewHeaderService.getReviewHeader(
				reviewHeader.getMainAppraiserEmployee().getEmpId(),
				reviewHeader.getReviewCycle().getReviewCycleId());

		while (managerReviewHeader != null
				&& managerReviewHeader.getMainAppraiserEmployee() != null) {

			if (managerReviewHeader.getMainAppraiserEmployee().getEmpId() == empId) {

				return true;
			}
			managerReviewHeader = reviewHeaderService.getReviewHeader(
					managerReviewHeader.getMainAppraiserEmployee().getEmpId(),
					reviewHeader.getReviewCycle().getReviewCycleId());
		}
		return false;
	}

	@Override
	public boolean isGivenEmpIdHasAccesstoIt(Integer empId,
			Integer reviewHeaderId) {
		ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(reviewHeaderId);
		return isGivenEmpIdHasAccesstoIt(empId, reviewHeader);
	}
	
}
