package com.wuwei.homework.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        log.warn("isAccessAllowed 方法被调用");
        //使用onAccessDenied()方法
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        log.warn("onAccessDenied 方法被调用");
        //所以以后发起请求的时候就需要在Header中放一个Authorization，值就是对应的Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        JwtToken jwtToken = new JwtToken(jwt);
        try {
            //委托 realm 进行登录认证
            //调用JwtRealm进行的认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
            //也就是subject.login(token)
        } catch (Exception e) {
            e.printStackTrace();
            onVerifyFail(servletResponse);
            //调用下面的方法向客户端返回错误信息
            return false;
        }
        return true;
    }

    //登录失败时默认返回 401 状态码
    private void onVerifyFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("token error");
    }
}
