package com.ggk.hrms.review.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.beans.domain.ONOMinutes;
import com.ggk.hrms.review.beans.domain.ReviewHeader;
import com.ggk.hrms.review.constants.ReviewFormRole;
import com.ggk.hrms.review.dao.ONOMinutesDao;
import com.ggk.hrms.review.service.EmployeeService;
import com.ggk.hrms.review.service.MailingService;
import com.ggk.hrms.review.service.ReviewFormService;
import com.ggk.hrms.review.service.ReviewHeaderService;
import com.ggk.hrms.review.task.ONOEmailNotificationTask;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;

/**
 * @author Swaroops
 * 
 *         Controller to handle OnetoOne meeting requests from UI.
 *
 */
@Controller
public class ONOMeetingsController {

	public static final String SUCCESS = "Success";
	public static final String APPROVED = "Approved";
	public static final String REJECTED = "Rejected";
	public static final String ERROR = "Error";
	public static final String SHOW = "ONO/show";
	public static final String ROWS = "ONO/rows";

	@Autowired
	private ONOMinutesDao onoMinutesDao;

	@Autowired
	private ReviewFormService reviewFormService;

	@Autowired
	private ReviewHeaderService reviewHeaderService;

	@Autowired
	private EmployeeService employeeService;

	@Resource(name = "onoInterval")
	private Long interval;

	@Resource(name = "mailingService")
	MailingService mailingService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

	@RequestMapping("/ONOMinutes/show.html")
	private String show(@RequestParam("reviewHeaderId") int reviewHeaderId,
			ModelMap modelMap, HttpServletRequest request) {

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		// Get Role using reviewHeader. If role is null, throw exception.
		ReviewFormRole reviewFormRole = reviewFormService
				.getReviewFormRole(reviewHeader);
		if (reviewFormRole == null
				|| reviewFormRole == ReviewFormRole.SHARED_APPRAISER) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}
		Long totalNoOfMinutes = onoMinutesDao.count(reviewHeader.getEmployee()
				.getEmpId());

		List<ONOMinutes> oNOMinutes = onoMinutesDao.getONOMinutes(reviewHeader
				.getEmployee().getEmpId(), 0L);
		modelMap.addAttribute("ONOMinutes", oNOMinutes);
		modelMap.addAttribute("reviewFormRole", reviewFormRole);
		modelMap.addAttribute("reviewHeaderId", reviewHeaderId);
		// Set modal attributes for add or edit minutes
		if (oNOMinutes != null & oNOMinutes.size() > 0) {
			ONOMinutes latestOnoMinutes = oNOMinutes.get(0);
			Calendar latestCreatedDate = Calendar.getInstance();
			Calendar currentDate = Calendar.getInstance();
			latestCreatedDate.setTime(latestOnoMinutes.getCreatedDate());
			if (latestCreatedDate.get(Calendar.MONTH) == currentDate
					.get(Calendar.MONTH)) {
				// If one to one happened in current month and is approved. Do
				// nothing. i.e show no buttons in UI.
				if (latestOnoMinutes.getIsApproved() == true) {

					// Do nothing.
				} else {
					if (reviewFormRole == ReviewFormRole.APPRAISE) {
						modelMap.addAttribute("edit", true);
					}
					if (reviewFormRole == ReviewFormRole.APPRAISER
							|| reviewFormRole == ReviewFormRole.SUPERUSER) {

						modelMap.addAttribute("approve", true);
					}
				}

			} else if (reviewFormRole == ReviewFormRole.APPRAISE) {
				// If one on one not happened in current month, display buttons
				// to add minutes.

				modelMap.addAttribute("add", true);
			}

		} else if (reviewFormRole == ReviewFormRole.APPRAISE) {

			modelMap.addAttribute("add", true);

		} else {
			modelMap.addAttribute("appraisalMsg",
					"There are no One on One notes for this Employee");
		}

		Collections.reverse(oNOMinutes);

		

		request.getSession().setAttribute("ONOCurrent", interval);
		request.getSession().setAttribute("totalNoOfMinutes", totalNoOfMinutes);

