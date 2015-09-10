package com.ggk.hrms.review.ui.vo;

public class OtherAppraisalsVO {

	private int reviewHeaderId;
	private String reviewCycleName;
	private Integer appraiseId;
	private Integer appraiserId;
	
	public OtherAppraisalsVO(String reviewCycleName, int reviewHeaderId) {
		super();
		this.reviewCycleName = reviewCycleName;
		this.reviewHeaderId = reviewHeaderId;
	}
	
	public OtherAppraisalsVO(String reviewCycleName, int reviewHeaderId,Integer appraiseId,Integer appraiserId) {
		super();
		this.reviewCycleName = reviewCycleName;
		this.reviewHeaderId = reviewHeaderId;
		this.appraiseId = appraiseId;
		this.appraiserId = appraiserId;
	}

	public int getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

	public String getReviewCycleName() {
		return reviewCycleName;
	}

	public void setReviewCycleName(String reviewCycleName) {
		this.reviewCycleName = reviewCycleName;
	}

	public Integer getAppraiseId() {
		return appraiseId;
	}

	public void setAppraiseId(Integer appraiseId) {
		this.appraiseId = appraiseId;
	}

	public Integer getAppraiserId() {
		return appraiserId;
	}

	public void setAppraiserId(Integer appraiserId) {
		this.appraiserId = appraiserId;
	}
	

}
