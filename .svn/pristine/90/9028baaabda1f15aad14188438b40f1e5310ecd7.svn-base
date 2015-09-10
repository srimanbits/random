package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.ReminderMessages;
import com.ggk.hrms.review.dao.ReminderMessagesDao;
import com.ggk.hrms.review.service.ReminderMessagesService;
import com.ggk.hrms.review.ui.vo.ReminderMessagesVO;
import com.ggk.hrms.review.utils.AppraisalPeriodTypeEnum;

@Service("reminderMessagesService")
public class ReminderMessagesServiceImpl implements ReminderMessagesService {

	@Resource
	ReminderMessagesDao reminderMessagesDao;
	
	@Override
	public List<ReminderMessagesVO> getReminderMessagesForHalfYearly(String reviewPeriod, String appraisalStage) {
		
		int appraisalPeriodTypeId = 0;
		for (AppraisalPeriodTypeEnum appraisalPeriodEnum : AppraisalPeriodTypeEnum.values()) {
			if (appraisalPeriodEnum.getName().equals(reviewPeriod)) {
				appraisalPeriodTypeId = appraisalPeriodEnum.getId();
			}
		}
		
		List<ReminderMessages> reminderMessages = reminderMessagesDao.getReminderMessagesForHalfYearly(appraisalPeriodTypeId , appraisalStage);
		List<ReminderMessagesVO> reminderMessagesVOs = convertToModel(reminderMessages);
		
		return reminderMessagesVOs;
	}
	
	
	private List<ReminderMessagesVO> convertToModel(
			List<ReminderMessages> reminderMessages) {
		List<ReminderMessagesVO> reminderMessagesVOs = new ArrayList<ReminderMessagesVO>();
		for (ReminderMessages messages : reminderMessages) {
			ReminderMessagesVO reminderMessagesVO = new ReminderMessagesVO();
			reminderMessagesVO.setActive(messages.isActive());
			reminderMessagesVO.setDaysRemaining(messages.getDaysRemaining());
			reminderMessagesVO.setMessage(messages.getMessage());
			reminderMessagesVO.setMessageId(messages.getMessageId());
			reminderMessagesVO.setAppraisalPeriodTypeId(messages.getAppraisalPeriodType().getAppraisalPeriodTypeId());
			for(AppraisalPeriodTypeEnum appraisalPeriodEnum : AppraisalPeriodTypeEnum.values()) {
				if (appraisalPeriodEnum.getId().equals(reminderMessagesVO.getAppraisalPeriodTypeId())) {
					reminderMessagesVO.setReviewPeriod(appraisalPeriodEnum.getName());
				}
			}
			reminderMessagesVO.setStatus(messages.getStatus());
			reminderMessagesVO.setSubject(messages.getSubject());
			reminderMessagesVOs.add(reminderMessagesVO);
		}
		return reminderMessagesVOs;
	}

	private ReminderMessagesVO convertDomainToVO(ReminderMessages reminderMessages) {
		ReminderMessagesVO reminderMessagesVO = new ReminderMessagesVO();
		reminderMessagesVO.setActive(reminderMessages.isActive());
		reminderMessagesVO.setDaysRemaining(reminderMessages.getDaysRemaining());
		reminderMessagesVO.setMessage(reminderMessages.getMessage());
		reminderMessagesVO.setMessageId(reminderMessages.getMessageId());
		reminderMessagesVO.setAppraisalPeriodTypeId(reminderMessages.getAppraisalPeriodType().getAppraisalPeriodTypeId());
		for(AppraisalPeriodTypeEnum appraisalPeriodEnum : AppraisalPeriodTypeEnum.values()) {
			if (appraisalPeriodEnum.getId().equals(reminderMessagesVO.getAppraisalPeriodTypeId())) {
				reminderMessagesVO.setReviewPeriod(appraisalPeriodEnum.getName());
			}
		}
		reminderMessagesVO.setStatus(reminderMessages.getStatus());
		reminderMessagesVO.setSubject(reminderMessages.getSubject());
		return reminderMessagesVO;
	}
	
	@Override
	public ReminderMessagesVO getReminderMessageDetailsById(int messageId) {
		ReminderMessages reminderMessages = reminderMessagesDao.getReminderMessageDetailsById(messageId);
		return convertDomainToVO(reminderMessages);
	}
	
	@Override
	public int updateReminderMessage(int messageId, int remainingDays,
			String reminderMessage, String subject) {
		return reminderMessagesDao.updateReminderMessage(messageId,remainingDays,reminderMessage,subject);
	}

	@Override
	public int deleteReminderMessageById(int messageID) {
		return reminderMessagesDao.deleteReminderMessageById(messageID);
	}


	@Override
	public boolean insertReminderMessage(ReminderMessages reminderMessages) {
		return reminderMessagesDao.insertReminderMessage(reminderMessages);
	}

	@Override
	public boolean isReminderMessageAlreadyPresent(int daysRemaining,
			String reviewCycleStage, String reviewPeriod) {
		int appraisalPeriodTypeId = 0;
		for (AppraisalPeriodTypeEnum appraisalPeriodEnum : AppraisalPeriodTypeEnum.values()) {
			if (appraisalPeriodEnum.getName().equals(reviewPeriod)) {
				appraisalPeriodTypeId = appraisalPeriodEnum.getId();
			}
		}
		ReminderMessages reminderMessages = reminderMessagesDao.getReminderMessage(daysRemaining, reviewCycleStage, appraisalPeriodTypeId);
		if (reminderMessages == null) {
			return false;
		}
		else {
			return true;
		}
	}

}
