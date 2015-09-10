package com.ggk.hrms.review.ui.vo;


import java.io.Serializable;
import java.util.List;


public class GradeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private int gradeId;


	private String grade;

	
	private String gradeDescription;


	private boolean isActive;

	
	private List<GradeCompetencyExpectationVO> gradeCompetencyExpectations;

    public GradeVO() {
    }

	public int getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getGradeDescription() {
		return this.gradeDescription;
	}

	public void setGradeDescription(String gradeDescription) {
		this.gradeDescription = gradeDescription;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<GradeCompetencyExpectationVO> getGradeCompetencyExpectations() {
		return this.gradeCompetencyExpectations;
	}

	public void setGradeCompetencyExpectations(List<GradeCompetencyExpectationVO> gradeCompetencyExpectations) {
		this.gradeCompetencyExpectations = gradeCompetencyExpectations;
	}
	
}
