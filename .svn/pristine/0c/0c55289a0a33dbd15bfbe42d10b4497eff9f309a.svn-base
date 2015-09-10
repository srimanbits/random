package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.beans.domain.FeedbackRequest;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;

public interface FeedbackRequestDAO {

	// Feedback Request 
	
	public void persist(FeedbackRequest feedbackRequest);

	public void merge(FeedbackRequest feedbackRequest);

	public FeedbackRequest getFeedbackRequestById(int feedbackRequestId);

	public List<FeedbackRequestVO> getFeedbackRequests(int headerId, int employeeId);

	// Feedback Questions Related

	public List<FeedbackQuestion> getFeedbackQuestions(boolean status);
	

	//Feedback Response
	
	public List<FeedbackRequestVO> getFeedbackRequestsReceived(int employeeId , int reviewCycleId);

	public List<FeedbackRequestVO> getFeedbackRequestsSent(int employeeId,
			int reviewCycleId);

	public List<FeedbackRequestVO> getSelfFeedbackRequestsSent(int employeeId,
			int reviewCycleId);

	public List<FeedbackRequestVO> getSelfFeedbackRequestsReceived(
			int employeeId, int reviewCycleId);

	public List<FeedbackRequestVO> getFeedbackRequests(Integer reviewHeaderId);
	
	public List<FeedbackRequestVO> getFeedbackRequestsOnProjectChange(Integer previousManagerId, Integer startIndex, Integer pageDisplayLength);

	public Long getFeedbackRequestsOnProjectChangeCount(
			Integer previousManagerId);

	public Long getPendingFeedbackRequests(
			int loggedInEmployeeId);
	

}
