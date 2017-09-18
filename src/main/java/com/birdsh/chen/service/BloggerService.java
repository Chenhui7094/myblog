package com.birdsh.chen.service;

import com.birdsh.chen.domain.Blogger;

public interface BloggerService {

	public Blogger findByUsername(String username);
}
