package com.ggk.hrms.review.dao.hibernate;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.dao.GradeDAO;
import com.ggk.hrms.review.dao.ReviewHeaderDAO;
import com.ggk.hrms.review.ui.vo.OtherAppraisalsVO;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;

@Repository("reviewHeaderDAO")
public class ReviewHeaderDAOImpl implements ReviewHeaderDAO {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private GradeDAO gradeDao;
	
	private final Logger log = Logger
			.getLogger(ReviewHeaderDAOImpl.class);


	@Override
	@Transactional
	public ReviewHeader saveReviewHeader(ReviewHeader reviewHeader) {
		return entityManager.merge(reviewHeader);
	}

	@Override
	public ReviewHeader getReviewHeaderById(int reviewHeaderId) {
		//ReviewHeader reviewHeader = new ReviewHeader();
		Query query = entityManager
				.createQuery("select rh from ReviewHeader as rh where rh.reviewHeaderId=:reviewHeaderId");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		try{
			return (ReviewHeader) query.getSingleResult();
			}catch(NoResultException e){
				
				return null;
			}
	}


		
	/* 
	 * @see com.ggk.hrms.review.dao.ReviewHeaderDAO#getReviewFormLinks(int)
	 * This method is used to get data which in turn used to generate excel sheet for SUPER USER.
	 */
	@Override
	public  List<ReviewFormLinkVO> getReviewFormLinksForExcel(int reviewCycleId) {
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery("Select emp.DisplayName as employeeDisplayName, cast(emp.email as varchar(50)) as email,rc.ReviewCycleName,rh.ReviewHeaderId as reviewHeaderId ,RSAppraise.AppraiseRatingId,"+
				        " RSAppraiser.AppraiserRatingId,rs.ReviewStatusDescription as reviewFormStatus,emp.EmpId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,"
				        + " mgr.DisplayName as mainAppraiserDisplayName,"
				        + " gra.Grade,desg.ShortName as designation,emp.DefaultProject as project "+
						" from "+
						" Review.ReviewHeader rh "+
						" JOIN  "+
						" Review.ReviewCycle RC "+
						" on rc.ReviewCycleId = rh.ReviewCycleId "+
						" LEFT JOIN "+
						" Review.ReviewSummary RSAppraise on RH.ReviewHeaderId = RSAppraise.ReviewHeaderId and RSAppraise.Owner='APPRAISE' "+
						" LEFT JOIN  "+
						" Review.ReviewSummary RSAppraiser on RH.ReviewHeaderId = RSAppraiser.ReviewHeaderId and RSAppraiser.Owner='APPRAISER' "+
						" LEFT JOIN "+
						" Gal.Grade gra ON rh.CurrentGradeId = gra.GradeId "+
						" LEFT JOIN "+
						" Gal.Designation desg ON rh.CurrentDesgId = desg.Id "+
						" JOIN "+
						" Gal.Employee emp ON rh.EmployeeId = emp.EmpId and emp.isActive=1 "+
						" LEFT JOIN "+
						" Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId"+
						" LEFT JOIN "+
						" Review.ReviewStatus RS on Rh.ReviewStatusCode = rs.ReviewStatusCode"
						+ " Where rh.ReviewCycleId=:reviewCycleId order by mgr.DisplayName,rs.ReviewStatusDescription ").setResultTransformer(Transformers.aliasToBean(ReviewFormLinkVO.class));
		query.setParameter("reviewCycleId", reviewCycleId);
		
		return query.list();
		
	}

	@Override
	public ReviewHeader getReviewHeaderByEmployeeIdAndReviewCycleId(
			int employeeId, int reviewCycleId) {
		//ReviewHeader reviewHeader = new ReviewHeader();
		Query query = entityManager
				.createQuery("select rh from ReviewHeader as rh where rh.reviewCycle.reviewCycleId=:reviewCycleId and rh.employee.empId=:employeeId");
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("employeeId", employeeId);
		try{
		return (ReviewHeader) query.getSingleResult();
		}catch(NoResultException e){
			
			return null;
		}
	}

	

