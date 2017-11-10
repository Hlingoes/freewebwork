package com.cn.henry.freewebwork.dao;

import com.cn.henry.freewebwork.entity.Progress;

public interface ProgressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Progress record);

    int insertSelective(Progress record);

    Progress selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Progress record);

    int updateByPrimaryKeyWithBLOBs(Progress record);

    int updateByPrimaryKey(Progress record);
}