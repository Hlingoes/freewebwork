package com.cn.henry.freewebwork.dao;

import com.cn.henry.freewebwork.entity.RoleUserKey;

public interface RoleUserMapper {
    int deleteByPrimaryKey(RoleUserKey key);

    int insert(RoleUserKey record);

    int insertSelective(RoleUserKey record);
}