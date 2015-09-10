package com.ggk.hrms.review.dao.hibernate;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.dao.ReviewCycleDAO;
import com.ggk.hrms.review.ui.vo.DropDownVO;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.ui.vo.ManagerEmployeeEmailVO;

@Repository("reviewCycleDao")
public class ReviewCycleDAOImpl implements ReviewCycleDAO {

	Logger log = Logger.getLogger(ReviewCycleDAOImpl.class);

	@PersistenceContext(unitName = "hrms")
	private EntityManager entityManager;

	@Override
	public ReviewCycle getActiveReviewCycle() {

		StringBuilder jpql = new StringBuilder();

		jpql.append(" from ReviewCycle rc where  rc.isActive = :isActive order by CreatedDate desc");
		Query query = entityManager.createQuery(jpql.toString());
		query.setMaxResults(1);
		query.setParameter("isActive", true);
		//query.setParameter("appraisalPeriodTypeCode", "H1-2014");
		return (ReviewCycle) query.getSingleResult();
	}

	@Override
	public List<DropDownVO> getReviewCycleDropDown() {
		Query query = entityManager
				.createQuery("select new com.ggk.hrms.review.ui.vo.DropDownVO(r.reviewCycleId,r.reviewCycleName) from ReviewCycle r order by r.reviewCycleStartDate desc");
		return query.getResultList();
	}

