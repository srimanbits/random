package com.ggk.hrms.review.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewDevelopmentPlanService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.form.ReviewDevelopmentPlanForm;
import com.ggk.hrms.review.ui.vo.ReviewDevelopmentPlanActivityVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/appraisal")
public class ReviewDevelopmentPlanController {

	@Autowired
	ReviewDevelopmentPlanService reviewDevelopmentPlanService;

	@Autowired
	ReviewHeaderService reviewHeaderService;

	@Autowired
	ReviewFormService reviewFormService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	private ReviewAuditService reviewAuditService;

	@RequestMapping(method = RequestMethod.POST, value = "/appendDevelopmentPlanActivity.html")
	protected String appendDevelopmentPlanActivity(
			@RequestParam Integer index,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			ModelMap model) {
		if ((reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE && (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1||reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED||reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2))
				||((reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER || reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER || reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER)
						&& reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1)) {
			model.addAttribute("index", index);
			return "appraisalForm/developmentPlan/insertNewDevelopmentPlanActivity";
		} else {
			throw new RuntimeException("Invalid Operation");
		}
	}

	@RequestMapping(value = "/saveReviewDevelopmentPlan.html", method = RequestMethod.POST)
	public String saveReviewDevelopmentPlan(
			@ModelAttribute("reviewDevelopmentPlanForm") ReviewDevelopmentPlanForm reviewDevelopmentPlanForm,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			ModelMap model) {
		if (isSavable(reviewFormInfoVO)) {
			Iterator<ReviewDevelopmentPlanActivityVO> reviewDevPlanIterator=reviewDevelopmentPlanForm.getReviewDevelopmentPlanActivities().iterator();
			while(reviewDevPlanIterator.hasNext()){
				ReviewDevelopmentPlanActivityVO reviewActivity=reviewDevPlanIterator.next();
				
				if (reviewActivity.getGoalComment() == null
						&& reviewActivity.getMeasurementComment() == null
						&& reviewActivity.getActionStepsComment() == null
						&& reviewActivity.getAppraiseComment() == null
						&& reviewActivity.getAppraiserComment() == null) {
					reviewDevPlanIterator.remove();
				}
				
			}
			List<ReviewDevelopmentPlanActivityVO> updatedReviewDevlopmentPlanActivityVOs = reviewDevelopmentPlanService
					.saveReviewDevelopmentPlanActivities(
							reviewDevelopmentPlanForm.getReviewDevelopmentPlanActivities(), reviewFormInfoVO);
			reviewDevelopmentPlanForm
					.setReviewDevelopmentPlanActivities(updatedReviewDevlopmentPlanActivityVOs);
			model.addAttribute("reviewDevelopmentPlanForm",
					reviewDevelopmentPlanForm);
			model.addAttribute("noOfElements",
					reviewDevelopmentPlanForm.getReviewDevelopmentPlanActivities().size());
			return "appraisalForm/developmentPlan/reviewDevelopmentPlan";
		} else {
			throw new RuntimeException("Invalid Operation");
		}
	}

	@RequestMapping(value = "/removeDevPlanActivity.html", method = RequestMethod.POST)
	public @ResponseBody
	boolean removeDevPlanActivity(
			int reviewDevelopmentPlanActivityId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {
		ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity = reviewDevelopmentPlanService
				.getReviewDevelopmentPlanActivity(reviewDevelopmentPlanActivityId);
		boolean valuetoReturn = false;
		if (reviewDevelopmentPlanActivity == null) {

		} else if (!reviewDevelopmentPlanActivity.isApproved()&& reviewDevelopmentPlanActivity.getReviewHeader().getReviewHeaderId()==reviewFormInfoVO.getReviewHeaderId() &&((reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.APPRAISE && (reviewFormInfoVO.getReviewPhase()==ReviewPhase.APPRAISE_IN_PHASE1 ||reviewFormInfoVO.getReviewPhase()==ReviewPhase.APPRAISE_IN_PHASE2))
				||((reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.APPRAISER ||reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.SUPERUSER||reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.SHARED_APPRAISER ) && (reviewFormInfoVO.getReviewPhase()==ReviewPhase.APPRAISER_IN_PHASE1)))) {
			 {
				if (reviewDevelopmentPlanService
						.remove(reviewDevelopmentPlanActivityId)) {
					valuetoReturn = true;
					ReviewActionLog reviewActionLog = reviewAuditService
							.createReviewActionLog(reviewFormInfoVO, "DELETE",
									ReviewFeildGroupType.DEVELOPMENT_PLAN,
									reviewDevelopmentPlanActivity.getGoalComment()
											.getCommentText(), null, null, null,
									null);
					reviewAuditService.saveReviewActionLog(reviewActionLog);
				}
				
			}
		}
		return valuetoReturn;
	}

	@ModelAttribute("reviewFormInfoVO")
	ReviewFormInfoVO createReviewFormInfoVO(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "opsType", required = false) String opsType) {

		return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(),opsType);

	}

	@RequestMapping(value = "/getDevelopmentPlans.html", method = RequestMethod.POST)
	public String showReviewDevelopmentPlanForm(
			ModelMap model,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			@RequestParam("reviewHeaderId") Integer reviewHeaderId) {
		
		if(showOrHide(reviewFormInfoVO)){
		ReviewDevelopmentPlanForm reviewDevelopmentPlanForm = new ReviewDevelopmentPlanForm();
		List<ReviewDevelopmentPlanActivityVO> reviewDevelopmentPlanActivities = reviewDevelopmentPlanService
				.getReviewDevelopmentPlanActivities(reviewFormInfoVO.getReviewHeaderId(),
						reviewFormInfoVO.getReviewFormRole());
		reviewDevelopmentPlanForm
				.setReviewDevelopmentPlanActivities(reviewDevelopmentPlanActivities);
		model.addAttribute("reviewDevelopmentPlanForm",
				reviewDevelopmentPlanForm);
		model.addAttribute("noOfElements",
				reviewDevelopmentPlanActivities.size());
		return "appraisalForm/developmentPlan/reviewDevelopmentPlan";
		}else{
			throw new RuntimeException("Invalid Operation");
		}
	}

	private boolean isSavable(ReviewFormInfoVO reviewFormInfoVO) {

		boolean valueToReturn = false;
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE
				&& (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED
						|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2 || reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1)) {

			valueToReturn = true;

		}

		else if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2 || reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1) {

			valueToReturn = true;

		}

		return valueToReturn;
	}
	
	public boolean showOrHide(ReviewFormInfoVO reviewFormInfoVO) {

		boolean value = false;

		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {

			value = true;

		}

		else {
			if (reviewFormInfoVO.getActualReviewStatus().equals(
					"GOALS_SUBMITTED")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"GOALS_ACCEPTED")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"NEED_TO_EDIT_GOALS")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"GOALS_FINALIZED")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"APPRAISAL_IN_PROGRESS")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"NEED_TO_EDIT_APPRAISAL")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"APPRAISAL_SUBMITTED")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"ASSESSMENT_IN_PROGRESS")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"COMPLETED")
					|| reviewFormInfoVO.getActualReviewStatus().equals(
							"READY_FOR_MEETING")) {
				value = true;

			}

		}

		return value;
	}
}
