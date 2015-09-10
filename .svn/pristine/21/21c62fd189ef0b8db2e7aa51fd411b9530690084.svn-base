package com.ggk.hrms.review.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Comment;
import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.beans.domain.SharedAppraiser;
import com.ggk.hrms.review.dao.EmployeeDao;
import com.ggk.hrms.review.dao.ReviewHeaderDAO;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.RatingService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.vo.AppraisalShareInfoVO;
import com.ggk.hrms.review.ui.vo.OtherAppraisalsVO;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormLinkVO;
import com.ggk.hrms.review.ui.vo.ReviewHeaderVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Service(value = "reviewHeaderService")
public class ReviewHeaderServiceImpl implements ReviewHeaderService {
	
	private static Logger logger = LoggerFactory.getLogger(ReviewHeaderServiceImpl.class);

	@Autowired
	private ReviewHeaderDAO reviewHeaderDAO;

	/*@Autowired
	private RatingDAO ratingDAO;*/
	
	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeService empService;

	@Override
	public ReviewHeader saveReviewHeader(ReviewHeaderVO reviewHeaderVO,
			String reviewFormRole, String reviewPhase) {
		ReviewHeader reviewHeader = getReviewHeaderById(reviewHeaderVO.getReviewHeaderId());
		convertToDTO(reviewHeader, reviewHeaderVO, reviewFormRole, reviewPhase);
		reviewHeader.setLastModifiedDate(new Date());
		Employee loginEmployee=empService.getEmployeeById(SecurityDetailsAccessor.getEmpId());
		reviewHeader.setLastModifiedByEmployee(loginEmployee);
		return reviewHeaderDAO.saveReviewHeader(reviewHeader);
	}

	

	@Override
	public void convertToVO(ReviewHeaderVO reviewHeaderVO,
			ReviewHeader reviewHeader) {
		Integer reviewHeaderId = reviewHeader.getReviewHeaderId();
		if (reviewHeaderId != null) {
			reviewHeaderVO.setReviewHeaderId(reviewHeaderId);
		}
		Rating appraiseRating = reviewHeader.getAppraiseRating();
		if (appraiseRating != null) {
			reviewHeaderVO.setAppraiseRatingId(appraiseRating.getRatingId());
		}
		Comment appraiseComment = reviewHeader.getAppraiseComment();
		if (appraiseComment != null) {
			reviewHeaderVO.setAppraiseComment(appraiseComment.getCommentText());
		}
		Rating appraiserRating = reviewHeader.getAppraiserRating();
		if (appraiserRating != null) {
			reviewHeaderVO.setAppraiserRatingId(appraiserRating.getRatingId());
		}
		Comment appraiserComment = reviewHeader.getAppraiserComment();
		if (appraiserComment != null) {
			reviewHeaderVO.setAppraiserComment(appraiserComment
					.getCommentText());
		}
		Comment appraiseInternalComment = reviewHeader.getAppraiseInternalComment();
		if (appraiseInternalComment != null) {
			reviewHeaderVO.setAppraiseInternalComment(appraiseInternalComment.getCommentText());
		}
		
		Comment appraiserInternalComment = reviewHeader.getAppraiserInternalComment();
		if (appraiserInternalComment != null) {
			reviewHeaderVO.setAppraiserInternalComment(appraiserInternalComment.getCommentText());
		}

	}

