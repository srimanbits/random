package com.ggk.hrms.review.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.dao.ReviewFormDAO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

@Repository("reviewFormDao")
@Transactional
public class ReviewFormDAOImpl implements ReviewFormDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String validateAppraisal(int reviewHeaderId,ReviewFormInfoVO reviewFormInfoVO) {
		Query query=entityManager.createNativeQuery("{call Review.validateAppraisalMandatoryFields(:reviewHeaderId,:reviewPhase,:reviewFormRole)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE1
				|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE1) {

			query.setParameter("reviewPhase", "phase1");
		} else {
			query.setParameter("reviewPhase", "phase2");
		}
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
			query.setParameter("reviewFormRole",
					ReviewFormRole.APPRAISE.getDescription());
		} else {
			query.setParameter("reviewFormRole",
					ReviewFormRole.APPRAISER.getDescription());

		}
		return (String)query.getSingleResult();
	}

}
