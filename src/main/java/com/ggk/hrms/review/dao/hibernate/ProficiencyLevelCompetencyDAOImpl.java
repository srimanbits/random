package com.ggk.hrms.review.dao.hibernate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.ProficiencyLevelCompetency;
import com.ggk.hrms.review.dao.ProficiencyLevelCompetencyDAO;

@Repository("proficiencyLevelCompetencyDAO")
public class ProficiencyLevelCompetencyDAOImpl implements ProficiencyLevelCompetencyDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public String getBehavioralIndicator(int competencyId,
			int proficiencyLevelId) {
		ProficiencyLevelCompetency proficiencyLevelCompetency = get(competencyId, proficiencyLevelId);
		if(proficiencyLevelCompetency == null){
			return "";
		}
		return proficiencyLevelCompetency.getBehaviorIndicator();
	}
	
	private ProficiencyLevelCompetency get(int competencyId , int proficiencyLevelId) {
		String hql = new String("select PLC From ProficiencyLevelCompetency PLC Where PLC.competency.competencyId=:competencyId and PLC.proficiencyLevel.proficiencyLevelId=:proficiencyLevelId");

		Query query = entityManager.createQuery(hql);
		query.setParameter("competencyId", competencyId);
		query.setParameter("proficiencyLevelId", proficiencyLevelId);
		query.setHint("org.hibernate.cacheable", true);
		query.setHint("setCacheRegion", "QueryCache");
		ProficiencyLevelCompetency proficiencyLevelCompetency=(ProficiencyLevelCompetency) query.getResultList().get(0);
		
		
		
		
		/*Session session = (Session) entityManager.getDelegate();
		Criteria criteria = session
				.createCriteria(ProficiencyLevelCompetency.class)
				.add(Restrictions.eq("proficiencyLevel.proficiencyLevelId",
						proficiencyLevelId))
				.add(Restrictions.eq("competency.competencyId",
						proficiencyLevelId));
		criteria.setCacheable(true);
		criteria.setCacheRegion("QueryCache");
		ProficiencyLevelCompetency proficiencyLevelCompetency = (ProficiencyLevelCompetency) criteria
				.uniqueResult();*/

		return proficiencyLevelCompetency;
	}

}
