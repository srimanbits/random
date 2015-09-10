package com.ggk.hrms.review.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ggk.hrms.review.beans.domain.Competency;
import com.ggk.hrms.review.beans.domain.ProficiencyLevel;
import com.ggk.hrms.review.beans.domain.ProficiencyLevelCompetency;
import com.ggk.hrms.review.service.CompetencyService;
import com.ggk.hrms.review.service.ProficiencyLevelCompetencyService;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.ui.form.CompetencyForm;
import com.ggk.hrms.review.ui.vo.CompetencyVO;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelCompetencyVO;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelVO;

@Controller
@RequestMapping(value = "/review")
@RolesAllowed("ROLE_SUPERUSER")
public class CompetencyFormController {

	@Autowired
	private CompetencyService competencyService;

	@Autowired
	private ProficiencyLevelService proficiencyLevelService;

	@Autowired
	private ProficiencyLevelCompetencyService proficiencyLevelCompetencyService;

	@RequestMapping(value = "/admin/activeCompetencies.html")
	public ModelAndView getCompetencyForm() {

		CompetencyForm competencyForm = new CompetencyForm();

		List<Competency> competencies = competencyService
				.getAllActiveCompetencies();
		competencyForm.setCompetencyVOs(new ArrayList<CompetencyVO>());

		for (int i = 0; i < competencies.size(); i++) {

			CompetencyVO competencyVO = new CompetencyVO();
			competencyService.convertCompetencyDomainToVO(competencies.get(i),
					competencyVO);

			competencyForm.getCompetencyVOs().add(competencyVO);

		}

		return new ModelAndView("competencies", "competencyForm",
				competencyForm);

	}

	@RequestMapping(value = "/admin/editCompetency.html")
	public ModelAndView getCompetencyById(
			@RequestParam("competencyId") String competencyId) {
		Competency competency = competencyService.getCompetencyById(Integer
				.parseInt(competencyId.substring(1)));

		CompetencyVO competencyVO = new CompetencyVO();

		competencyService.convertCompetencyDomainToVO(competency, competencyVO);
		List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs = new ArrayList<ProficiencyLevelCompetencyVO>();
		proficiencyLevelCompetencyService
				.convertProficiencyLevelCompetencyDomainToVO(
						competency.getProficiencyLevelCompetencies(),
						proficiencyLevelCompetencyVOs); // converting

		setProficiencyLevelVOsToProficiencyLevelCompetencyVOs(
				proficiencyLevelCompetencyVOs,
				competency.getProficiencyLevelCompetencies());
		competencyVO
				.setProficiencyLevelCompetencyVOs(proficiencyLevelCompetencyVOs);
		competencyVO.setOperationType("EDIT");
		return new ModelAndView("super-user/editCompetency",
				"competencyVO", competencyVO);
	}

	@RequestMapping(value = "/admin/createNewCompetency.html")
	public ModelAndView createNewCompetency() {
		CompetencyVO competencyVO = new CompetencyVO();

		List<ProficiencyLevel> proficiencyLevels = proficiencyLevelService
				.getAllActiveProficiencyLevels();
		List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs = createproficiencyLevelCompetencyVOs(proficiencyLevels);
		competencyVO
				.setProficiencyLevelCompetencyVOs(proficiencyLevelCompetencyVOs);

		return new ModelAndView("super-user/editCompetency",
				"competencyVO", competencyVO);

	}

	@RequestMapping(value = "/admin/saveCompetency.html")
	public String saveCompetency(
			@ModelAttribute("competency") CompetencyVO competencyVO,
			ModelMap model) {

		int competencyId;
		Competency competency = new Competency();
		competencyService.convertCompetencyVOToDomain(competencyVO, competency);

		List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs = competencyVO
				.getProficiencyLevelCompetencyVOs();

		List<ProficiencyLevelCompetency> proficiencyLevelCompetencies = new ArrayList<ProficiencyLevelCompetency>();
		proficiencyLevelCompetencyService
				.convertProficiencyLevelCompetencyVOToDomain(
						proficiencyLevelCompetencyVOs,
						proficiencyLevelCompetencies);

		setProficiencyLevelsToProficiencyLevelCompetencies(
				proficiencyLevelCompetencyVOs, proficiencyLevelCompetencies);

		competency
				.setProficiencyLevelCompetencies(proficiencyLevelCompetencies);
		competency.setProficiencyLevelCompetencies(setCompetency(competency,
				competency.getProficiencyLevelCompetencies()));
		String competencyName = competency.getCompetencyName();

		if (competency.getCompetencyId() != 0) {
			makeCompetencyInActive(String.valueOf(competency.getCompetencyId()));
			competency.setCompetencyId(0);
			competency
					.setProficiencyLevelCompetencies(proficiencyLevelCompetencyService
							.removeProficiencyLevelCompetencyIds(competency
									.getProficiencyLevelCompetencies()));

			competencyId = competencyService.saveCompetency(competency);
		}

		else {

			competencyId = competencyService.saveCompetency(competency);
		}

		competencyVO.setCompetencyId(competencyId);
		competency.setCompetencyName(competencyName);

		model.put("competencyVO", competencyVO);

		return "super-user/masterCompetencyTemplate";

	}

