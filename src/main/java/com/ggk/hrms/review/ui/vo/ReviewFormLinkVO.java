package com.ggk.hrms.review.ui.vo;

import java.math.BigInteger;

public class ReviewFormLinkVO {
	private String reviewCycleName;
	private Integer reviewHeaderId;
	private String reviewFormStatus;
	private Integer employeeId;
	private Integer mainAppraiserEmployeeId;
	private String mainAppraiserDisplayName;
	private String employeeDisplayName;
	private String email;
	private Boolean isReady;
	private AppraisalShareInfoVO appraisalShareInfoVO;
	private String grade;
	private String designation;
	private String project;
	private Integer appraiseRatingId;
	private Integer appraiserRatingId;
	private String reviewHeaderCheckBox;
	private String reviewCycleNameField;
	private String gradeAndDesgField;
	private String mainAppraiserDisplayNameField;
	
	/* To get shared information about an appraisal, previously we are making a db call to SharedAppraiser table using ReviewFormLinkVO.reviewHeaderId
	 * 
	 * Refer 	public AppraisalShareInfoVO ReviewHeaderService.getAppraisalShareInfo(int employeeId,int reviewHeaderId)
	 * 
	 * Instead fetching data along with ReviewFormLinkVO
	 * 
	 * */
  
	private Integer assignedByEmployeeId;
	private String assignedByEmployeeName;
	private Integer assignedToEmployeeId;
	private String assignedToEmployeeName;
	private Integer sharedAppraiserId;
	
	
	

	public ReviewFormLinkVO() {

	}
	
	public ReviewFormLinkVO(Integer reviewHeaderId, String employeeDisplayName,
			String email, String reviewCycleName, String reviewFormStatus,
			Integer employeeId, Integer mainAppraiserEmployeeId,
			String mainAppraiserDisplayName, String grade, String designation,
			String project, Integer appraiseRatingId, Integer appraiserRatingId) {
		this.reviewHeaderId = reviewHeaderId;
		this.reviewCycleName = reviewCycleName;
		this.reviewFormStatus = reviewFormStatus;
		this.employeeId = employeeId;
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
		this.employeeDisplayName = employeeDisplayName;
		this.email = email;
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
		this.grade = grade;
		this.designation = designation;
		this.project = project;
		this.appraiseRatingId = appraiseRatingId;
		this.appraiserRatingId = appraiserRatingId;
	}

	public ReviewFormLinkVO(Integer reviewHeaderId, String employeeDisplayName,
			String email, String reviewCycleName, String reviewFormStatus,
			Integer employeeId, Integer mainAppraiserEmployeeId,
			String mainAppraiserDisplayName, String grade, String designation,
			String project) {
		this.reviewHeaderId = reviewHeaderId;
		this.reviewCycleName = reviewCycleName;
		this.reviewFormStatus = reviewFormStatus;
		this.employeeId = employeeId;
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
		this.employeeDisplayName = employeeDisplayName;
		this.email = email;
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
		this.grade = grade;
		this.designation = designation;
		this.project = project;
	}
	
	public ReviewFormLinkVO(Integer reviewHeaderId, String employeeDisplayName,
			String email, String reviewCycleName, String reviewFormStatus,
			Integer employeeId, Integer mainAppraiserEmployeeId,
			String mainAppraiserDisplayName, String grade, String designation,
			String project,Integer sharedAppraiserId,Integer assignedByEmployeeId,String assignedByEmployeeName,Integer assignedToEmployeeId,String assignedToEmployeeName) {
		this.reviewHeaderId = reviewHeaderId;
		this.reviewCycleName = reviewCycleName;
		this.reviewFormStatus = reviewFormStatus;
		this.employeeId = employeeId;
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
		this.employeeDisplayName = employeeDisplayName;
		this.email = email;
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
		this.grade = grade;
		this.designation = designation;
		this.project = project;
		this.sharedAppraiserId = sharedAppraiserId;
		this.assignedByEmployeeId= assignedByEmployeeId;
		this.assignedByEmployeeName = assignedByEmployeeName;
		this.assignedToEmployeeId = assignedToEmployeeId;
		this.assignedToEmployeeName= assignedToEmployeeName;
	}

	public ReviewFormLinkVO(Integer reviewHeaderId, String reviewCycleName,
			String reviewFormStatus, Integer employeeId,
			Integer mainAppraiserEmployeeId, String mainAppraiserDisplayName,
			String project,Integer sharedAppraiserId,Integer assignedByEmployeeId,String assignedByEmployeeName,Integer assignedToEmployeeId,String assignedToEmployeeName) {
		this.reviewHeaderId = reviewHeaderId;
		this.reviewCycleName = reviewCycleName;
		this.reviewFormStatus = reviewFormStatus;
		this.employeeId = employeeId;
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
		this.project = project;
		this.sharedAppraiserId = sharedAppraiserId;
		this.assignedByEmployeeId= assignedByEmployeeId;
		this.assignedByEmployeeName = assignedByEmployeeName;
		this.assignedToEmployeeId = assignedToEmployeeId;
		this.assignedToEmployeeName= assignedToEmployeeName;
	}
	
