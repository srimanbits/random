package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Department;
import com.ggk.hrms.review.dao.DepartmentDAO;

@Repository("departmentDAO")
public class DepartmentDAOImpl implements DepartmentDAO {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Department> getAllDepartments() {
		String hql = "Select D from Department D";
		Query query = entityManager.createQuery(hql);
		return query.getResultList();

	}

}
