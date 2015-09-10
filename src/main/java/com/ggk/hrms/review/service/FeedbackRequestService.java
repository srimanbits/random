package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.beans.domain.FeedbackRequest;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.ui.form.FeedbackRequestForm;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;

public interface FeedbackRequestService {

	public FeedbackRequest getFeedbackRequestById(int feedbackRequestId);

	public List<FeedbackRequestVO> getFeedbackRequests(int headerId, int employeeId);

	public List<FeedbackQuestion> getFeedbackQuestions(boolean isActive);
	
	public void buildFeedbackRequest(FeedbackRequestForm feedbackRequestForm, String type);

	public void update(FeedbackRequestForm feedbackRequestForm);
	
	public ReviewHeader getReviewHeader(int headerId) ;
	
	public List<FeedbackRequestVO> getFeedbackRequestsReceived(int employeeId ,int  reviewCycleId);

	public List<FeedbackRequestVO> getFeedbackRequestsSent(int empId, int reviewCycleId);

	public List<FeedbackRequestVO> getSelfFeedbackRequestsSent(int empId,
			int reviewCycleId);

	public List<FeedbackRequestVO> getSelfFeedbackRequestsReceived(int empId,
			int reviewCycleId);
	
	public void save(FeedbackRequestForm feedbackRequestForm);

	public List<FeedbackRequestVO> getFeedbackRequests(Integer reviewHeaderId);
	
	public List<FeedbackRequestVO> getFeedbackRequestsOnProjectChange(Integer previousManagerId, Integer startIndex, Integer pageDisplayLength, DataTablesJSONWrapper reviewFormLinkVOWrapper);

	public Long getPendingFeedbackRequests(int loggedInEmployeeId);

}
