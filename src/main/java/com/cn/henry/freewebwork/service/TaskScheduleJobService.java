package com.cn.henry.freewebwork.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.cn.henry.freewebwork.core.BaseService;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;

public interface TaskScheduleJobService extends BaseService<TaskScheduleJob>{
    List<TaskScheduleJob> selectByJobGroup(String jobGroup);

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void deleteJob(TaskScheduleJob scheduleJob) throws SchedulerException;

	void addJob(TaskScheduleJob job) throws SchedulerException;

	List<TaskScheduleJob> getAllJob() throws SchedulerException;

	void pauseJob(TaskScheduleJob scheduleJob) throws SchedulerException;
	
	void runAJobNow(TaskScheduleJob scheduleJob) throws SchedulerException;

	void updateJobCron(TaskScheduleJob scheduleJob) throws SchedulerException;

	void resumeJob(TaskScheduleJob scheduleJob) throws SchedulerException;
	
	void updateCron(Long jobId, String cron) throws SchedulerException;

}
