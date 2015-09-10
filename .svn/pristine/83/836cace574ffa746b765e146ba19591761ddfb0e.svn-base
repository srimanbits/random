package com.ggk.hrms.review.beans.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
public class DefaultObjectiveGrade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DefaultObjectiveGradeId")
	private int defaultObjectiveGradeId;

	@OneToOne
	@JoinColumn(name = "ReviewCycleId")
	@ForeignKey(name = "Fk_ReviewCycle")
	private ReviewCycle reviewCycle;

	@OneToOne
	@JoinColumn(name = "GradeId")
	@ForeignKey(name = "Fk_Grade")
	private Grade grade;

	@ManyToOne
	@JoinColumn(name = "DefaultObjectiveId")
	@ForeignKey(name = "Fk_DefaultObjective")
	private DefaultObjective defaultObjective;

	@OneToOne
	@JoinColumn(name = "DetailsCommentId")
	@ForeignKey(name = "Fk_DetailsComment")
	private Comment detailsComment;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	@OneToOne
	@JoinColumn(name = "SuccessCriteriaCommentId")
	@ForeignKey(name = "Fk_SuccessCriteriaComment")
	private Comment successCriteria;

	public int getDefaultObjectiveGradeId() {
		return defaultObjectiveGradeId;
	}

	public void setDefaultObjectiveGradeId(int defaultObjectiveGradeId) {
		this.defaultObjectiveGradeId = defaultObjectiveGradeId;
	}

	public ReviewCycle getReviewCycle() {
		return reviewCycle;
	}

	public void setReviewCycle(ReviewCycle reviewCycle) {
		this.reviewCycle = reviewCycle;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public DefaultObjective getDefaultObjective() {
		return defaultObjective;
	}

	public void setDefaultObjective(DefaultObjective defaultObjective) {
		this.defaultObjective = defaultObjective;
	}

	public Comment getDetailsComment() {
		return detailsComment;
	}

	public void setDetailsComment(Comment detailsComment) {
		this.detailsComment = detailsComment;
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

	public Comment getSuccessCriteria() {
		return successCriteria;
	}

	public void setSuccessCriteria(Comment successCriteria) {
		this.successCriteria = successCriteria;
	}
}
