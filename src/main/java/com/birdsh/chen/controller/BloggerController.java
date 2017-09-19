package com.birdsh.chen.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.birdsh.chen.service.BloggerService;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

    @Autowired
    @Qualifier("bloggerService")
    private BloggerService bloggerService;

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
            Model model,RedirectAttributes redirectAttributes) {
    	//得到当前的主体
        Subject subject = SecurityUtils.getSubject();
        //根据用户名和密码获得一个令牌（token）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
        	//进行认证
        	//调用Subject.login(token)方法后，传入代表最终用户的身份和凭证构造的AuthenticationToken实例token。 
            subject.login(token);// 会交给MyRealm中的doGetAuthenticationInfo方法去验证
            redirectAttributes.addFlashAttribute("username",username);
            return "redirect:/admin/main";//重定向到此路径，进入后台管理系统。
        } catch (AuthenticationException e) {
            e.printStackTrace();
            model.addAttribute("message", "用户名或密码错误");
            return "login";//返回登录视图
        }
    }
}