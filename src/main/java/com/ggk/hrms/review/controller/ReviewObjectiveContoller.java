package com.ggk.hrms.review.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.service.DefaultObjectiveGradeService;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ProjectService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.ReviewObjectiveService;
import com.ggk.hrms.review.ui.form.ObjectiveForm;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;
import com.ggk.hrms.review.utils.ReviewFormInfoUtil;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;
import com.ggk.hrms.review.utils.UtilDateEditor;

@Controller("reviewObjectiveController")
@RequestMapping("/appraisal")
@RolesAllowed(value = { "ROLE_USER", "ROLE_MANAGER", "ROLE_SUPERUSER" })
public class ReviewObjectiveContoller {

	@Resource
	private ReviewObjectiveService reviewObjectiveService;

	@Resource
	private ReviewHeaderService reviewHeaderService;

	/*
	 * @Resource private DefaultObjectiveService defaultObjectiveService;
	 */

	@Resource
	private RatingService ratingService;

	@Resource
	private ProjectService projectService;

	@Resource
	private ReviewFormService reviewFormService;

	@Resource
	DefaultObjectiveGradeService defaultObjectiveGradeService;

	@Resource
	private ReviewAuditService reviewAuditService;

	@Resource
	private EmployeeService employeeService;
	
	private Logger logger = Logger.getLogger(ReviewCompetencyFormController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new UtilDateEditor(false));
	}

	@RequestMapping(value = "/getReviewObjectives.html", method = RequestMethod.POST)
	public String getObjectives(
			ModelMap model,
			@ModelAttribute("ratingDropDown") Map<Integer, String> ratingDropDown,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,HttpServletRequest request) {
		logger.debug("\n\n\n######################### getObjectives () Start of flow "+new Date()+"##########################################\n\n\n");
		if (showOrHide(reviewFormInfoVO)) {
			ObjectiveForm objectiveForm = new ObjectiveForm();
			objectiveForm
					.setObjectives(new AutoPopulatingList<ReviewObjectiveVO>(
							ReviewObjectiveVO.class));
			List<ReviewObjective> domainReviewObjs = reviewObjectiveService.getReviewObjectives(reviewFormInfoVO.getReviewHeaderId(), reviewFormInfoVO.getReviewFormRole());
			for (ReviewObjective domainReviewObj : domainReviewObjs) {
				ReviewObjectiveVO voReviewObj = new ReviewObjectiveVO();
				reviewObjectiveService.copyToVOBean(voReviewObj,
						domainReviewObj, reviewFormInfoVO);
				objectiveForm.getObjectives().add(voReviewObj);
			}
			objectiveForm.setProjectDropDown(getProjectsDropDown(request,reviewFormInfoVO));
			// @Swaroops
			// List<DropDownVO> defaultObjectiveDropDown =
			// defaultObjectiveGradeService
			// .getDefaultObjDropDown(reviewHeader.getMainAppraiserEmployee()
			// .getEmpId(), reviewHeader.getReviewCycle()
			// .getReviewCycleId(), 2); // Grade hard coded here...
			// objectiveForm.setDefaultObjDropDown(defaultObjectiveDropDown); //
			// default objectives will be handled in second phase....
			model.put("objectiveForm", objectiveForm);
			model.put("reviewFormInfoVO", reviewFormInfoVO);
		logger.debug("\n\n\n######################### getObjectives () End of flow "+new Date()+"##########################################\n\n\n");
			return "appraisalForm/objectives/objectiveForm";
		} else {

			throw new RuntimeException("Invalid Operation");
		}
	}

	@RequestMapping(value = "/addNewReviewObjective.html", method = RequestMethod.POST)
	public String addNewObjective(
			@RequestParam int defaultObjId,
			@RequestParam int objectiveNumber,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			ModelMap model) {
		if ((reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE && (reviewFormInfoVO
				.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1
				|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2 || reviewFormInfoVO
				.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED))
				|| ((reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER||reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER||reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER) && (reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1 || reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2))) {
			ReviewObjectiveVO voReviewObj = new ReviewObjectiveVO();
			if (defaultObjId != -1) {
				/*
				 * DefaultObjective defaultObj = new DefaultObjective();
				 * defaultObj = defaultObjectiveService
				 * .getDefaultObjective(defaultObjId);
				 * voReviewObj.setObjectiveName(defaultObj.getObjectiveName());
				 * if (defaultObj.getDetailsComment() != null) {
				 * voReviewObj.setDetailsComment(defaultObj.getDetailsComment()
				 * .getCommentText()); } if (defaultObj.getSuccessCriteria() !=
				 * null) { voReviewObj.setSuccessCriteriaComment(defaultObj
				 * .getSuccessCriteria().getCommentText()); }
				 */
				// @Swaroops
				DefaultObjectiveGrade defaultObjectiveGrade = defaultObjectiveGradeService
						.getDefaultObjectiveGradeById(defaultObjId);

				voReviewObj.setObjectiveName(defaultObjectiveGrade
						.getDefaultObjective().getDefaultObjectiveName());

				if (defaultObjectiveGrade.getDetailsComment() != null) {
					voReviewObj.setDetailsComment(defaultObjectiveGrade
							.getDetailsComment().getCommentText());
				}
				if (defaultObjectiveGrade.getSuccessCriteria() != null) {
					voReviewObj.setSuccessCriteriaComment(defaultObjectiveGrade
							.getSuccessCriteria().getCommentText());
				}

			}
			Map<Integer, String> projectDropDown = projectService
					.getProjectsDropDown(
							reviewFormInfoVO.getReviewCycleStartDate(),
							reviewFormInfoVO.getReviewCycleEndDate());
			model.put("objectiveNumber", objectiveNumber);
			model.put("reveiwObj", voReviewObj);
			model.put("projectDropDown", projectDropDown);
			return "appraisalForm/objectives/insertNewObjective";
		} else {
			throw new RuntimeException("Invalid Operation");

		}
	}

	//Swaroops... Putting project drop down in session... Instead of getting from DB every time.
     private Map<Integer, String> getProjectsDropDown(HttpServletRequest request,ReviewFormInfoVO reviewFormInfoVO){
    	 Map<Integer, String> projectDropDown = (Map<Integer, String>)request.getSession().getAttribute("projectDropDown");
			if (projectDropDown== null || projectDropDown.size()==0){
			projectDropDown = projectService
					.getProjectsDropDown(
							reviewFormInfoVO.getReviewCycleStartDate(),
							reviewFormInfoVO.getReviewCycleEndDate());
			request.getSession().setAttribute("projectDropDown",projectDropDown);
			}
    	 return projectDropDown;
     }

	@ModelAttribute("ratingDropDown")
	public Map<Integer, String> getRatingDropDown(HttpServletRequest request) {
		
		Map<Integer, String> ratingDropDown = (Map<Integer, String>)request.getSession().getAttribute("ratingDropDown");
         if (ratingDropDown == null || ratingDropDown.size() == 0){
        	 
        	 ratingDropDown = ratingService.getRatingsDropDown();
        	request.getSession().setAttribute("ratingDropDown", ratingDropDown);        	 
         }		

		return ratingDropDown;
	}

	@RequestMapping(value = "/saveAllObjectives.html", method = RequestMethod.POST)
	public String saveAllObjectives(
			@ModelAttribute("objectiveForm") ObjectiveForm objectiveForm,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			@ModelAttribute("ratingDropDown") Map<Integer, String> ratingDropDown,HttpServletRequest request) {
		if (isSavable(reviewFormInfoVO)) {
			ReviewHeader reviewHeader = reviewHeaderService
					.getReviewHeaderById(reviewFormInfoVO.getReviewHeaderId());// got
																				// review
																				// header

			List<ReviewObjective> domainReviewObjs = 
					reviewObjectiveService.getReviewObjectives(reviewFormInfoVO.getReviewHeaderId(), reviewFormInfoVO.getReviewFormRole());// got all Domain objectives.....

			List<ReviewObjectiveVO> reviewObjectiveVOsinitial = new ArrayList<ReviewObjectiveVO>();
		
			for (ReviewObjective domainReviewObj : domainReviewObjs) {
				ReviewObjectiveVO voReviewObj = new ReviewObjectiveVO();
				reviewObjectiveService.copyToVOBean(voReviewObj,
						domainReviewObj, reviewFormInfoVO);
				reviewObjectiveVOsinitial.add(voReviewObj);
			}

			List<ReviewObjectiveVO> voReviewObjs;
			voReviewObjs = objectiveForm.getObjectives();// got all VO
															// objectives
			// List<Integer> reviewObjIdsToMerge = new ArrayList<Integer>();

			if (voReviewObjs != null) {
				for (ReviewObjectiveVO voReviewObj : voReviewObjs) {
					if (voReviewObj.getObjectiveIndex() != null) {

						if (voReviewObj.getReviewObjectiveId() == 0) {
							ReviewObjective newDomainReviewObj = new ReviewObjective();
							newDomainReviewObj.setCreatedDate(new Date());
							if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
								newDomainReviewObj.setOwner(reviewFormInfoVO.getReviewFormRole());
							} else {
								newDomainReviewObj.setOwner(ReviewFormRole.APPRAISER);
							}
							newDomainReviewObj.setCreatedBy(reviewFormInfoVO.getReviewFormRole());
							if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
								if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1) {
									reviewObjectiveService
											.copyAppraisePhase1Fields(
													newDomainReviewObj,
													voReviewObj,
													reviewFormInfoVO);

								} else if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2
										|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED) {
									reviewObjectiveService
											.copyAppraisePhase2Fields(
													newDomainReviewObj,
													voReviewObj,
													reviewFormInfoVO);
								}
							}
							// new feature proposed in UAT phase.. manager
							// allowed to add Objectives...

							else {
								if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1) {
									reviewObjectiveService
											.copyAppraisePhase1Fields(
													newDomainReviewObj,
													voReviewObj,
													reviewFormInfoVO);

								} 
							}
							newDomainReviewObj.setReviewHeader(reviewHeader);							
								newDomainReviewObj
										.setReviewPhase(reviewFormInfoVO
												.getReviewPhase());

								newDomainReviewObj.setReviewStatus(reviewHeader
										.getReviewStatus()
										.getReviewStatusCode());
							
							reviewObjectiveService.persist(newDomainReviewObj);
							/*reviewHeader.getReviewObjectives().add(
									newDomainReviewObj);*/

						} else {
							for (ReviewObjective domainReviewObj : domainReviewObjs) {
								if (voReviewObj.getReviewObjectiveId() == domainReviewObj
										.getReviewObjectiveId()) {
									if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
										if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1) {
											if (!domainReviewObj.isApproved()) {
												reviewObjectiveService
														.copyAppraisePhase1Fields(
																domainReviewObj,
																voReviewObj,
																reviewFormInfoVO);
											}

										} else if (reviewFormInfoVO
												.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2
												|| reviewFormInfoVO
														.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED) {
											reviewObjectiveService
													.copyAppraisePhase2Fields(
															domainReviewObj,
															voReviewObj,
															reviewFormInfoVO);
										}
									} else {
										if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2) {

											reviewObjectiveService
													.copyAppraiserPhase2Fields(
															domainReviewObj,
															voReviewObj,
															reviewFormInfoVO);

										}
										else if (reviewFormInfoVO
														.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1) {
											if (!domainReviewObj.isApproved()) {
												reviewObjectiveService
														.copyAppraisePhase1Fields(
																domainReviewObj,
																voReviewObj,
																reviewFormInfoVO);
											}

										}
										
									}
									break;
								}
							}
						}
					}
				}
			}
			reviewHeader.setLastModifiedDate(new Date());
			reviewHeader.setLastModifiedByEmployee(employeeService
					.getEmployeeById(SecurityDetailsAccessor.getEmpId()));
			reviewHeaderService.merge(reviewHeader);
			voReviewObjs = new ArrayList<ReviewObjectiveVO>();
			for (ReviewObjective domainReviewObj : 
				reviewObjectiveService.getReviewObjectives(reviewFormInfoVO.getReviewHeaderId(), reviewFormInfoVO.getReviewFormRole())) {
				ReviewObjectiveVO voReviewObj = new ReviewObjectiveVO();
				reviewObjectiveService.copyToVOBean(voReviewObj,
						domainReviewObj, reviewFormInfoVO);
				voReviewObjs.add(voReviewObj);
			}
			List<ReviewActionLog> reviewActionLogs = reviewAuditService
					.getReviewActionLog(ReviewObjectiveVO.class,
							reviewObjectiveVOsinitial, voReviewObjs,
							reviewFormInfoVO);
			reviewAuditService.saveReviewActionLog(reviewActionLogs);
			objectiveForm.setObjectives(voReviewObjs);
			objectiveForm.setProjectDropDown(getProjectsDropDown(request, reviewFormInfoVO));
			

			return "appraisalForm/objectives/objectiveForm";
		} else {

			throw new RuntimeException("Invalid Operation");
		}
	}

	@RequestMapping(value = "/removeObjective.html", method = RequestMethod.POST)
	public @ResponseBody
	boolean removeObjective(
			int reviewObjectiveId,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {
		ReviewObjective reviewObjective = reviewObjectiveService
				.getReviewObjective(reviewObjectiveId);

		boolean valuetoReturn = false;

		if (reviewObjective == null) {

		} else if (!reviewObjective.isApproved()
				&& reviewObjective.getReviewHeader().getReviewHeaderId() == reviewFormInfoVO
						.getReviewHeaderId()
				&& ((reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE && (reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1 || reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2)) || ((reviewFormInfoVO
						.getReviewFormRole() == ReviewFormRole.APPRAISER
						|| reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER || reviewFormInfoVO
						.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER) && (reviewFormInfoVO
						.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1)))) {
			if (reviewObjectiveService.remove(reviewObjectiveId)) {
				ReviewActionLog reviewActionLog = reviewAuditService
						.createReviewActionLog(reviewFormInfoVO, "DELETE",
								ReviewFeildGroupType.OBJECTIVE,
								reviewObjective.getObjectiveName(), null, null,
								null, null);
				reviewAuditService.saveReviewActionLog(reviewActionLog);
				valuetoReturn = true;

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
