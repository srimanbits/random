package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.ProficiencyLevel;
import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewCompetency;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.dao.ReviewCompetencyDAO;
import com.ggk.hrms.review.service.GradeService;
import com.ggk.hrms.review.service.ProficiencyLevelCompetencyService;
import com.ggk.hrms.review.service.ProficiencyLevelService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewCompetencyService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.vo.GradeProficiencyVO;
import com.ggk.hrms.review.ui.vo.ReviewCompetencyVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Service(value = "reviewCompetencyService")
public class ReviewCompetencyServiceImpl implements ReviewCompetencyService {

	@Autowired
	private ReviewCompetencyDAO reviewCompetencyDAO;

	@Autowired
	private ReviewHeaderService reviewHeaderService;

	@Autowired
	private ProficiencyLevelService proficiencyLevelService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private ProficiencyLevelCompetencyService proficiencyLevelCompetencyService;
	
	@Autowired
	private GradeService gradeService;

	@Override
	public List<ReviewCompetency> saveReviewCompetencies(
			List<ReviewCompetency> reviewCompetencies) {

		return reviewCompetencyDAO.saveReviewCompetencies(reviewCompetencies);
	}


	@Override
	public void convertReviewCompetencyDomainToVO(
			List<ReviewCompetency> reviewCompetencies,
			List<ReviewCompetencyVO> reviewCompetencyVOs,ReviewFormInfoVO reviewFormInfoVO) {

		for (ReviewCompetency reviewCompetency : reviewCompetencies) {

			ReviewCompetencyVO reviewCompetencyVO = new ReviewCompetencyVO();

			reviewCompetencyVO.setReviewCompetencyId(reviewCompetency
					.getReviewCompetencyId());
			
			if (reviewCompetency.getExpectedProficiencyLevel() != null) {
				//selected proficiency Id
				Integer selectedProficiencyLevelValue = reviewCompetency.getExpectedProficiencyLevel().getProficiencyLevelId();
				reviewCompetencyVO
						.setExpectedProficiencyLevelId(selectedProficiencyLevelValue);
				reviewCompetencyVO
						.setExpectedBehavioralIndicator(proficiencyLevelCompetencyService
								.getBehavioralIndicator(reviewCompetency
										.getCompetency().getCompetencyId(),
										reviewCompetency
												.getExpectedProficiencyLevel()
												.getProficiencyLevelId()));
				
			} else {
				reviewCompetencyVO
						.setExpectedBehavioralIndicator("No Proficiency level selected.");

			}
			
			// Expected Proficiency Id
			ProficiencyLevel selectedProficiencyLevel = reviewCompetency.getExpectedProficiencyLevelIdAsPerGrade();
			if(selectedProficiencyLevel != null) {
				reviewCompetencyVO.setExpectedProficiencyLevelIdAsPerGrade(selectedProficiencyLevel.getProficiencyLevelId());
			}
			// Competency competency = reviewCompetency.getCompetency();
			reviewCompetencyVO.setCompetencyName(reviewCompetency
					.getCompetency().getCompetencyName());
			reviewCompetencyVO.setCompetencyDefinition(reviewCompetency
					.getCompetency().getDefinition());
			reviewCompetencyVO.setCompetencyId(reviewCompetency.getCompetency()
					.getCompetencyId());

			if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED
					|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2
					|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2
					|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE2_COMPLETED) {// copy
																									// below
																									// fields
																									// only
																									// if
																									// appraisal
																									// form
																									// in
																									// second
																									// phase

				if (reviewCompetency.getAppraiseComment() != null) {
					reviewCompetencyVO.setAppraiseCommentText(reviewCompetency
							.getAppraiseComment().getCommentText());
				} else {
					reviewCompetencyVO.setAppraiseCommentText(null);

				}
				if (reviewCompetency.getAppraiserComment() != null) {
					reviewCompetencyVO.setAppraiserCommentText(reviewCompetency
							.getAppraiserComment().getCommentText());
				} else {
					reviewCompetencyVO.setAppraiserCommentText(null);

				}

				if (reviewCompetency.getAppraiseRating() != null) {
					reviewCompetencyVO.setAppraiseRatingId(reviewCompetency
							.getAppraiseRating().getRatingId());
				}
				if (reviewCompetency.getAppraiserRating() != null) {
					reviewCompetencyVO.setAppraiserRatingId(reviewCompetency
							.getAppraiserRating().getRatingId());
				}				
			}
			reviewCompetencyVOs.add(reviewCompetencyVO);
		}
		
	}

	@Override
	public void convertReviewCompetencyVOToDomain(
			List<ReviewCompetencyVO> reviewCompetencyVOs,
			List<ReviewCompetency> reviewCompetencies,
			ReviewFormInfoVO reviewFormInfoVO) {

		String reviewFormRole = reviewFormInfoVO.getReviewFormRole()
				.getDescription();
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewFormInfoVO.getReviewHeaderId());
		String reviewStatus = reviewHeader.getReviewStatus()
				.getReviewStatusCode();

		for (int i = 0; i < reviewCompetencies.size(); i++) {

			for (int j = 0; j < reviewCompetencyVOs.size(); j++) {

				if (reviewCompetencies.get(i).getReviewCompetencyId() == reviewCompetencyVOs
						.get(j).getReviewCompetencyId()) {
					if (reviewFormRole.equals("APPRAISE")) {
						
						if (reviewStatus.equals("NOT_STARTED")
								|| reviewStatus
										.equals("GOALS_SETTING_IN_PROGRESS")
								|| reviewStatus.equals("NEED_TO_EDIT_GOALS")) {
							
							if (reviewCompetencyVOs.get(i)
									.getExpectedProficiencyLevelId() != -1) {

								ProficiencyLevel proficiencyLevel = proficiencyLevelService
										.getProficiencyLevelById(reviewCompetencyVOs
												.get(i)
												.getExpectedProficiencyLevelId());
								reviewCompetencies.get(i)
										.setExpectedProficiencyLevel(
												proficiencyLevel);

							}
							if (reviewCompetencyVOs.get(i)
									.getExpectedProficiencyLevelId() == -1) {

								reviewCompetencies.get(i)
										.setExpectedProficiencyLevel(null);
							}
							
							
							
						}

						if (reviewStatus.equals("APPRAISAL_IN_PROGRESS")
								|| reviewStatus
										.equals("NEED_TO_EDIT_APPRAISAL")
								|| reviewStatus.equals("GOALS_ACCEPTED")) {
							
							if (reviewCompetencyVOs.get(i)
									.getAppraiseCommentText() == null
									|| reviewCompetencyVOs.get(i)
									.getAppraiseCommentText().trim().equals("")) {
								reviewCompetencies.get(i).setAppraiseComment(null);
							} else {
								if (reviewCompetencies.get(i).getAppraiseComment() == null) {
									reviewCompetencies.get(i).setAppraiseComment(new Comment());
								}
								reviewCompetencies.get(i).getAppraiseComment().setCommentText(
										reviewCompetencyVOs.get(i)
										.getAppraiseCommentText());
							}

							if (reviewCompetencyVOs.get(i)
									.getAppraiseRatingId() != -1) {
								Rating rating = ratingService
										.getRatingById(reviewCompetencyVOs.get(
												i).getAppraiseRatingId());
								reviewCompetencies.get(i).setAppraiseRating(
										rating);

							}
							if (reviewCompetencyVOs.get(i)
									.getAppraiseRatingId() == -1) {

								reviewCompetencies.get(i).setAppraiseRating(
										null);
							}
						}

						if (reviewStatus.equals("APPRAISAL_SUBMITTED")
								|| reviewStatus
										.equals("ASSESSMENT_IN_PROGRESS")) {
							// do nothing

						}

					}

					else {
						
						if (reviewStatus.equals("APPRAISAL_IN_PROGRESS")
								|| reviewStatus
										.equals("NEED_TO_EDIT_APPRAISAL")) {
							// do nothing
						}

						if (reviewStatus.equals("GOALS_SUBMITTED")) {
							
							if (reviewCompetencyVOs.get(i)
									.getExpectedProficiencyLevelId() != -1) {

								ProficiencyLevel proficiencyLevel = proficiencyLevelService
										.getProficiencyLevelById(reviewCompetencyVOs
												.get(i)
												.getExpectedProficiencyLevelId());
								reviewCompetencies.get(i)
										.setExpectedProficiencyLevel(
												proficiencyLevel);

							}
							if (reviewCompetencyVOs.get(i)
									.getExpectedProficiencyLevelId() == -1) {

								reviewCompetencies.get(i)
										.setExpectedProficiencyLevel(null);
							}
							
						}

						if (reviewStatus.equals("APPRAISAL_SUBMITTED")
								|| reviewStatus
										.equals("ASSESSMENT_IN_PROGRESS")) {
							if (reviewCompetencyVOs.get(i)
									.getAppraiserCommentText() == null
									|| reviewCompetencyVOs.get(i)
									.getAppraiserCommentText().trim().equals("")) {
								reviewCompetencies.get(i).setAppraiserComment(null);
							} else {
								if (reviewCompetencies.get(i).getAppraiserComment() == null) {
									reviewCompetencies.get(i).setAppraiserComment(new Comment());
								}
								reviewCompetencies.get(i).getAppraiserComment().setCommentText(
										reviewCompetencyVOs.get(i)
										.getAppraiserCommentText());
							}

							if (reviewCompetencyVOs.get(i)
									.getAppraiserRatingId() != -1) {
								Rating rating = ratingService
										.getRatingById(reviewCompetencyVOs.get(
												i).getAppraiserRatingId());
								reviewCompetencies.get(i).setAppraiserRating(
										rating);

							}

							if (reviewCompetencyVOs.get(i)
									.getAppraiserRatingId() == -1) {

								reviewCompetencies.get(i).setAppraiserRating(
										null);
							}

						}

					}

				}
			}
		}

	}

	@Override
	public void createReviewCompetencies(int reviewHeaderId,ReviewFormRole owner) {

		reviewCompetencyDAO.createReviewCompetencies(reviewHeaderId,SecurityDetailsAccessor.getEmpId(),owner);
		
	}

	@Override
	public List<ReviewCompetencyVO> getReviewCompetencyVOByHeaderId(int reviewHeaderId, ReviewFormRole owner,String requestType) { 
		List<ReviewCompetencyVO> reviewCompetencyVOs = new ArrayList<ReviewCompetencyVO>();
		List<ReviewCompetency> reviewCompetencies =	reviewCompetencyDAO.getReviewCompetencies(reviewHeaderId, owner);
		for(ReviewCompetency reviewCompetency : reviewCompetencies) {
			ReviewCompetencyVO reviewCompetencyVO = new ReviewCompetencyVO();
			reviewCompetencyVO.setCompetencyName(reviewCompetency.getCompetency().getCompetencyName());
			reviewCompetencyVO.setCompetencyDefinition(reviewCompetency.getCompetency().getDefinition());
			if(reviewCompetency.getExpectedProficiencyLevel() == null) {
				reviewCompetencyVO.setProficiencyLevelNumber(null);
				reviewCompetencyVO.setProficiencyLevelName(null);
			} else {
				reviewCompetencyVO.setProficiencyLevelNumber(reviewCompetency.getExpectedProficiencyLevel().getProficiencyLevelNumber());
				reviewCompetencyVO.setProficiencyLevelName(reviewCompetency.getExpectedProficiencyLevel().getProficiencyLevelName());
			}
			if (reviewCompetency.getExpectedProficiencyLevel() != null) {
				reviewCompetencyVO.setExpectedBehavioralIndicator(
						proficiencyLevelCompetencyService.getBehavioralIndicator(
								reviewCompetency.getCompetency().getCompetencyId(), reviewCompetency.getExpectedProficiencyLevel().getProficiencyLevelId()));
			}
			//Added this to facilitate download option for super user
			if (requestType.equals("SYS_GENERATED") == false) {
				if (reviewCompetency.getAppraiseComment() != null) {
					reviewCompetencyVO.setAppraiseCommentText(reviewCompetency
							.getAppraiseComment().getCommentText());
				}
				if (reviewCompetency.getAppraiserComment() != null) {
					reviewCompetencyVO.setAppraiserCommentText(reviewCompetency
							.getAppraiserComment().getCommentText());
				}

				if (reviewCompetency.getAppraiseRating() != null) {
					reviewCompetencyVO.setAppraiseRatingId(reviewCompetency
							.getAppraiseRating().getRating());
					reviewCompetencyVO.setAppraiseRatingStr(reviewCompetency
							.getAppraiseRating().getRating()+"-"+reviewCompetency
							.getAppraiseRating().getShortDescription());
				}
				if (reviewCompetency.getAppraiserRating() != null) {
					reviewCompetencyVO.setAppraiserRatingId(reviewCompetency
							.getAppraiserRating().getRatingId());
					reviewCompetencyVO.setAppraiserRatingStr(reviewCompetency
							.getAppraiserRating().getRating()+"-"+reviewCompetency
							.getAppraiserRating().getShortDescription());
				}

			}
			reviewCompetencyVOs.add(reviewCompetencyVO);
		}
		return reviewCompetencyVOs;
	}

	@Override
	public List<ReviewCompetency> getReviewCompetencies(int reviewHeaderId,
			ReviewFormRole owner) {
		return reviewCompetencyDAO.getReviewCompetencies(reviewHeaderId, owner);
	}

	@Override
	public void copyReviewCompetencies(int reviewHeaderId,
			ReviewFormRole owner, String actionType) {

     reviewCompetencyDAO.copyReviewCompetencies(reviewHeaderId, owner, actionType);
		
	}


	@Override
	public List<GradeProficiencyVO> getGradeProficiencyByCompetencyAndDept(
			int competency, int dept) {
		return reviewCompetencyDAO.getGradeProficiencyBycompetencyAndDept(competency,dept);
	}


	@Override
	public void insertReviewCompetency(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept) {
		reviewCompetencyDAO.insertReviewCompetency(gradeProficiencyVO,competency,dept);
	}


	@Override
	public void updateReviewCompetancy(GradeProficiencyVO gradeProficiencyVO,
			int competency, int dept) {
		reviewCompetencyDAO.updateReviewCompetancy(gradeProficiencyVO,competency,dept);
		
	}


	@Override
	public void deleteReviewCompetancy(Integer gradeCompetencyExpectationId) {
		reviewCompetencyDAO.deleteReviewCompetancy(gradeCompetencyExpectationId);
		
	}


	@Override
	public void createFinalGradeProficiencyList(
			List<GradeProficiencyVO> gradeProficiencyVOsFrmUI,
			List<GradeProficiencyVO> gradeProficiencyVOsFrmDB) {
		Map<Integer,Integer> gradeAndCompetencyExpectationIdMap=new HashMap<Integer, Integer>();
		for(GradeProficiencyVO gradeProficiencyVO: gradeProficiencyVOsFrmDB){
			if(gradeProficiencyVO.getGradeCompetencyExpectationId()!=null){
				gradeAndCompetencyExpectationIdMap.put(gradeProficiencyVO.getGradeId(),gradeProficiencyVO.getGradeCompetencyExpectationId());
			}
		}
		
		for(GradeProficiencyVO gradeProficiencyVO:gradeProficiencyVOsFrmUI){
			if(gradeAndCompetencyExpectationIdMap.containsKey(gradeProficiencyVO.getGradeId())){
				gradeProficiencyVO.setGradeCompetencyExpectationId(gradeAndCompetencyExpectationIdMap.get(gradeProficiencyVO.getGradeId()));
			}else{
				gradeProficiencyVO.setGradeCompetencyExpectationId(null);
			}
		}
		
	}
	
	
}