package com.ggk.hrms.review.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller(value = "authenticationContoller")
@RequestMapping("/authentication")
public class AuthenticationController {

	private static final String login = "authentication/login";
	private static final String denied = "authentication/denied";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) boolean error,	ModelMap model) {
		if (error == true) {
			model.put("error","You have entered an invalid username or password!");
		} else {
			model.put("error", "");
		}
		return login;
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public String denied() {
		return denied;
	}
	
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String mail(HttpServletRequest request,HttpServletResponse response) {
		request.getSession().invalidate();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      if (auth != null){    
	         new SecurityContextLogoutHandler().logout(request, response, auth);
	      }
	    SecurityContextHolder.getContext().setAuthentication(null);
		
		return "authentication/mail";
	}

}
