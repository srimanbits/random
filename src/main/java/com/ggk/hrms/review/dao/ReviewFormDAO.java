package com.ggk.hrms.review.dao;

import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;

public interface ReviewFormDAO {
	
	
	public String validateAppraisal(int reviewHeaderId,ReviewFormInfoVO reviewFormInfoVO);

}
