package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReminderMessages;
import com.ggk.hrms.review.ui.vo.ReminderMessagesVO;

public interface ReminderMessagesService {

	public List<ReminderMessagesVO> getReminderMessagesForHalfYearly(String reviewPeriod, String appraisalStage);
	
	public ReminderMessagesVO getReminderMessageDetailsById(int messageId);
	
	public int updateReminderMessage(int messageId, int remainingDays, String reminderMessage, String subject);

	public int deleteReminderMessageById(int messageId);

	public boolean insertReminderMessage(ReminderMessages reminderMessages);
	
	public boolean isReminderMessageAlreadyPresent(int daysRemaining, String reviewCycleStage, String reviewPeriod);
}
