package com.cn.henry.freewebwork.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cn.henry.freewebwork.core.Message;
import com.cn.henry.freewebwork.entity.User;
import com.cn.henry.freewebwork.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class HomeController {

    @Value("${user.salt}")
    private String passwordSalt;

    @Resource
    private CustomerService customerService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(Model model) {

        return "index";
    }

    /**
     * 用户登录
     * @param tel
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(String tel, String password, RedirectAttributes redirectAttributes) {

        //获取认证主体，如果主体已存在，则将当前的主体退出
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            subject.logout();
        }

        try {
            //登录，调用ShiroRealm类中的登录认证方法
            subject.login(new UsernamePasswordToken(tel, DigestUtils.md5Hex(password+passwordSalt)));

            //将登录的对象放入到Session中
            Session session = subject.getSession();
            session.setAttribute(User.SESSION_KEY,(User)subject.getPrincipal());

            return "redirect:/home";
        } catch (LockedAccountException ex) {
            redirectAttributes.addFlashAttribute("message",new Message(Message.ERROR,ex.getMessage()));
            return "redirect:/";
        } catch (UnknownAccountException ex) {
            redirectAttributes.addFlashAttribute("message",new Message(Message.ERROR,ex.getMessage()));
            return "redirect:/";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message",new Message(Message.ERROR,"账号或密码错误"));
            return "redirect:/";
        }
    }


    /**
     * 安全退出
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(RedirectAttributes redirectAttributes) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        redirectAttributes.addFlashAttribute("message",new Message(Message.SUCCESS,"你已安全退出"));
        return "redirect:/";
    }

    /**
     * 登录后的页面
     * @throws JsonProcessingException 
     */
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model) throws JsonProcessingException {
        List<Map<String,Object>> result = customerService.homeTotal();
        ObjectMapper mapper = new ObjectMapper();
        model.addAttribute("json", mapper.writeValueAsString(result));
        return "home";
    }







}
