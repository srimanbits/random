package com.ggk.hrms.authentication;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ggk.hrms.review.beans.domain.Employee;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 2992659214881509857L;
	
	private String userName;
	private String email;
	private String fullName;
	private int empId;
	private String grade;
	
	private Employee managerEmployee;
	

	private Collection<GrantedAuthority> authorities;

	public UserDetailsImpl(String userName, String email, String fullName) {
		this.userName = userName;
		this.email = email;
		this.fullName = fullName;
	}
	
	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getEmail() {
		return email;
	}
	
	public String getFullName() {
		return fullName;
	}

	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return null;
	}
	
	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
	
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Employee getManagerEmployee() {
		return managerEmployee;
	}

	public void setManagerEmployee(Employee managerEmployee) {
		this.managerEmployee = managerEmployee;
	}

	
}
