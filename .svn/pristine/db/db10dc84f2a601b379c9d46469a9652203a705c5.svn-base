package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AutoPopulatingList;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.dao.ReviewDevelopmentPlanDAO;
import com.ggk.hrms.review.dao.ReviewHeaderDAO;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewDevelopmentPlanService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Service(value = "reviewDevelopmentPlanService")
public class ReviewDevelopmentPlanServiceImpl implements
		ReviewDevelopmentPlanService {
	@Autowired
	ReviewDevelopmentPlanDAO reviewDevelopmentPlanDAO;
	@Autowired
	private ReviewHeaderDAO reviewHeaderDAO;
	
	@Autowired
	private ReviewHeaderService reviewHeaderService;

	@Resource
	private ReviewAuditService reviewAuditService;

	@Autowired
	private EmployeeService employeeService;

	@Override
	public List<ReviewDevelopmentPlanActivityVO> saveReviewDevelopmentPlanActivities(
			List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities,
			ReviewFormInfoVO reviewFormInfoVO) {

		// getting the reviewHeader object
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewFormInfoVO.getReviewHeaderId());
		List<ReviewDevelopmentPlanActivity> initialReviewDevelopmentPlanActivities = reviewDevelopmentPlanDAO
				.getReviewDevelopmentPlanActivities(
						reviewFormInfoVO.getReviewHeaderId(),
						reviewFormInfoVO.getReviewFormRole());
		List<ReviewDevelopmentPlanActivityVO> initialReviewDevlopmentPlanActivityVOs = new ArrayList<ReviewDevelopmentPlanActivityVO>();
		if (initialReviewDevelopmentPlanActivities.size() > 0) {
			// getting the existing developmentPlan activities for the review
			// Header		
			for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : initialReviewDevelopmentPlanActivities) {

				ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO = new ReviewDevelopmentPlanActivityVO();
				convertToVO(reviewDevelopmentPlanActivityVO,
						reviewDevelopmentPlanActivity);
				initialReviewDevlopmentPlanActivityVOs
						.add(reviewDevelopmentPlanActivityVO);
			}
		}
		List<ReviewDevelopmentPlanActivity> updatedReviewDevelopmentPlanActivities = null;
		if (reviewDevelopmentPlanActivities != null) {
			// creating new list of entity objects to set it to review Header
			updatedReviewDevelopmentPlanActivities = new ArrayList<ReviewDevelopmentPlanActivity>();

			for (int i = 0; i < reviewDevelopmentPlanActivities.size(); i++) {
				ReviewDevelopmentPlanActivityVO updatedDevelopmentPlanActivity = reviewDevelopmentPlanActivities
						.get(i);
				if (updatedDevelopmentPlanActivity
						.getReviewDevelopmentPlanActivityId() == null) {
					ReviewDevelopmentPlanActivity newReviewDevelopmentPlanActivity = new ReviewDevelopmentPlanActivity();
					convertToDTO(newReviewDevelopmentPlanActivity,
							updatedDevelopmentPlanActivity, reviewFormInfoVO);
					newReviewDevelopmentPlanActivity.setReviewHeader(reviewHeader);
					newReviewDevelopmentPlanActivity.setCreatedDate(new Date());
					newReviewDevelopmentPlanActivity.setLastModifiedDate(new Date());
					if(reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.APPRAISE){
						
						newReviewDevelopmentPlanActivity.setOwner(ReviewFormRole.APPRAISE);
					}
					else{
						
						newReviewDevelopmentPlanActivity.setOwner(ReviewFormRole.APPRAISER);
					}
					newReviewDevelopmentPlanActivity.setCreatedBy(reviewFormInfoVO.getReviewFormRole());
					updatedReviewDevelopmentPlanActivities
							.add(newReviewDevelopmentPlanActivity);
				} else {
					
					for (ReviewDevelopmentPlanActivity existingDevelopmentPlanActivity : initialReviewDevelopmentPlanActivities) {
						if (updatedDevelopmentPlanActivity
								.getReviewDevelopmentPlanActivityId() == existingDevelopmentPlanActivity
								.getReviewDevelopmentPlanActivityId()) {
							convertToDTO(existingDevelopmentPlanActivity,
									updatedDevelopmentPlanActivity,
									reviewFormInfoVO);
							existingDevelopmentPlanActivity.setLastModifiedDate(new Date());
							updatedReviewDevelopmentPlanActivities
									.add(existingDevelopmentPlanActivity);
							break;
						}
					}
					
				}
			}
		}
		reviewHeader
				.setReviewDevelopmentPlanActivities(updatedReviewDevelopmentPlanActivities);
		reviewHeader.setLastModifiedDate(new Date());
		reviewHeader.setLastModifiedByEmployee(employeeService
				.getEmployeeById(SecurityDetailsAccessor.getEmpId()));
		reviewHeaderDAO.saveReviewHeader(reviewHeader);

		List<ReviewDevelopmentPlanActivityVO> updatedReviewDevlopmentPlanActivityVOs = new ArrayList<ReviewDevelopmentPlanActivityVO>();

		if (reviewHeader.getReviewDevelopmentPlanActivities() != null) {
			for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : reviewDevelopmentPlanDAO
					.getReviewDevelopmentPlanActivities(
							reviewFormInfoVO.getReviewHeaderId(),
							reviewFormInfoVO.getReviewFormRole())) {

				ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO = new ReviewDevelopmentPlanActivityVO();
				convertToVO(reviewDevelopmentPlanActivityVO,
						reviewDevelopmentPlanActivity);
				updatedReviewDevlopmentPlanActivityVOs
						.add(reviewDevelopmentPlanActivityVO);

			}
		}
		// @Swaroops
		List<ReviewActionLog> reviewActionLogs = reviewAuditService
				.getReviewActionLog(ReviewDevelopmentPlanActivityVO.class,
						initialReviewDevlopmentPlanActivityVOs,
						updatedReviewDevlopmentPlanActivityVOs,
						reviewFormInfoVO);
		reviewAuditService.saveReviewActionLog(reviewActionLogs);
		return updatedReviewDevlopmentPlanActivityVOs;
	}

	private void convertToDTO(
			ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity,
			ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO,
			ReviewFormInfoVO reviewFormInfoVO) {

		if(!reviewDevelopmentPlanActivity.isApproved()){
			
			if (reviewDevelopmentPlanActivityVO.getGoalComment()  == null
					|| reviewDevelopmentPlanActivityVO.getGoalComment().trim().equals("")) {
				reviewDevelopmentPlanActivity.setGoalComment(null);
			} else {
				if (reviewDevelopmentPlanActivity.getGoalComment() == null) {
					reviewDevelopmentPlanActivity.setGoalComment(new Comment());
				}
				reviewDevelopmentPlanActivity.getGoalComment().setCommentText(
						reviewDevelopmentPlanActivityVO.getGoalComment());
			}

			
			if (reviewDevelopmentPlanActivityVO.getMeasurementComment()  == null
					|| reviewDevelopmentPlanActivityVO.getMeasurementComment().trim().equals("")) {
				reviewDevelopmentPlanActivity.setMeasurementComment(null);
			} else {
				if (reviewDevelopmentPlanActivity.getMeasurementComment() == null) {
					reviewDevelopmentPlanActivity.setMeasurementComment(new Comment());
				}
				reviewDevelopmentPlanActivity.getMeasurementComment().setCommentText(
						reviewDevelopmentPlanActivityVO.getMeasurementComment());
			}
			if (reviewDevelopmentPlanActivityVO.getActionStepsComment()  == null
					|| reviewDevelopmentPlanActivityVO.getActionStepsComment().trim().equals("")) {
				reviewDevelopmentPlanActivity.setActionStepsComment(null);
			} else {
				if (reviewDevelopmentPlanActivity.getActionStepsComment() == null) {
					reviewDevelopmentPlanActivity.setActionStepsComment(new Comment());
				}
				reviewDevelopmentPlanActivity.getActionStepsComment().setCommentText(
						reviewDevelopmentPlanActivityVO.getActionStepsComment());
			}
			
			
	}

			if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE
					&& (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2 || reviewFormInfoVO
							.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED)) {

				
				if (reviewDevelopmentPlanActivityVO.getAppraiseComment()  == null
						|| reviewDevelopmentPlanActivityVO.getAppraiseComment().trim().equals("")) {
					reviewDevelopmentPlanActivity.setAppraiseComment(null);
				} else {
					if (reviewDevelopmentPlanActivity.getAppraiseComment() == null) {
						reviewDevelopmentPlanActivity.setAppraiseComment(new Comment());
					}
					reviewDevelopmentPlanActivity.getAppraiseComment().setCommentText(
							reviewDevelopmentPlanActivityVO.getAppraiseComment());
				}

			}
			else if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2) {
				
				
				if (reviewDevelopmentPlanActivityVO.getAppraiserComment()  == null
						|| reviewDevelopmentPlanActivityVO.getAppraiserComment().trim().equals("")) {
					reviewDevelopmentPlanActivity.setAppraiserComment(null);
				} else {
					if (reviewDevelopmentPlanActivity.getAppraiserComment() == null) {
						reviewDevelopmentPlanActivity.setAppraiserComment(new Comment());
					}
					reviewDevelopmentPlanActivity.getAppraiserComment().setCommentText(
							reviewDevelopmentPlanActivityVO.getAppraiserComment());
				}
			}
		

	}
	private void convertToVO(
			ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO,
			ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity) {

		reviewDevelopmentPlanActivityVO
				.setReviewDevelopmentPlanActivityId(reviewDevelopmentPlanActivity
						.getReviewDevelopmentPlanActivityId());

		if (reviewDevelopmentPlanActivity.getGoalComment() != null) {
			reviewDevelopmentPlanActivityVO
					.setGoalComment(reviewDevelopmentPlanActivity
							.getGoalComment().getCommentText());
		}
		if (reviewDevelopmentPlanActivity.getMeasurementComment() != null) {
			reviewDevelopmentPlanActivityVO
					.setMeasurementComment(reviewDevelopmentPlanActivity
							.getMeasurementComment().getCommentText());
		}
		if (reviewDevelopmentPlanActivity.getActionStepsComment() != null) {
			reviewDevelopmentPlanActivityVO
					.setActionStepsComment(reviewDevelopmentPlanActivity
							.getActionStepsComment().getCommentText());
		}
		if (reviewDevelopmentPlanActivity.getAppraiseComment() != null) {
			reviewDevelopmentPlanActivityVO
					.setAppraiseComment(reviewDevelopmentPlanActivity
							.getAppraiseComment().getCommentText());
		}
		if (reviewDevelopmentPlanActivity.getAppraiserComment() != null) {
			reviewDevelopmentPlanActivityVO
					.setAppraiserComment(reviewDevelopmentPlanActivity
							.getAppraiserComment().getCommentText());
		}
		reviewDevelopmentPlanActivityVO
				.setIsApproved(reviewDevelopmentPlanActivity.isApproved());

	}

	@Override
	public ReviewDevelopmentPlanActivity getReviewDevelopmentPlanActivity(
			int reviewDevelopmentPlanActivityId) {
		return reviewDevelopmentPlanDAO
				.getReviewDevelopmentPlanActivityById(reviewDevelopmentPlanActivityId);
	}

	@Override
	public boolean remove(int reviewDevelopmentPlanActivityId) {
		try {
			reviewDevelopmentPlanDAO.remove(reviewDevelopmentPlanActivityId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivityVOByHeaderId(
			int reviewHeaderId, ReviewFormRole owner,String requestType) {
		List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivityVOs = new ArrayList<ReviewDevelopmentPlanActivityVO>();
		List<ReviewDevelopmentPlanActivity> reviewDevelopmentPlanActivities = reviewDevelopmentPlanDAO
				.getReviewDevelopmentPlanActivities(reviewHeaderId, owner);
		for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : reviewDevelopmentPlanActivities) {
			ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO = new ReviewDevelopmentPlanActivityVO();
			if (reviewDevelopmentPlanActivity.getGoalComment() != null) {
				reviewDevelopmentPlanActivityVO
						.setGoalComment(reviewDevelopmentPlanActivity
								.getGoalComment().getCommentText());
			}
			if (reviewDevelopmentPlanActivity.getMeasurementComment() != null) {
				reviewDevelopmentPlanActivityVO
						.setMeasurementComment(reviewDevelopmentPlanActivity
								.getMeasurementComment().getCommentText());
			}
			if (reviewDevelopmentPlanActivity.getActionStepsComment() != null) {
				reviewDevelopmentPlanActivityVO
						.setActionStepsComment(reviewDevelopmentPlanActivity
								.getActionStepsComment().getCommentText());
			}
			//Added this to facilitate download option for super user a full appraisal PDF
			if (requestType.equals("SYS_GENERATED") == false) {

				if (reviewDevelopmentPlanActivity.getAppraiseComment() != null) {
					reviewDevelopmentPlanActivityVO
							.setAppraiseComment(reviewDevelopmentPlanActivity
									.getAppraiseComment().getCommentText());
				}

				if (reviewDevelopmentPlanActivity.getAppraiserComment() != null) {
					reviewDevelopmentPlanActivityVO
							.setAppraiserComment(reviewDevelopmentPlanActivity
									.getAppraiserComment().getCommentText());
				}
			}
			reviewDevelopmentPlanActivityVOs
					.add(reviewDevelopmentPlanActivityVO);
		}
		return reviewDevelopmentPlanActivityVOs;
	}

	@Override
	public int getNoOfDevPlans(int reviewHeaderId, ReviewFormRole owner) {
		return reviewDevelopmentPlanDAO.getNoOfDevPlans(reviewHeaderId, owner);
	}

	@Override
	public int getNoOfUnApprovedDevPlans(int reviewHeaderId, ReviewFormRole owner) {
		return reviewDevelopmentPlanDAO
				.getNoOfUnApprovedDevPlans(reviewHeaderId, owner);
	}

	@Override
	public List<ReviewDevelopmentPlanActivityVO> getReviewDevelopmentPlanActivities(
			int reviewHeaderId, ReviewFormRole owner) {

		List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities = new AutoPopulatingList<ReviewDevelopmentPlanActivityVO>(
				ReviewDevelopmentPlanActivityVO.class);		
			for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity: reviewDevelopmentPlanDAO
					.getReviewDevelopmentPlanActivities(reviewHeaderId, owner)) {
				ReviewDevelopmentPlanActivityVO reviewDevelopmentPlanActivityVO = new ReviewDevelopmentPlanActivityVO();
				convertToVO(reviewDevelopmentPlanActivityVO,
						reviewDevelopmentPlanActivity);
				reviewDevelopmentPlanActivities
						.add(reviewDevelopmentPlanActivityVO);
			}
		
		return reviewDevelopmentPlanActivities;
	}

	@Override
	public void copyReviewDevelopmentPlanActivities(int reviewHeaderId,
			ReviewFormRole owner, String actionType) {
		reviewDevelopmentPlanDAO.copyReviewDevelopmentPlanActivities(
				reviewHeaderId, owner, actionType);
	}

}
