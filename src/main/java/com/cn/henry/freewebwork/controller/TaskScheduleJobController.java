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
import com.cn.henry.freewebwork.core.JqGridPage;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.cn.henry.freewebwork.utils.SpringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 任务的增删改调度
 * @author Hlingoes
 * @time 2017-8-17 23:12:55
 */
@Controller
public class TaskScheduleJobController {

	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private TaskScheduleJobService taskScheduleJobService;
	
	/**
	 * 后台的分发器，定位到jsp页面。前端没有使用jsp，直接使用的静态html，通过ajax获取数据，jqGrig渲染出表格
	 */
	@RequestMapping("/taskSchedule")
	public String taskSchedule () {
		return "quartzTask/taskPage";
	}
	
	@RequestMapping("/html/task/showJobs")
	@ResponseBody
	public JqGridPage showJobs(HttpServletRequest request, Model model) {
		String jobGroup = "test";
		int pageNo = 1;
    	int pageSize = 10;
        PageHelper.startPage(pageNo, pageSize);
        List<TaskScheduleJob> list = this.taskScheduleJobService.selectByJobGroup(jobGroup);
        //用PageInfo对结果进行包装
        PageInfo<TaskScheduleJob> pageInfo = new PageInfo<TaskScheduleJob>(list);
		return new JqGridPage(pageInfo);
	}
	
	@RequestMapping("add")
	@ResponseBody
	public BaseResult addTadk(HttpServletRequest request, TaskScheduleJob scheduleJob) {
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
		} catch (Exception e) {
			baseResult.setMsg("cron表达式有误，不能被解析！");
			return baseResult;
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
			baseResult.setMsg("未找到目标类！");
			return baseResult;
		} else {
			Class clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(scheduleJob.getMethodName(), null);
			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				baseResult.setMsg("未找到目标方法！");
				return baseResult;
			}
		}
		try {
			this.taskScheduleJobService.addJob(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			baseResult.setFlag(false);
			baseResult.setMsg("保存失败，检查 name group 组合是否有重复！");
			return baseResult;
		}
		baseResult.setFlag(true);
		return baseResult;
	}

	@RequestMapping("changeJobStatus")
	@ResponseBody
	public BaseResult changeJobStatus(HttpServletRequest request, Long jobId, String cmd) {
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(false);
		try {
			this.taskScheduleJobService.changeStatus(jobId, cmd);
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			baseResult.setMsg("任务状态改变失败！");
			return baseResult;
		}
		baseResult.setFlag(true);
		return baseResult;
	}

	@RequestMapping("updateCron")
	@ResponseBody
	public BaseResult updateCron(HttpServletRequest request, Long jobId, String cron) {
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(false);
		try {
			CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			baseResult.setMsg("cron表达式有误，不能被解析！");
			return baseResult;
		}
		try {
			this.taskScheduleJobService.updateCron(jobId, cron);
		} catch (SchedulerException e) {
			baseResult.setMsg("cron更新失败！");
			return baseResult;
		}
		baseResult.setFlag(true);
		return baseResult;
	}
	
}
