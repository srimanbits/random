package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the ProficiencyLevel database table.
 * 
 */
@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.ProficiencyLevel",usage=CacheConcurrencyStrategy.READ_ONLY)
public class ProficiencyLevel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProficiencyLevelId")
	private int proficiencyLevelId;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "ProficiencyLevelName")
	private String proficiencyLevelName;

	@Column(name = "ProficiencyLevelNumber")
	private int proficiencyLevelNumber;

	// bi-directional many-to-one association to ProficiencyLevelCompetency
	@OneToMany(mappedBy = "proficiencyLevel")
	private List<ProficiencyLevelCompetency> proficiencyLevelCompetencies;

	public ProficiencyLevel() {
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

	public List<ProficiencyLevelCompetency> getProficiencyLevelCompetencies() {
		return this.proficiencyLevelCompetencies;
	}

	public void setProficiencyLevelCompetencies(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {
		this.proficiencyLevelCompetencies = proficiencyLevelCompetencies;
	}

}