		return SHOW;
	}

	@RequestMapping(value = "/ONOMinutes/save.html", method = RequestMethod.POST)
	private @ResponseBody String save(
			@RequestParam("reviewHeaderId") int reviewHeaderId,
			@RequestParam("meetingMinutes") String meetingMinutes,
			@RequestParam("minutesId") int minutesId,
			@RequestParam("commentsHistory") String commentsHistory,
			ModelMap modelMap) {
		String meetingMinutesCopy = meetingMinutes;
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		// Get Role using reviewHeader. If role is null, throw exception.
		ReviewFormRole reviewFormRole = reviewFormService
				.getReviewFormRole(reviewHeader);
		if (reviewFormRole == null) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}

		Employee loggedInEmp = employeeService
				.getEmployeeById(SecurityDetailsAccessor.getEmpId());
		Date currentDate = new Date();
		
		if (minutesId <= 0) {
			ONOMinutes minutes = new ONOMinutes();
			
			meetingMinutes = "<b>"
					+ reviewHeader.getEmployee().getDisplayName() + "  ("
					+ dateFormat.format(currentDate) + "):</b>\n"
					+ meetingMinutes;
			
			minutes.setMeetingMinutes(meetingMinutes);
			minutes.setApparise(reviewHeader.getEmployee());
			minutes.setAppraiser(reviewHeader.getMainAppraiserEmployee());
			minutes.setCreatedEmployee(reviewHeader.getEmployee());
			minutes.setLastUpdateEmployee(reviewHeader.getEmployee());

			minutes.setCreatedDate(currentDate);
			minutes.setLastModifiedDate(currentDate);
			onoMinutesDao.save(minutes);
			new Thread(new ONOEmailNotificationTask(mailingService,"ONO_Started",
					reviewHeader.getEmployee(), reviewHeader.getMainAppraiserEmployee(),
					meetingMinutesCopy, minutes.getIsApproved())).start();
		} else {

			ONOMinutes minutes = onoMinutesDao.find(minutesId);
			if (minutes == null) {
				return ERROR;
			}
			
			meetingMinutes =minutes.getMeetingMinutes()+ "\n\n" +"<b>"
					+ reviewHeader.getEmployee().getDisplayName() + "  ("
					+ dateFormat.format(currentDate) + "):</b>\n"
					+ meetingMinutes;
			
			minutes.setMeetingMinutes(meetingMinutes);
			minutes.setLastUpdateEmployee(loggedInEmp);
			currentDate = new Date();
			minutes.setLastModifiedDate(currentDate);
			onoMinutesDao.save(minutes);
			new Thread(new ONOEmailNotificationTask(mailingService,"ONO_updated",
					reviewHeader.getEmployee(), reviewHeader.getMainAppraiserEmployee(),
					meetingMinutesCopy, minutes.getIsApproved())).start();
		}

		return SUCCESS;
	}

	@RequestMapping("/ONOMinutes/loadPrevious.html")
	private String loadPrevious(
			@RequestParam("reviewHeaderId") int reviewHeaderId,
			ModelMap modelMap, HttpServletRequest request) {
		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		ReviewFormRole reviewFormRole = reviewFormService
				.getReviewFormRole(reviewHeader);
		if (reviewFormRole == null
				|| reviewFormRole == ReviewFormRole.SHARED_APPRAISER) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}
		Long current = (Long) request.getSession().getAttribute("ONOCurrent");
		List<ONOMinutes> oNOMinutes = onoMinutesDao.getONOMinutes(reviewHeader
				.getEmployee().getEmpId(), current);

		Collections.reverse(oNOMinutes);
		List<String> ONOMonthsList = new ArrayList<String>();

		for (ONOMinutes onoMinutes : oNOMinutes) {
			Calendar createdDate = Calendar.getInstance();
			createdDate.setTime(onoMinutes.getCreatedDate());
			ONOMonthsList.add(new SimpleDateFormat("MMMM").format(createdDate
					.getTime()));
		}

		modelMap.addAttribute("ONOMonthsList", ONOMonthsList);

		current = current + interval;
		request.getSession().setAttribute("ONOCurrent", current);
		modelMap.addAttribute("ONOMinutes", oNOMinutes);
		modelMap.addAttribute("reviewFormRole", reviewFormRole);
		modelMap.addAttribute("reviewHeaderId", reviewHeaderId);

		return ROWS;
	}

	@RequestMapping("/ONOMinutes/approve.html")
	public @ResponseBody String approve(
			@RequestParam("minutesId") int minutesId,
			@RequestParam("reviewHeaderId") int reviewHeaderId,
			@RequestParam("comments") String appraiserComments) {

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		ReviewFormRole reviewFormRole = reviewFormService
				.getReviewFormRole(reviewHeader);
		if (reviewFormRole == null || reviewFormRole == ReviewFormRole.APPRAISE || reviewFormRole == ReviewFormRole.SHARED_APPRAISER) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}
		ONOMinutes minutes = onoMinutesDao.find(minutesId);
		if (minutes == null) {
			return ERROR;
		}
		Date currentDate = new Date();
		int employeeId = SecurityDetailsAccessor.getEmpId();
		Employee employee = employeeService.getEmployeeById(employeeId);

		/*
		 * String meetingMinutes = employee.getDisplayName() + "  (" +
		 * dateFormat .format( currentDate) + "):\n" + appraiserComments +
		 * "\n\n" + minutes.getMeetingMinutes();
		 */
		String meetingMinutes =minutes.getMeetingMinutes()+ "\n\n" +"<b>"
				+ employee.getDisplayName() + "  ("
				+ dateFormat.format(currentDate) + "):</b>\n"
				+ appraiserComments;
		minutes.setIsApproved(true);
		minutes.setMeetingMinutes(meetingMinutes);
		minutes.setLastUpdateEmployee(employee);
		minutes.setLastModifiedDate(currentDate);
		onoMinutesDao.save(minutes);
		new Thread(new ONOEmailNotificationTask(mailingService,"message_to_appraise",
				reviewHeader.getEmployee(), employee,
				appraiserComments, minutes.getIsApproved())).start();
		return APPROVED;

	}

	@RequestMapping("/ONOMinutes/reject.html")
	public @ResponseBody String reject(
			@RequestParam("minutesId") int minutesId,
			@RequestParam("reviewHeaderId") int reviewHeaderId,
			@RequestParam("comments") String appraiserComments) {

		ReviewHeader reviewHeader = reviewHeaderService
				.getReviewHeaderById(reviewHeaderId);
		ReviewFormRole reviewFormRole = reviewFormService
				.getReviewFormRole(reviewHeader);
		if (reviewFormRole == null || reviewFormRole == ReviewFormRole.APPRAISE) {
			throw new RuntimeException("Invalid ReviewFormRole");
		}
		ONOMinutes minutes = onoMinutesDao.find(minutesId);
		if (minutes == null) {
			return ERROR;
		}
		Date currentDate = new Date();
		int employeeId = SecurityDetailsAccessor.getEmpId();
		Employee employee = employeeService.getEmployeeById(employeeId);

		/*
		 * String meetingMinutes = employee.getDisplayName() + "  (" +
		 * dateFormat .format( currentDate) + "):\n" + appraiserComments +
		 * "\n\n" + minutes.getMeetingMinutes();
		 */
		String meetingMinutes =minutes.getMeetingMinutes()+ "\n\n" +"<b>"
				+ employee.getDisplayName() + "  ("
				+ dateFormat.format(currentDate) + "):</b>\n"
				+ appraiserComments;
		minutes.setIsApproved(false);
		minutes.setMeetingMinutes(meetingMinutes);

		minutes.setLastUpdateEmployee(employee);
		minutes.setLastModifiedDate(currentDate);
		onoMinutesDao.save(minutes);

		new Thread(new ONOEmailNotificationTask(mailingService,"message_to_appraise",
				reviewHeader.getEmployee(), employee,
				appraiserComments, minutes.getIsApproved())).start();
		return REJECTED;

	}

}
