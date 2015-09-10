package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ForeignKey;

/**
 * The persistent class for the ReviewCycle database table.
 * 
 */
@Entity
public class ReviewCycle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewCycleId")
	private int reviewCycleId;
	
	@Column(name = "ReviewCycleName")
	private String reviewCycleName;

	@Column(name = "GoalsSettingDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date goalsSettingDueDate;
	
	@Column(name = "GoalsApprovalDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date goalsApprovalDueDate;

	@Column(name = "GoalsAcceptanceDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date goalsAcceptanceDueDate;
	
	@Column(name = "SelfAppraisalDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date selfAppraisalDueDate;
	
	@Column(name = "AppraisalDiscussionDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date appraisalDiscussionDueDate;
	
	@Column(name = "AppraisalDueDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date appraisalDueDate;

	@Column(name = "ReviewCycleStartDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reviewCycleStartDate;
	
	@Column(name = "ReviewCycleEndDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reviewCycleEndDate;

	@Column(name = "IsActive")
	private boolean isActive;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Column(name = "ReviewCycleStatus")
	private String reviewCycleStatus;

	// bi-directional many-to-one association to ReviewHeader
	@OneToMany(mappedBy = "reviewCycle" , fetch=FetchType.LAZY)
	private List<ReviewHeader> reviewHeaders;
	
	//Swaroops
	@ManyToOne
	@JoinColumn(name = "AppraisalPeriodTypeId")
	@ForeignKey(name = "FK_ReviewCycle_AppraisalPeriodTypeId")
	private AppraisalPeriodType appraisalPeriodType;

	public ReviewCycle() {
	}

	public int getReviewCycleId() {
		return this.reviewCycleId;
	}

	public void setReviewCycleId(int reviewCycleId) {
		this.reviewCycleId = reviewCycleId;
	}

	public Date getGoalsAcceptanceDueDate() {
		return goalsAcceptanceDueDate;
	}

	public void setGoalsAcceptanceDueDate(Date goalsAcceptanceDueDate) {
		this.goalsAcceptanceDueDate = goalsAcceptanceDueDate;
	}

	public Date getAppraisalDiscussionDueDate() {
		return appraisalDiscussionDueDate;
	}

	public void setAppraisalDiscussionDueDate(Date appraisalDiscussionDueDate) {
		this.appraisalDiscussionDueDate = appraisalDiscussionDueDate;
	}

	public Date getAppraisalDueDate() {
		return this.appraisalDueDate;
	}

	public void setAppraisalDueDate(Date appraisalDueDate) {
		this.appraisalDueDate = appraisalDueDate;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getGoalsApprovalDueDate() {
		return this.goalsApprovalDueDate;
	}

	public void setGoalsApprovalDueDate(Date goalsApprovalDueDate) {
		this.goalsApprovalDueDate = goalsApprovalDueDate;
	}

	public Date getGoalsSettingDueDate() {
		return this.goalsSettingDueDate;
	}

	public void setGoalsSettingDueDate(Date goalsSettingDueDate) {
		this.goalsSettingDueDate = goalsSettingDueDate;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getReviewCycleName() {
		return this.reviewCycleName;
	}

	public void setReviewCycleName(String reviewCycleName) {
		this.reviewCycleName = reviewCycleName;
	}

	public Date getSelfAppraisalDueDate() {
		return this.selfAppraisalDueDate;
	}

	public void setSelfAppraisalDueDate(Date selfAppraisalDueDate) {
		this.selfAppraisalDueDate = selfAppraisalDueDate;
	}

	public List<ReviewHeader> getReviewHeaders() {
		return this.reviewHeaders;
	}

	public void setReviewHeaders(List<ReviewHeader> reviewHeaders) {
		this.reviewHeaders = reviewHeaders;
	}

	public Date getReviewCycleStartDate() {
		return reviewCycleStartDate;
	}

	public void setReviewCycleStartDate(Date reviewCycleStartDate) {
		this.reviewCycleStartDate = reviewCycleStartDate;
	}

	public Date getReviewCycleEndDate() {
		return reviewCycleEndDate;
	}

	public void setReviewCycleEndDate(Date reviewCycleEndDate) {
		this.reviewCycleEndDate = reviewCycleEndDate;
	}

	public String getReviewCycleStatus() {
		return reviewCycleStatus;
	}

	public void setReviewCycleStatus(String reviewCycleStatus) {
		this.reviewCycleStatus = reviewCycleStatus;
	}

	public AppraisalPeriodType getAppraisalPeriodType() {
		return appraisalPeriodType;
	}

	public void setAppraisalPeriodType(AppraisalPeriodType appraisalPeriodType) {
		this.appraisalPeriodType = appraisalPeriodType;
	}

	@Override
	public String toString() {
		return "ReviewCycle [reviewCycleId=" + reviewCycleId
				+ ", reviewCycleName=" + reviewCycleName + "]";
	}

}