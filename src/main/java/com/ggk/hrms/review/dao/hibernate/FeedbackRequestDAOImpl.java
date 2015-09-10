package com.ggk.hrms.review.dao.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.FeedbackQuestion;
import com.ggk.hrms.review.beans.domain.FeedbackRequest;
import com.ggk.hrms.review.dao.FeedbackRequestDAO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;

@Repository("feedbackRequestDao")
public class FeedbackRequestDAOImpl implements FeedbackRequestDAO {

	@PersistenceContext(unitName = "hrms")
	private EntityManager entityManager;



	public void persist(FeedbackRequest feedbackRequest) {

		entityManager.persist(feedbackRequest);

	}

	public void merge(FeedbackRequest feedbackRequest) {

		entityManager.merge(feedbackRequest);

	}

	public FeedbackRequest getFeedbackRequestById(int feedbackRequestId) {

		return entityManager.find(FeedbackRequest.class, feedbackRequestId);

	}

	public List<FeedbackRequestVO> getFeedbackRequests(int headerId, int employeeId) {

		StringBuilder jpql = new StringBuilder();

		jpql.append(
				" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName, fr.requestedByEmployee.displayName ,fr.requestedToEmployee.displayName, fr.type ) ")
				.append(" from FeedbackRequest fr ")
				.append(" where  fr.reviewHeader.reviewHeaderId = :headerId  ")
				.append(" and  fr.requestedByEmployee.empId = :employeeId")
		.append(" ORDER BY fr.isPendingAssistance,fr.requestDueDate asc,  fr.requestedDate asc "); 
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("headerId", headerId);
		query.setParameter("employeeId", employeeId);

		return query.getResultList();

	}

	// Feedback Questions Related

	public List<FeedbackQuestion> getFeedbackQuestions(boolean isActive) {

		StringBuilder jpql = new StringBuilder();
		jpql.append("from FeedbackQuestion fq where  fq.isActive = :isActive");

		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("isActive", isActive);

		return query.getResultList();
	}
     
	public List<FeedbackRequestVO> getFeedbackRequestsReceived(int employeeId, int reviewCycleId){
		
		
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.requestedDate , fr.requestDueDate, fr.requestedByEmployee.displayName ,fr.feedbackOnEmployee.displayName , fr.isPendingAssistance, fr.type ) ")
			.append(" from FeedbackRequest fr ")
			.append(" where  fr.requestedToEmployee.empId = :employeeId  ")
		    .append(" AND fr.reviewHeader.reviewCycle.reviewCycleId = :reviewCycleId ")
		    .append(" AND (fr.type='request' OR fr.type='projectchange') ")
		    .append(" ORDER BY fr.isPendingAssistance desc,fr.requestDueDate asc,  fr.requestedDate asc"); 
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("employeeId", employeeId);
		
		query.setParameter("reviewCycleId", reviewCycleId);

