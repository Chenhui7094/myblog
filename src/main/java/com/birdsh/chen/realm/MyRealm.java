package com.birdsh.chen.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.birdsh.chen.service.BloggerService;
import domain.Blogger;

/**
 * @ClassName: MyRealm.java 
 * @Description:在realm包中建立此类，用来获取封装正确的信息交给shiro
 * @author chenhuihui
 * @time 2017年9月18日上午10:25:46
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    @Qualifier("bloggerService")
    private BloggerService bloggerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 不需要角色和权限验证，只有自己为博主
        return null;
    }

    /*
     * 对当前登录的用户进行身份认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();// 根据刚刚传过来的token获取用户名
        Blogger blogger = bloggerService.findByUsername(username);//只是根据用户名查询出，不涉及密码
        if (blogger != null) {
            System.out.println("验证信息:"+ blogger);
            // 把获取到的用户存到session中
            SecurityUtils.getSubject().getSession().setAttribute("blogger", blogger);
            // 把从数据库中查询出来的博主信息放到AuthenticationInfo中,即把正确的用户名，密码，交给shiro,再和前台输入的校验。
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(blogger.getUsername(),
                    blogger.getPassword(), "MyRealm");
            return authenticationInfo;
        } else {
            return null;
        }

    }

}