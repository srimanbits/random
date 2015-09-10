package com.ggk.hrms.review.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.DataTablesJSONWrapper;
import com.ggk.hrms.review.service.FeedbackRequestService;
import com.ggk.hrms.review.ui.vo.FeedbackRequestVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

@Controller("userController")
@RolesAllowed(value = { "ROLE_USER", "ROLE_MANAGER", "ROLE_SUPERUSER" })
public class UserController {

	@Autowired
	private FeedbackRequestService feedbackRequestService;
	
	
	@Resource
	private String scrollingNotificationMsg;

	private final Logger log = Logger.getLogger(UserController.class);

	@RequestMapping(value = "/welcome.html", method = RequestMethod.GET)
	public String welcome(HttpSession session) {
		log.info("User controller");
		setAppraiserChangeLogsForManager(session);
		return "user/welcome";
	}

	@RequestMapping(value = "/review/appraisal.html", method = RequestMethod.GET)
	public String getrefresh(HttpSession session) {
		return welcome(session);
	}

	@RequestMapping(value = "/appraiserChangeLogsForManager.html", method = RequestMethod.GET)
	public @ResponseBody DataTablesJSONWrapper setAppraiserChangeLogsForManager(
			HttpServletRequest request) {

		DataTablesJSONWrapper reviewFormLinkVOWrapper = new DataTablesJSONWrapper();

		reviewFormLinkVOWrapper.setsEcho(request.getParameter("sEcho"));
		
		String contextPath=request.getContextPath();

		int loggedInEmployeeId = SecurityDetailsAccessor.getEmpId();
		
		Integer startIndex = 0;
    	if (null != request.getParameter("iDisplayStart"))
    		startIndex = Integer.valueOf(request.getParameter("iDisplayStart"));		
    	
    	//Fetch Page display length
    	Integer pageDisplayLength = Integer.valueOf(request.getParameter("iDisplayLength"));
    	
		List<FeedbackRequestVO> feedbackRequestVOs = feedbackRequestService.getFeedbackRequestsOnProjectChange(loggedInEmployeeId,startIndex,pageDisplayLength,reviewFormLinkVOWrapper);

		setTableData(feedbackRequestVOs,contextPath);

		reviewFormLinkVOWrapper.setAaData(feedbackRequestVOs);
		return reviewFormLinkVOWrapper;
	}
	
	private void setTableData(List<FeedbackRequestVO> feedbackRequestVOs, String contextPath) {
		for (FeedbackRequestVO feedbackRequestVO : feedbackRequestVOs) {
			
			String actionInfo="<a onclick=\" pendingByRequestId('"+feedbackRequestVO.getFeedbackRequestId()+"', '"+feedbackRequestVO.getType()+"','"+contextPath+"/')\" href=\"javascript:void(0)\"> Pending </a>";
			feedbackRequestVO.setActionField(actionInfo);
		}
	}
	
	private void setAppraiserChangeLogsForManager(HttpSession session) {

		Long count = feedbackRequestService
				.getPendingFeedbackRequests(SecurityDetailsAccessor.getEmpId());
		if (count <= 0) {
			session.removeAttribute("scrollingNotificationMsg");
			return;
		}

		session.setAttribute("scrollingNotificationMsg",
				scrollingNotificationMsg);
	}
}
