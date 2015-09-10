package com.ggk.hrms.review.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewCompetency;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ProficiencyLevelCompetencyService;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.form.ReviewCompetenyForm;
import com.ggk.hrms.review.ui.vo.ReviewCompetencyVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/appraisal")
@SessionAttributes(value = { "proficiencyLevelDropDown","ratingsDropDown" })
public class ReviewCompetencyFormController {

	@Autowired
	private ReviewHeaderService reviewHeaderService;

	@Autowired
	private ReviewCompetencyService reviewCompetencyService;

	@Autowired
	private ProficiencyLevelService proficiencyLevelService;

	@Autowired
	private ReviewFormService reviewFormService;

	@Autowired
	private ReviewAuditService reviewAuditService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ProficiencyLevelCompetencyService proficiencyLevelCompetencyService;
	
	private Logger log = Logger.getLogger(ReviewCompetencyFormController.class);

	@RequestMapping(value = "/getCompetencies.html", method = RequestMethod.POST)
	public String getReviewCompetencyForm(
			@RequestParam("reviewHeaderId") int reviewHeaderId,
			ModelMap model,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			@ModelAttribute("ratingsDropDown") Map<Integer, String> ratingsDropDown,
			@ModelAttribute("proficiencyDropDown") Map<Integer, String> proficiencyDropDown) {
		Date currentDate = new Date();
		log.debug("\n\n\n######################### getReviewCompetencyForm() Start of flow "+currentDate+"   ################################\n\n\n");	
		if(showOrHide(reviewFormInfoVO)){
		
			
			ReviewCompetenyForm competencyForm = new ReviewCompetenyForm();

			List<ReviewCompetency> reviewCompetencies = reviewCompetencyService
					.getReviewCompetencies(reviewHeaderId,reviewFormInfoVO.getReviewFormRole());

			if (reviewCompetencies.size() == 0) {
				// if review competences don't exist for a user..code will
				// create ReviewCompetencies without comments n ratings
				 reviewCompetencyService
						.createReviewCompetencies(reviewHeaderId,reviewFormInfoVO.getReviewFormRole());
			
				 reviewCompetencies = reviewCompetencyService
							.getReviewCompetencies(reviewHeaderId,reviewFormInfoVO.getReviewFormRole()); // getting
																	// persisted
																	// ones
			}

			
			List<ReviewCompetencyVO> reviewCompetencyVOs = new ArrayList<ReviewCompetencyVO>();
			reviewCompetencyService.convertReviewCompetencyDomainToVO(
					reviewCompetencies, reviewCompetencyVOs,reviewFormInfoVO);
			competencyForm.setReviewCompetencyVOs(reviewCompetencyVOs);
			competencyForm.setProficiencyLevels(proficiencyDropDown);
			competencyForm.setRatings(ratingsDropDown);
			model.put("competencyForm", competencyForm);
			log.debug("\n\n\n######################## getReviewCompetencyForm() END of flow ############################################");	
			return "appraisalForm/competencies/reviewCompetencies";
		}
		else {
			
			throw new RuntimeException("Invalid Operation");
		}
		
	}

	@RequestMapping(value = "/saveCompetencies.html", method = RequestMethod.POST)
	public String saveReviewCompetencies(
			@ModelAttribute("competencyForm") ReviewCompetenyForm competencyForm,
			ModelMap model,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			@ModelAttribute("ratingsDropDown") Map<Integer, String> ratingsDropDown,
			@ModelAttribute("proficiencyDropDown") Map<Integer, String> proficiencyDropDown) {
		if(isSavable(reviewFormInfoVO)){
		List<ReviewCompetency> reviewCompetencies = reviewCompetencyService
				.getReviewCompetencies(reviewFormInfoVO.getReviewHeaderId(),reviewFormInfoVO.getReviewFormRole());// retrived initial copy before committing changes....
		List<ReviewCompetencyVO> reviewCompetencyVOsInitail = new ArrayList<ReviewCompetencyVO>();//initial vos from intial competencies
		reviewCompetencyService.convertReviewCompetencyDomainToVO(
				reviewCompetencies, reviewCompetencyVOsInitail,reviewFormInfoVO);//populated initial Vos with values for logging..

		List<ReviewCompetencyVO> reviewCompetencyVOsUpdated = competencyForm
				.getReviewCompetencyVOs();// got updated vos from Browser...i mean request object...
		reviewCompetencyService.convertReviewCompetencyVOToDomain(
				reviewCompetencyVOsUpdated, reviewCompetencies,
				reviewFormInfoVO);// changed reviewCompetencies retrived from DB to reflect new changes...
		ReviewHeader reviewHeader =reviewHeaderService.getReviewHeaderById(reviewFormInfoVO.getReviewHeaderId());
		reviewHeader.setReviewCompetencies(reviewCompetencies);// setting updated review Competencies to review Header...
		reviewHeader.setLastModifiedDate(new Date());
		Employee loginEmployee=employeeService.getEmployeeById(SecurityDetailsAccessor.getEmpId());
		reviewHeader.setLastModifiedByEmployee(loginEmployee);
		reviewHeaderService.merge(reviewHeader);// merged review Header...
//		reviewCompetencies = reviewCompetencyService
//				.saveReviewCompetencies(reviewHeader, reviewCompetencies);
		reviewCompetencies= reviewCompetencyService
				.getReviewCompetencies(reviewFormInfoVO.getReviewHeaderId(),reviewFormInfoVO.getReviewFormRole());
		reviewCompetencyVOsUpdated = new ArrayList<ReviewCompetencyVO>();
		reviewCompetencyService.convertReviewCompetencyDomainToVO(
				reviewCompetencies, reviewCompetencyVOsUpdated,reviewFormInfoVO);
		// competencyForm.setReviewCompetencyVOs(reviewCompetencyVOsUpdated);
		// morphing ids to values
//		reviewAuditService.substituteRatingIdsWithRatingValues(
//				reviewCompetencyVOsUpdated, ratingsDropDown,
//				ReviewCompetencyVO.class);
//		reviewAuditService.substituteRatingIdsWithRatingValues(
//				reviewCompetencyVOsInitail, ratingsDropDown,
//				ReviewCompetencyVO.class);
		//
		List<ReviewActionLog> reviewActionLogs = reviewAuditService
				.getReviewActionLog(ReviewCompetencyVO.class,
						reviewCompetencyVOsInitail, reviewCompetencyVOsUpdated,
						reviewFormInfoVO);
		reviewAuditService.saveReviewActionLog(reviewActionLogs);
		// morphing values to ids
//		reviewAuditService.substituteRatingValuesWithRatingIds(
//				reviewCompetencyVOsUpdated, ratingsDropDown,
//				ReviewCompetencyVO.class);
		competencyForm.setReviewCompetencyVOs(reviewCompetencyVOsUpdated);
		competencyForm.setRatings(ratingsDropDown);
		competencyForm.setProficiencyLevels(proficiencyDropDown);
		return "appraisalForm/competencies/reviewCompetencies";
		}
		
		else {
			
			throw new RuntimeException("Invalid Operation");
		}
	}

