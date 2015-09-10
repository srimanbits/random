/**
 * 
 */
package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.Competency;

/**
 * @author SwethaP
 *
 */
public interface CompetencyDAO {
	public Competency getCompetencyById(int competencyId);
	public List<Competency> getAllActiveCompetencies();
	public int saveCompetency(Competency competency);
	public List<Competency> getAllCompetencies();
	
}