	public void convertToDTO(ReviewHeader reviewHeader,
			ReviewHeaderVO reviewHeaderVO, String reviewFormRole,
			String reviewPhase) {
		int reviewHeaderId = reviewHeaderVO.getReviewHeaderId().intValue();
		reviewHeader.setReviewHeaderId(reviewHeaderId);

		String appraiseCommentText = null;
		String appraiserCommentText = null;
		Integer appraiseRatingId = null;
		Integer appraiserRatingId = null;

		Comment appraiseComment = null;
		Comment appraiserComment = null;
		Comment appraiseInternalComment = reviewHeader.getAppraiseInternalComment();
		Comment appraiserInternalComment=reviewHeader.getAppraiserInternalComment();
		Rating appraiseRating = null;
		Rating appraiserRating = null;
		if (reviewHeaderVO.getAppraiseComment() != null) {
			if (reviewHeaderVO.getAppraiseComment().trim().length() != 0) {
				appraiseCommentText = reviewHeaderVO.getAppraiseComment();
			}
		}
		if (reviewHeaderVO.getAppraiserComment() != null) {
			if (reviewHeaderVO.getAppraiserComment().trim().length() != 0) {
				appraiserCommentText = reviewHeaderVO.getAppraiserComment();
			}
		}
		
		if (reviewHeaderVO.getAppraiseRatingId() != null) {
			appraiseRatingId = reviewHeaderVO.getAppraiseRatingId();
		}
		if (reviewHeaderVO.getAppraiserRatingId() != null) {
			appraiserRatingId = reviewHeaderVO.getAppraiserRatingId();
		}

		if ((reviewFormRole.equals("APPRAISER")||reviewFormRole.equals("SHARED_APPRAISER")||reviewFormRole.equals("SUPERUSER"))
				&& reviewPhase.equals("APPRAISER_IN_PHASE2"))
				
		{

			if (reviewHeader.getAppraiseComment() != null) {
				appraiseComment = reviewHeader.getAppraiseComment();
				appraiseCommentText = appraiseComment.getCommentText();

			}
		}
		
		 else {
			if (appraiseCommentText != null) {

				// if the comment filed is null in the existing object
				if (reviewHeader.getAppraiseComment() == null) {
					appraiseComment = new Comment();
				}
				// if the comment field is not null in the existing object
				else {
					// assigning that existing comment to goalComment
					appraiseComment = reviewHeader.getAppraiseComment();
				}	
		}
		 }
		// setting the text from UI to db comment object
		if (appraiseComment != null) {
			appraiseComment.setCommentText(appraiseCommentText);
		}

		if ((reviewFormRole.equals("APPRAISER")||reviewFormRole.equals("SHARED_APPRAISER")||reviewFormRole.equals("SUPERUSER"))
				&& reviewPhase.equals("APPRAISER_IN_PHASE2")) {

			if (reviewHeader.getAppraiseRating() != null) {
				appraiseRating = reviewHeader.getAppraiseRating();

			}
		}
		
		else // if user set the appraise rating drop down from UI
			if (appraiseRatingId != null && appraiseRatingId!=-1) {

				appraiseRating = ratingService.getRatingById(appraiseRatingId);

			} 

		
		if (reviewFormRole.equals("APPRAISE")
				&& reviewPhase.equals("APPRAISE_IN_PHASE2"))
				
		{
			if (reviewHeader.getAppraiserComment() != null) {
				appraiserComment = reviewHeader.getAppraiserComment();
				appraiserCommentText = appraiserComment.getCommentText();

			}
		}
		 else {
			 if (appraiserCommentText != null) {

					// if the comment filed is null in the existing object
					if (reviewHeader.getAppraiserComment() == null) {
						appraiserComment = new Comment();
					}
					// if the comment field is not null in the existing object
					else {
						// assigning that existing comment to goalComment
						appraiserComment = reviewHeader.getAppraiserComment();
					}

				}
		}
		// setting the text from UI to db comment object
		if (appraiserComment != null) {
			appraiserComment.setCommentText(appraiserCommentText);
		}

		if (reviewFormRole.equals("APPRAISE")
				&& reviewPhase.equals("APPRAISE_IN_PHASE2")) {

			if (reviewHeader.getAppraiserRating() != null) {
				appraiserRating = reviewHeader.getAppraiserRating();

			}
		}
		
		else // if user set the appraise rating drop down from UI
			if (appraiserRatingId != null && appraiserRatingId!=-1) {

				appraiserRating = ratingService.getRatingById(appraiserRatingId);

			} 


		
		reviewHeader.setAppraiseRating(appraiseRating);
		reviewHeader.setAppraiserRating(appraiserRating);
		reviewHeader.setAppraiseComment(appraiseComment);
		reviewHeader.setAppraiserComment(appraiserComment);
		reviewHeader.setAppraiseInternalComment(appraiseInternalComment);
		reviewHeader.setAppraiserInternalComment(appraiserInternalComment);
		
		// reviewHeader.setReviewStatus(reviewStatusDAO.getReviewStatusDescriptionByCode("GOALS_SETTING_IN_PROGRESS"));
	}

	@Override
	public ReviewHeader getReviewHeaderById(int reviewHeaderId) {
		return reviewHeaderDAO.getReviewHeaderById(reviewHeaderId);
	}

