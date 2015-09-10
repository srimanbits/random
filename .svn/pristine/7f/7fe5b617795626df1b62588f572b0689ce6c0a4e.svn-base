package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ONOMinutes;
import com.ggk.hrms.review.dao.ONOMinutesDao;

@Repository("ONOMinitesDAO")
public class ONOMinitesDaoImpl implements ONOMinutesDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource(name="onoInterval")
	private Long interval=2L;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ONOMinutes> getONOMinutes(int empid, Long current){
		
		Query query = entityManager
				.createNativeQuery("select ONOMinutesId,AppraiseId,AppraiserId,MeetingMinutes,CreatedDate,LastUpdatedDate,CreatedId,LastUpdatedId,IsApproved from "
						+ "(select m.ONOMinutesId,m.AppraiseId,m.AppraiserId,m.MeetingMinutes,m.CreatedDate,m.LastUpdatedDate,m.CreatedId,m.LastUpdatedId,m.IsApproved,ROW_NUMBER() OVER ( order by m.createdDate desc) rownum  "
						+ "from Review.ONOMinutes m where AppraiseId=:empid) as sub where sub.rownum between :lowerLimit and :upperLimit",ONOMinutes.class);
		query.setParameter("empid", empid);
		query.setParameter("lowerLimit", current+1);
		query.setParameter("upperLimit", current+interval);
		return (List<ONOMinutes>)query.getResultList();
	}

	@Override
	@Transactional
	public void save(ONOMinutes minutes) {
		entityManager.merge(minutes);
		
	}

	@Override
	public ONOMinutes find(int id) {
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		ONOMinutes onoMinutes=null;
		List<ONOMinutes> result= entityManager.createQuery("from ONOMinutes as minutes where minutes.minutesId=:id and minutes.isApproved=0").setParameter("id", id).getResultList();
		if(result.size()>0)
			onoMinutes=result.get(0);
		return onoMinutes;  
	}

	@Override
	public Long count(int empid) {
		Query query = entityManager.createQuery("select count(*) from ONOMinutes where AppraiseId=:empid");
		query.setParameter("empid", empid);
		return (Long)query.getSingleResult();
	}

}
