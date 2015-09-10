package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the Designation database table.
 * 
 */
@Entity
@Table(schema = "Gal")
@Cache(region="com.ggk.hrms.review.beans.domain.Designation",usage=CacheConcurrencyStrategy.READ_ONLY)
public class Designation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Id")
	private int id;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "LongName")
	private String longName;

	@Column(name = "ShortName")
	private String shortName;

	// bi-directional many-to-one association to Designation

	@OneToMany(mappedBy = "designation", fetch = FetchType.LAZY)
	private List<Grade> grades;

	public Designation() {
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

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

}