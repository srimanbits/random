package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;
import com.ggk.hrms.review.dao.DefaultObjectiveGradeDAO;
import com.ggk.hrms.review.service.DefaultObjectiveGradeService;
import com.ggk.hrms.review.ui.vo.DropDownVO;

@Repository("defaultObjectiveGradeService")
public class DefaultObjectiveGradeServiceImpl implements
		DefaultObjectiveGradeService {

	@Resource
	private DefaultObjectiveGradeDAO defaultObjectiveGradeDAO;

	@Override
	public List<DefaultObjectiveGrade> getDefaultObjectiveGradesByDefaultObjectiveId(
			int defaultObjectiveId) {

		return defaultObjectiveGradeDAO
				.getDefaultObjectiveGradesByDefaultObjectiveId(defaultObjectiveId);
	}

	@Override
	public List<DropDownVO> getDefaultObjDropDown(int managerId,
			int reviewCycleId, int gradeId) {
		List<DefaultObjectiveGrade> defaultObjectiveGrades = defaultObjectiveGradeDAO
				.getDefaultObjDropDown(managerId, reviewCycleId, gradeId);
		List<DropDownVO> defaultObjectiveDropDown = null;

		if (defaultObjectiveGrades.size() > 0) {

			defaultObjectiveDropDown = new ArrayList<DropDownVO>();

			for (int i = 0; i < defaultObjectiveGrades.size(); i++) {

				DropDownVO dropDownVO = new DropDownVO();
				String value = defaultObjectiveGrades.get(i)
						.getDefaultObjective().getDefaultObjectiveName();

				if (value.length() > 70) {

					value = value.substring(0, 70);
				}
				dropDownVO.setId(defaultObjectiveGrades.get(i)
						.getDefaultObjectiveGradeId());
				dropDownVO.setValue(value);
				defaultObjectiveDropDown.add(dropDownVO);

			}
		}

		return defaultObjectiveDropDown;
	}

	@Override
	//@Cacheable(value = "defaultObjectiveGrade", key = "'getDefaultObjectiveGradeById_'+#defaultObjectiveGradeId")
	public DefaultObjectiveGrade getDefaultObjectiveGradeById(
			int defaultObjectiveGradeId) {
		return defaultObjectiveGradeDAO
				.getDefaultObjectiveGradeById(defaultObjectiveGradeId);
	}

}
