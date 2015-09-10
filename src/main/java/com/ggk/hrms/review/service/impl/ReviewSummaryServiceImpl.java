package com.ggk.hrms.review.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewSummary;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.constants.ReviewPhase;
import com.ggk.hrms.review.dao.ReviewSummaryDAO;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewSummaryService;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;
import com.ggk.hrms.review.ui.vo.ReviewSummaryVO;

@Service("reviewSummaryService")
public class ReviewSummaryServiceImpl implements ReviewSummaryService {
	
	@Autowired
	private ReviewSummaryDAO reviewSummaryDAO;
	
	@Autowired
	private RatingService ratingService;

	@Override
	public void convertToVO(ReviewSummaryVO reviewSummaryVO,
			ReviewSummary reviewSummary) {

		if(reviewSummary == null)return;

		if (reviewSummary.getAppraiseRating() != null) {
			reviewSummaryVO.setAppraiseRatingId(reviewSummary.getAppraiseRating().getRatingId());
		}
		if (reviewSummary.getAppraiseComment() != null) {
			reviewSummaryVO
					.setAppraiseComment(reviewSummary.getAppraiseComment().getCommentText());
		}
		if (reviewSummary.getAppraiserRating() != null) {
			reviewSummaryVO.setAppraiserRatingId(reviewSummary.getAppraiserRating().getRatingId());
		}
		if (reviewSummary.getAppraiserComment() != null) {
			reviewSummaryVO.setAppraiserComment(reviewSummary.getAppraiserComment()
					.getCommentText());
		}
		
		if (reviewSummary.getAppraiseRating() != null) {
			reviewSummaryVO.setAppraiseRatingStr(reviewSummary.getAppraiseRating().getRating()+"-"+reviewSummary.getAppraiseRating().getShortDescription());
		}
		if (reviewSummary.getAppraiserComment() != null) {
			reviewSummaryVO.setAppraiserRatingStr(reviewSummary.getAppraiserRating().getRating()+"-"+reviewSummary.getAppraiserRating().getShortDescription());
		}

	}