	public ReviewFormLinkVO(Integer reviewHeaderId, String reviewCycleName,
			String reviewFormStatus, Integer employeeId,
			Integer mainAppraiserEmployeeId, String mainAppraiserDisplayName,
			String project) {
		this.reviewHeaderId = reviewHeaderId;
		this.reviewCycleName = reviewCycleName;
		this.reviewFormStatus = reviewFormStatus;
		this.employeeId = employeeId;
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
		this.project = project;
	}

	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getMainAppraiserEmployeeId() {
		return mainAppraiserEmployeeId;
	}

	public void setMainAppraiserEmployeeId(Integer mainAppraiserEmployeeId) {
		this.mainAppraiserEmployeeId = mainAppraiserEmployeeId;
	}

	public String getReviewFormStatus() {
		return reviewFormStatus;
	}

	public void setReviewFormStatus(String reviewFormStatus) {
		this.reviewFormStatus = reviewFormStatus;
	}

	public String getReviewCycleName() {
		return reviewCycleName;
	}

	public void setReviewCycleName(String reviewCycleName) {
		this.reviewCycleName = reviewCycleName;
	}

	public int getReviewHeaderId() {
		return reviewHeaderId;
	}

	public void setReviewHeaderId(int reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}

	public String getEmployeeDisplayName() {
		return employeeDisplayName;
	}

	public void setEmployeeDisplayName(String employeeDisplayName) {
		this.employeeDisplayName = employeeDisplayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public void setReviewHeaderId(Integer reviewHeaderId) {
		this.reviewHeaderId = reviewHeaderId;
	}


	public String getMainAppraiserDisplayName() {
		return mainAppraiserDisplayName;
	}

	public void setMainAppraiserDisplayName(String mainAppraiserDisplayName) {
		if(mainAppraiserDisplayName== null) mainAppraiserDisplayName="Unassigned";
		this.mainAppraiserDisplayName = mainAppraiserDisplayName;
	}

	public AppraisalShareInfoVO getAppraisalShareInfoVO() {
		return appraisalShareInfoVO;
	}

	public void setAppraisalShareInfoVO(
			AppraisalShareInfoVO appraisalShareInfoVO) {
		this.appraisalShareInfoVO = appraisalShareInfoVO;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		if (grade == null) grade="NA";
		this.grade = grade;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		if (designation == null) designation="NA";
		this.designation = designation;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		if (project == null) project="NA";
		this.project = project;
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
	
	public void setReviewHeaderCheckBox(String reviewHeaderCheckBox) {
		this.reviewHeaderCheckBox=reviewHeaderCheckBox;
	}
	public String getReviewHeaderCheckBox() {
		return reviewHeaderCheckBox;
	}
	public String getReviewCycleNameField() {
		return reviewCycleNameField;
	}
	public void setReviewCycleNameField(String reviewCycleNameField) {
		this.reviewCycleNameField = reviewCycleNameField;
	}
	public String getGradeAndDesgField() {
		return gradeAndDesgField;
	}
	public void setGradeAndDesgField(String gradeAndDesgField) {
		this.gradeAndDesgField = gradeAndDesgField;
	}
	public String getMainAppraiserDisplayNameField() {
		return mainAppraiserDisplayNameField;
	}
	public void setMainAppraiserDisplayNameField(
			String mainAppraiserDisplayNameField) {
		this.mainAppraiserDisplayNameField = mainAppraiserDisplayNameField;
	}

	public Integer getAssignedByEmployeeId() {
		return assignedByEmployeeId;
	}

	public void setAssignedByEmployeeId(Integer assignedByEmployeeId) {
		this.assignedByEmployeeId = assignedByEmployeeId;
	}

	public String getAssignedByEmployeeName() {
		return assignedByEmployeeName;
	}

	public void setAssignedByEmployeeName(String assignedByEmployeeName) {
		this.assignedByEmployeeName = assignedByEmployeeName;
	}

	public Integer getAssignedToEmployeeId() {
		return assignedToEmployeeId;
	}

	public void setAssignedToEmployeeId(Integer assignedToEmployeeId) {
		this.assignedToEmployeeId = assignedToEmployeeId;
	}

	public String getAssignedToEmployeeName() {
		return assignedToEmployeeName;
	}

	public void setAssignedToEmployeeName(String assignedToEmployeeName) {
		this.assignedToEmployeeName = assignedToEmployeeName;
	}

	public Integer getSharedAppraiserId() {
		return sharedAppraiserId;
	}

	public void setSharedAppraiserId(Integer sharedAppraiserId) {
		this.sharedAppraiserId = sharedAppraiserId;
	}
	
	public void setRowNum(BigInteger rowNum){
		
	}

}
