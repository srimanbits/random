package com.ggk.hrms.review.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Project;
import com.ggk.hrms.review.dao.ProjectDAO;
import com.ggk.hrms.review.service.ProjectService;
import com.ggk.hrms.review.ui.vo.ProjectVO;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService{
	
	private static Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
	
	@Resource
	private ProjectDAO projectDao;

	@Override
	public List<ProjectVO> getAllProjects() {
		return null;
	}

	@Override
	public Map<Integer, String>  getProjectsDropDown(Date reviewStartDate,Date reviewEndDate) {
		logger.debug("Get All Projects DropDown between "+reviewStartDate+"and "+reviewEndDate);
		return projectDao.getProjectsDropDown( reviewStartDate, reviewEndDate);
	}

	@Override
	public Project getProject(int projectId) {
		return projectDao.getProject(projectId);
	}
	
	@Override
	public Map<Integer, String>  getProjectsDropDown() {
		logger.debug("Get All Projects DropDown");
		return projectDao.getProjectsDropDown();
	}

}
