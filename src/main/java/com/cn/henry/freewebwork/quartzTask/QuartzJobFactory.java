package com.cn.henry.freewebwork.quartzTask;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.henry.freewebwork.entity.TaskScheduleJob;

/**
 * @Description: 计划任务执行处 无状态
 * @author Hlingoes
 * @cite https://github.com/snailxr/quartz-spring_demo
 * @date 2017-8-2 22:37:30
 */
public class QuartzJobFactory implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskScheduleJob scheduleJob = (TaskScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
		QuartzTaskUtils.invokMethod(scheduleJob);
	}
}
