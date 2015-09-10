package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

/**
 * @author Swaroops
 *
 * The persistent class for the [ONOMinites] database table.
 */
@Entity
public class ONOMinutes implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ONOMinutesId")
	private int minutesId;
	
	@OneToOne
	@JoinColumn(name = "AppraiseId")
	@ForeignKey(name = "FK_ONOMinutes_Appraise")
	private Employee apparise;
	
	@OneToOne
	@JoinColumn(name = "AppraiserId")
	@ForeignKey(name = "FK_ONOMinutes_Appraiser")
	private Employee appraiser;
	
	@OneToOne
	@JoinColumn(name = "CreatedId")
	@ForeignKey(name = "FK_ONOMinutes_Created")
	private Employee createdEmployee;
	
	@OneToOne
	@JoinColumn(name = "LastUpdatedId")
	@ForeignKey(name = "FK_ONOMinutes_LastUpdated")
	private Employee lastUpdateEmployee;
	
	@Column(name = "MeetingMinutes")
	private String meetingMinutes;
	
	@Column(name = "CreatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	
	@Column(name = "LastUpdatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;
	
	@Column(name = "IsApproved")
	private boolean isApproved;


	public int getMinutesId() {
		return minutesId;
	}


	public void setMinitesId(int minitesId) {
		this.minutesId = minitesId;
	}


	public Employee getApparise() {
		return apparise;
	}


	public void setApparise(Employee apparise) {
		this.apparise = apparise;
	}


	public Employee getAppraiser() {
		return appraiser;
	}


	public void setAppraiser(Employee appraiser) {
		this.appraiser = appraiser;
	}


	public Employee getCreatedEmployee() {
		return createdEmployee;
	}


	public void setCreatedEmployee(Employee createdEmployee) {
		this.createdEmployee = createdEmployee;
	}


	public Employee getLastUpdateEmployee() {
		return lastUpdateEmployee;
	}


	public void setLastUpdateEmployee(Employee lastUpdateEmployee) {
		this.lastUpdateEmployee = lastUpdateEmployee;
	}


	public String getMeetingMinutes() {
		return meetingMinutes;
	}


	public void setMeetingMinutes(String meetingMinites) {
		this.meetingMinutes = meetingMinites;
	}


	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}


	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}



	public void setMinutesId(int minutesId) {
		this.minutesId = minutesId;
	}


	public boolean getIsApproved() {
		return isApproved;
	}


	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	

}
