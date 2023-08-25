package com.wuwei.homework.mapper;

import com.wuwei.homework.entity.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper {

    @Insert("insert into role(rolename) values('${role.rolename}')")
    void insertRole(@Param("role") Role role);
}