	@Override
	public SharedAppraiser getActiveSharedAppraiser(int reviewHeaderId) {
		Query query = entityManager
				.createQuery("select sha from SharedAppraiser as sha where sha.reviewHeader.reviewHeaderId=:reviewHeaderId and isActive='true'");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		try {
			return (SharedAppraiser) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public List<ReviewActionLogVO> getAllInternalComments(int reviewHeaderId,
			String reviewFormRoleDescription) {
		List<ReviewActionLogVO> reviewActionLogVOs = Collections.EMPTY_LIST;
		Query query = null;
		if (reviewFormRoleDescription.equals("APPRAISE")) {
			query = entityManager
					.createQuery("select log  from ReviewActionLog as log where log.reviewHeaderId=:reviewHeaderId and log.actionType in ('RE_SUBMIT_TO_APPRAISE','APPROVE_GOALS', 'APPROVE_APPRAISAL','SUBMIT', 'ACCEPT_GOALS','ACCEPT_APPRAISAL' ) order by createdDate desc");
		} else {
			query = entityManager
					.createQuery("select log  from ReviewActionLog as log where log.reviewHeaderId=:reviewHeaderId and log.actionType in ('RE_SUBMIT_TO_APPRAISE','APPROVE_GOALS', 'APPROVE_APPRAISAL','SUBMIT','UNSHARE', 'ACCEPT_GOALS','ACCEPT_APPRAISAL' ) order by createdDate desc");
		}
		query.setParameter("reviewHeaderId", reviewHeaderId);
		reviewActionLogVOs = query.getResultList();
		return reviewActionLogVOs;
	}

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForEmp(int empId, int reviewCycleId) {
		List<OtherAppraisalsVO> appraisalsVOs;
		Query query = entityManager.createQuery("select new com.ggk.hrms.review.ui.vo.OtherAppraisalsVO(rc.reviewCycleName,rh.reviewHeaderId,rh.employee.empId,rh.mainAppraiserEmployee.empId) from ReviewHeader as rh join rh.reviewCycle as rc WHERE rh.employee.empId=:empId AND rc.reviewCycleId!=:reviewCycleId");
		query.setParameter("empId", empId);
		query.setParameter("reviewCycleId", reviewCycleId);
		appraisalsVOs = query.getResultList();
		return appraisalsVOs;
	}

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForMgr(int managerEmpId,
			int reviewCycleId, int empId) {
		List<OtherAppraisalsVO> appraisalsVOs;
		Query query= entityManager.createQuery("select new com.ggk.hrms.review.ui.vo.OtherAppraisalsVO(rc.reviewCycleName,rh.reviewHeaderId,rh.employee.empId,rh.mainAppraiserEmployee.empId) from ReviewHeader as rh join rh.reviewCycle as rc join rh.mainAppraiserEmployee as mgr "
				+ " join rh.employee as emp where mgr.empId=:managerEmpId "
				+ " and rc.reviewCycleId!=:reviewCycleId and emp.empId=:empId and "
				+ " rh.reviewStatus.reviewStatusCode NOT IN ('NOT_STARTED', 'GOALS_SETTING_IN_PROGRESS')");
		query.setParameter("managerEmpId", managerEmpId);
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("empId", empId);
		appraisalsVOs = query.getResultList();
		return appraisalsVOs;
	}

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForSuperUser(int reviewCycleId,
			int empId) {
		List<OtherAppraisalsVO> appraisalsVOs;
		Query query = entityManager
				.createQuery("select new com.ggk.hrms.review.ui.vo.OtherAppraisalsVO(rc.reviewCycleName,rh.reviewHeaderId,rh.employee.empId,rh.mainAppraiserEmployee.empId) from ReviewHeader as rh join rh.reviewCycle as rc "
				+ " join rh.employee as emp where "
				+ " rc.reviewCycleId!=:reviewCycleId and emp.empId=:empId and "
				+ " rh.reviewStatus.reviewStatusCode NOT IN ('NOT_STARTED', 'GOALS_SETTING_IN_PROGRESS')");
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("empId", empId);
		appraisalsVOs = query.getResultList();
		return appraisalsVOs;
	}
	
	@Override
	public List<ReviewFormLinkVO> getReviewFormIndexOfAll(int reviewCycleId,
			int employeeId, Integer displayLength, Integer pageDisplayLength,String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		List<ReviewFormLinkVO> reviewFormLinks = Collections.EMPTY_LIST;
		/*String queryString="select new com.ggk.hrms.review.ui.vo.ReviewFormLinkVO(rh.reviewHeaderId, rh.employee.displayName, rh.employee.email, rh.reviewCycle.reviewCycleName, rh.reviewStatus.reviewStatusCode, rh.employee.empId, rh.mainAppraiserEmployee.empId, "
				+ "rh.mainAppraiserEmployee.displayName,grade.grade,designation.shortName, rh.employee.project,shared.sharedAppraiserId,shared.assignedByEmployee.empId,shared.assignedByEmployee.displayName ,"
				+ "shared.assignedToEmployee.empId,shared.assignedToEmployee.displayName)"
						+ " from ReviewHeader as rh left join rh.currentGrade as grade left join rh.currentDesignation as designation left join rh.sharedAppraisers as shared "
						+ " where rh.reviewCycle.reviewCycleId=:reviewCycleId and (shared.isActive='true' or shared.isActive is null)"
						+ " and rh.employee.empId not in (select er.employee.empId from EmployeeRole as er where er.role.roleDescription='ROLE_SUPERUSER')"
						+
						 " and rh.mainAppraiserEmployee.empId!=:employeeId" + 
						" and rh.employee.isActive = 'true'";*/
		
		String queryString="WITH query AS (select ROW_NUMBER() OVER (order by "+colName+" "+sortDirection+") as rowNum ,rh.ReviewHeaderId as reviewHeaderId,emp.displayName as employeeDisplayName,cast(emp.email as varchar(50)) as email,RC.ReviewCycleName as reviewCycleName,rh.ReviewStatusCode as reviewFormStatus,emp.empId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,"
				+" mgr.DisplayName as mainAppraiserDisplayName,grade.Grade as grade,desg.ShortName as designation,emp.DefaultProject as project,sh.SharedAppraiserId as sharedAppraiserId,sh.AssignedByEmployeeId as assignedByEmployeeId,sh.AssignedToEmployeeId as assignedToEmployeeId, "
				+" assignedBy.DisplayName as assignedByEmployeeName ,assignedTo.DisplayName as assignedToEmployeeName"
				+" from  Review.ReviewHeader rh "
						+ " 	LEFT JOIN  "
						+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
						+ " 	JOIN "
						+ " 	Review.ReviewCycle RC "
						+ " 	on RC.ReviewCycleId = rh.ReviewCycleId "
						+ " 	LEFT JOIN "
						+ "		Gal.Grade grade ON rh.CurrentGradeId = grade.GradeId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Designation desg ON rh.CurrentDesgId = desg.Id "
						+ "		 JOIN "
						+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId and emp.isActive= 1"
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
						+ " 	Where rh.reviewCycleId=:reviewCycleId";
		
		String queryStringforCount="select count(*) "
						+" from  Review.ReviewHeader rh "
						+ " 	LEFT JOIN  "
						+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
						+ " 	 JOIN "
						+ " 	Review.ReviewCycle RC "
						+ " 	on RC.ReviewCycleId = rh.ReviewCycleId "
						+ " 	LEFT JOIN "
						+ "		Gal.Grade grade ON rh.CurrentGradeId = grade.GradeId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Designation desg ON rh.CurrentDesgId = desg.Id "
						+ "		 JOIN "
						+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId and emp.isActive= 1"
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
						+ " 	Where rh.reviewCycleId=:reviewCycleId";
		/*String queryStringforCount="select count(rh)"
				+ " from ReviewHeader as rh left join rh.currentGrade as grade left join rh.currentDesignation as designation"
				+ " where rh.reviewCycle.reviewCycleId=:reviewCycleId"
				+ " and rh.employee.empId not in (select er.employee.empId from EmployeeRole as er where er.role.roleDescription='ROLE_SUPERUSER')"
				+
				 " and rh.mainAppraiserEmployee.empId!=:employeeId" + 
				" and rh.employee.isActive = 'true'";*/
		
		if (null != searchValue && !searchValue.equals("")) {
			searchValue = searchValue.toUpperCase();
			queryString=queryString+" and (upper(emp.displayName) like '"+searchValue+"%' or upper(emp.email) like '"+searchValue+"%' or upper(grade.Grade) like '"+searchValue+"%' or upper(desg.ShortName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%' or upper(emp.DefaultProject) like '"+searchValue+"%'"
					+ " or upper(assignedTo.DisplayName) like '"+searchValue+"%' or upper(assignedBy.DisplayName) like '"+searchValue+"%')";
			queryStringforCount=queryStringforCount+" and (upper(emp.displayName) like '"+searchValue+"%' or upper(emp.email) like '"+searchValue+"%' or upper(grade.Grade) like '"+searchValue+"%' or upper(desg.ShortName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%' or upper(emp.DefaultProject) like '"+searchValue+"%'"
					+ " or upper(assignedTo.DisplayName) like '"+searchValue+"%' or upper(assignedBy.DisplayName) like '"+searchValue+"%')";
		}
		if(pageDisplayLength==-1)
			queryString=queryString+") SELECT * FROM query";
		else
			queryString=queryString+") SELECT * FROM query WHERE rowNum BETWEEN :min AND :max";
		//queryString=queryString+ " order by emp.displayName";
		
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(ReviewFormLinkVO.class));
		query.setParameter("reviewCycleId", reviewCycleId);
		if(pageDisplayLength!=-1){
		query.setParameter("min", displayLength+1);
		query.setParameter("max", displayLength+pageDisplayLength);
		}
		
		//query.setFirstResult(displayLength).setMaxResults(pageDisplayLength);
		// query.setParameter("employeeId", employeeId);
		reviewFormLinks = query.list();
		
		query=session.createSQLQuery(queryStringforCount);
		query.setParameter("reviewCycleId", reviewCycleId);
		
		Long totalRecords=((Integer)query.uniqueResult()).longValue();
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(totalRecords);
		reviewFormLinkVOWrapper.setiTotalRecords(totalRecords);
		
		
		return reviewFormLinks;
	}

	@Override
	public List<ReviewFormLinkVO> getReviewFormIndex(int employeeId,
			Integer displayLength, Integer pageDisplayLength,
			String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper,boolean pending) {
		List<ReviewFormLinkVO> reviewFormLinks;
		String queryString="WITH query AS (select ROW_NUMBER() OVER (order by rh.CreatedDate "+sortDirection+") as rowNum ,rh.ReviewHeaderId as reviewHeaderId,rc.ReviewCycleName as reviewCycleName,rh.ReviewStatusCode as reviewFormStatus,"
						+ " emp.EmpId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,mgr.DisplayName as mainAppraiserDisplayName,emp.DefaultProject as project,"
						+ " sh.SharedAppraiserId as sharedAppraiserId,sh.AssignedByEmployeeId as assignedByEmployeeId,"
						+ " assignedBy.DisplayName as assignedByEmployeeName ,sh.AssignedToEmployeeId as assignedToEmployeeId,assignedTo.DisplayName as assignedToEmployeeName"
						+ " from "
						+ " 	Review.ReviewHeader rh "
						+ " 	LEFT JOIN  "
						+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewCycle RC "
						+ " 	on rc.ReviewCycleId = rh.ReviewCycleId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
						+ " 	Where rh.EmployeeId=:empId";
						
		
		
		String queryStringForCount="select count(*)"
				+ " from "
				+ " 	Review.ReviewHeader rh "
				+ " 	LEFT JOIN  "
				+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
				+ " 	LEFT JOIN "
				+ " 	Review.ReviewCycle RC "
				+ " 	on rc.ReviewCycleId = rh.ReviewCycleId "
				+ " 	LEFT JOIN "
				+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId "
				+ " 	LEFT JOIN "
				+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
				+ " 	LEFT JOIN "
				+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
				+ " 	LEFT JOIN "
				+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
				+ " 	LEFT JOIN "
				+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
				+ " 	Where rh.EmployeeId=:empId";

		
		if(pending == true){
			
			queryString = queryString+" and rh.ReviewStatusCode !='COMPLETED' ";
			queryStringForCount = queryStringForCount+" and rh.ReviewStatusCode !='COMPLETED' ";
		}
			
			
		if (null != searchValue && !searchValue.equals("")) {
			searchValue = searchValue.toUpperCase();
			queryString=queryString+" and (upper(rc.ReviewCycleName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%'"
					+" or upper(assignedTo.DisplayName) like '"+searchValue+"%' or upper(assignedBy.DisplayName) like '"+searchValue+"%' )";
			queryStringForCount=queryStringForCount+" and (upper(rc.ReviewCycleName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%'"
					+" or upper(assignedTo.DisplayName) like '"+searchValue+"%' or upper(assignedBy.DisplayName) like '"+searchValue+"%' )";
		}
		
		if(pageDisplayLength==-1)
			queryString=queryString+") SELECT * FROM query";
		else
			queryString=queryString+") SELECT * FROM query WHERE rowNum BETWEEN :min AND :max";
		
		/*queryString=queryString+" order by "+colName +" "+sortDirection;*/
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(ReviewFormLinkVO.class));
		query.setParameter("empId", employeeId);
		if(pageDisplayLength!=-1){
			query.setParameter("min", displayLength+1);
			query.setParameter("max", displayLength+pageDisplayLength);
			}
		reviewFormLinks = query.list();
		
		query = session.createSQLQuery(queryStringForCount);
		query.setParameter("empId", employeeId);
		Long totalRecords =((Integer)query.uniqueResult()).longValue();	
		
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(totalRecords);
		reviewFormLinkVOWrapper.setiTotalRecords(totalRecords);
		
		return reviewFormLinks;
	}