	@Override
	public void convertToDTO(ReviewSummary reviewSummary,
			ReviewSummaryVO reviewSummaryVO, ReviewFormInfoVO reviewFormInfoVO) {

		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISE) {

			if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE1_COMPLETED
					|| reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISE_IN_PHASE2) {
				
				if (reviewSummaryVO.getAppraiseComment()  == null
						|| reviewSummaryVO.getAppraiseComment() .trim().equals("")) {
					reviewSummary.setAppraiseComment(null);
				} else {
					if (reviewSummary.getAppraiseComment() == null) {
						reviewSummary.setAppraiseComment(new Comment());
					}
					reviewSummary.getAppraiseComment().setCommentText(
							reviewSummaryVO.getAppraiseComment());
				}
				if (reviewSummaryVO.getAppraiseRatingId() != -1) {
					Rating appraiseRating = ratingService
							.getRatingById(reviewSummaryVO.getAppraiseRatingId());
					reviewSummary.setAppraiseRating(appraiseRating);

				}
				if (reviewSummaryVO.getAppraiseRatingId() == -1) {

					reviewSummary.setAppraiseRating(null);
				}

			}

		}
		if (reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.APPRAISER
				|| reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SHARED_APPRAISER
				|| reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER) {

			if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.APPRAISER_IN_PHASE2) {
				if (reviewSummaryVO.getAppraiserComment()  == null
						|| reviewSummaryVO.getAppraiserComment() .trim().equals("")) {
					reviewSummary.setAppraiserComment(null);
				} else {
					if (reviewSummary.getAppraiserComment() == null) {
						reviewSummary.setAppraiserComment(new Comment());
					}
					reviewSummary.getAppraiserComment().setCommentText(
							reviewSummaryVO.getAppraiserComment());
				}
				if (reviewSummaryVO.getAppraiserRatingId() != -1) {
					Rating appraiserRating = ratingService
							.getRatingById(reviewSummaryVO.getAppraiserRatingId());
					reviewSummary.setAppraiserRating(appraiserRating);

				}
				if (reviewSummaryVO.getAppraiserRatingId() == -1) {

					reviewSummary.setAppraiserRating(null);
				}

			}if (reviewFormInfoVO.getReviewPhase() == ReviewPhase.SYSTEM_PHASE2_COMPLETED && reviewFormInfoVO.getReviewFormRole() == ReviewFormRole.SUPERUSER){
				
				if (reviewSummaryVO.getAppraiserRatingId() != -1) {
					Rating appraiserRating = ratingService
							.getRatingById(reviewSummaryVO.getAppraiserRatingId());
					reviewSummary.setAppraiserRating(appraiserRating);

				}else{
					
					reviewSummary.setAppraiserRating(null);
				}
			}

		}

	}

	@Override
	public ReviewSummary getReviewSummary(int reviewHeaderId,
			ReviewFormRole reviewFormRole) {
		return reviewSummaryDAO.getReviewSummary(reviewHeaderId, reviewFormRole);
	}

	@Override
	public ReviewSummary saveReviewSummary(ReviewSummary reviewSummary) {
		return reviewSummaryDAO.saveReviewSummary(reviewSummary);
	}

	@Override
	public void copyReviewSummary(int reviewHeaderId, ReviewFormRole owner,
			String actionType) {
		reviewSummaryDAO.copyReviewSummary(reviewHeaderId, owner, actionType);
	}

	@Override
	public ReviewSummary saveSuperUserReviewSummary(int reviewHeaderId,
			Rating appraiserRating) {
		return reviewSummaryDAO.saveSuperUserReviewSummary(reviewHeaderId,appraiserRating);
	}
	
	public void fillDetails(List<ReviewFormLinkVO> reviewFormLinks,
			Map<String, List<ReviewFormLinkVO>> detailsMap, String excelType) {
		for (int i = 0; i < reviewFormLinks.size(); i++) {
				if (excelType.equals("projects")) {
					if (detailsMap.size() == 0) {
						List<ReviewFormLinkVO> objects = new ArrayList<ReviewFormLinkVO>();
						objects.add(reviewFormLinks.get(i));
						detailsMap.put(reviewFormLinks.get(i)
								.getProject(), objects);
					} else {
					
						Set<String> key = detailsMap.keySet();
						if (key.contains(reviewFormLinks.get(i)
								.getProject())) {
							List<ReviewFormLinkVO> values = detailsMap
									.get(reviewFormLinks.get(i)
											.getProject());
							values.add(reviewFormLinks.get(i));
						} else {
							List<ReviewFormLinkVO> objects = new ArrayList<ReviewFormLinkVO>();
							objects.add(reviewFormLinks.get(i));
							detailsMap.put(reviewFormLinks.get(i)
									.getProject(), objects);
						}
					}
				}
				else if (excelType.equals("managers")) {
					if (detailsMap.size() == 0) {
						List<ReviewFormLinkVO> objects = new ArrayList<ReviewFormLinkVO>();
						objects.add(reviewFormLinks.get(i));
						detailsMap.put(reviewFormLinks.get(i)
								.getMainAppraiserDisplayName(), objects);
					} else {
						Set<String> key = detailsMap.keySet();
						if (key.contains(reviewFormLinks.get(i)
								.getMainAppraiserDisplayName())) {
							List<ReviewFormLinkVO> values = detailsMap
									.get(reviewFormLinks.get(i)
											.getMainAppraiserDisplayName());
							values.add(reviewFormLinks.get(i));
						} else {
							List<ReviewFormLinkVO> objects = new ArrayList<ReviewFormLinkVO>();
							objects.add(reviewFormLinks.get(i));
							detailsMap.put(reviewFormLinks.get(i)
									.getMainAppraiserDisplayName(), objects);
						}
					}
				}
			}
		}
	
	public void generateExcelSheet(Set<String> keyset, HSSFWorkbook workbook, Map<String, List<ReviewFormLinkVO>> detailsMap, List<String> excelSheets, String excelType) {
		HSSFSheet sheet = null;
		for (String key : keyset) {
			sheet = workbook.createSheet(key);

			int headerRowCellnum = 2;
			Row headingRow = sheet.createRow(3);

			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			HSSFCellStyle headingRowStyle = workbook.createCellStyle();
			headingRowStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			headingRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headingRowStyle.setFont(font);
			
			for (String excelSheet : excelSheets) {
				headerRowCellnum += 1;
				sheet.autoSizeColumn(headerRowCellnum);
				Cell headingRowDisplayName = headingRow
						.createCell(headerRowCellnum);
				headingRowDisplayName.setCellValue(excelSheet);
				headingRowDisplayName.setCellStyle(headingRowStyle);
			}

			List<ReviewFormLinkVO> objArr = detailsMap.get(key);
			int rownum = 4;
			int cellnum = 2;
			for (ReviewFormLinkVO obj : objArr) {
				cellnum = 2;

				Row row = sheet.createRow(rownum++);
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(HSSFColor.WHITE.index);
				style.setFillPattern(HSSFCellStyle.NO_FILL);

				cellnum += 1;
				
				Cell employeeId = row.createCell(cellnum);
				employeeId.setCellValue(obj.getEmployeeId().toString());
				employeeId.setCellStyle(style);
				
				cellnum += 1;
				
				Cell displayName = row.createCell(cellnum);
				displayName.setCellValue(obj.getEmployeeDisplayName());
				displayName.setCellStyle(style);

				cellnum += 1;
				
				Cell email = row.createCell(cellnum);
				email.setCellValue(obj.getEmail());
				email.setCellStyle(style);

				cellnum += 1;
				
				Cell grade = row.createCell(cellnum);
				grade.setCellValue(obj.getGrade());
				grade.setCellStyle(style);

				cellnum += 1;
				
				Cell status = row.createCell(cellnum);
				status.setCellValue(obj.getReviewFormStatus());
				status.setCellStyle(style);

				if (excelType.equals("projects")) {
					cellnum += 1;
					
					Cell mainAppraiser = row.createCell(cellnum);
					mainAppraiser.setCellValue(obj.getMainAppraiserDisplayName());
					mainAppraiser.setCellStyle(style);
				}
				
				else if (excelType.equals("managers")) {
					cellnum += 1;
					
					Cell mainAppraiser = row.createCell(cellnum);
					mainAppraiser.setCellValue(obj.getProject());
					mainAppraiser.setCellStyle(style);
				}
				
				cellnum += 1;
				
				Cell appraiseRating = row.createCell(cellnum);
				if (obj.getAppraiseRatingId() != null) {
					appraiseRating.setCellValue(obj.getAppraiseRatingId());
				}
				appraiseRating.setCellStyle(style);
				
				cellnum += 1;
				
				Cell appraiserRating = row.createCell(cellnum);
				if (obj.getAppraiserRatingId() != null) {
					appraiserRating.setCellValue(obj.getAppraiserRatingId());
				}
				appraiserRating.setCellStyle(style);
			}
			
			for (int i = 3; i <= cellnum; i++) {
				sheet.autoSizeColumn(i);
				
			}
		}
	}

	@Override
	public void generateSingleExcelSheet(HSSFWorkbook workbook,
			List<ReviewFormLinkVO> reviewFormLinks,
			List<String> excelSheets) {
		
			HSSFSheet sheet = null;
		
			sheet = workbook.createSheet("GGKTECH_ALL");

			int headerRowCellnum = 2;
			Row headingRow = sheet.createRow(3);

			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);

			HSSFCellStyle headingRowStyle = workbook.createCellStyle();
			headingRowStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			headingRowStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headingRowStyle.setFont(font);
			
			for (String excelSheet : excelSheets) {
				headerRowCellnum += 1;
				sheet.autoSizeColumn(headerRowCellnum);
				Cell headingRowDisplayName = headingRow
						.createCell(headerRowCellnum);
				headingRowDisplayName.setCellValue(excelSheet);
				headingRowDisplayName.setCellStyle(headingRowStyle);
			}

			
			int rownum = 4;
			int cellnum = 2;
			for (ReviewFormLinkVO obj : reviewFormLinks) {
				cellnum = 2;

				Row row = sheet.createRow(rownum++);
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFillBackgroundColor(HSSFColor.WHITE.index);
				style.setFillPattern(HSSFCellStyle.NO_FILL);
				
				cellnum += 1;
				
				Cell employeeId = row.createCell(cellnum);
				employeeId.setCellValue(obj.getEmployeeId().toString());
				employeeId.setCellStyle(style);

				cellnum += 1;
				
				Cell displayName = row.createCell(cellnum);
				displayName.setCellValue(obj.getEmployeeDisplayName());
				displayName.setCellStyle(style);

				cellnum += 1;
				
				Cell email = row.createCell(cellnum);
				email.setCellValue(obj.getEmail());
				email.setCellStyle(style);

				cellnum += 1;
				
				Cell grade = row.createCell(cellnum);
				grade.setCellValue(obj.getGrade());
				grade.setCellStyle(style);

				cellnum += 1;
				
				Cell status = row.createCell(cellnum);
				status.setCellValue(obj.getReviewFormStatus());
				status.setCellStyle(style);

				cellnum += 1;
				
				Cell mainAppraiser = row.createCell(cellnum);
				mainAppraiser.setCellValue(obj.getMainAppraiserDisplayName());
				mainAppraiser.setCellStyle(style);
				
				cellnum += 1;
				
				Cell project = row.createCell(cellnum);
				project.setCellValue(obj.getProject());
				project.setCellStyle(style);
				
				cellnum += 1;
				
				Cell appraiseRating = row.createCell(cellnum);
				if (obj.getAppraiseRatingId() != null) {
					appraiseRating.setCellValue(obj.getAppraiseRatingId());
				}
				appraiseRating.setCellStyle(style);
				
				cellnum += 1;
				
				Cell appraiserRating = row.createCell(cellnum);
				if (obj.getAppraiserRatingId() != null) {
					appraiserRating.setCellValue(obj.getAppraiserRatingId());
				}
				appraiserRating.setCellStyle(style);
			}
			
			for (int i = 3; i <= cellnum; i++) {
				sheet.autoSizeColumn(i);
				
			}
		
		
		
	}
}
