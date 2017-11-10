package com.cn.henry.freewebwork.dao;

import com.cn.henry.freewebwork.entity.ProgressFile;

public interface ProgressFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProgressFile record);

    int insertSelective(ProgressFile record);

    ProgressFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProgressFile record);

    int updateByPrimaryKey(ProgressFile record);
}