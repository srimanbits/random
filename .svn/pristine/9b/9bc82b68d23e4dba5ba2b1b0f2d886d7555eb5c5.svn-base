package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.beans.domain.FeedbackQuestionAnswer;
import com.ggk.hrms.review.beans.domain.FeedbackRequest;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.dao.FeedbackQuestionDAO;
import com.ggk.hrms.review.dao.FeedbackRequestDAO;
import com.ggk.hrms.review.dao.ReviewCycleDAO;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.FeedbackRequestService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.task.FeedbackSharingNotificationTask;
import com.ggk.hrms.review.ui.form.FeedbackRequestForm;
import com.ggk.hrms.review.ui.vo.FeedbackQuestionAnswerVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Service("feedbackRequestService")
public class FeedbackRequestServiceImpl implements FeedbackRequestService {

	@Resource
	private FeedbackRequestDAO feedbackRequestDao;

	/*@Resource
	private EmployeeDao employeeDao;*/

	/*@Resource
	private ReviewHeaderDAO reviewHeaderDao;*/
	
	@Resource
	private FeedbackQuestionDAO feedbackQuestionDAO;
	
	@Resource
	private ReviewCycleDAO reviewCycleDAO;
	
	@Resource
	private MailingService mailingService;

	@Resource
	private ReviewCycleService reviewCycleService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ReviewHeaderService reviewHeaderService;
	
	
	
	//@Cacheable(value = "feedbackRequest", key = "'getFeedbackRequests_'+#headerId+'_'+#employeeId")
	public List<FeedbackRequestVO> getFeedbackRequests(int headerId,
			int employeeId) {
		return feedbackRequestDao.getFeedbackRequests(headerId, employeeId);
	}

	public FeedbackRequest getFeedbackRequestById(int feedbackRequestId) {
		FeedbackRequest feedbackRequest = feedbackRequestDao
				.getFeedbackRequestById(feedbackRequestId);
		return feedbackRequest;
	}

	//@Cacheable(value = "feedbackRequest", key = "'getFeedbackQuestions_'+#isActive")
	public List<FeedbackQuestion> getFeedbackQuestions(boolean isActive) {

		return feedbackRequestDao.getFeedbackQuestions(isActive);
	}

	public ReviewHeader getReviewHeader(int headerId) {

		return reviewHeaderService.getReviewHeaderById(headerId);
	}
	
	public List<FeedbackRequestVO> getFeedbackRequestsReceived(int employeeId,
			int reviewCycleId) {

		return feedbackRequestDao.getFeedbackRequestsReceived(employeeId,
				reviewCycleId);
	}

	@Transactional
	public void update(FeedbackRequestForm feedbackRequestForm) {

		FeedbackRequest feedbackRequest = feedbackRequestDao
				.getFeedbackRequestById(feedbackRequestForm
						.getFeedbackRequestId());

		for (FeedbackQuestionAnswer feedbackQuestionAnswer : feedbackRequest
				.getFeedbackQuestionAnswers()) {

			for (FeedbackQuestionAnswerVO feedbackQuestionAnswerVO : feedbackRequestForm
					.getFeedbackQuestionAnswerVOs()) {

				if (feedbackQuestionAnswer.getFeedbackQuestion()
						.getFeedbackQuestionId() == feedbackQuestionAnswerVO
						.getFeedbackQuestionId()) {

					Comment comment = new Comment();

					comment.setCommentText(feedbackQuestionAnswerVO
							.getFeedbackAnswer());

					feedbackQuestionAnswer.setFeedbackAnswerComment(comment);

					break;

				}
			}
		}

		Comment overallFeedbackComment = new Comment();

		overallFeedbackComment.setCommentText(feedbackRequestForm
				.getOverallFeedbackComment());

		feedbackRequest.setOverallFeedbackComment(overallFeedbackComment);

		feedbackRequest.setPendingAssistance(false);

		feedbackRequestDao.merge(feedbackRequest);

		Employee sendToEmployee = feedbackRequest.getRequestedByEmployee();
		Employee sendFromEmployee = feedbackRequest.getRequestedToEmployee();
		Employee sendOnEmployee =  feedbackRequest.getFeedbackOnEmployee();
		String subject = sendFromEmployee.getDisplayName()+" responded for your feedback request on "+sendOnEmployee.getDisplayName();
		ReviewCycle reviewCycle = reviewCycleService.getReviewCycle(feedbackRequestForm.getReviewCycleId());
		String text = "Hi "+sendToEmployee.getDisplayName() + ",\n\n" + subject;
		new Thread(new FeedbackSharingNotificationTask(mailingService, sendToEmployee.getEmail(), sendFromEmployee.getEmail(), subject, text, reviewCycle.getReviewCycleName(), "Feedback")).start();
		//mailingService.sendFeedBackNotification(sendToEmployee.getEmail(), sendFromEmployee.getEmail(), subject, text, reviewCycle.getReviewCycleName());
	}
	
