package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the Employee_Role database table.
 * 
 */
@Entity
@Table(name = "Employee_Role")
public class EmployeeRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EmployeeRoleId")
	private int employeeRoleId;

	@OneToOne
	@JoinColumn(name = "EmployeeId")
	@ForeignKey(name = "FK_EmployeeRole_emp")
	private Employee employee;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "RoleId")
	@ForeignKey(name = "Fk_EmployeeRole_Role")
	private Role role;

	public EmployeeRole() {
	}

	public int getEmployeeRoleId() {
		return this.employeeRoleId;
	}

	public void setEmployeeRoleId(int employeeRoleId) {
		this.employeeRoleId = employeeRoleId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}