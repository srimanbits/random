package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.ui.vo.OtherAppraisalsVO;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;

public interface ReviewHeaderDAO {
	
	

	public ReviewHeader saveReviewHeader(ReviewHeader reviewHeader);

	ReviewHeader getReviewHeaderById(int reviewHeaderId);

	/*public List<ReviewFormLinkVO> getPendingReviewFormIndex(int employeeId);*/



	public ReviewHeader getReviewHeaderByEmployeeIdAndReviewCycleId(int employeeId,
			int reviewCycleId);

	

	public SharedAppraiser getActiveSharedAppraiser(
			int reviewHeaderId);

	public List<ReviewActionLogVO> getAllInternalComments(int reviewHeaderId, String reviewFormRoleDescription);

	public List<OtherAppraisalsVO> getOtherAppraisalsForEmp(int empId, int reviewCycleId);

	public List<OtherAppraisalsVO> getOtherAppraisalsForMgr(int managerEmpId,
			int reviewCycleId, int empId);

	public List<OtherAppraisalsVO> getOtherAppraisalsForSuperUser(int reviewCycleId,
			int empId);

	 List<ReviewFormLinkVO> getReviewFormLinksForExcel(int reviewCycleId);
	
	public List<ReviewFormLinkVO> getReviewFormIndexOfAll(int reviewCycleId,
			int employeeId, Integer displayLength, Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	public List<ReviewFormLinkVO> getReviewFormIndex(int employeeId,
			Integer displayLength, Integer pageDisplayLength,
			String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper,boolean pending);

	public List<ReviewFormLinkVO> getSharedReviewFormIndexByReviewCycleId(
			int employeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	public List<ReviewFormLinkVO> getReviewFormIndexOfPeersByReviewCycleId(
			int mainAppraiserEmployeeId, int reviewCycleId,
			Integer displayLength, Integer pageDisplayLength, String searchValue, String colIndex, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	public List<Integer> getLeadEmpIds();
	
}