	@Override
	public ReviewCycle getReviewCycle(int reviewCycleId) {
		ReviewCycle reviewCycle = new ReviewCycle();
		Query query = entityManager
				.createQuery("select rc from ReviewCycle as rc where rc.reviewCycleId=:reviewCycleId");
		query.setParameter("reviewCycleId", reviewCycleId);
		reviewCycle = (ReviewCycle) query.getSingleResult();
		return reviewCycle;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	@Transactional
	public List<ManagerEmployeeEmailVO> getManagerEmployeesEmailMapToNotify(
			String statusMessage1, String statusMessage2,
			String statusMessage3, int reviewCycleId, float daysRemaining) {
		Session session = (Session) entityManager.getDelegate();
		SQLQuery sqlQuery = session
				.createSQLQuery("{call Review.ReturnEmployeeMails(:statusMessage1,:statusMessage2,:statusMessage3,:reviewCycleId,:daysRemaining)}");
		sqlQuery.setParameter("statusMessage1", statusMessage1);
		sqlQuery.setParameter("statusMessage2", statusMessage2);
		sqlQuery.setParameter("statusMessage3", statusMessage3);
		sqlQuery.setParameter("reviewCycleId", reviewCycleId);
		sqlQuery.setParameter("daysRemaining", (int) daysRemaining);
		return sqlQuery.setResultTransformer(
				Transformers.aliasToBean(ManagerEmployeeEmailVO.class)).list();
	}

	public List<ReviewCycle> getReviewCyclesToNotify(boolean isActive,
			int reminderNoofDaysAfter) {
		StringBuilder jpql = new StringBuilder();
		jpql.append(" from ReviewCycle rc where rc.isActive = :isActive"
				+ " OR (	DATEDIFF(day,AppraisalDueDate,GETDATE()) >= 0 AND DATEDIFF(day,AppraisalDueDate,GETDATE()) <= :reminderNoofDaysAfter)");
		Query query = entityManager.createQuery(jpql.toString());
		query.setParameter("isActive", isActive);
		query.setParameter("reminderNoofDaysAfter", reminderNoofDaysAfter);
		return query.getResultList();
	}

	@Override
	@Transactional
	public int insertRow(ReviewCycle reviewCycle) {
		entityManager.merge(reviewCycle);
		return 1;
	}

	@SuppressWarnings("unchecked")
	public List<ReviewCycle> getList() {
		Query query = entityManager
				.createQuery(" from ReviewCycle as rc ORDER by rc.reviewCycleEndDate DESC");
		return query.getResultList();
	}

	@Override
	public ReviewCycle getRowById(int reviewCycleId) {
		ReviewCycle reviewCycle = new ReviewCycle();
		Query query = entityManager
				.createQuery("select rc from ReviewCycle as rc where rc.reviewCycleId=:reviewCycleId");
		query.setParameter("reviewCycleId", reviewCycleId);
		reviewCycle = (ReviewCycle) query.getSingleResult();
		return reviewCycle;
	}

	@Transactional
	@Override
	public int updateRow(int reviewCycleId, Date goalsSettingDueDate, Date goalsApprovalDueDate,
			Date goalsAcceptanceDueDate, Date selfAppraisalDueDate,
			Date appraisalDiscussionDueDate, Date appraisalDueDate,
			Date reviewCycleStartDate, Date reviewCycleEndDate,
			boolean isActive, Date lastModifiedDate) {
		// ReviewCycle reviewCycle=new ReviewCycle();
		Query query = entityManager
				.createQuery("UPDATE ReviewCycle rc SET rc.goalsSettingDueDate =:goalsSettingDueDate, "
						+ "rc.goalsApprovalDueDate =:goalsApprovalDueDate , rc.goalsAcceptanceDueDate=:goalsAcceptanceDueDate,"
						+ "rc.selfAppraisalDueDate =:selfAppraisalDueDate ,rc.appraisalDiscussionDueDate=:appraisalDiscussionDueDate ,"
						+ " rc.appraisalDueDate =:appraisalDueDate , rc.reviewCycleStartDate=:reviewCycleStartDate,"
						+ "rc.reviewCycleEndDate=:reviewCycleEndDate, rc.isActive=:isActive , rc.lastModifiedDate=:lastModifiedDate  where rc.reviewCycleId=:reviewCycleId");

		query.setParameter("goalsSettingDueDate", goalsSettingDueDate);
		query.setParameter("goalsApprovalDueDate", goalsApprovalDueDate);
		query.setParameter("goalsAcceptanceDueDate", goalsAcceptanceDueDate);
		query.setParameter("selfAppraisalDueDate", selfAppraisalDueDate);
		query.setParameter("appraisalDiscussionDueDate",
				appraisalDiscussionDueDate);
		query.setParameter("appraisalDueDate", appraisalDueDate);
		query.setParameter("reviewCycleStartDate", reviewCycleStartDate);
		query.setParameter("reviewCycleEndDate", reviewCycleEndDate);
		query.setParameter("isActive", isActive);
		query.setParameter("lastModifiedDate", lastModifiedDate);
		query.setParameter("reviewCycleId", reviewCycleId);
		// reviewCycle=(ReviewCycle) query.getSingleResult();
		return query.executeUpdate();
	}

	@Override
	@Transactional
	public int deleteRow(int reviewCycleId) {
		Query queryToGetEmployeesCount = entityManager
				.createQuery("select count(*) from ReviewHeader rh where rh.reviewCycle.reviewCycleId=:reviewCycleId");
		queryToGetEmployeesCount.setParameter("reviewCycleId", reviewCycleId);
		Long count = (Long) queryToGetEmployeesCount.getSingleResult();
		log.info("Number of employees asssigned to this review cycle: " + count);
		if (count == 0) {
			Query query = entityManager
					.createQuery("DELETE FROM ReviewCycle rc WHERE rc.reviewCycleId=:reviewCycleId");
			query.setParameter("reviewCycleId", reviewCycleId);
			return query.executeUpdate();
		} else {
			return -1;
		}

	}
	
	@Override
	public Long getEmployeesCountAssignedToReviewCycle(int reviewCycleId) {
		Query queryToGetEmployeesCount = entityManager
				.createQuery("select count(*) from ReviewHeader rh where rh.reviewCycle.reviewCycleId=:reviewCycleId");
		queryToGetEmployeesCount.setParameter("reviewCycleId", reviewCycleId);
		return (Long) queryToGetEmployeesCount.getSingleResult();
		
	}

	@Override
	public Employee getManagerEmailId(String mailToCc) {

		Query query = entityManager
				.createQuery("FROM Employee emp where emp.email=:managerEmail");
		query.setParameter("managerEmail", mailToCc);
		return (Employee) query.getSingleResult();
	}

	@Override
	public String getSharedAppraiser(
			ManagerEmployeeEmailVO managerEmployeeEmailVO) {
		Query query = entityManager
				.createQuery("SELECT sharedAppraiser.assignedToEmployee.email FROM SharedAppraiser sharedAppraiser where sharedAppraiser.reviewHeader.reviewHeaderId=:reviewHeaderId "
						+ "and sharedAppraiser.assignedByEmployee.empId=:managerEmployeeId and sharedAppraiser.isShared=1 and sharedAppraiser.isActive=1");
		query.setParameter("managerEmployeeId",
				managerEmployeeEmailVO.getManagerId());
		query.setParameter("reviewHeaderId",
				managerEmployeeEmailVO.getReviewHeaderId());
		List<String> sharedAppraiserMails = (List<String>) query
				.getResultList();
		if (sharedAppraiserMails.size() == 0) {
			return null;
		}
		return sharedAppraiserMails.get(0);
	}

	@Override
	@Transactional
	public int startReviewCycle(int employeeId, int reviewCycleId, int appraisalPeriodTypeId) {
		Query query = entityManager.createQuery("UPDATE ReviewCycle rc SET rc.isActive = False, rc.reviewCycleStatus = 'Completed' where rc.isActive = True AND rc.appraisalPeriodType.appraisalPeriodTypeId=:appraisalPeriodTypeId");
		query.setParameter("appraisalPeriodTypeId", appraisalPeriodTypeId);
		query.executeUpdate();
		
		Query query1 = entityManager.createQuery("UPDATE ReviewCycle rc SET rc.isActive = True, rc.reviewCycleStatus = 'In Progress' where rc.reviewCycleId=:reviewCycleId");
		query1.setParameter("reviewCycleId", reviewCycleId);
		query1.executeUpdate();
		
		Query query2=entityManager.createNativeQuery("{call Review.CreateReviewCycle(:employeeId,:reviewCycleId,:appraisalPeriodTypeId)}");
		query2.setParameter("employeeId", employeeId);
		query2.setParameter("reviewCycleId", reviewCycleId);
		query2.setParameter("appraisalPeriodTypeId", appraisalPeriodTypeId);
		return query2.executeUpdate();
	}

	@Override
	public List<ReviewCycle> getActiveReviewCycles() {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" from ReviewCycle rc where  rc.isActive = :isActive");
		Query query = entityManager.createQuery(jpql.toString());
		query.setParameter("isActive", true);
		//query.setParameter("appraisalPeriodTypeCode", "H1-2014");
		return (List<ReviewCycle>) query.getResultList();
	}

