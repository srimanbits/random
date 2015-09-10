package com.ggk.hrms.review.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.Project;
import com.ggk.hrms.review.ui.vo.ProjectVO;

public interface ProjectService {
	
	List<ProjectVO> getAllProjects();
	Map<Integer, String>  getProjectsDropDown();
	Map<Integer, String>  getProjectsDropDown(Date reviewStartDate,Date reviewEndDate);
	 Project getProject(int projectId);
}
