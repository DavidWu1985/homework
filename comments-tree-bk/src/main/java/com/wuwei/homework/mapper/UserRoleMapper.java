package com.wuwei.homework.mapper;

import com.wuwei.homework.entity.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleMapper {
    @Insert("insert into user_role(userid, roleid) values(${role.userid}, ${role.roleid})")
    void insertUserRole(@Param("role") UserRole role);
}
