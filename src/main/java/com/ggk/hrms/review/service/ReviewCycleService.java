package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ManagerEmployeeEmailVO;
import com.ggk.hrms.review.ui.vo.ReviewCycleVO;

public interface ReviewCycleService {
	
	public ReviewCycle getActiveReviewCycle();

	public List<DropDownVO> getReviewCycleDropDown();

	public ReviewCycle getReviewCycle(int reviewCycleId);

	public Map<String, List<ManagerEmployeeEmailVO>> getManagerEmployeesEmailMapToNotify(String statusMessage1, String statusMessage2, String statusMessage3,int reviewCycleId, float pendingDays);

	public List<ReviewCycle> getReviewCyclesToNotify(boolean isActive, int reminderNoofDaysAfter);
	
	public List<ReviewCycle> getList();

	public ReviewCycleVO getRowById(int reviewCycleId);

	public int deleteRow(int reviewCycleId);
	  
	public Employee getManagerEmailId(String mailToCc);

	public Map<String, Map<String, List<ManagerEmployeeEmailVO>>> getManagerEmployeesForSharedEmailMapToNotify(
			String string, String string2, String string3, int reviewCycleId,
			float pendingDays);
	
	public Long getEmployeesCountAssignedToReviewCycle(int reviewCycleId);

	public int updateRow(ReviewCycleVO reviewCycleVO);

	public int insertRow(ReviewCycleVO reviewCycleVO);

	public int startReviewCycle(int employeeId, int reviewCycleId,
			int appraisalPeriodTypeId);
	
	public List<ReviewCycle> getActiveReviewCycles();

	public int getReviewCycleIdByName(String reviewCycleName);
	
	public List<Map<String, List<FeedbackRequestVO>>> getManagerEmployeeEmailToNotifyForFeedback();
}
