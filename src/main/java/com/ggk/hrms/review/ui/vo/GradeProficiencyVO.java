package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;

public class GradeProficiencyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer expectedProficiencyLevelAsPerGrade;
	private Integer gradeId;
	private Integer gradeCompetencyExpectationId;
	private String gradeName;

	public GradeProficiencyVO(Integer gradeId, String gradeName,
			Integer expectedProficiencyLevelAsPerGrade,
			Integer gradeCompetencyExpectationId) {
		super();
		this.expectedProficiencyLevelAsPerGrade = expectedProficiencyLevelAsPerGrade;
		this.gradeId = gradeId;
		this.gradeCompetencyExpectationId = gradeCompetencyExpectationId;
		this.gradeName = gradeName;
	}

	public GradeProficiencyVO() {
		super();
	}

	public int getExpectedProficiencyLevelAsPerGrade() {
		return expectedProficiencyLevelAsPerGrade;
	}

	public void setExpectedProficiencyLevelAsPerGrade(
			Integer expectedProficiencyLevelAsPerGrade) {
		if(expectedProficiencyLevelAsPerGrade==null)
			this.expectedProficiencyLevelAsPerGrade=-1;
		else
			this.expectedProficiencyLevelAsPerGrade = expectedProficiencyLevelAsPerGrade;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getGradeCompetencyExpectationId() {
		return gradeCompetencyExpectationId;
	}

	public void setGradeCompetencyExpectationId(
			Integer gradeCompetencyExpectationId) {
		this.gradeCompetencyExpectationId = gradeCompetencyExpectationId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeDisplayName) {
		this.gradeName = gradeDisplayName;
	}

}
