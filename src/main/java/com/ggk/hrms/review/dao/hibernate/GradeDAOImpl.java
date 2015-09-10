package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Designation;
import com.ggk.hrms.review.beans.domain.Grade;
import com.ggk.hrms.review.dao.GradeDAO;

@Repository("gradeDAO")
public class GradeDAOImpl implements GradeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Grade getGradeById(int gradeId) {
		Session session=(Session) entityManager.getDelegate();
		return (Grade) session.load(Grade.class, gradeId);
	}

	@Override
	public int getIdByGrade(Grade grade) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Grade> getAllActiveGrades() {

		String hql = "Select G from Grade G";
		Query query = entityManager.createQuery(hql);
		return query.getResultList();

	}
	
	@Override
	public Designation getDesignation(int designationId) {
		Session session=(Session) entityManager.getDelegate();
		return (Designation) session.load(Designation.class, designationId);
	}

}
