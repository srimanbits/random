package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.AppraisalPeriodType;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.dao.ReviewCycleDAO;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ManagerEmployeeEmailVO;
import com.ggk.hrms.review.ui.vo.ReviewCycleVO;
import com.ggk.hrms.review.utils.PMSAppConstants;

@Service("reviewCycleService")
public class ReviewCycleServiceImpl implements ReviewCycleService {

	@Resource
	private ReviewCycleDAO reviewCycleDAO;
	
	Logger logger = Logger.getLogger(ReviewCycleServiceImpl.class);
	
	
	@Override
	public ReviewCycle getActiveReviewCycle() {
		logger.debug("ReviewCycleServiceImpl.getActiveReviewCycle()........... Making a DB call to get active review cycle.");
		return reviewCycleDAO.getActiveReviewCycle() ;
	}
	
	@Override
	public Long getEmployeesCountAssignedToReviewCycle(int reviewCycleId) {
		logger.debug("ReviewCycleServiceImpl.getEmployeesCountAssignedToReviewCycle()........... Making a DB call to get no. of employees assigned to a review cycle.");
		return reviewCycleDAO.getEmployeesCountAssignedToReviewCycle(reviewCycleId);
	}


	@Override
	public List<DropDownVO> getReviewCycleDropDown() {
		logger.debug("ReviewCycleServiceImpl.getReviewCycleDropDown()...........Making a DB call to get Review cycle dropdown");
		return reviewCycleDAO.getReviewCycleDropDown();
	}


	@Override
	public ReviewCycle getReviewCycle(int reviewCycleId) {
		logger.debug("ReviewCycleServiceImpl.getReviewCycle()...........Making a DB call to get Review cycle "+reviewCycleId);
		return reviewCycleDAO.getReviewCycle(reviewCycleId) ;
	}

	@Override
	public Map<String, List<ManagerEmployeeEmailVO>> getManagerEmployeesEmailMapToNotify(String statusMessage1, String statusMessage2, String statusMessage3, int reviewCycleId, float daysRemaining) {
		
		List<ManagerEmployeeEmailVO> managerEmployeeEmailVOList = reviewCycleDAO.getManagerEmployeesEmailMapToNotify(statusMessage1,statusMessage2, statusMessage3, reviewCycleId, daysRemaining);
		Map<String, List<ManagerEmployeeEmailVO>> managerEmployeeEmailIdsMap = new HashMap<String, List<ManagerEmployeeEmailVO>>();
			for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeeEmailVOList) {
				if(managerEmployeeEmailIdsMap.containsKey(managerEmployeeEmailVO.getManagerEmailId())) {
					List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = managerEmployeeEmailIdsMap.get(managerEmployeeEmailVO.getManagerEmailId());
					employeeNameOrEmailIds.add(managerEmployeeEmailVO);
				} else {
					List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = new ArrayList<ManagerEmployeeEmailVO>();
					employeeNameOrEmailIds.add(managerEmployeeEmailVO);
					managerEmployeeEmailIdsMap.put(managerEmployeeEmailVO.getManagerEmailId(), employeeNameOrEmailIds);
				}
			}
		
