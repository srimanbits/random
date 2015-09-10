package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import com.ggk.hrms.review.constants.ReviewFormRole;

/**
 * The persistent class for the ReviewCompetency database table.
 * 
 */
@Entity
public class ReviewCompetency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewCompetencyId")
	private int reviewCompetencyId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "LastModifiedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;

	// bi-directional one-to-one association to Competency
	@OneToOne
	@JoinColumn(name = "CompetencyId")
	@ForeignKey(name = "FK_ReviewCompetency_Competency")
	private Competency competency;
	
	@ManyToOne
	@JoinColumn(name = "ExpectedProficiencyLevelId")
	@ForeignKey(name = "FK_ReviewCompetency_Expected_ProficiencyLevel")
	private ProficiencyLevel expectedProficiencyLevel;
	
	@ManyToOne
	@JoinColumn(name = "ExpectedProficiencyLevelIdAsPerGrade")
	@ForeignKey(name = "FK_ReviewCompetency_Expected_ProficiencyLevel_AsPerGrade")
	private ProficiencyLevel expectedProficiencyLevelIdAsPerGrade;

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiseRatingId")
	@ForeignKey(name = "FK_ReviewCompetency_Rating_Appraise")
	private Rating appraiseRating; 

	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiserRatingId")
	@ForeignKey(name = "FK_ReviewCompetency_Rating_Appraiser")
	private Rating appraiserRating;  

	// bi-directional many-to-one association to ReviewHeader
	@ManyToOne
	@JoinColumn(name = "ReviewHeaderId")
	@ForeignKey(name = "FK_ReviewCompetency_ReviewHeader")
	private ReviewHeader reviewHeader;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "AppraiseCommentId")
	@ForeignKey(name = "FK_ReviewCompetency_Comment_Appraise")
	private Comment appraiseComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name = "AppraiserCommentId")
	@ForeignKey(name = "FK_ReviewCompetency_Comment_Appraiser")
	private Comment appraiserComment;
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole Owner;

	public ReviewCompetency() {
	}

	public int getReviewCompetencyId() {
		return this.reviewCompetencyId;
	}

	public void setReviewCompetencyId(int reviewCompetencyId) {
		this.reviewCompetencyId = reviewCompetencyId;
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

	public Competency getCompetency() {
		return this.competency;
	}

	public void setCompetency(Competency competency) {
		this.competency = competency;
	}

	public ReviewHeader getReviewHeader() {
		return this.reviewHeader;
	}

	public void setReviewHeader(ReviewHeader reviewHeader) {
		this.reviewHeader = reviewHeader;
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

	public ProficiencyLevel getExpectedProficiencyLevel() {
		return expectedProficiencyLevel;
	}

	public void setExpectedProficiencyLevel(ProficiencyLevel expectedProficiencyLevel) {
		this.expectedProficiencyLevel = expectedProficiencyLevel;
	}

	public ReviewFormRole getOwner() {
		return Owner;
	}

	public void setOwner(ReviewFormRole owner) {
		Owner = owner;
	}

	public ProficiencyLevel getExpectedProficiencyLevelIdAsPerGrade() {
		return expectedProficiencyLevelIdAsPerGrade;
	}

	public void setExpectedProficiencyLevelIdAsPerGrade(
			ProficiencyLevel expectedProficiencyLevelIdAsPerGrade) {
		this.expectedProficiencyLevelIdAsPerGrade = expectedProficiencyLevelIdAsPerGrade;
	}


	

}