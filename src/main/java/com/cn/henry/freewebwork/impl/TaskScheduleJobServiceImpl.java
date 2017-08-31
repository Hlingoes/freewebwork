package com.cn.henry.freewebwork.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.cn.henry.freewebwork.dao.TaskScheduleJobMapper;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.quartzTask.QuartzJobFactory;
import com.cn.henry.freewebwork.quartzTask.QuartzJobFactoryDisallowConcurrentExecution;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;

@Service("taskScheduleJobService")
public class TaskScheduleJobServiceImpl implements TaskScheduleJobService{
	
	public final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	
	@Resource 
    private TaskScheduleJobMapper taskScheduleJobMapper; 
	
	/**
	 * 删除数据库中的记录
	 */
	@Override
	public int deleteByPrimaryKey(Object jobId) {
		return this.taskScheduleJobMapper.deleteByPrimaryKey(Long.parseLong(String.valueOf(jobId)));
	}

	/**
	 * 添加到数据库中 区别于addJob
	 */
	@Override
	public int insert(TaskScheduleJob record) {
		java.util.Date date = new java.util.Date();
		record.setCreateTime(new Timestamp(date.getTime()));
		return this.taskScheduleJobMapper.insert(record);
	}

	@Override
	public int insertSelective(TaskScheduleJob record) {
		return this.taskScheduleJobMapper.insertSelective(record);
	}

	/**
	 * 从数据库中查询job
	 */
	@Override
	public TaskScheduleJob selectByPrimaryKey(Object jobId) {
		return this.taskScheduleJobMapper.selectByPrimaryKey(Long.parseLong(String.valueOf(jobId)));
	}

	@Override
	public int updateByPrimaryKeySelective(TaskScheduleJob record) {
		return this.taskScheduleJobMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TaskScheduleJob record) {
		return this.taskScheduleJobMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<TaskScheduleJob> selectByCondition(Map<String, String> condition) {
		return this.taskScheduleJobMapper.selectByCondition(condition);
	}
	
	/**
	 * 更改任务状态
	 * @throws SchedulerException
	 */
	@Override
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		TaskScheduleJob job = this.selectByPrimaryKey(jobId);
		if (job == null) {
			return;
		}
		if ("stop".equals(cmd)) {
			this.deleteJob(job);
			job.setJobStatus(TaskScheduleJob.STATUS_NOT_RUNNING);
		} else if ("start".equals(cmd)) {
			job.setJobStatus(TaskScheduleJob.STATUS_RUNNING);
			this.addJob(job);
		}
		this.updateByPrimaryKeySelective(job);
	}
	
	/**
	 * 删除一个job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void deleteJob(TaskScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.deleteJob(jobKey);
	}
	
	/**
	 * 添加任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void addJob(TaskScheduleJob job) throws SchedulerException {
		if (job == null || !TaskScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			return;
		}

		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		log.debug(scheduler + ".......................................................................................add");
		TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		// 不存在，创建一个
		if (null == trigger) {
			Class clazz = TaskScheduleJob.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
			JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			
			// Trigger已存在，那么更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
	
	/**
	 * 更改任务 cron表达式
	 * @throws SchedulerException
	 */
	public void updateCron(Long jobId, String cron) throws SchedulerException {
		TaskScheduleJob job = this.selectByPrimaryKey(jobId);
		if (job == null) {
			return;
		}
		job.setCronExpression(cron);
		if (TaskScheduleJob.STATUS_RUNNING.equals(job.getJobStatus())) {
			this.updateJobCron(job);
		}
		this.taskScheduleJobMapper.updateByPrimaryKeySelective(job);
	}
	
	/**
	 * 获取所有计划中的任务列表
	 * @throws SchedulerException
	 */
	@Override
	public List<TaskScheduleJob> getAllJob() throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
		List<TaskScheduleJob> jobList = new ArrayList<TaskScheduleJob>();
		TaskScheduleJob job = null; 
		for (JobKey jobKey : jobKeys) {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
			for (Trigger trigger : triggers) {
				job = new TaskScheduleJob();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setDescription("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				if (trigger instanceof CronTrigger) {
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
				}
				jobList.add(job);
			}
		}
		return jobList;
	}

	/**
	 * 暂停一个job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void pauseJob(TaskScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复一个job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void resumeJob(TaskScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 立即执行job
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void runAJobNow(TaskScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 更新job时间表达式
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	@Override
	public void updateJobCron(TaskScheduleJob scheduleJob) throws SchedulerException {
		Scheduler scheduler = this.schedulerFactoryBean.getScheduler();
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
}