	@Override
	public void merge(ReviewHeader reviewHeader) {
		reviewHeaderDAO.saveReviewHeader(reviewHeader);

	}

	

	
	@Override
	public  List<ReviewFormLinkVO> getReviewFormLinks(int reviewCycleId) {
		logger.debug("Get Review Form Index for all Employees");
		return reviewHeaderDAO.getReviewFormLinksForExcel(reviewCycleId);
	}

	

	@Override
	public AppraisalShareInfoVO getAppraisalShareInfo(int employeeId,
			int reviewHeaderId) {
		AppraisalShareInfoVO appraisalShareInfoVO=new AppraisalShareInfoVO();
		SharedAppraiser sharedAppraiser=reviewHeaderDAO.getActiveSharedAppraiser(reviewHeaderId);
		 if(sharedAppraiser != null && sharedAppraiser.isShared()){
			 appraisalShareInfoVO.setIsShared(true);
			if(sharedAppraiser.getAssignedByEmployee().getEmpId()==employeeId){//manager
				appraisalShareInfoVO.setIsSharedByYou(true);
				appraisalShareInfoVO.setSharedWithEmployee(sharedAppraiser.getAssignedToEmployee().getDisplayName());
				appraisalShareInfoVO.setSharedWithEmployeeId(sharedAppraiser.getAssignedToEmployee().getEmpId());
				appraisalShareInfoVO.setSharedByEmployeeId(sharedAppraiser.getAssignedByEmployee().getEmpId());
				appraisalShareInfoVO.setSharedByEmployee(sharedAppraiser.getAssignedByEmployee().getDisplayName());
			} else
			if(sharedAppraiser.getAssignedToEmployee().getEmpId()==employeeId){//shared
				appraisalShareInfoVO.setIsSharedWithYou(true);
				appraisalShareInfoVO.setSharedByEmployee(sharedAppraiser.getAssignedByEmployee().getDisplayName());
				appraisalShareInfoVO.setSharedByEmployeeId(sharedAppraiser.getAssignedByEmployee().getEmpId());
				appraisalShareInfoVO.setSharedWithEmployeeId(sharedAppraiser.getAssignedToEmployee().getEmpId());
				appraisalShareInfoVO.setSharedWithEmployee(sharedAppraiser.getAssignedToEmployee().getDisplayName());
			} else{//super user
				appraisalShareInfoVO.setSharedByEmployee(sharedAppraiser.getAssignedByEmployee().getDisplayName());
				appraisalShareInfoVO.setSharedByEmployeeId(sharedAppraiser.getAssignedByEmployee().getEmpId());
				appraisalShareInfoVO.setSharedWithEmployeeId(sharedAppraiser.getAssignedToEmployee().getEmpId());
				appraisalShareInfoVO.setSharedWithEmployee(sharedAppraiser.getAssignedToEmployee().getDisplayName());
			}			
			appraisalShareInfoVO.setReviewHeaderId(reviewHeaderId);
			
		}
		return appraisalShareInfoVO;
	}
	@Override
	public AppraisalShareInfoVO getAppraisalShareInfo(int employeeId , ReviewFormLinkVO reviewFormLinkVO){
		AppraisalShareInfoVO appraisalShareInfoVO=new AppraisalShareInfoVO();
		//SharedAppraiser sharedAppraiser=reviewHeaderDAO.getActiveSharedAppraiser(reviewFormLinkVO.getReviewHeaderId());
		 if(reviewFormLinkVO.getSharedAppraiserId() != null){
			 appraisalShareInfoVO.setIsShared(true);
			if(reviewFormLinkVO.getAssignedByEmployeeId()==employeeId){//manager
				appraisalShareInfoVO.setIsSharedByYou(true);
				appraisalShareInfoVO.setSharedWithEmployee(reviewFormLinkVO.getAssignedToEmployeeName());
				appraisalShareInfoVO.setSharedWithEmployeeId(reviewFormLinkVO.getAssignedToEmployeeId());
				appraisalShareInfoVO.setSharedByEmployeeId(reviewFormLinkVO.getAssignedByEmployeeId());
				appraisalShareInfoVO.setSharedByEmployee(reviewFormLinkVO.getAssignedByEmployeeName());
			} else
			if(reviewFormLinkVO.getAssignedToEmployeeId()==employeeId){//shared
				appraisalShareInfoVO.setIsSharedWithYou(true);
				appraisalShareInfoVO.setSharedByEmployee(reviewFormLinkVO.getAssignedByEmployeeName());
				appraisalShareInfoVO.setSharedByEmployeeId(reviewFormLinkVO.getAssignedByEmployeeId());
				appraisalShareInfoVO.setSharedWithEmployeeId(reviewFormLinkVO.getAssignedToEmployeeId());
				appraisalShareInfoVO.setSharedWithEmployee(reviewFormLinkVO.getAssignedToEmployeeName());
			} else{//super user
				appraisalShareInfoVO.setSharedByEmployee(reviewFormLinkVO.getAssignedByEmployeeName());
				appraisalShareInfoVO.setSharedByEmployeeId(reviewFormLinkVO.getAssignedByEmployeeId());
				appraisalShareInfoVO.setSharedWithEmployeeId(reviewFormLinkVO.getAssignedToEmployeeId());
				appraisalShareInfoVO.setSharedWithEmployee(reviewFormLinkVO.getAssignedToEmployeeName());
			}			
			appraisalShareInfoVO.setReviewHeaderId(reviewFormLinkVO.getReviewHeaderId());
			
		}
		return appraisalShareInfoVO;
		
	}

	
	public ReviewHeader getReviewHeader(int employeeId,
			int reviewCycleId) {
		return reviewHeaderDAO.getReviewHeaderByEmployeeIdAndReviewCycleId(employeeId, reviewCycleId);
	}

