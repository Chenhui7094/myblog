package com.birdsh.chen.dao;

import org.apache.ibatis.annotations.Select;

import com.birdsh.chen.domain.Blogger;

public interface BloggerDao {

	/**
     * 根据用户名查询
     * @param username
     * @return
     */
	@Select(" select * from t_blogger where username=#{username}")
    Blogger selectByUsername(String username);
}
