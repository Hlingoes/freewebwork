package com.cn.henry.freewebwork.quartzTask;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cn.henry.freewebwork.entity.TaskScheduleJob;

/**
 * @Description: 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 * @author chenjianlin
 * @date 2014年4月24日 下午5:05:47
 * @cite https://github.com/snailxr/quartz-spring_demo
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution implements Job {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		TaskScheduleJob scheduleJob = (TaskScheduleJob) context.getMergedJobDataMap().get(QuartzTaskUtils.SCHEDULE_JOB_KEY);
		QuartzTaskUtils.invokMethod(scheduleJob);

	}
}
