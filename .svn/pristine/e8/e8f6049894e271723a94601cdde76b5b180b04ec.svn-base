package com.ggk.hrms.review.dao.hibernate;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ggk.hrms.review.beans.domain.ReviewCompetency;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.dao.ReviewCompetencyDAO;
import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;

@Repository(value = "reviewCompetencyDAO")
public class ReviewCompetencyDAOImpl implements ReviewCompetencyDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public List<ReviewCompetency> saveReviewCompetencies(
			List<ReviewCompetency> reviewCompetencies) {
		
		try {
			for (int i = 0; i < reviewCompetencies.size(); i++) {

				ReviewCompetency reviewCompetency = entityManager
						.merge(reviewCompetencies.get(i));
				reviewCompetencies.set(i, reviewCompetency);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reviewCompetencies;
	}

	@Override
	@Transactional
	public void createReviewCompetencies(int reviewHeaderId,int employeeId,ReviewFormRole owner) {
		Query query=entityManager.createNativeQuery("{call Review.CreateReviewCompetencies(:reviewHeaderId,:employeeId,:owner)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("employeeId", employeeId);
		if(owner==ReviewFormRole.APPRAISE){			
			query.setParameter("owner", ReviewFormRole.APPRAISE.getDescription());
		}
		else {			
			query.setParameter("owner", ReviewFormRole.APPRAISER.getDescription());
		}
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReviewCompetency> getReviewCompetencies(int reviewHeaderId,
			ReviewFormRole owner) {
		Query query = entityManager
				.createQuery("from ReviewCompetency ro where ro.reviewHeader.reviewHeaderId=:reviewHeaderId and ro.Owner=:createdBy");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		if(owner==ReviewFormRole.APPRAISE){			
			query.setParameter("createdBy", ReviewFormRole.APPRAISE);
		}
		else {			
			query.setParameter("createdBy", ReviewFormRole.APPRAISER);
		}
		return query.getResultList();
	}

	@Override
	@Transactional
	public void copyReviewCompetencies(int reviewHeaderId,
			ReviewFormRole owner, String actionType) {
		Query query=entityManager.createNativeQuery("{call Review.CopyReviewCompetencies(:reviewHeaderId,:target,:actionType)}");
		query.setParameter("reviewHeaderId", reviewHeaderId);
		query.setParameter("actionType", actionType);
		if(owner==ReviewFormRole.APPRAISE){
			
			query.setParameter("target", "APPRAISER");
		}
		else {
			
			query.setParameter("target", "APPRAISE");
		}
		query.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GradeProficiencyVO> getGradeProficiencyBycompetencyAndDept(
			int competency, int dept) {

		String queryString = "SELECT grd.gradeId gradeId,grd.grade gradeName,plc.ProficiencyLevelId expectedProficiencyLevelAsPerGrade,gce.GradeCompetencyExpectationId gradeCompetencyExpectationId"
				+ " FROM Review.ProficiencyLevelCompetency plc"
				+ " JOIN Review.GradeCompetencyExpectation gce"
				+ " ON plc.ProficiencyLevelCompetencyId=gce.ProficiencyLevelCompetencyId AND gce.DeptId=:deptId AND plc.CompetencyId=:competencyId"
				+ " RIGHT JOIN Gal.Grade grd"
				+ " ON gce.GradeId=grd.GradeId"
				+ " ORDER BY grd.GradeId";

		List<GradeProficiencyVO> gradeProficiencyVOs = Collections.EMPTY_LIST;
		Session session = (Session) entityManager.getDelegate();
		org.hibernate.Query query = session.createSQLQuery(queryString)
				.setResultTransformer(
						Transformers.aliasToBean(GradeProficiencyVO.class));
		query.setParameter("competencyId", competency);
		query.setParameter("deptId", dept);
		gradeProficiencyVOs = query.list();

		return gradeProficiencyVOs;
	}

	@Override
	@Transactional
	public void insertReviewCompetency(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept) {
		String queryString = "INSERT INTO Review.GradeCompetencyExpectation(GradeId,ProficiencyLevelCompetencyId,IsActive,CreatedDate,LastModifiedDate,DeptId) "
				+ "VALUES(:gradeId,"
				+ "(SELECT plc.ProficiencyLevelCompetencyId FROM Review.ProficiencyLevelCompetency plc WHERE plc.ProficiencyLevelId=:proficiency AND plc.CompetencyId=:competency)"
				+ ",1,getdate(),getdate(),:dept)";

		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter("proficiency",
				gradeProficiencyVO.getExpectedProficiencyLevelAsPerGrade());
		query.setParameter("competency", competency);
		query.setParameter("dept", dept);
		query.setParameter("gradeId", gradeProficiencyVO.getGradeId());
		query.executeUpdate();

	}

	@Override
	@Transactional
	public void updateReviewCompetancy(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept) {
		String queryString = "UPDATE gce "
				+ "SET gce.ProficiencyLevelCompetencyId = ("
				+ "SELECT plc.ProficiencyLevelCompetencyId FROM Review.ProficiencyLevelCompetency plc WHERE plc.ProficiencyLevelId=:proficiency AND plc.CompetencyId=:competency"
				+ "),"
				+ "gce.LastModifiedDate=getdate()  "
				+ "FROM Review.GradeCompetencyExpectation gce  "
				+ "WHERE gce.DeptId=:dept AND gce.GradeId=:gradeId AND gce.GradeCompetencyExpectationId=:gradeCompetencyExpectationId";

		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter("proficiency",
				gradeProficiencyVO.getExpectedProficiencyLevelAsPerGrade());
		query.setParameter("competency", competency);
		query.setParameter("dept", dept);
		query.setParameter("gradeId", gradeProficiencyVO.getGradeId());
		query.setParameter("gradeCompetencyExpectationId",
				gradeProficiencyVO.getGradeCompetencyExpectationId());
		query.executeUpdate();

	}

	@Override
	@Transactional
	public void deleteReviewCompetancy(Integer gradeCompetencyExpectationId) {
		String queryString = "DELETE FROM Review.GradeCompetencyExpectation WHERE GradeCompetencyExpectationId=:gradeCompetencyExpectationId";

		Query query = entityManager.createNativeQuery(queryString);
		query.setParameter("gradeCompetencyExpectationId",
				gradeCompetencyExpectationId);
		query.executeUpdate();

	}

}
