package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;
import com.ggk.hrms.review.dao.DefaultObjectiveGradeDAO;
import com.ggk.hrms.review.ui.vo.DropDownVO;

@Repository("defaultObjectiveGradeDAO")
public class DefaultObjectiveGradeDAOImpl implements DefaultObjectiveGradeDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DefaultObjectiveGrade> getDefaultObjectiveGradesByDefaultObjectiveId(
			int defaultObjectiveId) {

		String hql = "select DOG From DefaultObjectiveGrade DOG join DOG.defaultObjective DO where DO.defaultObjectiveId=:defaultObjectiveId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("defaultObjectiveId", defaultObjectiveId);

		List<DefaultObjectiveGrade> defaultObjectiveGrades = query
				.getResultList();
		return defaultObjectiveGrades;
	}

	@Override
	public List<DefaultObjectiveGrade> getDefaultObjDropDown(int managerId,
			int reviewCycleId, int gradeId) {

		String hql = "select DOG From DefaultObjectiveGrade DOG join DOG.defaultObjective DO join DOG.reviewCycle RC join DOG.grade G join DO.managerEmployee E"
				+ " where RC.reviewCycleId=:reviewCycleId and E.empId=:managerId and G.gradeId=:gradeId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("gradeId", gradeId);
		query.setParameter("managerId", managerId);

		List<DefaultObjectiveGrade> defaultObjectiveGrades = query
				.getResultList();

		return defaultObjectiveGrades;
	}

	@Override
	public DefaultObjectiveGrade getDefaultObjectiveGradeById(
			int defaultObjectiveGradeId) {
		String hql = "From DefaultObjectiveGrade DOG  where DOG.defaultObjectiveGradeId=:defaultObjectiveGradeId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("defaultObjectiveGradeId", defaultObjectiveGradeId);

		return (DefaultObjectiveGrade) query.getSingleResult();
	}

}
