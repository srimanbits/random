package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the ProficiencyLevelCompetency database table.
 * 
 */
@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.ProficiencyLevelCompetency",usage=CacheConcurrencyStrategy.READ_ONLY)
public class ProficiencyLevelCompetency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ProficiencyLevelCompetencyId")
	private int proficiencyLevelCompetencyId;

	@Column(name = "BehaviorIndicator")
	private String behaviorIndicator;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// bi-directional many-to-one association to GradeCompetencyExpectation
	@OneToMany(mappedBy = "proficiencyLevelCompetency")
	private List<GradeCompetencyExpectation> gradeCompetencyExpectations;

	// bi-directional many-to-one association to Competency
	@ManyToOne
	@JoinColumn(name = "CompetencyId")
	@ForeignKey(name = "FK_ProficiencyLevelCompetency_Competency")
	private Competency competency;

	// bi-directional many-to-one association to ProficiencyLevel
	@ManyToOne
	@JoinColumn(name = "ProficiencyLevelId")
	@ForeignKey(name = "FK_ProficiencyLevelCompetency_Proficiency")
	private ProficiencyLevel proficiencyLevel;

	public ProficiencyLevelCompetency() {
	}

	public int getProficiencyLevelCompetencyId() {
		return this.proficiencyLevelCompetencyId;
	}

	public void setProficiencyLevelCompetencyId(int proficiencyLevelCompetencyId) {
		this.proficiencyLevelCompetencyId = proficiencyLevelCompetencyId;
	}

	public String getBehaviorIndicator() {
		return this.behaviorIndicator;
	}

	public void setBehaviorIndicator(String behaviorIndicator) {
		this.behaviorIndicator = behaviorIndicator;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public List<GradeCompetencyExpectation> getGradeCompetencyExpectations() {
		return this.gradeCompetencyExpectations;
	}

	public void setGradeCompetencyExpectations(
			List<GradeCompetencyExpectation> gradeCompetencyExpectations) {
		this.gradeCompetencyExpectations = gradeCompetencyExpectations;
	}

	public Competency getCompetency() {
		return this.competency;
	}

	public void setCompetency(Competency competency) {
		this.competency = competency;
	}

	public ProficiencyLevel getProficiencyLevel() {
		return this.proficiencyLevel;
	}

	public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
		this.proficiencyLevel = proficiencyLevel;
	}

}