	@Override
	public List<ReviewActionLogVO> getAllInternalComments(int reviewHeaderId, String reviewFormRoleDescription) {
		return reviewHeaderDAO.getAllInternalComments(reviewHeaderId, reviewFormRoleDescription);
	}
	

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForEmp(int empId, int reviewCycleId) {
		return reviewHeaderDAO.getOtherAppraisalsForEmp(empId, reviewCycleId);
	}

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForMgr(int managerEmpId,
			int reviewCycleId, int empId) {
		return reviewHeaderDAO.getOtherAppraisalsForMgr(managerEmpId, reviewCycleId, empId);
	}

	@Override
	public List<OtherAppraisalsVO> getOtherAppraisalsForSuperUser(int reviewCycleId,
			int empId) {
		return reviewHeaderDAO.getOtherAppraisalsForSuperUser(reviewCycleId, empId);
	}
	
	@Override
	public List<ReviewFormLinkVO> getReviewFormIndexOfAll(int reviewCycleId,
			int employeeId, Integer displayLength, Integer pageDisplayLength,String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		logger.debug("Get Review Form Index for all Employees");
		return reviewHeaderDAO.getReviewFormIndexOfAll(reviewCycleId, employeeId,displayLength,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper);
	}

	@Override
	public List<ReviewFormLinkVO> getReviewFormIndex(int employeeId,
			Integer displayLength, Integer pageDisplayLength,
			String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper,boolean pending) {
		logger.debug("Get Review Form Index for EmployeeId: "+employeeId);
		return reviewHeaderDAO.getReviewFormIndex(employeeId,displayLength,pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper, pending);
	}

	@Override
	public List<ReviewFormLinkVO> getSharedReviewFormIndexByReviewCycleId(
			int employeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colName, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		return reviewHeaderDAO.getSharedReviewFormIndexByReviewCycleId(employeeId,reviewCycleId,displayLength,
				pageDisplayLength,searchValue,colName,sortDirection,reviewFormLinkVOWrapper);
	}

	@Override
	public List<ReviewFormLinkVO> getReviewFormIndexOfPeersByReviewCycleId(
			int mainAppraiserEmployeeId, int reviewCycleId, Integer displayLength,
			Integer pageDisplayLength, String searchValue, String colIndex, String sortDirection, DataTablesJSONWrapper reviewFormLinkVOWrapper) {
		logger.debug("Get Review Form Index of Peers");
		return reviewHeaderDAO.getReviewFormIndexOfPeersByReviewCycleId(mainAppraiserEmployeeId,reviewCycleId,displayLength,pageDisplayLength,searchValue,colIndex,sortDirection,reviewFormLinkVOWrapper);
	}



	@Override
	public List<Integer> getLeadEmpIds() {
		return reviewHeaderDAO.getLeadEmpIds();
	}



	
}
