package com.cn.henry.freewebwork.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.henry.freewebwork.core.BaseMapper;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;

/**
 * 继承了基础接口规范，因为BaseMapper
 * @author Administrator
 *
 */
public interface TaskScheduleJobMapper extends BaseMapper<TaskScheduleJob>{
    List<TaskScheduleJob> selectByJobGroup(@Param("jobGroup") String jobGroup);
}