package com.ggk.hrms.review.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionHandlerController implements HandlerExceptionResolver {

	private  final Logger log = Logger.getLogger(UserController.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		if(exception instanceof RuntimeException) {
			
			log.info(exception);
			
			if(exception instanceof NullPointerException) {

				log.error("Some value is Null", exception);
		        return new ModelAndView("error/unAvailable");
			} else if (!exception.getMessage().isEmpty() && exception.getMessage().equals("Invalid ReviewFormRole")) {
					        	
	        	log.error("You are not authorized",exception);
	        	model.put("errorMessage", "You are not authorized");
	        	return new ModelAndView("error/forBidden");
	        } else if(!exception.getMessage().isEmpty() && exception.getMessage().equals("You are not authorized")) {
	        	
	        	throw new RuntimeException();
	        } else if(!exception.getMessage().isEmpty() && exception.getMessage().equals("Competencies will be activated in second phase of Review Cycle. Thank you")) {
	        	
	        	log.error("Competencies will be activated in second phase of Review Cycle. Thank you", exception);
	        	model.put("errorMessage", exception.getMessage());
	            return new ModelAndView("error/errorReviewCompetency", model);
	        } else if (exception instanceof AccessDeniedException){
	        	log.error("AccessDeniedException",exception);
	        	return new ModelAndView("error/invalidUser", model);
	        } else if(!exception.getMessage().isEmpty() && exception.getMessage().equals("Loggedout")){
	        	
	        	log.error("Redirecting");
	        	return new ModelAndView("authentication/loginRedirect", model);
	        } else if(!exception.getMessage().isEmpty() && exception.getMessage().equals("Invalid Operation")){
	        	
	        	log.error("User made an Invalid operation.", exception);
	        	return new ModelAndView("authentication/loginRedirect", model);
	        } else if(!exception.getMessage().isEmpty() && exception.getMessage().equals("Invalid User")){
		        	
		        log.error("Session expiered, refreshing");
		        return new ModelAndView("authentication/loginRedirect", model);
	        } else {
	        	
	        	log.error(exception.getMessage(), exception);
	            model.put("errorMessage", "Unexpected Runtime error: " + exception.getMessage());
	            return new ModelAndView("error/errorPage", model);
	        }
		} else {
			log.error(exception.getMessage(),exception);
            model.put("errorMessage", "Unexpected error: " + exception.getMessage());
            return new ModelAndView("error/errorPage", model);
		}
		
      
	}
}