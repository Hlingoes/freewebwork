package com.cn.henry.freewebwork.core;

public interface BaseMapper<T> {

	int deleteByPrimaryKey(Object jobId);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Object userId);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
