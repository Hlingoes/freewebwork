package com.cn.henry.freewebwork.core;

/**
 * 提供通用的mybatis-generator生成的基本CRUD接口
 * @author Hlingoes
 * @param <T>
 */
public interface BaseMapper<T> {

	int deleteByPrimaryKey(Object jobId);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Object userId);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
