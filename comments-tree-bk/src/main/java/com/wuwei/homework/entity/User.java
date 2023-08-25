package com.wuwei.homework.entity;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class User {

    private int id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String pswd;



}
