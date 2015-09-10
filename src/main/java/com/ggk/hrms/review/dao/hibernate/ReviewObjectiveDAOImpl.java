package com.ggk.hrms.review.dao.hibernate;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.dao.ReviewObjectiveDAO;

@Repository("reviewObjectiveDAO")
@Transactional
public class ReviewObjectiveDAOImpl implements ReviewObjectiveDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	Logger log = Logger.getLogger(ReviewObjectiveDAOImpl.class);

	@Override
	public void merge(List<ReviewObjective> reviewObjs) {
		for(ReviewObjective reviewObj:reviewObjs){
			entityManager.merge(reviewObj);
		}
	}

	@Override
	public void persist(ReviewObjective reviewObj) {
		entityManager.persist(reviewObj);		
	}

	@Override
	public void remove(int reviewObjId) {
		entityManager.remove(entityManager.find(ReviewObjective.class, reviewObjId));	
	}

	@Override
	public int getNoOfObjectives(int reviewHeaderId, ReviewFormRole owner) {
		
		Query query=entityManager.createNativeQuery("select count(1) from Review.ReviewObjective where ReviewHeaderId = :reviewHeaderId AND Owner = :owner");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner == ReviewFormRole.APPRAISE){
			query.setParameter("owner", ReviewFormRole.APPRAISE.getDescription());
		} else {
			query.setParameter("owner", ReviewFormRole.APPRAISER.getDescription());
		}
		return (Integer)query.getSingleResult();
	}

	@Override
	public ReviewObjective getReviewObjective(int reviewObjectiveId) {
		ReviewObjective reviewObjective=null;
		
		Query query = entityManager
				.createQuery("select ro from ReviewObjective ro where ro.reviewObjectiveId=:reviewObjectiveId");
		query.setParameter("reviewObjectiveId", reviewObjectiveId);
		try{
			reviewObjective=(ReviewObjective)query.getSingleResult();
		}
		catch (NoResultException e){
			log.error(e);
		}
		return reviewObjective;
	}

	@Override
	public int getNoOfUnApprovedObjectives(int reviewHeaderId, ReviewFormRole owner) {
		Query query=entityManager.createNativeQuery("select count(1) from Review.ReviewObjective where ReviewHeaderId=:reviewHeaderId and IsApproved=0 AND Owner = :owner");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner == ReviewFormRole.APPRAISE){
			query.setParameter("owner", ReviewFormRole.APPRAISE.getDescription());
		} else {
			query.setParameter("owner", ReviewFormRole.APPRAISER.getDescription());
		}
		return (Integer)query.getSingleResult();
	}

	@Override
	public List<ReviewObjective> getReviewObjectives(int reviewHeaderId,
			ReviewFormRole owner) {
		Query query = entityManager
				.createQuery("from ReviewObjective ro where ro.reviewHeader.reviewHeaderId=:reviewHeaderId and ro.Owner=:createdBy");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner==ReviewFormRole.APPRAISE){			
			query.setParameter("createdBy", ReviewFormRole.APPRAISE);
		}
		else {			
			query.setParameter("createdBy", ReviewFormRole.APPRAISER);
		}
		return query.getResultList();
	}

	@Override
	public void copyReviewObjectives(int reviewHeaderId, ReviewFormRole owner,
			String actionType) {
		Query query=entityManager.createNativeQuery("{call Review.CopyReviewObjectives(:reviewHeaderId,:target,:actionType)}");
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
}
