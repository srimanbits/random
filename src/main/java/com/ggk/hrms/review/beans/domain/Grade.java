package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the Grade database table.
 * 
 */
@Entity
@Table(schema = "Gal")
@Cache(region="com.ggk.hrms.review.beans.domain.Grade",usage=CacheConcurrencyStrategy.READ_ONLY)
public class Grade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GradeId")
	private int gradeId;

	@Column(name = "Grade")
	private String grade;

	@Column(name = "GradeDescription")
	private String gradeDescription;

	@Column(name = "IsActive")
	private boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "DesignationId")
	@ForeignKey(name = "FK_Grade_Designation")
	private Designation designation;

	// bi-directional many-to-one association to GradeCompetencyExpectation
	@OneToMany(mappedBy = "grade", fetch = FetchType.LAZY)
	private List<GradeCompetencyExpectation> gradeCompetencyExpectations;

	
	public Grade() {
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

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	
	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<GradeCompetencyExpectation> getGradeCompetencyExpectations() {
		return this.gradeCompetencyExpectations;
	}

	public void setGradeCompetencyExpectations(
			List<GradeCompetencyExpectation> gradeCompetencyExpectations) {
		this.gradeCompetencyExpectations = gradeCompetencyExpectations;
	}

}