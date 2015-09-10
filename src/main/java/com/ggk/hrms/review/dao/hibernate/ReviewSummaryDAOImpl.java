package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.dao.ReviewSummaryDAO;

@Repository("reviewSummaryDAO")
@Transactional
public class ReviewSummaryDAOImpl implements ReviewSummaryDAO {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public ReviewSummary getReviewSummary(int reviewHeaderId,
			ReviewFormRole reviewFormRole) {

		String hql = "select RS from ReviewSummary RS where RS.owner=:owner and RS.reviewHeader.reviewHeaderId=:reviewHeaderId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("owner", reviewFormRole);
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(reviewFormRole==ReviewFormRole.APPRAISE){
			
			query.setParameter("owner", ReviewFormRole.APPRAISE);
		}
		else {
			
			query.setParameter("owner", ReviewFormRole.APPRAISER);
		}
		try {
			return (ReviewSummary) query.getSingleResult();
		}

		catch (NoResultException e) {

			return null;
		}
	}

	@Override
	public ReviewSummary saveReviewSummary(ReviewSummary reviewSummary) {
		return (ReviewSummary) entityManager.merge(reviewSummary);
	}

	@Override
	public void copyReviewSummary(int reviewHeaderId,
			ReviewFormRole owner, String actionType) {
		Query query=entityManager.createNativeQuery("{call Review.CopyReviewSummary(:reviewHeaderId,:target,:actionType)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("actionType", actionType);
		if(owner==ReviewFormRole.APPRAISE){
			
			query.setParameter("target", "APPRAISER");
		}
		else {
			
			query.setParameter("target", "APPRAISE");
		}
		query.executeUpdate();
		
	}
	
	@Override
	public ReviewSummary saveSuperUserReviewSummary(int reviewHeaderId,
			Rating appraiserRating) {
		
		String hql = "select RS from ReviewSummary RS where RS.reviewHeader.reviewHeaderId=:reviewHeaderId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("reviewHeaderId", reviewHeaderId);
		List<ReviewSummary> reviewSummaryList = query.getResultList();
		ReviewSummary returnReviewSummary = null;
		for(ReviewSummary reviewSummary : reviewSummaryList){
			reviewSummary.setAppraiserRating(appraiserRating);
			returnReviewSummary = (ReviewSummary)entityManager.merge(reviewSummary);
	    }
		return returnReviewSummary;
	}
}
