package com.ggk.hrms.review.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ggk.hrms.review.beans.domain.Rating;
import com.ggk.hrms.review.dao.RatingDAO;
import com.ggk.hrms.review.service.RatingService;

@Service("ratingService")
public class RatingServiceImpl implements RatingService{
	
	@Resource
	private RatingDAO ratingDao;

	@Override
	public List<Rating> getAllActiveRatings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating getRatingById(int ratingId) {
		return ratingDao.getRatingById(ratingId);
	}

	@Override
	public Map<Integer,String> getRatingsDropDown() {
		return ratingDao.getRatingsDropDown();
	}

}
