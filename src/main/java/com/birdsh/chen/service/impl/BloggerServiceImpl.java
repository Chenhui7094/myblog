package com.birdsh.chen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birdsh.chen.dao.BloggerDao;
import com.birdsh.chen.service.BloggerService;

import domain.Blogger;
@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

	@Autowired
	BloggerDao bloggerDao;

	@Override
	public Blogger findByUsername(String username) {
		return bloggerDao.selectByUsername(username);
	}
}
