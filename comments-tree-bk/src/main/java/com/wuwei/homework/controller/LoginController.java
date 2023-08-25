package com.wuwei.homework.controller;

import com.wuwei.homework.api.R;
import com.wuwei.homework.dto.UserDto;
import com.wuwei.homework.entity.User;
import com.wuwei.homework.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param name
     * @param pswd
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestParam("name") String name, @RequestParam("pswd") String pswd){
        return R.data(userService.login(name, pswd));
    }

    /**
     * 注册用户
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/register")
    public R<Boolean> registerUser(@RequestBody @Valid UserDto user) throws Exception {
        return R.data(userService.registerUser(user));
    }

    /**
     * 获取当前用户信息
     * @param user
     * @return
     */
    @GetMapping("/current_user")
    public R<User> getCurrUser(User user){
        return R.data(userService.getUserById(user.getId()));
    }
}
