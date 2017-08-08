package com.cn.henry.freewebwork.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.henry.freewebwork.core.BaseResult;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.cn.henry.freewebwork.utils.SpringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/taskSchedule")
public class TaskScheduleJobController {

	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private TaskScheduleJobService taskScheduleJobService;
	
	@RequestMapping("/showJobs")
	public String showJobs(HttpServletRequest request, Model model) {
		String jobGroup = "test";
		int pageNo = 1;
    	int pageSize = 10;
        PageHelper.startPage(pageNo, pageSize);
        List<TaskScheduleJob> list = this.taskScheduleJobService.selectByJobGroup(jobGroup);
        //用PageInfo对结果进行包装
        PageInfo<TaskScheduleJob> page = new PageInfo<TaskScheduleJob>(list);
		request.setAttribute("taskList", page.getList());
		return "quartzTask/taskPage";
	}
	
	@RequestMapping("add")
	@ResponseBody
	public BaseResult addTadk(HttpServletRequest request, TaskScheduleJob scheduleJob) {
		BaseResult BaseResult = new BaseResult();
		BaseResult.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			BaseResult.setMsg("cron表达式有误，不能被解析！");
			return BaseResult;
		}
		Object obj = null;
		try {
			if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
				obj = SpringUtils.getBean(scheduleJob.getSpringId());
			} else {
				Class clazz = Class.forName(scheduleJob.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			// do nothing.........
		}
		if (obj == null) {
			BaseResult.setMsg("未找到目标类！");
			return BaseResult;
		} else {
			Class clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName(), null);
			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				BaseResult.setMsg("未找到目标方法！");
				return BaseResult;
			}
		}
		try {
			this.taskScheduleJobService.addJob(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			BaseResult.setFlag(false);
			BaseResult.setMsg("保存失败，检查 name group 组合是否有重复！");
			return BaseResult;
		}
		BaseResult.setFlag(true);
		return BaseResult;
	}

	@RequestMapping("changeJobStatus")
	@ResponseBody
	public BaseResult changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {
		BaseResult BaseResult = new BaseResult();
		BaseResult.setFlag(false);
		try {
			this.taskScheduleJobService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			BaseResult.setMsg("任务状态改变失败！");
			return BaseResult;
		}
		BaseResult.setFlag(true);
		return BaseResult;
	}

	@RequestMapping("updateCron")
	@ResponseBody
	public BaseResult updateCron(HttpServletRequest request, Long jobId, String cron) {
		BaseResult BaseResult = new BaseResult();
		BaseResult.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			BaseResult.setMsg("cron表达式有误，不能被解析！");
			return BaseResult;
		}
		try {
			this.taskScheduleJobService.updateCron(jobId, cron);
		} catch (SchedulerException e) {
			BaseResult.setMsg("cron更新失败！");
			return BaseResult;
		}
		BaseResult.setFlag(true);
		return BaseResult;
	}
	
}