	@ModelAttribute("ratingsDropDown")
	public Map<Integer, String> getRatingDropDown(HttpServletRequest request) {

		Map<Integer, String> ratingDropDown = (Map<Integer, String>)request.getSession().getServletContext().getAttribute("ratingDropDown");
        if (ratingDropDown == null || ratingDropDown.size() == 0){
       	 
       	 ratingDropDown = ratingService.getRatingsDropDown();
       	request.getSession().getServletContext().setAttribute("ratingDropDown", ratingDropDown);        	 
        }		

		return ratingDropDown;

	}
	
	@ModelAttribute("proficiencyDropDown")
	public Map<Integer, String> getproficiencyLevelDropDown(HttpServletRequest request) {
		

		Map<Integer, String> proficiencyDropDown = (Map<Integer, String>)request.getSession().getServletContext().getAttribute("proficiencyDropDown");
        if (proficiencyDropDown == null || proficiencyDropDown.size() == 0){
       	 
        	proficiencyDropDown = proficiencyLevelService.getProficiencyLevelDropDown();
       	request.getSession().getServletContext().setAttribute("proficiencyDropDown", proficiencyDropDown);        	 
        }		

		return proficiencyDropDown;

	}
	
	@RequestMapping(value="/getBehavioralIndicator.html", method = RequestMethod.POST)
	public @ResponseBody String getBehavioralIndicator(@RequestParam(value="competencyId") int competencyId,@RequestParam(value="proficiencyLevelId") int proficiencyLevelId,
			@ModelAttribute(value="reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO){
		
		
		
		return proficiencyLevelCompetencyService.getBehavioralIndicator(competencyId, proficiencyLevelId);
		
		
		
	}

	@ModelAttribute("reviewFormInfoVO")
	ReviewFormInfoVO createReviewFormInfoVO(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "opsType", required = false) String opsType) {
		Integer empId = SecurityDetailsAccessor.getEmpId();
		return reviewFormService.getReviewFromInfoVO(reviewHeaderId, empId,opsType);
	}
	
	
	public boolean showOrHide(ReviewFormInfoVO reviewFormInfoVO) {

		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE
				|| (reviewFormInfoVO.getActualReviewStatus().equals(
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
								"COMPLETED") || reviewFormInfoVO
						.getActualReviewStatus().equals("READY_FOR_MEETING"))) {

			return true;
		}
		return false;
	}
	
	
	private boolean isSavable(ReviewFormInfoVO reviewFormInFoVO){
		
		boolean valueToReturn=false;
		
		if(reviewFormInFoVO.getReviewFormRole()==ReviewFormRole.APPRAISE && (reviewFormInFoVO.getReviewPhase()==ReviewPhase.SYSTEM_PHASE1_COMPLETED || reviewFormInFoVO.getReviewPhase()==ReviewPhase.APPRAISE_IN_PHASE2 ||(reviewFormInFoVO.getReviewPhase()==ReviewPhase.APPRAISE_IN_PHASE1 && !reviewFormInFoVO.getActualReviewStatus().equals("GOALS_FINALIZED") ))){
			
			
			valueToReturn=true;
			
		}
		
    if((reviewFormInFoVO.getReviewFormRole()==ReviewFormRole.APPRAISER||reviewFormInFoVO.getReviewFormRole()==ReviewFormRole.SHARED_APPRAISER||reviewFormInFoVO.getReviewFormRole()==ReviewFormRole.SUPERUSER) && (reviewFormInFoVO.getReviewPhase()==ReviewPhase.APPRAISER_IN_PHASE2 ||reviewFormInFoVO.getReviewPhase()==ReviewPhase.APPRAISER_IN_PHASE1)){
			
			valueToReturn=true;
			
		}
		
		return valueToReturn;
	}


}
