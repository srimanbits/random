package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.Project;
import com.ggk.hrms.review.beans.domain.ReviewObjective;
import com.ggk.hrms.review.beans.domain.ReviewObjectiveProject;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.dao.ProjectDAO;
import com.ggk.hrms.review.dao.ReviewObjectiveDAO;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewObjectiveService;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveProjectVO;
import com.ggk.hrms.review.ui.vo.ReviewObjectiveVO;

@Service("reviewObjectiveService")
public class ReviewObjectiveServiceImpl implements ReviewObjectiveService {

	@Resource
	private ReviewObjectiveDAO reviewObjectiveDao;

	/*@Resource
	private RatingDAO ratingDao;*/

	@Autowired
	private RatingService ratingService;
	
	@Resource
	private ProjectDAO projectDAO;

	@Override
	public void persist(ReviewObjective reviewObj) {
		reviewObjectiveDao.persist(reviewObj);
	}

	@Override
	public boolean remove(int reviewObjId) {
		try {
			reviewObjectiveDao.remove(reviewObjId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void merge(List<ReviewObjective> reviewObjs) {
		reviewObjectiveDao.merge(reviewObjs);
	}

	@Override
	public void copyAppraisePhase1Fields(ReviewObjective domainReviewObj,
			ReviewObjectiveVO voReviewObj, ReviewFormInfoVO reviewFormInfoVO) {

		String objectiveName = voReviewObj.getObjectiveName().trim();
		// objectiveName=objectiveName.replace("\n", "");
		// this is to calculate the no of new line characters and add them to
		// the limit (as it takes 2 chars)
		objectiveName = objectiveName.replaceAll(
				"^\\s+|\\s+$|\\s*(\n)\\s*|(\\s)\\s*", "$1$2")
				.replace("\t", " ");
		String[] objectiveNameArray = objectiveName.split("\\n");
		if (objectiveName.length() > 200 + objectiveNameArray.length - 1) {
			objectiveName = objectiveName.substring(0,
					200 + objectiveNameArray.length - 1);
		}

		domainReviewObj.setObjectiveName(objectiveName);
		/*
		 * if (voReviewObj.getProjectId() == -1) { // phase-2 requirement
		 * change. user should be able to select multiple projects for single
		 * objective domainReviewObj.setProject(null); } else { Project project
		 * = new Project(); project.setId(voReviewObj.getProjectId());
		 * domainReviewObj.setProject(project); }
		 */
		syncProjects(domainReviewObj, voReviewObj, reviewFormInfoVO);

		if (voReviewObj.getSuccessCriteriaComment() == null
				|| voReviewObj.getSuccessCriteriaComment().trim().equals("")) {
			domainReviewObj.setSuccessCriteriaComment(null);
		} else {
			if (domainReviewObj.getSuccessCriteriaComment() == null) {
				domainReviewObj.setSuccessCriteriaComment(new Comment());
			}
			domainReviewObj.getSuccessCriteriaComment().setCommentText(
					voReviewObj.getSuccessCriteriaComment());
		}

		if (voReviewObj.getDetailsComment() == null
				|| voReviewObj.getDetailsComment().trim().equals("")) {
			domainReviewObj.setDetailsComment(null);
		} else {
			if (domainReviewObj.getDetailsComment() == null) {
				domainReviewObj.setDetailsComment(new Comment());
			}
			domainReviewObj.getDetailsComment().setCommentText(
					voReviewObj.getDetailsComment());
		}

	
		if (voReviewObj.getTargetCompletionDate() != null
				&& (voReviewObj.getTargetCompletionDate().after(
						reviewFormInfoVO.getReviewCycleStartDate()) || voReviewObj
						.getTargetCompletionDate().equals(
								reviewFormInfoVO.getReviewCycleStartDate()))) {
			domainReviewObj.setTargetCompletionDate(voReviewObj
					.getTargetCompletionDate());

		}

		domainReviewObj.setLastModifiedDate(new Date());
	}

	@Override
	public void copyAppraisePhase2Fields(ReviewObjective domainReviewObj,
			ReviewObjectiveVO voReviewObj, ReviewFormInfoVO reviewFormInfoVO) {

		if (!domainReviewObj.isApproved()) {

			copyAppraisePhase1Fields(domainReviewObj, voReviewObj,
					reviewFormInfoVO);
		} else {
			syncProjects(domainReviewObj, voReviewObj, reviewFormInfoVO);
			domainReviewObj.setLastModifiedDate(new Date());
		}

		domainReviewObj.setAchievmentDate(voReviewObj.getAchievmentDate());

		if (voReviewObj.getHowYouAchievedComment().trim().equals("")) {
			domainReviewObj.setHowYouAchievedComment(null);
		} else {
			if (domainReviewObj.getHowYouAchievedComment() == null) {
				domainReviewObj.setHowYouAchievedComment(new Comment());
			}
			domainReviewObj.getHowYouAchievedComment().setCommentText(
					voReviewObj.getHowYouAchievedComment());
		}

		if (voReviewObj.getAppraiseNegativeComment().trim().equals("")) {
			domainReviewObj.setAppraiseNegativeComment(null);
		} else {
			if (domainReviewObj.getAppraiseNegativeComment() == null) {
				domainReviewObj.setAppraiseNegativeComment(new Comment());
			}
			domainReviewObj.getAppraiseNegativeComment().setCommentText(
					voReviewObj.getAppraiseNegativeComment());
		}

		if (voReviewObj.getAppraisePositiveComment().trim().equals("")) {
			domainReviewObj.setAppraisePositiveComment(null);
		} else {
			if (domainReviewObj.getAppraisePositiveComment() == null) {
				domainReviewObj.setAppraisePositiveComment(new Comment());
			}
			domainReviewObj.getAppraisePositiveComment().setCommentText(
					voReviewObj.getAppraisePositiveComment());
		}

		if (voReviewObj.getAppraiseRatingId() == -1) {
			domainReviewObj.setAppraiseRating(null);
		} else {
			domainReviewObj.setAppraiseRating(ratingService
					.getRatingById(voReviewObj.getAppraiseRatingId()));
		}

		

	}

	@Override
	public void copyAppraiserPhase2Fields(ReviewObjective domainReviewObj,
			ReviewObjectiveVO voReviewObj, ReviewFormInfoVO reviewFormInfoVO) {

		if (voReviewObj.getAppraiserNegativeComment().trim().equals("")) {
			domainReviewObj.setAppraiserNegativeComment(null);
		} else {
			if (domainReviewObj.getAppraiserNegativeComment() == null) {
				domainReviewObj.setAppraiserNegativeComment(new Comment());
			}
			domainReviewObj.getAppraiserNegativeComment().setCommentText(
					voReviewObj.getAppraiserNegativeComment());
		}

		if (voReviewObj.getAppraiserPositiveComment().trim().equals("")) {
			domainReviewObj.setAppraiserPositiveComment(null);
		} else {
			if (domainReviewObj.getAppraiserPositiveComment() == null) {
				domainReviewObj.setAppraiserPositiveComment(new Comment());
			}
			domainReviewObj.getAppraiserPositiveComment().setCommentText(
					voReviewObj.getAppraiserPositiveComment());
		}

		if (voReviewObj.getAppraiserRatingId() == -1) {
			domainReviewObj.setAppraiserRating(null);
		} else {
			domainReviewObj.setAppraiserRating(ratingService
					.getRatingById(voReviewObj.getAppraiserRatingId()));
		}
		domainReviewObj.setLastModifiedDate(new Date());
	}

	@Override
	public void copyToVOBean(ReviewObjectiveVO voReviewObj,
			ReviewObjective domainReviewObj, ReviewFormInfoVO reviewFormInfoVO) {

		if (domainReviewObj.getDetailsComment() != null) {
			voReviewObj.setDetailsComment(domainReviewObj.getDetailsComment()
					.getCommentText());
		}
		if (domainReviewObj.getSuccessCriteriaComment() != null) {
			voReviewObj.setSuccessCriteriaComment(domainReviewObj
					.getSuccessCriteriaComment().getCommentText());
		}
		/*
		 * if (domainReviewObj.getProject() != null) {
		 * voReviewObj.setProjectId(domainReviewObj.getProject().getId()); }
		 */
		// project multi select code...

		if (domainReviewObj.getProjects() != null
				&& domainReviewObj.getProjects().size() > 0) {

			voReviewObj.setProjectIds(new ArrayList<Integer>());
			voReviewObj
					.setReviewObjectiveProjectVOs(new ArrayList<ReviewObjectiveProjectVO>());

			for (ReviewObjectiveProject reviewObjectiveProject : domainReviewObj
					.getProjects()) {

				voReviewObj.getProjectIds().add(
						reviewObjectiveProject.getProject().getId());
				ReviewObjectiveProjectVO reviewObjectiveProjectVO = new ReviewObjectiveProjectVO();
				reviewObjectiveProjectVO.setProjectId(reviewObjectiveProject
						.getProject().getId());
				reviewObjectiveProjectVO.setProjectName(reviewObjectiveProject
						.getProject().getName());
				reviewObjectiveProjectVO.setApproved(reviewObjectiveProject
						.isApproved());

				voReviewObj.getReviewObjectiveProjectVOs().add(
						reviewObjectiveProjectVO);
			}

		}

		// project multi select code...

		voReviewObj.setObjectiveName(domainReviewObj.getObjectiveName());
		voReviewObj.setReviewHeaderId(domainReviewObj.getReviewHeader()
				.getReviewHeaderId());
		voReviewObj
				.setReviewObjectiveId(domainReviewObj.getReviewObjectiveId());
		voReviewObj.setStartDate(domainReviewObj.getStartDate());
		voReviewObj.setTargetCompletionDate(domainReviewObj
				.getTargetCompletionDate());
		voReviewObj.setReviewPhase(domainReviewObj.getReviewPhase()
				.getDescription());
		
			voReviewObj.setApproved(domainReviewObj.isApproved());
		

		

		voReviewObj.setReviewStatus(domainReviewObj.getReviewStatus());
		voReviewObj.setCreatedBy(domainReviewObj.getCreatedBy()
				.getDescription());

		if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED
				|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2
				|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2
				|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE2_COMPLETED) {
			if (domainReviewObj.getAppraiserNegativeComment() != null) {
				voReviewObj.setAppraiserNegativeComment(domainReviewObj
						.getAppraiserNegativeComment().getCommentText());
			}
			if (domainReviewObj.getAppraiserPositiveComment() != null) {
				voReviewObj.setAppraiserPositiveComment(domainReviewObj
						.getAppraiserPositiveComment().getCommentText());
			}
			if (domainReviewObj.getAppraiserRating() != null) {
				voReviewObj.setAppraiserRatingId(domainReviewObj
						.getAppraiserRating().getRatingId());
			}

			voReviewObj.setAchievmentDate(domainReviewObj.getAchievmentDate());

			if (domainReviewObj.getHowYouAchievedComment() != null) {
				voReviewObj.setHowYouAchievedComment(domainReviewObj
						.getHowYouAchievedComment().getCommentText());
			}

			if (domainReviewObj.getAppraiseNegativeComment() != null) {
				voReviewObj.setAppraiseNegativeComment(domainReviewObj
						.getAppraiseNegativeComment().getCommentText());
			}
			if (domainReviewObj.getAppraisePositiveComment() != null) {
				voReviewObj.setAppraisePositiveComment(domainReviewObj
						.getAppraisePositiveComment().getCommentText());
			}
			if (domainReviewObj.getAppraiseRating() != null) {
				voReviewObj.setAppraiseRatingId(domainReviewObj
						.getAppraiseRating().getRatingId());
			}
		}

	}

	/*
	 * @Override public void copyAppraiserPhase1Fields(ReviewObjective
	 * domainReviewObj, ReviewObjectiveVO voReviewObj, ReviewFormInfoVO
	 * reviewFormInfoVO) {
	 * 
	 * 
	 * }
	 */

	@Override
	public int getNoOfObjectives(int reviewHeaderId, ReviewFormRole owner) {
		return reviewObjectiveDao.getNoOfObjectives(reviewHeaderId, owner);
	}

	@Override
	//@Cacheable(value ="reviewObjective", key = "'getReviewObjective_'+#reviewObjectiveId")
	public ReviewObjective getReviewObjective(int reviewObjectiveId) {
		return reviewObjectiveDao.getReviewObjective(reviewObjectiveId);
	}

	/*
	 * @Override public int getNoOfNotApplicableObjectives(int reviewHeaderId) {
	 * return reviewObjectiveDao
	 * .getNoOfNotApplicableObjectives(reviewHeaderId); }
	 */

	public List<ReviewObjectiveVO> getReviewObjectiveVOByHeaderId(int reviewHeaderId, ReviewFormRole owner,String requestType) {
		List<ReviewObjectiveVO> reviewObjectiveVOs = new ArrayList<ReviewObjectiveVO>();
		List<ReviewObjective> reviewObjectives = reviewObjectiveDao.getReviewObjectives(reviewHeaderId, owner);
		for (ReviewObjective reviewObjective : reviewObjectives) {
			ReviewObjectiveVO reviewObjectiveVO = new ReviewObjectiveVO();
			reviewObjectiveVO.setObjectiveName(reviewObjective.getObjectiveName());
			if (reviewObjective.getProject() != null) {
				reviewObjectiveVO.setProjectNames(reviewObjective.getProject().getName());
			}
			if(reviewObjective.getProjects().size() > 0 ) {
				StringBuilder projectNames = new StringBuilder();
				for(ReviewObjectiveProject objectiveProject : reviewObjective.getProjects()) {
					if(objectiveProject.getProject() != null) {
						projectNames.append(" ");
						projectNames.append(objectiveProject.getProject().getName());
				    	projectNames.append(",");
					}
					reviewObjectiveVO.setProjectNames(projectNames.length() > 0 ? projectNames.substring(1, projectNames.length() - 1): "");
				}
			}
			if (reviewObjective.getDetailsComment() != null) {
				reviewObjectiveVO.setDetailsComment(reviewObjective.getDetailsComment().getCommentText());
			}
			if (reviewObjective.getSuccessCriteriaComment() != null) {
				reviewObjectiveVO.setSuccessCriteriaComment(reviewObjective.getSuccessCriteriaComment().getCommentText());
			}
			reviewObjectiveVO.setTargetCompletionDate(reviewObjective.getTargetCompletionDate());
			//Added this to facilitate download option for super user
			if (requestType.equals("SYS_GENERATED")== false) {
				if (reviewObjective.getHowYouAchievedComment() != null) {
					reviewObjectiveVO.setHowYouAchievedComment(reviewObjective
							.getHowYouAchievedComment().getCommentText());
				}
				if (reviewObjective.getHowYouAchievedComment() != null) {
					reviewObjectiveVO.setHowYouAchievedComment(reviewObjective
							.getHowYouAchievedComment().getCommentText());
				}
				reviewObjectiveVO.setAchievmentDate(reviewObjective
						.getAchievmentDate());
				if (reviewObjective.getAppraisePositiveComment() != null) {
					reviewObjectiveVO
							.setAppraisePositiveComment(reviewObjective
									.getAppraisePositiveComment()
									.getCommentText());
				}
				if (reviewObjective.getAppraiseNegativeComment() != null) {
					reviewObjectiveVO
							.setAppraiseNegativeComment(reviewObjective
									.getAppraiseNegativeComment()
									.getCommentText());
				}
				if (reviewObjective.getAppraiserPositiveComment() != null) {
					reviewObjectiveVO
							.setAppraiserPositiveComment(reviewObjective
									.getAppraiserPositiveComment()
									.getCommentText());
				}
				if (reviewObjective.getAppraiserNegativeComment() != null) {
					reviewObjectiveVO
							.setAppraiserNegativeComment(reviewObjective
									.getAppraiserNegativeComment()
									.getCommentText());
				}
				if (reviewObjective.getAppraiserRating() != null) {

					reviewObjectiveVO.setAppraiserRatingId(reviewObjective
							.getAppraiserRating().getRating());
					reviewObjectiveVO.setAppraiserRatingStr(reviewObjective
							.getAppraiserRating().getRating()+"-"+reviewObjective.getAppraiserRating().getShortDescription());
				}
				if (reviewObjective.getAppraiseRating() != null) {

					reviewObjectiveVO.setAppraiseRatingId(reviewObjective
							.getAppraiseRating().getRating());
					reviewObjectiveVO.setAppraiseRatingStr(reviewObjective
							.getAppraiseRating().getRating()+"-"+reviewObjective.getAppraiseRating().getShortDescription());
				}
			}
			reviewObjectiveVOs.add(reviewObjectiveVO);
		}
		return reviewObjectiveVOs;
	}

	@Override
	public int getNoOfUnApprovedObjectives(int reviewHeaderId, ReviewFormRole owner) {
		return reviewObjectiveDao.getNoOfUnApprovedObjectives(reviewHeaderId, owner);
	}

	@Override
	public List<ReviewObjective> getReviewObjectives(int reviewHeaderId,
			ReviewFormRole owner) {
		return reviewObjectiveDao.getReviewObjectives(reviewHeaderId, owner);
	}

	@Override
	public void copyReviewObjectives(int reviewHeaderId, ReviewFormRole owner,
			String actionType) {
		reviewObjectiveDao.copyReviewObjectives(reviewHeaderId, owner,
				actionType);
	}

	private void syncProjects(ReviewObjective domainReviewObj,
			ReviewObjectiveVO voReviewObj, ReviewFormInfoVO reviewFormInfoVO) {

		Map<Integer, Project> availableProjects = null;
		List<Integer> projectsToDelete = null;
		
		/*if (voReviewObj.getProjectIds() != null
				&& voReviewObj.getProjectIds().size() > 0
				&& (domainReviewObj.getProjects() == null || domainReviewObj
						.getProjects().size() == 0)) {

			
			 * domainReviewObj.setProjects(new
			 * ArrayList<ReviewObjectiveProject>());
			 

			for (Integer voProjectId : voReviewObj.getProjectIds()) {

				ReviewObjectiveProject newreviewObjectiveProject = new ReviewObjectiveProject();
				Project project = availableProjects.get(voProjectId);
				newreviewObjectiveProject.setReviewObjective(domainReviewObj);
				newreviewObjectiveProject.setProject(project);
				if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
					newreviewObjectiveProject
							.setCreatedBy(ReviewFormRole.APPRAISE);
				} else {
					newreviewObjectiveProject
							.setCreatedBy(ReviewFormRole.APPRAISER);

				}
				domainReviewObj.getProjects().add(newreviewObjectiveProject);

			}

		}*/

		if (voReviewObj.getProjectIds() != null
				&& voReviewObj.getProjectIds().size() > 0) {

			availableProjects = projectDAO.getProjects();
			projectsToDelete = new ArrayList<Integer>();

			for (Integer projectId : voReviewObj.getProjectIds()) {
				
				boolean isNew = true;
				for (ReviewObjectiveProject reviewObjectiveProject : domainReviewObj.getProjects()) {
					if (reviewObjectiveProject.getProject().getId() == projectId) {
						isNew = false;
						break;
					}
				}
				if (isNew) {
					ReviewObjectiveProject reviewObjectiveProject = new ReviewObjectiveProject();
					reviewObjectiveProject.setReviewObjective(domainReviewObj);
					reviewObjectiveProject.setProject(availableProjects.get(projectId));
					/*if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {
						reviewObjectiveProject
								.setCreatedBy(ReviewFormRole.APPRAISE);
					} else {
						reviewObjectiveProject
								.setCreatedBy(ReviewFormRole.APPRAISER);

					}*/
					reviewObjectiveProject.setReviewPhase(reviewFormInfoVO.getReviewPhase());
					domainReviewObj.getProjects().add(reviewObjectiveProject);
				}

			}
			
			for (ReviewObjectiveProject reviewObjectiveProject : domainReviewObj.getProjects()) {
				
				boolean canDelete = true;
				if(!reviewObjectiveProject.isApproved()) {
					
					for (Integer voProjectId : voReviewObj.getProjectIds()) {
						
						if (voProjectId == reviewObjectiveProject.getProject().getId()) {
							canDelete = false;
							break;
						}
					}
				}
				if (canDelete) {
					projectsToDelete.add(reviewObjectiveProject.getProject().getId());
				}
			}

			Iterator<ReviewObjectiveProject> reviewObjectiveProjectIterator = domainReviewObj
					.getProjects().iterator();
			while (reviewObjectiveProjectIterator.hasNext()) {
				ReviewObjectiveProject reviewObjectiveProjectToBeDeleted = reviewObjectiveProjectIterator
						.next();
				if (projectsToDelete.contains(reviewObjectiveProjectToBeDeleted
						.getProject().getId())) {
					if (!reviewObjectiveProjectToBeDeleted.isApproved()) {
						reviewObjectiveProjectIterator.remove();
					}
				}
			}
		}

		if ((voReviewObj.getProjectIds() == null || voReviewObj.getProjectIds()
				.size() == 0) && domainReviewObj.getProjects() != null) {
			Iterator<ReviewObjectiveProject> reviewObjectiveProjectIterator = domainReviewObj
					.getProjects().iterator();
			while (reviewObjectiveProjectIterator.hasNext()) {
				ReviewObjectiveProject reviewObjectiveProjectToBeDeleted = reviewObjectiveProjectIterator
						.next();
				if (!reviewObjectiveProjectToBeDeleted.isApproved()) {
					reviewObjectiveProjectIterator.remove();
				}
			}

		}

	}

}
