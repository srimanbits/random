package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.Department;

public interface DepartmentService {
	
	public List<Department> getAllDepartments();
	
	public Map<Integer,String> getDepartmentDropDown();

}