	@Override
	public List<ReviewFormLinkVO> getSharedReviewFormIndexByReviewCycleId(
			int sharedWithEmployeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		List<ReviewFormLinkVO> reviewFormLinks = Collections.EMPTY_LIST;
		/*String queryString="select new com.ggk.hrms.review.ui.vo.ReviewFormLinkVO(rh.reviewHeaderId, rh.employee.displayName, rh.employee.email, rh.reviewCycle.reviewCycleName,"
						+ " rh.reviewStatus.reviewStatusCode, rh.employee.empId, rh.mainAppraiserEmployee.empId, rh.mainAppraiserEmployee.displayName, grade.grade, designation.shortName, rh.employee.project)"
						+ "  from ReviewHeader as rh left join rh.currentGrade as grade left join grade.designation as designation where rh.reviewCycle.reviewCycleId=:reviewCycleId and rh.reviewHeaderId in"
						+ " (select sh.reviewHeader.reviewHeaderId from SharedAppraiser as sh where sh.assignedToEmployee.empId=:sharedWithEmployeeId and sh.isActive=true and rh.employee.isActive = 'true'))";*/
		
		/*String queryString="select new com.ggk.hrms.review.ui.vo.ReviewFormLinkVO(rh.reviewHeaderId, rh.employee.displayName, rh.employee.email, rh.reviewCycle.reviewCycleName, rh.reviewStatus.reviewStatusCode, rh.employee.empId, rh.mainAppraiserEmployee.empId, "
		+ "rh.mainAppraiserEmployee.displayName,grade.grade,designation.shortName, rh.employee.project,shared.sharedAppraiserId,shared.assignedByEmployee.empId,shared.assignedByEmployee.displayName ,"
		+ "shared.assignedToEmployee.empId,shared.assignedToEmployee.displayName)"
				+ " from ReviewHeader as rh left join rh.currentGrade as grade left join rh.currentDesignation as designation left join rh.sharedAppraisers as shared "
				+ " where rh.reviewCycle.reviewCycleId=:reviewCycleId and (shared.isActive='true' or shared.isActive is null)"
				+ " and rh.employee.empId not in (select er.employee.empId from EmployeeRole as er where er.role.roleDescription='ROLE_SUPERUSER')"
				+
				 " and rh.mainAppraiserEmployee.empId!=:employeeId" + 
				" and rh.employee.isActive = 'true'";*/
		String queryString="WITH query AS (select ROW_NUMBER() OVER (order by "+colName+" "+sortDirection+") as rowNum ,rh.ReviewHeaderId as reviewHeaderId,emp.displayName as employeeDisplayName,cast(emp.email as varchar(50)) as email,RC.ReviewCycleName as reviewCycleName,rh.ReviewStatusCode as reviewFormStatus,emp.empId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,"
				+" mgr.DisplayName as mainAppraiserDisplayName,grade.Grade as grade,desg.ShortName as designation,emp.DefaultProject as project,sh.SharedAppraiserId as sharedAppraiserId,sh.AssignedByEmployeeId as assignedByEmployeeId,sh.AssignedToEmployeeId as assignedToEmployeeId, "
				+" assignedBy.DisplayName as assignedByEmployeeName ,assignedTo.DisplayName as assignedToEmployeeName"
				+" from  Review.ReviewHeader rh "
						+ " 	LEFT JOIN  "
						+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewCycle RC "
						+ " 	on RC.ReviewCycleId = rh.ReviewCycleId "
						+ " 	LEFT JOIN "
						+ "		Gal.Grade grade ON rh.CurrentGradeId = grade.GradeId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Designation desg ON rh.CurrentDesgId = desg.Id "
						+ "		LEFT JOIN "
						+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
						+ " 	Where rh.reviewCycleId=:reviewCycleId and rh.reviewHeaderId in"
						+ "		(select sh1.reviewHeaderId from Review.SharedAppraiser as sh1 where sh1.assignedToEmployeeId=:sharedWithEmployeeId and sh1.isActive='true' and emp.isActive = 'true')";
		String queryStringforCount="select count(*)"
				+ "  from  Review.ReviewHeader rh "
						+ " 	LEFT JOIN  "
						+ "    Review.SharedAppraiser sh on sh.ReviewHeaderId = rh.ReviewHeaderId and sh.isActive = 1 "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewCycle RC "
						+ " 	on RC.ReviewCycleId = rh.ReviewCycleId "
						+ " 	LEFT JOIN "
						+ "		Gal.Grade grade ON rh.CurrentGradeId = grade.GradeId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Designation desg ON rh.CurrentDesgId = desg.Id "
						+ "		LEFT JOIN "
						+ " 	Gal.Employee emp ON rh.EmployeeId = emp.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee mgr ON rh.MainAppraiserEmployeeId = mgr.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedBy ON sh.AssignedByEmployeeId = assignedBy.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Gal.Employee assignedTo ON sh.AssignedToEmployeeId = assignedTo.EmpId "
						+ " 	LEFT JOIN "
						+ " 	Review.ReviewStatus stat ON rh.ReviewStatusCode = stat.ReviewStatusCode "
						+ " 	Where rh.reviewCycleId=:reviewCycleId and rh.reviewHeaderId in"
						+ "		(select sh1.reviewHeaderId from Review.SharedAppraiser as sh1 where sh1.assignedToEmployeeId=:sharedWithEmployeeId and sh1.isActive='true' and emp.isActive = 'true')";
		if (null != searchValue && !searchValue.equals("")) {
			searchValue = searchValue.toUpperCase();
			queryString=queryString+" and (upper(emp.displayName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%')";
			queryStringforCount=queryStringforCount+" and (upper(emp.displayName) like '"+searchValue+"%' or upper(stat.ReviewStatusDescription) like '"+searchValue+"%' or upper(mgr.DisplayName) like '"+searchValue+"%')";
		}
		
		if(pageDisplayLength==-1)
			queryString=queryString+") SELECT * FROM query";
		else
			queryString=queryString+") SELECT * FROM query WHERE rowNum BETWEEN :min AND :max";
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(ReviewFormLinkVO.class));
		query.setParameter("reviewCycleId", reviewCycleId);
		if(pageDisplayLength!=-1){
			query.setParameter("min", displayLength+1);
			query.setParameter("max", displayLength+pageDisplayLength);
			}
		query.setParameter("sharedWithEmployeeId", sharedWithEmployeeId);
		
