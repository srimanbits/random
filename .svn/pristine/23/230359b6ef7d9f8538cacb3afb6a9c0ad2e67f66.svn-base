package com.ggk.hrms.review.dao.hibernate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.dao.RatingDAO;

@Repository("ratingDao")
public class RatingDAOImpl implements RatingDAO{
	
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getRatingsDropDown() {
		Query query = entityManager.createQuery("select r from Rating r where r.isActive=true order by r.rating asc");
		
		Map<Integer, String> ratingDropDown = new LinkedHashMap<Integer, String>();

		for (Rating rating : (List<Rating>) query
				.getResultList()) {

			ratingDropDown
					.put(rating.getRatingId(), String
							.valueOf(rating.getRating())+" - "+rating.getShortDescription());
		}

		return ratingDropDown;
		
	}

	@Override
	public Rating getRatingById(int ratingId) {
		/*Query query = entityManager
				.createQuery("From Rating where ratingId=:ratingId");
		query.setParameter("ratingId", ratingId);
		return (Rating)query.getSingleResult();*/
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		return (Rating)session.load(Rating.class, ratingId);
	}

}
