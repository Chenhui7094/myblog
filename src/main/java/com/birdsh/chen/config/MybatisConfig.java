package com.birdsh.chen.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;


/**
 * @ClassName: MybatisConfig.java 
 * @Description:Mybatis的配置类
 * @author chenhuihui
 * @time 2017年9月18日上午10:21:54
 */
@Configuration
public class MybatisConfig {

	@Autowired
	private DataSource dataSource;

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		// 分页插件
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("supportMethodsArguments", "true");
		properties.setProperty("returnPageInfo", "check");
		properties.setProperty("params", "pageNum=page;pageSize=rows;orderBy=orderBy");
		pageHelper.setProperties(properties);

		// 添加插件
		sqlSessionFactoryBean.setPlugins(new Interceptor[] { pageHelper });
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() {
		SqlSessionTemplate sqlSessionTemplate = null;
		try {
			sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSessionTemplate;
	}
}
