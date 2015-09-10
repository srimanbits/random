package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ReviewDevelopmentPlanActivity;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.dao.ReviewDevelopmentPlanDAO;

@Repository(value="reviewDevelopmentPlanDAO")
@Transactional
public class ReviewDevelopmentPlanDAOImpl implements ReviewDevelopmentPlanDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	

	@Override
	public ReviewDevelopmentPlanActivity getReviewDevelopmentPlanActivityById(int reviewDevelopmentPlanActivityId) {
		Query query=entityManager.createQuery("select dpa from ReviewDevelopmentPlanActivity dpa where dpa.reviewDevelopmentPlanActivityId=:reviewDevelopmentPlanActivityId");
		query.setParameter("reviewDevelopmentPlanActivityId", reviewDevelopmentPlanActivityId);
		ReviewDevelopmentPlanActivity reviewDevelopmentPlanActivity=(ReviewDevelopmentPlanActivity) query.getSingleResult();
		return reviewDevelopmentPlanActivity;
	}
	
	@Override
	public void remove(int reviewDevelopmentPlanActivityId) {
		entityManager.remove(entityManager.find(ReviewDevelopmentPlanActivity.class, reviewDevelopmentPlanActivityId));
	}

	@Override
	public int getNoOfDevPlans(int reviewHeaderId, ReviewFormRole owner) {
		Query query=entityManager.createNativeQuery("select count(1) from Review.ReviewDevelopmentPlanActivity where ReviewHeaderId = :reviewHeaderId AND Owner = :owner");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner == ReviewFormRole.APPRAISE){
			query.setParameter("owner", ReviewFormRole.APPRAISE.getDescription());
		} else {
			query.setParameter("owner", ReviewFormRole.APPRAISER.getDescription());
		}
		return (Integer)query.getSingleResult();
	}

	@Override
	public int getNoOfUnApprovedDevPlans(int reviewHeaderId, ReviewFormRole owner) {
		Query query=entityManager.createNativeQuery("select count(1) from Review.ReviewDevelopmentPlanActivity where ReviewHeaderId = :reviewHeaderId and IsApproved = 0 AND Owner = :owner");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner == ReviewFormRole.APPRAISE){
			query.setParameter("owner", ReviewFormRole.APPRAISE.getDescription());
		} else {
			query.setParameter("owner", ReviewFormRole.APPRAISER.getDescription());
		}
		return (Integer)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReviewDevelopmentPlanActivity> getReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner) {
		List<ReviewDevelopmentPlanActivity> reviewDevelopmentPlanActivities = null;
		Query query = entityManager.createQuery("from ReviewDevelopmentPlanActivity rdpa where rdpa.reviewHeader.reviewHeaderId=:reviewHeaderId and rdpa.Owner=:createdBy");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner == ReviewFormRole.APPRAISE){			
			query.setParameter("createdBy", ReviewFormRole.APPRAISE);
		}
		else {	
			query.setParameter("createdBy", ReviewFormRole.APPRAISER);
		}
		reviewDevelopmentPlanActivities = query.getResultList();
		return reviewDevelopmentPlanActivities;
	}

	@Override
	public void copyReviewDevelopmentPlanActivities(int reviewHeaderId, ReviewFormRole owner, String actionType) {
		Query query = entityManager.createNativeQuery("{call Review.CopyReviewDevelopmentPlanActivities(:reviewHeaderId,:target,:actionType)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("actionType", actionType);
		if(owner == ReviewFormRole.APPRAISE) {
			query.setParameter("target", "APPRAISER");
		} else {
			query.setParameter("target", "APPRAISE");
		}
		query.executeUpdate();	
	}
}
