package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(region="com.ggk.hrms.review.beans.domain.AppraisalPeriodType",usage=CacheConcurrencyStrategy.READ_ONLY)
public class AppraisalPeriodType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="AppraisalPeriodTypeId")
	private int appraisalPeriodTypeId;
	
	@Column(name="AppraisalPeriodTypeName")
	private String appraisalPeriodTypeName;
	
	@Column(name="AppraisalPeriodTypeCode")
	private String appraisalPeriodTypeCode;
	

	public int getAppraisalPeriodTypeId() {
		return appraisalPeriodTypeId;
	}

	public String getAppraisalPeriodTypeName() {
		return appraisalPeriodTypeName;
	}

	public String getAppraisalPeriodTypeCode() {
		return appraisalPeriodTypeCode;
	}

	public void setAppraisalPeriodTypeId(int appraisalPeriodTypeId) {
		this.appraisalPeriodTypeId = appraisalPeriodTypeId;
	}

	public void setAppraisalPeriodTypeName(String appraisalPeriodTypeName) {
		this.appraisalPeriodTypeName = appraisalPeriodTypeName;
	}

	public void setAppraisalPeriodTypeCode(String appraisalPeriodTypeCode) {
		this.appraisalPeriodTypeCode = appraisalPeriodTypeCode;
	}


}
