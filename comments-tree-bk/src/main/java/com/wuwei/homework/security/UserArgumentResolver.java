package com.wuwei.homework.security;

import com.wuwei.homework.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 参数处理器，针对请求方法中的某一类方法参数进行特殊处理
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取token, 并从中解析出user信息
        String token = nativeWebRequest.getHeader("Authorization");
        JwtUtil util = new JwtUtil();
        Claims claims = util.decode(token);
        User user = new User();
        user.setId(Integer.valueOf(claims.getSubject()));
        user.setUsername((String)claims.get("username"));
        return user;
    }
}
