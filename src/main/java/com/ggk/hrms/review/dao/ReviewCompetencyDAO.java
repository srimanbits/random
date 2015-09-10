package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewCompetency;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;



public interface ReviewCompetencyDAO {
	public List<ReviewCompetency> saveReviewCompetencies(List<ReviewCompetency> reviewCompetencies);	
	public void createReviewCompetencies(int reviewHeaderId,int employeeId,ReviewFormRole owner);
	List<ReviewCompetency> getReviewCompetencies(int reviewHeaderId,ReviewFormRole owner);	
	void copyReviewCompetencies(int reviewHeaderId,ReviewFormRole owner,String actionType);
	public List<GradeProficiencyVO> getGradeProficiencyBycompetencyAndDept(int competency,
			int dept);
	public void insertReviewCompetency(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept);
	public void updateReviewCompetancy(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept);
	public void deleteReviewCompetancy(Integer gradeCompetencyExpectationId);
}
