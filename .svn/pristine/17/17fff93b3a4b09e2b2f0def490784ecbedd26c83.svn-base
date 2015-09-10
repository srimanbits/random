package com.ggk.hrms.review.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.service.MailingService;

@Service("mailingService")
public class MailingServiceImpl implements MailingService {
	
	Logger log = Logger.getLogger(MailingServiceImpl.class);
	
	@Resource
    private JavaMailSender mailSender;
	
	@Resource
	private String hrmsUrl;
	
	@Resource
	private String appraisalSubmitText;
	
	@Resource
	private String appraisalReassignedText;
	
	@Resource
	private String reviewAlias;
	
	@Resource
	private String pmsToolAddress;
	
	@Override
	public boolean sendNotification(String opsType, Employee employee,
			Employee mainAppraiserEmployee, String reviewCycleName, String currentReviewStatusCode) {
		
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage(); 
		if(opsType.equals("submit_to_appraiser")) {
			
			if(currentReviewStatusCode.equals("GOALS_FINALIZED")) {
				// Sending mail to Main Appraiser
				mailMessage.setTo(mainAppraiserEmployee.getEmail());
				mailMessage.setSubject(employee.getDisplayName() + " has resubmitted " + reviewCycleName + " Appraisal");
				mailMessage.setText("Hi " + mainAppraiserEmployee.getDisplayName() + ",\n\n"+employee.getDisplayName() + " has changed the goals for " + reviewCycleName + " after your approval. Please provide your feedback \n" + hrmsUrl);
				try {
					mailSender.send(mailMessage);
					isMailSent = true;
					log.info("Notified to appraiser");
				} catch(Exception e) {
					isMailSent = false;
					log.error("Exception while sending notification to appraiser", e);
				}
			} else {
				// Sending mail to Main Appraiser
				mailMessage.setTo(mainAppraiserEmployee.getEmail());
				mailMessage.setSubject(employee.getDisplayName() + " has submitted " + reviewCycleName + " Appraisal");
				mailMessage.setText("Hi " + mainAppraiserEmployee.getDisplayName() + "," + appraisalSubmitText + hrmsUrl);
				try {
					mailSender.send(mailMessage);
					isMailSent = true;
					log.info("Notified to appraiser");
				} catch(Exception e) {
					isMailSent = false;
					log.error("Exception while sending notification to appraiser", e);
				}
			}
		} else if(opsType.equals("resubmit_to_appraise")) {
			// Sending Mail to Appraise 
			mailMessage.setTo(employee.getEmail());
			mailMessage.setSubject(mainAppraiserEmployee.getDisplayName() + " reassigned " + reviewCycleName + " appraisal to you");
			mailMessage.setText("Hi " + employee.getDisplayName() + "," + appraisalReassignedText + hrmsUrl);
			try {
				mailSender.send(mailMessage);
				isMailSent = true;
				log.info("Notified to appraise");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending notification to appraise", e);
			}
		} else if(opsType.equals("approve_goals")) {
			// Sending Mail to Appraise 
			mailMessage.setTo(employee.getEmail());
			mailMessage.setSubject(mainAppraiserEmployee.getDisplayName() + " has approved your goals for " + reviewCycleName);
			mailMessage.setText("Hi " + employee.getDisplayName() +",\n\n" + mainAppraiserEmployee.getDisplayName() + " has approved your goals for " + reviewCycleName + ". Please accept them.\n" + hrmsUrl);
			try {
				mailSender.send(mailMessage);
				isMailSent = true;
				log.info("Notified to appraise");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending notification to appraise", e);
			}
		} else if(opsType.equals("approve_appraisal")) {

			mailMessage.setTo(employee.getEmail());
			mailMessage.setSubject(mainAppraiserEmployee.getDisplayName() + " has approved your appraisal for " + reviewCycleName);
			mailMessage.setText("Hi " + employee.getDisplayName() +",\n\n" + mainAppraiserEmployee.getDisplayName() + " is ready for meeting with you" + hrmsUrl);
			try {
				mailSender.send(mailMessage);
				isMailSent = true;
				log.info("Notified to appraise");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending notification to appraise", e);
			}
		} else { //opsType.equals("accept_appraisal")
			// Sending mail to review@ggktech.com value was set in properties file
			mailMessage.setTo(reviewAlias);
			String sendCC[] = {employee.getEmail(), mainAppraiserEmployee.getEmail()};
			mailMessage.setCc(sendCC);
			mailMessage.setSubject(employee.getDisplayName() + " completed appraisal for " + reviewCycleName);
			mailMessage.setText("Hi, \n\n"+employee.getDisplayName() + " has completed appraisal for " + reviewCycleName +".\n"+ hrmsUrl);
			try {
				mailSender.send(mailMessage);
				isMailSent = true;
				log.info("Notified to appraise");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending notification to review alias", e);
			}
		}
		return isMailSent;
	}
	
