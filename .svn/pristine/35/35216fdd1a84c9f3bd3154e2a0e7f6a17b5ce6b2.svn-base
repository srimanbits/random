package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DefaultObjective;
import com.ggk.hrms.review.ui.vo.DefaultObjectiveVO;
import com.ggk.hrms.review.ui.vo.DropDownVO;

public interface DefaultObjectiveService {
	
	List<DefaultObjectiveVO> getDefaultObjectives(int empId,int reviewCycleId);
	DefaultObjective getDefaultObjective(int defaultObjId);
	List<DropDownVO> getDefaultObjDropDown(int managerId, int reviewCycleId,
			int gradeId);
	public List<DefaultObjective> getDefaultObjectivesByRCIdAndEmpId(int reviewCycleId,
			int managerEmployeeId);

}
