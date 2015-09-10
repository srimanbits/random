package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReminderMessages;

public interface ReminderMessagesDao {

	public List<ReminderMessages> getReminderMessagesForHalfYearly(int appraisalPeriodTypeId, String status);
	
	public ReminderMessages getReminderMessageDetailsById(int messageId);
	
	public int updateReminderMessage(int messageId, int remainingDays,
			String reminderMessage, String subject);

	public int deleteReminderMessageById(int messageID);

	public boolean insertReminderMessage(ReminderMessages reminderMessages);
	
	public ReminderMessages getReminderMessage(int daysRemaining, String reviewCycleStage, int appraisalPeriodTypeId);
}
