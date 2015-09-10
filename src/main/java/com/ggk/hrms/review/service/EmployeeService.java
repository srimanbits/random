package com.ggk.hrms.review.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.ui.vo.EmployeeVO;

public interface EmployeeService {
	
	List<GrantedAuthority> getRoles(int empId);

	Employee getEmployeeById(int empId);

	Employee getEmployee(String email);

	List<Employee> getEmployees(boolean isActive, String parameter, Integer reviewCycleId);

	List<Employee> getSubOrdinates(boolean isActive, String query, int managerId);

	Employee getEmployeeByDisplayName(boolean isActive,	String employeeDisplayName, Integer reviewCycleId);

	List<String> getEmployeEmaildsByCommaSeparatedEmpIds(String commaSeparatedEmployeeIds);

	List<EmployeeVO> getEmployeeGradeHistoryById(int empId);

	List<EmployeeVO> getEmployeedesignationHistoryById(int empId);

}
