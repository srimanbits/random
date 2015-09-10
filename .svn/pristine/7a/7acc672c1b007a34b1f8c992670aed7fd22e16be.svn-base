package com.ggk.hrms.review.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.ReviewStatus;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.Role;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.service.ReviewDevelopmentPlanService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.ReviewObjectiveService;
import com.ggk.hrms.review.service.ReviewSummaryService;
import com.ggk.hrms.review.service.SharedAppraiserService;
import com.ggk.hrms.review.task.AppraisalFormPDFTask;
import com.ggk.hrms.review.ui.form.ShareAppraisalForm;
import com.ggk.hrms.review.ui.vo.AppraisalShareInfoVO;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/appraisal")
public class ReviewIndexController {

	Logger log = Logger.getLogger(ReviewIndexController.class);

	@Autowired
	private ReviewHeaderService reviewHeaderService;
	
	@Autowired
	private ReviewSummaryService reviewSummaryService;

	/*
	 * @Autowired private ReviewFormService reviewFormService;
	 */

	@Resource
	private ReviewCycleService reviewCycleService;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private MailingService mailingService;
	
	
	@Resource
	private ReviewObjectiveService reviewObjectiveService;

	@Resource
	private ReviewDevelopmentPlanService reviewDevelopmentPlanService;

	@Resource
	private ReviewCompetencyService reviewCompetencyService;


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

	public static String regex = "(([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4}))(((;|,|; | ;| ; | , | ,){1}"
			+ "([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4}))*)";
	
	private static List<String> excelSheetsPerReviewCycle = new ArrayList<String>(Arrays.asList("Employee Id","Appraisal", "Email Address", "Grade Designation", 
			"Status", "Project", "AppraiseRatingId", "AppraiserRatingId"));
	
	private static List<String> excelSheetsPerProject =new ArrayList<String>(Arrays.asList("Employee Id","Appraisal", "Email Address", "Grade Designation", 
			"Status", "Main Appraiser", "AppraiseRatingId", "AppraiserRatingId"));
	
	private static List<String> singleExcelSheetsPerReviewCycle = new ArrayList<String>(Arrays.asList("Employee Id","Appraisal", "Email Address", "Grade Designation", 
			"Status", "Main Appraiser","Project", "AppraiseRatingId", "AppraiserRatingId"));
	
	
	
	

	// to get the values in review cycle drop down
	//@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER" })
	@ModelAttribute("reviewCycleDropDownItems")
	List<DropDownVO> getRatingDropDown(HttpServletRequest request) {
		List<DropDownVO> reviewCycleDropDownItems = (List<DropDownVO>)request.getSession().getAttribute("reviewCycleDropDownItems");
        if (reviewCycleDropDownItems == null || reviewCycleDropDownItems.size() == 0){
       	 
        	reviewCycleDropDownItems = reviewCycleService.getReviewCycleDropDown();
       	request.getSession().setAttribute("reviewCycleDropDownItems", reviewCycleDropDownItems);        	 
        }		

		return reviewCycleDropDownItems;
	}

	@RequestMapping(value = "/Home.html", method = RequestMethod.POST)
	public String showAppraisalHome(Model model,
			@RequestParam(value = "selfOrPeers") String selfOrPeers) {
		model.addAttribute("selfOrPeers", selfOrPeers);
		/*if (selfOrPeers.equals("self")) {
			getAppraisalList(model, 0, "pending", "self");
		} else if (selfOrPeers.equals("peers")) {
			getAppraisalList(model, 0, "all", "peers");
		} else if (selfOrPeers.equals("all")) {
			getAppraisalList(model, 0, "all", "all");
		} else if (selfOrPeers.equals("shared")) {
			getAppraisalList(model, 0, "all", "shared");
		}*/
		if (SecurityDetailsAccessor.getAuthorities().contains(
				Role.ROLE_SUPERUSER) && selfOrPeers.equals("all")) {
			model.addAttribute("isSuperUser", true);
		}
		return "appraisalForm/index/appraisalHome";
	}

