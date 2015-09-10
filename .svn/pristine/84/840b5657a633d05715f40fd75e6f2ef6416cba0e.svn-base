package com.ggk.hrms.review.controller;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.vo.EmployeeVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller("employeeDetails")
@RequestMapping("/employeeDetails")
public class EmployeeDetailsController {
	
	@Resource
	private ReviewHeaderService reviewHeaderService;
	
	@Resource
	private EmployeeService employeeService;
	
	@Resource
	private ReviewFormService reviewFormService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@RolesAllowed(value = {"ROLE_SUPERUSER","ROLE_MANAGER"})
	@RequestMapping("/getGradeOrDesgHistoryByEmpId")
	public String getHistory(@RequestParam int empId,boolean fromMenu,ModelMap model){
		Employee appraise = employeeService.getEmployeeById(empId);
		if(fromMenu==false){
			
			List<EmployeeVO> gradeEmployeeVOs=null;
			List<EmployeeVO> desgnEmployeeVOs=null;
			
				gradeEmployeeVOs =employeeService.getEmployeeGradeHistoryById(empId);
			
				desgnEmployeeVOs =employeeService.getEmployeedesignationHistoryById(empId);
		
			Collections.reverse(gradeEmployeeVOs);
			Collections.reverse(desgnEmployeeVOs);
			
			long years=0;
			long months=0;
			if (appraise.getDoj() != null) {
				long diff = TimeUnit.DAYS.convert(new Date().getTime()
						- appraise.getDoj().getTime(), TimeUnit.MILLISECONDS);
				years = diff / 365;
				months = (diff % 365) / 30;
			}
			if (years != 0 || months !=0){
			model.addAttribute("exp",years+" Years "+months+" Months");
			}
			
			model.addAttribute("gradeEmployeeVOs", gradeEmployeeVOs);
			model.addAttribute("desgnEmployeeVOs",desgnEmployeeVOs);
		}
		//model.addAttribute("forGrade", forGrade);
		model.addAttribute("fromMenu",fromMenu);
		model.addAttribute("displayName",appraise.getDisplayName());
		model.addAttribute("doj",appraise.getDoj());
		
		String role=getEmployeeRole();
		model.addAttribute("role", role);
		//getGradeHistory(employeeId,model);
		return fromMenu==true?"user/history":"user/historyDetails";
	}
		
	@RequestMapping("/getGradeOrDesgHistory")
	public String getGradeOrDesgHistory(@RequestParam int reviewHeaderId,ModelMap model){
		// int empId= SecurityDetailsAccessor.getEmpId();
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		ReviewFormRole reviewFormRole = reviewFormService.getReviewFormRole(reviewHeader);
		if(reviewFormRole == ReviewFormRole.SHARED_APPRAISER) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}
		Employee appraise = reviewHeader.getEmployee();
		int empId = appraise.getEmpId();
		String employeeName = appraise.getDisplayName();
		List<EmployeeVO> gradeEmployeeVOs = null;
		List<EmployeeVO> desgnEmployeeVOs = null;

		gradeEmployeeVOs = employeeService.getEmployeeGradeHistoryById(empId);

		desgnEmployeeVOs = employeeService
				.getEmployeedesignationHistoryById(empId);
		if (gradeEmployeeVOs.isEmpty() && desgnEmployeeVOs.isEmpty()) {

			model.addAttribute("employeeName", employeeName);
			return "user/unAvailable";
		}

		Collections.reverse(gradeEmployeeVOs);
		Collections.reverse(desgnEmployeeVOs);

		long years = 0;
		long months = 0;

		if (appraise.getDoj() != null) {
			long diff = TimeUnit.DAYS.convert(new Date().getTime()
					- appraise.getDoj().getTime(), TimeUnit.MILLISECONDS);
			years = diff / 365;
			months = (diff % 365) / 30;
		}

		if (years != 0 || months !=0){
			model.addAttribute("exp",years+" Years "+months+" Months");
			}

		model.addAttribute("gradeEmployeeVOs", gradeEmployeeVOs);
		model.addAttribute("desgnEmployeeVOs", desgnEmployeeVOs);

		// model.addAttribute("forGrade", forGrade);
		model.addAttribute("fromMenu", false);
		model.addAttribute("displayName",appraise.getDisplayName());
		model.addAttribute("doj",appraise.getDoj());
		
		String role = getEmployeeRole();
		model.addAttribute("role", role);
		return "user/history";
	}
	
	private String getEmployeeRole(){
		String role = null;
		List<String> authorities = SecurityDetailsAccessor.getAuthorities();
		if (authorities.contains("ROLE_MANAGER")) {
			role = "manager";
		} else if (authorities.contains("ROLE_USER")) {
			role = "user";
		} else if (authorities.contains("ROLE_SUPERUSER")) {
			role = "superuser";
		}
		return role;
	}
	
}
