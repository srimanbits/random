package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.dao.EmployeeDao;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.ui.vo.EmployeeVO;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDao employeeDao;

	@Override
	public List<GrantedAuthority> getRoles(int empId) {
		List<String> roles = employeeDao.getRoles(empId);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new GrantedAuthorityImpl(role));
		}
		return authorities;
	}

	@Override
	public Employee getEmployeeById(int empId) {
		return employeeDao.getEmployeeById(empId);
	}

	@Override
	public Employee getEmployee(String email) {
		return employeeDao.getEmployee(email);
	}

	@Override
	public List<Employee> getEmployees(boolean isActive, String parameter, Integer reviewCycleId) {

		return employeeDao.getEmployees(isActive, parameter, reviewCycleId);
	}

	@Override
	public List<Employee> getSubOrdinates(boolean isActive, String query, int managerId) {
		return employeeDao.getSubOrdinates(isActive, query, managerId);
	}

	@Override
	public Employee getEmployeeByDisplayName(boolean isActive,
			String employeeDisplayName, Integer reviewCycleId) {
		
		return employeeDao.getEmployeeByDisplayName(isActive, employeeDisplayName, reviewCycleId);
	}

	@Override
	public List<String> getEmployeEmaildsByCommaSeparatedEmpIds(
			String commaSeparatedEmployeeIds) {
		return employeeDao.getEmployeEmaildsByCommaSeparatedEmpIds(commaSeparatedEmployeeIds);
	}

	@Override
	public List<EmployeeVO> getEmployeeGradeHistoryById(int empId) {
		return employeeDao.getEmployeeGradeHistoryById(empId);
	}

	@Override
	public List<EmployeeVO> getEmployeedesignationHistoryById(int empId) {
		return employeeDao.getEmployeedesignationHistoryById(empId);
	}

	
//	@Override
//	public List<Employee> getAllEmployees(boolean isActive, String parameter) {
//		return employeeDao.getAllEmployees(isActive, parameter);
//		}
}
