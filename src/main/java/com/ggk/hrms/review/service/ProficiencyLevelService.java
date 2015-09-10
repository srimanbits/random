package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;

import com.ggk.hrms.review.beans.domain.ProficiencyLevel;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelVO;

public interface ProficiencyLevelService {
	public ProficiencyLevel getProficiencyLevelById(Integer proficiencyLevelId);

	public List<ProficiencyLevel> getAllActiveProficiencyLevels();
	
	public Map<Integer,String>  getProficiencyLevelDropDown();

	public List<DropDownVO> convert(List<ProficiencyLevel> proficiencyLevels);

	public void convertDomainToVO(
			ProficiencyLevel proficiencyLevel,
			ProficiencyLevelVO proficiencyLevelVO);


}
