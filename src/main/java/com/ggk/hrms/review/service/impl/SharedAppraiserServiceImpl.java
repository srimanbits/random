package com.ggk.hrms.review.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.dao.EmployeeDao;
import com.ggk.hrms.review.dao.ReviewHeaderDAO;
import com.ggk.hrms.review.dao.SharedAppraiserDAO;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.service.SharedAppraiserService;
import com.ggk.hrms.review.task.FeedbackSharingNotificationTask;

@Service("sharedAppraiserService")
public class SharedAppraiserServiceImpl implements SharedAppraiserService{
	
	
	@Resource
	private SharedAppraiserDAO sharedAppraiserDAO;
	
	/*@Resource
	private EmployeeDao employeeDao;*/
	
	/*@Resource
	private ReviewHeaderDAO reviewHeaderDAO;*/
	
	@Resource
	private MailingService mailingService;
	
	@Resource
	private ReviewCycleService reviewCycleService;
	
	@Resource
	private ReviewHeaderService reviewHeaderService;
	
	@Resource
	private EmployeeService employeeService;

	@Override
	public void shareAppraisalsToEmployee(List<Integer> appraisalList,
			int targetEmployeeId, int assignedByemployeeId, int reviewCycleId) {
		StringBuilder sharedAppraisals  = new StringBuilder();
		for(int i=0;i<appraisalList.size();i++) {
			SharedAppraiser existingSharedAppraiser=sharedAppraiserDAO.getCurrentSharedAppraiser(appraisalList.get(i).intValue());
			SharedAppraiser sharedAppraiser=new SharedAppraiser();
			sharedAppraiser.setAssignedByEmployee(employeeService.getEmployeeById(assignedByemployeeId));
			sharedAppraiser.setAssignedToEmployee(employeeService.getEmployeeById(targetEmployeeId));
			sharedAppraiser.setDelegated(false);
			sharedAppraiser.setShared(true);
			sharedAppraiser.setActive(true);
			sharedAppraiser.setReviewHeader(reviewHeaderService.getReviewHeaderById(appraisalList.get(i)));
			if(existingSharedAppraiser!=null){
				existingSharedAppraiser.setDueDate(new Date());
				existingSharedAppraiser.setActive(false);
				sharedAppraiserDAO.update(existingSharedAppraiser);
				ReviewHeader reviewHeader=reviewHeaderService.getReviewHeaderById(appraisalList.get(i));
				reviewHeader.setSharedAppraiserInternalComment(null);
				reviewHeaderService.merge(reviewHeader);
			}
			sharedAppraiserDAO.create(sharedAppraiser);
			ReviewHeader reviewHeader = reviewHeaderService.getReviewHeaderById(appraisalList.get(i).intValue());
			/*if(sharedAppraisals.length() == 0) {
				sharedAppraisals.append(reviewHeader.getEmployee().getDisplayName());
			} else {
				sharedAppraisals.append(", " + reviewHeader.getEmployee().getDisplayName());
			}*/
			sharedAppraisals.append("\n" + reviewHeader.getEmployee().getDisplayName());
		}
		if(sharedAppraisals.length() > 0) {
			String subject = employeeService.getEmployeeById(assignedByemployeeId).getDisplayName() + " shared the appraisal(s) with you.";
			Employee sendToEmployee = employeeService.getEmployeeById(targetEmployeeId);
			String text = "Hi " + sendToEmployee.getDisplayName() + ",\n\n" + employeeService.getEmployeeById(assignedByemployeeId).getDisplayName() + " shared the below appraisal(s): \n" + sharedAppraisals;
			ReviewCycle reviewCycle = reviewCycleService.getReviewCycle(reviewCycleId);
			
			new Thread(new FeedbackSharingNotificationTask(mailingService, sendToEmployee.getEmail(), null, subject, text, reviewCycle.getReviewCycleName(), "Sharing")).start();
			//mailingService.sendSharingNotification(employeeDao.getEmployeeById(targetEmployeeId).getEmail(), subject, text, reviewCycle.getReviewCycleName());
		}
	}

	@Override
	public SharedAppraiser getActiveSharedAppraiser(int reviewHeaderId) {
		return sharedAppraiserDAO.getCurrentSharedAppraiser(reviewHeaderId);
	}

	@Override
	public SharedAppraiser update(SharedAppraiser sharedAppraiser) {
		return sharedAppraiserDAO.update(sharedAppraiser);
	}
	
	

}
