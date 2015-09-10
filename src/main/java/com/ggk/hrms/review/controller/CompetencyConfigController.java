package com.ggk.hrms.review.controller;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ggk.hrms.review.service.CompetencyService;
import com.ggk.hrms.review.service.DepartmentService;
import com.ggk.hrms.review.service.GradeService;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.ui.form.CompetencyConfigForm;
import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;

@Controller
@RequestMapping(value = "/appraisal")
public class CompetencyConfigController {

	@Autowired
	private ReviewCompetencyService reviewCompetencyService;

	@Autowired
	private ProficiencyLevelService proficiencyLevelService;

	@Autowired
	private GradeService gradeService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private CompetencyService competencyService;
	private Logger log = Logger.getLogger(CompetencyConfigController.class);

	/*
	 * Display UI with competency and dept drowpdown
	 */
	@RolesAllowed(value = {"ROLE_SUPERUSER"})
	@RequestMapping(value = "/showCompetencies.html", method = RequestMethod.GET)
	public String editCompetencies(
			ModelMap model,
			@ModelAttribute("competencyDropDown") Map<Integer, String> competencyDropDown,
			@ModelAttribute("deptDropDown") Map<Integer, String> deptDropDown) {

		CompetencyConfigForm competencyForm = new CompetencyConfigForm();
		competencyForm.setCompetencyList(competencyDropDown);
		competencyForm.setDeptList(deptDropDown);
		model.put("competencyForm", competencyForm);

		return "appraisalForm/competencies/configureCompetencies";

	}

	
	/*
	 * Return List of grade and proficiencies for supplied dept and competency
	 */
	@RolesAllowed(value = {"ROLE_SUPERUSER"})
	@RequestMapping(value = "/editProficiencies.html", method = RequestMethod.GET)
	public String editCompetencies(
			ModelMap model,
			@ModelAttribute("competencyForm") CompetencyConfigForm competencyForm,
			@ModelAttribute("competencyDropDown") Map<Integer, String> competencyDropDown,
			@ModelAttribute("proficiencyDropDown") Map<Integer, String> proficiencyDropDown,
			@ModelAttribute("deptDropDown") Map<Integer, String> deptDropDown) {

		@SuppressWarnings("unchecked")
		List<GradeProficiencyVO> gradeProficiencyVOs = Collections.EMPTY_LIST;
		gradeProficiencyVOs = reviewCompetencyService
				.getGradeProficiencyByCompetencyAndDept(
						competencyForm.getCompetency(),
						competencyForm.getDept());

		competencyForm.setGradeProficiencyVOs(gradeProficiencyVOs);
		competencyForm.setCompetencyList(competencyDropDown);
		competencyForm.setProficiencyLevels(proficiencyDropDown);
		competencyForm.setDeptList(deptDropDown);
		model.put("competencyForm", competencyForm);
		return "appraisalForm/competencies/configureCompetencies";

	}

	/*
	 * save/update/delete competencies of dept
	 */
	@RolesAllowed(value = {"ROLE_SUPERUSER"})
	@RequestMapping(value = "/saveEditedProficiencies.html", method = RequestMethod.POST)
	public String saveEditedCompetencies(
			@ModelAttribute("competencyForm") CompetencyConfigForm competencyForm,
			ModelMap model,
			@ModelAttribute("competencyDropDown") Map<Integer, String> competencyDropDown,
			@ModelAttribute("proficiencyDropDown") Map<Integer, String> proficiencyDropDown,
			@ModelAttribute("deptDropDown") Map<Integer, String> deptDropDown) {

		int competency = competencyForm.getCompetency();
		int dept = competencyForm.getDept();

		List<GradeProficiencyVO> gradeProficiencyVOsFrmUI = competencyForm
				.getGradeProficiencyVOs();
		List<GradeProficiencyVO> gradeProficiencyVOsFrmDB = reviewCompetencyService
				.getGradeProficiencyByCompetencyAndDept(
						competencyForm.getCompetency(),
						competencyForm.getDept());
		reviewCompetencyService.createFinalGradeProficiencyList(gradeProficiencyVOsFrmUI,gradeProficiencyVOsFrmDB);
		
		Iterator<GradeProficiencyVO> VOsIterator = gradeProficiencyVOsFrmUI
				.iterator();
		while (VOsIterator.hasNext()) {
			GradeProficiencyVO gradeProficiencyVO = VOsIterator.next();

			if (gradeProficiencyVO.getGradeCompetencyExpectationId() == null) {
				if (gradeProficiencyVO.getExpectedProficiencyLevelAsPerGrade() != -1)
					reviewCompetencyService.insertReviewCompetency(								//Inserts new competency for a grade to a dept 
							gradeProficiencyVO, competency, dept);
			} else {
				if (gradeProficiencyVO.getExpectedProficiencyLevelAsPerGrade() == -1) {
					reviewCompetencyService
							.deleteReviewCompetancy(gradeProficiencyVO							//Remove an competency for a grade of a dept 
									.getGradeCompetencyExpectationId());
				} else {
					reviewCompetencyService.updateReviewCompetancy(								//Update an competency's proficiency level for a grade
							gradeProficiencyVO, competency, dept);
				}
			}
		}

		gradeProficiencyVOsFrmUI = reviewCompetencyService
				.getGradeProficiencyByCompetencyAndDept(competency, dept);

		competencyForm.setGradeProficiencyVOs(gradeProficiencyVOsFrmUI);
		competencyForm.setCompetencyList(competencyDropDown);
		competencyForm.setProficiencyLevels(proficiencyDropDown);
		competencyForm.setDeptList(deptDropDown);
		model.put("competencyForm", competencyForm);
		return "appraisalForm/competencies/configureCompetencies";

	}

	@ModelAttribute("proficiencyDropDown")
	public Map<Integer, String> getproficiencyLevelDropDown(
			HttpServletRequest request) {

		@SuppressWarnings("unchecked")
		Map<Integer, String> proficiencyDropDown = (Map<Integer, String>) request
				.getSession().getServletContext()
				.getAttribute("proficiencyDropDown");
		if (proficiencyDropDown == null || proficiencyDropDown.size() == 0) {

			proficiencyDropDown = proficiencyLevelService
					.getProficiencyLevelDropDown();
			request.getSession().getServletContext()
					.setAttribute("proficiencyDropDown", proficiencyDropDown);
		}

		return proficiencyDropDown;

	}

	@ModelAttribute("deptDropDown")
	public Map<Integer, String> getDeptDropDown() {

		
		Map<Integer, String> deptDropDown 

			 = departmentService.getDepartmentDropDown();
			

		return deptDropDown;

	}

	@ModelAttribute("competencyDropDown")
	public Map<Integer, String> getCompetencyDropDown() {

		
		Map<Integer, String> competencyDropDown= competencyService.getCompetencyDropDown();
			

		return competencyDropDown;

	}

}
