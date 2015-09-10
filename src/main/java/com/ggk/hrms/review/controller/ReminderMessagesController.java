package com.ggk.hrms.review.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ggk.hrms.review.beans.domain.AppraisalPeriodType;
import com.ggk.hrms.review.beans.domain.ReminderMessages;
import com.ggk.hrms.review.service.ReminderMessagesService;
import com.ggk.hrms.review.ui.vo.ReminderMessagesVO;
import com.ggk.hrms.review.utils.AppraisalPeriodTypeEnum;

@Controller
@RequestMapping(value = "/reminderMessages")
@SessionAttributes("reviewCycleId")
public class ReminderMessagesController {
	
	@Autowired
	ReminderMessagesService reminderMessagesService;
	
	private  final Logger log = Logger.getLogger(ReminderMessagesController.class);
	
	@RequestMapping(value = "/getStages.html", method = RequestMethod.GET)
	public String getStages(ModelMap model, @RequestParam("reviewPeriod") String reviewPeriod){
		//System.out.println("Review Period: "+reviewPeriod);
		if(reviewPeriod == null){
			model.addAttribute("reviewCycleId", model.get("reviewCycleId"));
		}
		else{
			model.addAttribute("reviewCycleId", reviewPeriod);
		}
		return "super-user/populateReminderMessages";
	}
	
	@RequestMapping(value="/getReminderMessages.html", method = RequestMethod.POST)
	public @ResponseBody List<ReminderMessagesVO> getReminderMessages(@RequestParam("reviewCycleStage") String reviewCycleStage, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model){
		List<ReminderMessagesVO> reminderMessagesVO = null;
		
		if(!reviewCycleStage.isEmpty() && reviewCycleStage != null){
			reminderMessagesVO=reminderMessagesService.getReminderMessagesForHalfYearly(reviewPeriod , reviewCycleStage);
		}
		model.addAttribute("reminderMessages", reminderMessagesVO);
		return reminderMessagesVO;
		
	}
	

	@RequestMapping(value="/update.html", method = RequestMethod.GET)
	public String update(@RequestParam("id") int messageId, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model){
		ReminderMessagesVO reminderMessageVO = reminderMessagesService.getReminderMessageDetailsById(messageId);
		model.addAttribute("reminderMessage",reminderMessageVO);
		model.addAttribute("addNew", false);
		return "super-user/editOrAddReminderMessages";
	}
	
	
	
	@RequestMapping(value="/addReminderMessage.html",method = RequestMethod.GET)
	public String addReminderMessages(@RequestParam("reviewCycleStage") String reviewCycleStage, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model)
	{
		model.addAttribute("addNew", true);
		model.addAttribute("reviewPeriod", reviewPeriod);
		model.addAttribute("reviewCycleStage", reviewCycleStage);
		return "super-user/editOrAddReminderMessages";
		
	}
	
	@RequestMapping(value="/saveReminderMessage.html",method = RequestMethod.GET)
	public ModelAndView saveReminderMessages(@RequestParam("daysRemaining") int daysRemaining, @RequestParam("reminderMessage") String reminderMessage, @RequestParam("subject") String subject, @RequestParam("reviewPeriod") String reviewPeriod, @RequestParam("status") String status, ModelMap model)
	{
		if (!reminderMessagesService.isReminderMessageAlreadyPresent(daysRemaining, status, reviewPeriod)) {
			ReminderMessages reminderMessages=new ReminderMessages();
			reminderMessages.setActive(true);
			reminderMessages.setDaysRemaining(daysRemaining);
			reminderMessages.setMessage(reminderMessage);
			int appraisalPeriodTypeId = 0;
			for (AppraisalPeriodTypeEnum appraisalPeriodEnum : AppraisalPeriodTypeEnum.values()) {
				if (appraisalPeriodEnum.getName().equals(reviewPeriod)) {
					appraisalPeriodTypeId = appraisalPeriodEnum.getId();
				}
			}
			AppraisalPeriodType appraisalPeriodType = new AppraisalPeriodType();
			appraisalPeriodType.setAppraisalPeriodTypeId(appraisalPeriodTypeId);
			reminderMessages.setAppraisalPeriodType(appraisalPeriodType);
			reminderMessages.setStatus(status);
			reminderMessages.setSubject(subject);
		 
			reminderMessages.setLastUpdatedDate(new Date());
			
			reminderMessagesService.insertReminderMessage(reminderMessages);
			
			model.addAttribute("addNew", true);
			model.addAttribute("reviewPeriod", reviewPeriod);
			
			return new ModelAndView("redirect:/reminderMessages/getStages.html");
		}
		else {
			return new ModelAndView("redirect:/reminderMessages/error.html");
		}
	}
	
	@RequestMapping(value="/updateReminderMessage.html", method = RequestMethod.POST)
	public ModelAndView updateReminderMessage(@RequestParam("reviewCycleStage") String reviewCycleStage, @RequestParam("messageId") int messageId, @RequestParam("daysRemaining") int daysRemaining, @RequestParam("reminderMessage") String reminderMessage, @RequestParam("subject") String subject, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model, @ModelAttribute("reviewCycleId") String reviewCycleId){
			reminderMessagesService.updateReminderMessage(messageId, daysRemaining, reminderMessage, subject);
			model.addAttribute("reviewCycleId", reviewPeriod);
			List<ReminderMessagesVO> reminderMessagesVO = reminderMessagesService.getReminderMessagesForHalfYearly(reviewPeriod, reviewCycleStage);
			model.addAttribute("reminderMessages", reminderMessagesVO);
			model.addAttribute("reviewCycleStage", reviewCycleStage);
			log.info("number of records"+reminderMessagesVO.size());
			return new ModelAndView("super-user/populateReminderMessages");
	}
	
	@RequestMapping(value="/deleteReminderMessage.html", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") int messageId, @RequestParam("reviewCycleStage") String reviewCycleStage, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model){
		reminderMessagesService.deleteReminderMessageById(messageId);
		model.addAttribute("reviewCycleId", reviewPeriod);
		model.addAttribute("reviewCycleStage", reviewCycleStage);
		List<ReminderMessagesVO> reminderMessagesVO=reminderMessagesService.getReminderMessagesForHalfYearly(reviewPeriod, reviewCycleStage);
		model.addAttribute("reminderMessages", reminderMessagesVO);
		model.addAttribute("reviewCycleStage", reviewCycleStage);
		log.info("number of records"+reminderMessagesVO.size());
		return new ModelAndView("super-user/populateReminderMessages");
	}
	
	@RequestMapping(value="/backToReviewCycleDisplay.html", method = RequestMethod.GET)
	public ModelAndView backToReviewCycleDisplay(@RequestParam("reviewCycleStage") String reviewCycleStage, @RequestParam("reviewPeriod") String reviewPeriod, ModelMap model){
		List<ReminderMessagesVO> reminderMessagesVO=reminderMessagesService.getReminderMessagesForHalfYearly(reviewPeriod , reviewCycleStage);
		model.addAttribute("reminderMessages", reminderMessagesVO);
		model.addAttribute("reviewCycleStage", reviewCycleStage);
		log.info("number of records"+reminderMessagesVO.size());
		return new ModelAndView("super-user/populateReminderMessages");
		
	}
	
	@RequestMapping(value="/error.html", method = RequestMethod.GET)
	public @ResponseBody
	String error(){
		return "error";
	}
}
