package com.ggk.hrms.review.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.dao.EmployeeDao;
import com.ggk.hrms.review.ui.vo.EmployeeVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;

@Repository("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<String> getRoles(int empId) {
		Query query = entityManager
				.createQuery("select er.role.roleDescription from EmployeeRole as er where er.employee.empId=:empId");
		query.setParameter("empId", empId);
		return query.getResultList();
	}

	@Override
	public Employee getEmployeeById(int empId) {
		org.hibernate.Session session = (Session) entityManager.getDelegate();
		return (Employee)session.load(Employee.class, empId);
	}

	@Override
	public Employee getEmployee(String email) {
		Query query = entityManager
				.createQuery("from Employee as e where e.email=:email and e.isActive=true");
		query.setParameter("email", email);
		return (Employee) query.getSingleResult();
	}

	public List<Employee> getEmployees(boolean isActive, String parameter,
			Integer reviewCycleId) {

		List<Employee> employees = new ArrayList<Employee>();
		try {
			// Query query =
			// entityManager.createQuery(" from Employee as emp,  where emp.isActive =:isActive And emp.displayName LIKE :parameter order by emp.displayName");
			Query query = null;
			if (reviewCycleId != null) {

				query = entityManager
						.createQuery(" SELECT emp from Employee as emp, ReviewHeader as header"
								+ " WHERE emp.isActive = :isActive"
								+ " AND emp.displayName LIKE :parameter"
								+ " AND header.employee.empId = emp.empId"
								+ " AND header.reviewCycle.reviewCycleId = :reviewCycleId"
								+ " ORDER BY emp.displayName");
				query.setParameter("reviewCycleId", reviewCycleId);
			} else {
				query = entityManager
						.createQuery(" SELECT emp from Employee as emp"
								+ " WHERE emp.isActive = :isActive"
								+ " AND emp.displayName LIKE :parameter"
								+ " ORDER BY emp.displayName");

			}
			query.setParameter("isActive", isActive);
			query.setParameter("parameter", parameter + "%");
			employees = query.getResultList();
			return employees;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Employee> getSubOrdinates(boolean isActive, String parameter,
			int managerId) {

		List<Employee> employees = new ArrayList<Employee>();
		Session session = (Session) entityManager.getDelegate();
		SQLQuery sqlQuery = session
				.createSQLQuery("{call Review.ReturnSubOrdinates(:managerId, :parameter)}");
		sqlQuery.setParameter("managerId", managerId);
		sqlQuery.setParameter("parameter", parameter);
		employees = sqlQuery.setResultTransformer(
				Transformers.aliasToBean(Employee.class)).list();
		return employees;
	}

	@Override
	public Employee getEmployeeByDisplayName(boolean isActive,
			String employeeDisplayName, Integer reviewCycleId) {

		try {
			Query query = null;
			if (reviewCycleId != null) {
				query = entityManager
						.createQuery(" SELECT emp from Employee as emp, ReviewHeader as header"
								+ " WHERE emp.isActive = :isActive"
								+ " And emp.displayName = :employeeDisplayName"
								+ " AND header.employee.empId = emp.empId"
								+ " AND header.reviewCycle.reviewCycleId = :reviewCycleId"
								+ " ORDER BY emp.displayName");
				query.setParameter("reviewCycleId", reviewCycleId);
			} else {
				query = entityManager
						.createQuery(" SELECT emp from Employee as emp"
								+ " WHERE emp.isActive = :isActive"
								+ " AND emp.displayName =:employeeDisplayName"
								+ " ORDER BY emp.displayName");

			}
			query.setParameter("isActive", isActive);
			query.setParameter("employeeDisplayName", employeeDisplayName);
			return (Employee) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<String> getEmployeEmaildsByCommaSeparatedEmpIds(
			String commaSeparatedEmployeeIds) {
		Query query = entityManager
				.createQuery("SELECT emp.email FROM Employee as emp, ReviewHeader as rh WHERE emp.empId = rh.employee.empId AND rh.reviewHeaderId IN ("
						+ commaSeparatedEmployeeIds + ")");
		// query.setParameter("commaSeparatedEmployeeIds",
		// commaSeparatedEmployeeIds);
		return query.getResultList();
	}

	@Override
	public List<EmployeeVO> getEmployeeGradeHistoryById(int empId) {
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery("{call Review.ReturnGradeHistory(:empId)}").setResultTransformer(Transformers.aliasToBean(EmployeeVO.class));
		query.setParameter("empId", empId);
		return query.list();
		
	}

	@Override
	public List<EmployeeVO> getEmployeedesignationHistoryById(int empId) {
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery("{call Review.ReturnDesignationHistory(:empId)}").setResultTransformer(Transformers.aliasToBean(EmployeeVO.class));
		query.setParameter("empId", empId);
		return query.list();
	}
}
