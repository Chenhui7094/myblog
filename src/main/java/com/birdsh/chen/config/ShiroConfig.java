package com.birdsh.chen.config;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.birdsh.chen.realm.MyRealm;


/**
 * @ClassName: ShiroConfig.java 
 * @Description:shiro配置类
 * @author chenhuihui
 * @time 2017年9月18日上午10:30:06
 */
@Configuration
public class ShiroConfig {

	/**
	 * shiroFilter
	 * @param manager
	 * @return
	 */
	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url
		bean.setLoginUrl("/login");
		//bean.setSuccessUrl("/admin/main");
		// 过滤链
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/login", "anon"); // 表示可以匿名访问
		filterChainDefinitionMap.put("/admin/**", "authc");// 管理员权限需要验证过滤

		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}
	
    /**
     * 注册自定义的Realm
     * @return
     */
    @Bean(name="myRealm")
    public MyRealm myRealm() {
        MyRealm myRealm=new MyRealm();
        return myRealm;
    }
    
    /**
     * 声明SecurityManager
     * @param myRealm
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("myRealm") MyRealm myRealm) {
        System.out.println("加载shiro");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(myRealm);
        return manager;
    }    
	

	/**
	 * 保证实现了shiro内部lifecycle函数的bean执行
	 * @return
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	
    /**
     * 开启shiro,首先创建代理
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    
    /**
     * 再加载securityManager
     * @param manager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }	
	

}
