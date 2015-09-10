package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DefaultObjective;
import com.ggk.hrms.review.ui.vo.DefaultObjectiveVO;
import com.ggk.hrms.review.ui.vo.DropDownVO;

public interface DefaultObjectiveDAO {
	
	
	public List<DefaultObjective> getDefaultObjectivesByRCIdAndEmpId(int reviewCycleId,int managerEmployeeId);
	List<DefaultObjectiveVO> getDefaultObjectives(int empId,int reviewHeaderId);
	DefaultObjective getDefaultObjective(int defaultObjId);
	List<DropDownVO> getDefaultObjDropDown(int managerId, int reviewCycleId,
			int gradeId);

}
