package com.ggk.hrms.review.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.ProficiencyLevelCompetency;
import com.ggk.hrms.review.dao.ProficiencyLevelCompetencyDAO;
import com.ggk.hrms.review.service.ProficiencyLevelCompetencyService;
import com.ggk.hrms.review.ui.vo.ProficiencyLevelCompetencyVO;

@Service("ProficiencyLevelCompetencyService")
public class ProficiencyLevelCompetencyServiceImpl implements
		ProficiencyLevelCompetencyService {
	
	@Resource
	private ProficiencyLevelCompetencyDAO proficiencyLevelCompetencyDAO;

	@Override
	public void convertProficiencyLevelCompetencyDomainToVO(
			ProficiencyLevelCompetency proficiencyLevelCompetency,
			ProficiencyLevelCompetencyVO proficiencyLevelCompetencyVO) {

		// ProficiencyLevelCompetencyUtil
		// .convertProficiencyLevelCompetencyDomainToVO(
		// proficiencyLevelCompetency,
		// proficiencyLevelCompetencyVO);

		proficiencyLevelCompetencyVO
				.setBehaviorIndicator(proficiencyLevelCompetency
						.getBehaviorIndicator());
		proficiencyLevelCompetencyVO.setIsActive(proficiencyLevelCompetency
				.getIsActive());
		proficiencyLevelCompetencyVO
				.setProficiencyLevelCompetencyId(proficiencyLevelCompetency
						.getProficiencyLevelCompetencyId());
	}

	@Override
	public void convertProficiencyLevelCompetencyDomainToVO(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies,
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs) {

		boolean isNew = false;

		if (proficiencyLevelCompetencies.size() != 0
				&& proficiencyLevelCompetencyVOs.size() == 0) {
			isNew = true;
		}

		if (proficiencyLevelCompetencies.size() != 0
				&& proficiencyLevelCompetencyVOs.size() != 0
				&& proficiencyLevelCompetencies.size() != proficiencyLevelCompetencyVOs
						.size()) {

			return;
		}

		for (int i = 0; i < proficiencyLevelCompetencies.size(); i++) {

			if (isNew) {
				ProficiencyLevelCompetencyVO proficiencyLevelCompetencyVO = new ProficiencyLevelCompetencyVO();
				convertProficiencyLevelCompetencyDomainToVO(
						proficiencyLevelCompetencies.get(i),
						proficiencyLevelCompetencyVO);
				proficiencyLevelCompetencyVOs.add(proficiencyLevelCompetencyVO);
			}

			else {
				convertProficiencyLevelCompetencyDomainToVO(
						proficiencyLevelCompetencies.get(i),
						proficiencyLevelCompetencyVOs.get(i));
			}
		}

	}

	@Override
	public List<ProficiencyLevelCompetency> removeProficiencyLevelCompetencyIds(
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {
		for (int i = 0; i < proficiencyLevelCompetencies.size(); i++) {

			proficiencyLevelCompetencies.get(i)
					.setProficiencyLevelCompetencyId(0);

		}

		return proficiencyLevelCompetencies;
	}

	@Override
	public void convertProficiencyLevelCompetencyVOToDomain(
			ProficiencyLevelCompetencyVO proficiencyLevelCompetencyVO,
			ProficiencyLevelCompetency proficiencyLevelCompetency) {

		// ProficiencyLevelCompetencyUtil
		// .convertProficiencyLevelCompetencyVOToDomain(
		// proficiencyLevelCompetencyVO,
		// proficiencyLevelCompetency);

		proficiencyLevelCompetency
				.setProficiencyLevelCompetencyId(proficiencyLevelCompetencyVO
						.getProficiencyLevelCompetencyId());
		proficiencyLevelCompetency
				.setBehaviorIndicator(proficiencyLevelCompetencyVO
						.getBehaviorIndicator());
		proficiencyLevelCompetency.setCreatedDate(new Date());
		proficiencyLevelCompetency.setLastModifiedDate(new Date());
		proficiencyLevelCompetency.setIsActive(true);
	}

	@Override
	public void convertProficiencyLevelCompetencyVOToDomain(
			List<ProficiencyLevelCompetencyVO> proficiencyLevelCompetencyVOs,
			List<ProficiencyLevelCompetency> proficiencyLevelCompetencies) {
		boolean isNew = false;

		if (proficiencyLevelCompetencyVOs.size() != 0
				&& proficiencyLevelCompetencies.size() == 0) {
			isNew = true;
		}

		if (proficiencyLevelCompetencies.size() != 0
				&& proficiencyLevelCompetencyVOs.size() != 0
				&& proficiencyLevelCompetencies.size() != proficiencyLevelCompetencyVOs
						.size()) {

			return;
		}

		for (int i = 0; i < proficiencyLevelCompetencyVOs.size(); i++) {

			if (isNew) {
				ProficiencyLevelCompetency proficiencyLevelCompetency = new ProficiencyLevelCompetency();
				convertProficiencyLevelCompetencyVOToDomain(
						proficiencyLevelCompetencyVOs.get(i),
						proficiencyLevelCompetency);
				proficiencyLevelCompetencies.add(proficiencyLevelCompetency);
			}

			else {
				convertProficiencyLevelCompetencyDomainToVO(
						proficiencyLevelCompetencies.get(i),
						proficiencyLevelCompetencyVOs.get(i));
			}
		}

	}

	@Override
	public String getBehavioralIndicator(int competencyId,
			int proficiencyLevelId) {
		return proficiencyLevelCompetencyDAO.getBehavioralIndicator(competencyId, proficiencyLevelId);
	}

}
