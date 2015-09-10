package com.ggk.hrms.review.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.SharedAppraiserService;
import com.ggk.hrms.review.task.FeedbackSharingNotificationTask;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;


@Controller("reviewShareController")
@RequestMapping("/appraisal")
public class ReviewShareController {

	@Autowired
	private SharedAppraiserService sharedAppraiserService;

	@Autowired
	private ReviewHeaderService reviewHeaderService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ReviewFormService reviewFormService;
	
	@Autowired
	private ReviewAuditService reviewAuditService;
	
	@Resource
	private MailingService mailingService;
	
	@Resource
	private ReviewCycleService reviewCycleService;
	
	@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER"})
	@RequestMapping(value = "/share.html", method = RequestMethod.POST)
	public @ResponseBody String shareAppraisal(Model model,
			@RequestParam(value = "shareOrDelegate") String shareOrDelegate,
			//@ModelAttribute(value = "shareAppraisalForm") ShareAppraisalForm shareAppraisalForm,
			@RequestParam(value = "reviewHeaderIds") String reviewHeaderIds,
			@RequestParam(value = "targetEmployeeId") Integer targetEmployeeId,
			@RequestParam(value = "reviewCycleId") Integer reviewCycleId)
	{
		int assignedByEmployeeId = SecurityDetailsAccessor.getEmpId();
		if(targetEmployeeId==assignedByEmployeeId){
			return "selfAssign";
		}
		if(employeeService.getRoles(targetEmployeeId).get(0).getAuthority().equals("ROLE_SUPERUSER")){
			return "superUserAssign";
		}
		String[] reviewHeaderArray = reviewHeaderIds.split(",");
		List<Integer> selectedAppraisalList = new ArrayList<Integer>();
		for(String headerId : reviewHeaderArray) {
			selectedAppraisalList.add(Integer.valueOf(headerId));
		}
		String assignedByEmployeeName=employeeService.getEmployeeById(assignedByEmployeeId).getDisplayName();
		if(shareOrDelegate.equals("share")){
			List<ReviewActionLog> reviewActionLogs=new ArrayList<ReviewActionLog>();
			for(int i=0;i<selectedAppraisalList.size();i++ ) {
				int selectedAppraisalreviewHeaderId=selectedAppraisalList.get(i);
				ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(selectedAppraisalreviewHeaderId);
				ReviewActionLog reviewActionLog= new ReviewActionLog();
				reviewActionLog.setCreateByEmployeeId(assignedByEmployeeId);
				ReviewFormRole reviewFormRole = reviewFormService.getReviewFormRole(reviewHeader);
				if (reviewFormRole == null || reviewFormRole == ReviewFormRole.SHARED_APPRAISER || reviewFormRole == ReviewFormRole.APPRAISE){
					throw new RuntimeException("Invalid ReviewFormRole");
				}
				reviewActionLog.setCreatedByRole(reviewFormRole);
				reviewActionLog.setReviewHeaderId(reviewHeader.getReviewHeaderId());
				reviewActionLog.setActionType("SHARE");
				reviewActionLog.setCreatedDate(new Date());
				String targetEmployeeName=employeeService.getEmployeeById(targetEmployeeId).getDisplayName();				
				reviewActionLog.setCreatedByName(assignedByEmployeeName);
				reviewActionLog.setUserCommentText(assignedByEmployeeName+" shared appraisal with "+targetEmployeeName);
				reviewActionLog.setReviewFieldGroupType(ReviewFeildGroupType.APPRAISAL);
				reviewActionLogs.add(reviewActionLog);
				SharedAppraiser activeSharedAppraiser=null;
				int activeSharedAppraiserEmployeeId=0;
				activeSharedAppraiser=sharedAppraiserService.getActiveSharedAppraiser(selectedAppraisalreviewHeaderId);
				if(activeSharedAppraiser!=null){
					activeSharedAppraiserEmployeeId=activeSharedAppraiser.getAssignedToEmployee().getEmpId();
				}
				if (targetEmployeeId==reviewHeader.getEmployee().getEmpId()){
					return "assignToAppraise";
				} else if (targetEmployeeId==reviewHeader.getMainAppraiserEmployee().getEmpId()|| reviewFormService.isGivenEmpIdHasAccesstoIt(targetEmployeeId, reviewHeader)){
					return "assignToMainAppraiser";
				}  else if (targetEmployeeId==activeSharedAppraiserEmployeeId){
					return "assignToActiveSharedAppraiser";
				}
			}
			sharedAppraiserService.shareAppraisalsToEmployee(selectedAppraisalList, targetEmployeeId, assignedByEmployeeId, reviewCycleId);
			
			reviewAuditService.saveReviewActionLog(reviewActionLogs);
		}
		return "success";
	}
	
