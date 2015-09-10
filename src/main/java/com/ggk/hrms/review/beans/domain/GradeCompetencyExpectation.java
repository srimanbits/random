package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the GradeCompetencyExpectation database table.
 * 
 */
@Entity
public class GradeCompetencyExpectation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GradeCompetencyExpectationId")
	private int gradeCompetencyExpectationId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// bi-directional many-to-one association to Grade
	@ManyToOne
	@JoinColumn(name = "GradeId")
	@ForeignKey(name = "FK_GradeCompetencyExpectation_Grade")
	private Grade grade;

	// bi-directional many-to-one association to ProficiencyLevelCompetency
	@ManyToOne
	@JoinColumn(name = "ProficiencyLevelCompetencyId")
	@ForeignKey(name = "FK_GradeCompExp_ProfLevelComp")
	private ProficiencyLevelCompetency proficiencyLevelCompetency;
	
	@ManyToOne
	@JoinColumn(name = "DeptId")
	@ForeignKey(name = "FK_GradeCompetencyExpectation_Department")
	private Department dept;

	public GradeCompetencyExpectation() {
	}

	public int getGradeCompetencyExpectationId() {
		return this.gradeCompetencyExpectationId;
	}

	public void setGradeCompetencyExpectationId(int gradeCompetencyExpectationId) {
		this.gradeCompetencyExpectationId = gradeCompetencyExpectationId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
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

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Grade getGrade() {
		return this.grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public ProficiencyLevelCompetency getProficiencyLevelCompetency() {
		return this.proficiencyLevelCompetency;
	}

	public void setProficiencyLevelCompetency(
			ProficiencyLevelCompetency proficiencyLevelCompetency) {
		this.proficiencyLevelCompetency = proficiencyLevelCompetency;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

}