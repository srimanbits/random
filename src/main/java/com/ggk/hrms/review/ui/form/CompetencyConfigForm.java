package com.ggk.hrms.review.ui.form;

import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;

public class CompetencyConfigForm {

	int competency;
	int grade;
	int dept;
	Map<Integer, String> gradeList;
	Map<Integer, String> competencyList;
	Map<Integer, String> deptList;
	Map<Integer, String> proficiencyLevels;
	List<GradeProficiencyVO> gradeProficiencyVOs;

	public int getCompetency() {
		return competency;
	}

	public void setCompetency(int competency) {
		this.competency = competency;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Map<Integer, String> getGradeList() {
		return gradeList;
	}

	public void setGradeList(Map<Integer, String> gradeList) {
		this.gradeList = gradeList;
	}

	public Map<Integer, String> getCompetencyList() {
		return competencyList;
	}

	public void setCompetencyList(Map<Integer, String> competencyList) {
		this.competencyList = competencyList;
	}

	public int getDept() {
		return dept;
	}

	public void setDept(int dept) {
		this.dept = dept;
	}

	public Map<Integer, String> getDeptList() {
		return deptList;
	}

	public void setDeptList(Map<Integer, String> deptList) {
		this.deptList = deptList;
	}

	public Map<Integer, String> getProficiencyLevels() {
		return proficiencyLevels;
	}

	public void setProficiencyLevels(Map<Integer, String> proficiencyLevels) {
		this.proficiencyLevels = proficiencyLevels;
	}

	public List<GradeProficiencyVO> getGradeProficiencyVOs() {
		return gradeProficiencyVOs;
	}

	public void setGradeProficiencyVOs(
			List<GradeProficiencyVO> gradeProficiencyVOs) {
		this.gradeProficiencyVOs = gradeProficiencyVOs;
	}

}
