package com.ggk.hrms.review.service;

import java.util.List;

import com.ggk.hrms.review.beans.domain.SharedAppraiser;

public interface SharedAppraiserService {

	void shareAppraisalsToEmployee(List<Integer> appraisalList,
			int targetEmployeeId, int assignedByemployeeId, int reviewCycleId);

	SharedAppraiser getActiveSharedAppraiser(int reviewHeaderId);

	SharedAppraiser update(SharedAppraiser sharedAppraiser);

}
