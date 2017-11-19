package com.cn.henry.freewebwork.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Resource
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String list() {
        return "account/list";
    }

    /**
     * 让DataTables控件加载数据
     * @return
     */
    @RequestMapping(value = "/users.json",method = RequestMethod.GET,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Map<String,Object> load(HttpServletRequest request) {

        String draw = request.getParameter("draw");
        Integer start = Integer.valueOf(request.getParameter("start"));
        Integer length = Integer.valueOf(request.getParameter("length"));
        String searchValue = request.getParameter("search[value]");
        String orderColumnIndex = request.getParameter("order[0][column]");
        String orderType = request.getParameter("order[0][dir]");
        String orderColumnName = request.getParameter("columns["+orderColumnIndex+"][name]");
        Map<String,Object> param = new HashMap<>();
        param.put("start",start);
        param.put("length",length);
        if(StringUtils.isNotEmpty(searchValue)) {
            param.put("keyword", "%" + searchValue + "%");
        }
        param.put("orderColumn",orderColumnName);
        param.put("orderType",orderType);
        Map<String,Object> result = new HashMap<>();
        List<User> userList = userService.findUserByParam(param); //.findAllUser();
        Integer count = userService.findUserCount();
        Integer filteredCount = userService.findUserCountByParam(param);
        result.put("draw",draw);
        result.put("recordsTotal",count); //总记录数
        result.put("recordsFiltered",filteredCount); //过滤出来的数量
        result.put("data",userList);
        return result;
    }

    /**
     * 添加新用户
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @ResponseBody
    public String newUser(User user,String[] role) {
        userService.saveNewUser(user,role);
        return "success";
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    public String delUser(Integer id) {
        userService.delUserById(id);
        return "success";
    }

    /**
     * 根据ID获取用户信息
     */
    @RequestMapping(value = "/user.json",method = RequestMethod.GET)
    @ResponseBody
    public User getUserInfo(Integer id) {
        return userService.findUserWithRoleById(id);
    }

    /**
     * 编辑用户
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public String editUser(User user,String[] role) {
        userService.editUser(user,role);
        return "success";
    }
}
