package com.ggk.hrms.review.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ggk.hrms.review.beans.domain.DefaultObjective;
import com.ggk.hrms.review.beans.domain.DefaultObjectiveGrade;
import com.ggk.hrms.review.beans.domain.Grade;
import com.ggk.hrms.review.service.DefaultObjectiveGradeService;
import com.ggk.hrms.review.service.DefaultObjectiveService;
import com.ggk.hrms.review.service.GradeService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.ui.form.DefaultObjectivesForm;
import com.ggk.hrms.review.ui.vo.DefaultObjectiveVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/review")
@RolesAllowed({ "ROLE_SUPERUSER", "ROLE_MANAGER" })
public class DefaultObjectivesController {

	@Resource
	private ReviewCycleService reviewCycleService;

	@Resource
	private DefaultObjectiveService defaultObjectiveService;

	@Resource
	private DefaultObjectiveGradeService defaultObjectiveGradeService;

	@Resource
	private GradeService gradeService;

	@RequestMapping(value = "/manager/defaultObjectives.html")
	public ModelAndView getDefaultObjectives() {

		int reviewCycleId = reviewCycleService.getActiveReviewCycle()
				.getReviewCycleId();
		 int managerEmployeeId = SecurityDetailsAccessor.getEmpId();
		List<DefaultObjective> defaultObjectives = new AutoPopulatingList<DefaultObjective>(
				DefaultObjective.class);

		defaultObjectives = defaultObjectiveService
				.getDefaultObjectivesByRCIdAndEmpId(reviewCycleId, managerEmployeeId);

		DefaultObjectivesForm defaultObjectivesForm = new DefaultObjectivesForm();
		defaultObjectivesForm.setDefaultObjectives(defaultObjectives);

		return new ModelAndView("defaultObjectivesForm",
				"defaultObjectivesForm", defaultObjectivesForm);

	}

	@RequestMapping(value = "/manager/getObjective.html")
	public ModelAndView getDefaultObjectiveById(
			@RequestParam String defaultObjectiveIdToEdit) {

		int defaultObjectiveId = Integer.parseInt(defaultObjectiveIdToEdit
				.substring(16));
		List<DefaultObjectiveGrade> defaultObjectiveGrades = defaultObjectiveGradeService
				.getDefaultObjectiveGradesByDefaultObjectiveId(defaultObjectiveId);
		DefaultObjectiveVO defaultObjectiveVO = createDefaultObjectiveVO(defaultObjectiveGrades);
		List<Grade> allActiveGrades = gradeService.getAllActiveGrades();
		defaultObjectiveVO.setGradeMap(createGrademap(allActiveGrades));// for
																		// check
																		// boxes
																		// display
		defaultObjectiveVO.setGrades(setGrades(defaultObjectiveGrades));// for
																		// path
																		// in
																		// check
																		// boxes
		return new ModelAndView("editDefaultObjective", "defaultObjectiveVO",
				defaultObjectiveVO);
	}

	public DefaultObjectiveVO createDefaultObjectiveVO(
			List<DefaultObjectiveGrade> defaultObjectiveGrades) {

		DefaultObjectiveVO defaultObjectiveVO = new DefaultObjectiveVO();
		defaultObjectiveVO.setDefaultObjectiveGrades(defaultObjectiveGrades);
		defaultObjectiveVO.setDefaultObjectiveId(defaultObjectiveGrades.get(0)
				.getDefaultObjective().getDefaultObjectiveId());
		defaultObjectiveVO.setDefaultObjectiveName(defaultObjectiveGrades
				.get(0).getDefaultObjective().getDefaultObjectiveName());
		// defaultObjectiveVO.setManagerEmployeeId(SecurityDetailsAccessor
		// .getEmpId());

		return defaultObjectiveVO;
	}

	public Map<Integer, String> createGrademap(List<Grade> allActiveGrades) {

		Map<Integer, String> gradeMap = new HashMap<Integer, String>();

		for (int i = 0; i < allActiveGrades.size(); i++) {

			Grade grade = allActiveGrades.get(i);

			gradeMap.put(grade.getGradeId(), grade.getGrade());

		}

		return gradeMap;
	}

	public List<Integer> setGrades(
			List<DefaultObjectiveGrade> defaultObjectiveGrades) {

		List<Integer> grades = new ArrayList<Integer>();

		for (int i = 0; i < defaultObjectiveGrades.size(); i++) {
			grades.add(defaultObjectiveGrades.get(i).getGrade().getGradeId());

		}

		return grades;

	}

	@RequestMapping("/manager/getRemainingGrades.html")
	public @ResponseBody
	String getRemainingGrades(@RequestParam String existingGrades) {

		String remainingGrades = "";

		List<Grade> allActiveGrades = gradeService.getAllActiveGrades();

		for (int i = 0; i < allActiveGrades.size(); i++) {

			if (existingGrades.contains(allActiveGrades.get(i).getGrade())) {

			}

			else {

				remainingGrades = remainingGrades
						+ allActiveGrades.get(i).getGrade() + ",";

			}

		}
		try {
			return remainingGrades.substring(0, remainingGrades.length() - 1);
		}

		catch (IndexOutOfBoundsException e) {

			return "";
		}
	}

	@RequestMapping("/manager/getNoOfActiveGrades.html")
	public @ResponseBody
	int getNoOfActiveGrades() {
		return gradeService.getAllActiveGrades().size();
	}

	@RequestMapping("/manager/addNewGrade.html")
	public String addNewGrade(
			@RequestParam String gradeName,
			@RequestParam String noOfExistingElements,
			ModelMap model,
			@ModelAttribute("defaultObjectiveVO") DefaultObjectiveVO defaultObjectiveVO) {
		model.put("gradeName", gradeName);
		model.put("index", (Integer.parseInt(noOfExistingElements)));
		model.put("defaultObjectiveVO", defaultObjectiveVO);

		return "manager/defaultObjectiveTemplate";
	}

	@RequestMapping("/manager/saveDefaultObjective.html")
	public String saveDefaultObjective(
			@ModelAttribute("defaultObjectiveVO") DefaultObjectiveVO defaultObjectiveVO) {
		for (int i = 0; i < defaultObjectiveVO.getDefaultObjectiveGrades()
				.size(); i++) {
			if (defaultObjectiveVO.getDefaultObjectiveGrades().get(i)
					.getDetailsComment() == null
					&& defaultObjectiveVO.getDefaultObjectiveGrades().get(i)
							.getSuccessCriteria() == null) {
				defaultObjectiveVO.getDefaultObjectiveGrades().remove(i);
				i = i - 1;
			}
		}
		return null;
	}
}
