package com.ggk.hrms.review.dao;

import com.ggk.hrms.review.beans.domain.EmpSelfNotes;

public interface EmpSelfNotesDao {

	EmpSelfNotes getEmpSelfNotes(int empId);
	void saveEmpSelfNotes(EmpSelfNotes notes);
}
