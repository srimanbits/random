package com.ggk.hrms.review.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Project;
import com.ggk.hrms.review.dao.ProjectDAO;

@Repository("projectDao")
public class ProjectDAOImpl implements ProjectDAO {

	@PersistenceContext
	private EntityManager entityManager;

	/*Not being called anywhere*/
/*	@Override
	public List<Project> getAllProjects() {
		Query query = entityManager.createQuery("from Project");
		return query.getResultList();
	}
*/
	@Override
	public Map<Integer, String> getProjectsDropDown(Date reviewStartDate,
			Date reviewEndDate) {
		Query query = entityManager
				.createQuery("select p from Project p where p.isActive=true and  ((p.startDate between :reviewStartDate and  :reviewEndDate) " +
						"or(p.startDate <:reviewStartDate and  p.endDate between :reviewStartDate and  :reviewEndDate) " +
						"or(p.startDate <:reviewStartDate and p.endDate is null)"+
						"or(p.startDate <:reviewStartDate and p.endDate >:reviewStartDate)) order by upper(p.name)");
		query.setParameter("reviewStartDate", reviewStartDate);
		query.setParameter("reviewEndDate", reviewEndDate);
		Map<Integer, String> projectsDropDown = new LinkedHashMap<Integer, String>();

		for (Project project : (List<Project>) query.getResultList()) {
			projectsDropDown.put(project.getId(),
					String.valueOf(project.getName()));
		}
		return projectsDropDown;
	}

	@Override
	public Project getProject(int projectId) {
		Query query = entityManager
				.createQuery("from Project p where p.id=:projectId");
		query.setParameter("projectId", projectId);
		return (Project) query.getResultList().get(0);
	}

	@Override
	public Map<Integer, String> getProjectsDropDown() {
		Query query = entityManager.createQuery("select p from Project p");
		Map<Integer, String> projectsDropDown = new HashMap<Integer, String>();

		for (Project project : (List<Project>) query.getResultList()) {

			projectsDropDown.put(project.getId(),
					String.valueOf(project.getName()));
		}
		return projectsDropDown;
	}
	
	@Override
	//@Cacheable(value = "employee", key = "'getProjects'")
	public Map<Integer, Project> getProjects() {
		Query query = entityManager.createQuery("select p from Project p");
		Map<Integer, Project> projectsDropDown = new HashMap<Integer, Project>();

		for (Project project : (List<Project>) query.getResultList()) {
			projectsDropDown.put(project.getId(), project);
		}
		return projectsDropDown;
	}
}
