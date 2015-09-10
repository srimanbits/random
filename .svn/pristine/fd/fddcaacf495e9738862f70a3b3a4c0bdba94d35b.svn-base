package com.ggk.hrms.review.beans.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ggk.hrms.review.constants.ReviewFeildGroupType;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Entity
public class ReviewActionLog {

	public ReviewActionLog(
			ReviewFormInfoVO reviewFormInfoVO) {
		if (reviewFormInfoVO.getReviewFormRole().getDescription()
				.equals("APPRAISE")) {

			this.createdByName = reviewFormInfoVO.getAppraise();
		}
		else {

			this.createdByName = reviewFormInfoVO.getCurrentAppraiser();
		}

		this.createByEmployeeId = SecurityDetailsAccessor.getEmpId();
		this.reviewHeaderId = reviewFormInfoVO.getReviewHeaderId();
		this.createdByRole = reviewFormInfoVO.getReviewFormRole();
		this.createdDate = new Date();
		

	}

	public ReviewActionLog() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ReviewActionLogId")
	private int reviewActionLogId;

	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "ReviewHeaderId")
	private int reviewHeaderId;

	@Column(name = "ActionType")
	private String actionType;

	@Column(name = "CreatedByEmployeeId")
	private int createByEmployeeId;

	@Enumerated(EnumType.STRING)
	private ReviewFormRole createdByRole;

	@Enumerated(EnumType.STRING)
	private ReviewFeildGroupType reviewFieldGroupType;

	@Column(name = "ReviewFieldGroupName")
	private String reviewFieldGroupName;

	@Column(name = "FieldName")
	private String fieldName;

	@Column(name = "UpdatedValue")
	private String updatedValue;

	@Column(name = "UserCommentText")
	private String userCommentText;

	@Column(name = "CreatedByName")
	private String createdByName;

	@Column(name = "InitialValue")
	private String initialValue;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public int getReviewActionLogId() {
		return reviewActionLogId;
	}

	public void setReviewActionLogId(int reviewActionLogId) {
		this.reviewActionLogId = reviewActionLogId;
	}

	public ReviewFeildGroupType getReviewFieldGroupType() {
		return reviewFieldGroupType;
	}

	public void setReviewFieldGroupType(
			ReviewFeildGroupType reviewFieldGroupType) {
		this.reviewFieldGroupType = reviewFieldGroupType;
	}

	

	public String getUpdatedValue() {
		return updatedValue;
	}

	public void setUpdatedValue(String updatedValue) {
		this.updatedValue = updatedValue;
	}

	public String getUserCommentText() {
		return userCommentText;
	}

	public void setUserCommentText(String userCommentText) {
		this.userCommentText = userCommentText;
	}

	public ReviewFormRole getCreatedByRole() {
		return createdByRole;
	}

	public void setCreatedByRole(ReviewFormRole createdByRole) {
		this.createdByRole = createdByRole;
	}

	public int getCreateByEmployeeId() {
		return createByEmployeeId;
	}

	public void setCreateByEmployeeId(int createByEmployeeId) {
		this.createByEmployeeId = createByEmployeeId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public String getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(String initialValue) {
		this.initialValue = initialValue;
	}

	public String getReviewFieldGroupName() {
		return reviewFieldGroupName;
	}

	public void setReviewFieldGroupName(String reviewFieldGroupName) {
		this.reviewFieldGroupName = reviewFieldGroupName;
	}

	public int getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

}
