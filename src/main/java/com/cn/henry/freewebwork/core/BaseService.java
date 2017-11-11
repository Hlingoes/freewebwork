package com.cn.henry.freewebwork.core;

public interface BaseService<T> {

	int deleteByPrimaryKey(Object id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
