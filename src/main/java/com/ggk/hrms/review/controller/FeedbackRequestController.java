package com.ggk.hrms.review.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.beans.domain.FeedbackQuestionAnswer;
import com.ggk.hrms.review.beans.domain.FeedbackRequest;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.FeedbackRequestService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.form.FeedbackRequestForm;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.EmployeeVO;
import com.ggk.hrms.review.ui.vo.FeedbackQuestionAnswerVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ReviewCycleVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping("/feedback")
@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER", "ROLE_USER" })
public class FeedbackRequestController {

	public static final String SHOW_FEEDBACKTEMPLATE = "feedback/feedbackTemplate";

	@Resource
	private FeedbackRequestService feedBackRequestService;

	@Resource
	private EmployeeService employeeService;
	
	
	@Resource
	private ReviewCycleService reviewCycleService;

	@Resource
	private ReviewFormService reviewFormService;

	@Resource
	private ReviewHeaderService reviewHeaderService;

	private final Logger log = Logger
			.getLogger(FeedbackRequestController.class);

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	// to get the values in review cycle drop down
	@ModelAttribute("reviewCycleDropDownItems")
	List<DropDownVO> getRatingDropDown(HttpServletRequest request) {
		List<DropDownVO> reviewCycleDropDownItems = (List<DropDownVO>)request.getSession().getAttribute("reviewCycleDropDownItems");
        if (reviewCycleDropDownItems == null || reviewCycleDropDownItems.size() == 0){
       	 
        	reviewCycleDropDownItems = reviewCycleService.getReviewCycleDropDown();
       	request.getSession().setAttribute("reviewCycleDropDownItems", reviewCycleDropDownItems);        	 
        }		

		return reviewCycleDropDownItems;
	}

	// request to open feedback request
	@RequestMapping(value = "/show.html", method = RequestMethod.POST)
	public String show(
			@RequestParam(required = true, value = "requestId") int requestId,
			@RequestParam(required = false, value = "type") String type,
			ModelMap model) {

		FeedbackRequest feedbackRequest = feedBackRequestService
				.getFeedbackRequestById(requestId);

		if (type != null) {
			if (type.equals("request")
					&& (!feedbackRequest.getType().equals("request"))) {
				throw new RuntimeException(
						"You can not view or modify the content");
			} else if ((type.equals("self") || type.equals("selfReceived"))
					&& (!feedbackRequest.getType().equals("self"))) {
				throw new RuntimeException(
						"You can not view or modify the content");
			}
		}
		if (!isAuthorized(feedbackRequest, SecurityDetailsAccessor.getEmpId())) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}

