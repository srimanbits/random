package com.ggk.hrms.review.task;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.service.MailingService;

public class AppraisalOperationNotificationTask implements Runnable {


	MailingService mailingService;
	
	String opsType;
	
	Employee employee;
	
	Employee mainAppraiser;
	
	String reviewCycleName;
	
	String currentReviewStatusCode;
	
	public AppraisalOperationNotificationTask(MailingService mailingService, String opsType,
			Employee employee, Employee mainAppraiser, String reviewCycleName,
			String currentReviewStatusCode) {
		super();
		this.mailingService = mailingService;
		this.opsType = opsType;
		this.employee = employee;
		this.mainAppraiser = mainAppraiser;
		this.reviewCycleName = reviewCycleName;
		this.currentReviewStatusCode = currentReviewStatusCode;
	}

	@Override
	public void run() {
		mailingService.sendNotification(opsType, employee, mainAppraiser, reviewCycleName, currentReviewStatusCode);
	}
}
