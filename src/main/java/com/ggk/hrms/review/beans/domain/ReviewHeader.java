package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the ReviewHeader database table.
 * 
 */
@Entity
public class ReviewHeader implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewHeaderId")
	private int reviewHeaderId;

	@OneToOne
	@JoinColumn(name = "CreatedByEmployeeId")
	@ForeignKey(name = "FK_ReviewHeader_Employee_Created")
	private Employee createdByEmployee;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@OneToOne
	@JoinColumn(name = "EmployeeId")
	@ForeignKey(name = "FK_ReviewHeader_Employee")
	private Employee employee;

	@OneToOne
	@JoinColumn(name = "LastModifiedByEmployeeId")
	@ForeignKey(name = "FK_ReviewHeader_Employee_Modified")
	private Employee lastModifiedByEmployee;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@OneToOne
	@JoinColumn(name = "MainAppraiserEmployeeId")
	@ForeignKey(name = "FK_ReviewHeader_Employee_mgr")
	private Employee mainAppraiserEmployee;

	// bi-directional many-to-one association to FeedbackRequest
	@OneToMany(mappedBy = "reviewHeader", fetch = FetchType.LAZY)
	private List<FeedbackRequest> feedbackRequests;

	// bi-directional many-to-one association to ReviewCompetency
	@OneToMany(mappedBy = "reviewHeader", fetch = FetchType.LAZY)
	private List<ReviewCompetency> reviewCompetencies;

	// bi-directional many-to-one association to ReviewDevelopmentPlanActivity
	@OneToMany(mappedBy = "reviewHeader", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ReviewDevelopmentPlanActivity> reviewDevelopmentPlanActivities;

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiseRatingId")
	@ForeignKey(name = "FK_ReviewHeader_Rating_Appraise")
	private Rating appraiseRating;

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiserRatingId")
	@ForeignKey(name = "FK_ReviewHeader_Rating_Appraiser")
	private Rating appraiserRating;

	// bi-directional many-to-one association to ReviewCycle
	@ManyToOne
	@ForeignKey(name = "FK_ReviewHeader_ReviewCycle")
	@JoinColumn(name = "ReviewCycleId")
	private ReviewCycle reviewCycle;

	// bi-directional many-to-one association to ReviewStatus
	@ManyToOne
	@JoinColumn(name = "ReviewStatusCode")
	@ForeignKey(name = "FK_ReviewHeader_ReviewStatusCode")
	private ReviewStatus reviewStatus;

	// bi-directional many-to-one association to ReviewObjective
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "reviewHeader", fetch = FetchType.LAZY)
	private List<ReviewObjective> reviewObjectives;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiseCommentId")
	@ForeignKey(name = "FK_ReviewHeader_Comment_Appraise")
	private Comment appraiseComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiserCommentId")
	@ForeignKey(name = "FK_ReviewHeader_Comment_Appraiser")
	private Comment appraiserComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiseInternalCommentId")
	@ForeignKey(name = "FK_ReviewHeader_Comment_Internal")
	private Comment appraiseInternalComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiserInternalCommentId")
	@ForeignKey(name = "FK_ReviewHeader_AppraiserComment_Intrl")
	private Comment appraiserInternalComment;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sharedAppraiserInternalCommentId")
	@ForeignKey(name = "FK_ReviewHeader_SharedAppraiserInternalComment_Intrl")
	private Comment sharedAppraiserInternalComment;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SuperUserInternalCommentId")
	@ForeignKey(name = "FK_ReviewHeader_SuperUserInternalComment_Intrl")
	private Comment superUserInternalComment;

	// bi-directional many-to-one association to SharedAppraiser
	@OneToMany(mappedBy = "reviewHeader", fetch = FetchType.LAZY)
	private Set<SharedAppraiser> sharedAppraisers;

	// @OneToMany(mappedBy = "reviewHeader", cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY)
	// @OrderBy("createdDate")
	// private List<ReviewActionLog> reviewActionLogs;
	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "reviewHeader", fetch = FetchType.LAZY)
	private List<ReviewSummary> reviewSummaries;
	
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "CurrentGradeId")
	@ForeignKey(name = "FK_ReviewHeader_CurrentGradeId")
	private Grade currentGrade;

	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name = "CurrentDesgId")
	@ForeignKey(name = "FK_ReviewHeader_CurrentDesgId")
	private Designation currentDesignation;
	
	
	public ReviewHeader() {
	}

	public int getReviewHeaderId() {
		return this.reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Employee getCreatedByEmployee() {
		return createdByEmployee;
	}

	public void setCreatedByEmployee(Employee createdByEmployee) {
		this.createdByEmployee = createdByEmployee;
	}

	public Employee getLastModifiedByEmployee() {
		return lastModifiedByEmployee;
	}

	public void setLastModifiedByEmployee(Employee lastModifiedByEmployee) {
		this.lastModifiedByEmployee = lastModifiedByEmployee;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<FeedbackRequest> getFeedbackRequests() {
		return this.feedbackRequests;
	}

	public void setFeedbackRequests(List<FeedbackRequest> feedbackRequests) {
		this.feedbackRequests = feedbackRequests;
	}

	public List<ReviewCompetency> getReviewCompetencies() {
		return this.reviewCompetencies;
	}

	public void setReviewCompetencies(List<ReviewCompetency> reviewCompetencies) {
		this.reviewCompetencies = reviewCompetencies;
	}

	public List<ReviewDevelopmentPlanActivity> getReviewDevelopmentPlanActivities() {
		return this.reviewDevelopmentPlanActivities;
	}

	public void setReviewDevelopmentPlanActivities(
			List<ReviewDevelopmentPlanActivity> reviewDevelopmentPlanActivities) {
		this.reviewDevelopmentPlanActivities = reviewDevelopmentPlanActivities;
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

	public ReviewCycle getReviewCycle() {
		return this.reviewCycle;
	}

	public void setReviewCycle(ReviewCycle reviewCycle) {
		this.reviewCycle = reviewCycle;
	}

	public ReviewStatus getReviewStatus() {
		return this.reviewStatus;
	}

	public void setReviewStatus(ReviewStatus reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	public List<ReviewObjective> getReviewObjectives() {
		return this.reviewObjectives;
	}

	public void setReviewObjectives(List<ReviewObjective> reviewObjectives) {
		this.reviewObjectives = reviewObjectives;
	}

	public Comment getAppraiseComment() {
		return appraiseComment;
	}

	public void setAppraiseComment(Comment appraiseComment) {
		this.appraiseComment = appraiseComment;
	}

	public Comment getAppraiserComment() {
		return appraiserComment;
	}

	public void setAppraiserComment(Comment appraiserComment) {
		this.appraiserComment = appraiserComment;
	}

	public Comment getAppraiserInternalComment() {
		return appraiserInternalComment;
	}

	public void setAppraiserInternalComment(Comment appraiserInternalComment) {
		this.appraiserInternalComment = appraiserInternalComment;
	}

	public Comment getAppraiseInternalComment() {
		return appraiseInternalComment;
	}

	public void setAppraiseInternalComment(Comment appraiseInternalComment) {
		this.appraiseInternalComment = appraiseInternalComment;
	}

	public Employee getMainAppraiserEmployee() {
		return mainAppraiserEmployee;
	}

	public void setMainAppraiserEmployee(Employee mainAppraiserEmployee) {
		this.mainAppraiserEmployee = mainAppraiserEmployee;
	}

	public Set<SharedAppraiser> getSharedAppraisers() {
		return sharedAppraisers;
	}

	public void setSharedAppraisers(Set<SharedAppraiser> sharedAppraisers) {
		this.sharedAppraisers = sharedAppraisers;
	}

	public Comment getSharedAppraiserInternalComment() {
		return sharedAppraiserInternalComment;
	}

	public void setSharedAppraiserInternalComment(
			Comment sharedAppraiserInternalComment) {
		this.sharedAppraiserInternalComment = sharedAppraiserInternalComment;
	}

	public Comment getSuperUserInternalComment() {
		return superUserInternalComment;
	}

	public void setSuperUserInternalComment(Comment superUserInternalComment) {
		this.superUserInternalComment = superUserInternalComment;
	}

	public List<ReviewSummary> getReviewSummaries() {
		return reviewSummaries;
	}

	public void setReviewSummaries(List<ReviewSummary> reviewSummaries) {
		this.reviewSummaries = reviewSummaries;
	}

	public Grade getCurrentGrade() {
		return currentGrade;
	}

	public void setCurrentGrade(Grade currentGrade) {
		this.currentGrade = currentGrade;
	}

	public Designation getCurrentDesignation() {
		return currentDesignation;
	}

	public void setCurrentDesignation(Designation currentDesignation) {
		this.currentDesignation = currentDesignation;
	}

	// public List<ReviewActionLog> getReviewActionLogs() {
	// return reviewActionLogs;
	// }
	//
	// public void setReviewActionLogs(List<ReviewActionLog> reviewActionLogs) {
	// this.reviewActionLogs = reviewActionLogs;
	// }

}