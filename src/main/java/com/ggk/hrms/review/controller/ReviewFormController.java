package com.ggk.hrms.review.controller;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.beans.domain.ReviewObjectiveProject;
import com.ggk.hrms.review.beans.domain.ReviewStatus;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.service.ReviewDevelopmentPlanService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.ReviewObjectiveService;
import com.ggk.hrms.review.service.ReviewSummaryService;
import com.ggk.hrms.review.service.SharedAppraiserService;
import com.ggk.hrms.review.task.AppraisalFormPDFTask;
import com.ggk.hrms.review.task.AppraisalOperationNotificationTask;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.ReviewFormInfoUtil;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller("reviewFormController")
@RequestMapping("/appraisal")
@RolesAllowed(value = { "ROLE_USER", "ROLE_MANAGER", "ROLE_SUPERUSER" })
public class ReviewFormController {

	Logger log = Logger.getLogger(ReviewFormController.class);

	@Resource
	private ReviewHeaderService reviewHeaderService;

	@Resource
	private ReviewFormService reviewFormService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private MailingService mailingService;

	@Resource
	private ReviewAuditService reviewAuditService;

	@Resource
	private ReviewObjectiveService reviewObjectiveService;

	@Resource
	private ReviewDevelopmentPlanService reviewDevelopmentPlanService;

	@Resource
	private ReviewCompetencyService reviewCompetencyService;

	@Resource
	private ReviewSummaryService reviewSummaryService;

	@Resource
	private String reviewAlias;

	@Resource
	private String pdfLocation;

	@Resource
	private String jrxmlLocation;

	@Resource
	private SharedAppraiserService sharedAppraiserService;
	
	@Resource
	private String superUserPdfLocation;
	
	@Resource
	private String superUserJrxmlLocation;

	@RequestMapping(value = "/updateReviewStatus.html", method = RequestMethod.POST)
	public @ResponseBody
	boolean updateReviewStatus(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "opsType") String opsType,
			@RequestParam(value = "checkinComments") String checkinComments,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		ReviewStatus updatedReviewStatus = null;
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		String currentReviewStatusCode = reviewHeader.getReviewStatus()
				.getReviewStatusCode();
		String reviewFormRole = reviewFormInfoVO.getReviewFormRole()
				.getDescription();

