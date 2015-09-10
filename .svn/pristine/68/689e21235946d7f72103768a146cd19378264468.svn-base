package com.ggk.hrms.review.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ggk.hrms.review.beans.domain.ReviewCycle;
import com.ggk.hrms.review.service.ReviewCycleService;


@Controller
@RequestMapping(value = "/serverDetails")
public class ServerDetailsController {

	private  final Logger log = Logger.getLogger(ServerDetailsController.class);
	
	@Autowired
	ReviewCycleService reviewCycleService;
	
 @RequestMapping(value = "/editServerDetails.html", method = RequestMethod.GET)
 public String showReveiwHeader(ModelMap model){
	 
  File propsFile = new File(System.getProperty("user.dir")+"/conf/hrms-properties.properties");
  Properties prop = new Properties();
  try {
	prop.load(new FileInputStream(propsFile));
	model.put("url", prop.getProperty("datasource.hrms.jdbc.url"));
	model.put("username", prop.getProperty("datasource.hrms.jdbc.username"));
	model.put("password", prop.getProperty("datasource.hrms.jdbc.password"));
	
} catch (Exception e) {
	e.printStackTrace();
}
  return "super-user/editServerDetails";
 }
 
 
 
 @RequestMapping(value="/save.html")
 public String saveServerDetails(@RequestParam("url")String url,@RequestParam("username")String username,@RequestParam("password")String password) throws FileNotFoundException, IOException
 {
	 File propsFile = new File(System.getProperty("user.dir")+"/conf/hrms-properties.properties");
	   Properties prop = new Properties();
	   prop.load(new FileInputStream(propsFile));
	   // make changes
	   
	   // set the properties value
	   if(!((url==null) ||(url.equals(""))))
	   prop.setProperty("datasource.hrms.jdbc.url",url);
	  if(!((username==null) ||(username.equals(""))))
	   prop.setProperty("datasource.hrms.jdbc.username", username);
	 if(!((password==null) ||(password.equals(""))))
	   prop.setProperty("datasource.hrms.jdbc.password", password);
	   prop.save(new FileOutputStream(propsFile), "");
	   
	return "super-user/editServerDetails";
	 
 }
 
 
}