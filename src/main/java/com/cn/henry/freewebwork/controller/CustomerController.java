package com.cn.henry.freewebwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.henry.freewebwork.core.Message;
import com.cn.henry.freewebwork.entity.Customer;
import com.cn.henry.freewebwork.entity.Progress;
import com.cn.henry.freewebwork.entity.ProgressFile;
import com.cn.henry.freewebwork.entity.Task;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.CustomerService;
import com.cn.henry.freewebwork.service.ProgressService;
import com.cn.henry.freewebwork.service.TaskService;
import com.cn.henry.freewebwork.service.UserService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Resource
    private CustomerService customerService;
	@Resource
    private UserService userService;
	@Resource
    private ProgressService progressService;
	@Resource
    private TaskService taskService;


    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "customer/list";
    }


    /**
     * DataTables加载数据
     * @return
     */
    @RequestMapping(value = "/customers.json",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> load(HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<String, Object>();
        String draw = request.getParameter("draw");
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));
        String searchName = request.getParameter("seaName");
        String searchTel = request.getParameter("seaTel");
        String searchState = request.getParameter("seaState");
        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderType = request.getParameter("order[0][dir]");
        String orderColumnName = request.getParameter("columns["+orderColumnIndex+"][name]");
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("start",start);
        param.put("length",length);
        if(StringUtils.isNotEmpty(searchName)) {
            param.put("seaName", "%" + searchName + "%");
        }
        if(StringUtils.isNotEmpty(searchTel)) {
            param.put("seaTel", "%" + searchTel + "%");
        }
        if(StringUtils.isNotEmpty(searchState)) {
            param.put("seaState", searchState);
        }
        if(orderColumnName == null || orderType == null) {
            param.put("orderColumn","id");
            param.put("orderType","desc");
        } else {
            param.put("orderColumn", orderColumnName);
            param.put("orderType", orderType);
        }

        List<Customer> userList = customerService.findUserByParam(param);
        Integer count = customerService.findCustomerCount();
        Integer filteredCount = customerService.findUserCountByParam(param);

        resultMap.put("draw",draw);
        resultMap.put("recordsTotal",count); //总记录数
        resultMap.put("recordsFiltered",filteredCount); //过滤出来的数量
        resultMap.put("data",userList);

        return resultMap;
    }


    /**
     * 添加新客户
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String newCust(Customer customer) {
        customerService.saveNewCustomer(customer);
        return "success";
    }


    /**
     * 查看客户资料
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String viewCustomer(@PathVariable Integer id, Model model) {
        Customer customer = customerService.findCustomerById(id);
        List<User> userList = userService.findAllUser();
        List<Progress> progressList = progressService.findProgressByCustId(id);
        List<ProgressFile> fileList = progressService.findProgressFileByCustId(id);
        List<Task> taskList = taskService.findunDoneTaskByCustId(id);

        model.addAttribute("customer",customer);
        model.addAttribute("userList",userList);
        model.addAttribute("progressList",progressList);
        model.addAttribute("fileList",fileList);
        model.addAttribute("taskList",taskList);
        return "customer/view";
    }

    /**
     * 删除客户
     */
    @RequestMapping(value = "/del/{id:\\d+}",method = RequestMethod.GET)
    public String delCustomer(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        customerService.delCustomer(id);
        redirectAttributes.addFlashAttribute("message",new Message(Message.SUCCESS,"删除成功"));
        return "redirect:/customer";
    }

    /**
     * 公开客户
     * @return
     */
    @RequestMapping(value = "/public/{id:\\d+}",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publicCustomer(@PathVariable Integer id) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            customerService.publicCustomer(id);
            result.put("state","success");
        } catch (NotFoundException ex) {
            result.put("state","error");
            result.put("message","客户不存在");
        } catch (ForbiddenException ex) {
            result.put("state","error");
            result.put("message","权限不足");
        }
        return result;
    }

    /**
     * 转交客户
     * @return
     */
    @RequestMapping("/tran/{custId:\\d+}/{userId:\\d+}")
    @ResponseBody
    public Map<String,Object> tranCustomer(@PathVariable Integer custId,@PathVariable Integer userId) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            customerService.tranCustomer(custId, userId);
            result.put("state","success");
        } catch (NotFoundException ex) {
            result.put("state","error");
            result.put("message",ex.getMessage());
        } catch (ForbiddenException ex) {
            result.put("state","error");
            result.put("message","权限不足");
        }
        return result;
    }

    /**
     * 新增跟进记录
     */
    @RequestMapping(value = "/progress/new",method = RequestMethod.POST)
    public String newProgress(Progress progress,@RequestParam MultipartFile[] file,RedirectAttributes redirectAttributes) {
        progressService.saveNewProgress(progress,file);

        redirectAttributes.addFlashAttribute("message",new Message(Message.SUCCESS,"添加成功"));
        return "redirect:/customer/"+progress.getCustid();
    }

    /**
     * 将待办任务完成
     */
    @RequestMapping(value = "/change/taskstate",method = RequestMethod.POST)
    @ResponseBody
    public String changeTaskState(String taskId,boolean state) {
        taskService.changeTaskState(taskId,state);
        return "success";
    }



}