	@Transactional
	public void save(FeedbackRequestForm feedbackRequestForm) {

		FeedbackRequest feedbackRequest = feedbackRequestDao
				.getFeedbackRequestById(feedbackRequestForm
						.getFeedbackRequestId());

		for (FeedbackQuestionAnswer feedbackQuestionAnswer : feedbackRequest
				.getFeedbackQuestionAnswers()) {

			for (FeedbackQuestionAnswerVO feedbackQuestionAnswerVO : feedbackRequestForm
					.getFeedbackQuestionAnswerVOs()) {

				if (feedbackQuestionAnswerVO.getFeedbackAnswer()!=null) {
					if (feedbackQuestionAnswer.getFeedbackQuestion()
							.getFeedbackQuestionId() == feedbackQuestionAnswerVO
							.getFeedbackQuestionId()) {

						Comment comment = new Comment();

						comment.setCommentText(feedbackQuestionAnswerVO
								.getFeedbackAnswer());

						feedbackQuestionAnswer
								.setFeedbackAnswerComment(comment);

						break;

					}
				}
			}
		}

		if (feedbackRequestForm.getOverallFeedbackComment()!=null) {
			Comment overallFeedbackComment = new Comment();
			overallFeedbackComment.setCommentText(feedbackRequestForm
					.getOverallFeedbackComment());
			feedbackRequest.setOverallFeedbackComment(overallFeedbackComment);
		}
		

		//feedbackRequest.setPendingAssistance(false);

		feedbackRequestDao.merge(feedbackRequest);
		
	}

