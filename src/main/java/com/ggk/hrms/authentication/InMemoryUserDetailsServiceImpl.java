package com.ggk.hrms.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InMemoryUserDetailsServiceImpl implements UserDetailsService {
	Map<String, UserDetails> userDetailsMap = new HashMap<String, UserDetails>();
	
	/** Since we are using only OpenID authentication - username is openId account
	 * 
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		if (!userDetailsMap.containsKey(username)) {
			throw new UsernameNotFoundException("Cannot find user " + username);
		}		
		return userDetailsMap.get(username);
	}
	
	/** Method allowed to add new userDetails */
	public void addUser(UserDetails userDetails) {
		userDetailsMap.put(userDetails.getUsername(), userDetails);
	}
	
	public void deleteUser(String userName){
		userDetailsMap.remove(userName);
	}

}
