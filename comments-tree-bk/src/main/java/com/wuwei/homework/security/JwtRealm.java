package com.wuwei.homework.security;

import com.wuwei.homework.entity.User;
import com.wuwei.homework.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    //认证
    //这个token就是从过滤器中传入的jwtToken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String jwt = (String) token.getPrincipal();
        if (jwt == null) {
            throw new UnknownAccountException("jwtToken 不允许为空");
        }
        //判断
        JwtUtil jwtUtil = new JwtUtil();
        if (!jwtUtil.isVerify(jwt)) {
            throw new UnknownAccountException();
        }
        //下面是验证这个user是否是真实存在的
//        String username = (String) jwtUtil.decode(jwt).get("username");
//        String pswd = (String) jwtUtil.decode(jwt).get("pswd");
//        User user = userService.loadUserByUsername(username);
//        //如果用户不存在或者密码不相等则报错
//        if (user == null || (!user.getPswd().equals(pswd))) {
//            throw new AuthenticationException("Username or password error!");
//        }
//        log.info("在使用token登录"+username);
        return new SimpleAuthenticationInfo(jwt,jwt,"JwtRealm");

    }

}