	// to send the appraisal pdf in attachment to the appraise
	public boolean sendAppraisalPDF(String sendTo, String sendCC[], String subject, String text, String reviewCycleName, File appraisalFormPdf) {
		boolean isMailSent = false;
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(sendTo);
			if(sendCC != null && sendCC.length > 0) {
				helper.setCc(sendCC);
			}
			helper.setSubject(subject);
			helper.setText(text);
			//helper.setText(text +"\n"+hrmsUrl);
			FileSystemResource fileResource = new FileSystemResource(appraisalFormPdf);
			helper.addAttachment(fileResource.getFilename(), fileResource);
		} catch (MessagingException e) {
			//throw new MailParseException(e);
			log.error("Exception while preparing appraisal form PDF mail", e);
		}	
		try {
			mailSender.send(message);
			log.info("Mail sent with appraisal form pdf attachment");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending appraisal form pdf attachment notification", e);
		}
		return isMailSent;
	}
	
	public boolean sendEmailNotification(String sendFrom, String sendTo, String sendCC[], String sendBcc[], String subject, String text) {
		boolean isMailSent = false;
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			if(sendFrom != null && !sendFrom.isEmpty()) {
				try {
					helper.setFrom(new InternetAddress(pmsToolAddress, sendFrom));
				} catch (UnsupportedEncodingException e) {
					log.error("Exception while encoding from email address", e);
				}
			}
			helper.setTo(sendTo);
			if(sendCC != null && sendCC.length > 0) {
				helper.setCc(sendCC);
			}
			if(sendBcc != null && sendBcc.length > 0) {
				helper.setBcc(sendBcc);
			}
			helper.setSubject(subject);
			helper.setText(text);
//			helper.setText(text +"\n"+hrmsUrl);
		} catch(MessagingException me) {
			log.error("Exception while preparing mail Message", me);
		}		
		try {
			mailSender.send(message);
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending Email Notification", e);
		}
		return isMailSent;
	}
	
	public boolean sendONOEmailNotification(String opsType, Employee employee,
			Employee mainAppraiserEmployee, String appraisalMsg, boolean isApproved){
		boolean isMailSent=false;
		SimpleMailMessage message=new SimpleMailMessage();
		
		if(opsType.equals("message_to_appraise")){
			if(isApproved==true) {
				// Sending mail to Main Appraiser
				message.setTo(employee.getEmail());
				//message.setCc(mainAppraiserEmployee.getEmail());
				message.setSubject(mainAppraiserEmployee.getDisplayName() + " has approved OneOnOne Minutes for this Month");
				message.setText("Hi, \n\n"+mainAppraiserEmployee.getDisplayName() + " has approved OneonOne Minutes for this Month with following Message. \n\n" +appraisalMsg +" \n\n");
				try {
					mailSender.send(message);
					isMailSent = true;
					log.info("OneOnOne mail to appraise");
				} catch(Exception e) {
					isMailSent = false;
					log.error("Exception while sending OneOnOne mail to appraise", e);
				}
			}else{
				message.setTo(employee.getEmail());
				//message.setCc(mainAppraiserEmployee.getEmail());
				message.setSubject(mainAppraiserEmployee.getDisplayName() + " has rejected OneOnOne Minutes for this Month");
				message.setText("Hi, \n\n"+mainAppraiserEmployee.getDisplayName() + " has rejected OneonOne Minutes for this Month with following Message. \n\n" +appraisalMsg +" \n\n");
				try {
					mailSender.send(message);
					isMailSent = true;
					log.info("OneOnOne mail to appraise");
				} catch(Exception e) {
					isMailSent = false;
					log.error("Exception while sending OneOnOne mail to appraise", e);
				}
			}
		} else if(opsType.equals("ONO_Started")){
			message.setTo(mainAppraiserEmployee.getEmail());
			//message.setCc(employee.getEmail());
			message.setSubject(employee.getDisplayName() + " has added OneOnOne Minutes for this Month");
			message.setText("Hi, \n\n"+employee.getDisplayName() + " has added OneonOne Minutes for this Month with following Message. \n\n" +appraisalMsg +" \n\n");
			try {
				mailSender.send(message);
				isMailSent = true;
				log.info("OneOnOne mail to appraiser");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending OneOnOne mail to appraiser", e);
			}
		}else{
			message.setTo(mainAppraiserEmployee.getEmail());
			//message.setCc(employee.getEmail());
			message.setSubject(employee.getDisplayName() + " has updated OneOnOne Minutes for this Month");
			message.setText("Hi, \n\n"+employee.getDisplayName() + " has updated OneonOne Minutes for this Month with following Message. \n\n" +appraisalMsg+" \n\n");
			try {
				mailSender.send(message);
				isMailSent = true;
				log.info("OneOnOne mail to appraiser");
			} catch(Exception e) {
				isMailSent = false;
				log.error("Exception while sending OneOnOne mail to appraiser", e);
			}
		}
		
		
		return isMailSent;
	}
	
	public boolean sendEmailNotificationWithHtml(String sendFrom, String sendTo, String sendCC[], String sendBcc[], String subject, String text) {
		boolean isMailSent = false;
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			if(sendFrom != null && !sendFrom.isEmpty()) {
				try {
					helper.setFrom(new InternetAddress(pmsToolAddress, sendFrom));
				} catch (UnsupportedEncodingException e) {
					log.error("Exception while encoding from email address", e);
				}
			}
			helper.setTo(sendTo);
			if(sendCC != null && sendCC.length > 0) {
				helper.setCc(sendCC);
			}
			if(sendBcc != null && sendBcc.length > 0) {
				helper.setBcc(sendBcc);
			}
			helper.setSubject(subject);
			helper.setText(text,true);
//			helper.setText(text +"\n"+hrmsUrl);
		} catch(MessagingException me) {
			log.error("Exception while preparing mail Message", me);
		}		
		try {
			mailSender.send(message);
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending Email Notification", e);
		}
		return isMailSent;
	}

	/*public boolean sendEmailNotification(String sendTo, String sendCC[], String sendBcc[], String subject, String text) {
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("TejaGogula");
		mailMessage.setTo(sendTo);
		if(sendCC != null) {
			mailMessage.setCc(sendCC);
		}
		if(sendBcc != null) {
			mailMessage.setBcc(sendBcc);
		}
		mailMessage.setSubject(subject);
		mailMessage.setText(text +"\n"+hrmsUrl);
		try {
			mailSender.send(mailMessage);
			log.info("notification sent!");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending Email Notification", e);
		}
		return isMailSent;
	}*/

	// can remove
	/*public boolean sendFeedBackNotification(String sendTo, String sendFrom, String subject, String text, String reviewCycleName) {
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sendTo);
		mailMessage.setSubject(subject+" ("+reviewCycleName+")");
		mailMessage.setText(text +"\n"+hrmsUrl);
		if(sendFrom!= null && sendFrom.equals("")) {
			mailMessage.setCc(sendFrom);
		}
		try {
			mailSender.send(mailMessage);
			log.info("Feedback notification sent");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending feedback notification", e);
		}
		return isMailSent;
	}*/
	
	// can remove
	/*public boolean sendReviewReminderNotificationToEmployees(String sendTo, String sendCC, String subject, String text) {
		
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sendTo);
		mailMessage.setSubject(subject);
		mailMessage.setText(text +"\n"+hrmsUrl);
		if(sendCC!= null && !sendCC.equals("")) {
			mailMessage.setCc(sendCC);
			log.info("mail sent as cc to :"+ sendCC);
		}
		try {
			mailSender.send(mailMessage);
			log.info("Reminder notification sent  to appraise");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending reminder notification to appraise", e);
		}
		return isMailSent;
	}*/
	
	// can remove
	/*public boolean sendReviewReminderNotificationToManager(String sendTo, String sendCC[], String subject, String text) {
		
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sendTo);
		mailMessage.setSubject(subject);
		mailMessage.setText(text +".\n"+hrmsUrl+".");
		try {
			mailSender.send(mailMessage);
			log.info("Reminder notification sent to appraiser");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending reminder notification to appraiser", e);
		}
		return isMailSent;
	}*/
	
	// can remove
	/*public boolean sendSharingNotification(String sendTo, String subject, String text, String reviewCycleName) {
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sendTo);
		mailMessage.setSubject(subject+" ("+reviewCycleName+")");
		mailMessage.setText(text +"\n"+hrmsUrl);
		try {
			mailSender.send(mailMessage);
			log.info("Sharing notification sent");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending sharing notification", e);
		}
		return isMailSent;
	}*/

	// can remove
	/*public boolean sendCustomizedEmail(String sendTo, String[] sendBcc, String subject, String text) {
		boolean isMailSent = false;
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(sendTo);
		mailMessage.setBcc(sendBcc);
		mailMessage.setSubject(subject);
		mailMessage.setText(text +"\n"+hrmsUrl);
		try {
			mailSender.send(mailMessage);
			log.info("Email Notification Sent");
			isMailSent = true;
		} catch(Exception e) {
			isMailSent = false;
			log.error("Exception while sending Email Notification", e);
		}
		return isMailSent;
	}*/
}
