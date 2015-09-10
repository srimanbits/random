package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ReviewActionLog;
import com.ggk.hrms.review.dao.ReviewActionLogDAO;

@Repository("reviewActionLogDAO")
public class ReviewActionLogDAOImpl implements ReviewActionLogDAO {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void saveReviewActionLogs(List<ReviewActionLog> reviewActionLogs) {
		for(ReviewActionLog reviewActionLog:reviewActionLogs){
			entityManager.persist(reviewActionLog);		
		}
	}

	@Override
	public List getReviewLog(int reviewHeaderId,String reviewFormRole,int startIndex, int pageDisplayLength,
			String searchValue,String sortDirection, String colIndex) {
		int sortAsc;
		if(sortDirection.equalsIgnoreCase("asc")){
			sortAsc=1;
		}else{
			sortAsc=0;
		}
		Query query=entityManager.createNativeQuery("{call Review.ReturnCommentText2(:reviewHeaderId,:reviewFormRole,:startIndex,:pageDisplayLength,:searchValue,:sortDirection,:colIndex)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("reviewFormRole", reviewFormRole);
		query.setParameter("startIndex", startIndex);
		query.setParameter("pageDisplayLength", pageDisplayLength);
		query.setParameter("searchValue", searchValue);
		query.setParameter("sortDirection", sortAsc);
		query.setParameter("colIndex", colIndex);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void saveReviewActionLog(ReviewActionLog reviewActionLog) {
		entityManager.persist(reviewActionLog);	
	}
	
	@Override
	public Long getTotalActivityCount(int reviewHeaderId, String reviewFormRole) {
		Query query=entityManager.createNativeQuery("{call Review.ReturnCommentTextCount(:reviewHeaderId,:reviewFormRole) }");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("reviewFormRole", reviewFormRole);
		List<Object> list= query.getResultList();
		Long count=0l;
		if(list.size()!=0)
			count=new Long((Integer)list.get(0));
		return count;
	}

}
