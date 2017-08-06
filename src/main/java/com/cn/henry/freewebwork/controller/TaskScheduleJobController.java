package com.cn.henry.freewebwork.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.henry.freewebwork.entity.TaskScheduleJob;
import com.cn.henry.freewebwork.service.TaskScheduleJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/taskSchedule")
public class TaskScheduleJobController {

	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private TaskScheduleJobService taskScheduleJobService;
	
	@RequestMapping("/showJobs")
	public String toIndex(HttpServletRequest request, Model model) {
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
	
}
