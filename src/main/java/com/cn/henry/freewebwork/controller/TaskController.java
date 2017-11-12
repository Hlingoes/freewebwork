package com.cn.henry.freewebwork.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.henry.freewebwork.core.Message;
import com.cn.henry.freewebwork.entity.Customer;
import com.cn.henry.freewebwork.entity.Task;
import com.cn.henry.freewebwork.service.CustomerService;
import com.cn.henry.freewebwork.service.TaskService;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;
    @Resource
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<Customer> customerList = customerService.findCustomerByCurrentUser();
        List<Task> taskList = taskService.findAllByCurrentUser();
        List<Task> newTaskList = new ArrayList<>();
        List<Task> doneTaskList = new ArrayList<>();
        for (Task task : taskList) {
			if (!task.getDone()) {
				newTaskList.add(task);
			} else {
				doneTaskList.add(task);
			}
		}
        model.addAttribute("newTaskList",newTaskList);
        model.addAttribute("doneTaskList",doneTaskList);
        model.addAttribute("customerList",customerList);
        return "task/list";
    }

    /**
     * 保存新的待办任务
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newTask(Task task, RedirectAttributes redirectAttributes) {
        taskService.save(task);

        redirectAttributes.addFlashAttribute("message",new Message(Message.SUCCESS,"添加新任务成功"));
        return "redirect:/task";
    }

    /**
     * 改变任务的状态（已完成，未完成）
     */
    @RequestMapping(value = "/state/change",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> changeState(String taskId, boolean state) {
        Map<String,Object> result = new HashMap<>();
        try {
            taskService.changeTaskState(taskId, state);
            result.put("state","success");
        } catch (NotFoundException ex) {
            result.put("state","error");
            result.put("message","待办任务不存在");
        } catch (ForbiddenException ex) {
            result.put("state","error");
            result.put("message","权限不足");
        }
        return result;
    }

}
