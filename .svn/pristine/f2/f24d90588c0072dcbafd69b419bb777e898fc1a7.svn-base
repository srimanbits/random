package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.constants.ReviewFormRole;

public interface ReviewObjectiveDAO {
	
	void merge(List<ReviewObjective> reviewObjs);
	void persist(ReviewObjective reviewObj);
	void remove(int reviewObjId);	
	int getNoOfObjectives(int reviewHeaderId, ReviewFormRole owner);
	ReviewObjective getReviewObjective(int reviewObjectiveId);
	int getNoOfUnApprovedObjectives(int reviewHeaderId, ReviewFormRole owner);
	List<ReviewObjective> getReviewObjectives(int reviewHeaderId,ReviewFormRole owner);	
	void copyReviewObjectives(int reviewHeaderId,ReviewFormRole owner,String actionType);
}