	private List<ProficiencyLevelCompetencyVO> createproficiencyLevelCompetencyVOs(
			List<ProficiencyLevel> proficiencyLevels) {

		List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs = new ArrayList<ProficiencyLevelCompetencyVO>();

		for (int i = 0; i < proficiencyLevels.size(); i++) {

			ProficiencyLevelCompetencyVO proficiencyLevelCompetencyVO = new ProficiencyLevelCompetencyVO();
			ProficiencyLevelVO proficiencyLevelVO = new ProficiencyLevelVO();
			proficiencyLevelService.convertDomainToVO(proficiencyLevels.get(i),
					proficiencyLevelVO);
			proficiencyLevelCompetencyVO
					.setProficiencyLevelVO(proficiencyLevelVO);
			proficiencyLevelCompetencyVOs.add(proficiencyLevelCompetencyVO);

		}

		return proficiencyLevelCompetencyVOs;
	}

	private void setProficiencyLevelVOsToProficiencyLevelCompetencyVOs(
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs,
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {

		for (int i = 0; i < proficiencyLevelCompetencies.size(); i++) {

			ProficiencyLevelVO proficiencyLevelVO = new ProficiencyLevelVO();
			proficiencyLevelService.convertDomainToVO(
					proficiencyLevelCompetencies.get(i).getProficiencyLevel(),
					proficiencyLevelVO);

			proficiencyLevelCompetencyVOs.get(i).setProficiencyLevelVO(
					proficiencyLevelVO);

		}

	}

	private void setProficiencyLevelsToProficiencyLevelCompetencies(
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs,
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {

		for (int i = 0; i < proficiencyLevelCompetencies.size(); i++) {

			ProficiencyLevel proficiencyLevel = proficiencyLevelService
					.getProficiencyLevelById(proficiencyLevelCompetencyVOs
							.get(i).getProficiencyLevelVO()
							.getProficiencyLevelId());

			proficiencyLevelCompetencies.get(i).setProficiencyLevel(
					proficiencyLevel);

		}

	}

	private List<ProficiencyLevelCompetency> setCompetency(
			Competency competency,
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {

		for (int i = 0; i < proficiencyLevelCompetencies.size(); i++) {

			proficiencyLevelCompetencies.get(i).setCompetency(competency);

		}

		return proficiencyLevelCompetencies;
	}

	@RequestMapping(value = "/admin/checkForDupes.html")
	public @ResponseBody
	String checkCompetencyName(@RequestParam String competencyName) {

		String returningString = "no";

		List<Competency> competencies = competencyService
				.getAllActiveCompetencies();

		for (int i = 0; i < competencies.size(); i++) {

			if (competencies
					.get(i)
					.getCompetencyName()
					.trim()
					.replaceAll("\\s", "")
					.equalsIgnoreCase(
							competencyName.trim().replaceAll("\\s", ""))) {
				returningString = "yes";

			}
		}

		return returningString;
	}

	@RequestMapping(value = "/admin/InactiveCompetency.html")
	public @ResponseBody
	String makeCompetencyInActive(@RequestParam String competencyId) {

		Competency competency = competencyService.getCompetencyById(Integer
				.parseInt(competencyId));

		competency.setIsActive(false);
		competencyService.makeProficiencyLevelCompetenciesInactive(competency);
		competencyService.saveCompetency(competency);

		return "yes";

	}

	@RequestMapping(value = "/admin/activeCompetency.html")
	public @ResponseBody
	String makeCompetencyActive(@RequestParam String competencyId) {

		Competency competency = competencyService.getCompetencyById(Integer
				.parseInt(competencyId));

		competency.setIsActive(true);
		competencyService.makeProficiencyLevelCompetenciesactive(competency);
		competencyService.saveCompetency(competency);
		return "yes";

	}

}
