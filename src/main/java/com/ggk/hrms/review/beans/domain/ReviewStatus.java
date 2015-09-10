package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * The persistent class for the ReviewStatus database table.
 * 
 */
@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.ReviewStatus",usage=CacheConcurrencyStrategy.READ_ONLY)
public class ReviewStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final ReviewStatus NOT_STARTED = new ReviewStatus(
			"NOT_STARTED");
	public static final ReviewStatus GOALS_SETTING_IN_PROGRESS = new ReviewStatus(
			"GOALS_SETTING_IN_PROGRESS");
	public static final ReviewStatus GOALS_SUBMITTED = new ReviewStatus(
			"GOALS_SUBMITTED");
	public static final ReviewStatus NEED_TO_EDIT_GOALS = new ReviewStatus(
			"NEED_TO_EDIT_GOALS");
	public static final ReviewStatus GOALS_FINALIZED = new ReviewStatus(
			"GOALS_FINALIZED");
	public static final ReviewStatus GOALS_ACCEPTED = new ReviewStatus(
			"GOALS_ACCEPTED");
	public static final ReviewStatus APPRAISAL_IN_PROGRESS = new ReviewStatus(
			"APPRAISAL_IN_PROGRESS");
	public static final ReviewStatus APPRAISAL_SUBMITTED = new ReviewStatus(
			"APPRAISAL_SUBMITTED");
	public static final ReviewStatus NEED_TO_EDIT_APPRAISAL = new ReviewStatus(
			"NEED_TO_EDIT_APPRAISAL");
	public static final ReviewStatus ASSESSMENT_IN_PROGRESS = new ReviewStatus(
			"ASSESSMENT_IN_PROGRESS");
	public static final ReviewStatus READY_FOR_MEETING = new ReviewStatus(
			"READY_FOR_MEETING");
	public static final ReviewStatus COMPLETED = new ReviewStatus("COMPLETED");

	public static final List<String> APPRAISE_PHASE1_STATUS = new ArrayList<String>(
			Arrays.asList(ReviewStatus.NOT_STARTED.getReviewStatusCode(),
					ReviewStatus.GOALS_SETTING_IN_PROGRESS
							.getReviewStatusCode(),
							ReviewStatus.GOALS_FINALIZED
							.getReviewStatusCode(),
					ReviewStatus.NEED_TO_EDIT_GOALS.getReviewStatusCode()));
	
	public static final List<String> APPRAISE_PHASE2_STATUS = new ArrayList<String>(
			Arrays.asList(
//					ReviewStatus.GOALS_FINALIZED.getReviewStatusCode()
//					,
					ReviewStatus.APPRAISAL_IN_PROGRESS.getReviewStatusCode(),
					ReviewStatus.NEED_TO_EDIT_APPRAISAL.getReviewStatusCode(),
					ReviewStatus.READY_FOR_MEETING.getReviewStatusCode()));
	
	public static final List<String> APPRAISER_PHASE1_STATUS = new ArrayList<String>(
			Arrays.asList(ReviewStatus.GOALS_SUBMITTED
					.getReviewStatusCode()));
	
	public static final List<String> APPRAISER_PHASE2_STATUS = new ArrayList<String>(
			Arrays.asList(ReviewStatus.APPRAISAL_SUBMITTED.getReviewStatusCode(),
					ReviewStatus.ASSESSMENT_IN_PROGRESS.getReviewStatusCode()));
	
	public static final List<String> SYSTEM_PHASE2_COMPLETED=new ArrayList<String>(
			Arrays.asList(ReviewStatus.COMPLETED.getReviewStatusCode()));
	
	public static final List<String> SYSTEM_PHASE1_COMPLETED=new ArrayList<String>(
			Arrays.asList(ReviewStatus.GOALS_ACCEPTED.getReviewStatusCode()));
	

	ReviewStatus(String reviewStatusCode) {
		this.reviewStatusCode = reviewStatusCode;
	}

	public ReviewStatus() {

	}

	@Id
	@Column(name = "ReviewStatusCode")
	private String reviewStatusCode;

	@Column(name = "ReviewStatusDescription")
	private String reviewStatusDescription;

	public String getReviewStatusDescription() {
		return this.reviewStatusDescription;
	}

	public void setReviewStatusDescription(String reviewStatusCode) {
		this.reviewStatusCode = reviewStatusCode;
	}

	public String getReviewStatusCode() {
		return reviewStatusCode;
	}

	public void setReviewStatusCode(String reviewStatusCode) {
		this.reviewStatusCode = reviewStatusCode;
	}

	public static String getAppraiseReviewStatus(ReviewStatus reviewStatus) {
		if (reviewStatus.getReviewStatusCode().equals("NOT_STARTED")) {
			return "Stage 1 - Employee - Goals Not Started";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SETTING_IN_PROGRESS")) {
			return "Stage 1 - Employee - Goals setting in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SUBMITTED")) {
			return "Stage 2 - Manager - Need to approve goals";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_GOALS")) {
			return "Stage 2 - Employee - Need more information";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_FINALIZED")) {
			return "Stage 3 - Employee - Goals Approved";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_ACCEPTED")) {
			return "Stage 4 - Employee - Goals Accepted";
		}
		else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_IN_PROGRESS")) {
			return "Stage 5 - Employee - Self Assessment in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_SUBMITTED")) {
			return "Stage 6 - Manager - Pending  Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_APPRAISAL")) {
			return "Stage 6 - Employee - Need More Information";
		} else if (reviewStatus.getReviewStatusCode().equals("ASSESSMENT_IN_PROGRESS")) {
			return "Stage 7 - Manager - Pending Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("READY_FOR_MEETING")) {
			return "Stage 7 - Manager Employee Discussion";
		}else if (reviewStatus.getReviewStatusCode().equals("COMPLETED")) {
			return "Stage 8 - Completed";
		}
		return null;
	}

	public static String getAppraiserReviewStatus(ReviewStatus reviewStatus) {
		if (reviewStatus.getReviewStatusCode().equals("NOT_STARTED")) {
			return "Stage 1 - Employee - Goals Not Started";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SETTING_IN_PROGRESS")) {
			return "Stage 1 - Employee - Goals setting in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SUBMITTED")) {
			return "Stage 2 - Manager - Need to approve goals";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_GOALS")) {
			return "Stage 2 - Employee - Need more information";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_FINALIZED")) {
			return "Stage 3 - Employee - Goals Approved";
		}else if (reviewStatus.getReviewStatusCode().equals("GOALS_ACCEPTED")) {
			return "Stage 4 - Employee - Goals Accepted";
		}
		else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_IN_PROGRESS")) {
			return "Stage 5 - Employee - Self Assessment in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_SUBMITTED")) {
			return "Stage 6 - Manager - Pending  Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_APPRAISAL")) {
			return "Stage 6 - Employee - Need More Information";
		} else if (reviewStatus.getReviewStatusCode().equals("ASSESSMENT_IN_PROGRESS")) {
			return "Stage 7 - Manager - Pending Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("READY_FOR_MEETING")) {
			return "Stage 7 - Manager Employee Discussion";
		}else if (reviewStatus.getReviewStatusCode().equals("COMPLETED")) {
			return "Stage 8 - Completed";
		}
		return null;
	}
	
	public static String getSuperUserReviewStatus(ReviewStatus reviewStatus) {
		if (reviewStatus.getReviewStatusCode().equals("NOT_STARTED")) {
			return "Stage 1 - Employee - Goals Not Started";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SETTING_IN_PROGRESS")) {
			return "Stage 1 - Employee - Goals setting in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_SUBMITTED")) {
			return "Stage 2 - Manager - Need to approve goals";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_GOALS")) {
			return "Stage 2 - Employee - Need more information";
		} else if (reviewStatus.getReviewStatusCode().equals("GOALS_FINALIZED")) {
			return "Stage 3 - Employee - Goals Approved";
		}else if (reviewStatus.getReviewStatusCode().equals("GOALS_ACCEPTED")) {
			return "Stage 4 - Employee - Goals Accepted";
		}
		else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_IN_PROGRESS")) {
			return "Stage 5 - Employee - Self Assessment in progress";
		} else if (reviewStatus.getReviewStatusCode().equals("APPRAISAL_SUBMITTED")) {
			return "Stage 6 - Manager - Pending  Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("NEED_TO_EDIT_APPRAISAL")) {
			return "Stage 6 - Employee - Need More Information";
		} else if (reviewStatus.getReviewStatusCode().equals("ASSESSMENT_IN_PROGRESS")) {
			return "Stage 7 - Manager - Pending Approval";
		} else if (reviewStatus.getReviewStatusCode().equals("READY_FOR_MEETING")) {
			return "Stage 7 - Manager Employee Discussion";
		}else if (reviewStatus.getReviewStatusCode().equals("COMPLETED")) {
			return "Stage 8 - Completed";
		}
		return null;
	}
	
	public static String getReviewStatus(String reviewStatusCode) {
		if (reviewStatusCode.equals("NOT_STARTED")) {
			return "Stage 1 - Employee - Goals Not Started";
		} else if (reviewStatusCode.equals(
				"GOALS_SETTING_IN_PROGRESS")) {
			return "Employee - Goals setting in progress";
		} else if (reviewStatusCode.equals("GOALS_SUBMITTED")) {
			return "Manager - Need to approve goals";
		} else if (reviewStatusCode.equals(
				"NEED_TO_EDIT_GOALS")) {
			return "Employee - Need more information";
		} else if (reviewStatusCode.equals("GOALS_FINALIZED")) {
			return "Employee - Goals Approved";
		} else if (reviewStatusCode.equals("GOALS_ACCEPTED")) {
			return "Employee - Goals Accepted";
		} else if (reviewStatusCode.equals(
				"APPRAISAL_IN_PROGRESS")) {
			return "Employee - Self Assessment in progress";
		} else if (reviewStatusCode.equals(
				"APPRAISAL_SUBMITTED")) {
			return "Manager - Pending  Approval";
		} else if (reviewStatusCode.equals(
				"NEED_TO_EDIT_APPRAISAL")) {
			return "Employee - Need More Information";
		} else if (reviewStatusCode.equals(
				"ASSESSMENT_IN_PROGRESS")) {
			return "Manager - Pending Approval";
		} else if (reviewStatusCode.equals(
				"READY_FOR_MEETING")) {
			return "Manager Employee Discussion";
		} else if (reviewStatusCode.equals("COMPLETED")) {
			return "Completed";
		}
		return null;
	}
}