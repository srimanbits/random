package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ProficiencyLevelCompetency;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelCompetencyVO;

public interface ProficiencyLevelCompetencyService {
	public void convertProficiencyLevelCompetencyDomainToVO(
			ProficiencyLevelCompetency proficiencyLevelCompetency,
			ProficiencyLevelCompetencyVO proficiencyLevelCompetencyVO);

	public void convertProficiencyLevelCompetencyDomainToVO(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies,
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs);

	public List<ProficiencyLevelCompetency> removeProficiencyLevelCompetencyIds(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies);

	public void convertProficiencyLevelCompetencyVOToDomain(
			ProficiencyLevelCompetencyVO proficiencyLevelCompetency,
			ProficiencyLevelCompetency proficiencyLevelCompetencyVO);

	public void convertProficiencyLevelCompetencyVOToDomain(
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencies,
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencyVOs);
	
	public String getBehavioralIndicator(int competencyId,int proficiencyLevelId);

}
