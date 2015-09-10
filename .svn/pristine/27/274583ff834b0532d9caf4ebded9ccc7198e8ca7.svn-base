package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import com.ggk.hrms.review.constants.ReviewFormRole;

/**
 * The persistent class for the ReviewDevelopmentPlanActivity database table.
 * 
 */
@Entity
public class ReviewDevelopmentPlanActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewDevelopmentPlanActivityId")
	private int reviewDevelopmentPlanActivityId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// bi-directional many-to-one association to ReviewHeader
	@ManyToOne
	@JoinColumn(name = "ReviewHeaderId")
	@ForeignKey(name = "FK_ReviewDevPlan_ReviewHeader")
	private ReviewHeader reviewHeader;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name = "ActionStepsCommentId")
	@ForeignKey(name = "FK_ReviewDevPlan_Comment_Action")
	private Comment actionStepsComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraiseCommentId")
	@ForeignKey(name = "FK_ReviewDevPlan_Comment_Appraise")
	private Comment appraiseComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name = "AppraiserCommentId")
	@ForeignKey(name = "FK_ReviewDevPlan_Comment_Appraiser")
	private Comment appraiserComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name = "GoalCommentId")
	@ForeignKey(name = "FK_ReviewDevPlan_Comment_Goal")
	private Comment goalComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade={CascadeType.ALL},orphanRemoval=true,fetch=FetchType.EAGER)
	@JoinColumn(name = "MeasurementCommentId")
	@ForeignKey(name = "FK_ReviewDevPlan_Comment_Measurement")
	private Comment measurementComment;
	
	@Column(name="IsApproved")
	private boolean isApproved;  
	
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole CreatedBy;
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole Owner;

	public ReviewDevelopmentPlanActivity() {
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



	public int getReviewDevelopmentPlanActivityId() {
		return this.reviewDevelopmentPlanActivityId;
	}

	public void setReviewDevelopmentPlanActivityId(
			int reviewDevelopmentPlanActivityId) {
		this.reviewDevelopmentPlanActivityId = reviewDevelopmentPlanActivityId;
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

	public ReviewHeader getReviewHeader() {
		return this.reviewHeader;
	}

	public void setReviewHeader(ReviewHeader reviewHeader) {
		this.reviewHeader = reviewHeader;
	}

	public Comment getActionStepsComment() {
		return actionStepsComment;
	}

	public void setActionStepsComment(Comment actionStepsComment) {
		this.actionStepsComment = actionStepsComment;
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

	public Comment getGoalComment() {
		return goalComment;
	}

	public void setGoalComment(Comment goalComment) {
		this.goalComment = goalComment;
	}

	public Comment getMeasurementComment() {
		return measurementComment;
	}

	public void setMeasurementComment(Comment measurementComment) {
		this.measurementComment = measurementComment;
	}



	public ReviewFormRole getOwner() {
		return Owner;
	}



	public void setOwner(ReviewFormRole owner) {
		Owner = owner;
	}

}