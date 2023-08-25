package com.wuwei.homework;

import com.wuwei.homework.security.UserArgumentResolver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;


@MapperScan({"com.wuwei.homework.mapper"})
@SpringBootApplication
public class HomeworkApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }


    //注册参数解析器，可单独定义一个Config类
    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserArgumentResolver());
    }


}
