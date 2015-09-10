package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.DefaultObjective;
import com.ggk.hrms.review.dao.DefaultObjectiveDAO;
import com.ggk.hrms.review.ui.vo.DefaultObjectiveVO;
import com.ggk.hrms.review.ui.vo.DropDownVO;

@Repository("defaultObjectiveDao")
public class DefaultObjectiveDAOImpl implements DefaultObjectiveDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<DropDownVO> getDefaultObjDropDown(int managerId,
			int reviewCycleId, int gradeId) {
		Query query = entityManager
				.createQuery("select new com.ggk.hrms.review.ui.vo.DropDownVO(do.defaultObjectiveId,do.objectiveName) from DefaultObjective do "
						+ "where do.managerEmployee.empId=:managerId and do.reviewCycle.reviewCycleId=:reviewCycleId and do.grade.gradeId=:gradeId");
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("gradeId", gradeId);
		query.setParameter("managerId", managerId);
		List<DropDownVO> defaultObjDropDown = query.getResultList();
		return defaultObjDropDown;
	}

	@Override
	public List<DefaultObjectiveVO> getDefaultObjectives(int empId,
			int reviewHeaderId) {
		return null;
	}

	@Override
	public DefaultObjective getDefaultObjective(int defaultObjId) {
		Query query = entityManager.createQuery("from DefaultObjective do where do.defaultObjectiveId=:defaultObjId");
		query.setParameter("defaultObjId", defaultObjId);
		List<DefaultObjective> defaultObjs = query.getResultList();
		return defaultObjs.get(0);
	}
	
	@Override
	public List<DefaultObjective> getDefaultObjectivesByRCIdAndEmpId(int reviewCycleId,
			int managerEmployeeId) {
		String hql = "select DO From DefaultObjective  DO join DO.managerEmployee E "
				+ "where E.empId=:managerEmployeeId";
		Query query = entityManager.createQuery(hql);
		query.setParameter("managerEmployeeId", managerEmployeeId);
		List<DefaultObjective> defaultObjectives = query.getResultList();
		return defaultObjectives;
	}
	
	
}