		FeedbackRequestForm feedbackRequestForm = createFeedbackRequestForm(
				feedbackRequest, type);
		feedbackRequestForm.setAction("show");
		feedbackRequestForm.setType(type);
		model.addAttribute("feedbackRequestForm", feedbackRequestForm);
		return SHOW_FEEDBACKTEMPLATE;
	}

	private boolean isAuthorized(FeedbackRequest feedbackRequest,int loginEmployeeId) {
		List<String> authorities = SecurityDetailsAccessor.getAuthorities();
		if(authorities.contains("ROLE_SUPERUSER")||authorities.contains("ROLE_MANAGER")){
			return true;
		}
		else if (feedbackRequest.getRequestedByEmployee().getEmpId() == loginEmployeeId
				|| feedbackRequest.getRequestedToEmployee().getEmpId() == loginEmployeeId) {
			return true;
		} else {
			return false;
		}
	}

	// creating new feedback request
	// in feedback tab when clicked on give feedback link.
	@RequestMapping(value = "/newFeedbackrequest.html", method = RequestMethod.POST)
	public String request(
			@RequestParam(required = false, value = "reviewHeaderId") Integer reviewHeaderId,
			@RequestParam(required = false, value = "type") String type,
			@RequestParam(required = false, value = "location") String location,
			@RequestParam(required = false, value = "selectedReviewCycleId") Integer selectedReviewCycleId,
			ModelMap model) {
		FeedbackRequestForm feedbackRequestForm = generateFeedbackRequest(
				reviewHeaderId, type);
		if (reviewHeaderId != 0) {
			feedbackRequestForm.setReviewHeaderId(reviewHeaderId);
		}
		if (type.equals("request") && reviewHeaderId != 0) {
			ReviewHeader reviewHeader = reviewHeaderService
					.getReviewHeaderById(reviewHeaderId);
			// getting the review form role. i.e, check the user employee id is
			// matching with the appraise employee id or main appraiser employee
			// id
			// or super user employee id
			ReviewFormRole reviewFormRole = reviewFormService
					.getReviewFormRole(reviewHeader);
			// if the role is none of those 4, exception will be thrown.
			if (reviewFormRole == null) {
				throw new RuntimeException("Invalid ReviewFormRole");
			}
		}
		feedbackRequestForm.setAction("request");
		feedbackRequestForm.setType(type);

		String role = null;
		List<String> authorities = SecurityDetailsAccessor.getAuthorities();
		if (authorities.contains("ROLE_MANAGER")) {
			role = "manager";
		} else if (authorities.contains("ROLE_USER")) {
			role = "user";
		} else if (authorities.contains("ROLE_SUPERUSER")) {
			role = "superuser";
		}
		ReviewCycle selectedReviewCycle = null;
		if (selectedReviewCycleId != null) {
			selectedReviewCycle = reviewCycleService
					.getReviewCycle(selectedReviewCycleId);
			feedbackRequestForm.setReviewCycleId(selectedReviewCycleId);
			feedbackRequestForm.setReviewCycleName(selectedReviewCycle
					.getReviewCycleName());
		}

		model.addAttribute("location", location);
		model.addAttribute("role", role);
		model.addAttribute("feedbackRequestForm", feedbackRequestForm);
		return SHOW_FEEDBACKTEMPLATE;
	}

	// setting model attribute
	@ModelAttribute(value = "reviewFormInfoVO")
	ReviewFormInfoVO getReviewFormInfo(
			@RequestParam(value = "reviewHeaderId", required = false) Integer reviewHeaderId) {
		ReviewFormInfoVO reviewFormInfoVO = null;
		if (reviewHeaderId != null && reviewHeaderId != 0) {
			return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(),null);

		} else {
			return reviewFormInfoVO;
		}
	}

	// sending feedback request and self feedback
	@RequestMapping(value = "saveRequest.html", method = RequestMethod.POST)
	public @ResponseBody
	String saveRequest(
			@ModelAttribute("feedbackRequestForm") FeedbackRequestForm feedbackRequestForm,
			@RequestParam(required = false, value = "type") String type,
			ModelMap model) {
		try {
			feedBackRequestService.buildFeedbackRequest(feedbackRequestForm,
					type);
			log.info("Feedback request sent...");
			return "success";
		} catch (Exception e) {
			return "error";
		}
	}

	// Getting feedback requests home page, This will be called when we clicked
	// on Feedback, Appraisal Of Others, Create Feedback Requests tab and
	// Feedback tab of appraisal form tabs
	@RequestMapping(value = "/feedbackRequests.html", method = RequestMethod.POST)
	public String requests(
			ModelMap model,
			@RequestParam(required = false, value = "reviewHeaderId") Integer reviewHeaderId,
			@RequestParam(required = false, value = "user") String user,
			@RequestParam(required = false, value = "type") String type,
			@RequestParam(required = false, value = "location") String location,@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {
		model.addAttribute("reviewHeaderId", reviewHeaderId);
		model.addAttribute("user", user);
		model.addAttribute("type", type);
		model.addAttribute("location", location);
		if(reviewHeaderId != null && reviewHeaderId != 0 ){ // added for testing  TODO
		getFeedbackRequestIndex(model, user, 0, location, reviewHeaderId, type,reviewFormInfoVO);
		}else{
		
			model.addAttribute("selectReviewCycle", true);
		}
		return "feedback/feedbackRequestHome";
	}

	// getting the feedback requests index, from feedback request home page, a
	// request will be generated to load the list of feedbacks
	@RequestMapping(value = "/getFeedbackRequestIndex.html", method = RequestMethod.POST)
	public String getFeedbackRequestIndex(
			ModelMap model,
			@RequestParam(required = false, value = "user") String user,
			@RequestParam(required = false, value = "reviewCycleId") Integer reviewCycleId,
			@RequestParam(required = false, value = "location") String location,
			@RequestParam(required = false, value = "reviewHeaderId") Integer reviewHeaderId,
			@RequestParam(required = false, value = "type") String type,@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO) {

		int employeeId = SecurityDetailsAccessor.getEmpId();
		ReviewCycle reviewCycle;
		/*
		 * //if review cycle is 0 then the feedback requests of current review
		 * cycle will be loaded else required review cylce's will be loaded
		 * if(reviewCycleId!=0){ reviewCycle=
		 * reviewCycleService.getReviewCycle(reviewCycleId.intValue()); } else {
		 * reviewCycle= reviewCycleService.getActiveReviewCycle(); }
		 * model.addAttribute("reviewCycleVO", convertDomainToVo(reviewCycle));
		 */
		List<FeedbackRequestVO> feedbackRequests = null;
		
		// the user is manager and he is viewing the feedback requests while
		// reviewing the appraisal
		 if (reviewHeaderId != 0) {
			 
			 //ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(reviewHeaderId);
			
			if (SecurityDetailsAccessor.getAuthorities().contains(
					"ROLE_SUPERUSER")
					|| reviewFormInfoVO.getReviewFormRole()== ReviewFormRole.APPRAISER) {
				feedbackRequests = feedBackRequestService
						.getFeedbackRequests(reviewHeaderId);
			}else {
				feedbackRequests = feedBackRequestService.getFeedbackRequests(
						reviewHeaderId, employeeId);

			}
			// If Manager's manager is viewing the appraisal.
			 /*if (SecurityDetailsAccessor.getAuthorities().contains(
						"ROLE_MANAGER")) {

				Iterator<FeedbackRequestVO> feedbackRequestsIterator = feedbackRequests
						.iterator();
				while (feedbackRequestsIterator.hasNext()) {
					FeedbackRequestVO feedbackRequest = (FeedbackRequestVO) feedbackRequestsIterator
							.next();
					ReviewHeader requestedByEmployeeHeader = reviewHeaderService
							.getReviewHeader(feedbackRequest
									.getRequestedByEmployeeId(), reviewHeader
									.getReviewCycle().getReviewCycleId());
					ReviewHeader requestedToEmployeeHeader = reviewHeaderService
							.getReviewHeader(feedbackRequest
									.getRequestedToEmployeeId(), reviewHeader
									.getReviewCycle().getReviewCycleId());
					if (reviewFormService
							.isLoggedInUserHasAccesstoIt(requestedByEmployeeHeader) == false
							|| reviewFormService
									.isLoggedInUserHasAccesstoIt(requestedToEmployeeHeader) == false) {

						feedbackRequestsIterator.remove();
					}

				}
			 }*/
		}
		// if the user is sender and he is viewing the feedbacks out of the
		// review form
		else if (user.equals("sender") && location.equals("out")) {

			// if review cycle is 0 then the feedback requests of current review
			// cycle will be loaded else required review cylce's will be loaded
			if (reviewCycleId != 0) {
				reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId
						.intValue());
			} else {
				reviewCycle = reviewCycleService.getActiveReviewCycle();
			}
			model.addAttribute("reviewCycleVO", convertDomainToVo(reviewCycle));

			// if the type is 'request', then the manager is viewing his sent
			// requests list
			if (type.equals("request")) {
				feedbackRequests = feedBackRequestService
						.getFeedbackRequestsSent(
								SecurityDetailsAccessor.getEmpId(),
								reviewCycle.getReviewCycleId());
			}
			// if the type is 'self', then the employee is viewing his self
			// feedbacks sent
			else if (type.equals("self")) {
				feedbackRequests = feedBackRequestService
						.getSelfFeedbackRequestsSent(
								SecurityDetailsAccessor.getEmpId(),
								reviewCycle.getReviewCycleId());

				List<FeedbackRequestVO> feedbacksReceived = feedBackRequestService
						.getSelfFeedbackRequestsReceived(
								SecurityDetailsAccessor.getEmpId(),
								reviewCycle.getReviewCycleId());
				model.addAttribute("feedbacksReceived", feedbacksReceived);
			}
		} else {

			// if review cycle is 0 then the feedback requests of current review
			// cycle will be loaded else required review cylce's will be loaded
			if (reviewCycleId != 0) {
				reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId
						.intValue());
			} else {
				reviewCycle = reviewCycleService.getActiveReviewCycle();
			}
			model.addAttribute("reviewCycleVO", convertDomainToVo(reviewCycle));

			// user wants to check his incoming feedback requests
			feedbackRequests = feedBackRequestService.getFeedbackRequestsReceived(SecurityDetailsAccessor.getEmpId(),reviewCycle.getReviewCycleId());
		}
		model.addAttribute("reviewHeaderId", reviewHeaderId);
		model.addAttribute("user", user);
		model.addAttribute("type", type);
		model.addAttribute("location", location);
		model.addAttribute("feedbackRequests", feedbackRequests);
		return "feedback/feedbackRequestIndex";
	}

	@RequestMapping(value = "/pendingByRequestId.html", method = RequestMethod.POST)
	public String pendingByRequestId(
			@RequestParam(required = true, value = "requestId") int requestId,
			@RequestParam(required = true, value = "type") String type,
			@RequestParam(required = false, value = "selectedReviewCycleId") Integer selectedReviewCycleId,
			ModelMap model) {
		FeedbackRequest feedbackRequest = feedBackRequestService
				.getFeedbackRequestById(requestId);
		if (!type.equals(feedbackRequest.getType())) {
			throw new RuntimeException("You can not view or modify the content");
		}
		int loginEmployeeId = SecurityDetailsAccessor.getEmpId();
		if (loginEmployeeId == feedbackRequest.getRequestedToEmployee()
				.getEmpId()) {
			FeedbackRequestForm feedbackRequestForm = createFeedbackRequestForm(
					feedbackRequest, "request");
			feedbackRequestForm.setAction("pending");
			feedbackRequestForm.setType(type);
			ReviewCycle selectedReviewCycle = null;
			if (selectedReviewCycleId != null) {
				selectedReviewCycle = reviewCycleService
						.getReviewCycle(selectedReviewCycleId);
				feedbackRequestForm.setReviewCycleId(selectedReviewCycleId);
				feedbackRequestForm.setReviewCycleName(selectedReviewCycle
						.getReviewCycleName());
			}
			model.addAttribute("feedbackRequestForm", feedbackRequestForm);
			return SHOW_FEEDBACKTEMPLATE;
		} else {
			throw new RuntimeException("You can not view or modify the content");
		}
	}

	// submitting the feedback request to manager
	@RequestMapping(value = "/submit.html", method = RequestMethod.POST)
	public @ResponseBody
	String submit(
			@ModelAttribute("feedbackRequestForm") FeedbackRequestForm feedbackRequestForm,
			ModelMap model) {

		if (SecurityDetailsAccessor.getEmpId() == feedBackRequestService
				.getFeedbackRequestById(
						feedbackRequestForm.getFeedbackRequestId())
				.getRequestedToEmployee().getEmpId()) {
			try {
				feedBackRequestService.update(feedbackRequestForm);
				return "success";
			} catch (Exception e) {
				return "error";
			}
		} else {
			return "notAuthorized";
		}
	}

	@RequestMapping(value = "/save.html", method = RequestMethod.POST)
	public @ResponseBody
	String save(
			@ModelAttribute("feedbackRequestForm") FeedbackRequestForm feedbackRequestForm,
			ModelMap model) {

		if (SecurityDetailsAccessor.getEmpId() == feedBackRequestService
				.getFeedbackRequestById(
						feedbackRequestForm.getFeedbackRequestId())
				.getRequestedToEmployee().getEmpId()) {
			try {

				feedBackRequestService.save(feedbackRequestForm);
				return "success";
			} catch (Exception e) {
				return "error";
			}
		} else {
			return "notAuthorized";
		}
	}

	// For auto Complete jquery
	@RequestMapping(value = "/employees.html", method = RequestMethod.GET)
	public @ResponseBody
	List<EmployeeVO> employees(
			ModelMap model,
			@RequestParam("term") String query,
			@RequestParam(required = false, value = "reviewCycleId") Integer reviewCycleId) {

		// log.info("review cycle Id is: "+reviewCycleId);
		List<Employee> employees = employeeService.getEmployees(true, query,
				reviewCycleId);
		List<EmployeeVO> employeeVOs = new ArrayList<EmployeeVO>();
		for (int i = 0; i < employees.size(); i++) {
			employeeVOs.add(convertDomainToVo(employees.get(i)));
		}
		return employeeVOs;
	}

	@RequestMapping(value = "/getEmployeeByDisplayname.html", method = RequestMethod.GET)
	public @ResponseBody
	EmployeeVO getEmployeeByDisplayname(
			ModelMap model,
			@RequestParam("employeeDisplayName") String employeeDisplayName,
			@RequestParam(required = false, value = "reviewCycleId") Integer reviewCycleId) {

		Employee employee = employeeService.getEmployeeByDisplayName(true,
				employeeDisplayName, reviewCycleId);
		if (employee != null) {
			return convertDomainToVo(employee);
		} else {
			return null;
		}
	}

	// for auto complete query while generating feedback requests
	@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER" })
	@RequestMapping(value = "/subOrdinates.html", method = RequestMethod.GET)
	public @ResponseBody
	List<EmployeeVO> subOrdinates(ModelMap model,
			@RequestParam("term") String query) {
		int managerId = SecurityDetailsAccessor.getEmpId();
		List<Employee> employees = employeeService.getSubOrdinates(true, query,
				managerId);
		List<EmployeeVO> employeeVOs = new ArrayList<EmployeeVO>();
		for (int i = 0; i < employees.size(); i++) {
			employeeVOs.add(convertDomainToVo(employees.get(i)));
		}
		return employeeVOs;

	}

	private FeedbackRequestForm createFeedbackRequestForm(
			FeedbackRequest feedbackRequest, String type) {

		FeedbackRequestForm feedbackRequestForm = new FeedbackRequestForm();
		feedbackRequestForm.setFeedbackRequestId(feedbackRequest
				.getFeedbackRequestId());
		feedbackRequestForm
				.setRequestedDate(feedbackRequest.getRequestedDate());
		feedbackRequestForm
				.setFeedbackOnEmployee(convertDomainToVo(feedbackRequest
						.getFeedbackOnEmployee()));
		// Overall Feedback Comment
		if (feedbackRequest.getOverallFeedbackComment() != null) {

			feedbackRequestForm.setOverallFeedbackComment(feedbackRequest
					.getOverallFeedbackComment().getCommentText());
			feedbackRequestForm.setOverallFeedbackCommentId(feedbackRequest
					.getOverallFeedbackComment().getCommentId());
		}

		if (type.equals("request")) {
			feedbackRequestForm.setReviewHeaderId(feedbackRequest
					.getReviewHeader().getReviewHeaderId());

			feedbackRequestForm.setRequestDueDate(feedbackRequest
					.getRequestDueDate());
			// Employee Details

			feedbackRequestForm
					.setRequestedByEmployee(convertDomainToVo(feedbackRequest
							.getRequestedByEmployee()));
			feedbackRequestForm
					.setRequestedToEmployee(convertDomainToVo(feedbackRequest
							.getRequestedToEmployee()));
			// Question & Answers

			List<FeedbackQuestionAnswer> feedbackQuestionAnswers = feedbackRequest
					.getFeedbackQuestionAnswers();

			if (!feedbackQuestionAnswers.isEmpty()) {

				List<FeedbackQuestionAnswerVO> feedbackQuestionAnswerVOs = new ArrayList<FeedbackQuestionAnswerVO>();

				for (FeedbackQuestionAnswer feedbackQuestionAnswer : feedbackQuestionAnswers) {

					FeedbackQuestionAnswerVO feedbackQuestionAnswerVO = new FeedbackQuestionAnswerVO();

					feedbackQuestionAnswerVO
							.setFeedbackQuestionId(feedbackQuestionAnswer
									.getFeedbackQuestion()
									.getFeedbackQuestionId());

					feedbackQuestionAnswerVO
							.setFeedbackQuestion(feedbackQuestionAnswer
									.getFeedbackQuestion().getQuestion());

					if (feedbackQuestionAnswer.getFeedbackAnswerComment() != null) {

						feedbackQuestionAnswerVO
								.setFeedbackAnswerId(feedbackQuestionAnswer
										.getFeedbackAnswerComment()
										.getCommentId());
						feedbackQuestionAnswerVO
								.setFeedbackAnswer(feedbackQuestionAnswer
										.getFeedbackAnswerComment()
										.getCommentText());
					}
					feedbackQuestionAnswerVOs.add(feedbackQuestionAnswerVO);
				}
				feedbackRequestForm
						.setFeedbackQuestionAnswerVOs(feedbackQuestionAnswerVOs);
			}
			ReviewCycle selectedReviewCycle = feedbackRequest.getReviewHeader()
					.getReviewCycle();
			feedbackRequestForm.setReviewCycleId(selectedReviewCycle
					.getReviewCycleId());
			feedbackRequestForm.setReviewCycleName(selectedReviewCycle
					.getReviewCycleName());
		} else if (type.equals("self") || type.equals("projectchange")) {
			feedbackRequestForm
					.setRequestedByEmployee(convertDomainToVo(feedbackRequest
							.getRequestedByEmployee()));

			feedbackRequestForm
					.setRequestedToEmployee(convertDomainToVo(feedbackRequest
							.getRequestedToEmployee()));

			feedbackRequestForm.setRequestDueDate(new Date());
			ReviewCycle selectedReviewCycle = feedbackRequest.getReviewHeader()
					.getReviewCycle();
			feedbackRequestForm.setReviewCycleId(selectedReviewCycle
					.getReviewCycleId());
			feedbackRequestForm.setReviewCycleName(selectedReviewCycle
					.getReviewCycleName());

		} else if (type.equals("selfReceived")) {
			feedbackRequestForm
					.setRequestedToEmployee(convertDomainToVo(feedbackRequest
							.getRequestedToEmployee()));

			feedbackRequestForm.setRequestDueDate(new Date());

			ReviewCycle selectedReviewCycle = feedbackRequest.getReviewHeader()
					.getReviewCycle();
			feedbackRequestForm.setReviewCycleId(selectedReviewCycle
					.getReviewCycleId());
			feedbackRequestForm.setReviewCycleName(selectedReviewCycle
					.getReviewCycleName());

		}
		return feedbackRequestForm;
	}

	public FeedbackRequestForm generateFeedbackRequest(int headerId, String type) {

		List<FeedbackQuestion> feedbackQuestions = feedBackRequestService
				.getFeedbackQuestions(true);
		FeedbackRequestForm feedbackRequestForm = new FeedbackRequestForm();
		if (headerId != 0) {
			ReviewHeader reviewHeader = feedBackRequestService
					.getReviewHeader(headerId);
			int loginEmployeeId = SecurityDetailsAccessor.getEmpId();
			if (loginEmployeeId == reviewHeader.getMainAppraiserEmployee()
					.getEmpId()) {
				feedbackRequestForm
						.setRequestedByEmployee(convertDomainToVo(reviewHeader
								.getMainAppraiserEmployee()));
			} else {
				feedbackRequestForm
						.setRequestedByEmployee(convertDomainToVo(employeeService
								.getEmployeeById(loginEmployeeId)));
			}
			feedbackRequestForm
					.setFeedbackOnEmployee(convertDomainToVo(reviewHeader
							.getEmployee()));
		} else {
			if (type.equals("request")) {
				feedbackRequestForm
						.setRequestedByEmployee(convertDomainToVo(employeeService
								.getEmployeeById(SecurityDetailsAccessor
										.getEmpId())));
			}
		}

		List<FeedbackQuestionAnswerVO> feedbackQuestionAnswerVOs = new ArrayList<FeedbackQuestionAnswerVO>();

		for (FeedbackQuestion feedbackQuestion : feedbackQuestions) {

			FeedbackQuestionAnswerVO feedbackQuestionAnswerVO = new FeedbackQuestionAnswerVO();

			feedbackQuestionAnswerVO.setFeedbackQuestion(feedbackQuestion
					.getQuestion());
			feedbackQuestionAnswerVO.setFeedbackQuestionId(feedbackQuestion
					.getFeedbackQuestionId());
			feedbackQuestionAnswerVOs.add(feedbackQuestionAnswerVO);
		}

		feedbackRequestForm
				.setFeedbackQuestionAnswerVOs(feedbackQuestionAnswerVOs);

		return feedbackRequestForm;

	}

	public ReviewCycleVO convertDomainToVo(ReviewCycle reviewCycle) {

		ReviewCycleVO reviewCycleVO = new ReviewCycleVO();

		reviewCycleVO.setReviewCycleName(reviewCycle.getReviewCycleName());
		reviewCycleVO.setReviewCycleStartDate(reviewCycle
				.getReviewCycleStartDate());
		reviewCycleVO
				.setReviewCycleEndDate(reviewCycle.getReviewCycleEndDate());
		reviewCycleVO.setReviewCycleId(reviewCycle.getReviewCycleId());
		return reviewCycleVO;
	}

	public EmployeeVO convertDomainToVo(Employee employee) {

		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setDisplayName(employee.getDisplayName());
		employeeVO.setEmpId(employee.getEmpId());
		employeeVO.setEmail(employee.getEmail());

		if (employee.getDesignation() != null) {
			employeeVO.setDesignation(employee.getDesignation().getShortName());
		}
		return employeeVO;

	}

}
