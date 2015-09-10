package com.ggk.hrms.review.ui.vo;

import java.io.Serializable;

public class ReviewHeaderVO implements Serializable,Comparable<ReviewHeaderVO> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer reviewHeaderId;
	
	private String reviewStatus;

	private Integer appraiseRatingId;

	private Integer appraiserRatingId;

	private String appraiseComment;

	private String appraiserComment;

	private String appraiseInternalComment;
	
	private String appraiserInternalComment;
	
	private String appraiseName;
	
	private String appraiserName;
	
	public String getAppraiseName() {
		return appraiseName;
	}

	public void setAppraiseName(String appraiseName) {
		this.appraiseName = appraiseName;
	}

	public String getAppraiserName() {
		return appraiserName;
	}

	public void setAppraiserName(String appraiserName) {
		this.appraiserName = appraiserName;
	}

	public ReviewHeaderVO() {

	}
	
	public ReviewHeaderVO(Integer reviewHeaderId, String reviewStatus, String appraiseName, String appraiserName){
		this.reviewHeaderId=reviewHeaderId;
		this.reviewStatus=reviewStatus;
		this.appraiseName=appraiseName;
		this.appraiserName=appraiserName;

	}

	public Integer getReviewHeaderId() {
		return reviewHeaderId;
	}
	public void setReviewHeaderId(Integer reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}
	
	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
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
	public String getAppraiseComment() {
		return appraiseComment;
	}

	public void setAppraiseComment(String appraiseComment) {
		this.appraiseComment = appraiseComment;
	}

	public String getAppraiserComment() {
		return appraiserComment;
	}

	public void setAppraiserComment(String appraiserComment) {
		this.appraiserComment = appraiserComment;
	}

	public String getAppraiseInternalComment() {
		return appraiseInternalComment;
	}

	public void setAppraiseInternalComment(String appraiseInternalComment) {
		this.appraiseInternalComment = appraiseInternalComment;
	}

	public String getAppraiserInternalComment() {
		return appraiserInternalComment;
	}

	public void setAppraiserInternalComment(String appraiserInternalComment) {
		this.appraiserInternalComment = appraiserInternalComment;
	}

	@Override
	public int compareTo(ReviewHeaderVO reviewHeaderVO) {
		if (this.reviewHeaderId == reviewHeaderVO.reviewHeaderId) {

			return 0;
		}
		if (this.reviewHeaderId > reviewHeaderVO.reviewHeaderId) {

			return 1;
		} else

			return -1;
	}



	

}