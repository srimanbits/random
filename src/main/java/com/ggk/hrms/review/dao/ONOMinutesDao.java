package com.ggk.hrms.review.dao;

import java.util.List;


import com.ggk.hrms.review.beans.domain.ONOMinutes;


/**
 * @author Swaroops
 * 
 * Interface to handle all DB calls we make to [ONOMinites] table.
 *
 */
public interface ONOMinutesDao {
	
	/**
	 * @param empid
	 * @returns all ONOMeeting Minites for given empid.
	 */
	List<ONOMinutes> getONOMinutes(int empid,Long current);
	
	void save(ONOMinutes minutes);
	
	ONOMinutes find(int id);
	
	Long count(int empid);

}
