package com.ggk.hrms.review.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.ReviewAuditService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.ui.vo.ReviewActionLogVO;
import com.ggk.hrms.review.ui.vo.ReviewFormInfoVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller
@RequestMapping(value = "/appraisal")
public class ReviewActionLogController {
	
	@Autowired
	private ReviewHeaderService reviewHeaderService;
	
	@Autowired
	private ReviewFormService reviewFormService;
	
	@Autowired
	private ReviewAuditService reviewAuditService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.POST,value="/actionLog.html")
	public String getReviewActionLog(@ModelAttribute("reviewFormInfoVO") ReviewFormInfoVO reviewFormInfoVO,ModelMap model){
		//List<ReviewActionLogVO> reviewActionLogs=reviewAuditService.getReviewActionLog(reviewFormInfoVO.getReviewHeaderId(),reviewFormInfoVO.getReviewFormRole().getDescription());
		
		//model.put("reviewActionLogs", reviewActionLogs);				
		return "appraisalForm/log/reviewLog";
		
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/actionLogJsonData.html")
	public @ResponseBody DataTablesJSONWrapper getReviewActionLog(HttpServletRequest  request,@RequestParam(value = "reviewHeaderId") int reviewHeaderId,@RequestParam(value = "roleDescription") String roleDescription
			,@RequestParam(value = "iDisplayStart") int startIndex,@RequestParam(value = "iDisplayLength") int pageDisplayLength,@RequestParam(value = "sSearch",required=false) String searchValue
			){
		DataTablesJSONWrapper reviewFormLinkVOWrapper = new DataTablesJSONWrapper();
		
		reviewFormLinkVOWrapper.setsEcho(request.getParameter("sEcho"));
		
		//Fetch sorting column
    	String sortDirection="asc";
    	String colIndex = request.getParameter("iSortCol_0");
    	sortDirection = request.getParameter("sSortDir_0");
		
		List<ReviewActionLogVO> reviewActionLogs=reviewAuditService.getReviewActionLog(reviewHeaderId,roleDescription,startIndex,pageDisplayLength,searchValue,sortDirection,colIndex,reviewFormLinkVOWrapper);
		
		setTableData(reviewActionLogs);
		
		reviewFormLinkVOWrapper.setAaData(reviewActionLogs);	
		
		return reviewFormLinkVOWrapper;
	}
	
	
	private void setTableData(List<ReviewActionLogVO> reviewActionLogs) {
		for (ReviewActionLogVO reviewActionLogVO : reviewActionLogs) {
			
			String notesInfo="<div class='close'><span>Added To/Edited "+reviewActionLogVO.getReviewFieldGroupType()+" Section</span>"
			+"<input type=\"hidden\" value=\""+reviewActionLogVO.getCommentText()+"\"> </div>";
			reviewActionLogVO.setNotesInfo(notesInfo);
		}
	}

	@ModelAttribute("reviewFormInfoVO")
	 ReviewFormInfoVO createReviewFormInfoVO(
			@RequestParam("reviewHeaderId") int reviewHeaderId) {
		return reviewFormService.getReviewFromInfoVO(reviewHeaderId, SecurityDetailsAccessor.getEmpId(),null);
	}

}
