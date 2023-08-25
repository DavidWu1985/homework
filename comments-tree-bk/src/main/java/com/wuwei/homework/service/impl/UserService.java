package com.wuwei.homework.service.impl;

import com.wuwei.homework.dto.UserDto;
import com.wuwei.homework.entity.User;
import com.wuwei.homework.entity.UserRole;
import com.wuwei.homework.error.BusinessException;
import com.wuwei.homework.mapper.UserMapper;
import com.wuwei.homework.mapper.UserRoleMapper;
import com.wuwei.homework.security.JwtUtil;
import com.wuwei.homework.util.Md5Util;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public User loadUserByUsername(String name) {
        User user = userMapper.findUserByNameOrEmail(name);
        return user;
    }

    public Boolean registerUser(UserDto user) throws Exception {
        User userNameExist = userMapper.findUserByName(user.getUsername());
        User userEmailExist = userMapper.findUserByEmail(user.getEmail());
        if(userNameExist != null && userEmailExist != null){
            throw new BusinessException("用户名和邮箱重复");
        }else if(userNameExist != null){
            throw new BusinessException("用户名重复");
        }else if(userEmailExist != null){
            throw new BusinessException("邮箱重复");
        }
//        if(!user.getPswd().equals(user.getRepswd())){
//            throw new Exception("密码不一致");
//        }
        User u = new User();
        //密码加密
        u.setUsername(user.getUsername());
        u.setEmail(user.getEmail());
        u.setPswd(Md5Util.md5(user.getPswd()));
        userMapper.insertUser(u);

        //同时保存用户角色
        UserRole r = new UserRole();
        r.setUserid(userMapper.findUserByName(u.getUsername()).getId());
        r.setRoleid(1);
        userRoleMapper.insertUserRole(r);
        return true;
    }

    public String login(String name, String pswd) {
        User user = loadUserByUsername(name);
        String md5Pswd = Md5Util.md5(pswd);
        if(user != null  && md5Pswd.equals(user.getPswd())){
            JwtUtil jwtUtil = new JwtUtil();
            Map<String, Object> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("pswd", md5Pswd);
            return jwtUtil.encode(String.valueOf(user.getId()), map);
        }else{
            throw new AuthenticationException("用户名或者密码错误");
        }
    }

    public User getUserById(int id) {
        User u = userMapper.getUserById(id);
        u.setPswd(null);
        return u;
    }
}
