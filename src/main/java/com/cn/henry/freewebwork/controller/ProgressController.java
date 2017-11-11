package com.cn.henry.freewebwork.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.henry.freewebwork.core.Message;
import com.cn.henry.freewebwork.entity.Customer;
import com.cn.henry.freewebwork.entity.Progress;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.CustomerService;
import com.cn.henry.freewebwork.service.ProgressService;
import com.cn.henry.freewebwork.service.UserService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/progress")
public class ProgressController {

    @Inject
    private UserService userService;
    @Inject
    private CustomerService customerService;
    @Inject
    private ProgressService progressService;

    /**
     * 跟进首页
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(required = false,defaultValue = "") String userid,@RequestParam(required = false,defaultValue = "") String progress,
                       @RequestParam(required = false,defaultValue = "") String date,@RequestParam(required = false,defaultValue = "")String context,
                       @RequestParam(required = false,defaultValue = "1") String p,
                       Model model) {
        List<User> userList = userService.findAllUser();
        List<Customer> customerList = customerService.findCustomerByCurrentUser();
        PageInfo<Progress> page = progressService.findProgressByPageAndParam(userid,progress ,date,context,p);
        model.addAttribute("userList",userList);
        model.addAttribute("page",page);
        model.addAttribute("customerList",customerList);
        model.addAttribute("userid",userid);
        model.addAttribute("progress",progress);
        model.addAttribute("date",date);
        model.addAttribute("context",context);
        return "progress/list";
    }

    /**
     * 保存新跟进记录
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String save(Progress progress,@RequestParam MultipartFile[] file, RedirectAttributes redirectAttributes) {
        progressService.saveNewProgress(progress,file);
        redirectAttributes.addFlashAttribute("message",new Message(Message.SUCCESS,"添加成功"));
        return "redirect:/progress";
    }
}
