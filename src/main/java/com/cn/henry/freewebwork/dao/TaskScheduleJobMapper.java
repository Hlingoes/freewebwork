package com.cn.henry.freewebwork.dao;

import java.util.List;
import java.util.Map;

import com.cn.henry.freewebwork.core.BaseMapper;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;

/**
 * 继承了基础接口规范，BaseMapper，新增私有接口
 * @author Hlingoes
 * @time 2017-8-31 22:26:17
 */
public interface TaskScheduleJobMapper extends BaseMapper<TaskScheduleJob>{
    List<TaskScheduleJob> selectByCondition(Map<String, String> condition);
}