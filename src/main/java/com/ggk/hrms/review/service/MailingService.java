package com.ggk.hrms.review.service;

import java.io.File;

import com.ggk.hrms.review.beans.domain.Employee;

public interface MailingService {

	public boolean sendNotification(String opsType, Employee employee,
			Employee mainAppraiserEmployee, String reviewCycleName, String currentReviewStatusCode);

	/*public boolean sendFeedBackNotification(String sendTo, String sendFrom, String subject, String text, String reviewCycleName);
	
	public boolean sendReviewReminderNotificationToEmployees(String sendTo, String sendCC, String subject, String text);
	
	public boolean sendReviewReminderNotificationToManager(String sendTo, String sendCC[], String subject, String text);
	
	public boolean sendSharingNotification(String sendTo, String subject, String text, String reviewCycleName);

	public boolean sendCustomizedEmail(String sendTo, String sendBcc[], String subject, String text);*/
	
	public boolean sendEmailNotification(String from, String sendTo, String sendCC[], String sendBcc[], String subject, String text);
	
	public boolean sendAppraisalPDF(String sendTo, String sendCC[], String subject, String text,
			String reviewCycleName, File appraisalFormPdf);
	
	public boolean sendONOEmailNotification(String opsType, Employee employee,
			Employee mainAppraiserEmployee, String appraisalMsg, boolean isApproved);
	
	public boolean sendEmailNotificationWithHtml(String from, String sendTo, String sendCC[], String sendBcc[], String subject, String text);
	
	/*public boolean sendChangedRatingEmail(String from, String sendTo, String sendCC[], String sendBcc[], String subject, String text);*/
}
