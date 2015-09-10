package com.ggk.hrms.review.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.dao.SharedAppraiserDAO;

@Repository("sharedAppraiserDAO")
@Transactional
public class SharedAppraiserDAOImpl implements SharedAppraiserDAO{

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void create(SharedAppraiser sharedAppraiser) {
		entityManager.persist(sharedAppraiser);
	}

	@Override
	public SharedAppraiser getCurrentSharedAppraiser(int reviewHeaderId) {
		SharedAppraiser sharedAppraiser=null;
		Query query=entityManager.createQuery("select sh from SharedAppraiser as sh where sh.reviewHeader.reviewHeaderId=:reviewHeaderId and IsActive=true");
		query.setParameter( "reviewHeaderId", reviewHeaderId);
		try{
		sharedAppraiser=(SharedAppraiser) query.getSingleResult();
		return sharedAppraiser;}
		catch(Exception e){
			return null;
		}
	}

	@Override
	public SharedAppraiser update(SharedAppraiser existingSharedAppraiser) {
		return entityManager.merge(existingSharedAppraiser);	
	}
}
