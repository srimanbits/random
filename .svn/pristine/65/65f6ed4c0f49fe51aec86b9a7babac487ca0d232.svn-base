package com.ggk.hrms.review.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.EmpSelfNotes;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.dao.EmpSelfNotesDao;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
public class EmployeeNotesController {
	
	
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmpSelfNotesDao notesDao;
	
	
	
	@RequestMapping("/getSelfNotes.html")
	public @ResponseBody  String getEmployeeNotes(){
		int loggedInEmpId = SecurityDetailsAccessor.getEmpId();
		EmpSelfNotes selfNotes = notesDao.getEmpSelfNotes(loggedInEmpId);
		if(selfNotes == null){
			return "";
		}
		return selfNotes.getNotes();
	}
	
	@RequestMapping(value="/saveSelfNotes.html",method=RequestMethod.POST)
	public @ResponseBody  Boolean saveEmployeeNotes(@RequestParam("notesText") String notesText) throws Exception{
		
		return saveEmpSelfNotes(notesText);
	}
	
	
	private boolean saveEmpSelfNotes (String notesText) throws Exception{
		int loggedInEmpId = SecurityDetailsAccessor.getEmpId();
		Employee loggedInEmployee = employeeService.getEmployeeById(loggedInEmpId);
		EmpSelfNotes empSelfNotes = notesDao.getEmpSelfNotes(loggedInEmpId);
		if (empSelfNotes == null){
			empSelfNotes = new EmpSelfNotes();
			empSelfNotes.setEmployee(loggedInEmployee);
		}
		
		empSelfNotes.setNotes(notesText);
		empSelfNotes.setLastUpdatedDate(new Date());
		notesDao.saveEmpSelfNotes(empSelfNotes);
		return true;
	}
	
	

}
