package com.ggk.hrms.review.beans.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

@Entity
public class SharedAppraiser implements Serializable {
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="SharedAppraiserId")
	private int sharedAppraiserId;

	// bi-directional many-to-one association to ReviewHeader
		@ManyToOne
		@JoinColumn(name = "ReviewHeaderId")
		@ForeignKey(name = "FK_SharedAppraiser_ReviewHeader")
		private ReviewHeader reviewHeader;
		
		
		@OneToOne
		@JoinColumn(name = "AssignedByEmployeeId")
		@ForeignKey(name = "FK_SharedAppraiser_Byemp")
		private Employee assignedByEmployee;
		
		@OneToOne
		@JoinColumn(name = "AssignedToEmployeeId")
		@ForeignKey(name = "FK_SharedAppraiser_Toemp")
		private Employee assignedToEmployee;


		@Column(name = "AssignedDate", insertable=false)
		@Temporal(TemporalType.TIMESTAMP)
		private Date assignedDate;
		
		@Column(name = "IsShared")
		private boolean isShared;
		
		@Column(name = "IsActive")
		private boolean isActive;
		
		@Column(name = "DueDate")
		@Temporal(TemporalType.TIMESTAMP)
		private Date dueDate;
		
		@Column(name = "IsDelegated")
		private boolean isDelegated;

		public int getSharedAppraiserId() {
			return sharedAppraiserId;
		}

		public void setSharedAppraiserId(int sharedAppraiserId) {
			this.sharedAppraiserId = sharedAppraiserId;
		}

		public ReviewHeader getReviewHeader() {
			return reviewHeader;
		}

		public void setReviewHeader(ReviewHeader reviewHeader) {
			this.reviewHeader = reviewHeader;
		}

		public Employee getAssignedByEmployee() {
			return assignedByEmployee;
		}

		public void setAssignedByEmployee(Employee assignedByEmployee) {
			this.assignedByEmployee = assignedByEmployee;
		}

		public Employee getAssignedToEmployee() {
			return assignedToEmployee;
		}

		public void setAssignedToEmployee(Employee assignedToEmployee) {
			this.assignedToEmployee = assignedToEmployee;
		}

		public Date getAssignedDate() {
			return assignedDate;
		}

		public void setAssignedDate(Date assignedDate) {
			this.assignedDate = assignedDate;
		}

		public boolean isShared() {
			return isShared;
		}

		public void setShared(boolean isShared) {
			this.isShared = isShared;
		}

		public Date getDueDate() {
			return dueDate;
		}

		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}

		public boolean isDelegated() {
			return isDelegated;
		}

		public void setDelegated(boolean isDelegated) {
			this.isDelegated = isDelegated;
		}

		public boolean isActive() {
			return isActive;
		}

		public void setActive(boolean isActive) {
			this.isActive = isActive;
		}
		
		

		
}
