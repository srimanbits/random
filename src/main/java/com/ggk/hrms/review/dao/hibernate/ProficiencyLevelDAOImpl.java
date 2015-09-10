package com.ggk.hrms.review.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.ProficiencyLevel;
import com.ggk.hrms.review.dao.ProficiencyLevelDAO;

@Repository("ProficiencyLevelDAO")
public class ProficiencyLevelDAOImpl implements ProficiencyLevelDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public ProficiencyLevel getProficiencyLevelById(Integer proficiencyLevelId) {
		
		
		Session session = (Session) entityManager.getDelegate();
		ProficiencyLevel proficiencyLevel = (ProficiencyLevel) session.load(ProficiencyLevel.class, proficiencyLevelId);
		

		/*ProficiencyLevel proficiencyLevel = null;
		String hql = "From ProficiencyLevel PL where PL.proficiencyLevelId=:proficiencyLevelId";

		Query query = entityManager.createQuery(hql);
		query.setParameter("proficiencyLevelId", proficiencyLevelId);
		query.setHint("org.hibernate.cacheable", true);
		query.setHint("setCacheRegion", "QueryCache");

		try {

			proficiencyLevel = (ProficiencyLevel) query.getSingleResult();
		}

		catch (NoResultException e) {
			return proficiencyLevel;
		}*/

		return proficiencyLevel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getProficiencyLevelDropDown() {

		Query query = entityManager
				.createQuery("select PL from ProficiencyLevel PL where PL.isActive=true order by PL.proficiencyLevelNumber");

		Map<Integer, String> proficiencyLevels = new HashMap<Integer, String>();

		for (ProficiencyLevel proficiencyLevel : (List<ProficiencyLevel>) query
				.getResultList()) {

			proficiencyLevels
					.put(proficiencyLevel.getProficiencyLevelId(), String
							.valueOf(proficiencyLevel
									.getProficiencyLevelNumber())+" - "+proficiencyLevel.getProficiencyLevelName());
		}

		return proficiencyLevels;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ProficiencyLevel> getAllActiveProficiencyLevels() {
		Query query = entityManager
				.createQuery("From ProficiencyLevel PL where PL.isActive=true");
		return query.getResultList();
	}

}