		return  query.getResultList();
	
		
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequestsSent(int employeeId,
			int reviewCycleId) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName ,fr.requestedByEmployee.displayName, fr.requestedToEmployee.displayName, fr.type ) ")
			.append(" from FeedbackRequest fr ")
			.append(" where  fr.requestedByEmployee.empId = :employeeId  ")
		    .append(" AND fr.reviewHeader.reviewCycle.reviewCycleId = :reviewCycleId ")
		    .append(" AND (fr.type='request' OR fr.type='projectchange')")
		    .append(" ORDER BY fr.isPendingAssistance, fr.requestedDate desc "); 
		
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("employeeId", employeeId);
		
		query.setParameter("reviewCycleId", reviewCycleId);

		return  query.getResultList();
	}

	@Override
	public List<FeedbackRequestVO> getSelfFeedbackRequestsSent(int employeeId,
			int reviewCycleId) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName ,fr.requestedByEmployee.displayName, fr.requestedToEmployee.displayName, fr.type ) ")
			.append(" from FeedbackRequest fr ")
			.append(" where  fr.requestedToEmployee.empId = :employeeId  ")
		    .append(" AND fr.reviewHeader.reviewCycle.reviewCycleId = :reviewCycleId ")
		    .append(" AND fr.type='self'")
		    .append(" ORDER BY requestedDate desc"); 
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("employeeId", employeeId);
		
		query.setParameter("reviewCycleId", reviewCycleId);

		return  query.getResultList();
	}
	
	@Override
	public List<FeedbackRequestVO> getSelfFeedbackRequestsReceived(int employeeId,
			int reviewCycleId) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName ,fr.requestedByEmployee.displayName, fr.requestedToEmployee.displayName, fr.type ) ")
			.append(" from FeedbackRequest fr ")
			.append(" where  fr.requestedByEmployee.empId = :employeeId  ")
		    .append(" AND fr.reviewHeader.reviewCycle.reviewCycleId = :reviewCycleId ")
		    .append(" AND fr.type='self'")
		    .append(" ORDER BY fr.requestedDate desc"); 
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("employeeId", employeeId);
		
		query.setParameter("reviewCycleId", reviewCycleId);

		return  query.getResultList();
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequests(Integer reviewHeaderId) {
		StringBuilder jpql = new StringBuilder();

		jpql.append(
				" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName, fr.requestedByEmployee.displayName ,fr.requestedToEmployee.displayName, fr.type,"
				+ "fr.feedbackOnEmployee.empId, fr.requestedByEmployee.empId ,fr.requestedToEmployee.empId ) ")
				.append(" from FeedbackRequest fr ")
				.append(" where  fr.reviewHeader.reviewHeaderId = :headerId  and fr.type in ('projectchange','request')")
		.append(" ORDER BY fr.isPendingAssistance,fr.requestDueDate asc,  fr.requestedDate asc "); 
		Query query = entityManager.createQuery(jpql.toString());

		query.setParameter("headerId",reviewHeaderId);

		return query.getResultList();
	
	}

	@Override
	public List<FeedbackRequestVO> getFeedbackRequestsOnProjectChange(
			Integer previousManagerId, Integer startIndex, Integer pageDisplayLength) {
		StringBuilder jpql = new StringBuilder();

		
		jpql.append("WITH query AS (select ROW_NUMBER() OVER (order by fr.requestedDate desc) as rowNum ")
			.append(",fr.feedbackRequestId, fr.isPendingAssistance , fr.requestedDate , fr.requestDueDate, onEmp.displayName as requestedOnEmployee ,fromEmp.displayName as requestedByEmployee, toEmp.displayName as requestedToEmployee, fr.type ")
			.append(" from review.FeedbackRequest fr ")
			.append(" LEFT JOIN Gal.Employee toEmp ON fr.ToEmployeeId = toEmp.EmpId ")
			.append(" LEFT JOIN Gal.Employee fromEmp ON fr.fromEmployeeId = fromEmp.EmpId ")
			.append(" LEFT JOIN Gal.Employee onEmp ON fr.onEmployeeId = onEmp.EmpId ")
			.append(" where  fromEmp.empId = :employeeId  ")
		    .append(" AND fr.type='projectchange' and isPendingAssistance = 'true'")
		    .append(") SELECT * FROM query WHERE rowNum BETWEEN :min AND :max");
		
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery(jpql.toString()).setResultTransformer(Transformers.aliasToBean(FeedbackRequestVO.class));
		
		query.setParameter("employeeId", previousManagerId);
		query.setParameter("min", startIndex+1);
		query.setParameter("max", startIndex+pageDisplayLength);

		return  query.list();
	}

	@Override
	public Long getFeedbackRequestsOnProjectChangeCount(
			Integer previousManagerId) {
		
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select count(*) ")
				.append(" from FeedbackRequest fr ")
				.append(" where  fr.requestedByEmployee.empId = :employeeId  ")
			    .append(" AND fr.type='projectchange' and isPendingAssistance = 'true'"); 
		Query query = entityManager.createQuery(jpql.toString());
		query.setParameter("employeeId", previousManagerId);
		
		
		return  (Long)query.getSingleResult();
		
	}

	@Override
	public Long getPendingFeedbackRequests(
			int loggedInEmployeeId) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" select count(*) ")
				.append(" from Review.FeedbackRequest fr ")
				.append(" JOIN Review.ReviewHeader rh ON rh.ReviewHeaderId = fr.ReviewHeaderId ")
				//.append(" LEFT JOIN Review.REviewCycle rc ON rc.ReviewCycleId = rh.ReviewCycleId ")
				.append(" where  fr.FromEmployeeId = :employeeId  ")
			    .append(" and isPendingAssistance = 'true'"); 
		Query query = entityManager.createNativeQuery(jpql.toString());
		query.setParameter("employeeId", loggedInEmployeeId);
		
		
		return  ((Integer)query.getSingleResult()).longValue();
		
	}
	
}
