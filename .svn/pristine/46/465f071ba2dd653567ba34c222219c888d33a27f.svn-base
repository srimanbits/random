package com.ggk.hrms.review.process.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ManagerEmployeeEmailVO;

public class ReviewReminderMailingJob {

	Logger log = Logger.getLogger(ReviewReminderMailingJob.class);
	
	@Resource(name = "reviewCycleService")
	private ReviewCycleService reviewCycleService;
	
	@Resource
	private String reminderNoofDaysBefore;
	
	@Resource
	private int reminderNoofDaysAfter;
		
	@Resource
	private MailingService mailingService;
	
	@Resource
	private String pmsToolAddress;
	
	private Boolean isDueDateAfter = false;
	
	private int noOfMilliSecsinOneday=1000 * 60 * 60 * 24;
	
	Map<String, List<ManagerEmployeeEmailVO>> managerEmployeesEmailIdsMap = new HashMap<String, List<ManagerEmployeeEmailVO>>();
	Map<String, Map<String, List<ManagerEmployeeEmailVO>>> managerSharedEmployeesEmailIdsMap= new HashMap<String, Map<String,List<ManagerEmployeeEmailVO>>>();	
	
	@SuppressWarnings("deprecation")
	public void sendReminderEmail() {
		
		log.info("job running");
		List<ReviewCycle> reviewCycles = reviewCycleService.getActiveReviewCycles();
		log.info("No of review cycles:" + reviewCycles.size());
		log.info("Review cycles for which reminder mails to be sent :" + reviewCycles);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date todaysDate = null;
		try {
			todaysDate = dateFormat.parse(dateFormat.format(new Date()));
		} catch(ParseException pe) {
			pe.printStackTrace();
		}
		
		for(ReviewCycle reviewCycle : reviewCycles) {
		
			if (reviewCycle != null) {
				float pendingDays = 0;
				if (reviewCycle.getGoalsSettingDueDate() != null && reviewCycle.getGoalsApprovalDueDate() != null) {
					pendingDays=((float)(reviewCycle.getGoalsSettingDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getGoalsApprovalDueDate().getTime())/ (noOfMilliSecsinOneday)<0){
						goalsSettingReminder(pendingDays,reviewCycle);
					}
				}
				
				if (reviewCycle.getGoalsApprovalDueDate() != null && reviewCycle.getGoalsAcceptanceDueDate() != null) {
					pendingDays=((float)(reviewCycle.getGoalsApprovalDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getGoalsAcceptanceDueDate().getTime())/ (noOfMilliSecsinOneday)<0){
						approveGoalsReminder(pendingDays,reviewCycle);
					}
				}
				
				if (reviewCycle.getGoalsAcceptanceDueDate() != null && reviewCycle.getSelfAppraisalDueDate() != null) {
					pendingDays=((float)(reviewCycle.getGoalsAcceptanceDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getSelfAppraisalDueDate().getTime())/(noOfMilliSecsinOneday)<0){
						acceptGoalsReminder(pendingDays, reviewCycle);
					}
				}
				
				if (reviewCycle.getSelfAppraisalDueDate() != null && reviewCycle.getAppraisalDiscussionDueDate() != null) {
					pendingDays=((float)(reviewCycle.getSelfAppraisalDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getAppraisalDiscussionDueDate().getTime())/(noOfMilliSecsinOneday)<0){
						selfAppraisalReminder(pendingDays,reviewCycle);
					}
				}
				
				if (reviewCycle.getAppraisalDiscussionDueDate() != null && reviewCycle.getAppraisalDueDate() != null) {
					pendingDays=((float)(reviewCycle.getAppraisalDiscussionDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getAppraisalDueDate().getTime())/(noOfMilliSecsinOneday)<0){
						appraisalDiscussionProcessReminder(pendingDays,reviewCycle);
					}
				}
				
				if (reviewCycle.getAppraisalDueDate() != null && reviewCycle.getReviewCycleEndDate() != null) {
					pendingDays=((float)(reviewCycle.getAppraisalDueDate().getTime()- todaysDate.getTime()) / (noOfMilliSecsinOneday));
					if(pendingDays>=0 && (todaysDate.getTime()-reviewCycle.getReviewCycleEndDate().getTime())/(noOfMilliSecsinOneday)<0){
						acceptRating(pendingDays,reviewCycle);
					}
				}
			}
		}
		
	}
	