	@Transactional
	public void buildFeedbackRequest(FeedbackRequestForm feedbackRequestForm,
			String type) {

		if (type.equals("request")) {
			// Create Feedback request
			if (feedbackRequestForm.getFeedbackOnEmployeeIds() != null) {
				
				String feedbackOnEmployeeIds = feedbackRequestForm
						.getFeedbackOnEmployeeIds();
				List<String> feedbackOnEmployeesList = Arrays
						.asList(feedbackOnEmployeeIds.split(","));
				String feedbackOnEmployeeNames = "";
				for (int i = 0; i < feedbackOnEmployeesList.size(); i++) {

					FeedbackRequest feedbackRequest = new FeedbackRequest();
					try {
						int feedbackOnEmployeeId = Integer
								.parseInt(feedbackOnEmployeesList.get(i));
						Employee feedbackOnEmployee = employeeService.getEmployeeById(feedbackOnEmployeeId);
						feedbackRequest.setFeedbackOnEmployee(feedbackOnEmployee);
						feedbackRequest.setRequestedToEmployee(employeeService
								.getEmployeeById(feedbackRequestForm
										.getRequestedToEmployee().getEmpId()));
						feedbackRequest.setRequestedByEmployee(employeeService
								.getEmployeeById(feedbackRequestForm
										.getRequestedByEmployee().getEmpId()));
						int reviewHeaderid = feedbackRequestForm
								.getReviewHeaderId();
						if (reviewHeaderid != 0) {

							feedbackRequest.setReviewHeader(reviewHeaderService
									.getReviewHeaderById(reviewHeaderid));
						} else {
							feedbackRequest
									.setReviewHeader(reviewHeaderService
											.getReviewHeader(
													feedbackOnEmployeeId,
													feedbackRequestForm
															.getReviewCycleId()));
						}
						feedbackRequest.setRequestedDate(new Date());
						feedbackRequest.setRequestDueDate(feedbackRequestForm
								.getRequestDueDate());

						feedbackRequest.setIsPendingAssistance(true);
						
						List<Integer> selectedFeedbackQuestionnaire=feedbackRequestForm.getSelectedFeedbackQuestionnaire();
						List<FeedbackQuestion> feedbackQuestions =new ArrayList<FeedbackQuestion>();
						if(selectedFeedbackQuestionnaire!=null){
						
						for(int j=0;j<selectedFeedbackQuestionnaire.size();j++){
							FeedbackQuestion selectedFeedbackQuestion=feedbackQuestionDAO.getFeedbackQuestionById(selectedFeedbackQuestionnaire.get(j));
							feedbackQuestions.add(selectedFeedbackQuestion);
						}
						}

												feedbackRequest.setType("request");

						if (!feedbackQuestions.isEmpty()) {

							List<FeedbackQuestionAnswer> feedbackQuestionAnswers = new ArrayList<FeedbackQuestionAnswer>();
							for (FeedbackQuestion feedbackQuestion : feedbackQuestions) {
								FeedbackQuestionAnswer feedbackQuestionAnswer = new FeedbackQuestionAnswer();
								feedbackQuestionAnswer
										.setFeedbackQuestion(feedbackQuestion);
								feedbackQuestionAnswer
										.setFeedbackRequest(feedbackRequest);
								feedbackQuestionAnswers
										.add(feedbackQuestionAnswer);
							}
							feedbackRequest
							.setFeedbackQuestionAnswers(feedbackQuestionAnswers);
						}
						feedbackRequestDao.persist(feedbackRequest);
						if(!feedbackOnEmployeeNames.isEmpty()) {
							feedbackOnEmployeeNames = feedbackOnEmployeeNames+", "+feedbackOnEmployee.getDisplayName();
						} else {
							feedbackOnEmployeeNames = feedbackOnEmployee.getDisplayName();
						}
					} catch (Exception e) {
						throw new RuntimeException("Invalid Selection");
					}
				}
				if(!feedbackOnEmployeeNames.isEmpty()) {
					Employee sendToEmployee = employeeService.getEmployeeById(feedbackRequestForm.getRequestedToEmployee().getEmpId());
					Employee sendFromEmployee = employeeService.getEmployeeById(feedbackRequestForm.getRequestedByEmployee().getEmpId());
					String subject = sendFromEmployee.getDisplayName()+" requested for feedback";
					ReviewCycle reviewCycle = reviewCycleService.getReviewCycle(feedbackRequestForm.getReviewCycleId());
					String text = "Hi " + sendToEmployee.getDisplayName() + ",\n\n" + sendFromEmployee.getDisplayName()+" requested for feedback on "+ feedbackOnEmployeeNames;
					//mailingService.sendFeedBackNotification(sendToEmployee.getEmail(), null, subject, text, reviewCycle.getReviewCycleName());
					new Thread(new FeedbackSharingNotificationTask(mailingService, sendToEmployee.getEmail(), null, subject, text, reviewCycle.getReviewCycleName(), "Feedback")).start();
				}
			} else {
				FeedbackRequest feedbackRequest = new FeedbackRequest();

				int feedbackOnEmployeeId = feedbackRequestForm.getFeedbackOnEmployee().getEmpId();

				feedbackRequest.setFeedbackOnEmployee(employeeService
						.getEmployeeById(feedbackOnEmployeeId));
				feedbackRequest.setRequestedToEmployee(employeeService
						.getEmployeeById(feedbackRequestForm
								.getRequestedToEmployee().getEmpId()));

				feedbackRequest.setRequestedByEmployee(employeeService
						.getEmployeeById(feedbackRequestForm
								.getRequestedByEmployee().getEmpId()));

				int reviewHeaderid = feedbackRequestForm.getReviewHeaderId();
				if (reviewHeaderid != 0) {

					feedbackRequest.setReviewHeader(reviewHeaderService
							.getReviewHeaderById(reviewHeaderid));
				} else {
					int reviewCycleId;
					if(feedbackRequestForm.getReviewCycleId()==0){
						reviewCycleId=reviewCycleService.getActiveReviewCycle().getReviewCycleId();
					} else {
						reviewCycleId=feedbackRequestForm.getReviewCycleId();
					}
					feedbackRequest.setReviewHeader(reviewHeaderService
							.getReviewHeader(
									feedbackOnEmployeeId,
									reviewCycleId));
				}
				feedbackRequest.setRequestedDate(new Date());
				feedbackRequest.setRequestDueDate(feedbackRequestForm
						.getRequestDueDate());

				feedbackRequest.setIsPendingAssistance(true);
				
				List<Integer> selectedFeedbackQuestionnaire=feedbackRequestForm.getSelectedFeedbackQuestionnaire();
				List<FeedbackQuestion> feedbackQuestions =new ArrayList<FeedbackQuestion>();
				if(selectedFeedbackQuestionnaire!=null){
				
					for(int j=0;j<selectedFeedbackQuestionnaire.size();j++){
						FeedbackQuestion selectedFeedbackQuestion=feedbackQuestionDAO.getFeedbackQuestionById(selectedFeedbackQuestionnaire.get(j));
						feedbackQuestions.add(selectedFeedbackQuestion);
					}
				}
				feedbackRequest.setType("request");

				if (!feedbackQuestions.isEmpty()) {

					List<FeedbackQuestionAnswer> feedbackQuestionAnswers = new ArrayList<FeedbackQuestionAnswer>();
					for (FeedbackQuestion feedbackQuestion : feedbackQuestions) {
						FeedbackQuestionAnswer feedbackQuestionAnswer = new FeedbackQuestionAnswer();
						feedbackQuestionAnswer
								.setFeedbackQuestion(feedbackQuestion);
						feedbackQuestionAnswer
								.setFeedbackRequest(feedbackRequest);
						feedbackQuestionAnswers.add(feedbackQuestionAnswer);
					}
					feedbackRequest
							.setFeedbackQuestionAnswers(feedbackQuestionAnswers);}
					feedbackRequestDao.persist(feedbackRequest);
					Employee sendToEmployee = employeeService.getEmployeeById(feedbackRequestForm.getRequestedToEmployee().getEmpId());
					Employee sendFromEmployee = employeeService.getEmployeeById(feedbackRequestForm.getRequestedByEmployee().getEmpId());
					Employee sendOnEmployee =  employeeService.getEmployeeById(feedbackOnEmployeeId);
					//ReviewCycle reviewCycle = reviewCycleService.getReviewCycleById(feedbackRequestForm.getReviewCycleId());
					ReviewCycle reviewCycle = feedbackRequest.getReviewHeader().getReviewCycle();
					String subject = sendFromEmployee.getDisplayName()+" requested for feedback on "+sendOnEmployee.getDisplayName();
					String text = "Hi " + sendToEmployee.getDisplayName() + ",\n\n" + subject;
					new Thread(new FeedbackSharingNotificationTask(mailingService, sendToEmployee.getEmail(), null, subject, text, reviewCycle.getReviewCycleName(), "Feedback")).start();
					//mailingService.sendFeedBackNotification(sendToEmployee.getEmail(), null, subject, text, reviewCycle.getReviewCycleName());
			}
		} else if (type.equals("self")) {
			FeedbackRequest feedbackRequest = new FeedbackRequest();

			int feedbackOnemployeeId = feedbackRequestForm
					.getFeedbackOnEmployee().getEmpId();
			feedbackRequest.setFeedbackOnEmployee(employeeService
					.getEmployeeById(feedbackOnemployeeId));
			//int feedbackByEmployeeId = SecurityDetailsAccessor.getEmpId();
			//int requestedByEmployeeId = feedbackRequestForm.getRequestedByEmployee().getEmpId();
			feedbackRequest.setRequestedToEmployee(employeeService
					.getEmployeeById(SecurityDetailsAccessor.getEmpId()));
			feedbackRequest.setType("self");
			feedbackRequest.setIsPendingAssistance(false);
			int reviewCycleId;
			if(feedbackRequestForm.getReviewCycleId()==0){
				reviewCycleId=reviewCycleDAO.getActiveReviewCycle().getReviewCycleId();
			}else{
				reviewCycleId=feedbackRequestForm.getReviewCycleId();
			}
			feedbackRequest.setReviewHeader(reviewHeaderService
					.getReviewHeader(
							feedbackOnemployeeId,
							reviewCycleId));
			feedbackRequest.setRequestedDate(new Date());
			feedbackRequest.setRequestDueDate(new Date());
			feedbackRequest.setRequestedByEmployee(employeeService
					.getEmployeeById(feedbackRequestForm
							.getRequestedByEmployee().getEmpId()));
			Comment overallFeedbackComment = new Comment();

			overallFeedbackComment.setCommentText(feedbackRequestForm
					.getOverallFeedbackComment());

			feedbackRequest.setOverallFeedbackComment(overallFeedbackComment);

			feedbackRequest.setPendingAssistance(false);
			feedbackRequestDao.persist(feedbackRequest);
			
			/*Employee sendToEmployee = employeeDao.getEmployeeById(requestedByEmployeeId);
			Employee sendFromEmployee = employeeDao.getEmployeeById(feedbackByEmployeeId);
			Employee sendOnEmployee =  employeeDao.getEmployeeById(feedbackOnemployeeId);*/
			Employee sendToEmployee = feedbackRequest.getRequestedByEmployee();
			Employee sendFromEmployee = feedbackRequest.getRequestedToEmployee();
			Employee sendOnEmployee =  feedbackRequest.getFeedbackOnEmployee();
			String subject = sendFromEmployee.getDisplayName()+" sent feedback on "+sendOnEmployee.getDisplayName();
			ReviewCycle reviewCycle = reviewCycleService.getReviewCycle(feedbackRequestForm.getReviewCycleId());
			String text = "Hi " + sendToEmployee.getDisplayName() + ",\n\n" + subject;
			new Thread(new FeedbackSharingNotificationTask(mailingService, sendToEmployee.getEmail(), sendFromEmployee.getEmail(), subject, text, reviewCycle.getReviewCycleName(), "Feedback")).start();
			//mailingService.sendFeedBackNotification(sendToEmployee.getEmail(), sendFromEmployee.getEmail(), subject, text, reviewCycle.getReviewCycleName());
		}
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequestsSent(int employeeId,
			int reviewCycleId) {
		return feedbackRequestDao.getFeedbackRequestsSent(employeeId,
				reviewCycleId);
	}

	@Override
	public List<FeedbackRequestVO> getSelfFeedbackRequestsSent(int employeeId,
			int reviewCycleId) {
		return feedbackRequestDao.getSelfFeedbackRequestsSent(employeeId,
				reviewCycleId);
	}

	@Override
	public List<FeedbackRequestVO> getSelfFeedbackRequestsReceived(
			int employeeId, int reviewCycleId) {
		return feedbackRequestDao.getSelfFeedbackRequestsReceived(employeeId,
				reviewCycleId);
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequests(Integer reviewHeaderId) {
		return feedbackRequestDao.getFeedbackRequests(reviewHeaderId);
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequestsOnProjectChange(
			Integer previousManagerId, Integer startIndex, Integer pageDisplayLength,DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		// TODO Auto-generated method stub
		Long iTotalRecords=feedbackRequestDao.getFeedbackRequestsOnProjectChangeCount(previousManagerId);
		reviewFormLinkVOWrapper.setiTotalRecords(iTotalRecords);
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(iTotalRecords);
		return feedbackRequestDao.getFeedbackRequestsOnProjectChange(previousManagerId,startIndex,pageDisplayLength);
	}

	@Override
	public Long getPendingFeedbackRequests(int loggedInEmployeeId) {
		return feedbackRequestDao.getPendingFeedbackRequests(loggedInEmployeeId);
		
	}

}
