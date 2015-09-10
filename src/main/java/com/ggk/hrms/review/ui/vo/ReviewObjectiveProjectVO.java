package com.ggk.hrms.review.ui.vo;

public class ReviewObjectiveProjectVO {

	private int projectId;

	private String projectName;

	private boolean isApproved;

	private int reviewObjectiveId;

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public int getReviewObjectiveId() {
		return reviewObjectiveId;
	}

	public void setReviewObjectiveId(int reviewObjectiveId) {
		this.reviewObjectiveId = reviewObjectiveId;
	}
}
