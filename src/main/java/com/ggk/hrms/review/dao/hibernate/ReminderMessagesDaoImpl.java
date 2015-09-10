package com.ggk.hrms.review.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ReminderMessages;
import com.ggk.hrms.review.dao.ReminderMessagesDao;

@Repository("reminderMessagesDao")
@Transactional
public class ReminderMessagesDaoImpl implements ReminderMessagesDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	private  final Logger log = Logger.getLogger(ReminderMessagesDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReminderMessages> getReminderMessagesForHalfYearly(int appraisalPeriodTypeId, String status) {
		
		String hql = "Select reminderMessages from ReminderMessages reminderMessages where reminderMessages.status=:Status and reminderMessages.appraisalPeriodType.appraisalPeriodTypeId=:ReviewPeriod and reminderMessages.isActive=True";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("Status",status);
		query.setParameter("ReviewPeriod", appraisalPeriodTypeId);
		log.info("number of records:" +query.getResultList().size());
		return query.getResultList();
	}
	
	@Override
	public ReminderMessages getReminderMessageDetailsById(int messageId) {
		String hql = "Select reminderMessages from ReminderMessages reminderMessages where reminderMessages.messageId=:messageId and reminderMessages.isActive=True";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("messageId",messageId);
		return (ReminderMessages) query.getSingleResult();
	}
	
	@Override
	public int updateReminderMessage(int messageId, int remainingDays,
			String reminderMessage, String subject) {
		String hql = "update ReminderMessages reminderMessages set reminderMessages.lastUpdatedDate=:lastUpdatedDate, reminderMessages.message=:message, reminderMessages.subject=:subject  where reminderMessages.messageId=:messageId";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("lastUpdatedDate", new Date());
		query.setParameter("message", reminderMessage);
		query.setParameter("subject", subject);
		query.setParameter("messageId", messageId);
		return query.executeUpdate();
	}

	@Override
	public int deleteReminderMessageById(int messageID) {
		String hql = "update ReminderMessages reminderMessages set reminderMessages.isActive=False where reminderMessages.messageId=:messageId";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("messageId", messageID);
		return query.executeUpdate();
	}
	
	@Override
	public ReminderMessages getReminderMessage(int daysRemaining, String reviewCycleStage, int appraisalPeriodTypeId) {
		String hql = "Select reminderMessages from ReminderMessages reminderMessages where reminderMessages.daysRemaining=:daysRemaining and reminderMessages.status=:reviewCycleStage and reminderMessages.appraisalPeriodType.appraisalPeriodTypeId=:reviewPeriod and reminderMessages.isActive=True";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("daysRemaining",daysRemaining);
		query.setParameter("reviewCycleStage",reviewCycleStage);
		query.setParameter("reviewPeriod",appraisalPeriodTypeId);
		try {
			return (ReminderMessages) query.getSingleResult();
		}
		catch (NoResultException noResultException) {
			return null;
		}
	}
	
	@Override
	public boolean insertReminderMessage(ReminderMessages reminderMessages) {
		entityManager.merge(reminderMessages);
		return true;
	}

}
