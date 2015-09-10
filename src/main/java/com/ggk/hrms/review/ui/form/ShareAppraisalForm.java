package com.ggk.hrms.review.ui.form;

import java.util.List;

public class ShareAppraisalForm {
	
	private Integer targetEmployeeId;
	private List<Integer> selectedAppraisals;
	public Integer getTargetEmployeeId() {
		return targetEmployeeId;
	}
	public void setTargetEmployeeId(Integer targetEmployeeId) {	
		this.targetEmployeeId = targetEmployeeId;
	}
	public List<Integer> getSelectedAppraisals() {
		return selectedAppraisals;
	}
	public void setSelectedAppraisals(List<Integer> selectedAppraisals) {
		this.selectedAppraisals = selectedAppraisals;
	}
	
	

}
