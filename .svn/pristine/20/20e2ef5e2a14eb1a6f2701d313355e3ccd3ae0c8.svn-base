/**
 * 
 */
package com.ggk.hrms.review.ui.vo;

/**
 * @author SwethaP
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReviewObjectiveVO implements Serializable,
		Comparable<ReviewObjectiveVO> {
	private static final long serialVersionUID = 1L;
	
	private int reviewObjectiveId;

	private String objectiveName;

	private Date achievmentDate;

	private Date createdDate;

	private Date lastModifiedDate;

	private Integer objectiveIndex;

	private Integer projectId;
	
	private Date startDate;

	private Date targetCompletionDate;

	private Integer appraiseRatingId;

	private Integer appraiserRatingId;

	private int reviewHeaderId;

	private String appraiseNegativeComment;

	private String appraisePositiveComment;

	private String appraiserNegativeComment;

	private String appraiserPositiveComment;

	private String detailsComment;

	private String successCriteriaComment;

	private String howYouAchievedComment;

	private String reviewPhase;

	private String isNotApplicable; // indicates whether this objective
									// applicable for an appraise or not... it
									// will set to true by manager in
									// GOALS_SUBMITTED status.
	private boolean isApproved;

	private String reviewStatus;
	
	private String projectNames;
	
	private List<Integer> projectIds;
	
	
	
	private List<ReviewObjectiveProjectVO> reviewObjectiveProjectVOs;
	
	
	private String appraiseRatingStr;
	
	private String appraiserRatingStr;

	
/*	public ReviewObjectiveVO() {
		super();
	}

	public ReviewObjectiveVO(String objectiveName) {
		this.objectiveName = objectiveName;
	}
	
	public ReviewObjectiveVO(String objectiveName, Date targetCompletionDate,
			String detailsComment, String successCriteriaComment,
			String projectName) {
		super();
		this.objectiveName = objectiveName;
		this.targetCompletionDate = targetCompletionDate;
		this.detailsComment = detailsComment;
		this.successCriteriaComment = successCriteriaComment;
		this.projectName = projectName;
	}*/

	public int getReviewObjectiveId() {
		return reviewObjectiveId;
	}

	public void setReviewObjectiveId(int reviewObjectiveId) {
		this.reviewObjectiveId = reviewObjectiveId;
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public Date getAchievmentDate() {
		return achievmentDate;
	}

	public void setAchievmentDate(Date achievmentDate) {
		if (achievmentDate!=null && achievmentDate.getClass() == java.sql.Date.class) {

			java.util.Date date = new java.util.Date(achievmentDate.getTime());
			this.achievmentDate = date;
		} else {
			this.achievmentDate = achievmentDate;
		}
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTargetCompletionDate() {
		return targetCompletionDate;
	}

	public void setTargetCompletionDate(Date targetCompletionDate) {
		if (targetCompletionDate!=null && targetCompletionDate.getClass() == java.sql.Date.class) {

			java.util.Date date = new java.util.Date(
					targetCompletionDate.getTime());
			this.targetCompletionDate = date;
		} else {
			this.targetCompletionDate = targetCompletionDate;
		}
	}

	public int getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

	public String getAppraiseNegativeComment() {
		return appraiseNegativeComment;
	}

	public void setAppraiseNegativeComment(String appraiseNegativeComment) {
		this.appraiseNegativeComment = appraiseNegativeComment;
	}

	public String getAppraisePositiveComment() {
		return appraisePositiveComment;
	}

	public void setAppraisePositiveComment(String appraisePositiveComment) {
		this.appraisePositiveComment = appraisePositiveComment;
	}

	public String getAppraiserNegativeComment() {
		return appraiserNegativeComment;
	}

	public void setAppraiserNegativeComment(String appraiserNegativeComment) {
		this.appraiserNegativeComment = appraiserNegativeComment;
	}

	public String getAppraiserPositiveComment() {
		return appraiserPositiveComment;
	}

	public void setAppraiserPositiveComment(String appraiserPositiveComment) {
		this.appraiserPositiveComment = appraiserPositiveComment;
	}

	public String getDetailsComment() {
		return detailsComment;
	}

	public void setDetailsComment(String detailsComment) {
		this.detailsComment = detailsComment;
	}

	public String getSuccessCriteriaComment() {
		return successCriteriaComment;
	}

	public void setSuccessCriteriaComment(String successCriteriaComment) {
		this.successCriteriaComment = successCriteriaComment;
	}

	public String getHowYouAchievedComment() {
		return howYouAchievedComment;
	}

	public void setHowYouAchievedComment(String howYouAchievedComment) {
		this.howYouAchievedComment = howYouAchievedComment;
	}

	public Integer getObjectiveIndex() {
		return objectiveIndex;
	}

	public void setObjectiveIndex(Integer objectiveIndex) {
		this.objectiveIndex = objectiveIndex;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReviewPhase() {
		return reviewPhase;
	}

	public void setReviewPhase(String reviewPhase) {
		this.reviewPhase = reviewPhase;
	}

	
	private String createdBy;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getAppraiseRatingId() {
		return appraiseRatingId;
	}

	public void setAppraiseRatingId(Integer appraiseRatingId) {
		this.appraiseRatingId = appraiseRatingId;
	}

	public Integer getAppraiserRatingId() {
		return appraiserRatingId;
	}

	public void setAppraiserRatingId(Integer appraiserRatingId) {
		this.appraiserRatingId = appraiserRatingId;
	}



	@Override
	public int compareTo(ReviewObjectiveVO reviewObjectiveVO) {
		if (this.reviewObjectiveId == reviewObjectiveVO.reviewObjectiveId) {

			return 0;
		}
		if (this.reviewObjectiveId > reviewObjectiveVO.reviewObjectiveId) {

			return 1;
		} else

			return -1;
	}

	public String getIsNotApplicable() {
		return isNotApplicable;
	}

	public void setIsNotApplicable(String isNotApplicable) {
		this.isNotApplicable = isNotApplicable;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public boolean getIsApproved() {
		return isApproved;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public String getProjectNames() {
		return projectNames;
	}

	public void setProjectNames(String projectNames) {
		this.projectNames = projectNames;
	}

	public List<Integer> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Integer> projectIds) {
		this.projectIds = projectIds;
	}
	public List<ReviewObjectiveProjectVO> getReviewObjectiveProjectVOs() {
		return reviewObjectiveProjectVOs;
	}

	public void setReviewObjectiveProjectVOs(
			List<ReviewObjectiveProjectVO> reviewObjectiveProjectVOs) {
		this.reviewObjectiveProjectVOs = reviewObjectiveProjectVOs;
	}

	public String getAppraiseRatingStr() {
		return appraiseRatingStr;
	}

	public void setAppraiseRatingStr(String appraiseRatingStr) {
		this.appraiseRatingStr = appraiseRatingStr;
	}

	public String getAppraiserRatingStr() {
		return appraiserRatingStr;
	}

	public void setAppraiserRatingStr(String apparaiserRatingStr) {
		this.appraiserRatingStr = apparaiserRatingStr;
	}
	
	
}