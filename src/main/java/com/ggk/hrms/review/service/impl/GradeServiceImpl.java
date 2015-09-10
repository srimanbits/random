package com.ggk.hrms.review.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.ggk.hrms.review.beans.domain.Grade;
import com.ggk.hrms.review.dao.GradeDAO;
import com.ggk.hrms.review.service.GradeService;


@Repository("gradeService")
public class GradeServiceImpl implements GradeService {
	
	@Resource
	private GradeDAO gradeDAO;

	@Override
	//@Cacheable(value ="grade", key = "'getAllActiveGrades'")
	public List<Grade> getAllActiveGrades() {
		return gradeDAO.getAllActiveGrades();
	}

}