		reviewFormLinks = query.list();
		
		query = session.createSQLQuery(queryStringforCount);
		query.setParameter("sharedWithEmployeeId", sharedWithEmployeeId);
		query.setParameter("reviewCycleId", reviewCycleId);
		
		Long totalRecords=((Integer)query.uniqueResult()).longValue();
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(totalRecords);
		reviewFormLinkVOWrapper.setiTotalRecords(totalRecords);
		
		return reviewFormLinks;
	}

	@Override
	public List<ReviewFormLinkVO> getReviewFormIndexOfPeersByReviewCycleId(
			int mainAppraiserEmployeeId, int reviewCycleId,
			Integer displayLength, Integer pageDisplayLength, String searchValue, String colIndex, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		List<ReviewFormLinkVO> reviewFormLinks = Collections.EMPTY_LIST;
		reviewFormLinks = getReviewLinksBySPROC(mainAppraiserEmployeeId, reviewCycleId, displayLength, pageDisplayLength, searchValue,colIndex,sortDirection);
		Long totalRecords=(Long)getReviewLinksCountBySPROC(mainAppraiserEmployeeId, reviewCycleId, searchValue);
		reviewFormLinkVOWrapper.setiTotalDisplayRecords(totalRecords);
		reviewFormLinkVOWrapper.setiTotalRecords(totalRecords);
		
		return reviewFormLinks;
	}

	
	private List<ReviewFormLinkVO> getReviewLinksBySPROC(int managerEmpId,
			int reviewCycleId, int start, int stop, String searchTerm, String colIndex, String sortDir) {
         Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session
				.createSQLQuery("{call Review.GetReviewLinks(:managerEmpId,:reviewCycleId,:start,:stop,:searchTerm,:colIndex,:sortDir)}");
		query.setParameter("managerEmpId", managerEmpId);
		query.setParameter("reviewCycleId", reviewCycleId);
		query.setParameter("start", start+1); // index is starting from 0 
		query.setParameter("stop", start+stop);
		query.setParameter("searchTerm", searchTerm);
		query.setParameter("colIndex", colIndex);
		query.setParameter("sortDir", sortDir);
		return  query.setResultTransformer(Transformers.aliasToBean(ReviewFormLinkVO.class)).list();
	}

private Long getReviewLinksCountBySPROC(int managerEmpId , int reviewCycleId,String searchTerm){
	
	Query query=entityManager.createNativeQuery("{call Review.GetReviewLinksCount(:managerEmpId,:reviewCycleId,:searchTerm)}");
	query.setParameter("managerEmpId", managerEmpId);
	query.setParameter("reviewCycleId", reviewCycleId);
	query.setParameter("searchTerm", searchTerm);
	Integer count= (Integer)query.getSingleResult();
	return Long.valueOf(count+"");
	
}

	public List<Integer> getLeadEmpIds() {

		Query query = entityManager
				.createNativeQuery("select Distinct LeadEmpId from Gal.Employee where LeadEmpId is not null and IsActive=1");
		List<Integer> leadEmpIds = (List<Integer>) query.getResultList();
		log.info("Lead EmpIds :" + leadEmpIds);
		return leadEmpIds;
	}

}
