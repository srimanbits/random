/**
 * 
 */
package com.ggk.hrms.review.ui.vo;

/**
 * @author SwethaP
 *
 */
import java.io.Serializable;

/**
 * The persistent class for the ReviewCompetency database table.
 * 
 */

public class ReviewCompetencyVO implements Serializable,Comparable<ReviewCompetencyVO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int reviewCompetencyId;
	
	private int competencyId;

	private String competencyName;
	
	private String expectedBehavioralIndicator;
	
	private String competencyDefinition;

	private Integer appraiseRatingId;

	private Integer appraiserRatingId;
	
	private Integer expectedProficiencyLevelId;
	
	private Integer expectedProficiencyLevelIdAsPerGrade;
	
	private String appraiseCommentText;

	private String appraiserCommentText;
	
	private String proficiencyLevelName;
	
	private Integer proficiencyLevelNumber;
	
    private String appraiseRatingStr;
	
	private String appraiserRatingStr;
	
	
	public Integer getExpectedProficiencyLevelIdAsPerGrade() {
		return expectedProficiencyLevelIdAsPerGrade;
	}

	public void setExpectedProficiencyLevelIdAsPerGrade(
			Integer expectedProficiencyLevelIdAsPerGrade) {
		this.expectedProficiencyLevelIdAsPerGrade = expectedProficiencyLevelIdAsPerGrade;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCompetencyName() {
		return competencyName;
	}

	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}

	
	public String getCompetencyDefinition() {
		return competencyDefinition;
	}

	public void setCompetencyDefinition(String competencyDefinition) {
		this.competencyDefinition = competencyDefinition;
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

	public ReviewCompetencyVO() {
	}

	public int getReviewCompetencyId() {
		return this.reviewCompetencyId;
	}

	public void setReviewCompetencyId(int reviewCompetencyId) {
		this.reviewCompetencyId = reviewCompetencyId;
	}

	public String getAppraiseCommentText() {
		return appraiseCommentText;
	}

	public void setAppraiseCommentText(String appraiseCommentText) {
		this.appraiseCommentText = appraiseCommentText;
	}

	public String getAppraiserCommentText() {
		return appraiserCommentText;
	}

	public void setAppraiserCommentText(String appraiserCommentText) {
		this.appraiserCommentText = appraiserCommentText;
	}

	@Override
	public int compareTo(ReviewCompetencyVO reviewCompetencyVO) {

		if (this.reviewCompetencyId == reviewCompetencyVO.reviewCompetencyId) {

			return 0;
		}
		if (this.reviewCompetencyId > reviewCompetencyVO.reviewCompetencyId) {

			return 1;
		}
		else

			return -1;
	}

	public Integer getExpectedProficiencyLevelId() {
		return expectedProficiencyLevelId;
	}

	public void setExpectedProficiencyLevelId(Integer expectedProficiencyLevelId) {
		this.expectedProficiencyLevelId = expectedProficiencyLevelId;
	}

	public int getCompetencyId() {
		return competencyId;
	}

	public void setCompetencyId(int competencyId) {
		this.competencyId = competencyId;
	}

	public String getExpectedBehavioralIndicator() {
		return expectedBehavioralIndicator;
	}

	public void setExpectedBehavioralIndicator(String expectedBehavioralIndicator) {
		this.expectedBehavioralIndicator = expectedBehavioralIndicator;
	}

	public String getProficiencyLevelName() {
		return proficiencyLevelName;
	}

	public void setProficiencyLevelName(String proficiencyLevelName) {
		this.proficiencyLevelName = proficiencyLevelName;
	}

	public Integer getProficiencyLevelNumber() {
		return proficiencyLevelNumber;
	}

	public void setProficiencyLevelNumber(Integer proficiencyLevelNumber) {
		this.proficiencyLevelNumber = proficiencyLevelNumber;
	}

	public String getAppraiseRatingStr() {
		return appraiseRatingStr;
	}

	public void setAppraiseRatingStr(String appraiseRatingStr) {
		this.appraiseRatingStr = appraiseRatingStr;
	}

	public String getAppraiserRatingStr() {
		return appraiserRatingStr;
	}

	public void setAppraiserRatingStr(String apparaiserRatingStr) {
		this.appraiserRatingStr = apparaiserRatingStr;
	}
	
}
