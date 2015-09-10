package com.ggk.hrms.review.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.service.ReviewCycleService;
import com.ggk.hrms.review.ui.vo.ReviewCycleVO;
import com.ggk.hrms.review.utils.SecurityDetailsAccessor;
import com.ggk.hrms.review.utils.UtilDateEditor;


@Controller
@RequestMapping(value="/reviewCycle")
public class ReviewCycleController {
	
	private  final Logger log = Logger.getLogger(ServerDetailsController.class);
	
	@Autowired
	ReviewCycleService reviewCycleService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new UtilDateEditor(false));
	}
	//sriman code
	 
	 @RequestMapping(value ="/reviewCycleDisplay.html" , method = RequestMethod.GET)
	 public String getList(ModelMap model) {
		 List<ReviewCycle> reviewcycleList = reviewCycleService.getList();
		 log.info("number of review cycles:"+reviewcycleList.size());
		 model.addAttribute("reviewcycleList", reviewcycleList);
		 return "super-user/reviewCycleDisplay";
	 }

	 @RequestMapping(value = "/delete.html" ,method = RequestMethod.GET)
	 public String deleteReviewCycle(@ModelAttribute("id") Integer id, ModelMap model) {
		 int employeesDeleted=0;
		 employeesDeleted=reviewCycleService.deleteRow(id);
		 List<ReviewCycle> reviewcycleList = reviewCycleService.getList();
		 log.info("number of review cycles:"+reviewcycleList.size());
		 model.addAttribute("reviewcycleList", reviewcycleList);
		  
		 if(employeesDeleted==-1) {
			 model.addAttribute("isDeleted", "");
			 log.info("can be deleted");
		 }
		 else {
			 model.addAttribute("isDeleted", true);
			 log.info("cant be deleted");
		 }
		  
		 return "super-user/reviewCycleDisplay";
	 }
	 
	 
	 @RequestMapping(value = "/edit.html" , method = RequestMethod.GET)
	 public ModelAndView editReviewCycle(@ModelAttribute("id") Integer id, ModelMap model) {
		 ReviewCycleVO reviewCycleVO = reviewCycleService.getRowById(id);
		 log.info(reviewCycleVO.getGoalsSettingDueDate());
		 model.addAttribute("update", true);
		 model.addAttribute("displayReviewCycle", false);
		 return new ModelAndView("super-user/addReveiwCycle", "reviewcycle", reviewCycleVO);
	 }

	 
	 @RequestMapping(value = "/update.html" , method = RequestMethod.GET) 
	 public ModelAndView updateReviewCycle(@ModelAttribute("reviewCycleForm") ReviewCycleVO reviewCycleVO, ModelMap map) throws NumberFormatException , IllegalArgumentException, ParseException{
	  
	     reviewCycleVO.setCreatedDate(new Date());
	     reviewCycleVO.setLastModifiedDate(new Date());
	     reviewCycleService.updateRow(reviewCycleVO);

	     return new ModelAndView("forward:/reviewCycle/reviewCycleDisplay.html");
	}
	 
	 @RequestMapping(value = "/add.html" , method = RequestMethod.GET)
	 public String addReviewCycle(ModelMap model) {
		 log.info("add");
		 model.addAttribute("add", true);
		 model.addAttribute("displayReviewCycle", false);
		 return "super-user/addReveiwCycle";
	 } 
	 
	 @RequestMapping(value = "/save.html" , method = RequestMethod.GET) 
	 public ModelAndView insertReviewCycle(@ModelAttribute("reviewCycleForm") ReviewCycleVO reviewCycleVO, ModelMap model) throws NumberFormatException , IllegalArgumentException, ParseException{
		 
	     reviewCycleVO.setCreatedDate(new Date());
	     reviewCycleVO.setLastModifiedDate(new Date());
		 reviewCycleService.insertRow(reviewCycleVO);

		 return new ModelAndView("forward:/reviewCycle/reviewCycleDisplay.html");	 
	 }
	 
	 
	 @RequestMapping(value = "/displayReviewCycleDetails.html" , method = RequestMethod.GET)
	public ModelAndView displayReviewCycleDetails(@ModelAttribute("id") Integer id,
			ModelMap model) {
		ReviewCycleVO reviewCycleVO = reviewCycleService.getRowById(id);
		model.addAttribute("displayReviewCycle", true);
		return new ModelAndView("super-user/addReveiwCycle", "reviewcycle",
				reviewCycleVO);
	}
	 
	@RequestMapping(value = "/startReviewCycle.html", method = RequestMethod.GET)
	public ModelAndView startReviewCycle(ModelMap model, @ModelAttribute("reviewCycleId") Integer reviewCycleId, @ModelAttribute("appraisalPeriodTypeId") Integer appraisalPeriodTypeId) {
		reviewCycleService.startReviewCycle(SecurityDetailsAccessor.getEmpId(), reviewCycleId, appraisalPeriodTypeId);
		return new ModelAndView("forward:/reviewCycle/reviewCycleDisplay.html");
	}
	
	@RequestMapping(value = "/getReviewCycleId.html", method = RequestMethod.POST)
	public @ResponseBody int getReviewCycleId(Model model,
			@RequestParam("reviewCycleName") String reviewCycleName) {
		int reviewCycleId = reviewCycleService.getReviewCycleIdByName(reviewCycleName);
		return reviewCycleId;
	}

}