	@Override
	public int getReviewCycleIdByName(String reviewCycleName) {
		Query query = entityManager
				.createQuery("select rc.reviewCycleId from ReviewCycle as rc where rc.reviewCycleName=:reviewCycleName");
		query.setParameter("reviewCycleName", reviewCycleName);
		return (Integer) query.getSingleResult();
	}

	@Override
	public List<FeedbackRequestVO> getManagerEmployeeEmailToNotifyForFeedback() {
		StringBuilder jpql = new StringBuilder();

		jpql.append(" select new com.ggk.hrms.review.ui.vo.FeedbackRequestVO(fr.feedbackRequestId, fr.requestedDate , fr.requestDueDate, fr.feedbackOnEmployee.displayName ,fr.requestedByEmployee.email,fr.requestedByEmployee.displayName,fr.requestedToEmployee.email, fr.requestedToEmployee.displayName) ")
			.append(" from FeedbackRequest fr ")
			//.append(" where  fr.requestedToEmployee.empId = :employeeId  ")
		    .append(" where fr.type='projectchange' and isPendingAssistance = 'true' and fr.requestDueDate > CURRENT_TIMESTAMP ")
		    .append(" ORDER BY fr.requestedDate desc"); 
		Query query = entityManager.createQuery(jpql.toString());

		//query.setParameter("employeeId", previousManagerId);	

		return  query.getResultList();
	}

}