	private void sendEmailNotificationForPendingFeedback() {
		log.info("feedback Notification job started");
		Map<String, List<FeedbackRequestVO>> managerEmployeeEmailIdsMap ;
		Map<String, List<FeedbackRequestVO>> currentManagerEmployeeEmailIdsMap ;
		List<Map<String, List<FeedbackRequestVO>>> emailMap=reviewCycleService.getManagerEmployeeEmailToNotifyForFeedback();
		managerEmployeeEmailIdsMap=emailMap.get(0);
		currentManagerEmployeeEmailIdsMap=emailMap.get(1);
		
		Set<String> managerEmails=managerEmployeeEmailIdsMap.keySet();
		String subject = "Reminder - Send Your Feedback ";
		for (String managerEmail : managerEmails) {
			boolean isTableCreated = false;
			StringBuilder message = new StringBuilder();
			for (FeedbackRequestVO feedbackRequestVO : managerEmployeeEmailIdsMap
					.get(managerEmail)) {
				if (isTableCreated == false) {
					message.append("Hi ")
							.append(feedbackRequestVO.getRequestedToEmployee())
							.append(",\n\n<br><br>")
							.append("Request you to give your feedback on following Employees to their current Managers. Please visit 'Appraisal > Feedback Solicited' page in PMS.");
					message.append("\n\n<br><br>");
					message.append("<table cellspacing=\"0\" cellpadding=\"5px\" border=\"1\" ><thead style=\"font-weight:bold;background-color:rgb(204,204,204)\"><tr style=\"  padding-right: 50px;\"><td>Employee</td><td>Current Manager</td></thead><tbody>");
					isTableCreated = true;
				}

				message.append("<tr><td style=\"  padding-right: 50px;\">")
						.append(feedbackRequestVO.getRequestedOnEmployee())
						.append("</td><td style=\"  padding-right: 50px;\">")
						.append(feedbackRequestVO.getRequestedByEmployee())
						.append("</td></tr>");
			}
			message.append("</tbody></table>");
			
			mailingService.sendEmailNotificationWithHtml(pmsToolAddress,managerEmail , null, null, subject, message.toString());
			log.info("feedback Notification  sent");
		}
		
		
		Set<String> currentManagerEmails=currentManagerEmployeeEmailIdsMap.keySet();
		String subject1 = "Reminder - Get Feedback From Previous Manager ";
		for (String managerEmail : currentManagerEmails) {
			boolean isTableCreated = false;
			StringBuilder message = new StringBuilder();
			for (FeedbackRequestVO feedbackRequestVO : currentManagerEmployeeEmailIdsMap
					.get(managerEmail)) {
				if (isTableCreated == false) {
					message.append("Hi ")
							.append(feedbackRequestVO.getRequestedByEmployee())
							.append(",\n\n<br><br>")
							.append("Request you to check feedback on following Employees from their previous Managers. Please visit 'SuperUser/Manager > Solicit Feedback' page in PMS.");
					message.append("\n\n<br><br>");
					message.append("<table cellspacing=\"0\" cellpadding=\"5px\" border=\"1\" ><thead style=\"font-weight:bold;background-color:rgb(204,204,204)\"><tr><td style=\"  padding-right: 50px;\">Employee</td><td>Previous Manager</td></thead><tbody>");
					isTableCreated = true;
				}

				message.append("<tr><td style=\"  padding-right: 50px;\">")
						.append(feedbackRequestVO.getRequestedOnEmployee())
						.append("</td><td style=\"  padding-right: 50px;\">")
						.append(feedbackRequestVO.getRequestedToEmployee())
						.append("</td></tr>");
			}
			message.append("</tbody></table>");
			
			mailingService.sendEmailNotificationWithHtml(pmsToolAddress,managerEmail , null, null, subject1, message.toString());
			log.info("feedback Notification  sent");
		}
		
		log.info("feedback Notification job Completed");
	}
	
