package com.wuwei.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {


    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String pswd;

//    @NotBlank(message = "密码不能为空")
//    private String repswd;

}
