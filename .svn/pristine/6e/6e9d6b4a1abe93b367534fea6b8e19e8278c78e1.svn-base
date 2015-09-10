package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ForeignKey;

import com.ggk.hrms.review.constants.ReviewPhase;

@Entity
public class ReviewObjectiveProject implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewObjectiveProjectId")
	private int reviewObjectiveProjectId;
	
	@ManyToOne
	@JoinColumn(name = "ReviewObjectiveId")
	@ForeignKey(name = "FK_ReviewObjectiveProject_ReviewObjective")
	private ReviewObjective reviewObjective;

	@OneToOne
	@JoinColumn(name = "ProjectId")
	@ForeignKey(name = "FK_ReviewObjectiveProject_Project")
	private Project project;
	
	/*@Enumerated(EnumType.STRING)
	@Column(name="Owner")
	private ReviewFormRole createdBy;*/
	
	@Enumerated(EnumType.STRING)
	@Column(name="ReviewPhase")
	private ReviewPhase reviewPhase;
	
	public int getReviewObjectiveProjectId() {
		return reviewObjectiveProjectId;
	}

	public void setReviewObjectiveProjectId(int reviewObjectiveProjectId) {
		this.reviewObjectiveProjectId = reviewObjectiveProjectId;
	}

	public ReviewObjective getReviewObjective() {
		return reviewObjective;
	}

	public void setReviewObjective(ReviewObjective reviewObjective) {
		this.reviewObjective = reviewObjective;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	/*public ReviewFormRole getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ReviewFormRole createdBy) {
		this.createdBy = createdBy;
	}*/

	public ReviewPhase getReviewPhase() {
		return reviewPhase;
	}

	public void setReviewPhase(ReviewPhase reviewPhase) {
		this.reviewPhase = reviewPhase;
	}

	@Column(name="IsApproved")
	private boolean isApproved;
}
