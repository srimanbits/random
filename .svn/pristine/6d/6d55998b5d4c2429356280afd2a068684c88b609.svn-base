package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;

public interface ReviewObjectiveService {
		
	void merge(List<ReviewObjective> reviewObjs);
	void persist(ReviewObjective reviewObj);
	boolean remove(int reviewObjId);
	void copyToVOBean(ReviewObjectiveVO voReviewObj,ReviewObjective domainReviewObj,ReviewFormInfoVO reviewFormInfoVO);
	void copyAppraisePhase1Fields(ReviewObjective domainReviewObj,ReviewObjectiveVO voReviewObj,ReviewFormInfoVO reviewFormInfoVO);
	void copyAppraisePhase2Fields(ReviewObjective domainReviewObj,ReviewObjectiveVO voReviewObj,ReviewFormInfoVO reviewFormInfoVO);
	void copyAppraiserPhase2Fields(ReviewObjective domainReviewObj,ReviewObjectiveVO voReviewObj,ReviewFormInfoVO reviewFormInfoVO);
	//void copyAppraiserPhase1Fields(ReviewObjective domainReviewObj,ReviewObjectiveVO voReviewObj,ReviewFormInfoVO reviewFormInfoVO);// added for objectve applicable and NA functionality..@Swarups
	int getNoOfObjectives(int reviewHeaderId, ReviewFormRole owner);
	public ReviewObjective getReviewObjective(int reviewObjectiveId);
	public List<ReviewObjectiveVO> getReviewObjectiveVOByHeaderId(int reviewHeaderId, ReviewFormRole owner,String requestType);
	int getNoOfUnApprovedObjectives(int reviewHeaderId, ReviewFormRole owner);
	List<ReviewObjective> getReviewObjectives(int reviewHeaderId,ReviewFormRole owner);	
	void copyReviewObjectives(int reviewHeaderId,ReviewFormRole owner,String actionType);
}
