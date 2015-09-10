package com.ggk.hrms.review.controller;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.ReviewSummaryService;
import com.ggk.hrms.review.task.AppraisalSummaryEmailTask;
import com.ggk.hrms.review.ui.form.ReviewSummaryForm;
import com.ggk.hrms.review.ui.vo.OtherAppraisalsVO;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewSummaryVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/appraisal")
public class ReviewSummaryController {

	@Autowired
	ReviewHeaderService reviewHeaderService;
	@Autowired
	RatingService ratingService;
	@Autowired
	ReviewFormService reviewFormService;
	@Autowired
	ReviewAuditService reviewAuditService;
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	ReviewSummaryService reviewSummaryService;
	
	@Resource
	int daysLeftForSuperUser;
	
	@Autowired
	private MailingService mailingService;
	
	

	@RequestMapping(value = "/summary.html", method = {RequestMethod.POST,RequestMethod.GET})
	public String showReveiwHeader(
			ModelMap model,
			@RequestParam(required = false, value = "isInternal") String isInternal,HttpServletRequest request) {
		
		
		ReviewFormInfoVO reviewFormInfoVO=(ReviewFormInfoVO)model.get("reviewFormInfoVO");
		ReviewFormRole reviewFormRole=reviewFormInfoVO.getReviewFormRole();
		String actualReviewStatus=reviewFormInfoVO.getActualReviewStatus();
		if(reviewFormRole.getDescription().equals("APPRAISE")||(actualReviewStatus.equals("GOALS_SUBMITTED")||
				actualReviewStatus.equals("GOALS_ACCEPTED")||actualReviewStatus.equals("NEED_TO_EDIT_GOALS")
				||actualReviewStatus.equals("GOALS_FINALIZED")
				||actualReviewStatus.equals("APPRAISAL_IN_PROGRESS")
				||actualReviewStatus.equals("NEED_TO_EDIT_APPRAISAL")
				||actualReviewStatus.equals("APPRAISAL_SUBMITTED")
				||actualReviewStatus.equals("ASSESSMENT_IN_PROGRESS")
				||actualReviewStatus.equals("COMPLETED")
				||actualReviewStatus.equals("READY_FOR_MEETING"))){
			
			int reviewHeaderId = reviewFormInfoVO.getReviewHeaderId();
			ReviewSummaryForm reviewSummaryForm = new ReviewSummaryForm();
			/*ReviewHeaderVO reviewHeaderVO = new ReviewHeaderVO();
			reviewHeaderVO = reviewHeaderService.getReviewSummary(reviewHeaderId);
			reviewSummaryForm.setReviewHeaderVO(reviewHeaderVO);*/
			ReviewSummary reviewSummary=reviewSummaryService.getReviewSummary(reviewHeaderId, reviewFormRole);
			if(reviewSummary==null){
				
				reviewSummary= new ReviewSummary();
				if(reviewFormRole.getDescription().equals("APPRAISE")){
					reviewSummary.setOwner(ReviewFormRole.APPRAISE);
				}
				else {
					reviewSummary.setOwner(ReviewFormRole.APPRAISER);
				}
				//reviewSummary.setOwner(reviewFormRole); // bug fix in prod(Multiple records for a ReviewHeaderId and Owner in ReviewSummary table)
				ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(reviewHeaderId);
				reviewSummary.setReviewHeader(reviewHeader);
				reviewSummary=reviewSummaryService.saveReviewSummary(reviewSummary);
				
			}
			ReviewSummaryVO reviewSummaryVO= new ReviewSummaryVO();
			reviewSummaryService.convertToVO(reviewSummaryVO, reviewSummary);
			reviewSummaryForm.setReviewSummaryVO(reviewSummaryVO);
			List<ReviewActionLogVO> reviewActionLogVOs=reviewHeaderService.getAllInternalComments(reviewHeaderId, reviewFormRole.getDescription());
			reviewSummaryForm.setReviewActionLogVOs(reviewActionLogVOs);
			model.put("reviewSummaryForm", reviewSummaryForm);
			model.put("reviewFormRole", reviewFormRole);
			
			//
			ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(reviewFormInfoVO.getReviewHeaderId());
			/*Date date=new Date();
			
			long daysPending = (date.getTime()-reviewHeader.getLastModifiedDate().getTime()) / (1000 * 60 * 60 * 24);
			model.put("daysPending",daysPending);
			model.put("daysLeftForSuperUser",daysLeftForSuperUser);
			
			
			List<String> authorities = SecurityDetailsAccessor.getAuthorities();
			
			boolean isModifiable = false;
			if(authorities.contains("ROLE_SUPERUSER")){
				isModifiable = true;
			}
			model.put("isModifiable", isModifiable);*/
			
			int LoggedInEmpId = SecurityDetailsAccessor.getEmpId();
			int currentReviewCycleId = reviewHeader.getReviewCycle().getReviewCycleId();
			
			if (reviewFormInfoVO.getReviewFormRole().getDescription().equals("APPRAISE")) {
				List<OtherAppraisalsVO> otherAppraisals = reviewHeaderService.getOtherAppraisalsForEmp(LoggedInEmpId, currentReviewCycleId);
				model.addAttribute("otherAppraisals", otherAppraisals);
			}
			else if (reviewFormInfoVO.getReviewFormRole().getDescription().equals("SUPERUSER")) {
				List<OtherAppraisalsVO> otherAppraisals = reviewHeaderService.getOtherAppraisalsForSuperUser(currentReviewCycleId, reviewSummary.getReviewHeader().getEmployee().getEmpId());
				model.addAttribute("otherAppraisals", otherAppraisals);
			}
			
			else if (reviewFormInfoVO.getReviewFormRole().getDescription().equals("APPRAISER")) {
				List<OtherAppraisalsVO> otherAppraisals;
				if (reviewFormInfoVO.getIsManagersMgr() == false){
					otherAppraisals = reviewHeaderService.getOtherAppraisalsForMgr(LoggedInEmpId, currentReviewCycleId, reviewSummary.getReviewHeader().getEmployee().getEmpId());
				}else{
					
					otherAppraisals = reviewHeaderService.getOtherAppraisalsForSuperUser(currentReviewCycleId, reviewSummary.getReviewHeader().getEmployee().getEmpId());
					Iterator<OtherAppraisalsVO> otherAppraisalsVOIterator = otherAppraisals.iterator();
					while (otherAppraisalsVOIterator.hasNext()){
						OtherAppraisalsVO otherAppraisalsVO = (OtherAppraisalsVO) otherAppraisalsVOIterator.next();
						if (reviewFormService.isLoggedInUserHasAccesstoIt(otherAppraisalsVO.getReviewHeaderId())== false){
							
							otherAppraisalsVOIterator.remove();
						}
						
					}
					
				}
				model.addAttribute("otherAppraisals", otherAppraisals);
			}
			
			
			
			//<!-- PMS 2.0 Changes to open review from in new tab -->
			// Display appraise name as tab title instead of PMS Home in every tab.
			if ( request.getMethod().equals("GET")) {
				Employee employee = reviewHeader.getEmployee();
				String reviewCycleName = reviewFormInfoVO.getReviewCycleName();
				String employeeDisplayName = employee.getDisplayName();
				model.addAttribute("appraisalName", reviewCycleName+" : "+employeeDisplayName);
				return "appraisalForm/reviewSummaryGet";
			}else if (isInternal == null){
				
				return "appraisalForm/reviewSummary";
			}  else {
				return "appraisalForm/summary/reviewSummary";
			}
		} else
		{
			model.addAttribute("errorMessage","You cant view this content");
			return "error/errorPage";
		}
	}
	

