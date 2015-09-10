package com.ggk.hrms.review.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;
import com.ggk.hrms.review.ui.vo.ReviewSummaryVO;

public interface ReviewSummaryService {

	void convertToVO(ReviewSummaryVO reviewSummaryVO,
			ReviewSummary reviewSummary);

	public void convertToDTO(ReviewSummary reviewSummary,
			ReviewSummaryVO reviewSummaryVO, ReviewFormInfoVO reviewFormInfoVO);

	public ReviewSummary getReviewSummary(int reviewHeaderId,
			ReviewFormRole reviewFormRole);

	public ReviewSummary saveReviewSummary(ReviewSummary reviewSummary);

	void copyReviewSummary(int reviewHeaderId, ReviewFormRole owner,
			String actionType);

	ReviewSummary saveSuperUserReviewSummary(int reviewHeaderId, Rating rating);

	void fillDetails(List<ReviewFormLinkVO> reviewFormLinks,
			Map<String, List<ReviewFormLinkVO>> detailsMap, String excelType);

	void generateExcelSheet(Set<String> keyset, HSSFWorkbook workbook,
			Map<String, List<ReviewFormLinkVO>> detailsMap, List<String> excelSheets,
			String excelType);

	void generateSingleExcelSheet(HSSFWorkbook workbook,
			List<ReviewFormLinkVO> reviewFormLinks,
			List<String> singleExcelSheetsPerReviewCycle);
}
