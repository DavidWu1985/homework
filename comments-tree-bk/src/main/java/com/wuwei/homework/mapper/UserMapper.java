package com.wuwei.homework.mapper;

import com.wuwei.homework.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {

    @Select("select * from user where username = '${name}' or email = '${name}'")
    User findUserByNameOrEmail(String name);

    @Select("select * from user where username = '${name}'")
    User findUserByName(String name);

    @Select("select * from user where email = '${email}'")
    User findUserByEmail(String email);

    @Insert("insert into user(username, email, pswd) values ('${user.username}', '${user.email}','${user.pswd}')")
    void insertUser(@Param("user") User user);

    @Select("select * from user where id = ${id}")
    User getUserById(int id);
}
