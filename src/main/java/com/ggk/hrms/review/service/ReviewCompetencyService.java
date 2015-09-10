package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewCompetency;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;
import com.ggk.hrms.review.ui.vo.ReviewCompetencyVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public interface ReviewCompetencyService {

	public List<ReviewCompetency>  saveReviewCompetencies(
			List<ReviewCompetency> reviewCompetencies);


	public void convertReviewCompetencyDomainToVO(
			List<ReviewCompetency> reviewCompetencies,
			List<ReviewCompetencyVO> reviewCompetencyVOs,ReviewFormInfoVO reviewFormInfoVO);

	public void convertReviewCompetencyVOToDomain(
			List<ReviewCompetencyVO> reviewCompetencyVOs,
			List<ReviewCompetency> reviewCompetencies,ReviewFormInfoVO reviewFormInfoVO);
	
	public List<ReviewCompetencyVO> getReviewCompetencyVOByHeaderId(int reviewHeaderId, ReviewFormRole owner,String requestType);

	public void createReviewCompetencies(int reviewHeaderId,ReviewFormRole owner);// added
																				// this
																				// method..
																				// deleted
																				// in
																				// controller..
	List<ReviewCompetency> getReviewCompetencies(int reviewHeaderId,ReviewFormRole owner);	
	void copyReviewCompetencies(int reviewHeaderId,ReviewFormRole owner,String actionType);


	public List<GradeProficiencyVO> getGradeProficiencyByCompetencyAndDept(
			int competency, int dept);


	public void insertReviewCompetency(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept);


	public void updateReviewCompetancy(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept);


	public void deleteReviewCompetancy(Integer gradeCompetencyExpectationId);


	public void createFinalGradeProficiencyList(
			List<GradeProficiencyVO> gradeProficiencyVOsFrmUI,
			List<GradeProficiencyVO> gradeProficiencyVOsFrmDB);

}
