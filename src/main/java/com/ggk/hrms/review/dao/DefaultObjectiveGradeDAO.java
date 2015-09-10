package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;

public interface DefaultObjectiveGradeDAO {

	public List<DefaultObjectiveGrade> getDefaultObjectiveGradesByDefaultObjectiveId(
			int defaultObjectiveId);

	List<DefaultObjectiveGrade> getDefaultObjDropDown(int managerId, int reviewCycleId,
			int gradeId);
	
	DefaultObjectiveGrade getDefaultObjectiveGradeById(int defaultObjectiveGradeId);
}
