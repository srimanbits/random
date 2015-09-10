package com.ggk.hrms.review.ui.vo;



public class ReviewActionLogVO {
	
	private String createdByName;
	
	private String createdByRole;
	
	private String reviewFieldGroupType;
	
	private String createdDate;
	
	private StringBuffer commentText;

	private String notesInfo;

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getCreatedByRole() {
		return createdByRole;
	}

	public void setCreatedByRole(String createdByRole) {
		this.createdByRole = createdByRole;
	}

	public String getReviewFieldGroupType() {
		return reviewFieldGroupType;
	}

	public void setReviewFieldGroupType(String reviewFieldGroupType) {
		this.reviewFieldGroupType = reviewFieldGroupType;
	}

	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public StringBuffer getCommentText() {
		return commentText;
	}

	public void setCommentText(StringBuffer commentText) {
		this.commentText = commentText;
	}

	public void setNotesInfo(String notesInfo) {
		this.notesInfo=notesInfo;
		
	}

	public String getNotesInfo() {
		return notesInfo;
	}

	



	
	

}
