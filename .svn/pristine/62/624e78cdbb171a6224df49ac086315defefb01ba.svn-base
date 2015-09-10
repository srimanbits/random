package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.ui.vo.AppraisalShareInfoVO;
import com.ggk.hrms.review.ui.vo.OtherAppraisalsVO;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;
import com.ggk.hrms.review.ui.vo.ReviewHeaderVO;

public interface ReviewHeaderService {
	
	ReviewHeader saveReviewHeader(ReviewHeaderVO reviewHeaderVO, String reviewFormRole, String reviewPhase);

	
	ReviewHeader getReviewHeaderById(int reviewHeaderId);

	void merge(ReviewHeader reviewHeader);


	void convertToVO(ReviewHeaderVO reviewHeaderVO, ReviewHeader reviewHeader);


	AppraisalShareInfoVO getAppraisalShareInfo(int employeeId,
			int reviewHeaderId);


	public ReviewHeader getReviewHeader(int employeeId,
			int reviewCycleId);

	List<ReviewActionLogVO> getAllInternalComments(int reviewHeaderId, String reviewFormRoleDescription);
	
	

	List<OtherAppraisalsVO> getOtherAppraisalsForEmp(int empId, int reviewCycleId);

	List<OtherAppraisalsVO> getOtherAppraisalsForMgr(int managerEmpId, int reviewCycleId, int empId);

	List<OtherAppraisalsVO> getOtherAppraisalsForSuperUser(int reviewCycleId,
			int empId);

	 List<ReviewFormLinkVO> getReviewFormLinks(int reviewCycleId);
	
	List<ReviewFormLinkVO> getReviewFormIndexOfAll(int i, int employeeId,
			Integer displayLength, Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	List<ReviewFormLinkVO> getReviewFormIndex(int employeeId,
			Integer displayLength, Integer pageDisplayLength,
			String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper,boolean pending);

	List<ReviewFormLinkVO> getSharedReviewFormIndexByReviewCycleId(
			int employeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	List<ReviewFormLinkVO> getReviewFormIndexOfPeersByReviewCycleId(
			int employeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colIndex, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);
	
	AppraisalShareInfoVO getAppraisalShareInfo(int employeeId , ReviewFormLinkVO reviewFormLinkVO);
	
	public List<Integer> getLeadEmpIds();

}
