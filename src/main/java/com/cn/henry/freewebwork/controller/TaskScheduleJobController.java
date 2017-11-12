package com.cn.henry.freewebwork.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.henry.freewebwork.core.BaseResult;
import com.cn.henry.freewebwork.core.JqGridPage;
import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.cn.henry.freewebwork.utils.SpringUtils;
import com.cn.henry.freewebwork.utils.Excel.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
 * 任务的增删改调度
 * @author Hlingoes
 * @time 2017-8-17 23:12:55
 */
@Controller
@RequestMapping("/html/task")
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
	
	/**  
	 * RequestParam注解绑定请求参数 
     * @RequestParam 映射请求参数  
     * value 请求参数的参数名 ，作为参数映射名称 
     * required 该参数是否必填，默认为true(必填)，当设置成必填时，如果没有传入参数，报错  
     * defaultValue 设置请求参数的默认值  
     */ 
	@RequestMapping("/showJobs")
	@ResponseBody
	public JqGridPage showJobs(@RequestParam(value = "rows", required = true, defaultValue = "10") int pageSize,  
            						@RequestParam(value = "page", required = true, defaultValue = "1") int pageNum, 
            						@RequestParam(value = "sidx", required = false, defaultValue = "update_time") String sidx,
            						@RequestParam(value = "sord", required = false, defaultValue = "desc") String sord) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, String> condition = new HashMap<String, String> ();
        condition.put("sidx", sidx);
        condition.put("sord", sord);
        condition.put("jobGroup", "test"); // 用作对条件查询的测试
        List<TaskScheduleJob> list = this.taskScheduleJobService.selectByCondition(condition);
        //用PageInfo对结果进行包装
        PageInfo<TaskScheduleJob> pageInfo = new PageInfo<TaskScheduleJob>(list);
		return new JqGridPage(pageInfo);
	}
	
	@RequestMapping(value="add", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult addTask(@Valid TaskScheduleJob scheduleJob, BindingResult result) {
		BaseResult baseResult = new BaseResult();
		if (result.hasErrors()) { 
			baseResult.setFlag(false);
			
			StringBuffer msg = new StringBuffer();  
			List<ObjectError> errorObjects = result.getAllErrors();
			for (ObjectError objectError : errorObjects) {
				msg.append(objectError.getDefaultMessage());
			}
			baseResult.setMsg(msg.toString());
			return baseResult;
		}
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
				method = clazz.getMethod(scheduleJob.getMethodName(), new Class<?>[]{JobExecutionContext.class});
			} catch (Exception e) {
				// do nothing.....
			}
			if (method == null) {
				baseResult.setMsg("未找到目标方法！");
				return baseResult;
			}
		}
		try {
			// 注册到quartz factory中
			this.taskScheduleJobService.addJob(scheduleJob);
			// 插入数据库
			this.taskScheduleJobService.insert(scheduleJob);
		} catch (Exception e) {
			e.printStackTrace();
			baseResult.setFlag(false);
			baseResult.setMsg("保存失败，检查 name group 组合是否有重复！");
			return baseResult;
		}
		baseResult.setFlag(true);
		return baseResult;
	}
	
	@RequestMapping(value="getTask", method=RequestMethod.GET)
	@ResponseBody
	public BaseResult getTask(@RequestParam(value = "jobId", required = true) Long jobId){
		BaseResult baseResult = new BaseResult();
		baseResult.setFlag(true);
		baseResult.setObj(this.taskScheduleJobService.selectByPrimaryKey(jobId));
		return baseResult;
	}

	@RequestMapping(value="changeJobStatus", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult changeJobStatus(@RequestParam(value = "jobId", required = true) Long jobId,
			@RequestParam(value = "cmd", required = true) String cmd) throws SchedulerException {
		BaseResult baseResult = new BaseResult();
		this.taskScheduleJobService.changeStatus(jobId, cmd);
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
			return baseResult;		}
		baseResult.setFlag(true);
		return baseResult;
	}
	
	@RequestMapping(value="testHandlerException", method=RequestMethod.POST)
	@ResponseBody
	public BaseResult testHandlerException(@RequestParam(value = "jobId", required = true) Long jobId) throws SchedulerException {
		BaseResult baseResult = new BaseResult();
		if (jobId < 10) {
			this.taskScheduleJobService.testHandlerException();
		}
		baseResult.setFlag(true);
		return baseResult;
	}
	
	@RequestMapping(value = "/exportExcel ")  
    public void exportExcel(@RequestParam(value = "sidx", required = false, defaultValue = "update_time") String sidx,
			@RequestParam(value = "sord", required = false, defaultValue = "desc") String sord, HttpServletResponse response) throws IOException, SchedulerException {  
		Map<String, String> condition = new HashMap<>();
		condition.put("sidx", sidx);
        condition.put("sord", sord);
		List<TaskScheduleJob> taskList = this.taskScheduleJobService.selectByCondition(condition);
		String fileName = "task_" + DateFormatUtils.format(new Date(), "yyyyMMddhhmmss") + ".xls";
		Map<String, String> exportHeadMap = new LinkedHashMap<>();
		// 用排序的Map，即LinkedHashMap，且Map的键要与ExcelCell注解的index对应
		exportHeadMap.put("jobName", "任务名");
		exportHeadMap.put("jobGroup", "任务分组");
		exportHeadMap.put("jobStatus", "任务状态");
		exportHeadMap.put("cronExpression", "时间表达式");
		exportHeadMap.put("description", "功能描述");
		exportHeadMap.put("beanClass", "实例化的功能类");
		exportHeadMap.put("methodName", "功能函数名");
		exportHeadMap.put("createTime", "创建时间");
		exportHeadMap.put("updateTime", "修改时间");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);  
        OutputStream ouputStream = response.getOutputStream();
		ExcelUtil.exportExcel(exportHeadMap, taskList, ouputStream);
		ouputStream.flush();
		ouputStream.close();
   }  
	
}