	@RequestMapping(value = "/saveSummary.html", method = RequestMethod.POST)
	public String saveReveiwHeader(
			ModelMap model,
			@ModelAttribute("reviewSummaryForm") ReviewSummaryForm reviewSummaryForm,
			@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,
			@ModelAttribute("ratingDropDownItems") Map<Integer, String> ratingDropDownItems) {
		
		int reviewHeaderId = reviewFormInfoVO.getReviewHeaderId();
		ReviewSummaryVO reviewSummaryVOUpdated = reviewSummaryForm
				.getReviewSummaryVO();
		ReviewSummary reviewSummaryInitial = reviewSummaryService
				.getReviewSummary(reviewHeaderId,
						reviewFormInfoVO.getReviewFormRole());
		ReviewSummaryVO reviewSummaryVOInitial = new ReviewSummaryVO();
		reviewSummaryService.convertToVO(reviewSummaryVOInitial,
				reviewSummaryInitial);
		reviewSummaryService.convertToDTO(reviewSummaryInitial,
				reviewSummaryVOUpdated, reviewFormInfoVO);

		if (isSavable(reviewFormInfoVO)) {

			ReviewSummary reviewSummaryUpdated = reviewSummaryService
					.saveReviewSummary(reviewSummaryInitial);
			reviewSummaryVOUpdated = new ReviewSummaryVO();
			reviewSummaryService.convertToVO(reviewSummaryVOUpdated,
					reviewSummaryUpdated);
			List<ReviewActionLog> reviewActionLogs = reviewAuditService
					.getReviewActionLog(ReviewSummaryVO.class,
							reviewSummaryVOInitial, reviewSummaryVOUpdated,
							reviewFormInfoVO);
			reviewAuditService.saveReviewActionLog(reviewActionLogs);
			reviewSummaryForm.setReviewSummaryVO(reviewSummaryVOUpdated);
			model.put("reviewSummaryForm", reviewSummaryForm);
			// return "success";
			if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE2_COMPLETED
					&& reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER) {
				// Save updated rating to Appraiser's copy as well.
				ReviewSummary appraiseReviewSummary = reviewSummaryService
						.getReviewSummary(reviewHeaderId,
								ReviewFormRole.APPRAISE);
				Integer updatedAppraiserRating ;
				Rating rating = null;
				if(reviewSummaryVOUpdated.getAppraiserRatingId()!=null){
					rating = ratingService
							.getRatingById(reviewSummaryVOUpdated
									.getAppraiserRatingId());
				}
				if (rating == null) {
					appraiseReviewSummary.setAppraiserRating(null);
					updatedAppraiserRating = -1;
					
				} else {
					
					appraiseReviewSummary.setAppraiserRating(rating);
					updatedAppraiserRating = rating.getRating();
				}
				reviewSummaryService.saveReviewSummary(appraiseReviewSummary);
				// Done with saving appraise copy as well.
				ReviewHeader reviewHeader = reviewHeaderService
						.getReviewHeaderById(reviewHeaderId);
				ReviewCycle reviewCycle = reviewHeader.getReviewCycle();
				String employeeEmailId = reviewHeader.getEmployee().getEmail();
				String employeeDisplayName = reviewHeader.getEmployee().getDisplayName();
				String managerEmailId = reviewHeader.getMainAppraiserEmployee()
						.getEmail();

				String loggedInEmployee = SecurityDetailsAccessor.getFullName();
				new Thread(new AppraisalSummaryEmailTask(mailingService,
						employeeEmailId, (new String[] { managerEmailId }),
						"Change Of Rating ( "
								+ reviewCycle.getReviewCycleName() + " )",
						"Hi "+employeeDisplayName+",\n\n" + loggedInEmployee
								+ " has changed your final rating to "
								+ updatedAppraiserRating + ".")).start();
			}
			return "appraisalForm/summary/appraisalSummary";
		}

