package com.ggk.hrms.review.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ggk.hrms.authentication.UserDetailsImpl;

public class SecurityDetailsAccessor {
	
	public static String getUserName(){
		return getUserDetails().getUsername();
	}
	
	public static String getEmailAddress(){
		return getUserDetails().getEmail();
	}
	
	public static int getEmpId(){
		return getUserDetails().getEmpId();
	}
	
	public static String getFullName() {
		return getUserDetails().getFullName();
	}
	
	public static List<String> getAuthorities(){
		Collection<GrantedAuthority> grantedAuthorities=getUserDetails().getAuthorities();
		List<String> authorities=new ArrayList<String>();
		for(GrantedAuthority grantedAuthority: grantedAuthorities){			
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities;
	}
	
	private static UserDetailsImpl getUserDetails(){
		try{
		UserDetailsImpl userDetails=(UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;}
		catch (Exception e) {
			throw new RuntimeException("Loggedout");
		}
	}
	
}
