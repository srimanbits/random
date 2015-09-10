package com.ggk.hrms.review.task;

import org.apache.log4j.Logger;

import com.ggk.hrms.review.service.MailingService;

public class FeedbackSharingNotificationTask implements Runnable {

	Logger log = Logger.getLogger(FeedbackSharingNotificationTask.class);
	
	MailingService mailingService;
	
	String sendTo;
	
	String sendFrom;
	
	String subject;
	
	String text;
	
	String reviewCycleName;
	
	String type;
	
	public FeedbackSharingNotificationTask(MailingService mailingService,
			String sendTo, String sendFrom, String subject, String text,
			String reviewCycleName, String type) {
		super();
		this.mailingService = mailingService;
		this.sendTo = sendTo;
		this.sendFrom = sendFrom;
		this.subject = subject;
		this.text = text;
		this.reviewCycleName = reviewCycleName;
		this.type = type;
	}

	@Override
	public void run() {
		if(type.equals("Feedback")) {
			String sendCC[] = null;
			if(sendFrom != null && sendFrom.equals("")) {
				sendCC = new String[0];
				sendCC[0] = sendFrom;
			}
			//mailingService.sendFeedBackNotification(sendTo, sendFrom, subject, text, reviewCycleName);
			// single method
			if(mailingService.sendEmailNotification(null, sendTo, sendCC, null, subject + " (" + reviewCycleName + ")", text)){
				log.info("Feedback notification sent");
			}
		} else {
			//mailingService.sendSharingNotification(sendTo, subject, text, reviewCycleName);
			// single method
			if(mailingService.sendEmailNotification(null, sendTo, null, null, subject + " (" + reviewCycleName + ")", text)){
				log.info("Sharing notification sent");
			}
		}
		
	}
}