		if (reviewFormRole.equals("APPRAISE")) {
			if (opsType.equals("submit_to_appraiser")) {

				if (currentReviewStatusCode.equals("GOALS_SETTING_IN_PROGRESS")
						|| currentReviewStatusCode.equals("NEED_TO_EDIT_GOALS")
						|| currentReviewStatusCode.equals("NOT_STARTED")
						|| currentReviewStatusCode.equals("GOALS_FINALIZED")) {
					updatedReviewStatus = ReviewStatus.GOALS_SUBMITTED;
					for (ReviewObjective reviewObjective : reviewHeader
							.getReviewObjectives()) {
						reviewObjective.setApproved(false);
						for (ReviewObjectiveProject reviewObjectiveProject : reviewObjective
								.getProjects()) {
							reviewObjectiveProject.setApproved(false);
						}
					}
					for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : reviewHeader
							.getReviewDevelopmentPlanActivities()) {
						reviewDevelopmentPlanActivity.setApproved(false);
					}
				}
				if (currentReviewStatusCode.equals("APPRAISAL_IN_PROGRESS")
						|| currentReviewStatusCode.equals("NEED_TO_EDIT_APPRAISAL")
						|| currentReviewStatusCode.equals("GOALS_ACCEPTED")
						|| currentReviewStatusCode.equals("READY_FOR_MEETING")) {
					for (ReviewObjective reviewObjective : reviewHeader
							.getReviewObjectives()) {
						for (ReviewObjectiveProject reviewObjectiveProject : reviewObjective
								.getProjects()) {
							if (reviewObjectiveProject.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2) {
								reviewObjectiveProject.setApproved(false);
							}
						}
					}
					updatedReviewStatus = ReviewStatus.APPRAISAL_SUBMITTED;
				}
			} else if (opsType.equals("accept_goals")) {
				if (currentReviewStatusCode.equals("GOALS_FINALIZED")) {
					updatedReviewStatus = ReviewStatus.GOALS_ACCEPTED;
				}
			} else if (opsType.equals("accept_appraisal")) {
				if (currentReviewStatusCode.equals("READY_FOR_MEETING")) {
					updatedReviewStatus = ReviewStatus.COMPLETED;
				}
			}
		} else if (reviewFormRole.equals("APPRAISER")
				|| reviewFormRole.equals("SUPERUSER")) {

			if (opsType.equals("approve_goals")) {
				if (currentReviewStatusCode.equals("GOALS_SUBMITTED")) {
					updatedReviewStatus = ReviewStatus.GOALS_FINALIZED;
					for (ReviewObjective reviewObjective : reviewHeader
							.getReviewObjectives()) {

						//reviewObjective.setNotApplicable(false);
						reviewObjective.setApproved(true);
						if (reviewObjective.getTargetCompletionDate() == null) {
							reviewObjective
									.setTargetCompletionDate(reviewFormInfoVO
											.getReviewCycleEndDate());
						}
						for (ReviewObjectiveProject reviewObjectiveProject : reviewObjective
								.getProjects()) {
							reviewObjectiveProject.setApproved(true);
						}
					}
					for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : reviewHeader
							.getReviewDevelopmentPlanActivities()) {
						reviewDevelopmentPlanActivity.setApproved(true);
					}
				}
			} else  if (opsType.equals("approve_appraisal")) {
				if (currentReviewStatusCode.equals("APPRAISAL_SUBMITTED")
						|| currentReviewStatusCode
								.equals("ASSESSMENT_IN_PROGRESS")) {
					updatedReviewStatus = ReviewStatus.READY_FOR_MEETING;
					for (ReviewObjective reviewObjective : reviewHeader
							.getReviewObjectives()) {
						reviewObjective.setApproved(true);
						for (ReviewObjectiveProject reviewObjectiveProject : reviewObjective
								.getProjects()) {
							reviewObjectiveProject.setApproved(true);
							// need to implement approve projects in phase 2 logic
						}
					}
					for (ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity : reviewHeader
							.getReviewDevelopmentPlanActivities()) {
						reviewDevelopmentPlanActivity.setApproved(true);
					}
				}
			} else if (opsType.equals("resubmit_to_appraise")) {

				ReviewPhase reviewPhase = reviewFormService
						.getReviewPhase(reviewHeader.getReviewStatus());
				if (reviewPhase.getDescription().equals("APPRAISER_IN_PHASE2")
						&& (currentReviewStatusCode
								.equals("ASSESSMENT_IN_PROGRESS") || currentReviewStatusCode
								.equals("APPRAISAL_SUBMITTED"))) {
					updatedReviewStatus = ReviewStatus.NEED_TO_EDIT_APPRAISAL;
					// need to implement the logic for approve projects to set false which are approved in phase 2
				}
				if (reviewPhase.getDescription().equals("APPRAISER_IN_PHASE1")
						&& currentReviewStatusCode.equals("GOALS_SUBMITTED")) {
					updatedReviewStatus = ReviewStatus.NEED_TO_EDIT_GOALS;
				}
			}
		}
		if (updatedReviewStatus != null) {

			if(reviewFormRole.equals("SUPERUSER")) {
				/*if (reviewHeader.getSuperUserInternalComment() != null) {
					reviewHeader.getSuperUserInternalComment().setCommentText(checkinComments);
				} else {
					Comment comment = new Comment();
					comment.setCommentText(checkinComments);
					reviewHeader.setSuperUserInternalComment(comment);
				}*/
				if (reviewHeader.getSuperUserInternalComment() != null) {
					reviewHeader.getSuperUserInternalComment().setCommentText("");
				}
			} else if(reviewFormRole.equals("APPRAISER")) {
				/*if (reviewHeader.getAppraiserInternalComment() != null) {
					reviewHeader.getAppraiserInternalComment().setCommentText(checkinComments);
				} else {
					Comment comment = new Comment();
					comment.setCommentText(checkinComments);
					reviewHeader.setAppraiserInternalComment(comment);
				}*/
				if (reviewHeader.getAppraiserInternalComment() != null) {
					reviewHeader.getAppraiserInternalComment().setCommentText("");
				}
			} else {
				/*if (reviewHeader.getAppraiseInternalComment() != null) {
					reviewHeader.getAppraiseInternalComment().setCommentText(checkinComments);
				} else {
					Comment comment = new Comment();
					comment.setCommentText(checkinComments);
					reviewHeader.setAppraiseInternalComment(comment);
				}*/
				if (reviewHeader.getAppraiseInternalComment() != null) {
					reviewHeader.getAppraiseInternalComment().setCommentText("");
				}
			}
			// Below is to send the appraisal form pdf to appraise or review Alias

			if (opsType.equals("submit_to_appraiser")
					&& (reviewHeader.getEmployee().getEmpId() == SecurityDetailsAccessor.getEmpId())
					&& (currentReviewStatusCode
							.equals("GOALS_SETTING_IN_PROGRESS")
							|| currentReviewStatusCode
									.equals("NEED_TO_EDIT_GOALS")
							|| currentReviewStatusCode.equals("NOT_STARTED") || currentReviewStatusCode
								.equals("GOALS_FINALIZED"))) {

				String subject = "Appraisal Form - "
						+ reviewHeader.getReviewCycle().getReviewCycleName()
						+ " (Draft).";
				String text = "Hi "
						+ reviewHeader.getEmployee().getDisplayName()
						+ ", \n\nYou have submitted your goals to your manager. Please find the attachment for appraisal form.";

				new Thread(new AppraisalFormPDFTask(reviewHeaderService,
						reviewObjectiveService, reviewCompetencyService,
						reviewDevelopmentPlanService,reviewSummaryService,
						reviewFormInfoVO.getReviewFormRole(), reviewHeaderId,
						jrxmlLocation, pdfLocation, mailingService, subject,
						text, reviewHeader.getEmployee().getEmail(), null,
						reviewHeader.getReviewCycle().getReviewCycleName(),"SYS_GENERATED",superUserPdfLocation,superUserJrxmlLocation))
						.start();

			} else if (opsType.equals("accept_goals")
					&& (reviewHeader.getEmployee().getEmpId() == SecurityDetailsAccessor
							.getEmpId())
					&& currentReviewStatusCode.equals("GOALS_FINALIZED")) {

				String subject = reviewHeader.getEmployee().getDisplayName()
						+ " appraisal - "
						+ reviewHeader.getReviewCycle().getReviewCycleName();
				String text = "Please find the attachement for "
						+ reviewHeader.getEmployee().getDisplayName()
						+ " appraisal form.";
				String sendCC[] = { reviewHeader.getEmployee().getEmail(),
						reviewHeader.getMainAppraiserEmployee().getEmail() };

				new Thread(new AppraisalFormPDFTask(reviewHeaderService,
						reviewObjectiveService, reviewCompetencyService,
						reviewDevelopmentPlanService,reviewSummaryService,
						reviewFormInfoVO.getReviewFormRole(), reviewHeaderId,
						jrxmlLocation, pdfLocation, mailingService, subject,
						text, reviewAlias, sendCC, reviewHeader
								.getReviewCycle().getReviewCycleName(),"SYS_GENERATED",superUserPdfLocation,superUserJrxmlLocation))
						.start();

			} // else if(opsType.equals("accept_appraisal") &&
				// (reviewHeader.getEmployee().getEmpId() ==
				// SecurityDetailsAccessor.getEmpId()) &&
				// currentReviewStatusCode.equals("READY_FOR_MEETING"))
				// need to implement pdf attachment for accept_appraisal when
				// current status is in READY_FOR_MEETING
			reviewHeader.setReviewStatus(updatedReviewStatus);
			reviewHeaderService.merge(reviewHeader);

			// added code to copy objectives, Competencies , Development plan and summary

			reviewObjectiveService.copyReviewObjectives(reviewHeaderId,
					reviewFormInfoVO.getReviewFormRole(), opsType);
			reviewCompetencyService.copyReviewCompetencies(reviewHeaderId,
					reviewFormInfoVO.getReviewFormRole(), opsType);
			reviewDevelopmentPlanService.copyReviewDevelopmentPlanActivities(
					reviewHeaderId, reviewFormInfoVO.getReviewFormRole(),
					opsType);
			reviewSummaryService.copyReviewSummary(reviewHeaderId,
					reviewFormInfoVO.getReviewFormRole(), opsType);

			// Sending Mail when the appraisal is submitted or reassigned or
			// accepted approved by manager/super user/ (shared manager).
			// READY_FOR_MEETING
			if (opsType.equals("approve_goals")
					|| opsType.equals("approve_appraisal")
					|| opsType.equals("resubmit_to_appraise")) {

				Employee loggedInUser = employeeService
						.getEmployeeById(SecurityDetailsAccessor.getEmpId());
				new Thread(new AppraisalOperationNotificationTask(
						mailingService, opsType, reviewHeader.getEmployee(),
						loggedInUser, reviewHeader.getReviewCycle()
								.getReviewCycleName(), currentReviewStatusCode))
						.start();
			} else if (opsType.equals("submit_to_appraiser")
					|| opsType.equals("accept_appraisal")) {
				new Thread(new AppraisalOperationNotificationTask(
						mailingService, opsType, reviewHeader.getEmployee(),
						reviewHeader.getMainAppraiserEmployee(), reviewHeader
								.getReviewCycle().getReviewCycleName(),
						currentReviewStatusCode)).start();
			}
			ReviewActionLog reviewActionLog = null;
			if (opsType.equals("submit_to_appraiser")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "SUBMIT",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			} else if (opsType.equals("resubmit_to_appraise")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "RE_SUBMIT_TO_APPRAISE",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			} else if (opsType.equals("approve_appraisal")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "APPROVE_APPRAISAL",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			} else if (opsType.equals("approve_goals")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "APPROVE_GOALS",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			} else if (opsType.equals("accept_goals")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "ACCEPT_GOALS",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			}else if (opsType.equals("accept_appraisal")) {
				reviewActionLog = reviewAuditService.createReviewActionLog(
						reviewFormInfoVO, "ACCEPT_APPRAISAL",
						ReviewFeildGroupType.APPRAISAL, null, null, null, null,
						checkinComments);
			}
			if (reviewActionLog != null) {
				reviewAuditService.saveReviewActionLog(reviewActionLog);
			}
			return true;
		} else {
			return false;
		}

	}

	@ModelAttribute("reviewFormInfoVO")
	public ReviewFormInfoVO createReviewFormInfoVO(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId) {
		 return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(),null);
	}

	@RequestMapping(value = "/showHelperNotes.html", method = RequestMethod.POST)
	public String showHelperNotes(
			ModelMap model,
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "roleDescription") String roleDescription,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		Comment comment = null;
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
			comment = reviewHeader.getAppraiseInternalComment();
			if (comment == null) {
				comment = new Comment();
				comment.setCommentText("");
				reviewHeader.setAppraiseInternalComment(comment);
			}
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER) {
			comment = reviewHeader.getAppraiserInternalComment();
			if (comment == null) {
				comment = new Comment();
				comment.setCommentText("");
				reviewHeader.setAppraiserInternalComment(comment);
			}
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER) {
			comment = reviewHeader.getSharedAppraiserInternalComment();
			if (comment == null) {
				comment = new Comment();
				comment.setCommentText("");
				reviewHeader.setSharedAppraiserInternalComment(comment);
			}
		} else {
			comment = reviewHeader.getSuperUserInternalComment();
			if (comment == null) {
				comment = new Comment();
				comment.setCommentText("");
				reviewHeader.setSuperUserInternalComment(comment);
			}
		}
		reviewHeaderService.merge(reviewHeader);
		model.addAttribute("comment", comment);
		return "appraisalForm/reviewFormInfo/showHelperNotes";
	}

	// @ResponseBody boolean

	@RequestMapping(value = "/saveHelperNotes.html", method = RequestMethod.POST)
	public void saveHelperNotes(
			ModelMap model,
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "roleDescription") String roleDescription,
			@RequestParam(value = "notesCommentText") String notesCommentText,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		Comment comment = null;
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
			if (reviewHeader.getAppraiseInternalComment() != null) {
				comment = reviewHeader.getAppraiseInternalComment();
			} else {
				comment = new Comment();
			}
			comment.setCommentText(notesCommentText);
			reviewHeader.setAppraiseInternalComment(comment);
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER) {
			if (reviewHeader.getAppraiserInternalComment() != null) {
				comment = reviewHeader.getAppraiserInternalComment();
			} else {
				comment = new Comment();
			}
			comment.setCommentText(notesCommentText);
			reviewHeader.setAppraiserInternalComment(comment);
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER) {
			if (reviewHeader.getSharedAppraiserInternalComment() != null) {
				comment = reviewHeader.getSharedAppraiserInternalComment();
			} else {
				comment = new Comment();
			}
			comment.setCommentText(notesCommentText);
			reviewHeader.setSharedAppraiserInternalComment(comment);
		} else {
			if (reviewHeader.getSuperUserInternalComment() != null) {
				comment = reviewHeader.getSuperUserInternalComment();
			} else {
				comment = new Comment();
			}
			comment.setCommentText(notesCommentText);
			reviewHeader.setSuperUserInternalComment(comment);
		}
		reviewHeaderService.merge(reviewHeader);
		// return true;
	}

	@RequestMapping(value = "/getInternalComments.html", method = RequestMethod.POST)
	public @ResponseBody
	String getInternalComments(
			ModelMap model,
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "roleDescription") String roleDescription,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		String commentText = "";

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
			if (reviewHeader.getAppraiseInternalComment() != null) {
				commentText = reviewHeader.getAppraiseInternalComment()
						.getCommentText();
			}
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER) {
			if (reviewHeader.getAppraiserInternalComment() != null) {
				commentText = reviewHeader.getAppraiserInternalComment()
						.getCommentText();
			}
		} else if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER) {
			if (reviewHeader.getSharedAppraiserInternalComment() != null) {
				commentText = reviewHeader.getSharedAppraiserInternalComment()
						.getCommentText();
			}
		} else {
			if (reviewHeader.getSuperUserInternalComment() != null) {
				commentText = reviewHeader.getSuperUserInternalComment()
						.getCommentText();
			}
		}
		return commentText;
	}

	@RequestMapping(value = "/checkForActiveSharedAppraiser.html", method = RequestMethod.POST)
	public @ResponseBody
	String getActiveSharedAppraiser(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {
		SharedAppraiser activeSharedAppraiser = sharedAppraiserService
				.getActiveSharedAppraiser(reviewHeaderId);
		if (activeSharedAppraiser != null && reviewFormService.
				isGivenEmpIdHasAccesstoIt(activeSharedAppraiser.getAssignedToEmployee().getEmpId(),
				reviewHeaderId)== false) {
			return activeSharedAppraiser.getAssignedToEmployee()
					.getDisplayName();
		} else {
			return "noActiveSharedAppraiser";
		}
	}

	@RequestMapping(value = "/validateAppraisal.html", method = RequestMethod.POST)
	public @ResponseBody
	String validateAppraisal(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {
		if (reviewFormService.validateAppraisal(reviewHeaderId,
				reviewFormInfoVO) == null) {
			return " ";
		} else {
			return reviewFormService.validateAppraisal(reviewHeaderId,
					reviewFormInfoVO);
		}
	}

	@RequestMapping(value = "/isSubmittable.html", method = RequestMethod.POST)
	public @ResponseBody
	boolean isSubmittable(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		if (reviewObjectiveService.getNoOfObjectives(reviewHeaderId, reviewFormInfoVO.getReviewFormRole()) > 0
				&& reviewDevelopmentPlanService.getNoOfDevPlans(reviewHeaderId, reviewFormInfoVO.getReviewFormRole()) > 0) {
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/areGoalsAcceptable.html", method = RequestMethod.POST)
	public @ResponseBody
	boolean areGoalsAcceptable(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		if (reviewObjectiveService.getNoOfUnApprovedObjectives(reviewHeaderId, reviewFormInfoVO.getReviewFormRole()) > 0
				|| reviewDevelopmentPlanService.getNoOfUnApprovedDevPlans(reviewHeaderId, reviewFormInfoVO.getReviewFormRole()) > 0) {

			return true;
		}
		return false;
	}
}