	// for getting the appraisals
	@RequestMapping(value = "/appraisalData.html", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json")
    public @ResponseBody DataTablesJSONWrapper getAppraisalTableData(HttpServletRequest  request,@RequestParam(value = "reviewCycleId") int reviewCycleId,
			@RequestParam(value = "state") String state,
			@RequestParam(value = "selfOrPeers") String selfOrPeers,ModelMap model) throws IOException {
		
		DataTablesJSONWrapper reviewFormLinkVOWrapper = new DataTablesJSONWrapper();
    	reviewFormLinkVOWrapper.setsEcho(request.getParameter("sEcho"));
    	
   
    	if(reviewCycleId == -1 || state.equals("-1")){
    		List<ReviewFormLinkVO> reviewFormLinks = Collections.EMPTY_LIST;
    		reviewFormLinkVOWrapper.setAaData(reviewFormLinks);
    		reviewFormLinkVOWrapper.setiTotalDisplayRecords(0l);
    		reviewFormLinkVOWrapper.setiTotalRecords(0l);
    		return reviewFormLinkVOWrapper;
    	}
    	String contextPath=request.getContextPath();
    	//Create page list data
    	List<ReviewFormLinkVO> reviewFormLinks = getAppraisalList(request,reviewCycleId,state,selfOrPeers,reviewFormLinkVOWrapper,model);
    	//create table data and set to form
    	setTableData(request,reviewFormLinks,contextPath,selfOrPeers);

		//set Table Data
		reviewFormLinkVOWrapper.setAaData(reviewFormLinks);		
		
		
		
	
		return reviewFormLinkVOWrapper;
    }
    @SuppressWarnings("unchecked")
	private List<ReviewFormLinkVO> getAppraisalList( HttpServletRequest request,int reviewCycleId,String state,String selfOrPeers,DataTablesJSONWrapper reviewFormLinkVOWrapper,ModelMap model) {
		
		List<ReviewFormLinkVO> reviewFormLinks = new ArrayList<ReviewFormLinkVO>();
		int employeeId=	SecurityDetailsAccessor.getEmpId();
		
		//Swaroops add current review cycle.. one year request change
		request.setAttribute("reviewCycle", reviewCycleService.getActiveReviewCycle());		
		
		//Fetch the next page number from client
    	Integer startIndex = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		startIndex = Integer.valueOf(request.getParameter("iDisplayStart"));		
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
		//Fetch search parameter
    	String searchValue = request.getParameter("sSearch");
    	
    	//Fetch sorting column
    	String sortDirection="asc";
    	String colIndex = request.getParameter("iSortCol_0");
    	sortDirection = request.getParameter("sSortDir_0");
    	
    	String colName=getSortColName(colIndex,selfOrPeers,sortDirection);
		
		
		
    	// if employee is viewing self appraisal
        if(selfOrPeers.equals("self")){
		
		//if employee is viewing his pending appraisals
		if(state.equals("pending")) {
			reviewFormLinks = reviewHeaderService.getReviewFormIndex(employeeId,startIndex,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper,true);
			reviewFormLinkVOWrapper.setiTotalDisplayRecords(Long.valueOf(reviewFormLinks.size()+""));
			reviewFormLinkVOWrapper.setiTotalRecords(1l);
		}
		//if employee is viewing all his appraisals
		else {
			reviewFormLinks =reviewHeaderService.getReviewFormIndex(employeeId,startIndex,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper,false);

		}

					// looping through review form links
					for (int i = 0; i < reviewFormLinks.size(); i++) {
						ReviewFormLinkVO reviewFormLinkVO = reviewFormLinks.get(i);
						Boolean isReady = false;
						String reviewStatusTobeDisplayed = null;
						// getting the actual status of the appraisal
						ReviewStatus reviewStatus = new ReviewStatus();
						reviewStatus.setReviewStatusCode(reviewFormLinkVO
								.getReviewFormStatus());
						// checking the logged in employee is requesting for his own
						// appraisal
						if (employeeId == reviewFormLinkVO.getEmployeeId()) {
							isReady = true;
							request.setAttribute("loginRole", "APPRAISE");
							reviewStatusTobeDisplayed = ReviewStatus
									.getAppraiseReviewStatus(reviewStatus);
							reviewFormLinkVO.setIsReady(isReady);
							reviewFormLinkVO
									.setReviewFormStatus(reviewStatusTobeDisplayed);
							/*reviewFormLinkVO
									.setAppraisalShareInfoVO(reviewHeaderService
											.getAppraisalShareInfo(employeeId,
													reviewFormLinkVO
															.getReviewHeaderId()));*/
							reviewFormLinkVO
							.setAppraisalShareInfoVO(reviewHeaderService
									.getAppraisalShareInfo(employeeId,
											reviewFormLinkVO
													));
						}
					}
				}else if (selfOrPeers.equals("shared")) {
					ReviewCycle reviewCycle;
					// if review cycle id is 0, getting active review cycle id else
					// getting the review cycle object of the passed review cycle id
					if (reviewCycleId != 0) {
						reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
					} else {
						reviewCycle = reviewCycleService.getActiveReviewCycle();
					}
					// Swaroops add current review cycle.. one year request change
					request.setAttribute("reviewCycle", reviewCycle);
					//
					reviewFormLinks = reviewHeaderService
							.getSharedReviewFormIndexByReviewCycleId(employeeId,
									reviewCycle.getReviewCycleId(),startIndex,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper);

					// looping through review form links
					for (int i = 0; i < reviewFormLinks.size(); i++) {

						ReviewFormLinkVO reviewFormLinkVO = reviewFormLinks.get(i);

						AppraisalShareInfoVO appraisalShareInfoVO = null;
						// getting the actual status of the appraisal
						ReviewStatus reviewStatus = new ReviewStatus();
						reviewStatus.setReviewStatusCode(reviewFormLinkVO
								.getReviewFormStatus());

						// getting the phase of the review form based on the status to
						// know
						// the review form in whose court
						// ReviewPhase reviewPhase =
						// reviewFormService.getReviewPhase(reviewStatus);
						// String reviewPhaseDescription = reviewPhase.getDescription();
						/*appraisalShareInfoVO = reviewHeaderService
								.getAppraisalShareInfo(employeeId,
										reviewFormLinkVO.getReviewHeaderId());*/
						appraisalShareInfoVO = reviewHeaderService
								.getAppraisalShareInfo(employeeId,
										reviewFormLinkVO);
						Integer sharedWithEmployeeId = 0;
						if (appraisalShareInfoVO.getSharedWithEmployeeId() != null) {
							sharedWithEmployeeId = appraisalShareInfoVO
									.getSharedWithEmployeeId();
						}

						// checking the logged in employee is shared with the review
						// forms

						if (employeeId == sharedWithEmployeeId) {
							request.setAttribute("loginRole", "SHARED_APPRAISER");

							reviewFormLinkVO.setReviewFormStatus(ReviewStatus
									.getAppraiserReviewStatus(reviewStatus));
							String actualReviewStatus = reviewStatus
									.getReviewStatusCode();

							// If the logged in person is appraiser and if the review
							// form is in appraiser court, setting is ready to true.
							// Means he can open the review form
							// changes suggested in UAT phase
							if (actualReviewStatus.equals("GOALS_SUBMITTED")
									|| actualReviewStatus.equals("GOALS_ACCEPTED")
									|| actualReviewStatus.equals("NEED_TO_EDIT_GOALS")
									|| actualReviewStatus.equals("GOALS_FINALIZED")
									|| actualReviewStatus
											.equals("APPRAISAL_IN_PROGRESS")
									|| actualReviewStatus
											.equals("NEED_TO_EDIT_APPRAISAL")
									|| actualReviewStatus.equals("APPRAISAL_SUBMITTED")
									|| actualReviewStatus
											.equals("ASSESSMENT_IN_PROGRESS")
									|| actualReviewStatus.equals("COMPLETED")
									|| actualReviewStatus.equals("READY_FOR_MEETING")) {
								reviewFormLinkVO.setIsReady(true);
							} else {
								reviewFormLinkVO.setIsReady(false);
							}
						}
						reviewFormLinkVO.setAppraisalShareInfoVO(appraisalShareInfoVO);

					}
				} 
				// user is viewing his team appraisals
				else if (selfOrPeers.equals("peers")) {

					// setting the shareAppraisalForm to the model to allow the user to
					// share the appraisal(s) with another employee
					ShareAppraisalForm shareAppraisalForm = new ShareAppraisalForm();
					model.addAttribute("shareAppraisalForm", shareAppraisalForm);

					ReviewCycle reviewCycle;
					// if review cycle id is 0, getting active review cycle id else
					// getting the review cycle object of the passed review cycle id
					if (reviewCycleId != 0) {
						reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
					} else {
						reviewCycle = reviewCycleService.getActiveReviewCycle();
					}
					// Swaroops add current review cycle.. one year request change
					request.setAttribute("reviewCycle",
							reviewCycleService.getActiveReviewCycle());
					//
					if (SecurityDetailsAccessor.getAuthorities().contains(
							Role.ROLE_MANAGER)
							|| SecurityDetailsAccessor.getAuthorities().contains(
									Role.ROLE_SUPERUSER)) {
						 reviewFormLinks = reviewHeaderService
								.getReviewFormIndexOfPeersByReviewCycleId(employeeId,
										reviewCycle.getReviewCycleId(),startIndex,pageDisplayLength,searchValue,colIndex,sortDirection,reviewFormLinkVOWrapper);
						

					} else
					//	return "error/unAuthorized";
						return new ArrayList<ReviewFormLinkVO>();
					// getting the review form links for which he is the main appraiser
					// in the specified review cycle id

					for (int i = 0; i < reviewFormLinks.size(); i++) {

						ReviewFormLinkVO reviewFormLinkVO = reviewFormLinks.get(i);

						AppraisalShareInfoVO appraisalShareInfoVO = null;
						// getting the actual status of the appraisal
						ReviewStatus reviewStatus = new ReviewStatus();
						reviewStatus.setReviewStatusCode(reviewFormLinkVO
								.getReviewFormStatus());
						String actualReviewStatus = reviewStatus.getReviewStatusCode();

						// getting the phase of the review form based on the status to
						// know
						// the review form in whose court
						// ReviewPhase reviewPhase =
						// reviewFormService.getReviewPhase(reviewStatus);
						// String reviewPhaseDescription = reviewPhase.getDescription();
						/*appraisalShareInfoVO = reviewHeaderService
								.getAppraisalShareInfo(employeeId,
										reviewFormLinkVO.getReviewHeaderId());*/
						appraisalShareInfoVO = reviewHeaderService
								.getAppraisalShareInfo(employeeId,
										reviewFormLinkVO);

						// checking the logged in employee is main appraiser for the
						// review forms
						//if (employeeId == reviewFormLinkVO.getMainAppraiserEmployeeId()) {TODO
							request.setAttribute("loginRole", "APPRAISER");
							reviewFormLinkVO.setReviewFormStatus(ReviewStatus
									.getAppraiserReviewStatus(reviewStatus));

							// If the logged in person is appraiser and if the review
							// form
							// is in appraiser court, setting is ready to true. Means he
							// can
							// open the review form

							// changes suggested in UAT phase
							if (actualReviewStatus.equals("GOALS_SUBMITTED")
									|| actualReviewStatus.equals("GOALS_ACCEPTED")
									|| actualReviewStatus.equals("NEED_TO_EDIT_GOALS")
									|| actualReviewStatus.equals("GOALS_FINALIZED")
									|| actualReviewStatus
											.equals("APPRAISAL_IN_PROGRESS")
									|| actualReviewStatus
											.equals("NEED_TO_EDIT_APPRAISAL")
									|| actualReviewStatus.equals("APPRAISAL_SUBMITTED")
									|| actualReviewStatus
											.equals("ASSESSMENT_IN_PROGRESS")
									|| actualReviewStatus.equals("COMPLETED")
									|| actualReviewStatus.equals("READY_FOR_MEETING")) {
								reviewFormLinkVO.setIsReady(true);
							} else {
								reviewFormLinkVO.setIsReady(false);
							}
						//}
						reviewFormLinkVO.setAppraisalShareInfoVO(appraisalShareInfoVO);
					}
				} else if (selfOrPeers.equals("all")) {
					ShareAppraisalForm shareAppraisalForm = new ShareAppraisalForm();
					model.addAttribute("shareAppraisalForm", shareAppraisalForm);
					ReviewCycle reviewCycle;
					if (reviewCycleId != 0) {
						reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
					} else {
						reviewCycle = reviewCycleService.getActiveReviewCycle();
					}
					// Swaroops add current review cycle.. one year request change
					request.setAttribute("reviewCycle",
							reviewCycleService.getActiveReviewCycle());
					//
					if (SecurityDetailsAccessor.getAuthorities().contains(
							Role.ROLE_SUPERUSER)){
						 reviewFormLinks =reviewHeaderService.getReviewFormIndexOfAll(reviewCycle.getReviewCycleId(), employeeId,startIndex,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper);
					} else
						return new ArrayList<ReviewFormLinkVO>();

					// looping through review form links
					// looping through review form links
					for (int i = 0; i < reviewFormLinks.size(); i++) {

						ReviewFormLinkVO reviewFormLinkVO = reviewFormLinks.get(i);
						AppraisalShareInfoVO appraisalShareInfoVO = null;
						// getting the actual status of the appraisal
						ReviewStatus reviewStatus = new ReviewStatus();
						reviewStatus.setReviewStatusCode(reviewFormLinkVO
								.getReviewFormStatus());

						// getting the phase of the review form based on the status to know
						// the review form in whose court
						//ReviewPhase reviewPhase = reviewFormService.getReviewPhase(reviewStatus);
						//String reviewPhaseDescription = reviewPhase.getDescription();
						/*appraisalShareInfoVO=reviewHeaderService.getAppraisalShareInfo(employeeId, reviewFormLinkVO.getReviewHeaderId());*/
						appraisalShareInfoVO=reviewHeaderService.getAppraisalShareInfo(employeeId, reviewFormLinkVO);
								
							//get the share information of appraisal
							
						 if (SecurityDetailsAccessor.getAuthorities().contains(
								Role.ROLE_SUPERUSER)){							
							
							 reviewFormLinkVO.setReviewFormStatus( ReviewStatus
									.getSuperUserReviewStatus(reviewStatus));
							String actualReviewStatus=reviewStatus.getReviewStatusCode();
							//changes suggested in UAT phase
							if (actualReviewStatus.equals("GOALS_SUBMITTED") || actualReviewStatus.equals("GOALS_ACCEPTED")
									|| actualReviewStatus.equals("NEED_TO_EDIT_GOALS") || actualReviewStatus.equals("GOALS_FINALIZED")
									|| actualReviewStatus.equals("APPRAISAL_IN_PROGRESS") || actualReviewStatus.equals("NEED_TO_EDIT_APPRAISAL")
									|| actualReviewStatus.equals("APPRAISAL_SUBMITTED") || actualReviewStatus.equals("ASSESSMENT_IN_PROGRESS")
									|| actualReviewStatus.equals("COMPLETED") || actualReviewStatus.equals("READY_FOR_MEETING")) {
								reviewFormLinkVO.setIsReady(true);
							}else{
								reviewFormLinkVO.setIsReady(false);
							}
							request.setAttribute("loginRole","SUPERUSER");
							reviewFormLinkVO.setAppraisalShareInfoVO(appraisalShareInfoVO);
						 }
					}
				}
				
				// setting the review form links
				request.setAttribute("reviewFormLinks", reviewFormLinks);
				request.setAttribute("selfOrPeers", selfOrPeers);
		
		return reviewFormLinks;
	}

	private void setTableData(HttpServletRequest request,List<ReviewFormLinkVO> reviewFormLinks, String contextPath,String selfOrPeers) {
		for (ReviewFormLinkVO reviewFormLinkVO : reviewFormLinks) {
			
			if(selfOrPeers.equals("peers") || selfOrPeers.equals("all")){
			reviewFormLinkVO.setReviewHeaderCheckBox("<input type=\"checkbox\" name=\"selectedAppraisals\" class=\"appraisal\" value=\""+reviewFormLinkVO.getReviewHeaderId()+"\" />");
			reviewFormLinkVO.setEmail("<span class=\"emailClass\">"+reviewFormLinkVO.getEmail()+"</span>");
			reviewFormLinkVO.setGradeAndDesgField(reviewFormLinkVO.getGrade()+"-"+reviewFormLinkVO.getDesignation());
			}
			
			if(selfOrPeers.equals("self")){
				if(reviewFormLinkVO.getIsReady()==false){
					reviewFormLinkVO.setReviewCycleNameField("<a onClick=\"openReview('"+reviewFormLinkVO.getReviewHeaderId() +"', '"+reviewFormLinkVO.getIsReady()+"', '"+request.getAttribute("loginRole")+"')\" href=\"javascript:void(0)\"> "+reviewFormLinkVO.getReviewCycleName()+"</a>");
				}else{
					reviewFormLinkVO.setReviewCycleNameField("<a href=\""+contextPath+"/appraisal/summary.html?reviewHeaderId="+reviewFormLinkVO.getReviewHeaderId() +"\" target='_blank'>"+reviewFormLinkVO.getReviewCycleName()+"</a>");
				}
			}else {
				if(reviewFormLinkVO.getIsReady()==false){
					reviewFormLinkVO.setReviewCycleNameField("<a onClick=\"openReview('"+reviewFormLinkVO.getReviewHeaderId() +"', '"+reviewFormLinkVO.getIsReady()+"', '"+request.getAttribute("loginRole")+"')\" href=\"javascript:void(0)\"> "+reviewFormLinkVO.getEmployeeDisplayName()+"</a>");
				}else{
					reviewFormLinkVO.setReviewCycleNameField("<a href=\""+contextPath+"/appraisal/summary.html?reviewHeaderId="+reviewFormLinkVO.getReviewHeaderId() +"\" target='_blank'>"+reviewFormLinkVO.getEmployeeDisplayName()+"</a>");
				}
			}
			
			
			if(selfOrPeers.equals("self")){
				if(reviewFormLinkVO.getAppraisalShareInfoVO().getSharedByEmployee()!=null && reviewFormLinkVO.getAppraisalShareInfoVO().getSharedByEmployee()!=""){
					reviewFormLinkVO.setMainAppraiserDisplayNameField(reviewFormLinkVO.getAppraisalShareInfoVO().getSharedByEmployee()+" shared appraisal with "+reviewFormLinkVO.getAppraisalShareInfoVO().getSharedWithEmployee());
				}else{
					reviewFormLinkVO.setMainAppraiserDisplayNameField("Not shared with anyone");
				}
			}else if(selfOrPeers.equals("shared")){
				if(reviewFormLinkVO.getAppraisalShareInfoVO().getIsSharedWithYou()!=null && reviewFormLinkVO.getAppraisalShareInfoVO().getIsSharedWithYou()==true){
					reviewFormLinkVO.setMainAppraiserDisplayNameField(reviewFormLinkVO.getAppraisalShareInfoVO().getSharedByEmployee()+" shared with you");
				}
			}else if(selfOrPeers.equals("peers") || selfOrPeers.equals("all")){
					if(reviewFormLinkVO.getAppraisalShareInfoVO().getIsSharedByYou()!=null && reviewFormLinkVO.getAppraisalShareInfoVO().getIsSharedByYou()==true){
						reviewFormLinkVO.setMainAppraiserDisplayNameField("You have shared with "+reviewFormLinkVO.getAppraisalShareInfoVO().getSharedWithEmployee());
					}else if(reviewFormLinkVO.getAppraisalShareInfoVO().getSharedWithEmployee()!=null && reviewFormLinkVO.getAppraisalShareInfoVO().getSharedWithEmployee()!=""){
						reviewFormLinkVO.setMainAppraiserDisplayNameField(reviewFormLinkVO.getAppraisalShareInfoVO().getSharedByEmployee()+" shared with "+reviewFormLinkVO.getAppraisalShareInfoVO().getSharedWithEmployee());
					}else{
						reviewFormLinkVO.setMainAppraiserDisplayNameField("Not shared with anyone");
					}
			}
		}
	}
	
	private String getSortColName(String colIndex, String selfOrPeers,String sortOrder){
		String DbColName="";
		if(colIndex.equals("0") || colIndex.equals("1")){
			if(selfOrPeers.equals("self")){
				DbColName= "reviewCycleName";
			}else if(selfOrPeers.equals("shared")){
				if(colIndex.equals("0"))
					DbColName="emp.displayName";
				else
					DbColName="stat.ReviewStatusDescription";
			}else{
				DbColName= "emp.displayName";
			}
		}else if(colIndex.equals("2")||colIndex.equals("5")){
			DbColName= "mgr.DisplayName";
		}else if(colIndex.equals("6")){
			DbColName= "emp.DefaultProject";
		}else if(colIndex.equals("4")){
			DbColName="stat.ReviewStatusDescription";
		}else if(colIndex.equals("3")){
			DbColName="grade.Grade "+sortOrder+",desg.ShortName";
		}
		return DbColName;
	}

	@RequestMapping(value = "/sendCustomizedEmail.html", method = RequestMethod.POST)
	public @ResponseBody String sendCustomizedEmail(
			Model model,
			@RequestParam(value = "emailToEmployee") String emailToEmployee,
			// @ModelAttribute(value = "shareAppraisalForm") ShareAppraisalForm
			// shareAppraisalForm,
			@RequestParam(value = "reviewHeaderIds") String reviewHeaderIds,
			@RequestParam(value = "emailSubject") String emailSubject,
			@RequestParam(value = "emailText") String emailText) {
		String emailAddressesArray[] = null;
		if (emailToEmployee != null && !emailToEmployee.equals("")) {
			if (Pattern.matches(regex, emailToEmployee)) {
				emailAddressesArray = emailToEmployee.split(",");
			} else {
				return "failed";
			}
		} else {
			String commaSeparatedEmployeeIds = reviewHeaderIds;
			List<String> employeeEmailList = employeeService
					.getEmployeEmaildsByCommaSeparatedEmpIds(commaSeparatedEmployeeIds);
			emailAddressesArray = employeeEmailList.toArray(new String[0]);
		}
		// single method
		if (mailingService.sendEmailNotification(
				SecurityDetailsAccessor.getFullName(),
				SecurityDetailsAccessor.getEmailAddress(), null,
				emailAddressesArray, emailSubject, emailText)) {
			log.info("Customized notification sent successfully...");
			return "success";
		} else {
			return "failed";
		}
	}

	@RequestMapping(value = "/generateReviewCycleExcelSheet.html")
	@RolesAllowed("ROLE_SUPERUSER")
	public void generateExcelSheetForReviewCycle(HttpServletResponse response,
			@RequestParam(value = "reviewCycleId") int reviewCycleId) throws SQLException {
		log.info("generateExcelSheetForReviewCycle...");
		ReviewCycle reviewCycle;
		if (reviewCycleId != 0) {
			reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
		} else {
			reviewCycle = reviewCycleService.getActiveReviewCycle();
		}

		List<ReviewFormLinkVO> reviewFormLinks =reviewHeaderService
				.getReviewFormLinks(reviewCycle.getReviewCycleId());
		

		HSSFWorkbook workbook = new HSSFWorkbook();
		Map<String, List<ReviewFormLinkVO>> detailsMap = new HashMap<String, List<ReviewFormLinkVO>>();
		
		//Fill Data into Excel Sheet
		reviewSummaryService.fillDetails(reviewFormLinks, detailsMap, "managers");
		
		Set<String> keyset = detailsMap.keySet();
		reviewSummaryService.generateExcelSheet(keyset, workbook, detailsMap, excelSheetsPerReviewCycle, "managers");

		
		String reviewCycleName = reviewCycle.getReviewCycleName();
		reviewCycleName = reviewCycleName.replaceAll(" ", "_");
		
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition",
					"inline;filename="+reviewCycleName+"_AppraisalsByManagers.xls");
			workbook.write(response.getOutputStream());
			response.getOutputStream().close();
			log.info("excel sheet downloaded successfully!!!");
		} catch (Exception e) {
			log.error(
					"Exception thrown on ReviewCycleController.generatedExcelSheetForReviewCycle()",
					e);
		}

	}
	
	@RequestMapping(value = "/generateReviewCycleExcelSheetByProjects.html")
	@RolesAllowed("ROLE_SUPERUSER")
	public void generateExcelSheetByProject(HttpServletResponse response,
			@RequestParam(value = "reviewCycleId") int reviewCycleId
			) throws SQLException {
		log.info("generateExcelSheetByProject...");
		ReviewCycle reviewCycle;
		if (reviewCycleId != 0) {
			reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
		} else {
			reviewCycle = reviewCycleService.getActiveReviewCycle();
		}

		List<ReviewFormLinkVO> reviewFormLinks =  reviewHeaderService
				.getReviewFormLinks(reviewCycle.getReviewCycleId());
		
		
		

		HSSFWorkbook workbook = new HSSFWorkbook();

		Map<String, List<ReviewFormLinkVO>> detailsMap = new HashMap<String, List<ReviewFormLinkVO>>();
		
		//Fill Data into Excel Sheet
		reviewSummaryService.fillDetails(reviewFormLinks, detailsMap, "projects");
		
		Set<String> keyset = detailsMap.keySet();
		reviewSummaryService.generateExcelSheet(keyset, workbook, detailsMap, excelSheetsPerProject, "projects");
		
		String reviewCycleName = reviewCycle.getReviewCycleName();
		reviewCycleName = reviewCycleName.replaceAll(" ", "_");

		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition",
					"inline;filename="+reviewCycleName+"_AppraisalsByProjects.xls");
			workbook.write(response.getOutputStream());
			response.getOutputStream().close();
			log.info("excel sheet downloaded successfully!!!");
		} catch (Exception e) {
			log.error(
					"Exception thrown on ReviewCycleController.generatedExcelSheetForReviewCycle()",
					e);
		}
	}
	
	@RequestMapping(value = "/generateReviewCycleSingleExcelSheet.html")
	@RolesAllowed("ROLE_SUPERUSER")
	public void generateSingleExcelSheetForReviewCycle(HttpServletResponse response,
			@RequestParam(value = "reviewCycleId") int reviewCycleId) throws SQLException {
		log.info("generateSingleExcelSheetForReviewCycle...");
		ReviewCycle reviewCycle;
		if (reviewCycleId != 0) {
			reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
		} else {
			reviewCycle = reviewCycleService.getActiveReviewCycle();
		}

		List<ReviewFormLinkVO> reviewFormLinks =reviewHeaderService
				.getReviewFormLinks(reviewCycle.getReviewCycleId());
		

		HSSFWorkbook workbook = new HSSFWorkbook();
		
		reviewSummaryService.generateSingleExcelSheet(workbook, reviewFormLinks, singleExcelSheetsPerReviewCycle);

		
		String reviewCycleName = reviewCycle.getReviewCycleName();
		reviewCycleName = reviewCycleName.replaceAll(" ", "_");
		
		try {
			response.setContentType("application/vnd.ms-excel");
			response.addHeader("Content-Disposition",
					"inline;filename="+reviewCycleName+"_GGKTechAllAppraisals.xls");
			workbook.write(response.getOutputStream());
			response.getOutputStream().close();
			log.info("Single excel sheet downloaded successfully!!!");
		} catch (Exception e) {
			log.error(
					"Exception thrown on ReviewCycleController.generateSingleExcelSheetForReviewCycle()",
					e);
		}

	}
	
	@RequestMapping(value = "/downloadPDFs.html")
	@RolesAllowed("ROLE_SUPERUSER")
	public void generatePDFs(@RequestParam(value = "reviewHeaderIds") List<Integer> reviewHeaderIds, HttpServletResponse response) throws InterruptedException, IOException{
		
		
		log.info("Downloading selected appraisals...................");
		deleteFiles(new File(superUserPdfLocation));
		String zipName = null;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		for(int reviewHeaderId : reviewHeaderIds){
			
			ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(reviewHeaderId);
			if(reviewHeader == null)
				continue;
			if(zipName == null)
				zipName = reviewHeader.getReviewCycle().getReviewCycleName();
			Runnable worker = new Thread(new AppraisalFormPDFTask(reviewHeaderService,
					reviewObjectiveService, reviewCompetencyService,
					reviewDevelopmentPlanService,reviewSummaryService,
					ReviewFormRole.SUPERUSER, reviewHeaderId,
					jrxmlLocation, superUserPdfLocation, mailingService, null,
					null, reviewAlias, null, zipName,"USER_REQUESTED",superUserPdfLocation,superUserJrxmlLocation));
			executor.execute(worker);
		}
		 executor.shutdown();
		while (!executor.isTerminated()) {
        }
		
		log.info("Done with generating PDFs for superuser...............");
		 File directory = new File(superUserPdfLocation);
         String[] files = directory.list();

         //
         // Checks to see if the directory contains some files.
         //
         if (files != null && files.length > 0) {

             //
             // Call the zipFiles method for creating a zip stream.
             //
             byte[] zip = zipFiles(directory, files);

             //
             // Sends the response back to the user / browser. The
             // content for zip file type is "application/zip". We
             // also set the content disposition as attachment for
             // the browser to show a dialog that will let user 
             // choose what action will he do to the sent content.
             //
             ServletOutputStream sos = response.getOutputStream();
             zipName = zipName+".zip";
             response.setContentType("application/zip");
             response.setHeader("Content-Disposition", "attachment; filename=\""+zipName+"\"");

             sos.write(zip);
             sos.flush();
         }
		
	
		
		
	}
	
	private byte[] zipFiles(File directory, String[] files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        byte bytes[] = new byte[2048];
 
        for (String fileName : files) {
            FileInputStream fis = new FileInputStream(directory.getPath() + 
            		System.getProperty("file.separator") + fileName);
            BufferedInputStream bis = new BufferedInputStream(fis);
 
            zos.putNextEntry(new ZipEntry(fileName));
 
            int bytesRead;
            while ((bytesRead = bis.read(bytes)) != -1) {
                zos.write(bytes, 0, bytesRead);
            }
            zos.closeEntry();
            bis.close();
            fis.close();
        }
        zos.flush();
        baos.flush();
        zos.close();
        baos.close();
 
        return baos.toByteArray();
    }
	
	private void deleteFiles(File file) {

		String files[] = file.list();
		if(files == null) return;

		for (String temp : files) {
			// construct the file structure
			File fileDelete = new File(file, temp);

			// recursive delete
			fileDelete.delete();
		}

		// check the directory again, if empty then delete it
		
		log.info("Files are deleted : "
					+ file.getAbsolutePath());
		
	}
	
}
