package com.ggk.hrms.review.dao;

import java.util.Date;
import java.util.List;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ManagerEmployeeEmailVO;


public interface ReviewCycleDAO {

	public ReviewCycle getActiveReviewCycle();

	public List<DropDownVO> getReviewCycleDropDown();

	public ReviewCycle getReviewCycle(int reviewCycleId);

	public List<ManagerEmployeeEmailVO> getManagerEmployeesEmailMapToNotify(String statusMessage1, String statusMessage2, String statusMessage3, int reviewCycleId, float daysRemaining);
	
	public List<ReviewCycle> getReviewCyclesToNotify(boolean isActive, int reminderNoofDaysAfter);
	
	public int insertRow(ReviewCycle reviewCycle);

	public List<ReviewCycle> getList();

	public ReviewCycle getRowById(int reviewCycleId);

	public int updateRow(int reviewCycleId ,Date goalsSettingDueDate ,  Date goalsApprovalDueDate, Date goalsAcceptanceDueDate , 
			  Date selfAppraisalDueDate ,Date appraisalDiscussionDueDate , Date appraisalDueDate, Date reviewCycleStartDate , Date reviewCycleEndDate , boolean isActive,
			  Date lastModifiedDate );

	public int deleteRow(int reviewCycleId);

	public Employee getManagerEmailId(String mailToCc);

	public String getSharedAppraiser(ManagerEmployeeEmailVO managerEmployeeEmailVO);
	
	public Long getEmployeesCountAssignedToReviewCycle(int reviewCycleId);

	public int startReviewCycle(int employeeId, int reviewCycleId, int appraisalPeriodTypeId);
	
	public List<ReviewCycle> getActiveReviewCycles();

	public int getReviewCycleIdByName(String reviewCycleName);
	
	public List<FeedbackRequestVO> getManagerEmployeeEmailToNotifyForFeedback();
	
}
