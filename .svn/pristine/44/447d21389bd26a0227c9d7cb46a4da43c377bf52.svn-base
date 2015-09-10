package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.Competency;
import com.ggk.hrms.review.ui.vo.CompetencyVO;

public interface CompetencyService {

	public Competency getCompetencyById(int competencyId);

	public List<Competency> getAllActiveCompetencies();

	public void convertCompetencyDomainToVO(Competency competency,
			CompetencyVO competencyVO);

	public void convertCompetencyVOToDomain(CompetencyVO competencyVO,
			Competency competency);

	public int saveCompetency(Competency competency);

	public List<Competency> getAllCompetencies();

	public void makeProficiencyLevelCompetenciesInactive(
			Competency competency);

	public void makeProficiencyLevelCompetenciesactive(
			Competency competency);

	public Map<Integer, String> getCompetencyDropDown();

}
