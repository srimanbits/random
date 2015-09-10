package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;
import java.util.List;

public class DesignationVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private boolean isActive;

	private String longName;

	private String shortName;

	private List<GradeVO> grades;

	public DesignationVO() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getLongName() {
		return this.longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<GradeVO> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeVO> grades) {
		this.grades = grades;
	}

}
