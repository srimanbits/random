package com.ggk.hrms.review.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Competency;
import com.ggk.hrms.review.dao.CompetencyDAO;
import com.ggk.hrms.review.service.CompetencyService;
import com.ggk.hrms.review.ui.vo.CompetencyVO;



@Service("competencyService")
public class CompetencyServiceImpl implements CompetencyService {

	// This service will talk to CompetencyDAO , CompetencyUtil and will give
	// you CompetencyVO objects for UI.
	@Resource
	private CompetencyDAO competencyDAO;

	@Override
	public Competency getCompetencyById(int competencyId) {

		return competencyDAO.getCompetencyById(competencyId);
	}

	@Override
	public List<Competency> getAllActiveCompetencies() {

		return competencyDAO.getAllActiveCompetencies();
	}

	@Override
	public void convertCompetencyDomainToVO(Competency competency,
			CompetencyVO competencyVO) {

		// CompetencyUtil.convertCompetencyDomainToVO(competency,
		// competencyVO);
		competencyVO.setCompetencyId(competency.getCompetencyId());
		competencyVO.setCompetencyName(competency.getCompetencyName());
		competencyVO.setDefinition(competency.getDefinition());
		competencyVO.setIsActive(competency.getIsActive());

	}

	@Override
	public void convertCompetencyVOToDomain(CompetencyVO competencyVO,
			Competency competency) {

		//CompetencyUtil.convertCompetencyVOToDomain(competencyVO, competency);
		competency.setCompetencyId(competencyVO.getCompetencyId());
		competency.setCompetencyName(competencyVO.getCompetencyName());
		competency.setDefinition(competencyVO.getDefinition());
		competency.setIsActive(true);
		competency.setCreatedDate(new Date());
		competency.setLastModifiedDate(new Date());
	}

	@Override
	public int saveCompetency(Competency competency) {

		return competencyDAO.saveCompetency(competency);
	}

	@Override
	public List<Competency> getAllCompetencies() {
		return competencyDAO.getAllCompetencies();
	}

	@Override
	public void makeProficiencyLevelCompetenciesInactive(
			Competency competency) {
		for (int i = 0; i < competency.getProficiencyLevelCompetencies().size(); i++) {

			competency.getProficiencyLevelCompetencies().get(i)
					.setIsActive(false);

		}

		
	}

	@Override
	public void makeProficiencyLevelCompetenciesactive(
			Competency competency) {
		for (int i = 0; i < competency.getProficiencyLevelCompetencies().size(); i++) {

			competency.getProficiencyLevelCompetencies().get(i)
					.setIsActive(true);

		}

		
	}

	@Override
	public Map<Integer, String> getCompetencyDropDown() {
		List<Competency> competencies= competencyDAO.getAllActiveCompetencies();
		Map<Integer, String> competencyDropDown=new HashMap<Integer, String>();
		Iterator competenciesIterator=competencies.iterator();
		while (competenciesIterator.hasNext()) {
			Competency competency = (Competency) competenciesIterator.next();
			competencyDropDown.put(competency.getCompetencyId(), competency.getCompetencyName());
			
		}
		return competencyDropDown;
		
	}

}
