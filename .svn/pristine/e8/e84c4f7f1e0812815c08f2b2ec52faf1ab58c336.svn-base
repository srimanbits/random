package com.ggk.hrms.review.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Department;
import com.ggk.hrms.review.dao.DepartmentDAO;
import com.ggk.hrms.review.service.DepartmentService;

@Repository("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	
	@Resource
	private DepartmentDAO departmentDAO;

	@Override
	public List<Department> getAllDepartments() {
		return departmentDAO.getAllDepartments();

	}

	@Override
	public Map<Integer, String> getDepartmentDropDown() {
		List<Department> departments= departmentDAO.getAllDepartments();
		Map<Integer,String> departmentDropDown=new HashMap<Integer,String>();
		Iterator departmentsIterator=departments.iterator();
		while(departmentsIterator.hasNext()){
			Department department=(Department)departmentsIterator.next();
			departmentDropDown.put(department.getDepartmentId(), department.getDepartmentName());
		}
		return departmentDropDown;
	}

}
