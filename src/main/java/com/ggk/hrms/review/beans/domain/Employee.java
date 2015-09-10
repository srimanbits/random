package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the Employee database table.
 * 
 */
@Entity
@Table(schema = "Gal")
@Cache(region="com.ggk.hrms.review.beans.domain.Employee",usage=CacheConcurrencyStrategy.READ_ONLY)
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "EmpId")
	private int empId;

	@Column(name = "CreateDate")
	private String createDate;
	
	@Column(name = "DefaultProject")
	private String project;

	@ManyToOne
	@JoinColumn(name = "DesgId")
	@ForeignKey(name = "FK_Employee_Designation")
	private Designation designation;

	@Column(name = "DisplayName")
	private String displayName;

//	@Column(name = "DOB")
//	private String dob;

//	@Column(name = "DOBOriginal")
//	private String DOBOriginal;

	@Column(name = "DOJ")
	@Temporal(TemporalType.DATE)
	private Date doj;

	@Column(name = "Email")
	private String email;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "IsActive")
	private boolean isActive;

//	@Column(name = "IsAdmin")
//	private boolean isAdmin;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "SurName")
	private String surName;
	
	//bi-directional many-to-one association to Employee
    @ManyToOne
	@JoinColumn(name="managerEmpId")
    @ForeignKey(name = "FK_emp_mngr")
	private Employee reportingToEmployee;

	//bi-directional many-to-one association to Employee
	//@OneToMany(mappedBy="reportingToEmployee")
    // Swaroops - Caching Employee Object
	//private List<Employee> subOrdinates;
	
	
	@ManyToOne
	@JoinColumn(name = "GradeId")
	@ForeignKey(name = "fk_Grade")
	private Grade grade;
	
	@Transient
	private Integer userId;


	public Employee() {
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	
	public int getEmpId() {
		return this.empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
	public String getDisplayName() {
		return this.displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

//	public String getDob() {
//		return this.dob;
//	}
//
//	public void setDob(String dob) {
//		this.dob = dob;
//	}
//
//	public String getDOBOriginal() {
//		return this.DOBOriginal;
//	}
//
//	public void setDOBOriginal(String DOBOriginal) {
//		this.DOBOriginal = DOBOriginal;
//	}
//


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

//	public boolean getIsAdmin() {
//		return this.isAdmin;
//	}
//
//	public void setIsAdmin(boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}

	
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSurName() {
		return this.surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Designation getDesignation() {
		return designation;
	}

	public void setDesignation(Designation designation) {
		this.designation = designation;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

//	public void setAdmin(boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}

	public Employee getReportingToEmployee() {
		return reportingToEmployee;
	}

	public void setReportingToEmployee(Employee reportingToEmployee) {
		this.reportingToEmployee = reportingToEmployee;
	}

	/*public List<Employee> getSubOrdinates() {
		return subOrdinates;
	}

	public void setSubOrdinates(List<Employee> subOrdinates) {
		this.subOrdinates = subOrdinates;
	}*/

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	

}