	//Sending reminder e-mails to employees to submit their goals.
	private void goalsSettingReminder(float pendingDays,ReviewCycle reviewCycle){
		if(pendingDays>=0){
			
			managerEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesEmailMapToNotify("NOT_STARTED","NEED_TO_EDIT_GOALS","GOALS_SETTING_IN_PROGRESS",reviewCycle.getReviewCycleId(),pendingDays);
			Set<String> managerEmails=managerEmployeesEmailIdsMap.keySet();
			
			for (String managerEmail : managerEmails) {
				
				for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeesEmailIdsMap.get(managerEmail)) {
					if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
					String subject=managerEmployeeEmailVO.getSubject();
					String replacingValue="- Annual Appraisal "+Calendar.getInstance().get(Calendar.YEAR);
					log.info(subject.replace("-",replacingValue ));
					
					//adding manager email id as Cc while sending e-mail to employee if pending days is less than 4
					if(managerEmployeeEmailVO.getEmployeeStatus().equals ("NOT_STARTED") ||managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_GOALS") || managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_SETTING_IN_PROGRESS")){
					if(pendingDays<4)
					{
						String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
						mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(),mailToCc ,null,managerEmployeeEmailVO.getSubject(),
								"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
					}
					
					//sending email only to the employees if pending days is greater than or equal to 4
					else{
						mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
							"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						log.info("notification sent");
						}
					}
				}
					//half-yearly
					else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId() == 2){
						
						String replacingValue="";
						if(reviewCycle.getReviewCycleName().equals("H2")){
						
							replacingValue="- H1 "+(Calendar.getInstance().get(Calendar.YEAR)+1);
						}
						else{
							replacingValue="- H2 "+(Calendar.getInstance().get(Calendar.YEAR));
						}
						//sending cc to manager if pending days is less than 4
						if(managerEmployeeEmailVO.getEmployeeStatus().equals ("NOT_STARTED") ||managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_GOALS") || managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_SETTING_IN_PROGRESS")){
						if(pendingDays<4)
						{
							String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(),mailToCc ,null,managerEmployeeEmailVO.getSubject(),
									"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						}
						
						//sending email to employees only if pending days is greater than or equal to 4
						else{
						mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
								"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						log.info("notification sent");
						}
						}
						
					}
				}
			}
			
		}
		
		
	}
	
	
	//sending reminder e-mails to managers to approve the goals of the employees.
	private void approveGoalsReminder(float pendingDays,ReviewCycle reviewCycle){
		if(pendingDays>=0){
			managerEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesEmailMapToNotify("GOALS_SUBMITTED","","",reviewCycle.getReviewCycleId(),pendingDays);
			int role = 0;
				
					Set<String> managerEmailVOs=managerEmployeesEmailIdsMap.keySet();
					for (String managerEmail : managerEmailVOs) {
						String mailToCc="", annualEmployees="", halfYearlyEmployees="";
						String annualMessage="", halfYearlyMessage="", annualSubject="", halfYearlySubject="", replacingValue="";
						for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeesEmailIdsMap.get(managerEmail)) {
							if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
								annualMessage="Dear "+managerEmployeeEmailVO.getManagerName()+"\n\n"+managerEmployeeEmailVO.getMessage();
							if(managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_SUBMITTED")){
								role=managerEmployeeEmailVO.getRole();
								
								mailToCc=managerEmployeeEmailVO.getManagerEmailId();
								annualSubject=managerEmployeeEmailVO.getSubject();
								annualEmployees=annualEmployees+"\n"+managerEmployeeEmailVO.getEmployeeName();
								}
							}
							else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==2){
								halfYearlyMessage="Dear "+managerEmployeeEmailVO.getManagerName()+"\n\n"+managerEmployeeEmailVO.getMessage();
							if(managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_SUBMITTED")){
								halfYearlySubject=managerEmployeeEmailVO.getSubject();
								
								mailToCc=managerEmployeeEmailVO.getManagerEmailId();
								if(reviewCycle.getReviewCycleName().equals("H2")){
									
									replacingValue="- H1 "+(Calendar.getInstance().get(Calendar.YEAR)+1);
								}
								else{
									replacingValue="- H2 "+(Calendar.getInstance().get(Calendar.YEAR));
								}

								halfYearlyEmployees=halfYearlyEmployees+"\n"+managerEmployeeEmailVO.getEmployeeName();
							}
						}
						}
						annualMessage=annualMessage+"\n"+annualEmployees;
						halfYearlyMessage=halfYearlyMessage+"\n"+halfYearlyEmployees;
							if(pendingDays<4){
								String toCcMangerEmail=null;
								Employee employee = reviewCycleService.getManagerEmailId(mailToCc);
								
								//SEND TO HR
								
								if(role==1 || employee.getReportingToEmployee()==null){
								//String[] superUserMailToCc={"apptestkpr@gmail.com"};
								if(annualMessage!=null && annualMessage.trim().length()!=0){
								mailingService.sendEmailNotification(pmsToolAddress, managerEmail,null,null,annualSubject,annualMessage);
								log.info("notification sent");
								}
								if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0){
								mailingService.sendEmailNotification(pmsToolAddress, managerEmail,null,null,halfYearlySubject,halfYearlyMessage);
								log.info("notification sent");
								}
								}
								else{
									toCcMangerEmail=employee.getReportingToEmployee().getEmail();
									//log.info(toCcMangerEmail);
									String[] sendmailToCc={toCcMangerEmail};
									if(annualMessage!=null && annualMessage.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc ,null,annualSubject,annualMessage);
									log.info("notification sent");
									}
									if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc,null,halfYearlySubject,halfYearlyMessage);
									log.info("notification sent");
									}
								}
							
							}
							else{
								if(annualMessage!=null && annualMessage.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null , null,annualSubject,annualMessage);
									log.info("notification sent");
									}
									if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null , null,halfYearlySubject,halfYearlyMessage);
									log.info("notification sent");
									}
							}
				}
			
		}
	}
	
	//sending e-mails to employees to accept their goals which are approved by their manager.
	private void acceptGoalsReminder(float pendingDays,ReviewCycle reviewCycle){
		
		if(pendingDays>=0){
			
			managerEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesEmailMapToNotify("GOALS_FINALIZED","NEED_TO_EDIT_APPRAISAL","",reviewCycle.getReviewCycleId(),pendingDays);
			Set<String> managerEmails=managerEmployeesEmailIdsMap.keySet();
			
			for (String managerEmail : managerEmails) {
				
					for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeesEmailIdsMap.get(managerEmail)) {
						if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
					String replacingValue="- Annual Appraisal "+Calendar.getInstance().get(Calendar.YEAR);
					if(managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_FINALIZED") || managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_APPRAISAL")){
						
						if(pendingDays<4){
							String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), mailToCc,null,managerEmployeeEmailVO.getSubject(),
									"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
							log.info("notification sent");
						}
						else{
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
							"Dear "+managerEmployeeEmailVO.getEmployeeName()+"\n\n"+managerEmployeeEmailVO.getMessage());
					log.info("notification sent");
						}
					}
					//SEND COPY TO REPORTING MANAGER IF PENDING DAYS=8,9,10.
				}
					else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==2){
						//half-yearly
						String replacingValue="";
						if(reviewCycle.getReviewCycleName().equals("H2")){
						
							replacingValue="- H1 "+(Calendar.getInstance().get(Calendar.YEAR)+1);
						}
						else{
							replacingValue="- H2 "+(Calendar.getInstance().get(Calendar.YEAR));
						}
						if(managerEmployeeEmailVO.getEmployeeStatus().equals("GOALS_FINALIZED") || managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_APPRAISAL")){
							
							if(pendingDays<4){
								String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
						mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(),mailToCc ,null,managerEmployeeEmailVO.getSubject(),
								"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
							}
							else
							{
								mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
										"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
										
							}
						log.info("notification sent");
						}
						
					}
				}
			}
		}
	}
	
	//sending e-mails to employees to complete their self-appraisal.
	private void selfAppraisalReminder(float pendingDays,ReviewCycle reviewCycle){
		
		if(pendingDays>=0){
			
			managerEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesEmailMapToNotify("GOALS_ACCEPTED","APPRAISAL_IN_PROGRESS","NEED_TO_EDIT_APPRAISAL",reviewCycle.getReviewCycleId(),pendingDays);
			Set<String> managerEmails=managerEmployeesEmailIdsMap.keySet();
			
			for (String managerEmail : managerEmails) {
				
					for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeesEmailIdsMap.get(managerEmail)) {
						if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
					String replacingValue="- Annual Appraisal "+Calendar.getInstance().get(Calendar.YEAR);
					if(managerEmployeeEmailVO.getEmployeeStatus().equals ("GOALS_ACCEPTED") ||managerEmployeeEmailVO.getEmployeeStatus().equals("APPRAISAL_IN_PROGRESS") || managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_APPRAISAL")){
						if(pendingDays<4){
							String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), mailToCc,null,managerEmployeeEmailVO.getSubject(),
									"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						}
						else{
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
							"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						}
					log.info("notification sent");
					}
					//SEND COPY TO REPORTING MANAGER IF PENDING DAYS=8,9,10.
				}
						else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==2){
						//half-yearly
						String replacingValue="";
						
						replacingValue="- H2 "+(Calendar.getInstance().get(Calendar.YEAR));
						
						if(managerEmployeeEmailVO.getEmployeeStatus().equals ("GOALS_ACCEPTED") ||managerEmployeeEmailVO.getEmployeeStatus().equals("APPRAISAL_IN_PROGRESS") || managerEmployeeEmailVO.getEmployeeStatus().equals("NEED_TO_EDIT_APPRAISAL")){
							
							if(pendingDays<4){
								String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
								mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), mailToCc,null,managerEmployeeEmailVO.getSubject(),
										"Dear "+managerEmployeeEmailVO.getEmployeeName()+"\n\n"+managerEmployeeEmailVO.getMessage());
								log.info("notification sent");
							}
							else
							{
								mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
										"Dear "+managerEmployeeEmailVO.getEmployeeName()+"\n\n"+managerEmployeeEmailVO.getMessage());
								log.info("notification sent");
							}
							
						
						}
						
					}
				}
			}
			
		}
	}
	
	
	//sending e-mails to managers to finish the appraisal discussion process with the employees.
	private void appraisalDiscussionProcessReminder(float pendingDays,ReviewCycle reviewCycle){
		
			if(pendingDays>=0){
				

				managerSharedEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesForSharedEmailMapToNotify("APPRAISAL_SUBMITTED","ASSESSMENT_IN_PROGRESS","",reviewCycle.getReviewCycleId(),pendingDays);
				int role = 0;
					
						Set<String> managerEmailVOs=managerSharedEmployeesEmailIdsMap.keySet();
						for (String managerEmail : managerEmailVOs) {
							
							
								Map<String, List<ManagerEmployeeEmailVO>> sharedAppraisalAndEmployeeEmails= managerSharedEmployeesEmailIdsMap.get(managerEmail);
								Set<String> sharedAppraiserEmails = sharedAppraisalAndEmployeeEmails.keySet();
								
								for (String sharedAppraiserEmail : sharedAppraiserEmails) {
									String annualEmployees="", halfYearlyEmployees="",sharedAppraiserEmailId=null;
									String annualMessage="", halfYearlyMessage="", annualSubject="", halfYearlySubject="", replacingValue="", mailToCc=null;
								for (ManagerEmployeeEmailVO managerEmployeeEmailVO : sharedAppraisalAndEmployeeEmails.get(sharedAppraiserEmail)) {
							
								if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
									annualMessage="Dear "+managerEmployeeEmailVO.getManagerName()+"\n"+managerEmployeeEmailVO.getMessage();
								if(managerEmployeeEmailVO.getEmployeeStatus().equals("APPRAISAL_SUBMITTED") || managerEmployeeEmailVO.getEmployeeStatus().equals("ASSESSMENT_IN_PROGRESS")){
									role=managerEmployeeEmailVO.getRole();
									
									sharedAppraiserEmailId = managerEmployeeEmailVO.getSharedAppraiserEmailId();
									log.info("sharedAppraiserEmailId"+sharedAppraiserEmailId);
									
									annualSubject=managerEmployeeEmailVO.getSubject();
									mailToCc=managerEmployeeEmailVO.getManagerEmailId();
									annualEmployees=annualEmployees+"\n"+managerEmployeeEmailVO.getEmployeeName();
									}
								}
								else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==2){
									halfYearlyMessage="Dear "+managerEmployeeEmailVO.getManagerName()+"\n\n"+managerEmployeeEmailVO.getMessage();
								if(managerEmployeeEmailVO.getEmployeeStatus().equals("APPRAISAL_SUBMITTED") || managerEmployeeEmailVO.getEmployeeStatus().equals("ASSESSMENT_IN_PROGRESS")){
									halfYearlySubject=managerEmployeeEmailVO.getSubject();
									
								//	if(!managerEmployeeEmailVO.getSharedAppraiserEmailId().equals(managerEmployeeEmailVO.getEmployeeEmailId())){
										sharedAppraiserEmailId = managerEmployeeEmailVO.getSharedAppraiserEmailId();
										log.info("sharedAppraiserEmailId"+sharedAppraiserEmailId);
										//}
									
									mailToCc=managerEmployeeEmailVO.getManagerEmailId();
									replacingValue="- H2 "+(Calendar.getInstance().get(Calendar.YEAR));
									halfYearlyEmployees=halfYearlyEmployees+"\n"+managerEmployeeEmailVO.getEmployeeName();
								}
							}
							}
							annualMessage=annualMessage+"\n"+annualEmployees;
							halfYearlyMessage=halfYearlyMessage+"\n"+halfYearlyEmployees;
								if(pendingDays<4){
									Employee employee = reviewCycleService.getManagerEmailId(mailToCc);
									String toCcMangerEmail = null;
									if (employee.getReportingToEmployee() != null){
										toCcMangerEmail=employee.getReportingToEmployee().getEmail();
									}
									//SEND TO HR
									if(role==1 || toCcMangerEmail==null ||toCcMangerEmail.trim().length()==0){
										
										if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
											//String[] superUserMailToCc={"apptestkpr@gmail.com",sharedAppraiserEmailId};
											String[] superUserMailToCc={sharedAppraiserEmailId};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail,superUserMailToCc,null,annualSubject,annualMessage);
											log.info("notification sent");
										}
										else if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId==null){
											//String[] superUserMailToCc={"apptestkpr@gmail.com"};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null,null,annualSubject,annualMessage);
											log.info("notification sent");
										}
										else if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
											String[] superUserMailToCc={sharedAppraiserEmailId};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, superUserMailToCc,null,halfYearlySubject,halfYearlyMessage);
											log.info("notification sent");
										}
										else if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId==null){
											//String[] superUserMailToCc={"apptestkpr@gmail.com"};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null,null,halfYearlySubject,halfYearlyMessage);
											log.info("notification sent");
										}
									}
									else{
										log.info(toCcMangerEmail);
										
										if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
											String[] sendmailToCc={toCcMangerEmail,sharedAppraiserEmailId};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc,null,annualSubject,annualMessage);
											log.info("notification sent");
										}
										else if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId==null){
											String[] superUserMailToCc={toCcMangerEmail};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, superUserMailToCc,null,annualSubject,annualMessage);
											log.info("notification sent");
										}
										if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
											String[] sendmailToCc={toCcMangerEmail,sharedAppraiserEmailId};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc,null,halfYearlySubject,halfYearlyMessage);
											log.info("notification sent");
										}
										else if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId==null){
											String[] superUserMailToCc={toCcMangerEmail};
											mailingService.sendEmailNotification(pmsToolAddress, managerEmail, superUserMailToCc,null,halfYearlySubject,halfYearlyMessage);
											log.info("notification sent");
										}
									}
								}
								else {
									String[] sendmailToCc={sharedAppraiserEmailId};
									if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc,null,annualSubject,annualMessage);
									log.info("notification sent");
									}
									else if(annualMessage!=null && annualMessage.trim().length()!=0 && sharedAppraiserEmailId==null){
										mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null,null,annualSubject,annualMessage);
										log.info("notification sent");
									}
									else if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId!=null && sharedAppraiserEmailId.trim().length()!=0){
									mailingService.sendEmailNotification(pmsToolAddress, managerEmail, sendmailToCc,null,halfYearlySubject,halfYearlyMessage);
									log.info("notification sent");
									}
									else if(halfYearlyMessage!=null && halfYearlyMessage.trim().length()!=0  && sharedAppraiserEmailId==null){
										mailingService.sendEmailNotification(pmsToolAddress, managerEmail, null,null,halfYearlySubject,halfYearlyMessage);
										log.info("notification sent");
									}
								}
							}
					}
			
			}
	}
	
	
	//sending e-mails to employees to accept the rating given by manager.
	private void acceptRating(float pendingDays,ReviewCycle reviewCycle){
		if(pendingDays>=0){
			
			managerEmployeesEmailIdsMap=reviewCycleService.getManagerEmployeesEmailMapToNotify("READY_FOR_MEETING","","",reviewCycle.getReviewCycleId(),pendingDays);
			Set<String> managerEmails=managerEmployeesEmailIdsMap.keySet();
			
			for (String managerEmail : managerEmails) {
				String replacingValue="";
					for (ManagerEmployeeEmailVO managerEmployeeEmailVO : managerEmployeesEmailIdsMap.get(managerEmail)) {
						
						//sending emails to employees with annual review period.
						if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==1){
					replacingValue="- Annual Appraisal "+Calendar.getInstance().get(Calendar.YEAR);
					if(managerEmployeeEmailVO.getEmployeeStatus().equals("READY_FOR_MEETING")){
						if(pendingDays<4){
							
							String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(),mailToCc ,null,managerEmployeeEmailVO.getSubject(),
									"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
						}
						else{
							mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
							"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
							log.info("notification sent");
						}
					}
				}
						//sending emails to employees with half-yearly review period.
						else if(managerEmployeeEmailVO.getPendingDays()==(int)pendingDays && managerEmployeeEmailVO.getAppraisalPeriodTypeId()==2){
						
						replacingValue="- H2"+(Calendar.getInstance().get(Calendar.YEAR));
						
						if(managerEmployeeEmailVO.getEmployeeStatus().equals("READY_FOR_MEETING")){
							
							if(pendingDays<4)
							{
								String[] mailToCc={managerEmployeeEmailVO.getManagerEmailId()};
								mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(),mailToCc ,null,managerEmployeeEmailVO.getSubject(),
										"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
							}
							else{
								mailingService.sendEmailNotification(pmsToolAddress, managerEmployeeEmailVO.getEmployeeEmailId(), null,null,managerEmployeeEmailVO.getSubject(),
										"Dear "+managerEmployeeEmailVO.getEmployeeName()+",\n\n"+managerEmployeeEmailVO.getMessage());
								log.info("notification sent");
							}
						}
					}
				}
			}
		}
	}
}