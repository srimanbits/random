package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;
import com.ggk.hrms.review.ui.vo.DropDownVO;

public interface DefaultObjectiveGradeService {
	public List<DefaultObjectiveGrade> getDefaultObjectiveGradesByDefaultObjectiveId(
			int defaultObjectiveId);
	List<DropDownVO> getDefaultObjDropDown(int managerId, int reviewCycleId,
			int gradeId);
	
	DefaultObjectiveGrade getDefaultObjectiveGradeById(int defaultObjectiveGradeId);

}