	@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER"})
	@RequestMapping(value = "/unShare.html", method = RequestMethod.POST)
	public @ResponseBody String unShareAppraisal(@RequestParam(value = "reviewHeaderIds") List<Integer> reviewHeaderIds,@RequestParam("reviewCycleId") int reviewCycleId){
		
		Map<Integer, List<String>> employeesToNotify = new HashMap<Integer, List<String>>();
		for (Integer reviewHeaderId : reviewHeaderIds) {

			SharedAppraiser activeSharedAppraiser = sharedAppraiserService
					.getActiveSharedAppraiser(reviewHeaderId);
			if (activeSharedAppraiser == null)
				continue;
			if (employeesToNotify.containsKey(activeSharedAppraiser
					.getAssignedToEmployee().getEmpId()) == false) {

				List<String> employeeNames = new ArrayList<String>();
				employeeNames.add(activeSharedAppraiser.getReviewHeader()
						.getEmployee().getDisplayName());
				employeesToNotify.put(activeSharedAppraiser
						.getAssignedToEmployee().getEmpId(),
						employeeNames);
			} else {
				List<String> employeeNames = employeesToNotify
						.get(activeSharedAppraiser.getAssignedToEmployee()
								.getEmpId());
				employeeNames.add(activeSharedAppraiser.getReviewHeader()
						.getEmployee().getDisplayName());
			}
			activeSharedAppraiser.setActive(false);
			activeSharedAppraiser.setDueDate(new Date());
			sharedAppraiserService.update(activeSharedAppraiser);
			ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(reviewHeaderId);
			reviewHeader.setSharedAppraiserInternalComment(null);
			reviewHeaderService.merge(reviewHeader);

		}

		for (Integer employeeId : employeesToNotify.keySet()) {

			String subject = employeeService.getEmployeeById(
					SecurityDetailsAccessor.getEmpId()).getDisplayName()
					+ " Has Unshared Appraisal(s).";
			Employee sendToEmployee = employeeService
					.getEmployeeById(employeeId);
			String employeeNames = employeesToNotify.get(employeeId).toString();
			employeeNames = employeeNames.substring(1,employeeNames.length()-1).replace(", ", "\n");
			String text = "Hi "
					+ sendToEmployee.getDisplayName()
					+ ",\n\n"
					+ employeeService.getEmployeeById(
							SecurityDetailsAccessor.getEmpId())
							.getDisplayName()
					+ " Unshared the below appraisal(s): \n\n"
					+ employeeNames;
			ReviewCycle reviewCycle = reviewCycleService
					.getReviewCycle(reviewCycleId);

			new Thread(new FeedbackSharingNotificationTask(mailingService,
					sendToEmployee.getEmail(), null, subject, text,
				reviewCycle.getReviewCycleName(), "Unsharing")).start();

		}

		return "success";
	}

	@RolesAllowed(value = { "ROLE_MANAGER", "ROLE_SUPERUSER", "ROLE_USER"})
	@RequestMapping(value = "/assessmentCompleted.html", method = RequestMethod.POST)
	public @ResponseBody String assessmentCompleted(Model model,
			@RequestParam(value = "reviewHeaderId", required = true) int reviewHeaderId,
			@RequestParam(value = "opsType") String opsType,
			@RequestParam(value = "checkinComments") String checkinComments) {
		ReviewFormInfoVO reviewFormInfoVO=createReviewFormInfoVO(reviewHeaderId);
		
		if(reviewFormInfoVO.getReviewFormRole()!=ReviewFormRole.SHARED_APPRAISER
//				|| (reviewFormInfoVO.getReviewFormRole()==ReviewFormRole.SHARED_APPRAISER 
//				&& (reviewFormInfoVO.getReviewPhase()!=ReviewPhase.APPRAISER_IN_PHASE1 && reviewFormInfoVO.getReviewPhase()!=ReviewPhase.APPRAISER_IN_PHASE2 &&
//						reviewFormInfoVO.getReviewPhase()==ReviewPhase.SYSTEM_PHASE1_COMPLETED &&reviewFormInfoVO.getReviewPhase()==ReviewPhase.SYSTEM_PHASE2_COMPLETED))
						){
			
			return "false";
		}
		int assignedToEmployeeId = SecurityDetailsAccessor.getEmpId();
		SharedAppraiser sharedAppraiser=sharedAppraiserService.getActiveSharedAppraiser(reviewHeaderId);
		if(sharedAppraiser != null && assignedToEmployeeId == sharedAppraiser.getAssignedToEmployee().getEmpId()) {
			sharedAppraiser.setActive(false);
			sharedAppraiser.setDueDate(new Date());
			sharedAppraiser = sharedAppraiserService.update(sharedAppraiser);
			String subject = sharedAppraiser.getAssignedToEmployee().getDisplayName() + " completed the assessment on " + sharedAppraiser.getReviewHeader().getEmployee().getDisplayName();
			String text = "Hi " + sharedAppraiser.getAssignedByEmployee().getDisplayName() + ",\n\nYou can continue with your assessment on " + sharedAppraiser.getReviewHeader().getEmployee().getDisplayName();
			new Thread(new FeedbackSharingNotificationTask(mailingService, sharedAppraiser.getAssignedByEmployee().getEmail(), null, subject, text, sharedAppraiser.getReviewHeader().getReviewCycle().getReviewCycleName(), "Sharing")).start();
			//mailingService.sendSharingNotification(sharedAppraiser.getAssignedByEmployee().getEmail(), subject, text, sharedAppraiser.getReviewHeader().getReviewCycle().getReviewCycleName());
			ReviewActionLog reviewActionLog = reviewAuditService.createReviewActionLog(
					reviewFormInfoVO, "UNSHARE",
					ReviewFeildGroupType.APPRAISAL, null, null, null, null,
					checkinComments);
			reviewAuditService.saveReviewActionLog(reviewActionLog);
			ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(reviewHeaderId);
			reviewHeader.setSharedAppraiserInternalComment(null);
			reviewHeaderService.merge(reviewHeader);
			return "success";
		}
		return "false";
	}
	
	
	public ReviewFormInfoVO createReviewFormInfoVO(int reviewHeaderId) {
		return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(), null);
	}
	
}
