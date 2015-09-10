package com.ggk.hrms.review.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.Project;

public interface ProjectDAO {

	 //List<Project> getAllProjects();
	 Map<Integer, String>  getProjectsDropDown(Date reviewStartDate,Date reviewEndDate);
	 Project getProject(int projectId);
	 Map<Integer, String>  getProjectsDropDown();
	 Map<Integer, Project> getProjects();
}
