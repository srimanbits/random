package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.ProficiencyLevel;
import com.ggk.hrms.review.dao.ProficiencyLevelDAO;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelVO;

@Service(value = "ProficiencyLevelService")
public class ProficiencyLevelServiceImpl implements ProficiencyLevelService {

	@Resource
	private ProficiencyLevelDAO proficiencyLevelDAO;

	@Override
	public ProficiencyLevel getProficiencyLevelById(Integer proficiencyLevelId) {
		return proficiencyLevelDAO.getProficiencyLevelById(proficiencyLevelId);
	}

	@Override
	public Map<Integer, String> getProficiencyLevelDropDown() {
		return proficiencyLevelDAO.getProficiencyLevelDropDown();
	}

	@Override
	public List<DropDownVO> convert(List<ProficiencyLevel> proficiencyLevels) {

		List<DropDownVO> dropDownVOs = new ArrayList<DropDownVO>();

		for (ProficiencyLevel proficiencyLevel : proficiencyLevels) {

			DropDownVO dropDownVO = new DropDownVO(
					proficiencyLevel.getProficiencyLevelId(),
					Integer.toString(proficiencyLevel
							.getProficiencyLevelNumber()));
			dropDownVOs.add(dropDownVO);

		}

		return dropDownVOs;
	}

	@Override
	public void convertDomainToVO(ProficiencyLevel proficiencyLevel,
			ProficiencyLevelVO proficiencyLevelVO) {
		// return ProficiencyLevelUtil.convertDomainToVO(proficiencyLevel,
		// proficiencyLevelVO);

		proficiencyLevelVO.setProficiencyLevelId(proficiencyLevel
				.getProficiencyLevelId());
		proficiencyLevelVO.setIsActive(proficiencyLevel.getIsActive());
		proficiencyLevelVO.setProficiencyLevelName(proficiencyLevel
				.getProficiencyLevelName());
		proficiencyLevelVO.setProficiencyLevelNumber(proficiencyLevel
				.getProficiencyLevelNumber());
	}

	

	@Override
	@Cacheable(value ="proficiencyLevel", key = "'getAllActiveProficiencyLevels'")
	public List<ProficiencyLevel> getAllActiveProficiencyLevels() {
		return proficiencyLevelDAO.getAllActiveProficiencyLevels();
	}

}