		throw new RuntimeException("Invalid Operation");
			
	}
	
	// to get the values in rating drop down
	@ModelAttribute("ratingDropDownItems")
	Map<Integer, String> getRatingDropDown(HttpServletRequest request) {
		Map<Integer, String> ratingDropDown = (Map<Integer, String>)request.getSession().getAttribute("ratingDropDownItems");
        if (ratingDropDown == null || ratingDropDown.size() == 0){
       	 
       	 ratingDropDown = ratingService.getRatingsDropDown();
       	request.getSession().setAttribute("ratingDropDownItems", ratingDropDown);        	 
        }		

		return ratingDropDown;
	}

	@ModelAttribute("reviewFormInfoVO")
	ReviewFormInfoVO createReviewFormInfoVO(
			@RequestParam(value = "reviewHeaderId") int reviewHeaderId,
			@RequestParam(value = "opsType", required = false) String opsType) {

		return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(), opsType);

	}
	
	private boolean isSavable(ReviewFormInfoVO reviewFormInfoVO) {
		
		boolean valueToReturn = false;
		String actualStatus=reviewFormInfoVO.getActualReviewStatus();
		ReviewFormRole reviewFormRole=reviewFormInfoVO.getReviewFormRole();
		if (reviewFormRole == ReviewFormRole.APPRAISE
				&&(actualStatus.equals("GOALS_ACCEPTED")|| actualStatus.equals("APPRAISAL_IN_PROGRESS")
						||actualStatus.equals("NEED_TO_EDIT_APPRAISAL")))
			 {

			valueToReturn = true;

		}

		if ((reviewFormRole == ReviewFormRole.APPRAISER
				|| reviewFormRole == ReviewFormRole.SHARED_APPRAISER || reviewFormRole == ReviewFormRole.SUPERUSER)
				&& (actualStatus.equals("APPRAISAL_SUBMITTED")|| actualStatus.equals("ASSESSMENT_IN_PROGRESS"))) {

			valueToReturn = true;

		}
		
		if(reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE2_COMPLETED && reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER){
			
			valueToReturn = true;
		}
		return valueToReturn;
		
		
	}

}
