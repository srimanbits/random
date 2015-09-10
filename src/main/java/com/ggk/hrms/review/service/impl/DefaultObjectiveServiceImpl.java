package com.ggk.hrms.review.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.DefaultObjective;
import com.ggk.hrms.review.dao.DefaultObjectiveDAO;
import com.ggk.hrms.review.service.DefaultObjectiveService;
import com.ggk.hrms.review.ui.vo.DefaultObjectiveVO;
import com.ggk.hrms.review.ui.vo.DropDownVO;

@Service("defaultObjectiveService")
public class DefaultObjectiveServiceImpl implements DefaultObjectiveService{
	@Resource
	private DefaultObjectiveDAO defaultObjectiveDAO;
	
	@Override
	public List<DefaultObjectiveVO> getDefaultObjectives(int empId,
			int reviewHeaderId) {
		return null;
	}

	@Override
	public List<DropDownVO> getDefaultObjDropDown(int managerId,
			int reviewCycleId, int gradeId) {
		List<DropDownVO> defaultObjDropDown=defaultObjectiveDAO.getDefaultObjDropDown(managerId, reviewCycleId, gradeId);
		return defaultObjDropDown;
	}

	@Override
	public DefaultObjective getDefaultObjective(int defaultObjId) {
		DefaultObjective domainObjective=defaultObjectiveDAO.getDefaultObjective(defaultObjId);		
		return domainObjective;
		
	}
	
	@Override
	 public List<DefaultObjective> getDefaultObjectivesByRCIdAndEmpId(
	   int reviewCycleId, int managerEmployeeId) {
	  return defaultObjectiveDAO.getDefaultObjectivesByRCIdAndEmpId(reviewCycleId, managerEmployeeId);
	 }

}
