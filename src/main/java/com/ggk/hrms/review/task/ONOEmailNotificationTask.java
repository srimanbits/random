package com.ggk.hrms.review.task;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.service.MailingService;

public class ONOEmailNotificationTask implements Runnable {

	MailingService mailingService;
	
	String message;
	
	Employee appraiserEmployee;
	
	Employee appraiseEmployee;
	
	String comments;
	
	boolean isApproved;

	

	public ONOEmailNotificationTask(MailingService mailingService,
			String message, Employee appraiseEmployee,
			Employee appraiserEmployee, String comments, boolean isApproved) {
		super();
		this.mailingService = mailingService;
		this.message = message;
		this.appraiseEmployee = appraiseEmployee;
		this.appraiserEmployee = appraiserEmployee;
		this.comments = comments;
		this.isApproved = isApproved;
	}



	@Override
	public void run() {
		
		mailingService.sendONOEmailNotification(message,
				appraiseEmployee, appraiserEmployee, comments,
				isApproved);
		
	}
	
	
}
