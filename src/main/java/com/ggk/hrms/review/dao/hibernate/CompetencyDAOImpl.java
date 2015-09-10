package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.Competency;
import com.ggk.hrms.review.dao.CompetencyDAO;

@Repository("competencyDAO")
public class CompetencyDAOImpl implements CompetencyDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Competency getCompetencyById(int competencyId) {

		Competency competency = (Competency) entityManager.find(Competency.class, competencyId);
		return competency;
	}

	@Override
	public List<Competency> getAllActiveCompetencies() {

		String hql = new String("From Competency C Where C.isActive=true");

		Query query = entityManager.createQuery(hql);
		List<Competency> allActiveCompetencies = query.getResultList();
		return allActiveCompetencies;
	}

	@Override
	@Transactional
	public int saveCompetency(Competency competency) {
		competency=entityManager.merge(competency);
		return competency.getCompetencyId();
	}

	@Override
	public List<Competency> getAllCompetencies() {
		String hql = new String("From Competency C");

		Query query = entityManager.createQuery(hql);
		List<Competency> allActiveCompetencies = query.getResultList();
		return allActiveCompetencies;
	}
}
