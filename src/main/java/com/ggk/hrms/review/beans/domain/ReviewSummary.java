package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;

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

import org.hibernate.annotations.ForeignKey;

import com.ggk.hrms.review.constants.ReviewFormRole;

@Entity
public class ReviewSummary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewSummaryId")
	private int reviewSummaryId;
	
	
	@ManyToOne
	@JoinColumn(name = "AppraiseRatingId")
	@ForeignKey(name = "FK_ReviewSummary_Rating_Appraise")
	private Rating appraiseRating;

	
	// bi-directional many-to-one association to Rating
	@ManyToOne
	@JoinColumn(name = "AppraiserRatingId")
	@ForeignKey(name = "FK_ReviewSummary_Rating_Appraiser")
	private Rating appraiserRating;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiseCommentId")
	@ForeignKey(name = "FK_ReviewSummary_Comment_Appraise")
	private Comment appraiseComment;

	// bi-directional one-to-one association to Comment
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AppraiserCommentId")
	@ForeignKey(name = "FK_ReviewSummary_Comment_Appraiser")
	private Comment appraiserComment;
	
	@OneToOne
	@JoinColumn(name = "ReviewHeaderId")
	@ForeignKey(name = "FK_ReviewSummary_ReviewHeader")
	private ReviewHeader reviewHeader;
	
	@Enumerated(EnumType.STRING)
	private ReviewFormRole owner;
	
	
	public Rating getAppraiserRating() {
		return appraiserRating;
	}

	public void setAppraiserRating(Rating appraiserRating) {
		this.appraiserRating = appraiserRating;
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

	public int getReviewSummaryId() {
		return reviewSummaryId;
	}

	public void setReviewSummaryId(int reviewSummaryId) {
		this.reviewSummaryId = reviewSummaryId;
	}

	

	public ReviewHeader getReviewHeader() {
		return reviewHeader;
	}

	public void setReviewHeader(ReviewHeader reviewHeader) {
		this.reviewHeader = reviewHeader;
	}

	public ReviewFormRole getOwner() {
		return owner;
	}

	public void setOwner(ReviewFormRole owner) {
		this.owner = owner;
	}

}
