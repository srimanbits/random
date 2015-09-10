package com.ggk.hrms.review.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.dao.FeedbackQuestionDAO;


@Repository("feedbackQuestionDAO")
@Transactional
public class FeedbackQuestionDAOImpl implements FeedbackQuestionDAO{

	@PersistenceContext
	private EntityManager entityManager;

		@Override
	public FeedbackQuestion getFeedbackQuestionById(int feedbackQuestionId) {
		return entityManager.find(FeedbackQuestion.class, feedbackQuestionId);
	}

}
