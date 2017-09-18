package com.birdsh.chen.domain;

import lombok.Data;

/**
 * @ClassName: Blogger.java 
 * @Description:博主实体类
 * @author chenhuihui
 * @time 2017年9月18日上午9:58:40
 */
@Data
public class Blogger {

	private Integer id;
	private String username;
	private String password;
	private String email;
}
