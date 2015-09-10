/**
 * 
 */
package com.ggk.hrms.review.ui.vo;


import java.io.Serializable;


public class ProficiencyLevelVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int proficiencyLevelId;

	private boolean isActive;

	private String proficiencyLevelName;

	
	private int proficiencyLevelNumber;

	

    public ProficiencyLevelVO() {
    }

	public int getProficiencyLevelId() {
		return this.proficiencyLevelId;
	}

	public void setProficiencyLevelId(int proficiencyLevelId) {
		this.proficiencyLevelId = proficiencyLevelId;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getProficiencyLevelName() {
		return this.proficiencyLevelName;
	}

	public void setProficiencyLevelName(String proficiencyLevelName) {
		this.proficiencyLevelName = proficiencyLevelName;
	}

	public int getProficiencyLevelNumber() {
		return this.proficiencyLevelNumber;
	}

	public void setProficiencyLevelNumber(int proficiencyLevelNumber) {
		this.proficiencyLevelNumber = proficiencyLevelNumber;
	}

	
}
