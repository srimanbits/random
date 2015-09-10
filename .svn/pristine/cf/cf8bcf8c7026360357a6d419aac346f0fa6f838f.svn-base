package com.ggk.hrms.review.beans.domain;

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

@Entity
public class EmpSelfNotes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SelfNotesId")
	private int selfNotesId;
	
	@OneToOne
	@JoinColumn(name = "EmployeeId")
	@ForeignKey(name = "FK_SelfNotes_emp")
	private Employee employee;
	
	@Column(name = "Notes")
	private String notes;
	
	@Column(name = "TempNotes")
	private String tempNotes;
	
	@Column(name = "LastUpdatedDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	public int getSelfNotesId() {
		return selfNotesId;
	}

	public void setSelfNotesId(int selfNotesId) {
		this.selfNotesId = selfNotesId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTempNotes() {
		return tempNotes;
	}

	public void setTempNotes(String tempNotes) {
		this.tempNotes = tempNotes;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public void appendNotes(String notesText){
		
		this.notes +="\n"+notesText; 
	}

}
