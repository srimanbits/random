package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ForeignKey;

import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;

/**
 * The persistent class for the ReviewObjective database table.
 * 
 */
@Entity
public class ReviewObjective implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewObjectiveId")
	private int reviewObjectiveId;

	@Column(name = "AchievmentDate")
	@Temporal(TemporalType.DATE)
	private Date achievmentDate;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@Column(name = "ObjectiveName")
	private String ObjectiveName;

	@ManyToOne
	@JoinColumn(name = "ProjectId")
	@ForeignKey(name = "FK_ReviewObjective_Project")
	private Project project;

	@Column(name = "StartDate")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "TargetCompletionDate")
	@Temporal(TemporalType.DATE)
	private Date targetCompletionDate;

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiseRatingId")
	@ForeignKey(name = "FK_ReviewObjective_Rating_Appraise")
	private Rating appraiseRating;

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiserRatingId")
	@ForeignKey(name = "FK_ReviewObjective_Rating_Appraiser")
	private Rating appraiserRating;

	// bi-directional many-to-one association to ReviewHeader
	@ManyToOne
	@JoinColumn(name = "ReviewHeaderId")
	@ForeignKey(name = "FK_ReviewObjective_ReviewHeader")
	private ReviewHeader reviewHeader;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraiseNegativeCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_AppraiseNgtv")
	private Comment appraiseNegativeComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraisePositiveCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_AppraisePstv")
	private Comment appraisePositiveComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraiserNegativeCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_AppraiserNgtv")
	private Comment appraiserNegativeComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraiserPositiveCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_AppraiserPstv")
	private Comment appraiserPositiveComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name = "DetailsCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_Details")
	private Comment detailsComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name = "SuccessCriteriaCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_SuccessCriteria")
	private Comment successCriteriaComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "howYouAchievedCommentId")
	@ForeignKey(name = "FK_ReviewObjective_Comment_HowYouAchieved")
	private Comment howYouAchievedComment;
	
	
	//@Swaroops...adding String ReviewPhase 
	@Enumerated(EnumType.STRING)
	private ReviewPhase reviewPhase;
	
	@Column(name="ReviewStatus")
	private String reviewStatus;
	
	

	@Column(name="IsNotApplicable")
	private boolean isNotApplicable; // indicates whether this objective applicable for an appraise or not... it will set to true by manager in GOALS_SUBMITTED status. 
	
	@Column(name="IsApproved")
	private boolean isApproved; // indicates whether this objective applicable for an appraise or not... it will set to true by manager in GOALS_SUBMITTED status. 
	
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole CreatedBy;
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole Owner;
	
	
	@SuppressWarnings("deprecation")
	@OneToMany(mappedBy = "reviewObjective", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<ReviewObjectiveProject> projects = new ArrayList<ReviewObjectiveProject>();
	

/*	public ReviewObjective() {
		appraiseNegativeComment=new Comment();
		appraisePositiveComment=new Comment();
		appraiserNegativeComment=new Comment();
		appraiserPositiveComment=new Comment();
		detailsComment=new Comment();
		howYouAchievedComment=new Comment();	
		successCriteriaComment=new Comment();
	}*/



	public int getReviewObjectiveId() {
		return this.reviewObjectiveId;
	}

	public boolean isNotApplicable() {
		return isNotApplicable;
	}

	public void setNotApplicable(boolean isNotApplicable) {
		this.isNotApplicable = isNotApplicable;
	}

	public void setReviewObjectiveId(int reviewObjectiveId) {
		this.reviewObjectiveId = reviewObjectiveId;
	}

	public Date getAchievmentDate() {
		return this.achievmentDate;
	}

	public void setAchievmentDate(Date achievmentDate) {
		this.achievmentDate = achievmentDate;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getTargetCompletionDate() {
		return this.targetCompletionDate;
	}

	public void setTargetCompletionDate(Date targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public ReviewHeader getReviewHeader() {
		return this.reviewHeader;
	}

	public void setReviewHeader(ReviewHeader reviewHeader) {
		this.reviewHeader = reviewHeader;
	}

	public Rating getAppraiseRating() {
		return appraiseRating;
	}

	public void setAppraiseRating(Rating appraiseRating) {
		this.appraiseRating = appraiseRating;
	}

	public Rating getAppraiserRating() {
		return appraiserRating;
	}

	public void setAppraiserRating(Rating appraiserRating) {
		this.appraiserRating = appraiserRating;
	}

	public Comment getAppraiseNegativeComment() {
		return appraiseNegativeComment;
	}

	public void setAppraiseNegativeComment(Comment appraiseNegativeComment) {
		this.appraiseNegativeComment = appraiseNegativeComment;
	}

	public Comment getAppraisePositiveComment() {
		return appraisePositiveComment;
	}

	public void setAppraisePositiveComment(Comment appraisePositiveComment) {
		this.appraisePositiveComment = appraisePositiveComment;
	}

	public Comment getAppraiserNegativeComment() {
		return appraiserNegativeComment;
	}

	public void setAppraiserNegativeComment(Comment appraiserNegativeComment) {
		this.appraiserNegativeComment = appraiserNegativeComment;
	}

	public Comment getAppraiserPositiveComment() {
		return appraiserPositiveComment;
	}

	public void setAppraiserPositiveComment(Comment appraiserPositiveComment) {
		this.appraiserPositiveComment = appraiserPositiveComment;
	}

	public Comment getDetailsComment() {
		return detailsComment;
	}

	public void setDetailsComment(Comment detailsComment) {
		this.detailsComment = detailsComment;
	}

	public Comment getSuccessCriteriaComment() {
		return successCriteriaComment;
	}

	public void setSuccessCriteriaComment(Comment successCriteriaComment) {
		this.successCriteriaComment = successCriteriaComment;
	}

	public String getObjectiveName() {
		return ObjectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		ObjectiveName = objectiveName;
	}

	public Comment getHowYouAchievedComment() {
		return howYouAchievedComment;
	}

	public void setHowYouAchievedComment(Comment howYouAchievedComment) {
		this.howYouAchievedComment = howYouAchievedComment;
	}

	public ReviewPhase getReviewPhase() {
		return reviewPhase;
	}

	public void setReviewPhase(ReviewPhase reviewPhase) {
		this.reviewPhase = reviewPhase;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public ReviewFormRole getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(ReviewFormRole createdBy) {
		CreatedBy = createdBy;
	}

	public List<ReviewObjectiveProject> getProjects() {
		return projects;
	}

	public void setProjects(List<ReviewObjectiveProject> projects) {
		this.projects = projects;
	}

	public ReviewFormRole getOwner() {
		return Owner;
	}

	public void setOwner(ReviewFormRole owner) {
		Owner = owner;
	}

	


}