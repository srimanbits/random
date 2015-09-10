/*
 * Cloud Foundry 2012.02.03 Beta
 * Copyright (c) [2009-2012] VMware, Inc. All Rights Reserved.
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product includes a number of subcomponents with
 * separate copyright notices and license terms. Your use of these
 * subcomponents is subject to the terms and conditions of the
 * subcomponent's license, as noted in the LICENSE file.
 */

package com.ggk.hrms.authentication;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;
import static org.springframework.util.StringUtils.arrayToCommaDelimitedString;
import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.ggk.hrms.review.beans.domain.Employee;
import com.ggk.hrms.review.service.EmployeeService;

/**
 * Copied from the original implementation of the
 * <code>DefaultUserAuthenticationConverter</code> to fix a bug in the
 * <code>getAuthorities</code> method. Rest all unchanged. Class with the
 * original bug
 * <code>org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter</code>
 */
public class DefaultUserAuthenticationConverter extends
		org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter {

	private Collection<? extends GrantedAuthority> defaultAuthorities;

	private AuthorityGranter authorityGranter;

	private InMemoryUserDetailsServiceImpl userDetailsService;

	private final Logger log = Logger.getLogger(DefaultUserAuthenticationConverter.class);

	private final static String EMAIL = "email";

	@Resource
	private EmployeeService employeeService;

	/**
	 * Default value for authorities if an Authentication is being created and
	 * the input has no data for authorities. Note that unless this property is
	 * set, the default Authentication created by
	 * {@link #extractAuthentication(java.util.Map)} will be unauthenticated.
	 *
	 * @param defaultAuthorities
	 *            the defaultAuthorities to set. Default null.
	 */
	public void setDefaultAuthorities(String[] defaultAuthorities) {
		this.defaultAuthorities = commaSeparatedStringToAuthorityList(arrayToCommaDelimitedString(defaultAuthorities));
	}

	/**
	 * Authority granter which can grant additional authority to the user based
	 * on custom rules.
	 *
	 * @param authorityGranter
	 */
	public void setAuthorityGranter(AuthorityGranter authorityGranter) {
		this.authorityGranter = authorityGranter;
	}

	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			UserDetailsImpl user = getUserPrincipal(map);
			return new UsernamePasswordAuthenticationToken(user, "N/A", getAuthorities(user));
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(UserDetailsImpl user) {
		return setCustomAuthorities(user);
	}

	private void grantAuthoritiesBasedOnValuesInMap(Map<String, ?> map, List<GrantedAuthority> authorityList) {
		List<GrantedAuthority> parsedAuthorities = parseAuthorities(map);
		authorityList.addAll(parsedAuthorities);
	}

	private void grantAdditionalAuthorities(Map<String, ?> map, List<GrantedAuthority> authorityList) {
		if (authorityGranter != null) {
			authorityList.addAll(authorityGranter.getAuthorities(map));
		}
	}

	private void assignDefaultAuthorities(List<GrantedAuthority> authorityList) {
		if (defaultAuthorities != null) {
			authorityList.addAll(defaultAuthorities);
		}
	}

	private List<GrantedAuthority> parseAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		List<GrantedAuthority> parsedAuthorities;
		if (authorities instanceof String) {
			// Bugfix for Spring OAuth codebase
			parsedAuthorities = commaSeparatedStringToAuthorityList((String) authorities);
		} else if (authorities instanceof Collection) {
			parsedAuthorities = commaSeparatedStringToAuthorityList(collectionToCommaDelimitedString((Collection<?>) authorities));
		} else {
			throw new IllegalArgumentException("Authorities must be either a String or a Collection");
		}
		return parsedAuthorities;
	}

	private UserDetailsImpl getUserPrincipal(Map<String, ?> map) {
		String email = (String) map.get(EMAIL);
		Employee employee = null;
		try {
			employee = employeeService.getEmployee(email);
		} catch (NoResultException nre) {
			log.error(email + " is not authorized user", nre);
			return null;
		} catch (Exception e) {
			log.error("Session timed out", e);
		}
		if(employee!=null) {
			UserDetailsImpl user = new UserDetailsImpl((String) map.get(USERNAME), email, employee.getDisplayName());
			user.setEmpId(employee.getEmpId());
			List<GrantedAuthority> authorities = employeeService.getRoles(employee.getEmpId());
			user.setAuthorities(authorities);
			user.setManagerEmployee(employee.getReportingToEmployee());
			if (employee.getGrade() == null) {
				user.setGrade("");
			} else {
				user.setGrade(" ( " + employee.getGrade().getGrade() + " )");
			}
			userDetailsService.addUser(user);
			return user;
		} else {
			return null;
		}
	}
	
	
	private List<GrantedAuthority> setCustomAuthorities(UserDetailsImpl user) {
		if(user!=null) {
			log.info("User " + user.getFullName() + " logged into the application");
			return (List<GrantedAuthority>) user.getAuthorities();
		} else {
			return new ArrayList<GrantedAuthority>();
		}
	}

	public void setUserDetailsService(InMemoryUserDetailsServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
