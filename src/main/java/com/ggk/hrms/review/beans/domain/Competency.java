package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the Competency database table.
 * 
 */
@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.Competency",usage=CacheConcurrencyStrategy.READ_ONLY)
public class Competency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CompetencyId")
	private int competencyId;

	@Column(name = "CompetencyName")
	private String competencyName;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "Definition")
	private String definition;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// bi-directional many-to-one association to ProficiencyLevelCompetency
	@OneToMany(mappedBy = "competency",cascade=CascadeType.ALL)
	//@JoinColumn(name="competencyId")
	private List<ProficiencyLevelCompetency> proficiencyLevelCompetencies;

	// bi-directional one-to-one association to ReviewCompetency
	@OneToMany(mappedBy = "competency",fetch=FetchType.LAZY)
	private List<ReviewCompetency> reviewCompetencies;

	public Competency() {
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

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<ProficiencyLevelCompetency> getProficiencyLevelCompetencies() {
		return this.proficiencyLevelCompetencies;
	}

	public void setProficiencyLevelCompetencies(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {
		this.proficiencyLevelCompetencies = proficiencyLevelCompetencies;

	}

	public List<ReviewCompetency> getReviewCompetencies() {
		return reviewCompetencies;
	}

	public void setReviewCompetencies(List<ReviewCompetency> reviewCompetencies) {
		this.reviewCompetencies = reviewCompetencies;
	}

	

}