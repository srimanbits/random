package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
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
 * The persistent class for the DefaultObjective database table.
 * 
 */
@Entity
public class DefaultObjective implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DefaultObjectiveId")
	private int defaultObjectiveId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@ManyToOne
	@JoinColumn(name = "ManagerEmployeeId")
	@ForeignKey(name = "Fk_ManagerEmployee")
	private Employee managerEmployee;

	@Column(name = "DefaultObjectiveName")
	private String defaultObjectiveName;

	public int getDefaultObjectiveId() {
		return defaultObjectiveId;
	}

	public void setDefaultObjectiveId(int defaultObjectiveId) {
		this.defaultObjectiveId = defaultObjectiveId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Employee getManagerEmployee() {
		return managerEmployee;
	}

	public void setManagerEmployee(Employee managerEmployee) {
		this.managerEmployee = managerEmployee;
	}

	public String getDefaultObjectiveName() {
		return defaultObjectiveName;
	}

	public void setDefaultObjectiveName(String defaultObjectiveName) {
		this.defaultObjectiveName = defaultObjectiveName;
	}

	

}