		return managerEmployeeEmailIdsMap;
	}
	
	
	@Override
	public Map<String, Map<String,List<ManagerEmployeeEmailVO>>> getManagerEmployeesForSharedEmailMapToNotify(String statusMessage1, String statusMessage2, String statusMessage3, int reviewCycleId, float daysRemaining) {
		
		List<ManagerEmployeeEmailVO> managerEmployeeEmailVOList = reviewCycleDAO.getManagerEmployeesEmailMapToNotify(statusMessage1,statusMessage2, statusMessage3, reviewCycleId, daysRemaining);
		Map<String, List<ManagerEmployeeEmailVO>> managerEmployeeEmailIdsMap = new HashMap<String, List<ManagerEmployeeEmailVO>>();
			for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeeEmailVOList) {
				if(managerEmployeeEmailIdsMap.containsKey(managerEmployeeEmailVO.getManagerEmailId())) {
					List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = managerEmployeeEmailIdsMap.get(managerEmployeeEmailVO.getManagerEmailId());
					employeeNameOrEmailIds.add(managerEmployeeEmailVO);
				} else {
					List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = new ArrayList<ManagerEmployeeEmailVO>();
					employeeNameOrEmailIds.add(managerEmployeeEmailVO);
					managerEmployeeEmailIdsMap.put(managerEmployeeEmailVO.getManagerEmailId(), employeeNameOrEmailIds);
				}
			}
			return getManagerSharedAppraiserEmployeesEmailMapToNotify(managerEmployeeEmailIdsMap);
		
	}
	
	public Map<String, Map<String,List<ManagerEmployeeEmailVO>>> getManagerSharedAppraiserEmployeesEmailMapToNotify(Map<String, List<ManagerEmployeeEmailVO>> managerEmployeeEmailIdsMap) {
		
		Set<String> managerEmails= managerEmployeeEmailIdsMap.keySet();
		for (String managerEmail : managerEmails) {
			for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeeEmailIdsMap.get(managerEmail)) {
				Log.info("shared appraiser"+reviewCycleDAO.getSharedAppraiser(managerEmployeeEmailVO));
				managerEmployeeEmailVO.setSharedAppraiserEmailId(reviewCycleDAO.getSharedAppraiser(managerEmployeeEmailVO));
			}
			
		}
		Map<String, Map<String,List<ManagerEmployeeEmailVO>>> managerEmployeeSharedAppraiserEmailIdsMap = new HashMap<String, Map<String,List<ManagerEmployeeEmailVO>>>();
		Map<String, List<ManagerEmployeeEmailVO>> sharedManagerEmployeeList = new HashMap<String, List<ManagerEmployeeEmailVO>>();
		
		for (String managerEmail : managerEmails) {
			List<ManagerEmployeeEmailVO> managerEmployeeEmailVOList = managerEmployeeEmailIdsMap.get(managerEmail);
			
			for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeeEmailVOList) {
				
				if(managerEmployeeSharedAppraiserEmailIdsMap.containsKey(managerEmployeeEmailVO.getManagerEmailId())) {
					if(sharedManagerEmployeeList.containsKey(managerEmployeeEmailVO.getSharedAppraiserEmailId())){
						
						Map<String, List<ManagerEmployeeEmailVO>> getmanagerEmployeeEmailIdsMap= managerEmployeeSharedAppraiserEmailIdsMap.get(managerEmail);
						List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = getmanagerEmployeeEmailIdsMap.get(managerEmployeeEmailVO.getSharedAppraiserEmailId());
						employeeNameOrEmailIds.add(managerEmployeeEmailVO);
					}
					else{
						List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = new ArrayList<ManagerEmployeeEmailVO>();
						employeeNameOrEmailIds.add(managerEmployeeEmailVO);
						Map<String, List<ManagerEmployeeEmailVO>> existingSharedManagerEmployeeList = managerEmployeeSharedAppraiserEmailIdsMap.get(managerEmployeeEmailVO.getManagerEmailId());
						existingSharedManagerEmployeeList.put(managerEmployeeEmailVO.getSharedAppraiserEmailId(), employeeNameOrEmailIds);
						sharedManagerEmployeeList.put(managerEmployeeEmailVO.getSharedAppraiserEmailId(), employeeNameOrEmailIds);
					}
					
				} else {
					
					List<ManagerEmployeeEmailVO> employeeNameOrEmailIds = new ArrayList<ManagerEmployeeEmailVO>();
					employeeNameOrEmailIds.add(managerEmployeeEmailVO);
					Map<String,List<ManagerEmployeeEmailVO>> employeeNameWithSharedAppraiser = new HashMap<String, List<ManagerEmployeeEmailVO>>();
					employeeNameWithSharedAppraiser.put(managerEmployeeEmailVO.getSharedAppraiserEmailId(), employeeNameOrEmailIds);
					managerEmployeeSharedAppraiserEmailIdsMap.put(managerEmployeeEmailVO.getManagerEmailId(), employeeNameWithSharedAppraiser);
					sharedManagerEmployeeList.put(managerEmployeeEmailVO.getSharedAppraiserEmailId(), employeeNameOrEmailIds);
				}
			}
		}
		
		return managerEmployeeSharedAppraiserEmailIdsMap;
	}
	
	//@Cacheable(value ="reviewCycle", key = "'getReviewCyclesToNotify_'+#isActive+'_'+#reminderNoofDaysAfter")
	public List<ReviewCycle> getReviewCyclesToNotify(boolean isActive, int reminderNoofDaysAfter) {
		return reviewCycleDAO.getReviewCyclesToNotify(isActive, reminderNoofDaysAfter);
	}
	
	
	@Override
	public int insertRow(ReviewCycleVO reviewCycleVO) {
		ReviewCycle reviewCycle = new ReviewCycle();
		convertVOToDomain(reviewCycleVO, reviewCycle);
		reviewCycle.setReviewCycleStatus(PMSAppConstants.REVIEW_CYCLE_CREATED);
		return reviewCycleDAO.insertRow(reviewCycle);
	}

	public List<ReviewCycle> getList() {
	   return reviewCycleDAO.getList();
	  }

	  @Override
	public ReviewCycleVO getRowById(int reviewCycleId) {
		ReviewCycle reviewCycle = reviewCycleDAO.getRowById(reviewCycleId);
		return convertDomainToVO(reviewCycle);
	}
	  
	  private void convertVOToDomain(ReviewCycleVO reviewCycleVO,
				ReviewCycle reviewCycle) {
		  reviewCycle.setAppraisalDueDate(reviewCycleVO.getAppraisalDueDate());
		  AppraisalPeriodType appraisalPeriodType = new AppraisalPeriodType();
		  appraisalPeriodType.setAppraisalPeriodTypeId(reviewCycleVO.getAppraisalPeriodTypeId());
		  reviewCycle.setAppraisalPeriodType(appraisalPeriodType);
		  reviewCycle.setAppraisalDiscussionDueDate(reviewCycleVO.getAppraisalDiscussionDueDate());
		  reviewCycle.setCreatedDate(reviewCycleVO.getCreatedDate());
		  reviewCycle.setGoalsApprovalDueDate(reviewCycleVO.getGoalsApprovalDueDate());
		  reviewCycle.setGoalsAcceptanceDueDate(reviewCycleVO.getGoalsAcceptanceDueDate());
		  reviewCycle.setGoalsSettingDueDate(reviewCycleVO.getGoalsSettingDueDate());
		  reviewCycle.setIsActive(false);
		  reviewCycle.setLastModifiedDate(reviewCycleVO.getLastModifiedDate());
		  reviewCycle.setReviewCycleEndDate(reviewCycleVO.getReviewCycleEndDate());
		  reviewCycle.setReviewCycleId(reviewCycleVO.getReviewCycleId());
		  reviewCycle.setReviewCycleName(reviewCycleVO.getReviewCycleName());
		  reviewCycle.setReviewCycleStartDate(reviewCycleVO.getReviewCycleStartDate());
		  reviewCycle.setSelfAppraisalDueDate(reviewCycleVO.getSelfAppraisalDueDate());
		}
	  
	  private ReviewCycleVO convertDomainToVO(ReviewCycle reviewCycle) {
		  ReviewCycleVO reviewCycleVO = new ReviewCycleVO();
		  reviewCycleVO.setAppraisalDueDate(reviewCycle.getAppraisalDueDate());
		  if (reviewCycle.getAppraisalPeriodType() != null) {
			  reviewCycleVO.setAppraisalPeriodTypeId(reviewCycle.getAppraisalPeriodType().getAppraisalPeriodTypeId());
		  }
		  reviewCycleVO.setAppraisalDiscussionDueDate(reviewCycle.getAppraisalDiscussionDueDate());
		  reviewCycleVO.setCreatedDate(reviewCycle.getCreatedDate());
		  reviewCycleVO.setGoalsApprovalDueDate(reviewCycle.getGoalsApprovalDueDate());
		  reviewCycleVO.setGoalsAcceptanceDueDate(reviewCycle.getGoalsAcceptanceDueDate());
		  reviewCycleVO.setGoalsSettingDueDate(reviewCycle.getGoalsSettingDueDate());
		  reviewCycleVO.setIsActive(reviewCycle.getIsActive());
		  reviewCycleVO.setLastModifiedDate(reviewCycle.getLastModifiedDate());
		  reviewCycleVO.setReviewCycleEndDate(reviewCycle.getReviewCycleEndDate());
		  reviewCycleVO.setReviewCycleId(reviewCycle.getReviewCycleId());
		  reviewCycleVO.setReviewCycleName(reviewCycle.getReviewCycleName());
		  reviewCycleVO.setReviewCycleStartDate(reviewCycle.getReviewCycleStartDate());
		  reviewCycleVO.setSelfAppraisalDueDate(reviewCycle.getSelfAppraisalDueDate());
		return reviewCycleVO;
	}

	@Override
	  public int updateRow(ReviewCycleVO reviewCycleVO) {
			    return reviewCycleDAO.updateRow(reviewCycleVO.getReviewCycleId(), reviewCycleVO.getGoalsSettingDueDate(), reviewCycleVO.getGoalsApprovalDueDate(),  reviewCycleVO.getGoalsAcceptanceDueDate(), 
			      reviewCycleVO.getSelfAppraisalDueDate(), reviewCycleVO.getAppraisalDiscussionDueDate(), reviewCycleVO.getAppraisalDueDate(), reviewCycleVO.getReviewCycleStartDate(), reviewCycleVO.getReviewCycleEndDate(),  reviewCycleVO.getIsActive() , reviewCycleVO.getLastModifiedDate());
	}

	  @Override
	  public int deleteRow(int reviewCycleId) {
	   return reviewCycleDAO.deleteRow(reviewCycleId);
	  }


	@Override
	//@Cacheable(value ="reviewCycle", key = "'getManagerEmailId_'+#mailToCc")
	public Employee getManagerEmailId(String mailToCc) {
		return reviewCycleDAO.getManagerEmailId(mailToCc);
	}

	@Override
	public int startReviewCycle(int employeeId, int reviewCycleId,
			int appraisalPeriodTypeId) {
		return reviewCycleDAO.startReviewCycle(employeeId, reviewCycleId, appraisalPeriodTypeId);
	}
	@Override
	public List<ReviewCycle> getActiveReviewCycles() {
	return reviewCycleDAO.getActiveReviewCycles();
	}

	@Override
	public int getReviewCycleIdByName(String reviewCycleName) {
		return reviewCycleDAO.getReviewCycleIdByName(reviewCycleName);
	}

	@Override
	public List<Map<String, List<FeedbackRequestVO>>> getManagerEmployeeEmailToNotifyForFeedback() {
		
		List<FeedbackRequestVO> feedbackRequestVOList = reviewCycleDAO.getManagerEmployeeEmailToNotifyForFeedback();
		Map<String, List<FeedbackRequestVO>> managerEmployeeEmailIdsMap = new HashMap<String, List<FeedbackRequestVO>>();
			for (FeedbackRequestVO feedbackRequestVO : feedbackRequestVOList) {
				if(managerEmployeeEmailIdsMap.containsKey(feedbackRequestVO.getRequestedToEmployeeEmail())) {
					List<FeedbackRequestVO> employeeNameOrEmailIds = managerEmployeeEmailIdsMap.get(feedbackRequestVO.getRequestedToEmployeeEmail());
					employeeNameOrEmailIds.add(feedbackRequestVO);
				} else {
					List<FeedbackRequestVO> employeeNameOrEmailIds = new ArrayList<FeedbackRequestVO>();
					employeeNameOrEmailIds.add(feedbackRequestVO);
					managerEmployeeEmailIdsMap.put(feedbackRequestVO.getRequestedToEmployeeEmail(), employeeNameOrEmailIds);
				}
			}
			
			Map<String, List<FeedbackRequestVO>> currentManagerEmployeeEmailIdsMap = new HashMap<String, List<FeedbackRequestVO>>();
			for (FeedbackRequestVO feedbackRequestVO : feedbackRequestVOList) {
				if(currentManagerEmployeeEmailIdsMap.containsKey(feedbackRequestVO.getRequestedByEmployeeEmail())) {
					List<FeedbackRequestVO> employeeNameOrEmailIds = currentManagerEmployeeEmailIdsMap.get(feedbackRequestVO.getRequestedByEmployeeEmail());
					employeeNameOrEmailIds.add(feedbackRequestVO);
				} else {
					List<FeedbackRequestVO> employeeNameOrEmailIds = new ArrayList<FeedbackRequestVO>();
					employeeNameOrEmailIds.add(feedbackRequestVO);
					currentManagerEmployeeEmailIdsMap.put(feedbackRequestVO.getRequestedByEmployeeEmail(), employeeNameOrEmailIds);
				}
			}
			
		List<Map<String, List<FeedbackRequestVO>>> emailMap=new ArrayList<Map<String, List<FeedbackRequestVO>>>();
		emailMap.add(managerEmployeeEmailIdsMap);
		emailMap.add(currentManagerEmployeeEmailIdsMap);
		return emailMap;
	}
	

}
