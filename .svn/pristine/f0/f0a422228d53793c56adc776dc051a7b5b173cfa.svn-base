package com.ggk.hrms.review.task;

import com.ggk.hrms.review.service.MailingService;

public class AppraisalSummaryEmailTask implements Runnable {

	String employeeEmailId;
	String[] managerEmailId;
	String subject;
	String text;
	
	
	MailingService mailingService;
	public AppraisalSummaryEmailTask(MailingService mailingService,
			String employeeEmailId, String[] managerEmailId, String subject,
			String text) {
		super();
		this.mailingService = mailingService;
		this.employeeEmailId = employeeEmailId;
		this.managerEmailId = managerEmailId;
		this.subject = subject;
		this.text = text;
	}


	@Override
	public void run() {
		mailingService.sendEmailNotification(null, employeeEmailId, managerEmailId, null, subject,
				text);

	}

}
