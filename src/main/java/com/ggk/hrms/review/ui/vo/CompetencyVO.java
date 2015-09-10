package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;
import java.util.List;

public class CompetencyVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int competencyId;

	private String competencyName;

	private String definition;

	private boolean isActive;

	private List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs;
	
	private String operationType;

	public CompetencyVO() {
	}

	public int getCompetencyId() {
		return this.competencyId;
	}

	public void setCompetencyId(int competencyId) {
		this.competencyId = competencyId;
	}

	public String getCompetencyName() {
		return this.competencyName;
	}

	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}

	public String getDefinition() {
		return this.definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<ProficiencyLevelCompetencyVO> getProficiencyLevelCompetencyVOs() {
		return proficiencyLevelCompetencyVOs;
	}

	public void setProficiencyLevelCompetencyVOs(
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs) {
		this.proficiencyLevelCompetencyVOs = proficiencyLevelCompetencyVOs;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
}