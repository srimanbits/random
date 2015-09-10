package com.ggk.hrms.review.dao;

import java.util.List;

import com.ggk.hrms.review.beans.domain.Designation;
import com.ggk.hrms.review.beans.domain.Grade;

public interface GradeDAO {

	public Grade getGradeById(int gradeId);
	public int getIdByGrade(Grade grade);
	public List<Grade> getAllActiveGrades();
	public Designation getDesignation(int designationId); 
}
